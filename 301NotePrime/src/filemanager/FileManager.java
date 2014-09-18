package filemanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import models.NotesArchive;

import android.content.Context;

public class FileManager {
	
	private static final String FILENAME = "fileManager.ser";
	private static final int TIMEOUT_MAX = 3;
	private static int retryTimeout = 0;
	
	private static FileOutputStream fos;
	private static FileInputStream fis;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	
	private FileManager() {}
	
	public static final NotesArchive getNotes(Context context)
			throws ClassNotFoundException 
	{
		NotesArchive result = null;
		
		try
		{
			fis = context.openFileInput(FILENAME);
		}
		catch (FileNotFoundException e)
		{
			// If the file does not exist just return a new NotesArchive
			return new NotesArchive();
		}
		
		try
		{
			// Try to get the object from the file.
			ois = new ObjectInputStream(fis);
			result = (NotesArchive) ois.readObject();
			
			// Cleanup
			ois.close(); fis.close();			
		}
		catch(IOException e)
		{
			// There was some sort of error in getting the object from file.
			// Try the operation again a few times.
			if(retryTimeout < TIMEOUT_MAX)
			{
				retryTimeout ++;
				return getNotes(context);
			}
		}
		
		retryTimeout = 0;
		return result;
	}
	
	public static final void saveNotes(Context context, NotesArchive archive)
	{
		try
		{
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
		}
		catch (FileNotFoundException e)
		{
			retrySave(context, archive);
		}
		
		try
		{
			oos = new ObjectOutputStream(fos);
			oos.writeObject(archive);
			
			oos.close(); fos.close();
			retryTimeout = 0;
		}
		catch(IOException e)
		{
			retrySave(context, archive);
		}
	}
	private static final void retrySave(Context context, NotesArchive archive)
	{
		if(retryTimeout < TIMEOUT_MAX)
		{
			retryTimeout ++;
			saveNotes(context, archive);
		}
	}
}
