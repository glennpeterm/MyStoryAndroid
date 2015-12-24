/*
 * Know My Story 
 */
package com.videoeditor.media;

import io.fabric.sdk.android.services.events.EnabledEventsStrategy;

import java.io.File;

import com.database.DatabaseHelper;
import com.dialog.DialogActivity;
import com.scriptures.Wheel_Activity;
import com.service.ApiService;
import com.supportclasses.CheckAvailableStorage;
import com.supportclasses.Constants;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.HomeScreen;
import net.onehope.mystory.R;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CustomCameraActivity extends ActivityGroup implements OnClickListener, OnTouchListener {
	CameraPreview record;
	LinearLayout llSpeed, llDis, llAgSpeed, llMaxG, llTimeTk;
	TextView txtSpeed, txtDis, txtAVGSpeed, txtMaxG, txtTimer;
	static Button btn_capture;
	Button btn_start;
	
	boolean isRecording = false;
	String NEXT_TAB = "next_tab";
	String OPEN_CAM = "open_cam";
	static String open_tab;
	String NEXT_TAB_VALUE;
	FrameLayout framelayout;
	private String mInsertMediaItemAfterMediaItemId;
	static TextView textview_timer;
	static Context ctx;
	CounterClass timer;
	boolean startRecording = false;
	String MEDIA_FILE_PATH = "media_file_path";
	DatabaseHelper db;
	Button btn_flip;
	Button btn_home;
	boolean FlipFlag ;
	static String chooseCamera = "back";
	static boolean startrecordingstatus = false;
	TextView textview_overlay;
	TabViewActivity tabviewActivity = new TabViewActivity();
	String opentabVlaue = null;
	RelativeLayout sliding_view;
	boolean TheWord = false;
	ImageView sliding_control;
	Handler handler;
	Runnable runnable;
	TextView tv_verses_header;
	TextView tv_verses_body;
	static int timeInMillis = 0;
	Button btnChooseAgain;
	
	public Typeface abel_ttf;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		System.out.println("OnCreate CustomcameraActivity");
		setContentView(R.layout.videoactivity);
		ctx = this;
		db = new DatabaseHelper(this);

		initializeObject();
		CheckFrontCam();
		setFontypes();
		addOnClickListner();

		tabviewActivity.setButtonActive();
		
		System.out.println("Inside CustomCameraActivity oncreate...");
		open_tab = getIntent().getStringExtra(OPEN_CAM);
		System.out.println("open_tab: "+open_tab);
		
		 framelayout = (FrameLayout)findViewById(R.id.frame_layout);
		 record = new CameraPreview(this, null);
		
		 framelayout.addView(record);
		 System.out.println("view added: "+framelayout.getChildCount());
			
		 textview_timer = (TextView)findViewById(R.id.countdown_timer);
		 textview_timer.setTypeface(abel_ttf);
		 String maxTimeFormat = null;
		 String maxTime = null;
		 if(open_tab==null){
			 HideSlider();
			 maxTimeFormat = "00:05";
			 textview_overlay.setText(getResources().getString(R.string.text_introduction));
			
			 //5 sec
			 maxTime = "5000";
		 }else if(open_tab.equals("open_cam_1")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_introduction));
			 db.updateCurrentScreen("open_cam_1");
			 maxTimeFormat = "00:05";
			 //5sec
			 maxTime = "5000";
			 opentabVlaue = "open_cam_1";
		 }else if(open_tab.equals("open_cam_2")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_setup));
			 db.updateCurrentScreen("open_cam_2");
			 maxTimeFormat = "00:50";
			 //50 sec
			 maxTime = "50000";
			 opentabVlaue = "open_cam_2";
		 }else if(open_tab.equals("open_cam_3")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_god_moment));
			 db.updateCurrentScreen("open_cam_3");
			 maxTimeFormat = "01:00";
			 //1 min
			 maxTime = "60000";
			 opentabVlaue = "open_cam_3";
		 }else if(open_tab.equals("open_cam_4")){
			 TheWord = true;
			 ShowSlider();
			 btnChooseAgain.setVisibility(View.VISIBLE);
			 textview_overlay.setText(getResources().getString(R.string.text_the_word));
			 db.updateCurrentScreen("open_cam_4");
			 maxTimeFormat = "01:00";
			 //1 min
			 maxTime = "60000";
			 opentabVlaue = "open_cam_4";
		 }else if(open_tab.equals("open_cam_5")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_conclusion));
			 db.updateCurrentScreen("open_cam_5");
			 maxTimeFormat = "00:05";
			 //1 min
			 maxTime = "5000";
			 opentabVlaue = "open_cam_5";
		 }else if(open_tab.equals("open_cam_6")){
			 HideSlider();
			 db.updateCurrentScreen("open_cam_6");
			 maxTimeFormat = "00:05";
			 //1 min
			 maxTime = "5000";
			 opentabVlaue = "open_cam_6";
		 }
		textview_timer.setText(maxTimeFormat);
		
		//Set countdown timer for 5 sec for Introducton video
		 timer = new CounterClass(Long.parseLong(maxTime),1000);  
		//timer.start();
		
	
	}

	

	private void CheckFrontCam() {
		int CamNumber = Camera.getNumberOfCameras();
		System.out.println("Cam number: "+CamNumber);
		
		if(CamNumber == 1){
			btn_flip.setVisibility(View.INVISIBLE);
		}else{
			btn_flip.setVisibility(View.VISIBLE);
		}
	}



	private void HideSlider() {
		/*
		 * Hide the scripture slider
		 */
		if (sliding_control != null) {
			if (sliding_control.getVisibility() == View.VISIBLE)
				sliding_control.setVisibility(View.GONE);
			if (sliding_view.getVisibility() == View.VISIBLE)
				
				sliding_view.setVisibility(View.GONE);
		}
		
	}



	private void ShowSlider() {
		//fetch verses data from db and show in text view 
		//Now dummy data shown
		/*tv_verses_header.setText("Exodus 3:14");
		tv_verses_body.setText("God said to moses,\" I AM WHO I AM. This is what you are to say to the Israelites: ' I AM has sent me to you'");*/
		//fetch verses data from db and show in text view 
				//Now dummy data shown
				String index=db.getVerseIndex();
				if(index != null)
				{
				tv_verses_header.setText(index);
				}
				String text=db.getVerseText();
				if(text != null)
				{
				tv_verses_body.setText(text);
				}
		handler.removeCallbacks(runnable);
		int slidingViewWidth = GetSlidingViewWidth();
		sliding_view.getLayoutParams().width = (int) (slidingViewWidth * 0.5);
		//if (TheWord) {
		
		sliding_control.setVisibility(View.VISIBLE);
		sliding_view.setVisibility(View.VISIBLE);
		// Animation slide_in_left = (AnimationSet)
		// AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
		Animation slide_in_left = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.slide_in_left);
		// slide_in_left.setDuration(500);
		// sliding_view.setAnimation(slide_in_left);
		sliding_view.startAnimation(slide_in_left);
		sliding_control.startAnimation(slide_in_left);
		passtruetoenableelseDisableChooseAgainButton(true);
		runnable = new Runnable() {

			@Override
			public void run() {
				SlideOutSlider();

			}
		};
		handler.postDelayed(runnable, 6000);
			
	}



	protected void SlideOutSlider() {
		//disable the button.
		passtruetoenableelseDisableChooseAgainButton(false);
		handler.removeCallbacks(runnable);
		Animation	slide_out_left = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_out_left);  
		//slide_out_left.setDuration(500);
		sliding_view.setAnimation(slide_out_left);
		sliding_view.setVisibility(View.GONE);
		sliding_view.startAnimation(slide_out_left);
		sliding_control.startAnimation(slide_out_left);
		
	}



	private void initializeObject() {
		btn_capture = (Button) findViewById(R.id.stop);
		btn_flip = (Button) findViewById(R.id.cam_flip);
		btn_home = (Button) findViewById(R.id.btn_home);
		textview_overlay = (TextView) findViewById(R.id.text_overlay);
		sliding_view = (RelativeLayout) findViewById(R.id.slidingview);
		sliding_control = (ImageView) findViewById(R.id.sliding_control);
		handler = new Handler();
		tv_verses_header = (TextView) findViewById(R.id.verse_header);
		tv_verses_body = (TextView) findViewById(R.id.verse_body);
		btnChooseAgain = (Button) findViewById(R.id.btn_ChooseAgain);
		
	}

	private void setFontypes()
	{
		/*
		 * Set custom font style
		 */
		abel_ttf=Typeface.createFromAsset(this.getAssets(), Constants.FONT_ABEL_REGULAR);
		textview_overlay.setTypeface(abel_ttf);
		tv_verses_body.setTypeface(abel_ttf);
		tv_verses_header.setTypeface(abel_ttf);
		btnChooseAgain.setTypeface(abel_ttf);
	}
	
	private void addOnClickListner() {
		btn_capture.setOnClickListener(this);
		btn_flip.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		sliding_control.setOnTouchListener(this);
		btnChooseAgain.setOnClickListener(this);
	}
	
	
	/**
	 * will enable and disble the CHOOSEAGAIN button according the input params. 
	 * */
   private void passtruetoenableelseDisableChooseAgainButton(boolean param){
	   
	   
	   btnChooseAgain.setEnabled(param);
	   btnChooseAgain.setClickable(param);
	   
   }
	
	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.stop:
			
			MuteShutterSound();
			if (startRecording) {
				framelayout.setKeepScreenOn(false);
				System.out.println("MinDuration: " + timeInMillis);
				if (timeInMillis > 1000) {
					tabviewActivity.showAllTabs();
					textview_timer.setVisibility(View.INVISIBLE);
					// btn_capture.setText("Start");
					btn_capture.setBackgroundResource(R.drawable.btn_record);
					btn_flip.setVisibility(View.VISIBLE);
					btn_home.setVisibility(View.VISIBLE);
					textview_overlay.setVisibility(View.VISIBLE);
					startrecordingstatus = false;
					// record.stopRecording();
					timer.cancel();
					try {
						record.m_recorder.stop();
						record.camera.stopPreview();
						UnmuteShutterSound();
						// framelayout.removeView(record);
					} catch (NullPointerException e) {
						e.printStackTrace();
						releaseMediaRecorder();
					} catch (RuntimeException e) {
						e.printStackTrace();
						releaseMediaRecorder();
					}
					Uri mCaptureMediaUri = CameraPreview.saveToContentValue();
					addMediaItem(mCaptureMediaUri);

					if (open_tab != null) {
						if (open_tab.equals("open_cam_1")) {
							// Constants.SHOW_PREVIEW_TAB1 = 1;
							db.updateShowPreviewTab1(1);
							Intent intent = new Intent(CustomCameraActivity.this,
									VideoPreviewOption.class);
							replaceContentView("VideoPreviewOption", intent);
							
							/**
							 * Re-Record
							 * Renaming of file
							 */
							int reRecordStatus = db.getRerecordState();
							if(reRecordStatus == 1){
								db.updateRerecordState(0);
								String project_path = TabViewActivity.getProjectPath();
								File mediaStorageDir = new File(project_path);
								
							String existingVideo = db.isVideo1Present();
							if(!existingVideo.equals("")){
							 String videoPath = existingVideo.substring(0,existingVideo.lastIndexOf("/"));
							 System.out.println("videoPath: "+videoPath);
							 File file = new File(videoPath+"/VideoClip1_1.mp4");
							 File file2 = new File(mediaStorageDir.getPath() + File.separator
									+ "VideoClip1.mp4");
							    boolean success = file.renameTo(file2);
							    System.out.println("Rename Status : "+success);
							db.updateVideo1(file2.getPath().toString());
							
							Intent intent1 = new Intent(CustomCameraActivity.this,
									VideoPreviewOption.class);
							replaceContentView("VideoPreviewOption", intent1);
							
							//deleteOldFile(videoPath+"/VideoClip1_1.mp4");
							}
							}
							
							
						} else if (open_tab.equals("open_cam_2")) {
							// Constants.SHOW_PREVIEW_TAB2 = 1;
							db.updateShowPreviewTab2(1);
							Intent intent = new Intent(this,
									VideoPreviewOptionTab2.class);
							replaceContentView("VideoPreviewOptionTab2", intent);
							
							/**
							 * Re-Record
							 * Renaming of file
							 */
							int reRecordStatus = db.getRerecordState();
							if(reRecordStatus == 1){
								db.updateRerecordState(0);
								String project_path = TabViewActivity.getProjectPath();
								File mediaStorageDir = new File(project_path);
								
							String existingVideo = db.isVideo2Present(TabViewActivity.getProjectName());
							if(!existingVideo.equals("")){
							 String videoPath = existingVideo.substring(0,existingVideo.lastIndexOf("/"));
							 System.out.println("videoPath: "+videoPath);
							 File file = new File(videoPath+"/VideoClip2_1.mp4");
							 File file2 = new File(mediaStorageDir.getPath() + File.separator
									+ "VideoClip2.mp4");
							    boolean success = file.renameTo(file2);
							    System.out.println("Rename Status : "+success);
							db.updateVideo2(file2.getPath().toString());
							
							Intent intent1 = new Intent(this,
									VideoPreviewOptionTab2.class);
							replaceContentView("VideoPreviewOptionTab2", intent1);
							//deleteOldFile(videoPath+"/VideoClip1_1.mp4");
							}
							}
						} else if (open_tab.equals("open_cam_3")) {
							// Constants.SHOW_PREVIEW_TAB3 = 1;
							db.updateShowPreviewTab3(1);
							Intent intent = new Intent(this,
									VideoPreviewOptionTab3.class);
							replaceContentView("VideoPreviewOptionTab3", intent);
							
							/**
							 * Re-Record
							 * Renaming of file
							 */
							int reRecordStatus = db.getRerecordState();
							if(reRecordStatus == 1){
								db.updateRerecordState(0);
								String project_path = TabViewActivity.getProjectPath();
								File mediaStorageDir = new File(project_path);
								
							String existingVideo = db.isVideo3Present(TabViewActivity.getProjectName());
							if(!existingVideo.equals("")){
							 String videoPath = existingVideo.substring(0,existingVideo.lastIndexOf("/"));
							 System.out.println("videoPath: "+videoPath);
							 File file = new File(videoPath+"/VideoClip3_1.mp4");
							 File file2 = new File(mediaStorageDir.getPath() + File.separator
									+ "VideoClip3.mp4");
							    boolean success = file.renameTo(file2);
							    System.out.println("Rename Status : "+success);
							db.updateVideo3(file2.getPath().toString());
							
							Intent intent1 = new Intent(this,
									VideoPreviewOptionTab3.class);
							replaceContentView("VideoPreviewOptionTab3", intent1);
							//deleteOldFile(videoPath+"/VideoClip1_1.mp4");
							}
							}
							
						} else if (open_tab.equals("open_cam_4")) {
							System.out.println("open cam 4 ...");
							// Constants.SHOW_PREVIEW_TAB4 = 1;
							db.updateShowPreviewTab4(1);
							Intent intent = new Intent(this,
									VideoPreviewOptionTab4.class);
							replaceContentView("VideoPreviewOptionTab4", intent);
							
							/**
							 * Re-Record
							 * Renaming of file
							 */
							int reRecordStatus = db.getRerecordState();
							if(reRecordStatus == 1){
								db.updateRerecordState(0);
								String project_path = TabViewActivity.getProjectPath();
								File mediaStorageDir = new File(project_path);
								
							String existingVideo = db.isVideo4Present(TabViewActivity.getProjectName());
							if(!existingVideo.equals("")){
							 String videoPath = existingVideo.substring(0,existingVideo.lastIndexOf("/"));
							 System.out.println("videoPath: "+videoPath);
							 File file = new File(videoPath+"/VideoClip4_1.mp4");
							 File file2 = new File(mediaStorageDir.getPath() + File.separator
									+ "VideoClip4.mp4");
							    boolean success = file.renameTo(file2);
							    System.out.println("Rename Status : "+success);
							db.updateVideo4(file2.getPath().toString());
							
							Intent intent1 = new Intent(this,
									VideoPreviewOptionTab4.class);
							replaceContentView("VideoPreviewOptionTab4", intent1);
							//deleteOldFile(videoPath+"/VideoClip1_1.mp4");
							}
							}
							
						} else if (open_tab.equals("open_cam_5")) {
							// Constants.SHOW_PREVIEW_TAB5 = 1;
							db.updateShowPreviewTab5(1);
							Intent intent = new Intent(this,
									VideoPreviewOptionTab5.class);
							replaceContentView("VideoPreviewOptionTab5", intent);
							
							/**
							 * Re-Record
							 * Renaming of file
							 */
							int reRecordStatus = db.getRerecordState();
							if(reRecordStatus == 1){
								db.updateRerecordState(0);
								String project_path = TabViewActivity.getProjectPath();
								File mediaStorageDir = new File(project_path);
								
							String existingVideo = db.isVideo5Present(TabViewActivity.getProjectName());
							if(!existingVideo.equals("")){
							 String videoPath = existingVideo.substring(0,existingVideo.lastIndexOf("/"));
							 System.out.println("videoPath: "+videoPath);
							 File file = new File(videoPath+"/VideoClip5_1.mp4");
							 File file2 = new File(mediaStorageDir.getPath() + File.separator
									+ "VideoClip5.mp4");
							    boolean success = file.renameTo(file2);
							    System.out.println("Rename Status : "+success);
							db.updateVideo5(file2.getPath().toString());
							
							Intent intent1 = new Intent(this,
									VideoPreviewOptionTab5.class);
							replaceContentView("VideoPreviewOptionTab5", intent1);
							//deleteOldFile(videoPath+"/VideoClip1_1.mp4");
							}
							}
						}
					}

					startRecording = false;
				} else {
					Toast.makeText(getBaseContext(),
							"Video duration can't be less than 1 second",
							Toast.LENGTH_SHORT).show();
				}
				
				db.updateRerecordState(0);
				
			} else {
				if(new CheckAvailableStorage().hasEnoughInternalMemory()){				
			
				framelayout.setKeepScreenOn(true);
				if (TheWord) {
					btnChooseAgain.setVisibility(View.INVISIBLE);
					if (sliding_view.getVisibility() == View.VISIBLE) {
						SlideOutSlider();
					}
				}
				
				
				// sliding_view.setVisibility(View.VISIBLE);
				tabviewActivity.hideAllTabs();

				System.out.println("start recording..");

				textview_timer.setVisibility(View.VISIBLE);
				btn_home.setVisibility(View.INVISIBLE);
				textview_overlay.setVisibility(View.INVISIBLE);
				// btn_capture.setText("Stop");
				btn_capture.setBackgroundResource(R.drawable.btn_stop);
				btn_flip.setVisibility(View.INVISIBLE);
				startrecordingstatus = false;
				// record = (CameraPreview) findViewById(R.id.surfaceView);
				if (record.m_recorder != null)
					try {
						// MuteShutterSound();
						// prepare() not wrking
						/*
						 * try { record.m_recorder.prepare(); } catch
						 * (IOException e) { // TODO Auto-generated catch block
						 * e.printStackTrace(); }
						 */
						db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
						record.m_recorder.start();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						System.out.println("m_recorder: " + record.m_recorder);
						e.printStackTrace();
						releaseMediaRecorder();
					} catch (RuntimeException e) {
						System.out.println("m_recorder: " + record.m_recorder);
						e.printStackTrace();
						releaseMediaRecorder();
					}

				timer.start();
				startRecording = true;
			}else{
				Intent intent=new Intent(CustomCameraActivity.this, DialogActivity.class);
				intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "low_memory");
				startActivity(intent);
				
			}
		}
		
		
			break;
		case R.id.cam_flip:
			if(chooseCamera.equals("back")){
				startrecordingstatus = true;
				chooseCamera = "front";
				//record = (CameraPreview) findViewById(R.id.surfaceView);
				if(record!=null)
				record.m_recorder.start();
				
				try {
					record.m_recorder.stop();
					record.camera.stopPreview();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}catch (RuntimeException e) {
					e.printStackTrace();
				}
				/*Intent intent = getIntent();
				
				overridePendingTransition(0, 0);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				overridePendingTransition(0, 0);
				startActivity(intent);*/
				Intent intent_flip = new Intent(this, CustomCameraActivity.class);
				intent_flip.putExtra(OPEN_CAM, opentabVlaue);
				overridePendingTransition(0, 0);
				intent_flip.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				//finish();
				overridePendingTransition(0, 0);
				replaceContentView("CustomCameraActivity", intent_flip);
				
				
				FlipFlag = false;
			}else if(chooseCamera.equals("front")){
				startrecordingstatus = true;
				chooseCamera = "back";
				//record = (CameraPreview) findViewById(R.id.surfaceView);
				if(record!=null)
				record.m_recorder.start();
				
				try {
					record.m_recorder.stop();
					record.camera.stopPreview();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}catch (RuntimeException e) {
					e.printStackTrace();
				}
				/*Intent intent = getIntent();
				
				overridePendingTransition(0, 0);
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				finish();
				overridePendingTransition(0, 0);
				startActivity(intent);*/
				
				Intent intent_flip = new Intent(this, CustomCameraActivity.class);
				intent_flip.putExtra(OPEN_CAM, opentabVlaue);
				overridePendingTransition(0, 0);
				intent_flip.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				//finish();
				overridePendingTransition(0, 0);
				replaceContentView("CustomCameraActivity", intent_flip);
				
				//Switch to Back cam 
				//CameraPreview.flipCamera("back");
				//framelayout.removeView(record);
				//record = (CameraPreview) findViewById(R.id.surfaceView);
				//record.camera.open(0);
				FlipFlag = true;
			}
			//record.camera.open(CAMERA_FACING_FRONT);
			
			
			break;
		case R.id.btn_home:{
			db.updateRerecordState(0);
			if(startRecording){
				record.m_recorder.stop();
				record.camera.stopPreview();
			}
			
			//framelayout.removeView(record);
			//Constants.SHOW_PREVIEW_TAB1 = 0;
			db.updateShowPreviewTab1(0);
			//Constants.SHOW_PREVIEW_TAB2 = 0;
			db.updateShowPreviewTab2(0);
			//Constants.SHOW_PREVIEW_TAB3 = 0;
			db.updateShowPreviewTab3(0);
			//Constants.SHOW_PREVIEW_TAB4 = 0;
			db.updateShowPreviewTab4(0);
			//Constants.SHOW_PREVIEW_TAB5 = 0;
			db.updateShowPreviewTab5(0);
			
			
			/**
			 * Delete files with extension '_1' 
			 */
			deleteOldFile();
			
			Intent intent = new Intent(CustomCameraActivity.this,HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
			}
			break;
		case R.id.btn_ChooseAgain:
			Constants.SET_VERSE_FLAG=0;
			Intent intent_choose = new Intent(CustomCameraActivity.this, Wheel_Activity.class);
		//	intent_choose.putExtra(OPEN_CAM, "open_cam_4");
			replaceContentView("Wheel_Activity", intent_choose);
			
			break;
		
			
		}
	}
	

	private void releaseMediaRecorder(){
	        if (record.m_recorder != null) {
	        	record.m_recorder.reset();   // clear recorder configuration
	        	record.m_recorder.release(); // release the recorder object
	        	record.m_recorder = null;
	           // mCamera.lock();           // lock camera for later use
	        }
	    }



	private void UnmuteShutterSound() {
		 AudioManager audio= (AudioManager)this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
         audio.setStreamVolume(AudioManager.STREAM_SYSTEM, audio.getStreamMaxVolume(AudioManager.STREAM_SYSTEM) , AudioManager.FLAG_ALLOW_RINGER_MODES);
		
	}

	private void MuteShutterSound() {
		 AudioManager audio= (AudioManager)this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
         audio.setStreamVolume(AudioManager.STREAM_SYSTEM, 0,   AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		
	}



	private int GetSlidingViewWidth() {
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		//int deviceHeight = displaymetrics.heightPixels;
		int deviceWidth = displaymetrics.widthPixels;
		//return PixelUtil.pxToDp(this, deviceWidth);
		return deviceWidth;
	}



	private void replaceContentView(String id, Intent newIntent) {
		/*
		 * To switch activity , replaces the current content with required activity page.
		 */
		System.out.println("replaceContentView.... ");
		View view = ((ActivityGroup) this)
	            .getLocalActivityManager()
	            .startActivity(id,
	                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
	            .getDecorView();
	    ((Activity) this).setContentView(view);
		
	}



	private void addMediaItem(Uri mCaptureMediaUri) {
		/*
		 * Add media item(video / audio) to medi que. 
		 */
		String  mProjectPath = TabViewActivity.getProjectPath();
		ApiService.addMediaItemVideoUri(this, mProjectPath,
                ApiService.generateId(), mInsertMediaItemAfterMediaItemId,
                mCaptureMediaUri, MediaItem.RENDERING_MODE_BLACK_BORDER,
                null);
		System.out.println("path: "+mProjectPath);
		System.out.println("generatedId: "+ApiService.generateId());
		System.out.println("border: "+MediaItem.RENDERING_MODE_BLACK_BORDER);
		
        mInsertMediaItemAfterMediaItemId = null;
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(startRecording){
			if(record.m_recorder!=null){
			try {
				record.m_recorder.stop();
			} catch (IllegalStateException e) {
				e.printStackTrace();
				Intent intent=new Intent(CustomCameraActivity.this, DialogActivity.class);
				intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "video_not_recorded");
				startActivity(intent);
			}
			record.camera.stopPreview();
			}
		}
		System.out.println("onDestroy CameraActivity: ");
		framelayout.removeView(record);
	
	//	record.removeView();
	}
	@Override
	protected void onPause() {
		
		super.onPause();
		System.out.println("OnPause called...customcamera");
		if(startRecording){
			record.m_recorder.stop();
			record.camera.stopPreview();
		}
		framelayout.removeView(record);
	}

	public static void SetTimerText(String timerText) {
		 textview_timer.setText(timerText);
	}

	public static void CallFinish() {
		btn_capture.performClick();
	}

	public static String getTabDetail() {
		return open_tab;
		
	}
	
	public String fetchCamId() {
		
		return chooseCamera;
	}

	public boolean startRecordingStatus() {
		
		return startrecordingstatus;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (v.getId()) {
		case R.id.sliding_control:{
			if (sliding_view.getVisibility() == View.VISIBLE){
				SlideOutSlider();
				
			}else
				ShowSlider();
		}
			
			break;

		default:
			break;
		}
		return false;
	}



	public static void TimeInMillis(String time) {
		
		timeInMillis = Integer.parseInt(time);
		
	}

     @Override
     protected void onResume() {
	
	super.onResume();
	System.out.println("onResume Customcamera..."+record);
	 System.out.println("view added2: "+framelayout.getChildCount());
	 
			
	 if(framelayout.getChildCount() == 0){
		 framelayout.removeView(record);
			//CheckFrontCam();
			//setFontypes();
			//addOnClickListner();
		 record = new CameraPreview(this, null);
		 framelayout.addView(record); 
		 //initializeObject();
		
		
		 //textview_timer.setTypeface(abel_ttf);
		 String maxTimeFormat = null;
		 String maxTime = null;
		 open_tab = getIntent().getStringExtra(OPEN_CAM);
		 System.out.println("open_tab in onresume: "+open_tab);
		 if(open_tab==null){
			 HideSlider();
			 maxTimeFormat = "00:05";
			 textview_overlay.setText(getResources().getString(R.string.text_introduction));
			
			// db.updateCurrentScreen("open_cam_1");
			 //5 sec
			 maxTime = "5000";
		 }else if(open_tab.equals("open_cam_1")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_introduction));
			 db.updateCurrentScreen("open_cam_1");
			 maxTimeFormat = "00:05";
			 //5sec
			 maxTime = "5000";
			 opentabVlaue = "open_cam_1";
		 }else if(open_tab.equals("open_cam_2")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_setup));
			 db.updateCurrentScreen("open_cam_2");
			 maxTimeFormat = "00:50";
			 //50 sec
			 maxTime = "50000";
			 opentabVlaue = "open_cam_2";
		 }else if(open_tab.equals("open_cam_3")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_god_moment));
			 db.updateCurrentScreen("open_cam_3");
			 maxTimeFormat = "01:00";
			 //1 min
			 maxTime = "60000";
			 opentabVlaue = "open_cam_3";
		 }else if(open_tab.equals("open_cam_4")){
			 TheWord = true;
			 ShowSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_the_word));
			 db.updateCurrentScreen("open_cam_4");
			 maxTimeFormat = "01:00";
			 //1 min
			 maxTime = "60000";
			 opentabVlaue = "open_cam_4";
		 }else if(open_tab.equals("open_cam_5")){
			 HideSlider();
			 textview_overlay.setText(getResources().getString(R.string.text_conclusion));
			 db.updateCurrentScreen("open_cam_5");
			 maxTimeFormat = "00:05";
			 //1 min
			 maxTime = "5000";
			 opentabVlaue = "open_cam_5";
		 }else if(open_tab.equals("open_cam_6")){
			 HideSlider();
			 db.updateCurrentScreen("open_cam_6");
			 maxTimeFormat = "00:05";
			 //1 min
			 maxTime = "5000";
			 opentabVlaue = "open_cam_6";
		 }
		 //textview_timer = (TextView)findViewById(R.id.countdown_timer);
		 System.out.println("maxTimeFormat in onresume: "+maxTimeFormat);
		textview_timer.setText(maxTimeFormat);
		
		//Set countdown timer for 5 sec for Introducton video
		 timer = new CounterClass(Long.parseLong(maxTime),1000);  
	 
	 }
	
}	

	@Override
	public void onBackPressed() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return (keyCode == KeyEvent.KEYCODE_BACK ? true : super.onKeyDown(
				keyCode, event));
	}
	
	private void deleteOldFile() {
	/*	File dir = new File(TabViewActivity.getProjectPath());
		File file = new File(dir,existingVideo );
		boolean deleted = file.delete();
		System.out.println("deleted: "+deleted);*/
		File dir = new File(TabViewActivity.getProjectPath());
		 if (dir.isDirectory()) {
	            final String[] children = dir.list();
	            for (int i = 0; i < children.length; i++) {
	            	if(children[i].contains("_1")){
	                final File f = new File(dir, children[i]);
	                f.delete();
	            	}
	            }
	        }
		
	}
	
	
}
