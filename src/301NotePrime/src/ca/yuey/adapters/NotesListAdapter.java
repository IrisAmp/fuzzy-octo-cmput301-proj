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
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import ca.yuey.models.Note;
import ca.yuey.models.NotesFile;
import ca.yuey.noteprime301.BaseActivity;
import ca.yuey.noteprime301.R;

@SuppressLint("UseSparseArrays")
public class NotesListAdapter 
extends BaseAdapter
{
	private LayoutInflater mInflater = null;
	private NotesFile data;
	private HashMap<Integer, Boolean> selection = new HashMap<Integer, Boolean>();
	private final boolean archive;
	
	public NotesListAdapter(Context context, boolean archive)
	{
		this.data = BaseActivity.notes;
		this.archive = archive;
       	this.mInflater = (LayoutInflater) 
       			context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount()
	{
		if(archive)
			return this.data.sizeArchive();
		else
			return this.data.size();
	}

	@Override
	public Object getItem(int position)
	{
		if (archive)
			return data.getFromArchive(position);
		else
			return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	public void add(Note item)
	{
		if (archive)
			this.data.addToArchive(item);
		else
			this.data.add(item);

		this.notifyDataSetChanged();
	}
	
	// Selection =============================================================
	public void setSelection(int position, boolean value)
	{
		this.selection.put(position, value);
		this.notifyDataSetChanged();
	}

	public void removeSelection(int position)
	{
		this.selection.remove(position);

		this.notifyDataSetChanged();
	}

	public boolean isChecked(int position)
	{
		Boolean res = this.selection.get(position);
		if (res != null)
			return res;
		else return false;
	}
	
	public Set<Integer> getCheckedPos()
	{
		return this.selection.keySet();
	}
	
	public void clearSelection()
	{
		this.selection = new HashMap<Integer, Boolean>();
		this.notifyDataSetChanged();
	}
	
	public void archiveSelection()
	{
		ArrayList<Integer> indicies = new ArrayList<Integer>(this.selection.keySet());
		Collections.sort(indicies);
		Collections.sort(indicies, Collections.reverseOrder());
		for (int i : indicies)
			this.data.archive(i);

		this.clearSelection();
		this.notifyDataSetChanged();
	}
	public void unarchiveSelection()
	{
		ArrayList<Integer> indicies = new ArrayList<Integer>(this.selection.keySet());
		Collections.sort(indicies);
		Collections.sort(indicies, Collections.reverseOrder());
		for (int i : indicies)
			this.data.unarchive(i);

		this.clearSelection();
		this.notifyDataSetChanged();
	}
	
	public void delSelection()
	{
		ArrayList<Integer> indicies = new ArrayList<Integer>(this.selection.keySet());
		Collections.sort(indicies);
		Collections.sort(indicies, Collections.reverseOrder());
		if (archive)
			for (int i : indicies) this.data.removeFromArchive(i);
		else
			for (int i : indicies) this.data.remove(i);
		
	}
	
	public ArrayList<Note> getSelectedItems()
	{
		ArrayList<Integer> indicies = new ArrayList<Integer>(this.selection.keySet());
		ArrayList<Note> result = new ArrayList<Note>();
		if (archive)
		{
			for (int i : indicies)
				result.add(this.data.getFromArchive(i));
		}
		else
		{
			for (int i : indicies)
				result.add(this.data.get(i));
		}
		return result;
	}

	public void selectAll()
	{
		int i = 0;
		if (archive)
			while (i++ < this.data.sizeArchive())
				this.setSelection(i, true);
		else
			while (i++ < this.data.size())
				this.setSelection(i, true);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// Get the Note object associated with this position.
		final Note item = (Note) this.getItem(position);
		
		// Load the view data if it hasn't been already.
		if (convertView == null)
		{
	        convertView = mInflater.inflate(R.layout.list_item_note, null);
		}
		
		// Set up the view
		TextView title = (TextView) convertView.findViewById(R.id.noteItemTitle);
		TextView detail = (TextView) convertView.findViewById(R.id.noteItemDetail);
		TextView due = (TextView) convertView.findViewById(R.id.noteItemDue);
		final CheckBox cb = (CheckBox) convertView.findViewById(R.id.noteItemCheckBox);
		
		cb.setChecked(item.isFinished());
		
		cb.setOnClickListener(new View.OnClickListener() {
			// Needed this weird hack to get around a bug where checking
			// one item in the archive would check all of them.
			@Override
			public void onClick(View arg0)
			{
				cb.toggle();
				if (!item.isFinished())
					item.finish();
				else
					item.unFinish();
				cb.setChecked(item.isFinished());
			}
		});

		ArrayList<String> strings = item.getInfo();
		
		title.setText(strings.get(0));
		
		if (strings.get(1) == null) detail.setVisibility(View.GONE);
			else detail.setText(strings.get(1));
		
		if (strings.get(2) == null) due.setVisibility(View.GONE);
			else due.setText("Due: " + strings.get(2));
		
		
		
        if (this.selection.get(position) != null)
            convertView.findViewById(R.id.noteItemBackground).setBackgroundColor(0x4000ddff);
        else
        	convertView.findViewById(R.id.noteItemBackground).setBackgroundColor(0xffffffff);
		
		// Ship it back
		return convertView;
	}

	public int size()
	{
		if (!archive)
			return data.size();
		else
			return data.sizeArchive();
	}

	public String digestSelectionToString()
	{
		ArrayList<Integer> indicies = new ArrayList<Integer>(this.selection.keySet());
		String result = "";
		
		for (int i : indicies)
		{
			ArrayList<String> strs = data.get(i).getInfo();
			for (String s : strs)
				if (s != null)
					result += (s + ". ");
			result += "\n";
		}
		
		return result;
	}

	public String digestEverythingToString()
	{
		String result = "/////Notes/////\n";
		int i = 0;
		while (i < this.data.size())
		{
			for (String s : this.data.get(i).getInfo())
				if (s != null)
					result += (s + ". ");
			result += "\n";
			i++;
		}
		
		result += "/////Archive/////\n";
		i = 0;
		while (i < this.data.sizeArchive())
		{
			for (String s : this.data.getFromArchive(i).getInfo())
				if (s != null)
					result += (s + ". ");
			result += "\n";
			i++;
		}
		
		return result;
	}

}
