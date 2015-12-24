package com.dialog;


import com.allchannelvideos.SelfieVideos;
import com.analytics.util.SendAnalytics;
import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import com.database.Model;

import net.onehope.mystory.R;

import com.supportclasses.Constants;
import com.tabview.Tab1Activity;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.AboutMyStory_Activity;
import com.test.socialnetwork.HomeScreen;
import com.test.socialnetwork.LoginActivity;
import com.test.socialnetwork.Upload_Progress_Activity;
import com.videoeditor.media.CustomCameraActivity;
import com.videoeditor.media.VideoPreviewOption;
import com.videoeditor.util.FileUtils;

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
 * Dialog activity class to display dialog messages
 */
public class DialogActivity extends ActivityGroup implements OnClickListener
{
	// Declare the variables and the objects used
	TextView tvVerse,tvVerseIndex;
	Button btnCancel,btnSelectVerse;

	DatabaseCreator db;
	DatabaseHelper dbHelper;
	
	public Typeface abel_ttf;
	Button btn_ok;
	TextView tv_text;
	TextView tv_header;
	DatabaseHelper dbHelp ;

	SendAnalytics analytics_conveyer;
	
	
	@Override
	public void onBackPressed() {
	
	}
	
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		
		analytics_conveyer = new SendAnalytics(DialogActivity.this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.errordialog);
		getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		dbHelp = new DatabaseHelper(this);
		this.setFinishOnTouchOutside(false);
		System.out.println(" verse activity");
		initializeComponents();
		setFontStyle();
		setClickListeners();
	
	}

	private void initializeComponents()
	{
		btn_ok = (Button)findViewById(R.id.btn_ok);
		tv_header = (TextView)findViewById(R.id.text_header);
		tv_text = (TextView)findViewById(R.id.text);
		
		String dialog_msg = getIntent().getStringExtra("DIALOG_MESSAGE_VALID_KEYWORD");
		if(dialog_msg!=null){
			if(dialog_msg.equals("valid_keyword")){
				tv_text.setText(getResources().getString(R.string.online_message));
			}else if(dialog_msg.equals("text_empty")){
				tv_text.setText(getResources().getString(R.string.text_empty_error));
			}else if(dialog_msg.equals("trim_activity")){
				tv_text.setText(getResources().getString(R.string.trim_length_error));
			}else if(dialog_msg.equals("Trim_not_possible")){
				tv_text.setText(getResources().getString(R.string.trim_not_possible));
			}else if(dialog_msg.equals("Upload_success_message")){
				tv_header.setText(getResources().getString(R.string.upload_success_header));
				tv_text.setText(getResources().getString(R.string.upload_success));
				analytics_conveyer.sendScreenandEvent(com.supportclasses.Constants.UAC_SCREEN,com.supportclasses.Constants.UAC_CATEGORY, com.supportclasses.Constants.UAC_ACTION, com.supportclasses.Constants.UAC_LABEL);
			
				
				dbHelp.updateYouTubeUploadProgress("100");
				FileUtils.deleteDir(DialogActivity.this);
				dbHelp.updateMergedVideo(null);

				dbHelp.updateCompletedScreen(0);
				dbHelp.UpdateServerVideoUploadStatus(0);
				com.supportclasses.Constants.ENABLED_TABS_STATE = 0;
				com.supportclasses.Constants.SET_VERSE_FLAG = 0;
				FileUtils.deleteDir(this);
				com.supportclasses.Constants.SET_CURRENT_TAB = 0;
				com.supportclasses.Constants.SET_TOPIC_SELECTED = 0;
				
				/*
				 * Delete all values from row
				 */
				dbHelp.updateSelectedBGMusic(null);
				dbHelp.updateStoryName(new Model(""));
				dbHelp.updateTopics(null);
				dbHelp.updateTopicPosition(null);
				dbHelp.insertDescription(null);
				dbHelp.insertTags(null);
				dbHelp.insertLanguage(null);
				dbHelp.insertCountry(null);
				
				dbHelp.updateVideo1("");
				dbHelp.updateVideo2("");
				dbHelp.updateVideo3("");
				dbHelp.updateVideo4("");
				dbHelp.updateVideo5("");
				//int rows = db.deleteAllRows();
				
				//Constants.SHOW_PREVIEW_TAB1 = 0;
				dbHelp.updateShowPreviewTab1(0);
				dbHelp.updateShowPreviewTab2(0);
				dbHelp.updateShowPreviewTab3(0);
				dbHelp.updateShowPreviewTab4(0);
				dbHelp.updateShowPreviewTab5(0);
//				Constants.SHOW_PREVIEW_TAB2 = 0;
//				Constants.SHOW_PREVIEW_TAB3 = 0;
//				Constants.SHOW_PREVIEW_TAB4 = 0;
//				Constants.SHOW_PREVIEW_TAB5 = 0;
				
				Constants.ENABLE_TAB1 = 0;
				Constants.ENABLE_TAB2 = 0;
				Constants.ENABLE_TAB3 = 0;
				Constants.ENABLE_TAB4 = 0;
				Constants.ENABLE_TAB5 = 0;
				Constants.ENABLE_TAB6 = 0;
				Constants.ENABLE_TAB7 = 0;
				Constants.ENABLE_TAB8 = 0;
				Constants.ENABLE_TAB9 = 0;
				
				
			
				
				
			}else if(dialog_msg.equals("no_network")){
				tv_text.setText(getResources().getString(R.string.network_conn_error));
			}else if(dialog_msg.equals("video_not_recorded")){
				tv_text.setText(getResources().getString(R.string.video_not_recorded));
			}else if(dialog_msg.equals("no_network_loginactivity")){
				tv_text.setText(getResources().getString(R.string.network_conn_error));
			}else if(dialog_msg.equals("Upload_failed_message")){
				tv_text.setText(getResources().getString(R.string.upload_heading_fail));
			}else if(dialog_msg.equals("low_memory")){
				tv_text.setText(getResources().getString(R.string.no_enuf_mem));
			}
		}else{
			tv_text.setText(getResources().getString(R.string.userdetail_update_fail));
		}
		
		
		//tv_header.setText(getResources().getString(R.string.short_app_name));
		tv_text.setPadding(2, 2, 2, 2);
		btn_ok.setPadding(2, 2, 2, 2);
	}

	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(DialogActivity.this.getAssets(),Constants.FONT_ABEL_REGULAR);
		tv_header.setTypeface(abel_ttf,Typeface.BOLD);
		btn_ok.setTypeface(abel_ttf);
		tv_text.setTypeface(abel_ttf);
	}

	private void setClickListeners()
	{
		btn_ok.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.btn_ok:
			
			
			finish();
			
			String dialog_msg = getIntent().getStringExtra("DIALOG_MESSAGE_VALID_KEYWORD");
			if(dialog_msg!=null){
				if(dialog_msg.equals("Upload_success_message")){
					Intent intent = new Intent(this, SelfieVideos.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					
					
				}else if(dialog_msg.equals("Upload_failed_message")){
					// Do Nothing ..User has to re-upload.
					if(Upload_Progress_Activity.fa!=null){
					Upload_Progress_Activity.fa.finish();
					}
					if(LoginActivity.login_activity!=null){
						LoginActivity.login_activity.finish();
					}
				}else if(dialog_msg.equals("no_network")){
					Tab1Activity.setTopicHolder();
				}
			}
			break;
		

		default:
			break;
		}
	}
	
}