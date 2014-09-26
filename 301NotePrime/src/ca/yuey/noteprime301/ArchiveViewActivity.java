package ca.yuey.noteprime301;

import ca.yuey.adapters.NotesListAdapter;
import ca.yuey.models.NotesFile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ArchiveViewActivity
extends Activity
{
	private Intent intent;
	
	private NotesFile notes;
	private NotesListAdapter notesAdapter;
	
	private ListView archiveListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive_view);
		
        this.getActivityResources();

		this.unpackIntent();
        
        this.attachListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.archive_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}
	
	private void getActivityResources()
	{
		this.archiveListView = (ListView) this.findViewById(R.id.archiveViewListView);
	}
	private void unpackIntent()
	{
		this.intent = this.getIntent();
		this.notes = (NotesFile) this.intent.getExtras().getSerializable(MainActivity.KEY_NOTE_FILE_SER);
		if (this.notes == null)
		{
			this.notes = new NotesFile(null, null);
			Log.e("ca.yuey.noteprime301.ArchiveViewActivity.unpackIntent()", "Failed to extract notes data from intent.");
		}
		Log.d("ca.yuey.noteprime301.ArchiveViewActivity.unpackIntent()", "Got a notesfile with " + this.notes.size() + " active and " + this.notes.sizeArchive() + " archived items.");
	}
	private void attachListeners()
	{
		this.notesAdapter = new NotesListAdapter(this, this.notes, true);
		this.archiveListView.setAdapter(this.notesAdapter);
		
        this.archiveListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        this.archiveListView.setMultiChoiceModeListener(new MultiChoiceModeListener()
        {	
        	private int numSelected = 0;
        	
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item)
			{
				ArchiveViewActivity host = ArchiveViewActivity.this;
				switch (item.getItemId())
				{
				case (R.id.action_unarchive_selected):
					numSelected = 0;
					host.notesAdapter.archiveSelection();
					host.notesAdapter.clearSelection();
					mode.finish();
				case (R.id.action_edit_selection):
					
				case (R.id.action_select_all):
					
				case (R.id.action_delete_selected):
					
				case (R.id.action_mail_selection):
					
				default:
					return false;
				}
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu)
			{
				this.numSelected = 0;
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.archive_view_context, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode)
			{
				ArchiveViewActivity.this.notesAdapter.clearSelection();
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
					ArchiveViewActivity.this.notesAdapter.setSelection(position, checked);
				}
				else
				{
					this.numSelected --;
					ArchiveViewActivity.this.notesAdapter.removeSelection(position);
				}
				
				if (numSelected != 1) // Edit only one thing at a time.
					mode.getMenu().getItem(1).setVisible(false);
				else
					mode.getMenu().getItem(1).setVisible(true);
					
				mode.setTitle(this.numSelected + " selected");
			}
        });
        this.archiveListView.setOnItemLongClickListener(new OnItemLongClickListener()
        {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3)
			{
				ArchiveViewActivity.this.archiveListView.setItemChecked(position, !ArchiveViewActivity.this.notesAdapter.isChecked(position));
				return false;
			}
        	
        });
	}
}
