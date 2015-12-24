/*
 * Know My Story 
 */
package com.videoeditor.media;

import com.database.DatabaseHelper;
import com.dialog.DialogActivity;
import com.dialog.ErrorDialog;
import com.supportclasses.CheckAvailableStorage;
import com.supportclasses.Constants;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.HomeScreen;
import com.trim.TrimActivity;
import net.onehope.mystory.R;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

import com.mediacontrols.VideoPlayerActivity;
public class VideoPreviewOption extends ActivityGroup implements OnClickListener {

	public static final String CURRENT_ACTIVITY_INFO = "current_activity_info";

	DatabaseHelper db;
	static String video1;
	Button btn_trim;
	Button btn_reRecord;
	String OPEN_CAM = "open_cam";
	String RERECORD = "video_reRecord";
	String VIDEO_URL = "video_url";
	Button button_PreviewPlay;
	Button btn_home;
	Button btn_next;
	public Typeface abel_ttf;
	MediaPlayer mp;
	//VideoView video;
	TabViewActivity tabViewActivity = new TabViewActivity();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorded_video_preview);
		db = new DatabaseHelper(this);
		 video1 = db.isVideo1Present();
		previewVideoView();
		
		db.updateCurrentScreen("open_cam_1");
		
		initializeObjects();
		setFonttypes();
		addOnClickListner();
		
		setInitialVlaues();
	
	}
	
	private void setInitialVlaues() {
		tabViewActivity.enabletab(0);
		tabViewActivity.enabletab(1);
		
	}

	private void initializeObjects() {
		button_PreviewPlay = (Button)findViewById(R.id.btn_preview_play);
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_trim = (Button)findViewById(R.id.btn_trim);
		btn_reRecord = (Button)findViewById(R.id.btn_rerecord);
		btn_home = (Button)findViewById(R.id.btn_home);
		//video = (VideoView)findViewById(R.id.video_preview);
	}

	private void setFonttypes()
	{
		abel_ttf=Typeface.createFromAsset(this.getAssets(), "fonts/Abel-Regular.ttf");
		btn_trim.setTypeface(abel_ttf);
		btn_reRecord.setTypeface(abel_ttf);

	}
	private void addOnClickListner() {
		button_PreviewPlay.setOnClickListener(this);
		btn_trim.setOnClickListener(this);
		btn_reRecord.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		btn_next.setOnClickListener(this);
	}
	private void previewVideoView() {
		System.out.println("video 1" +video1);
		 //video1 = db.isVideo1Present();
		String video1 = db.isVideo1Present();
		if(video1!=null){
			if(!(video1.equals(""))){
		Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(video1,
		        MediaStore.Images.Thumbnails.MINI_KIND);
		VideoView video = (VideoView)findViewById(R.id.video_preview);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(thumbnail);
		if(video!=null)
			video.setBackgroundDrawable(bitmapDrawable);
			}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_trim:
			video1 = db.isVideo1Present();
			int duration = getVideoDuration(video1);
			System.out.println("VideoDuration: "+duration);
			 
			if (duration > 2) {
				Intent intent = new Intent(this, TrimActivity.class);
				intent.putExtra("VIDEO1_PATH", video1);
				startActivity(intent);
			}else{
				Intent i = new Intent(VideoPreviewOption.this,DialogActivity.class);
				i.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Trim_not_possible");
				startActivity(i);
			}
			break;
		case R.id.btn_rerecord:
			/*Intent intent_reRecord = new Intent().setClass(this, CustomCameraActivity.class);
			db.updateCurrentScreen("open_cam_1");
			
			intent_reRecord.putExtra(OPEN_CAM, "open_cam_1");
			intent_reRecord.putExtra(RERECORD, true);
			startActivity(intent_reRecord);*/
			//db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
			//Constants.MERGED_STATUS_CHANGE = 1;
			
			//Update DB to MERGED_STATUS_NEW to notify the change in recording, so that it can re-merge
			
			Intent intent_re_record = new Intent(this, CustomCameraActivity.class);
			db.updateCurrentScreen("open_cam_1");
			db.updateRerecordState(1);
			intent_re_record.putExtra(OPEN_CAM, "open_cam_1");
			intent_re_record.putExtra(RERECORD, true);
	        replaceContentView("CustomCameraActivity", intent_re_record);
			
		break;
		case R.id.btn_preview_play:{
			//Toast.makeText(VideoPreviewOption.this, "Start Preview", Toast.LENGTH_SHORT).show();
			video1 = db.isVideo1Present();
			System.out.println("video1 in VideoPreviewOption: "+video1);
			if(video1!=null){
				if(!(video1.equals(""))){
			Intent intent_play = new Intent(VideoPreviewOption.this, VideoPlayerActivity.class);
			intent_play.putExtra(VIDEO_URL, video1);
			startActivity(intent_play);
				}
			}else{
				errorAlertDialog(getResources().getString(R.string.no_video_found));
			}
		}
			break;
		case R.id.btn_home:{
			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SHOW_PREVIEW_TAB2 = 0;
			Constants.SHOW_PREVIEW_TAB3 = 0;
			Constants.SHOW_PREVIEW_TAB4 = 0;
			Constants.SHOW_PREVIEW_TAB5 = 0;*/
			Intent intent_home = new Intent(VideoPreviewOption.this,HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
			}
			break;
		case R.id.btn_next:
			Constants.SET_CURRENT_TAB = 2;
			//Set the track of Enabled tabs. 
			Constants.ENABLE_TAB2 = 1;
			tabViewActivity.setCurrentActivityTab(2);
			tabViewActivity.enabletab(2);
			if(db.getCompletedScreens()<2){
			db.updateCompletedScreen(2);
			}
			
			break;
		default:
			break;
		}
		
	}

	private int getVideoDuration(String viewSource) {
		int maxVideoSize = 0;
		if (viewSource != null) {
			mp = MediaPlayer.create(VideoPreviewOption.this, Uri.parse(viewSource));
			if (mp != null) {
				int duration = mp.getDuration();

				//System.out.println("duration: " + duration);
				maxVideoSize = duration / 1000;
				mp.reset();
				mp.release();
			}
		}
		return maxVideoSize;

	}

	private void errorAlertDialog(String message) {
		DialogFragment newFragment = ErrorDialog.newInstance(VideoPreviewOption.this,message,null);
		newFragment.show(getFragmentManager(), "dialog");
	}
@Override
protected void onResume() {
	super.onResume();
	System.out.println("OnResume called!!! videopreviewoption");
	Constants.SET_CURRENT_TAB = 1;

	String video1 = db.isVideo1Present();
	
	 //previewVideoView();
}

	private void replaceContentView(String id, Intent newIntent) {
		System.out.println("replaceContentView.... ");
		View view = ((ActivityGroup) this)
	            .getLocalActivityManager()
	            .startActivity(id,
	                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	            .getDecorView();
	    ((Activity) this).setContentView(view);
	   
	}
	
}
