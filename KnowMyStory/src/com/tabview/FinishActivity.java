package com.tabview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.database.DatabaseHelper;
import com.database.Model;
import com.database.menu.Database;
import net.onehope.mystory.R;
import com.google.ytdl.Constants;
import com.videoeditor.util.FileUtils;

public class FinishActivity extends Activity implements OnClickListener
{

	private Dialog custom_dialog;
	Button btnYes,btnNo;
	Button btnDeleteStartOver,btnLater,btnFinishNow;
	TextView tvFinish,tvText,tvDialogHead,tvDialogText;
	DatabaseHelper db;
	
	public Typeface abel_ttf;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//getActionBar().hide();
		setContentView(R.layout.finish_story_activity);
		initializeComponents();
		setFontStyle();
		setClickListeners();
		IniAppointmentDialog();
	}

	public void showConfirmDialog()
	{

		custom_dialog.show();

	}

	private void setClickListeners()
	{
		btnDeleteStartOver.setOnClickListener(this);
		btnLater.setOnClickListener(this);
		btnFinishNow.setOnClickListener(this);
	}

	private void initializeComponents()
	{
		db = new DatabaseHelper(this);
		btnDeleteStartOver=(Button)findViewById(R.id.btn_delete);
		btnLater=(Button)findViewById(R.id.btn_finishlater);
		btnFinishNow=(Button)findViewById(R.id.btn_finishnow);
		tvFinish=(TextView)findViewById(R.id.tvFinishHeading);
		tvText=(TextView)findViewById(R.id.tvFinishText);
	}
	
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(FinishActivity.this.getAssets(), com.supportclasses.Constants.FONT_ABEL_REGULAR);
		btnDeleteStartOver.setTypeface(abel_ttf);
		btnLater.setTypeface(abel_ttf);
		btnFinishNow.setTypeface(abel_ttf);
		tvFinish.setTypeface(abel_ttf,Typeface.BOLD);
		tvText.setTypeface(abel_ttf);
	}

	public void IniAppointmentDialog()
	{
		custom_dialog = new Dialog(FinishActivity.this);
		custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		custom_dialog.setCancelable(false);
		custom_dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
		custom_dialog.setContentView(R.layout.confimation_dialog);
		custom_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		tvDialogHead=(TextView)custom_dialog.findViewById(R.id.tvDelete);
		tvDialogHead.setTypeface(abel_ttf,Typeface.BOLD);
		tvDialogText=(TextView)custom_dialog.findViewById(R.id.tvDeleteText);
		tvDialogText.setTypeface(abel_ttf);
		btnYes=(Button)custom_dialog.findViewById(R.id.btn_yes);
		btnYes.setTypeface(abel_ttf);
		btnNo=(Button)custom_dialog.findViewById(R.id.btn_No);
		btnNo.setTypeface(abel_ttf);
		btnYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				db.updateCompletedScreen(0);
				db.UpdateServerVideoUploadStatus(0);
				com.supportclasses.Constants.ENABLED_TABS_STATE = 0;
				com.supportclasses.Constants.SET_VERSE_FLAG = 0;
				FileUtils.deleteDir(FinishActivity.this);
				com.supportclasses.Constants.SET_CURRENT_TAB = 0;
				com.supportclasses.Constants.SET_TOPIC_SELECTED = 0;
				
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
				//System.out.println("ROWS: "+rows);
				
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 0;
				db.updateShowPreviewTab1(0);
				db.updateShowPreviewTab2(0);
				db.updateShowPreviewTab3(0);
				db.updateShowPreviewTab4(0);
				db.updateShowPreviewTab5(0);
				
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 0;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 0;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 0;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB5 = 0;
				
				com.supportclasses.Constants.ENABLE_TAB1 = 0;
				com.supportclasses.Constants.ENABLE_TAB2 = 0;
				com.supportclasses.Constants.ENABLE_TAB3 = 0;
				com.supportclasses.Constants.ENABLE_TAB4 = 0;
				com.supportclasses.Constants.ENABLE_TAB5 = 0;
				com.supportclasses.Constants.ENABLE_TAB6 = 0;
				com.supportclasses.Constants.ENABLE_TAB7 = 0;
				com.supportclasses.Constants.ENABLE_TAB8 = 0;
				com.supportclasses.Constants.ENABLE_TAB9 = 0;
				
				Intent i = new Intent(FinishActivity.this,TabViewActivity.class);
				startActivity(i);
				custom_dialog.dismiss();
				
				finish();

			}
		});
		btnNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				custom_dialog.dismiss();
			}
		} );
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId())
		{
		case R.id.btn_delete:
			
			showConfirmDialog();
			break;

		case R.id.btn_finishlater:
			finish();

			break;

		case R.id.btn_finishnow:{
		
			int CompletedScreen = db.getCompletedScreens();
			System.out.println("Completed screen: "+CompletedScreen);
			switch (CompletedScreen) {
			case 1:
				System.out.println("Finish_tab1");
				com.supportclasses.Constants.SET_CURRENT_TAB = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 1;
				break;
			case 2:
				System.out.println("Finish_tab2");
				com.supportclasses.Constants.SET_CURRENT_TAB = 2;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				db.updateShowPreviewTab2(1);
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 2;
				break;
			case 3:
				System.out.println("Finish_tab3");
				com.supportclasses.Constants.SET_CURRENT_TAB = 3;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				db.updateShowPreviewTab3(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
				db.updateShowPreviewTab2(1);
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 3;
				//TabViewActivity.setCurrentActivityTab(3);
				break;
			case 4:
				System.out.println("Finish_tab4");
				com.supportclasses.Constants.SET_CURRENT_TAB = 4;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 1;
				db.updateShowPreviewTab4(1);
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
				db.updateShowPreviewTab2(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
				db.updateShowPreviewTab3(1);
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 4;
				break;
			case 5:
				System.out.println("Finish_tab5");
				com.supportclasses.Constants.SET_CURRENT_TAB = 5;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB5 = 1;
				db.updateShowPreviewTab5(1);
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
				db.updateShowPreviewTab2(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
				db.updateShowPreviewTab3(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 1;
				db.updateShowPreviewTab4(1);
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 5;
				break;
			case 6:
				System.out.println("Finish_tab6");
				com.supportclasses.Constants.SET_CURRENT_TAB = 6;
				
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				db.updateShowPreviewTab2(1);
				db.updateShowPreviewTab3(1);
				db.updateShowPreviewTab4(1);
				db.updateShowPreviewTab5(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB5 = 1;
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 6;
				break;
			case 7:
				System.out.println("Finish_tab7");
				com.supportclasses.Constants.SET_CURRENT_TAB = 7;
				
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				db.updateShowPreviewTab2(1);
				db.updateShowPreviewTab3(1);
				db.updateShowPreviewTab4(1);
				db.updateShowPreviewTab5(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB5 = 1;
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 7;
				break;
			case 8:
				System.out.println("Finish_tab8");
				com.supportclasses.Constants.SET_CURRENT_TAB = 8;
				
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				db.updateShowPreviewTab2(1);
				db.updateShowPreviewTab3(1);
				db.updateShowPreviewTab4(1);
				db.updateShowPreviewTab5(1);
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 1;
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB5 = 1;
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 8;
				break;
			case 9:
				System.out.println("Finish_tab9");
				com.supportclasses.Constants.SET_CURRENT_TAB = 9;
				
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				db.updateShowPreviewTab2(1);
				db.updateShowPreviewTab3(1);
				db.updateShowPreviewTab4(1);
				db.updateShowPreviewTab5(1);
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 1;
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB5 = 1;
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 9;
				break;

			case 10:
				System.out.println("Finish_tab10");
				com.supportclasses.Constants.SET_CURRENT_TAB = 10;
				
				//Other tabs 
				//com.supportclasses.Constants.SHOW_PREVIEW_TAB1 = 1;
				db.updateShowPreviewTab1(1);
				db.updateShowPreviewTab2(1);
				db.updateShowPreviewTab3(1);
				db.updateShowPreviewTab4(1);
				db.updateShowPreviewTab5(1);
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB2 = 1;
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB3 = 1;
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB4 = 1;
//				com.supportclasses.Constants.SHOW_PREVIEW_TAB5 = 1;
				
				//Keep the state of Enabled tabs:
				com.supportclasses.Constants.ENABLED_TABS_STATE = 10;
				break;
			default:
				System.out.println("Finish_tab_default");
				com.supportclasses.Constants.SET_CURRENT_TAB = 0;
				break;
			}
			Intent i = new Intent(FinishActivity.this,TabViewActivity.class);
			i.putExtra("FinishStory", "FromFinisnStory");
			startActivity(i);
			
			finish();
		}
			break;

		default:
			break;
		}


	}
}
