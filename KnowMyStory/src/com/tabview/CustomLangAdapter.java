/*
 * Know My Story 
 */
package com.tabview;

import net.onehope.mystory.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomLangAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private String[] language;

	public CustomLangAdapter(Activity context, String[] language, int icLauncher) {
		super(context, R.layout.bg_music, language);
		// TODO Auto-generated constructor stub

		this.context = context;
		this.language = language;
		// this.imgid=icLauncher;
	}
	 public View getView(int position,View view,ViewGroup parent) {
		 LayoutInflater inflater=context.getLayoutInflater();
		 View rowView=inflater.inflate(R.layout.bg_music, null,true);
		 
		 TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
		// ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		// TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
		 
		 String musicNames = language[position];
		 txtTitle.setText(musicNames);
		 //imageView.setImageResource(imgid[position]);
		 //extratxt.setText("Description "+itemname[position]);
		 return rowView;
		 
		 }
	
}
