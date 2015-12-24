package com.scriptures;

import java.util.ArrayList;
import java.util.List;

import net.onehope.mystory.R;
import com.scriptures.ScriptureOfflineActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter class to get the items in offline list 
 */
public class CustomAdapter_VerseOffline extends BaseAdapter implements OnClickListener 
{
	/*********** Declare Used Variables *********/
	private Activity activity;
	private ArrayList data;
	private static LayoutInflater inflater=null;
	public Resources res;
	VerseModal tempValues=null;
	int i=0;
	
	ViewHolder holder;
	
	public Typeface abel_ttf;

	/*************  CustomAdapter Constructor *****************/
	public CustomAdapter_VerseOffline(Activity a, ArrayList d,Resources resLocal) {

		/********** Take passed values **********/
		activity = a;
		data=d;
		res = resLocal;

		/***********  Layout inflator to call external xml layout () ***********/
		inflater = ( LayoutInflater )activity.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		abel_ttf=Typeface.createFromAsset(a.getAssets(), "fonts/Abel-Regular.ttf");
	}

	/******** What is the size of Passed Arraylist Size ************/
	public int getCount() {

		if(data.size()<=0)
			return 1;
		return data.size();
	}

	/******** gets the item in the particular position************/
	public Object getItem(int position)
	{
		return position;
	}

	/******** gets the id of the item in the particular position************/
	public long getItemId(int position) 
	{
		return position;
	}

	/********* Create a holder Class to contain inflated xml file elements *********/
	public static class ViewHolder{

		public TextView textSl;
		public TextView textIndex;
		public TextView textVerse;

	}

	/****** Depends upon data size called for each row , Create each ListView row *****/
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;


		if(convertView==null){

			/****** Inflate tabitem.xml file for each row ( Defined below ) *******/
			vi = inflater.inflate(R.layout.custom_verse_display, null);

			/****** View Holder Object to contain tabitem.xml file elements ******/

			holder = new ViewHolder();
			holder.textSl = (TextView) vi.findViewById(R.id.tvSlNo);
			holder.textSl.setTypeface(abel_ttf);
			holder.textIndex=(TextView)vi.findViewById(R.id.tvVerseIndex);
			holder.textIndex.setTypeface(abel_ttf);
			holder.textVerse=(TextView)vi.findViewById(R.id.tvVerseText);
			holder.textVerse.setTypeface(abel_ttf);
			/************  Set holder with LayoutInflater ************/
			vi.setTag( holder );
		}
		else 
			holder=(ViewHolder)vi.getTag();

		if(data.size()<=0)
		{
			//	holder.text.setText("No Data");

		}
		else
		{
			/***** Get each Model object from Arraylist ********/
			tempValues=null;
			tempValues = ( VerseModal ) data.get( position );

			/************  Set Model values in Holder elements ***********/
			if(tempValues.getId() <10)
			{
				holder.textSl.setText( "0"+tempValues.getId() +".");
			}
			else
			{
				holder.textSl.setText( tempValues.getId()+"." );
			}
			System.out.println(" \n id "+tempValues.getId());
			holder.textIndex.setText( tempValues.getVerseIndex() );
			holder.textVerse.setText(tempValues.getVerseText());

			/******** Set Item Click Listner for LayoutInflater for each row *******/

			vi.setOnClickListener(new OnItemClickListener(tempValues.getVerseIndex() ,tempValues.getVerseText(),position ));
			
		}
		return vi;
	}

	@Override
	public void onClick(View v) {
		Log.v("CustomAdapter", "=====Row button clicked=====");
	}

	/********* Called when Item click in ListView ************/
	private class OnItemClickListener  implements OnClickListener{           
		private int mPosition;
		private String verse_text;
		private String Vers_ind;

		OnItemClickListener(String v,String t,int position){
			mPosition = position;
			Vers_ind=v;
			verse_text=t;
		}

		@Override
		public void onClick(View arg0) {


			ScriptureOfflineActivity sct = (ScriptureOfflineActivity)activity;

			/****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

			sct.onOfflineItemClick(Vers_ind,verse_text,mPosition);
			
			//arg0.setActivated(true);
		}               
	}   
}