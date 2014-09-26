package ca.yuey.noteprime301;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import ca.yuey.adapters.NotesListAdapter;
import ca.yuey.filemanager.FileManager;

public class ArchiveViewActivity
extends BaseActivity
{
	private NotesListAdapter notesAdapter;
	
	private ListView archiveListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive_view);
		
        this.getActivityResources();
        
        this.attachListeners();
	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart();
	}
	
	@Override
	protected void onPause()
	{
		FileManager.saveNotes(this, notes);
		super.onPause();
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
		switch (item.getItemId())
		{
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	private void getActivityResources()
	{
		this.archiveListView = (ListView) this.findViewById(R.id.archiveViewListView);
	}
	private void attachListeners()
	{
		this.notesAdapter = new NotesListAdapter(this, true);
		this.archiveListView.setAdapter(this.notesAdapter);
		
        this.archiveListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        this.archiveListView.setMultiChoiceModeListener(new MultiChoiceModeListener()
        {	
        	private int numSelected = 0;
        	
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item)
			{
				Intent intent;
				ArchiveViewActivity host = ArchiveViewActivity.this;
				switch (item.getItemId())
				{
				case (R.id.action_unarchive_selected):
					numSelected = 0;
					host.notesAdapter.unarchiveSelection();
					host.notesAdapter.clearSelection();
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
				return true;
			}
        	
        });
	}
}
