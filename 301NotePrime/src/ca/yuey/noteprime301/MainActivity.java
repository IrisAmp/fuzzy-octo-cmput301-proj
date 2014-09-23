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
import android.widget.ListView;

public class MainActivity
extends Activity
{
	private static NotesFile notes = new NotesFile(null, null);
	
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
    	// Housekeeping
        setContentView(R.layout.activity_main);
        
        // Grab the views we need.
        ListView noteList = (ListView) this.findViewById(R.id.listView1);
        
        // Attach the View adapter to our notes object.
        noteList.setAdapter(
        		new NotesListAdapter(
        				this, MainActivity.notes, false));
        
        this.generateData(notes);
        
        // Start FIO. The AsyncLoader will notify the UI thread to update the
        // ListView when it finishes FIO, but for the meantime we can show
        // a progress spinner.
        // TODO: Reimplement the Loader to actually work
        notes = FileManager.getNotes(this);
        
        // Super
        super.onCreate(savedInstanceState);
    }
    
	private void generateData(NotesFile notes)
	{
		notes.add(new Note("Hello!", null, null));
		notes.add(new Note("World!", "This is a test", null));
		notes.add(new Note("Foo!", "And another test.", null));
		notes.add(new Note("Bar!", "And yet another test.", null));
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
    
}
