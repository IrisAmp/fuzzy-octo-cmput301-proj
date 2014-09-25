/*
   Copyright 2014 Brandon Yue

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package ca.yuey.noteprime301;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewNoteActivity
extends Activity
implements OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
	private final Intent intent = getIntent();
	
	private TextView datePickerTV;
	private TextView timePickerTV;
	private Button actionDone;
	private Button actionCancel;

	final Calendar calendar = Calendar.getInstance(Locale.CANADA);
	final FragmentManager fm = getFragmentManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Some housekeeping to fix UI consistency
		calendar.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), 
				calendar.get(Calendar.HOUR_OF_DAY) + 1, 
				0);
		
		// Get the views we need.
		this.datePickerTV = (TextView) this.findViewById(R.id.newNoteDueDateEntry);
		this.timePickerTV = (TextView) this.findViewById(R.id.newNoteDueTimeEntry);
		this.actionDone = (Button) this.findViewById(R.id.newNoteActionAccept);
		this.actionCancel = (Button) this.findViewById(R.id.newNoteActionCancel);
        final DatePickerDialog datePickerDialog = 
        		DatePickerDialog.newInstance(
        				this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        final TimePickerDialog timePickerDialog = 
        		TimePickerDialog.newInstance(
        				this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);

		// Attach an onClick listener to both of the Date/Time views.
		this.datePickerTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				datePickerDialog.show(NewNoteActivity.this.fm, "");
			}
		});
		this.timePickerTV.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				timePickerDialog.setStartTime(
						calendar.get(Calendar.HOUR_OF_DAY), 
						calendar.get(Calendar.MINUTE));
				timePickerDialog.show(NewNoteActivity.this.fm, "");
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar()
	{
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_note, menu);
		return true;
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute)
	{
		DateFormat sf = new SimpleDateFormat("h:mm a", Locale.CANADA);
		calendar.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), 
				hourOfDay, 
				minute);
		this.timePickerTV.setText(sf.format(calendar.getTime()));
	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day)
	{
		DateFormat sf = new SimpleDateFormat("MMMM dd, yyyy", Locale.CANADA);
		calendar.set(year, month, day);
		this.datePickerTV.setText(sf.format(calendar.getTime()));
	}
}
