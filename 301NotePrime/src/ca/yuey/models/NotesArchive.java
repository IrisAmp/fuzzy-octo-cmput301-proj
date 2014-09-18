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
*/
package ca.yuey.models;

import java.io.Serializable;
import java.util.ArrayList;

public class NotesArchive 
implements Serializable
{
	private static final long serialVersionUID = 7092467508669329490L;
	
	private ArrayList<Note> notes;
	private ArrayList<Note> archive;
	
	public NotesArchive()
	{
		notes = new ArrayList<Note>();
		archive = new ArrayList<Note>();
	}
	
	/*========================================================================
	 * Accessors
	 */
	// Notes
	public ArrayList<Note> getNotes()
	{
		ArrayList<Note> clone = new ArrayList<Note>();
		clone.addAll(notes);
		return clone;
	}
	public int getNotesSize()
	{
		return notes.size();
	}

	// Archived Notes
	public ArrayList<Note> getArchive()
	{
		ArrayList<Note> clone = new ArrayList<Note>();
		clone.addAll(archive);
		return clone;
	}
	public int getArchiveSize()
	{
		return archive.size();
	}
	
	// Entire database.
	public Note getAnyNote(String key)
	{
		// TODO: Implement
		return null;
	}
	public ArrayList<Note> getAllNotes()
	{
		ArrayList<Note> result = new ArrayList<Note>();
		result.addAll(notes);
		result.addAll(archive);
		return result;
	}
	
	/*========================================================================
	 * Insertion
	 */
	public void addNote(Note n)
	{
		if (n.isArchived())
		{
			archive.add(n);
		}
		else
		{
			notes.add(n);
		}
	}
	public void addNotes(ArrayList<Note> notes)
	{
		for(Note n : notes)
		{
			if(n.isArchived())
			{
				archive.add(n);
			}
			else
			{
				notes.add(n);
			}
		}
	}
	
	/*========================================================================
	 * Deletion
	 */
	public void delNote(String key)
	{
		// TODO: Implement
	}
	
	/*========================================================================
	 * Modification
	 */
}