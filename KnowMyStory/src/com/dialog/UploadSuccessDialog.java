package com.dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.database.DatabaseHelper;
import com.database.Model;
import net.onehope.mystory.R;
import com.google.ytdl.UploadService;
import com.supportclasses.Constants;
import com.supportclasses.NoNetworkDialog;
import com.tabview.FinishActivity;
import com.tabview.Tab1Activity;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.HomeScreen;
import com.test.socialnetwork.Upload_Progress_Activity;
import com.videoeditor.util.FileUtils;

import android.graphics.Typeface;

/**
 * Dialog fragment to display dialog messages
 */
public class UploadSuccessDialog extends DialogFragment 
{
	// Declare the variables and the objects used
	static Context context;
	static String error_message;
	static String error_heading;
	static String ActionMsg;
	Button btn_ok ;
	public Typeface abel_ttf;
	TextView textview,text_heading;
	DatabaseHelper db;

	/**
	 * Create an instance for the class
	 */
	public static UploadSuccessDialog newInstance(Context ctx, String message,String heading, String action) {
		context = ctx;
		error_message = message;
		error_heading=heading;
		ActionMsg = action;
		UploadSuccessDialog frag = new UploadSuccessDialog();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.upload_dialog, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		 setFontStyle();
		 text_heading=(TextView)v.findViewById(R.id.text_heading);
		 textview = (TextView)v.findViewById(R.id.text);
		
		text_heading.setText(error_heading);
		textview.setText(error_message);
		
		btn_ok = (Button) v.findViewById(R.id.btn_ok);

		textview.setTypeface(abel_ttf);
		text_heading.setTypeface(abel_ttf);
		btn_ok.setTypeface(abel_ttf);
		db = new DatabaseHelper(context);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
				if (ActionMsg.equals("doNothing")) {
					// Do Nothing
				}
				else if (ActionMsg.equals("go_back_page")) {
					System.out.println("go back page");
					getActivity().onBackPressed();
				}
				else if (ActionMsg.equals("homescreen_uploadSuccess")) {
					/*
					 * DatabaseHelper db = new DatabaseHelper(context);
					 * db.updateCompletedScreen(0); db.updateMergedVideo(null);
					 */
					
					db.updateCompletedScreen(0);
					com.supportclasses.Constants.ENABLED_TABS_STATE = 0;
					com.supportclasses.Constants.SET_VERSE_FLAG = 0;
					FileUtils.deleteDir(context);
					com.supportclasses.Constants.SET_CURRENT_TAB = 0;
					
					/*
					 * Delete all values from row
					 */
					db.updateSelectedBGMusic(null);
					db.updateStoryName(new Model(""));
					db.updateTopics(null);
					db.updateTopicPosition(null);
					db.insertDescription(null);
					db.insertTags(null);
					db.insertLanguage(null);
					db.insertCountry(null);
					
					db.updateVideo1("");
					db.updateVideo2("");
					db.updateVideo3("");
					db.updateVideo4("");
					db.updateVideo5("");
					//int rows = db.deleteAllRows();
					
					//Constants.SHOW_PREVIEW_TAB1 = 0;
					db.updateShowPreviewTab1(0);
					db.updateShowPreviewTab2(0);
					db.updateShowPreviewTab3(0);
					db.updateShowPreviewTab4(0);
					db.updateShowPreviewTab5(0);
//					Constants.SHOW_PREVIEW_TAB2 = 0;
//					Constants.SHOW_PREVIEW_TAB3 = 0;
//					Constants.SHOW_PREVIEW_TAB4 = 0;
//					Constants.SHOW_PREVIEW_TAB5 = 0;
					
					Constants.ENABLE_TAB1 = 0;
					Constants.ENABLE_TAB2 = 0;
					Constants.ENABLE_TAB3 = 0;
					Constants.ENABLE_TAB4 = 0;
					Constants.ENABLE_TAB5 = 0;
					Constants.ENABLE_TAB6 = 0;
					Constants.ENABLE_TAB7 = 0;
					Constants.ENABLE_TAB8 = 0;
					Constants.ENABLE_TAB9 = 0;
					
					Intent intent = new Intent(context, HomeScreen.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					
				} else if (ActionMsg.equals("homescreen")) {
					/*
					 * DatabaseHelper db = new DatabaseHelper(context);
					 * db.updateCompletedScreen(0); db.updateMergedVideo(null);
					 */
					Intent intent = new Intent(context, HomeScreen.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
				} else if (ActionMsg.equals("upload_failed")) {
					// Do Nothing ..User has to re-upload.
					if(Upload_Progress_Activity.fa!=null){
					Upload_Progress_Activity.fa.finish();
					}
				} else if (ActionMsg.equals("merge_failed")) {
					/*
					 * Re-merge videos
					 */
					com.supportclasses.Constants.SET_CURRENT_TAB = 9;
					// Keep the state of Enabled tabs:
					com.supportclasses.Constants.ENABLED_TABS_STATE = 9;
					Intent i = new Intent(context, TabViewActivity.class);
					i.putExtra("FinishStory", "FromFinisnStory");

					startActivity(i);
				} else if (ActionMsg.equals("no_network")) {
					// noNetworkActivity.finish();
					Intent i1 = new Intent(context, NoNetworkDialog.class);
					i1.putExtra("CloseActivity", "close");
					i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i1);

					/*
					 * Intent i = new Intent(context, UploadService.class);
					 * context.stopService(i);
					 */
				}

			}
		});

		return v;
	}
	
	/**
	 * Function to define the custom font
	 */
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(context.getAssets(), Constants.FONT_ABEL_REGULAR);
		
	}
}