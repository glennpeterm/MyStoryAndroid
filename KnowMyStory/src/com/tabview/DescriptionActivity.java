/*
 * Know My Story 
 */
package com.tabview;

import com.database.DatabaseHelper;
import com.database.Model;
import net.onehope.mystory.R;
import com.supportclasses.Constants;
import com.test.socialnetwork.AboutMyStory_Activity;
import com.test.socialnetwork.HomeScreen;
import com.videoeditor.media.VideoPreviewOption;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends Activity implements OnClickListener, TextWatcher {
	EditText edittext_tags;
	EditText edittext_desc;
	TextView tvDescriptionHeading,tvTag;
	Button btn_next;
	Button btn_home;
	String tagValue;
	String descValue;
	TabViewActivity tabViewActivity; 
	// Database Helper
	DatabaseHelper db;
	ImageView ivMandatory_videoTag;
	ImageView ivMandatory_videodesc;
	
	public Typeface abel_ttf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.description);
		initializeObjects();
		setTypeFonts();
		setOnClickListners();
		setInitialValues();
	}
	private void setInitialValues() {
		tabViewActivity.enabletab(0);
		tabViewActivity.enabletab(1);
		tabViewActivity.enabletab(2);
		tabViewActivity.enabletab(3);
		tabViewActivity.enabletab(4);
		tabViewActivity.enabletab(5);
		tabViewActivity.enabletab(6);
		tabViewActivity.enabletab(7);
	}
	private void setOnClickListners() {
		btn_next.setOnClickListener(this);
		btn_home.setOnClickListener(this);
	}

	private void getDescValues() {
		descValue = edittext_desc.getText().toString();
		System.out.println("desc: "+descValue);

	}

	private void initializeObjects() {
		edittext_tags = (EditText) findViewById(R.id.enter_tags);
		edittext_tags.addTextChangedListener(this);
		edittext_desc = (EditText) findViewById(R.id.enter_desc);
		edittext_desc.addTextChangedListener(this);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_home = (Button) findViewById(R.id.btn_home);
		db = new DatabaseHelper(this);
		tvTag=(TextView)findViewById(R.id.textview_description);
		tabViewActivity = new TabViewActivity();
		ivMandatory_videoTag=(ImageView)findViewById(R.id.ivVideoTag);
		ivMandatory_videodesc=(ImageView)findViewById(R.id.ivVideoDesc);
		tvDescriptionHeading=(TextView)findViewById(R.id.tvDescriptionHeading);
	}
	
	private void setTypeFonts()
	{
		abel_ttf=Typeface.createFromAsset(DescriptionActivity.this.getAssets(), "fonts/Abel-Regular.ttf");
		tvDescriptionHeading.setTypeface(abel_ttf/*,Typeface.BOLD*/);
		edittext_tags.setTypeface(abel_ttf);
		edittext_desc.setTypeface(abel_ttf);
		tvTag.setTypeface(abel_ttf);
	}
	

	private void getTagvalues() {
		tagValue = edittext_tags.getText().toString();

	}
	private boolean isEmpty(EditText etText) 
	{
		if (etText.getText().toString().trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next: {
			{
				if((isEmpty(edittext_tags))&&(isEmpty(edittext_desc)))
				{
					ivMandatory_videoTag.setVisibility(View.VISIBLE);
					ivMandatory_videodesc.setVisibility(View.VISIBLE);
				}
				else if(isEmpty(edittext_tags))
				{
					ivMandatory_videoTag.setVisibility(View.VISIBLE);
					ivMandatory_videodesc.setVisibility(View.INVISIBLE);
				}
				else if(isEmpty(edittext_desc))
				{
					ivMandatory_videodesc.setVisibility(View.VISIBLE);
					ivMandatory_videoTag.setVisibility(View.INVISIBLE);
				}
				else
				{
					ivMandatory_videoTag.setVisibility(View.INVISIBLE);
					ivMandatory_videodesc.setVisibility(View.INVISIBLE);
					Constants.SET_CURRENT_TAB = 8;
					//Set the track of Enabled tabs. 
					Constants.ENABLE_TAB8 = 1;
					tabViewActivity.setCurrentActivityTab(8);
					tabViewActivity.enabletab(8);
					if(db.getCompletedScreens()<8){
					db.updateCompletedScreen(8);
					}
					InsertIntoDB();
				}
			}

			/*Constants.SET_CURRENT_TAB = 8;
			TabViewActivity.setCurrentActivityTab(8);
			tabViewActivity.enabletab(8);
			InsertIntoDB();*/
		}

			break;
		case R.id.btn_home: {
			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SHOW_PREVIEW_TAB2 = 0;
			Constants.SHOW_PREVIEW_TAB3 = 0;
			Constants.SHOW_PREVIEW_TAB4 = 0;
			Constants.SHOW_PREVIEW_TAB5 = 0;*/
			Intent intent_home = new Intent(DescriptionActivity.this,
					HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
		}
			break;
		default:
			break;
		}
	}

	private void InsertIntoDB() {
		getTagvalues();
		getDescValues();
		if(descValue!=null){
			
			db.insertDescription(descValue);
		}
		if(tagValue!=null){
			db.insertTags(tagValue);
		}
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		Constants.SET_CURRENT_TAB = 7;
		if(db.getDescription()!=null){
			if(!(db.getDescription().equals(""))){
				edittext_desc.setText(db.getDescription());
			}
		}
		if(db.getTags()!=null){
			if(!(db.getTags().equals(""))){
				edittext_tags.setText(db.getTags());
			}
		}
	}
	@Override
	protected void onPause() {
		
		super.onPause();
		System.out.println("onPause DescActivity...");
		if(db.loginCount()>0){
			
			InsertIntoDB();
		}
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
		
	}
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		
		
	}
	@Override
	public void afterTextChanged(Editable s) {
		String a = s.toString();
		System.out.println("Desc..afterTextChanged: "+a);
			/*if(a.equals("")){
				System.out.println("Desc..no text..");
				tabViewActivity.DisableTabs();
			}else{
				if(Constants.ENABLE_TAB1 == 1){
				tabViewActivity.enabletab(1);
				}
				
				if(Constants.ENABLE_TAB2 == 1){
					tabViewActivity.enabletab(2);
				}
				if(Constants.ENABLE_TAB3 == 1){
					tabViewActivity.enabletab(3);
				}
				if(Constants.ENABLE_TAB4 == 1){
					tabViewActivity.enabletab(4);
				}
				if(Constants.ENABLE_TAB5 == 1){
					tabViewActivity.enabletab(5);
				}
				if(Constants.ENABLE_TAB6 == 1){
					tabViewActivity.enabletab(6);
				}
				if(Constants.ENABLE_TAB7 == 1){
					tabViewActivity.enabletab(7);
				}
				if(Constants.ENABLE_TAB8 == 1){
					tabViewActivity.enabletab(8);
				}
				if(Constants.ENABLE_TAB9 == 1){
					tabViewActivity.enabletab(9);
				}
			}*/
		
	}
}
