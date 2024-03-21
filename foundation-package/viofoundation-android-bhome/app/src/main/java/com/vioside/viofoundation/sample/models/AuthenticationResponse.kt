package com.vioside.viofoundation.sample.models

import com.google.gson.annotations.SerializedName
import com.vioside.foundation.network.models.AuthenticationResponseInterface

data class AuthenticationResponse(

    @SerializedName("token")
    override val token: String,

    @SerializedName("refreshToken")
    override val refreshToken: String?,

    @SerializedName("user_id")
    override val userId: String?

): AuthenticationResponseInterface