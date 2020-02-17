package com.itcuties.android.apps

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.itcuties.android.apps.data.SMSData

class MainActivity : AppCompatActivity() {

    var smsList: MutableList<SMSData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val list = findViewById<ListView>(R.id.list)
//        val mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val requestCode = 123
        ActivityCompat.requestPermissions(this, arrayOf("android.permission.READ_SMS"), requestCode)

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

            list.adapter = ListAdapter(this, smsList)

        }

    }

}
