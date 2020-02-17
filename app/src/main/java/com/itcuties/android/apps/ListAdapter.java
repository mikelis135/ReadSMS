package com.itcuties.android.apps;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.itcuties.android.apps.data.SMSData;

public class ListAdapter extends ArrayAdapter<SMSData> {

	// List context
    private final Context context;
    // List values
    private final List<SMSData> smsList;
	
	ListAdapter(Context context, List<SMSData> smsList) {
		super(context, R.layout.activity_main, smsList);
		this.context = context;
		this.smsList = smsList;
	}

	@NonNull
	@Override
	public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = new View(context);
		if (inflater != null){
			rowView = inflater.inflate(R.layout.activity_main, parent, false);
			TextView senderNumber = rowView.findViewById(R.id.smsNumberText);
			senderNumber.setText(smsList.get(position).getBody());
			Toast.makeText(getContext(), smsList.get(position).getBody(), Toast.LENGTH_SHORT).show();

			senderNumber.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Toast.makeText(getContext(), smsList.get(position).getBody(), Toast.LENGTH_SHORT).show();
				}
			});
		}

		return rowView;
	}
	
}
