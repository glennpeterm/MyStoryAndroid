package com.dialog;

import android.annotation.SuppressLint;
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

import com.allchannelvideos.ChannelVideos;
import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import net.onehope.mystory.R;
import com.supportclasses.Constants;
import com.test.socialnetwork.HomeScreen;

/**
 * Dialog activity class to display dialog messages
 */
public class ReloadDialogActivity extends ActivityGroup implements OnClickListener
{
	// Declare the variables and the objects used
	TextView tvVerse,tvVerseIndex;
	Button btnCancel,btnSelectVerse;

	DatabaseCreator db;
	DatabaseHelper dbHelper;

	public Typeface abel_ttf;
	Button btn_tryagain,btn_cancel;
	TextView tv_text;
	TextView tv_header;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.watch_dialog);
		getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		this.setFinishOnTouchOutside(false);
		System.out.println(" verse activity");
		initializeComponents();
		setFontStyle();
		setClickListeners();

	}

	/**
	 * Function to initialize the components
	 */
	private void initializeComponents()
	{
		btn_tryagain = (Button)findViewById(R.id.btn_try_again);
		btn_cancel=(Button)findViewById(R.id.btn_cancel);
		tv_header = (TextView)findViewById(R.id.text_header);
		tv_text = (TextView)findViewById(R.id.text);

		//tv_header.setText(getResources().getString(R.string.short_app_name));
		tv_text.setPadding(2, 2, 2, 2);
		//		btn_ok.setPadding(2, 2, 2, 2);
	}

	/**
	 * Function to set custom font
	 */
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(ReloadDialogActivity.this.getAssets(),Constants.FONT_ABEL_REGULAR);
		tv_header.setTypeface(abel_ttf,Typeface.BOLD);
		btn_cancel.setTypeface(abel_ttf);
		btn_tryagain.setTypeface(abel_ttf);
		tv_text.setTypeface(abel_ttf);
	}

	/**
	 * Function to set up listeners for the buttons
	 */
	private void setClickListeners()
	{
		btn_tryagain.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.btn_cancel:
			Constants.WATCH_LOADING = 2;
			finish();
			break;
		case R.id.btn_try_again:
			//	finish();
			/*	ChannelVideos ch= new ChannelVideos();
			ch.CallWebViewAsync();*/
			Constants.VERSE_LOADING = 1;
			Constants.WATCH_LOADING = 1;
			//Intent intent_watch=new Intent(ReloadDialogActivity.this,ChannelVideos.class);
			//startActivity(intent_watch);
			finish();
			break;

		default:
			break;
		}
	}

}