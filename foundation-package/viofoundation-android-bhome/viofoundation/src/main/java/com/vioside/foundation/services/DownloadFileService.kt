package com.vioside.foundation.services

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment


interface DownloadFileService {

    fun downloadFile(
        url: String,
        wifiOnly: Boolean,
        fileName: String,
        onCompletion: () -> Unit
    )

}

interface DownloadFileServiceStringDataSource {
    val title: String
    val description: String
}

class DownloadFileServiceImpl(
    private val context: Context,
    private val stringDataSource: DownloadFileServiceStringDataSource
): DownloadFileService {


    override fun downloadFile(
        url: String,
        wifiOnly: Boolean,
        fileName: String,
        onCompletion: () -> Unit
    ) {
        // register for a completion notification from the download manager and invokes `onCompletion`
        context.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(ctxt: Context, intent: Intent) {
                onCompletion.invoke()
            }
        }, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        //setup the download manager with the parameters for download
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            if (wifiOnly) setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            setTitle(stringDataSource.title)
            setDescription(stringDataSource.description)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )
        }

        //start the download
        (context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).run {
            enqueue(request)
        }
    }

}