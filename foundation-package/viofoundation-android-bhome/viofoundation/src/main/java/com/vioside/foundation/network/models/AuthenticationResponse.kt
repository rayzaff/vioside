package com.vioside.foundation.network.models

interface AuthenticationResponseInterface {
    val token: String
    val refreshToken: String?
    val userId: String?
}