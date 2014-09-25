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

import ca.yuey.adapters.NotesListAdapter;
import ca.yuey.filemanager.FileManager;
import ca.yuey.models.Note;
import ca.yuey.models.NotesFile;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainActivity
extends Activity
{
	private static NotesFile notes = new NotesFile(null, null);
	private static NotesListAdapter notesAdapter;
	
	private ImageButton quickAcceptButton;
	
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
        
        // Grab the views we need.
        ListView noteList = (ListView) this.findViewById(R.id.listView1);
    	this.quickAcceptButton = (ImageButton) this.findViewById(R.id.buttonQuickAccept);
        
        // Attach the View adapter to our notes object.
        notesAdapter = new NotesListAdapter(this, MainActivity.notes, false);
        noteList.setAdapter(MainActivity.notesAdapter);
        
        // Attach an onClick listener to the accept button.
        this.quickAcceptButton.setOnClickListener(new View.OnClickListener()
        	{
				@Override
				public void onClick(View v)
				{
					MainActivity.this.onClickQuickNote(v);
				}
        	});
        
        this.generateData(notes);
        
        // Start FIO. The AsyncLoader will notify the UI thread to update the
        // ListView when it finishes FIO, but for the meantime we can show
        // a progress spinner.
        // TODO: Reimplement the Loader to actually work
        notes = FileManager.getNotes(this);
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
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	switch (item.getItemId())
    	{
    	case (R.id.action_new):
    		// TODO: Intent to NewNote
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
    public void onClickQuickNote(View button)
    {
    	// Add the new entry as a note with only title.
    	MainActivity.notes.add(
    			new Note(getQuickEntry(), null, null));
    	MainActivity.notesAdapter.notifyDataSetChanged();
    	
    	// Clear the EditText and the cached text.
    	EditText entry = (EditText) this.findViewById(R.id.editTextQuickNote);
    	entry.clearFocus();
    	entry.setText("");
    }
    
    /*
     * Auxiliary
     */
    private String getQuickEntry()
    {
    	EditText entry = (EditText) this.findViewById(R.id.editTextQuickNote);
    	return entry.getText().toString();
    }
    
	private void generateData(NotesFile notes)
	{
		notes.add(new Note("Hello!", null, null));
		notes.add(new Note("World!", "This is a test", null));
		notes.add(new Note("Foo!", "And another test.", null));
		notes.add(new Note("Bar!", "And yet another test.", null));
	}
}
