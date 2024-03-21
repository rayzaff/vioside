package com.vioside.foundation.network.respositories

import com.vioside.foundation.network.models.Resource
import com.vioside.foundation.network.models.Resource.Companion.success
import com.vioside.foundation.network.models.Resource.Companion.error
import org.koin.core.KoinComponent
import retrofit2.Response

open class BaseRepository: KoinComponent {

    suspend fun <ResponseT : Any> safeApiCall(
        call: suspend () -> Response<ResponseT>
    ): Resource<ResponseT> {
        return safeApiResult(call)
    }

    private suspend fun <ResponseT: Any> safeApiResult(call: suspend ()-> Response<ResponseT>) : Resource<ResponseT> {
        val response = call.invoke()
        return if(response.isSuccessful) success(response.body(), response.code())
        else {
            var errorBody: String? = response.errorBody()?.string()
            if(errorBody == null) errorBody = "Unknown error"
            error(Throwable("$errorBody"), response.body(), response.code())
        }
    }
}