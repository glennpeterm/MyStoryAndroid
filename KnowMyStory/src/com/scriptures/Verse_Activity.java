package com.scriptures;

import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import com.dialog.LoaderActivity;
import net.onehope.mystory.R;
import com.supportclasses.Constants;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.AboutMyStory_Activity;
import com.videoeditor.media.CustomCameraActivity;
import com.videoeditor.media.VideoPreviewOption;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class for displaying the selected verse
 */
public class Verse_Activity extends ActivityGroup implements OnClickListener
{
	// Declare the variables and the objects used
	TextView tvVerse,tvVerseIndex;
	Button btnCancel,btnSelectVerse;

	DatabaseCreator db;
	DatabaseHelper dbHelper;
	ScriptureOfflineActivity scriptureActivity;
	//LoaderActivity loaderActivity = new LoaderActivity();
	public Typeface abel_ttf;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.verse_screen);
		if(LoaderActivity.loader!=null){
			LoaderActivity.loader.finish();
		}
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setFinishOnTouchOutside(false);
		System.out.println(" verse activity");
		initializeComponents();
		setFontStyle();
		setClickListeners();
		Bundle bundle = getIntent().getExtras();
		tvVerse.setText(bundle.getString("verse_text"));
		tvVerseIndex.setText(bundle.getString("verse_index"));
	}

	/**
	 * Function to initialize the components
	 */
	private void initializeComponents()
	{
		tvVerse=(TextView)findViewById(R.id.tvVerse);
		tvVerseIndex=(TextView)findViewById(R.id.tvVerseIndex);

		btnCancel=(Button)findViewById(R.id.btn_Cancel);
		btnSelectVerse=(Button)findViewById(R.id.btn_select);

		db = new DatabaseCreator(this);
		dbHelper = new DatabaseHelper(this);
	}

	/**
	 * Function to set custom font
	 */
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(Verse_Activity.this.getAssets(),Constants.FONT_ABEL_REGULAR);
		tvVerse.setTypeface(abel_ttf);
		tvVerseIndex.setTypeface(abel_ttf,Typeface.BOLD);
		
		btnCancel.setTypeface(abel_ttf);
		btnSelectVerse.setTypeface(abel_ttf);
	}

	/**
	 * Function to set up listeners for the buttons
	 */
	private void setClickListeners()
	{
		btnCancel.setOnClickListener(this);
		btnSelectVerse.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.btn_Cancel:
			finish();
			break;
		case R.id.btn_select:
			
			System.out.println("index print "+getIntent().getExtras().getString("verse_index"));
			//db.updateSelection( getIntent().getExtras().getString("verse_index"),  getIntent().getExtras().getString("verse_text"));
			dbHelper.updateSelection( getIntent().getExtras().getString("verse_index"),  getIntent().getExtras().getString("verse_text"));
			Constants.SET_VERSE_FLAG = 1;
			//	System.out.println("print i \n  "+i);
			//System.out.println("index is \n"+db.getIndexText());
			finish();
			//Constants.SET_CURRENT_TAB = 4;
			//TabViewActivity.setCurrentActivityTab(4);

			break;

		default:
			break;
		}
	}
	
}