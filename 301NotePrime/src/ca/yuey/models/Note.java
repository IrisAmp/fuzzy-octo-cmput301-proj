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
