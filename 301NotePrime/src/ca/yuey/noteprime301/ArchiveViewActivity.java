package ca.yuey.noteprime301;

import ca.yuey.adapters.NotesListAdapter;
import ca.yuey.models.NotesFile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

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
		// TODO Auto-generated method stub
		
	}
	private void unpackIntent()
	{
		this.intent = this.getIntent();
		this.notes = (NotesFile) this.intent.getExtras().getSerializable(MainActivity.KEY_NOTE_FILE_SER);
		if (this.notes == null)
			this.notes = new NotesFile(null, null);
	}
	private void attachListeners()
	{
		// TODO Auto-generated method stub
		
	}
}
