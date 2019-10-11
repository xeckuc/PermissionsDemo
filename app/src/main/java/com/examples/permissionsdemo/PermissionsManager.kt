package com.examples.permissionsdemo

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog

class PermissionsManager {

    companion object {

        val RECORD_AUDIO_PERMISSION_CODE = 1


        fun checkPermissions(
                activity: Activity,
                permission: String,
                rationaleDialogTitle: String = "Default Title",
                rationaleDialogMessage: String = "Default Message"
        ) : Boolean
        {
            return if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
                true
            else
                requestPermission(activity, permission, rationaleDialogTitle, rationaleDialogMessage)
        }

        private fun requestPermission(
                activity: Activity,
                permission: String,
                rationaleDialogTitle: String,
                rationaleDialogMessage: String
        ) : Boolean {

            if (activity.shouldShowRequestPermissionRationale(permission)) {

                showRationaleDialog(
                    activity,
                    permission,
                    rationaleDialogTitle,
                    rationaleDialogMessage
                )
            }
            else {
                activity.requestPermissions(
                    arrayOf(permission), RECORD_AUDIO_PERMISSION_CODE
                )
            }

            return false
        }

        private fun showRationaleDialog(
                activity: Activity,
                permission: String,
                rationaleDialogTitle: String = "Default Title",
                rationaleDialogMessage: String = "Default Message"
            )
        {

            AlertDialog.Builder(activity)
                .setTitle(rationaleDialogTitle)
                .setMessage(rationaleDialogMessage)
                .setPositiveButton(
                    activity.getString(R.string.dialog_ok)) { _, _ ->

                    activity.requestPermissions(
                        arrayOf(permission),
                        RECORD_AUDIO_PERMISSION_CODE
                    )
                }
                .setNegativeButton(activity.getString(R.string.dialog_cancel)) { dialog, _ ->

                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}