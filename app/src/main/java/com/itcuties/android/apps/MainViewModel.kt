package com.itcuties.android.apps

import android.database.Cursor
import android.os.Build
import android.provider.Telephony
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itcuties.android.apps.data.SMSData

class MainViewModel : ViewModel() {

    var smsListLD: MutableLiveData<List<SMSData>> = MutableLiveData()
    var smsList: MutableList<SMSData> = mutableListOf()

    fun getSmsList(c: Cursor?) {
        smsList.clear()
        // Read the sms data and store it in the list
        try {
            c?.let {
                if (it.moveToFirst()) {
                    for (i in 0 until it.count) {
                        val sms = SMSData()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                            sms.body = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.BODY))
                            sms.number = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                            sms.subject = it.getString(it.getColumnIndexOrThrow(Telephony.Sms.SUBJECT))
                            smsList.add(sms)
                            it.moveToNext()
                        }
                    }
                    smsListLD.value = smsList
                }

                it.close()
            }
        } catch (ignore: Exception) {
        }
    }
}