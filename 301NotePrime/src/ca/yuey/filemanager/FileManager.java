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


import android.content.Context;
import android.util.Log;

public class FileManager
{
	private static final String FILENAME = "fileManager.ser";
	
	private static FileOutputStream fos;
	private static FileInputStream fis;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	
	private FileManager() {}
	
	public static final NotesFile getNotes(Context context)
	{
		Log.d("ca.yuey.filemanager.FileManager.getNotes(Context)", "getNotes started");
		NotesFile result = null;
		
		try
		{
			fis = context.openFileInput(FILENAME);
		}
		catch (FileNotFoundException e)
		{
			// If the file does not exist just return a new NotesArchive
			Log.e("ca.yuey.filemanager.FileManager.getNotes(Context)", "FileNotFoundException occured in method FileManager.getNotes");
			return new NotesFile(null, null);
		}
		
		try
		{
			// Try to get the object from the file.
			ois = new ObjectInputStream(fis);
			result = (NotesFile) ois.readObject();
			
			Log.d("ca.yuey.filemanager.FileManager.getNotes(Context)", "getNotes read the ObjectInputStream");
			
			// Cleanup
			ois.close(); fis.close();			
		}
		catch(IOException e)
		{
			// There was some sort of error in getting the object from file.
			// Try the operation again a few times.
			Log.e("ca.yuey.filemanager.FileManager.getNotes(Context)", "IOException occured in method FileManager.getNotes");
		}
		catch (ClassNotFoundException e)
		{
			// Something really f**ked up seriously what 
			Log.e("ca.yuey.filemanager.FileManager.getNotes(Context)", "ClassNotFoundException occured in method FileManager.getNotes");
		}

		Log.d("ca.yuey.filemanager.FileManager.getNotes(Context)", "getNotes finished");
		return result;
	}
	
	public static final void saveNotes(Context context, NotesFile archive)
	{
		Log.d("ca.yuey.filemanager.FileManager.saveNotes(Context, NotesFile)", "saveNotes started");
		try
		{
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
		}
		catch (FileNotFoundException e)
		{
			Log.e("ca.yuey.filemanager.FileManager.saveNotes(Context, NotesFile)", "FileNotFoundException occured in method FileManager.saveNotes");
		}
		
		try
		{
			oos = new ObjectOutputStream(fos);
			oos.writeObject(archive);

			Log.d("ca.yuey.filemanager.FileManager.saveNotes(Context, NotesFile)", "saveNotes wrote the object");
			
			oos.close(); fos.close();
		}
		catch(IOException e)
		{
			Log.e("ca.yuey.filemanager.FileManager.saveNotes(Context, NotesFile)", "IOException occured in method FileManager.saveNotes");
		}

		Log.d("ca.yuey.filemanager.FileManager.saveNotes(Context, NotesFile)", "saveNotes finished");
	}
}
