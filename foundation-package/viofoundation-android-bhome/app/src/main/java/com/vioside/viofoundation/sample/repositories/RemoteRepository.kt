package com.vioside.viofoundation.sample.repositories

import com.vioside.foundation.network.respositories.BaseRepository
import com.vioside.foundation.network.services.ApiEndpointService
import com.vioside.viofoundation.sample.models.SampleResponse
import com.vioside.viofoundation.sample.services.ApiPaths
import com.vioside.viofoundation.sample.services.AuthorizedApi
import org.koin.core.KoinComponent
import org.koin.core.inject

open class RemoteRepository(
    private val api: AuthorizedApi
): BaseRepository(), KoinComponent {

    private val endPointService: ApiEndpointService by inject()

    suspend fun sampleAsync(): SampleResponse? {

        val response = safeApiCall(
            call = {
                api.sampleAsync(
                    endPointService.endpointUrl(ApiPaths.SAMPLE)
                ).await()
            }
        )
        response.error?.let {
            throw it
        }
        return response.data
    }
}