package models;

import java.io.Serializable;

public class Note
implements Serializable
{
	private static final long serialVersionUID = -6518181418798265714L;
	
	private String name;
	private String desc;
	private boolean archived;
	
	public Note() { }
	public Note(String s)
	{
		desc = s;
	}
	
	/*========================================================================
	 * Accessors
	 */
	public String getName()
	{
		return name;
	}
	public String getDesc()
	{
		return desc;
	}
	public boolean isArchived()
	{
		return archived;
	}
	
}
