package com.vioside.viofoundation.sample.services

import com.vioside.foundation.network.models.StatusResponse
import com.vioside.viofoundation.sample.models.AuthenticationResponse
import com.vioside.viofoundation.sample.models.LoginCredentials
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Declares APIs that requires authentication to be processed related to the authorization
 */
interface AuthenticationOpenApi {

    @POST
    fun loginAsync(
        @Url url: String,
        @Body request: LoginCredentials
    ): Deferred<Response<AuthenticationResponse>>

}

/**
 * Declares APIs that do not require authentication to be processed related to the authorization
 */
interface AuthenticationAuthorizedApi {

    @POST
    fun validateAsync(
        @Url url: String
    ): Deferred<Response<StatusResponse>>

    @POST
    fun logoutAsync(
        @Url url: String
    ): Deferred<Response<ResponseBody>>

    @POST
    fun refreshTokenAsync(
        @Url url: String,
        @Body request: AuthenticationResponse
    ): Deferred<Response<AuthenticationResponse>>

}