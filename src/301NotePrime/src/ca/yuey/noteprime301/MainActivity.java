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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import ca.yuey.adapters.NotesListAdapter;
import ca.yuey.filemanager.FileManager;
import ca.yuey.models.Note;
import ca.yuey.models.NotesFile;

public class MainActivity
extends BaseActivity
{
	public static final String KEY_NOTES_BUNDLE = "notes";
	
	private NotesListAdapter notesAdapter;
	
	private ImageButton quickAcceptButton;
	private ListView noteList;
	private EditText quickEntry;
	
	/*========================================================================
	 * Android Lifecycle
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        BaseActivity.notes = FileManager.getNotes(this);
        
        this.getActivityResources();
        
        this.attachListeners();
    }
    
    @Override
    protected void onRestart()
    {
    	super.onRestart();
    }
    
    @Override
    protected void onResume()
    {
    	this.getActionBar().setTitle("Home");
    	super.onResume();
    }

    @Override
    protected void onPause()
    {
    	super.onPause();
    	FileManager.saveNotes(this, notes);
    }
    
	/*========================================================================
	 * onX Callbacks
	 */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
    	// The activity is being destroyed. Save our data into the bundle.
    	savedInstanceState.putSerializable(KEY_NOTES_BUNDLE, BaseActivity.notes);
    }
    @Override
    
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
    	BaseActivity.notes = (NotesFile) savedInstanceState.get(KEY_NOTES_BUNDLE);
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
    	Intent intent;
    	switch (item.getItemId())
    	{
    	case (R.id.action_new):
    		intent = new Intent(this, ComposeNoteActivity.class);
    		String msg = this.quickEntry.getText().toString();
    		if (msg != null)
    			intent.putExtra(
    					BaseActivity.KEY_NOTE_SERIALIZABLE, new Note(msg, null, null));
    		this.startActivityForResult(intent, 0);
    		return true;
    		
    	case (R.id.action_archive):
    		intent = new Intent(this, ArchiveViewActivity.class);
    		this.startActivity(intent);
    		return true;
    		
    	case (R.id.action_mail_all):
			intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","abc@domain.com", null));
			intent.putExtra(Intent.EXTRA_TEXT, this.notesAdapter.digestEverythingToString());
			startActivity(Intent.createChooser(intent, "Send email..."));
    		return true;
    		
    	case (R.id.action_info):
    		intent = new Intent(this, InfoActivity.class);
    		this.startActivity(intent);
    		return true;
    		
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	super.onActivityResult(requestCode, resultCode, data);
    	switch (requestCode)
    	{
    	case(0): // Navigated back via ComposeNoteActivity
    		if (resultCode == Activity.RESULT_OK)
    		{
    			Note incomingNote = (Note) data.getExtras().get(ComposeNoteActivity.KEY_COMPLETED_NOTE_ITEM);
    			if (incomingNote != null)
    				this.notesAdapter.add(incomingNote);
    			else Log.e("ca.yuey.noteprime301.MainActivity.onActivityResult(int, int, Intent)", "ComposeNoteActivity returned success, but the data returned was null.");
    			FileManager.saveNotes(this, BaseActivity.notes);
    		}
    		break;
    	}
    }
    
	/*========================================================================
     * Layout Callbacks
     */
    private void onClickQuickNote()
    {
    	//EditText entry = (EditText) this.findViewById(R.id.editTextQuickNote);
    	String msg = this.quickEntry.getText().toString();
    	
    	// Don't do anything if there hasn't been any input.
    	if (msg.trim().isEmpty()) return;
    	
    	// Add the new entry as a note with only title.
    	this.notesAdapter.add(
    			new Note(msg, null, null));
    	
    	// Clear the EditText and the cached text.
    	this.quickEntry.clearFocus();
    	this.quickEntry.setText("");
    }
    
	/*========================================================================
     * Auxiliary
     */
	private void getActivityResources()
	{
        // Grab the views we need.
    	this.quickAcceptButton = (ImageButton) this.findViewById(R.id.buttonQuickAccept);
        this.noteList = (ListView) this.findViewById(R.id.notesListView);
        this.quickEntry = (EditText) this.findViewById(R.id.editTextQuickNote);
	}
	private void attachListeners()
	{
        // Attach the View adapter to our notes object.
        this.notesAdapter = new NotesListAdapter(this, false);
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
        // Attach a multi choice listener to the ListView
        this.noteList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        this.noteList.setMultiChoiceModeListener(new MultiChoiceModeListener()
        {	
        	private int numSelected = 0;
        	
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item)
			{
				MainActivity host = MainActivity.this;
				Intent intent;
				switch (item.getItemId())
				{
				case (R.id.action_archive_selected):
					numSelected = 0;
					host.notesAdapter.archiveSelection();
					host.notesAdapter.clearSelection();
					mode.finish();
					return true;
					
				case (R.id.action_edit_selection):
					if (numSelected != 1 )
						// How did you get here???
						mode.finish();
					numSelected = 0;
					intent = new Intent(MainActivity.this, ComposeNoteActivity.class);
					intent.putExtra(
							BaseActivity.KEY_NOTE_SERIALIZABLE,
							host.notesAdapter.getSelectedItems().get(0));
					host.notesAdapter.clearSelection();
					startActivityForResult(intent, 1);
					mode.finish();
					return true;
					
				case (R.id.action_select_all):
					host.notesAdapter.selectAll();
					numSelected = host.notesAdapter.size();
					mode.setTitle(this.numSelected + " selected");
					return true;
					
				case (R.id.action_delete_selected):
					numSelected = 0;
					host.notesAdapter.delSelection();
					host.notesAdapter.clearSelection();
					mode.finish();
					return true;
					
				case (R.id.action_mail_selection):
					intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","abc@domain.com", null));
					intent.putExtra(Intent.EXTRA_TEXT, host.notesAdapter.digestSelectionToString());
					startActivity(Intent.createChooser(intent, "Send email..."));
					mode.finish();
					return true;
				default:
					return false;
				}
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu)
			{
				this.numSelected = 0;
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.main_context, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode)
			{
				MainActivity.this.notesAdapter.clearSelection();
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) 
			{
				return false;
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
			{
				if (checked)
				{
					this.numSelected ++;
					MainActivity.this.notesAdapter.setSelection(position, checked);
				}
				else
				{
					this.numSelected --;
					MainActivity.this.notesAdapter.removeSelection(position);
				}
				
				if (numSelected != 1)
					mode.getMenu().getItem(1).setVisible(false);
				else
					mode.getMenu().getItem(1).setVisible(true);
					
				mode.setTitle(this.numSelected + " selected");
			}
        });
        this.noteList.setOnItemLongClickListener(new OnItemLongClickListener()
        {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				MainActivity.this.noteList.setItemChecked(position, !MainActivity.this.notesAdapter.isChecked(position));
				return false;
			}
        	
        });
	}
}
