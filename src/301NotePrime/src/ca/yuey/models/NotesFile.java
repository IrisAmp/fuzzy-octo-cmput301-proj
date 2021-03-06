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

		NotesFile.java
   
   A Serializable class used to manage a note file on the device.
   Takes responsibility of managing which notes are archived, etc. and
   the statistics for the database as a whole.
   
   The database itself is implemented as 2 LinkedHashMaps and 2 ArrayLists
   (one Map and List for the data and archive each). The LinkedHashMap
   provides arbitrary access to each member without the need to search for a
   key, and the ArrayList is required for the 
   
   
*/
package ca.yuey.models;

import java.io.Serializable;
import java.util.ArrayList;

public class NotesFile
implements Serializable
{
	private static final long serialVersionUID = -7913436074635612392L;
	
	private ArrayList<Note> data;
	private ArrayList<Note> archive;
	
	public NotesFile(ArrayList<Note> data, ArrayList<Note> archive)
	{
		if (data != null) {
			this.data = new ArrayList<Note>(data);
		} else this.data = new ArrayList<Note>();
		
		if (data != null) {
			this.archive = new ArrayList<Note>(data);
		} else this.archive = new ArrayList<Note>();
	}
	
	public void add(Note item)
	{
		this.data.add(item);
	}
	public void addToArchive(Note item)
	{
		this.archive.add(item);
	}
	
	public boolean remove(Note killMe)
	{
		return this.data.remove(killMe);
	}
	public boolean removeFromArchive(Note killMe)
	{
		return this.archive.remove(killMe);
	}
	
	public Note get(int position)
	{
		return this.data.get(position);
	}
	
	public Note getFromArchive(int position)
	{
		return this.archive.get(position);
	}
	
	public void archive(int position)
	{
		this.archive.add(this.data.get(position));
		this.data.remove(position);
	}
	
	public void unarchive(int position)
	{
		this.data.add(this.archive.get(position));
		this.archive.remove(position);
	}
	
	public int size()
	{
		return this.data.size();
	}
	
	public int sizeArchive()
	{
		return this.archive.size();
	}
	
	public int sizeTotal()
	{
		return this.data.size() + this.archive.size();
	}

	public void remove(int i)
	{
		this.data.remove(i);
	}
	public void removeFromArchive(int i)
	{
		this.archive.remove(i);
	}

	public void sort()
	{
		// HERE BE DRAGONS (bugs).
		//Collections.sort(this.data);
		//Collections.sort(this.archive);
		
		// For some reason this function clears each element's due date
		// WTF???
	}

	public int getNumFinished()
	{
		int result = 0;
		for (Note n : this.data)
			if (n.isFinished())
				result += 1;
		return result;
	}

	public int getNumArchivedFinished()
	{
		int result = 0;
		for (Note n : this.archive)
			if (n.isFinished())
				result += 1;
		return result;
	}
}