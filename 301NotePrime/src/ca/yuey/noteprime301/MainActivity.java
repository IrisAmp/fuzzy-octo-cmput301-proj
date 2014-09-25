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

==============================================================================

		MainActivity.java
	The UI class for the main activity.

*/
package ca.yuey.noteprime301;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import ca.yuey.adapters.NotesListAdapter;
import ca.yuey.models.Note;
import ca.yuey.models.NotesFile;

public class MainActivity
extends Activity
{
	public static final String KEY_NOTES_BUNDLE = "notes";
	public static final String KEY_NEW_NOTE_ENTRY = "newEntry";
	
	private NotesFile notes;
	private NotesListAdapter notesAdapter;
	
	private ImageButton quickAcceptButton;
	private ListView noteList;
	private EditText quickEntry;
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Since this is the only launcher activity, this is called when the app
	 * is first started. Persistent user data is loaded here and UI elements
	 * are set up.
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Super
        super.onCreate(savedInstanceState);
                
    	// Housekeeping
        setContentView(R.layout.activity_main);
        
        // Get persistent data
        this.notes = new NotesFile(null, null);
        
        // Grab the views we need.
    	this.quickAcceptButton = (ImageButton) this.findViewById(R.id.buttonQuickAccept);
        this.noteList = (ListView) this.findViewById(R.id.listView1);
        this.quickEntry = (EditText) this.findViewById(R.id.editTextQuickNote);
        
        // Attach the View adapter to our notes object.
        this.notesAdapter = new NotesListAdapter(this, this.notes, false);
        this.noteList.setAdapter(this.notesAdapter);
    	
        // Attach an onClick listener to the accept button.
        this.quickAcceptButton.setOnClickListener(new View.OnClickListener()
        	{
				@Override
				public void onClick(View v)
				{
					MainActivity.this.onClickQuickNote();
				}
        	});
        
        // Attach an onEditorActionListener to the EditText too.
        this.quickEntry.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {

			@Override
			public boolean onEditorAction(TextView view, int actionID, KeyEvent event)
			{
				if (actionID == EditorInfo.IME_ACTION_DONE)
				{
					MainActivity.this.onClickQuickNote();
					return true;
				}
				return false;
			}
        	
        });

        //this.generateData(notes);
        
        // Start FIO. The AsyncLoader will notify the UI thread to update the
        // ListView when it finishes FIO, but for the meantime we can show
        // a progress spinner.
        // TODO: Reimplement the Loader to actually work
        //notes = FileManager.getNotes(this);
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
    	super.onResume();
    }

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 * Called when the activity is no longer considered to be in the
	 * foreground. User data is saved here, since other app states can
	 * be killed by Android.
	 */
    @Override
    protected void onPause()
    {
    	super.onPause();
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
    	// The activity is being destroyed. Save our data into the bundle.
    	savedInstanceState.putSerializable(KEY_NOTES_BUNDLE, this.notes);
    }
    @Override
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
    	this.notes = (NotesFile) savedInstanceState.get(KEY_NOTES_BUNDLE);

    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    	case (R.id.action_new):
    		Intent intent = new Intent(this, NewNoteActivity.class);
    		intent.putExtra(
    				KEY_NEW_NOTE_ENTRY, this.quickEntry.getText().toString());
    		startActivity(intent);
    		return true;
    	case (R.id.action_archive):
    		// TODO: Intent to ArchiveView
    		return true;
    	case (R.id.action_settings):
    		// TODO: Intent to Settings
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    /*
     * Layout Callbacks
     */
    private void onClickQuickNote()
    {
    	//EditText entry = (EditText) this.findViewById(R.id.editTextQuickNote);
    	String msg = this.quickEntry.getText().toString();
    	
    	// Don't do anything if there hasn't been any input.
    	if (msg.trim().isEmpty()) return;
    	
    	// Add the new entry as a note with only title.
    	this.notes.add(
    			new Note(msg, null, null));
    	this.notesAdapter.notifyDataSetChanged();
    	
    	// Clear the EditText and the cached text.
    	this.quickEntry.clearFocus();
    	this.quickEntry.setText("");
    }
    
    /*
     * Auxiliary
     */
	private void generateData(NotesFile notes)
	{
		notes.add(new Note("Hello!", null, null));
		notes.add(new Note("World!", "This is a test", new Date()));
		notes.add(new Note("Foo!", "And another test.", null));
		notes.add(new Note("Bar!", "And yet another test.", null));
	}
	
	private void logList()
	{
		for (Note n : this.notes.getAll())
		{
			Log.d("logList", n.getTitle());
		}
	}
}
