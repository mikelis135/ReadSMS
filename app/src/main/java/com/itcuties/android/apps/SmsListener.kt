package com.itcuties.android.apps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import java.lang.Exception

class SmsListener : BroadcastReceiver() {

    private val preferences: SharedPreferences? = null

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras           //---get the SMS message passed in---
            var msgs: Array<SmsMessage?>

            var msg_from: String?
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    val pdus = bundle.get("pdus") as Array<Any>?

                    pdus?.let {
                        msgs = arrayOfNulls(it.size)
                        for (i in msgs.indices) {
                            msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                            msg_from = msgs[i]?.originatingAddress
                            val msgBody = msgs[i]?.messageBody
                            Log.d("okh", "$msgBody incoming")
                            Toast.makeText(context, msgBody, Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {

                }


            }
        }
    }
}
