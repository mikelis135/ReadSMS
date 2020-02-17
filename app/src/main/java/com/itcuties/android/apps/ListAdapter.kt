package com.itcuties.android.apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.itcuties.android.apps.data.SMSData

class ListAdapter constructor(context: Context,
                                       private val smsList: List<SMSData>) : ArrayAdapter<SMSData>(context, R.layout.activity_main, smsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val rowView: View
        rowView = inflater.inflate(R.layout.activity_item, parent, false)
        val senderNumber = rowView.findViewById<TextView>(R.id.smsNumberText)
        senderNumber.text = smsList[position].body

        senderNumber.setOnClickListener {
            Toast.makeText(context, smsList[position].subject, Toast.LENGTH_SHORT).show()
        }

        return rowView
    }

}
