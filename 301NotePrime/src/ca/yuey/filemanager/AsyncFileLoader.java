package ca.yuey.filemanager;

import ca.yuey.models.NotesFile;

import android.content.Context;
import android.os.AsyncTask;

public class AsyncFileLoader
extends AsyncTask<Void, Void, NotesFile>
{
	//private Context context;
	private NotesFile data;
	
	public AsyncFileLoader(Context context, NotesFile newData)
	{
		this.data = newData;
		//this.context = context;
	}

	@Override
	protected void onPreExecute()
	{
		
	}
	
	@Override
	protected NotesFile doInBackground(Void... arg0)
	{
		// TODO: This is clunky. FIX.
		//this.data.addNotes(FileManager.getNotes(this.context).getAllNotes());
		return this.data;
	}
	
	@Override
	protected void onPostExecute(NotesFile result)
	{
		
	}
}
