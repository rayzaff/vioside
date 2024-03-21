package com.vioside.foundation.network.services

import android.content.Context
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class FileService: KoinComponent {

    private val context: Context by inject()

    fun writeResponseBodyToDisk(fileName: String, body: ResponseBody): Boolean {
        val file = File(context.getExternalFilesDir(null).toString() + File.separator + fileName)
        try {

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(4096)
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(file)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                }
                return true
            } catch (e: Exception) {
                println(e.toString())
                return false
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: Exception) {
            println(e.toString())
            return false
        }
    }

}