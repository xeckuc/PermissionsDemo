package com.examples.permissionsdemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.security.Permission

class MainActivity : AppCompatActivity() {

    private val RECORD_AUDIO_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindUI()
    }

    private fun bindUI() {

        btnSomeAction.setOnClickListener {

            //using manager

            if (PermissionsManager.checkPermissions(
                    this,
                    Manifest.permission.RECORD_AUDIO,
                    getString(R.string.permission_dialog_title),
                    getString(R.string.permission_dialog_message)
                )
            )
            {
                doYourMagic()
            }
        }
    }

    private fun showSettingsNavDialog() {

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.settings_dialog_title))
            .setMessage(getString(R.string.settings_dialog_message))
            .setPositiveButton(
                getString(R.string.settings)) { dialog, _ ->

                startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, _ ->

                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode) {

            RECORD_AUDIO_PERMISSION_CODE -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    doYourMagic()
                }
                else {

                    //Permission was denied -> dont do anything
                    //Toast.makeText(this@MainActivity, R.string.permission_denied, Toast.LENGTH_SHORT).show()

                    if (!shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO))
                        showSettingsNavDialog()
                    else
                        Toast.makeText(this@MainActivity, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                }
            }

            else -> {
                //do nothing
            }
        }
    }

    private fun doYourMagic() {
        Toast.makeText(this, getString(R.string.doing_magic), Toast.LENGTH_SHORT).show()
    }

}
