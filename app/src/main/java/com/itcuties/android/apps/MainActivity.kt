package com.itcuties.android.apps

import java.util.ArrayList

import android.app.ListActivity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.itcuties.android.apps.data.SMSData

class MainActivity : ListActivity() {

    internal var smsList: MutableList<SMSData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestCode = 123
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf("android.permission.READ_SMS"), requestCode)

        if (ContextCompat.checkSelfPermission(baseContext, "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            val uri = Uri.parse("content://sms/inbox")
            val c = contentResolver.query(uri, null, null, null, null)
            startManagingCursor(c)

            // Read the sms data and store it in the list
            c?.let {
                if (it.moveToFirst()) {
                    for (i in 0 until it.count) {
                        val sms = SMSData()
                        sms.body = it.getString(it.getColumnIndexOrThrow("body"))
                        sms.number = it.getString(it.getColumnIndexOrThrow("address"))
                        smsList.add(sms)

                        it.moveToNext()
                    }
                }
                it.close()
            }
            // Set smsList in the ListAdapter
            listAdapter = ListAdapter(this, smsList)
        }

    }

}
