package models;

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
	public ArrayList<Note> getAllNotes()
	{
		ArrayList<Note> result = new ArrayList<Note>();
		result.addAll(notes);
		result.addAll(archive);
		return result;
	}
	public Note getNote(String key)
	{
		// TODO: Implement
		return notes.get(0);
	}
	public Note getNoteFromArchive(String key)
	{
		// TODO: Implement
		return archive.get(0);
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
		// TODO
	}
	
	/*========================================================================
	 * Modification
	 */
}