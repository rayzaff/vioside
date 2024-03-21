package com.vioside.viofoundation.sample.repositories

import com.vioside.foundation.network.models.AuthenticationResponseInterface
import com.vioside.foundation.network.models.StatusResponse
import com.vioside.viofoundation.sample.services.ApiPaths
import com.vioside.viofoundation.sample.services.AuthenticationAuthorizedApi
import com.vioside.viofoundation.sample.services.AuthenticationOpenApi
import com.vioside.foundation.network.respositories.AuthenticationDataSource
import com.vioside.foundation.network.services.ApiEndpointService
import com.vioside.viofoundation.sample.models.AuthenticationResponse
import com.vioside.viofoundation.sample.models.LoginCredentials
import kotlinx.coroutines.Deferred
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Response

class AuthenticationDataSourceImpl(
    private val authenticationOpenApi: AuthenticationOpenApi,
    private val authenticationAuthorizedApi: AuthenticationAuthorizedApi
): KoinComponent, AuthenticationDataSource<AuthenticationResponse, AuthenticationResponse> {

    private val endPointService: ApiEndpointService by inject()

    /**
     * Performs Login
     */
    override fun authenticateAsync(
        username: String,
        password: String
    ) = authenticationOpenApi.loginAsync(
        endPointService.endpointUrl(ApiPaths.LOGIN),
        LoginCredentials(username, password)
    )

    override fun validationAsync() = authenticationAuthorizedApi.validateAsync(
        endPointService.endpointUrl(ApiPaths.VALIDATE)
    )

    override fun logoutAsync() = authenticationAuthorizedApi.logoutAsync(
        endPointService.endpointUrl(ApiPaths.LOGOUT)
    )

    override fun refreshTokenAsync(
        token: String,
        refreshToken: String,
        userId: String?
    ) = authenticationAuthorizedApi.refreshTokenAsync(
        endPointService.endpointUrl(ApiPaths.REFRESH_TOKEN),
        AuthenticationResponse(token, refreshToken, userId)
    )

}