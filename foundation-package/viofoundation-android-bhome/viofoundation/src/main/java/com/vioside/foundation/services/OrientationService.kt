package com.vioside.foundation.services

import android.content.Context
import android.content.res.Configuration

interface OrientationService {
    val isDevicePortrait: Boolean
}

class AndroidOrientationService(
    val context: Context
): OrientationService {

    override val isDevicePortrait: Boolean = context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

}