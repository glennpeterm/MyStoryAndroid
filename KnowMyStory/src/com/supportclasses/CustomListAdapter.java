/*
 * Know My Story 
 */
package com.supportclasses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import net.onehope.mystory.R;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;


public class CustomListAdapter extends ArrayAdapter<String> implements SectionIndexer {

	HashMap<String, Integer> mapIndex;
	String[] sections;
	List<String> region;

	public CustomListAdapter(Context context, List<String> regionList) {
		super(context, R.layout.spinner_item, regionList);

		this.region = regionList;
		mapIndex = new LinkedHashMap<String, Integer>();

		for (int x = 0; x < region.size(); x++) {
			String regionlist = region.get(x);
			String ch = regionlist.substring(0, 1);
			ch = ch.toUpperCase(Locale.US);
			

			// HashMap will prevent duplicates
			if( !mapIndex.containsKey(ch) )
			mapIndex.put(ch, x);
		}

		Set<String> sectionLetters = mapIndex.keySet();

		// create a list from the set to sort
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		
		Log.d("sectionList", sectionList.toString());
		Collections.sort(sectionList);

		sections = new String[sectionList.size()];

		sectionList.toArray(sections);
	}

	public int getPositionForSection(int section) {
		Log.d("getPositionForSection section", "" + section);
		
		return mapIndex.get(sections[section]);
	}

	public int getSectionForPosition(int position) {
		Log.d("getSectionForPosition position", "" + position);
		return 0;
	}

	public Object[] getSections() {
		return sections;
	}
}
