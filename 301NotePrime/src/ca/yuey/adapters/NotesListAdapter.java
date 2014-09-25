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

		NotesListAdapter.java
	A helper class required to implement a ListView. Translates the data
	object Note to a View.
*/
package ca.yuey.adapters;

import java.util.ArrayList;

import ca.yuey.models.Note;
import ca.yuey.models.NotesFile;
import ca.yuey.noteprime301.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NotesListAdapter 
extends BaseAdapter
{
	private LayoutInflater mInflater = null;
	private NotesFile data;
	private final boolean archive;
	
	public NotesListAdapter(Context context, NotesFile data, boolean archive)
	{
		this.data = data;
		this.archive = archive;
       	this.mInflater = (LayoutInflater) 
       			context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount()
	{
		if(archive)
		{
			return this.data.sizeArchive();
		}
		else
		{
			return this.data.size();
		}
	}

	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// Get the Note object associated with this position.
		Note item = (Note) getItem(position);
		
		// Load the view data if it hasn't been already.
		if (convertView == null)
		{
	        convertView = mInflater.inflate(R.layout.list_item_note, null);
		}
		
		// Set up the view
		TextView title = (TextView) convertView.findViewById(R.id.noteItemTitle);
		TextView detail = (TextView) convertView.findViewById(R.id.noteItemDetail);
		TextView due = (TextView) convertView.findViewById(R.id.noteItemDue);

		ArrayList<String> strings = item.getInfo();
		
		title.setText(strings.get(0));
		detail.setText(strings.get(1));
		due.setText(strings.get(2));
		
		// Ship it back
		return convertView;
	}
}
