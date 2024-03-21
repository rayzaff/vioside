package com.vioside.foundation.network.respositories

import com.vioside.foundation.network.models.AuthenticationResponseInterface
import com.vioside.foundation.network.services.AuthenticationTokenService
import org.koin.core.inject

open class AuthenticationRepository<AuthenticationResponseT: AuthenticationResponseInterface, RefreshResponseT: AuthenticationResponseInterface>(
    private val dataSource: AuthenticationDataSource<AuthenticationResponseT, RefreshResponseT>
) : BaseRepository() {

    private val authenticationTokenStorage: AuthenticationTokenService by inject()

    val userId: String?
        get() {
            return authenticationTokenStorage.userId
        }

    val userName: String?
        get() {
            return authenticationTokenStorage.userName
        }

    /**
     * Performs an authentication
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun authenticate(
        username: String,
        password: String
    ): Boolean? {
        val response = safeApiCall(
            call = {
                dataSource.authenticateAsync(username, password).await()
            }
        )

        response.data?.let { data ->
            if(data.token.isEmpty()) {
                return false
            }
            authenticationTokenStorage.token = data.token
            data.refreshToken?.let {
                authenticationTokenStorage.refreshToken = it
            }
            data.userId?.let {
                authenticationTokenStorage.userId = it
            }
            authenticationTokenStorage.userName = username
            return true
        }
        return false
    }

    suspend fun validate(): Boolean {
        val response = safeApiCall(
            call = {
                dataSource.validationAsync().await()
            }
        )

        return response.code == 200
    }

    suspend fun logout(): Boolean {
        val response = safeApiCall(
            call = {
                dataSource.logoutAsync().await()
            }
        )

        if(response.code == 200) {
            authenticationTokenStorage.token = ""
            authenticationTokenStorage.refreshToken = ""
            authenticationTokenStorage.userId = ""
            authenticationTokenStorage.userName = ""
        }

        return response.code == 200
    }

    /**
     * Performs an authentication
     */
    @Suppress("UNCHECKED_CAST")
    suspend fun refreshToken(token: String, refreshToken: String, userId: String?): Boolean {
        val response = safeApiCall(
            call = {
                dataSource.refreshTokenAsync(token, refreshToken, userId).await()
            }
        )

        response.data?.let { data ->
            authenticationTokenStorage.token = data.token
            authenticationTokenStorage.refreshToken = data.refreshToken
            return true
        }
        return false
    }

}
