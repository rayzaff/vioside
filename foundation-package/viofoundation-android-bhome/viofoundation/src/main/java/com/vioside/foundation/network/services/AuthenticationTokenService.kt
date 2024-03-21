package com.vioside.foundation.network.services

import com.vioside.foundation.services.UserStorage
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthenticationTokenService: KoinComponent {

    private val userStorage: UserStorage by inject()

    companion object{
        const val USER_TOKEN_KEY = "UserToken"
        const val USER_REFRESH_TOKEN_KEY = "UserRefreshToken"
        const val USER_ID = "UserID"
        const val USERNAME = "UserName"
    }

    var token: String?
        get() {
            return userStorage.getString(USER_TOKEN_KEY)
        }
        set(value) {
            userStorage.save(USER_TOKEN_KEY, value as String)
        }

    var refreshToken: String?
        get() {
            return userStorage.getString(USER_REFRESH_TOKEN_KEY)
        }
        set(value) {
            userStorage.save(USER_REFRESH_TOKEN_KEY, value as String)
        }

    var userId: String?
        get() {
            return userStorage.getString(USER_ID)
        }
        set(value) {
            userStorage.save(USER_ID, value as String)
        }

    var userName: String?
        get() {
            return userStorage.getString(USERNAME)
        }
        set(value) {
            userStorage.save(USERNAME, value as String)
        }
}