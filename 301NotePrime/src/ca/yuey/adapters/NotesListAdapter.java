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
	private Context context;
	private LayoutInflater mInflater = null;
	private ArrayList<Note> data;
	
	public NotesListAdapter(Context context, ArrayList<Note> data)
	{
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount()
	{
		return data.size();
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
	       	mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	        convertView = mInflater.inflate(R.layout.list_item_note, null);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.textViewTitle);
		TextView detail = (TextView) convertView.findViewById(R.id.textViewDetail);
		
		title.setText(item.getTitle());
		detail.setText(item.getDesc());
		
		return convertView;
	}
}