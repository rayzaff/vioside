package com.vioside.foundation.services

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.aslp.komunikapp.foundation.R

interface ShareService {
    fun shareSentence(text: String)
}

class ShareServiceImpl (
    private val context: Context
): ShareService{

    override fun shareSentence(text: String){
        if (text.isNotEmpty()){
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }else{
            Toast.makeText(context, context.resources.getString(R.string.nothing_shared_message), Toast.LENGTH_LONG).show()
        }
    }

}