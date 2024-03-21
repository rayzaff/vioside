package com.vioside.foundation.extensions

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


/**
 * This is used to provide a permission request code for when the app is requesting a permission
 * from the user. This must be extented to include addition permissions if more than one are
 * being requested at the same time
 */
fun Fragment.permissionRequestCode(): Int {
    return 100
}

/**
 * Checks if the user has granted a permission. If granted, perform the action provided in the
 * higher-order function. If not granted, request permission from the user. The function must then
 * implement [Fragment.onRequestPermissionsResult] and check for permission code [permissionRequestCode].
 * If granted at that point, make sure to call the same higher-order-function passed here
 * @param permission the permission to check if is available and request from the device
 * @param permissions the list of permissions to request when the permission above is not available (optional)
 * @param actionToPerform the action to perform when the permission is requested
 */
fun Fragment.checkPermission(permission: String,
                             permissions: Array<String>? = null,
                             actionToPerform: ()->Unit) {
    context?.let {
        if (ContextCompat.checkSelfPermission(it, permission )
            != PackageManager.PERMISSION_GRANTED
        ) {
            var permissionArray = arrayOf(permission)
            if(permissions != null){
                permissionArray = permissions
            }
            requestPermissions(permissionArray, permissionRequestCode())
        } else {
            actionToPerform.invoke()
        }
    }

}
