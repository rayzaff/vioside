package com.vioside.viofoundation.sample.services

import com.vioside.viofoundation.sample.models.SampleResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Declares APIs that requires authentication to be processed
 */
interface AuthorizedApi {

    @GET
    fun sampleAsync(
        @Url url: String
    ): Deferred<Response<SampleResponse>>

}