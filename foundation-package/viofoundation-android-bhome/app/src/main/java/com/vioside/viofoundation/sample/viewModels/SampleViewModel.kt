package com.vioside.viofoundation.sample.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vioside.foundation.base.BaseViewModel
import com.vioside.foundation.network.respositories.AuthenticationRepository
import com.vioside.foundation.network.services.AuthenticationTokenService
import com.vioside.viofoundation.sample.models.AuthenticationResponse
import com.vioside.viofoundation.sample.repositories.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleViewModel(
    private val authenticationRepository: AuthenticationRepository<AuthenticationResponse, AuthenticationResponse>,
    private val remoteRepository: RemoteRepository,
    private val tokenService: AuthenticationTokenService,
    private val dispatchers: CoroutineDispatcher = Dispatchers.Main
): BaseViewModel() {

    val result = MutableLiveData("ready")

    fun login(username: String, password: String) {
        viewModelScope.launch(dispatchers) {
            try {
                authenticationRepository.authenticate(username, password)
            } catch (e: Exception) {
                result.value = e.toString()
            }
            val token = tokenService.token
            val refreshToken = tokenService.refreshToken
            result.value = "token: $token, refresh: $refreshToken"
        }
    }

    fun replaceToken() {
        tokenService.token = "abc"
        result.value = "replaced"
    }

    fun logout() {
        viewModelScope.launch(dispatchers) {
            try {
                authenticationRepository.logout()
                val token = tokenService.token
                val refreshToken = tokenService.refreshToken
                result.value = "token: $token, refresh: $refreshToken"
            } catch (e: Exception) {
                result.value = e.toString()
            }
        }
    }

    fun test() {
        viewModelScope.launch(dispatchers) {
            try {
                result.value = remoteRepository.sampleAsync()?.id
            } catch (e: Throwable) {
                result.value = e.toString()
            }
        }
    }

}