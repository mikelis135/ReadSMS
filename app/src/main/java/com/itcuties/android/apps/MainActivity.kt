package com.itcuties.android.apps

import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val requestCode = 123
    private var cursor: Cursor? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf("android.permission.READ_SMS"), requestCode)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (ContextCompat.checkSelfPermission(baseContext, "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            val uri = Uri.parse("content://sms/inbox")
            try {
                cursor = contentResolver.query(uri, null, null, null, null)
                cursor?.let {
                    startManagingCursor(cursor)
                    mainViewModel.getSmsList(cursor)
                }
            } catch (ignore: Exception) {

            } finally {
                cursor?.close()
            }

            mainViewModel.smsListLD.observe(this, Observer {

                it?.let {
                    cursor?.close()
                    list.adapter = ListAdapter(this, it)
                }
            })

        }

    }

}
