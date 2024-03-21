package com.vioside.foundation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(uri: String?) {

    this.post {

        val myOptions = RequestOptions()
            .override(this.width, this.height)

        Glide
            .with(this.context)
            .load(uri)
            .apply(myOptions)
            .into(this)
    }

}