/*
 * Know My Story 
 */
package com.videoeditor.media;

import java.io.File;

import com.database.DatabaseHelper;
import com.dialog.DialogActivity;
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

public class VideoPreviewOptionTab5 extends ActivityGroup implements OnClickListener {

	/* public static final String PARAM_CREATE_PROJECT_NAME = "name";
	  public static final String PARAM_OPEN_PROJECT_PATH = "path";
		private static final int REQUEST_CODE_CREATE_PROJECT = 0;*/
	public Typeface abel_ttf;
	public static final String CURRENT_ACTIVITY_INFO = "current_activity_info";
	
		// Database Helper
	    DatabaseHelper db;
	    String video5;
	    Button btn_trim;
	    Button btn_reRecord;
	    String OPEN_CAM = "open_cam";
	    String RERECORD = "video_reRecord";
	    String VIDEO_URL = "video_url";
	    Button button_PreviewPlay;
	    Button btn_home;
	    Button btn_next;
	    MediaPlayer mp;
	    TabViewActivity tabViewActivity = new TabViewActivity();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recorded_video_preview);
		db = new DatabaseHelper(this);
		 video5 = db.isVideo5Present(TabViewActivity.getProjectName());
		previewVideoView();
		//TabViewActivity.setCurrentActivityTab(1);
		db.updateCurrentScreen("open_cam_5");
		
		initializeObjects();

		settypeFont();
		addOnClickListner();
		setInitialValues();
		
		
		/*VideoView videoview = (VideoView)findViewById(R.id.video_preview);
		videoview.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Toast.makeText(VideoPreviewOptionTab5.this, "Start Preview", Toast.LENGTH_SHORT) .show();
				Intent intent = new Intent(VideoPreviewOptionTab5.this, VideoPlayerActivity.class);
				intent.putExtra(VIDEO_URL, video5);
				startActivity(intent);
				Intent intent = new Intent(Intent.ACTION_VIEW);
				// String MediaFilePath = CameraPreview.getMediaFilePath();
				//Uri data = Uri.parse(MediaFilePath);
				//intent.setDataAndType(data, "video/*");
				
				File file = new File(video5); 
				String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
			    String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
			    intent.setDataAndType(Uri.fromFile(file),mimetype);
			    startActivity(intent);
				//startActivityForResult(intent, 0);
				return true;
			}
		});*/
	}
	private void setInitialValues() {
		tabViewActivity.enabletab(0);
		tabViewActivity.enabletab(1);
		tabViewActivity.enabletab(2);
		tabViewActivity.enabletab(3);
		tabViewActivity.enabletab(4);
		tabViewActivity.enabletab(5);
	}
	private void initializeObjects() {
		btn_trim = (Button)findViewById(R.id.btn_trim);
		btn_reRecord = (Button)findViewById(R.id.btn_rerecord);
		button_PreviewPlay = (Button)findViewById(R.id.btn_preview_play);
		btn_home = (Button)findViewById(R.id.btn_home);
		btn_next = (Button)findViewById(R.id.btn_next);
	}

	private void settypeFont() {
		abel_ttf=Typeface.createFromAsset(this.getAssets(), "fonts/Abel-Regular.ttf");
		btn_trim.setTypeface(abel_ttf);
		btn_reRecord.setTypeface(abel_ttf);
	}
	private void addOnClickListner() {
		btn_trim.setOnClickListener(this);
		btn_reRecord.setOnClickListener(this);
		button_PreviewPlay.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		
	}
	private void previewVideoView() {
		//String video1 = prefs.getString("media_path", null);
		
		
		if(video5!=null){
		Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(video5,
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
			
			video5 = db.isVideo5Present(TabViewActivity.getProjectName());
			int duration = getVideoDuration(video5);
			System.out.println("VideoDuration5: "+duration);
			
			if (duration > 2) {
				Intent intent = new Intent(this, TrimActivity.class);
				intent.putExtra("VIDEO1_PATH", video5);
				startActivity(intent);
			} else {
				Intent i = new Intent(VideoPreviewOptionTab5.this,
						DialogActivity.class);
				i.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Trim_not_possible");
				startActivity(i);
			}
			break;
		case R.id.btn_rerecord:
			/*Intent intent_reRecord = new Intent().setClass(this, CustomCameraActivity.class);
			db.updateCurrentScreen("open_cam_5");
		
			intent_reRecord.putExtra(OPEN_CAM, "open_cam_5");
			intent_reRecord.putExtra(RERECORD, true);
			startActivity(intent_reRecord);*/
			
			//Update DB to MERGED_STATUS_NEW to notify the change in recording, so that it can re-merge
			//db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
			Intent intent_re_record = new Intent(this, CustomCameraActivity.class);
			db.updateCurrentScreen("open_cam_5");
			db.updateRerecordState(1);
			intent_re_record.putExtra(OPEN_CAM, "open_cam_5");
			intent_re_record.putExtra(RERECORD, true);
	        replaceContentView("CustomCameraActivity", intent_re_record);
		break;
		case R.id.btn_preview_play:{
			 video5 = db.isVideo5Present(TabViewActivity.getProjectName());
			//Toast.makeText(VideoPreviewOptionTab5.this, "Start Preview", Toast.LENGTH_SHORT).show();
			Intent intent_preview = new Intent(VideoPreviewOptionTab5.this, VideoPlayerActivity.class);
			intent_preview.putExtra(VIDEO_URL, video5);
			startActivity(intent_preview);
		}
			break;
		case R.id.btn_home:{
			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SHOW_PREVIEW_TAB2 = 0;
			Constants.SHOW_PREVIEW_TAB3 = 0;
			Constants.SHOW_PREVIEW_TAB4 = 0;
			Constants.SHOW_PREVIEW_TAB5 = 0;*/
			Intent intent_home = new Intent(VideoPreviewOptionTab5.this,HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
			}
			break;
		case R.id.btn_next:
			Constants.SET_CURRENT_TAB = 6;
			//Set the track of Enabled tabs. 
			Constants.ENABLE_TAB6 = 1;
			tabViewActivity.setCurrentActivityTab(6);
			tabViewActivity.enabletab(6);
			if(db.getCompletedScreens()<6){
			db.updateCompletedScreen(6);
			}
			break;
		default:
			break;
		}
		
	}
	private int getVideoDuration(String viewSource) {
		int maxVideoSize = 0;
		if (viewSource != null) {
			mp = MediaPlayer.create(VideoPreviewOptionTab5.this, Uri.parse(viewSource));
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
		Constants.SET_CURRENT_TAB = 5;
	}
}
