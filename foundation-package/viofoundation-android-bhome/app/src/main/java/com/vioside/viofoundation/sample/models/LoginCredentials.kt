package com.vioside.viofoundation.sample.models

import com.google.gson.annotations.SerializedName

data class LoginCredentials(

    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String

)

