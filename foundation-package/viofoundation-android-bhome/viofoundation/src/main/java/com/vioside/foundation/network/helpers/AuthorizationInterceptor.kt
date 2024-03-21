package com.vioside.foundation.network.helpers

import com.vioside.foundation.network.models.AuthenticationResponseInterface
import com.vioside.foundation.network.respositories.AuthenticationRepository
import com.vioside.foundation.network.services.AuthenticationTokenService
import kotlinx.coroutines.runBlocking
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthorizationInterceptor<AuthenticationResponseT: AuthenticationResponseInterface, RefreshResponseT: AuthenticationResponseInterface>(
    private val authorized: Boolean
) : Interceptor, KoinComponent {

    private val authenticationTokenService: AuthenticationTokenService by inject()
    private val tokenRefreshRepository: AuthenticationRepository<AuthenticationResponseT, RefreshResponseT> by inject()

    override fun intercept(chain: Interceptor.Chain): Response {

        val httpClient = chain.request()
            .newBuilder()
            .header("Accept",  "application/json")
            .cacheControl(CacheControl.FORCE_NETWORK)

        // If authorized add the authorization token
        if (authorized) {
            val token = authenticationTokenService.token
            httpClient.header("Authorization",  "Bearer $token")
        }

        return chain.proceed(httpClient.build()).run {
            if(authorized) {
                return checkUnauthorizedAndRefreshToken(this, httpClient, chain)
            }
            this
        }
    }

    private fun checkUnauthorizedAndRefreshToken(
        response: Response,
        httpClient: Request.Builder,
        chain: Interceptor.Chain
    ): Response {
        if(authenticationTokenService.token == null || authenticationTokenService.refreshToken == null) {
            return response
        }
        if (response.code == 401 || response.code == 403) { //if unauthorized
            synchronized(httpClient) {
                //perform all 401 in sync blocks, to avoid multiply token updates
                val currentToken: String = authenticationTokenService.token!! //get currently stored token
                val refreshToken: String = authenticationTokenService.refreshToken!! //get currently stored token
                val refreshed = runBlocking { tokenRefreshRepository.refreshToken(currentToken, refreshToken, authenticationTokenService.userId) }
                if (!refreshed) { //if refresh token failed for some reason
                    authenticationTokenService.refreshToken = null
                    authenticationTokenService.token = null
                    authenticationTokenService.userName = null
                    authenticationTokenService.userId = null
                    return response //if token refresh failed - show error to user
                }
                authenticationTokenService.token?.let { token  ->
                    response.close()
                    httpClient.header("Authorization",  "Bearer $token")
                    return chain.proceed(httpClient.build()) //repeat request with new token
                }
            }
        }
        return response
    }
}