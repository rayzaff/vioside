package com.vioside.foundation.network.helpers

import com.vioside.foundation.network.models.AuthenticationResponseInterface
import com.vioside.foundation.network.services.AuthenticationTokenService
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthorizationHeaderInterceptor<AuthenticationResponseT: AuthenticationResponseInterface, RefreshResponseT: AuthenticationResponseInterface>(
    private val authorized: Boolean
) : Interceptor, KoinComponent {

    private val authenticationTokenService: AuthenticationTokenService by inject()

    override fun intercept(chain: Interceptor.Chain): Response {

        val httpClient = chain.request()
            .newBuilder()
            .header("Accept", "application/json")
            .cacheControl(CacheControl.FORCE_NETWORK)

        // If authorized add the authorization token
        if (authorized) {
            val token = authenticationTokenService.token
            httpClient.header("Authorization", "Bearer $token")
        }

        return chain.proceed(httpClient.build())
    }

}
