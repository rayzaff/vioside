package com.vioside.foundation.services

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.aslp.komunikapp.foundation.R

interface ClipboardService {
    fun copyToClipboard(text: String)
}

class ClipboardServiceImpl (
    private val context: Context
): ClipboardService{
    override fun copyToClipboard(text: String){
        if (text.isNotEmpty()){
            val clipboardManager = ContextCompat.getSystemService(
                context,
                ClipboardManager::class.java
            ) as ClipboardManager
            val clipData = ClipData.newPlainText(context.resources.getString(R.string.copy_label), text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(context, context.resources.getString(R.string.text_copied_message), Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(context, context.resources.getString(R.string.nothing_copied_message), Toast.LENGTH_LONG).show()
        }
    }

}