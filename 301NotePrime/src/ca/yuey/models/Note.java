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
import java.util.Date;

public class Note
implements Serializable
{
	private static final long serialVersionUID = -6518181418798265714L;
	
	private String title;
	private String desc;
	private final Date created;
	private Date modified;
	private boolean archived;
	
	@SuppressWarnings("unused")
	private Note() // Private. Shouldn't make a new note with no data.
	{ 
		created = new Date();
	}
	public Note(String s)
	{
		this.created = new Date();
		this.modified = new Date();
		this.title = s;
		this.desc = "";
		this.archived = false;
	}
	public Note(String title, String desc)
	{
		this.created = new Date();
		this.modified = new Date();
		this.title = title;
		this.desc = desc;
		this.archived = false;
	}
	
	/*========================================================================
	 * Accessors
	 */
	public String getTitle()
	{
		return title;
	}
	public String getDesc()
	{
		return desc;
	}
	public boolean isArchived()
	{
		return archived;
	}
	public Date getCreated()
	{
		return created;
	}
	public Date getModified()
	{
		return modified;
	}
	
	/*
	 * 
	 */
}
