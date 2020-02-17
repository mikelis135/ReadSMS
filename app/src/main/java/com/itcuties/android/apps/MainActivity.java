package com.itcuties.android.apps;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.itcuties.android.apps.data.SMSData;

public class MainActivity extends ListActivity {

    List<SMSData> smsList = new ArrayList<SMSData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            Uri uri = Uri.parse("content://sms/inbox");
            Cursor c = getContentResolver().query(uri, null, null, null, null);
            startManagingCursor(c);

            // Read the sms data and store it in the list
            if (c != null) {
                if (c.moveToFirst()) {
                    for (int i = 0; i < c.getCount(); i++) {
                        SMSData sms = new SMSData();
                        sms.setBody(c.getString(c.getColumnIndexOrThrow("body")));
                        sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")));
                        smsList.add(sms);

                        c.moveToNext();
                    }
                }
                c.close();
            }
            // Set smsList in the ListAdapter
            setListAdapter(new ListAdapter(this, smsList));
        }

    }

}
