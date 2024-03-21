package com.vioside.foundation.network.services

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface StreamingApi {

    @GET
    @Streaming
    fun downloadFileAsync(
        @Url url: String
    ): Deferred<Response<ResponseBody>>

}