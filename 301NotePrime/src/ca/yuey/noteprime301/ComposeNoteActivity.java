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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import ca.yuey.models.Note;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

public class ComposeNoteActivity
extends Activity
implements OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
	public static final String KEY_COMPLETED_NOTE_ITEM = "ca.yuey.noteprime301.COMPLETED_NOTE"; 
	
	private Intent intent;
	
	private DatePickerDialog datePickerDialog;
	private TimePickerDialog timePickerDialog;
	
	private TextView datePickerTV;
	private TextView timePickerTV;
	private EditText titleEntry;
	private EditText detailEntry;
	private ImageButton actionClearDate;
	private Button actionDone;
	private Button actionCancel;
	
	private boolean timeSet = false;

	final Calendar calendar = Calendar.getInstance(Locale.CANADA);
	final FragmentManager fm = getFragmentManager();
	
	/*========================================================================
	 * Android Lifecycle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_note);

		// Some housekeeping to fix UI consistency
		calendar.set(
				calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH), 
				calendar.get(Calendar.DAY_OF_MONTH), 
				calendar.get(Calendar.HOUR_OF_DAY) + 1, 
				0);
		
		this.getActivityResources();

		this.unpackIntent();
        
        this.attachListeners();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	}

	/*========================================================================
	 * onX Callbacks
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_note, menu);
		return true;
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute)
	{
		this.timeSet = true;
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
		this.timeSet = true;
		DateFormat sf = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
		calendar.set(year, month, day);
		this.datePickerTV.setText(sf.format(calendar.getTime()));
	}
	
	/*========================================================================
	 * Auxiliary
	 */
	private void getActivityResources()
	{
		// Set the action bar as up enabled so we can go home.
		this.getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// Get the intent data
		this.intent = getIntent();
		
		// Get the views we need.
		this.datePickerTV = (TextView) this.findViewById(R.id.newNoteDueDateEntry);
		this.timePickerTV = (TextView) this.findViewById(R.id.newNoteDueTimeEntry);
		this.titleEntry = (EditText) this.findViewById(R.id.newNoteTitleEntry);
		this.detailEntry = (EditText) this.findViewById(R.id.newNoteDetailEntry);
		this.actionClearDate = (ImageButton) this.findViewById(R.id.newNoteActionClearDate);
		this.actionDone = (Button) this.findViewById(R.id.newNoteActionAccept);
		this.actionCancel = (Button) this.findViewById(R.id.newNoteActionCancel);
		
		// Instantiate the date/time pickers
        this.datePickerDialog = 
        		DatePickerDialog.newInstance(
        				this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        this.timePickerDialog = 
        		TimePickerDialog.newInstance(
        				this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false, false);
	}
	private void unpackIntent()
	{
        // If we have data from our intent, insert it into the entry fields.
		Bundle extras = this.intent.getExtras();
		
        Note data = (Note) extras.get(BaseActivity.KEY_NOTE_SERIALIZABLE);
        
        ArrayList<String> strs = data.getInfo();
        String title = strs.get(0);
        String detail = strs.get(1);
                
        if (title != null)
        	this.titleEntry.setText(title);
        if (detail != null)
        	this.detailEntry.setText(detail);

        if (data.getDue() != null)
        {
        	this.calendar.setTime(data.getDue());
        	
    		DateFormat sf = new SimpleDateFormat("h:mm a", Locale.CANADA);
    		this.timePickerTV.setText(sf.format(calendar.getTime()));

    		sf = new SimpleDateFormat("MMM dd, yyyy", Locale.CANADA);
    		this.datePickerTV.setText(sf.format(calendar.getTime()));
        }
	}
	private void attachListeners()
	{
		// Attach an onClick listener to both of the Date/Time views.
		this.datePickerTV.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				datePickerDialog.show(ComposeNoteActivity.this.fm, "");
			}
		});
		this.timePickerTV.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				timePickerDialog.setStartTime(
						calendar.get(Calendar.HOUR_OF_DAY), 
						calendar.get(Calendar.MINUTE));
				timePickerDialog.show(ComposeNoteActivity.this.fm, "");
			}
		});
        // Attach an onEditorActionListener to the EditText
        this.titleEntry.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
			@Override
			public boolean onEditorAction(TextView view, int actionID, KeyEvent event)
			{
				if (actionID == EditorInfo.IME_ACTION_NEXT)
				{
					ComposeNoteActivity.this.detailEntry.requestFocus();
					return true;
				}
				return false;
			}
        });
        // And an onClickListener to the buttons we need.
        this.actionClearDate.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				ComposeNoteActivity.this.timeSet = false;
				ComposeNoteActivity.this.datePickerTV.setText(null);
				ComposeNoteActivity.this.timePickerTV.setText(null);
			}
		});
        this.actionDone.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View v)
			{
				if (ComposeNoteActivity.this.titleEntry.getText().toString().isEmpty())
				{
					ComposeNoteActivity.this.toastRequiredField();
					return;
				}
				Intent intent = new Intent();
				intent.putExtra(
						ComposeNoteActivity.KEY_COMPLETED_NOTE_ITEM, packNote());
				ComposeNoteActivity.this.setResult(Activity.RESULT_OK, intent);
				ComposeNoteActivity.this.finish();
			}
		});
        this.actionCancel.setOnClickListener(new View.OnClickListener()
        {
        	@Override
			public void onClick(View v)
        	{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				ComposeNoteActivity.this.setResult(Activity.RESULT_CANCELED, intent);
				ComposeNoteActivity.this.finish();
			}
		});
	}
	private Note packNote()
	{
		if (this.titleEntry.getText().toString().isEmpty())
			throw new IllegalArgumentException("Title was empty");

		String title = this.titleEntry.getText().toString();
		String detail = this.detailEntry.getText().toString();
		Date due = null;
		
		if (this.timeSet)
			due = this.calendar.getTime();
		if (detail.isEmpty())
			detail = null;
		
		return new Note(
				title,
				detail,
				due);
	}
	private void unpackNote()
	{
		
	}
	protected void toastRequiredField()
	{
		Toast.makeText(this.getBaseContext(), "You need to enter a title first!", Toast.LENGTH_SHORT).show();
	}
}
