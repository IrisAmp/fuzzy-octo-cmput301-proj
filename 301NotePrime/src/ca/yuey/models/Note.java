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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Note
implements Serializable, Comparable<Note>
{
	private static final long serialVersionUID = -6518181418798265714L;
	
	private final Date created;
	private Date modified;
	private Date due;
	
	private String title;
	private String detail;
	private boolean done;
	
	/*/=======================================================================
		Constructors 
	 */
	@SuppressWarnings("unused")
	private Note() // Private. Don't make a new note with no data.
	{
		this.created = new Date();
	}
	public Note(String t, String d, Date due)
	{
		this.created = new Date();
		this.modified = (Date) created.clone();
		
		this.title = t;
		this.detail = d;
		this.due = due;
		this.done = false;
	}
	
	/*/=======================================================================
		Accessors
	 */
	public ArrayList<String> getInfo()
	{
		// Return an array list of Strings containing the object's data.
		ArrayList<String> result = new ArrayList<String>();
		
		result.add(this.title);
		result.add(this.detail);
		
		if (this.due != null)
		{
			DateFormat sf = new SimpleDateFormat("MMMM dd, hh:mm a", Locale.CANADA);
			result.add(sf.format(this.due));
		}
		else
		{
			result.add(null);
		}
		
		return result;
	}
	public String getTitle()
	{
		return this.title;
	}
	public String getDetail()
	{
		return this.detail;
	}
	public Date getCreated()
	{
		return (Date) this.created.clone();
	}
	public Date getModified()
	{
		return (Date) this.modified.clone();
	}
	public Date getDue()
	{
		return (Date) this.modified.clone();
	}
	public boolean isFinished()
	{
		return this.done;
	}
	
	/*/=======================================================================
		Modifiers
	 */
	public Note modify(String title, String detail, Date due)
	{
		if(title != null)
		{
			this.title = title;
		}
		if(detail != null)
		{
			this.detail = detail;
		}
		if(due != null)
		{
			this.due = due;
		}
		
		this.modified = new Date();
		return this;
	}
	public void notifyModified()
	{
		this.modified = new Date();
	}
	public void finish()
	{
		this.done = true;
	}
	public void unFinish()
	{
		this.done = false;
	}
	
	/*/=======================================================================
		Auxiliary
	 */
	@Override
	public int compareTo(Note other)
	{
		// Natural ordering of Note objects. Returns negative if this should
		// be ordered before other, and vice versa.
		
		int x;
		
		// Compare completion
		x = compareBooleans(this.done, other.done);
		if (x != 0) return x;
		
		// Comepare due dates.
		x = this.due.compareTo(other.due);
		if (x != 0) return x;
		/*
		if(this.due != null && other.due != null)
			return this.due.compareTo(other.due);
		if(this.due != null)
			return -1;
		if(other.due != null)
			return 1;
		*/
		
		// Compare creation dates
		return this.created.compareTo(other.created);
	}
	private int compareBooleans(boolean first, boolean second)
	{
		if (first && second)
			return 0;
		if (first)
			return -1;
		if (second)
			return 1;
		return 0;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof Note)) return false;
		
		if (this.compareTo((Note)other) == 0) return true;
		
		return false;
	}
}
