package com.vioside.foundation.network.respositories

import com.vioside.foundation.network.models.AuthenticationResponseInterface
import com.vioside.foundation.network.models.StatusResponse
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response

interface AuthenticationDataSource<AuthenticationResponseT: AuthenticationResponseInterface, RefreshTokenResponseT: AuthenticationResponseInterface> {

    /**
     * Performs Login
     */
    fun authenticateAsync(username: String, password: String): Deferred<Response<AuthenticationResponseT>>

    /**
     * Performs token validation
     */
    fun validationAsync(): Deferred<Response<StatusResponse>>

    /**
     * Performs logout operation
     */
    fun logoutAsync(): Deferred<Response<ResponseBody>>

    /**
     * Performs Token Refresh
     */
    fun refreshTokenAsync(token: String, refreshToken: String, userId: String?): Deferred<Response<RefreshTokenResponseT>>

}