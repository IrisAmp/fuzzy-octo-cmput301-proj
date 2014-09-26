package ca.yuey.noteprime301;

import android.app.Activity;
import android.os.Bundle;
import ca.yuey.models.NotesFile;

public class BaseActivity
extends Activity
{
	public static final String KEY_NOTES_FILE_SERIALIZABLE = "ca.yuey.noteprime301.NOTES_FILE_SERIALIZABLE";
	public static final String KEY_NOTE_SERIALIZABLE = "ca.yuey.noteprime301.NOTE_SERIALIZABLE";
	public static final String KEY_NEW_NOTE_TITLE_ENTRY = "ca.yuey.noteprime301.ENTRY_TITLE";
	
	public static NotesFile notes;
	
	@Override
	protected void onSaveInstanceState(Bundle save)
	{
		save.putSerializable(BaseActivity.KEY_NOTE_SERIALIZABLE, BaseActivity.notes);
		super.onSaveInstanceState(save);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle save)
	{
		BaseActivity.notes = (NotesFile) save.get(BaseActivity.KEY_NOTE_SERIALIZABLE);
		super.onRestoreInstanceState(save);
	}

	public static String getStatistics()
	{
		String result = "";
		result += ("You have " + notes.sizeTotal() + " notes in total, " + notes.size() + " are active and " + notes.sizeArchive() + " have been archived. " +
		notes.getNumFinished() + " of your " + notes.size() + " active notes are finished, and " + notes.getNumArchivedFinished() + " of your " + notes.sizeArchive() + " archived notes are finished.");
		return result;
	}
}
