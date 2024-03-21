package com.vioside.foundation.models

data class ErrorMessage(
    var title: String?,
    var message: String?
){
    fun humanReadable():ErrorMessage{
        if (this.message == "i/o failure"){
            this.title = "No internet connection"
            this.message = "Make sure you have an internet connection"
        }
        return this
    }
}