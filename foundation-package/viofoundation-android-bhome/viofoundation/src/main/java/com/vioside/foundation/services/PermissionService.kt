package com.vioside.foundation.services

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

interface PermissionService {

    /**
     * This is used to provide a permission request code for when the app is requesting a permission
     * from the user. This must be extented to include addition permissions if more than one are
     * being requested at the same time
     */
    fun permissionRequestCode(permission: String): Int

    /**
     * Checks if the user has granted a permission. If granted, perform the action provided in the
     * higher-order function. If not granted, request permission from the user. The function must then
     * implement [Fragment.onRequestPermissionsResult] and check for permission code [permissionRequestCode].
     * If granted at that point, make sure to call the same higher-order-function passed here
     * @param permission the permission to check if is available and request from the device
     * @param permissions the list of permissions to request when the permission above is not available (optional)
     * @param actionToPerform the action to perform when the permission is requested
     */
    fun checkPermission(permission: String,
                        permissions: Array<String>? = null,
                        actionToPerform: ()->Unit)

    /**
     * Returns whether a permission was granted
     */
    fun permissionGranted(permission: String): Boolean

}

open class PermissionServiceImpl(

    /**
     * The fragment performing the permission request and that will receive the response
     * after permission is granted
     */
    open var fragment: Fragment

): PermissionService {

    override fun permissionRequestCode(permission: String): Int {
        var requestCode = 100
        when(permission) {
            Manifest.permission.CALL_PHONE -> requestCode = 101
        }
        return requestCode
    }

    override fun checkPermission(permission: String,
                                 permissions: Array<String>?,
                                 actionToPerform: ()->Unit) {
        fragment.context?.let {
            if (ContextCompat.checkSelfPermission(it, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                var permissionArray = arrayOf(permission)
                if (permissions != null) {
                    permissionArray = permissions
                }
                fragment.requestPermissions(permissionArray, permissionRequestCode(permission))
            } else {
                actionToPerform.invoke()
            }
        }
    }

    override fun permissionGranted(permission: String): Boolean {
        fragment.context?.let {
            return (ContextCompat.checkSelfPermission(
                it,
                permission
            ) == PackageManager.PERMISSION_GRANTED)
        }
        return false
    }

}