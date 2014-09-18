package filemanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class FileManager {
	
	private static FileManager instance;
	private static final String FILENAME = "fileManager.ser";
	private FileOutputStream fos;
	private FileInputStream fis;
	
	private FileManager() { }
	
	//TODO
	public static FileManager getFileManager(Context context)
			throws FileNotFoundException
	{
		// First try and retrieve any data stored on the device.
		try
		{
			instance.fis = context.openFileInput(FILENAME);
			// The FIS will be a serialized ArrayList<NotesArchive>, so
			// we will load the file into memory as such.
		}
		catch (FileNotFoundException e)
		{
			// There wasn't anything in local storage, so create a new file
			// to store persistent data.
		}
		instance.fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
		return instance;
	}
	public static boolean close() 
			throws IOException
	{
		instance.fos.close();
		return true;
	}
}
