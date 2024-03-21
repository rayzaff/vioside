package com.vioside.foundation.network.models

data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?, val code: Int) {
    companion object {
        fun <T> success(data: T?, code: Int): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null,
                code
            )
        }

        fun <T> error(error: Throwable, data: T?, code: Int): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error,
                code
            )
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null,
                0
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}