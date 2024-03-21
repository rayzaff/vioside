package com.vioside.foundation.services

import java.io.File
import java.io.InputStream

data class FileResult (
    val byteArray: ByteArray,
    val fileName: String
)

interface ReadFileService{
    fun readFile(file: File): FileResult
}

class ReadFileServiceImpl: ReadFileService {
    override fun readFile(file: File): FileResult {

        val filename = "image."+file.extension
        val ins = file.inputStream()
        val byteArray = ins.readBytes()

        return FileResult(byteArray, filename)
    }

}