package com.vioside.foundation.extensions

import android.content.Context
import android.text.Html
import java.io.File

fun String.Companion.getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun String.fromHtml(): String {
    return Html.fromHtml(this).toString()
}

fun String?.sanitizeColor(fallback: String): String {
    if(this == null) return fallback
    var sanitizedColor = this
    if(length == 6) sanitizedColor = "#$this"
    if(sanitizedColor.length != 7) return fallback
    return sanitizedColor
}

fun String.localFileURL(context: Context): String {
    val file = File(context.getExternalFilesDir(null).toString() + File.separator + this)
    return file.absolutePath
}

fun String.lastComponentOfURL(): String {
    return this.substring(this.lastIndexOf('/') + 1)
}