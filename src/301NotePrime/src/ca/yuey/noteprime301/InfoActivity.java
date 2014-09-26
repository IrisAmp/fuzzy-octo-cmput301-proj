package ca.yuey.noteprime301;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import ca.yuey.filemanager.FileManager;
import ca.yuey.models.NotesFile;

public class InfoActivity
extends BaseActivity
{
	TextView statistics;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		
		Button nuke = (Button) this.findViewById(R.id.nuke_button);
		nuke.setOnClickListener(new View.OnClickListener()
		{	
			@Override
			public void onClick(View arg0)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
				
				builder.setTitle("Delete the entire database?")
				       .setMessage("This action cannot be undone.");
				
				builder.setPositiveButton(R.string.butto_nuke_accept, new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
				       BaseActivity.notes = new NotesFile(null, null);
				       FileManager.saveNotes(InfoActivity.this.getBaseContext(), BaseActivity.notes);
				       }
				   });
				builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
				       // User cancelled the dialog
				       }
				   });
				
				builder.create().show();
			}
		});
		
		this.statistics = (TextView) this.findViewById(R.id.statisticsTV);
		this.statistics.setText(BaseActivity.getStatistics());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return super.onOptionsItemSelected(item);
	}
}
