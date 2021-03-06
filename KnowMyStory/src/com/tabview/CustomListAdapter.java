/*
 * Know My Story 
 */
package com.tabview;

import java.util.ArrayList;

import net.onehope.mystory.R;
import android.app.Activity;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<String> {

	private final Activity context;
	private final ArrayList<String> itemname;
	private final int imgid;

	public Typeface abel_ttf;

	public CustomListAdapter(Activity context, ArrayList<String> a, int icLauncher) {
		super(context, R.layout.bg_music, a);
		// TODO Auto-generated constructor stub

		this.context=context;
		this.itemname=a;
		this.imgid=icLauncher;
		abel_ttf=Typeface.createFromAsset(this.context.getAssets(), com.supportclasses.Constants.FONT_ABEL_REGULAR);
	}

	public View getView(int position,View view,ViewGroup parent) {
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.bg_music, null,true);

		TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);
		txtTitle.setTypeface(abel_ttf);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		// TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
		
		//String musicNames = DisplayMusicnames(itemname[position]);
		
		
		
		txtTitle.setText(itemname.get(position));
		
		//imageView.setImageResource(imgid[position]);
		//extratxt.setText("Description "+itemname[position]);
		return rowView;

	}


}
