package com.vioside.foundation.network.respositories

import com.vioside.foundation.network.services.FileService
import com.vioside.foundation.network.services.StreamingApi
import org.koin.core.KoinComponent
import org.koin.core.inject

class FileRepository(
    private val api: StreamingApi
): BaseRepository(), KoinComponent {

    private val fileService: FileService by inject()

    suspend fun downloadFile(url: String): Boolean {

        val response = safeApiCall(
            call = {
                api.downloadFileAsync(url).await()
            }
        )

        return response.data?.run {
            val fileName = url.substring(url.lastIndexOf('/') + 1)
            fileService.writeResponseBodyToDisk(fileName, this)
        } ?: false

    }

}