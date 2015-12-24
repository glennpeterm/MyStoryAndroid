/*
 * Know My Story 
 */
package com.videoeditor.media;

import java.io.File;

import com.database.DatabaseHelper;
import com.dialog.DialogActivity;
import com.dialog.ErrorDialog;
import com.mediacontrols.VideoPlayerActivity;
import com.supportclasses.Constants;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.AboutMyStory_Activity;
import com.test.socialnetwork.HomeScreen;
import com.trim.TrimActivity;
import net.onehope.mystory.R;

import dogtim.android.videoeditor.util.FileUtils;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPreviewOptionTab2 extends ActivityGroup implements OnClickListener {

	
	public static final String CURRENT_ACTIVITY_INFO = "current_activity_info";
	
		// Database Helper
	    DatabaseHelper db;
	    String video2;
	    Button btn_trim;
	    Button button_PreviewPlay;
	    Button btn_reRecord;
	    String OPEN_CAM = "open_cam";
	    String RERECORD = "video_reRecord";
	    String VIDEO_URL = "video_url";
	    Button btn_home;
	    Button btn_next;
	    TabViewActivity tabViewActivity = new TabViewActivity();
		public Typeface abel_ttf;
		MediaPlayer mp;
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorded_video_preview);
		db = new DatabaseHelper(this);
		 video2 = db.isVideo2Present(TabViewActivity.getProjectName());
		previewVideoView();
		//TabViewActivity.setCurrentActivityTab(1);
		db.updateCurrentScreen("open_cam_2");
		
		initializeObjects();
		setFonttypes();
		addOnClickListner();
		setInitialValues();
	
	}
	
	private void setInitialValues() {
		tabViewActivity.enabletab(0);
		tabViewActivity.enabletab(1);
		tabViewActivity.enabletab(2);
	}

	private void initializeObjects() {
		btn_trim = (Button) findViewById(R.id.btn_trim);
		btn_reRecord = (Button) findViewById(R.id.btn_rerecord);
		button_PreviewPlay = (Button) findViewById(R.id.btn_preview_play);
		btn_home = (Button) findViewById(R.id.btn_home);
		btn_next = (Button) findViewById(R.id.btn_next);
	}
	
	private void setFonttypes()
	{
		/*
		 * Set Font style
		 */
		abel_ttf=Typeface.createFromAsset(this.getAssets(), Constants.FONT_ABEL_REGULAR);
		btn_trim.setTypeface(abel_ttf);
		btn_reRecord.setTypeface(abel_ttf);
	
	}
	private void addOnClickListner() {
		/*
		 * Add onClick listeners
		 */
		btn_trim.setOnClickListener(this);
		btn_reRecord.setOnClickListener(this);
		button_PreviewPlay.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		btn_next.setOnClickListener(this);

		
	}
	private void previewVideoView() {
		/*
		 * Adds thumbnail to preview window.
		 */
		if(video2!=null){
		Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(video2,
		        MediaStore.Images.Thumbnails.MINI_KIND);
		
		VideoView video = (VideoView)findViewById(R.id.video_preview);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(thumbnail);
		video.setBackgroundDrawable(bitmapDrawable);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_trim:
			video2 = db.isVideo2Present(TabViewActivity.getProjectName());
			int duration = getVideoDuration(video2);
			System.out.println("VideoDuration2: "+duration);
			
			if (duration > 2) {
				Intent intent = new Intent(this, TrimActivity.class);
				intent.putExtra("VIDEO1_PATH", video2);
				startActivity(intent);
			} else {
				Intent i = new Intent(VideoPreviewOptionTab2.this,
						DialogActivity.class);
				i.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Trim_not_possible");
				startActivity(i);
			}
			break;
		case R.id.btn_rerecord:
			
			//Update DB to MERGED_STATUS_NEW to notify the change in recording, so that it can re-merge
			//db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
			Intent intent_re_record = new Intent(this, CustomCameraActivity.class);
			db.updateCurrentScreen("open_cam_2");
			db.updateRerecordState(1);
			intent_re_record.putExtra(OPEN_CAM, "open_cam_2");
			intent_re_record.putExtra(RERECORD, true);
	        replaceContentView("CustomCameraActivity", intent_re_record);
	        
		break;
		
		case R.id.btn_preview_play:{
			 video2 = db.isVideo2Present(TabViewActivity.getProjectName());
			//Toast.makeText(VideoPreviewOptionTab2.this, "Start Preview", Toast.LENGTH_SHORT).show();
			System.out.println("video2 in VideoPreviewOption: "+video2);
			if (video2 != null) {
				if (!(video2.equals(""))) {
					Intent intent_play = new Intent(
							VideoPreviewOptionTab2.this,
							VideoPlayerActivity.class);
					intent_play.putExtra(VIDEO_URL, video2);
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
			Intent intent_home = new Intent(VideoPreviewOptionTab2.this,HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
			}
			break;
		case R.id.btn_next:
			Constants.SET_CURRENT_TAB = 3;
			//Set the track of Enabled tabs. 
			Constants.ENABLE_TAB3 = 1;
			tabViewActivity.setCurrentActivityTab(3);
			tabViewActivity.enabletab(3);
			if(db.getCompletedScreens()<3){
				db.updateCompletedScreen(3);
			}
			break;
		default:
			break;
		}
		
	}
	private int getVideoDuration(String viewSource) {
		int maxVideoSize = 0;
		if (viewSource != null) {
			mp = MediaPlayer.create(VideoPreviewOptionTab2.this, Uri.parse(viewSource));
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
		DialogFragment newFragment = ErrorDialog.newInstance(VideoPreviewOptionTab2.this,message,null);
		newFragment.show(getFragmentManager(), "dialog");
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
	@Override
	protected void onResume() {
		super.onResume();
		Constants.SET_CURRENT_TAB = 2;
	}
}
