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

		FileManager.java
	An auxiliary class which manages all file IO, and handles errors silently.
*/
package ca.yuey.filemanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.yuey.models.NotesFile;
import ca.yuey.noteprime301.Helper;


import android.content.Context;
import android.util.Log;

public class FileManager
{
	private static final String FILENAME = "fileManager.ser";
	private static final int TIMEOUT_MAX = 3;
	private static int retryTimeout = 0;
	
	private static FileOutputStream fos;
	private static FileInputStream fis;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	
	private FileManager() {}
	
	public static final NotesFile getNotes(Context context)
	{
		NotesFile result = null;
		
		try
		{
			fis = context.openFileInput(FILENAME);
		}
		catch (FileNotFoundException e)
		{
			// If the file does not exist just return a new NotesArchive
			Log.d(Helper.DEBUG_TAG, "FileNotFoundException occured in method FileManager.getNotes", e);
			return new NotesFile(null, null);
		}
		
		try
		{
			// Try to get the object from the file.
			ois = new ObjectInputStream(fis);
			result = (NotesFile) ois.readObject();
			
			// Cleanup
			ois.close(); fis.close();			
		}
		catch(IOException e)
		{
			// There was some sort of error in getting the object from file.
			// Try the operation again a few times.
			Log.d(Helper.DEBUG_TAG, "IOException occured in method FileManager.getNotes", e);
			return retryLoad(context);
		}
		catch (ClassNotFoundException e)
		{
			// Something really f**ked up seriously what 
			Log.d(Helper.DEBUG_TAG, "ClassNotFoundException occured in method FileManager.getNotes", e);
			return retryLoad(context);
		}
		
		retryTimeout = 0;
		return result;
	}
	private static final NotesFile retryLoad(Context context)
	{
		if(retryTimeout < TIMEOUT_MAX)
		{
			retryTimeout ++;
			return getNotes(context);
		}
		else
		{
			toastError("Couldn't load notes!");
			return null;
		}
	}
	
	public static final void saveNotes(Context context, NotesFile archive)
	{
		try
		{
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
		}
		catch (FileNotFoundException e)
		{
			Log.d(Helper.DEBUG_TAG, "FileNotFoundException occured in method FileManager.saveNotes", e);
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
			Log.d(Helper.DEBUG_TAG, "IOException occured in method FileManager.saveNotes", e);
			retrySave(context, archive);
		}
	}
	private static final void retrySave(Context context, NotesFile archive)
	{
		if(retryTimeout < TIMEOUT_MAX)
		{
			retryTimeout ++;
			saveNotes(context, archive);
		}
		else
		{
			toastError("Couldn't save notes!");
		}
	}
	
	private static final void toastError(String msg)
	{
		
	}
}
