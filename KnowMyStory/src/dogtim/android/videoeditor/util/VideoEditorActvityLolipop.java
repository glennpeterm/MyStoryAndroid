package dogtim.android.videoeditor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.onehope.mystory.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.CamcorderProfile;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.analytics.util.SendAnalytics;
import com.coremedia.iso.boxes.Container;
import com.database.DatabaseHelper;
import com.dialog.ErrorDialog;
import com.dialog.TransparentProgressDialog;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegExecuteResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.mediacontrols.VideoPlayerActivity;
import com.supportclasses.CheckAvailableStorage;
import com.supportclasses.Constants;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.HomeScreen;
import com.test.socialnetwork.LoginActivity;
import com.test.socialnetwork.Upload_Progress_Activity;


import dogtim.android.videoeditor.widgets.PreviewSurfaceView;

public class VideoEditorActvityLolipop extends Activity implements SurfaceHolder.Callback, OnClickListener{
	
	
	private Button button_home;
	private Button button_PreviewPlay;
	private Button button_upload;
	private Button btn_save;
	private Typeface abel_ttf;
	private TabViewActivity tabViewActivity;
	private DatabaseHelper db;
	private SendAnalytics analytics_conveyer;

	private FrameLayout fl;
	private LinearLayout ll_btn;
	private RelativeLayout rl;
	private PreviewSurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	String VIDEO_URL = "video_url";
	private ImageView mOverlayView;
	private WakeLock mCpuWakeLock;
	public String mv;
	public String broll;
	private FFmpeg ffmpeg;
	private static ProgressDialog mExportProgressDialog;
	private static ProgressBar pb;
	
	private TextView tv;
	private TransparentProgressDialog pd;
	private Dialog custom_dialog;
	private Handler h;
	private Button btnGoto;
	private Button btnCancel;
	private static TextView progpercent;
	private int PROG_PESUDO=0;
	private Handler mHandler;
	public CamcorderProfile profile;
	
	private Timer timer;
	private  RotateAnimation anim;
	private  static ImageView iv;
	
	private static boolean MergerTask_complete= false;
	private static boolean Increasevol_input= false;
	private static boolean OverlaymusictoMergedItem= false;

	private static String MergeinProg="Merging In Progress";
	private static String MergerFailed="Error:Merging Failed!!";
	
	
	@Override
 	protected void onCreate(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);
		setContentView(R.layout.video_editor);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	
		
		System.out.println("OnCreate called in Videoeditoractivity..");
		tabViewActivity = new TabViewActivity();
		gotoDialog();
		initializeButtonObject();
		setFontStyle();
		addOnClickListner();
		db = new DatabaseHelper(this);
		
		analytics_conveyer = new SendAnalytics(VideoEditorActvityLolipop.this);
		h = new Handler();


		 fl = (FrameLayout)findViewById(R.id.video_frame_view);
		 ll_btn = (LinearLayout)findViewById(R.id.ll_button_layout);
		 rl = (RelativeLayout)findViewById(R.id.mainLayout);
		rl.removeView(fl);
		rl.removeView(button_PreviewPlay);
		rl.removeView(ll_btn);
		
		rl.addView(fl);
		rl.addView(button_PreviewPlay);
		rl.addView(ll_btn);
		
		// Prepare the surface holder
		mSurfaceView = (PreviewSurfaceView) findViewById(R.id.video_view);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		mOverlayView = (ImageView)findViewById(R.id.overlay_layer);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mCpuWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Video Editor Activity CPU Wake Lock");

		String MergedStatus = db.getMergedStatus();
		if (MergedStatus != null) {
			if (MergedStatus.equals(Constants.MERGED_STATUS_NEW)) {
				
				deleteFiles();
				if(new CheckAvailableStorage().hasEnoughInternalMemory()){
				new CreateFinalVideo().execute();
				}else{
					errorAlertDialog(getResources().getString(R.string.no_enuf_mem));
				}
				
			}else{
				previewVideoView();
			}
		}
	

	
		setInitialValues();
		
		 mHandler = new Handler() {
		    public void handleMessage(Message msg) {
    	    if(PROG_PESUDO<40){
    	    	PROG_PESUDO = PROG_PESUDO+1;
    	    }
    	    
    	     if(MergerTask_complete && PROG_PESUDO<60){
    	    	 PROG_PESUDO = PROG_PESUDO+1;
    	    }
    	     
    	     if(Increasevol_input  && PROG_PESUDO<100){
    	    	 PROG_PESUDO = PROG_PESUDO+1;
    	    } 
    	     
    	     
    	    	
    	    		Log.d("progval"," "+PROG_PESUDO);
    	     	pb.setProgress(PROG_PESUDO);
    			progpercent.setText(String.valueOf(PROG_PESUDO+" %"));
    	    	
    	    
            	
            	
            }
		};
		
		super.onCreate(savedInstanceState);
	}

	
	
	private void deleteFiles() {
		String videorollermerge = "mergerresult.mp4";
	    String videorollvoicehigh="mergerresultvoicehigh.mp4";
	  
	    String finalcomp ="finalcomplete.mp4";
	 
		//String 
		String root = TabViewActivity.getProjectPath();
		deleteOldFile(new File(root+"/"+videorollermerge).getName());
		deleteOldFile(new File(root+"/"+videorollvoicehigh).getName());
		deleteOldFile(new File(root+"/"+finalcomp).getName());
		
		
		
	}
		
	private void previewVideoView() {
		//String video1 = prefs.getString("media_path", null);
		//System.out.println("video 1" +video1);
		String existingVideo = db.getMergedVideoOnly(TabViewActivity.getProjectName());
		if(existingVideo!=null){
			if(!(existingVideo.equals(""))){
		Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(existingVideo,
		        MediaStore.Images.Thumbnails.MINI_KIND);
		
		//VideoView video = (VideoView)findViewById(R.id.video_preview);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(thumbnail);
		mSurfaceView.setBackgroundDrawable(bitmapDrawable);
			}
		}
	}

	private void initializeButtonObject() {
		button_home = (Button) findViewById(R.id.btn_home);
		button_PreviewPlay = (Button)findViewById(R.id.btn_preview_play);
		button_upload = (Button)findViewById(R.id.btn_upload);
		btn_save=(Button)findViewById(R.id.btn_save);
	}
	
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(VideoEditorActvityLolipop.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		btn_save.setTypeface(abel_ttf);
		button_upload.setTypeface(abel_ttf);
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
		tabViewActivity.enabletab(8);
		tabViewActivity.enabletab(9);
	}

	private void addOnClickListner() {

		button_home.setOnClickListener(this);
		button_PreviewPlay.setOnClickListener(this);
		button_upload.setOnClickListener(this);
		btn_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_home:
			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SHOW_PREVIEW_TAB2 = 0;
			Constants.SHOW_PREVIEW_TAB3 = 0;
			Constants.SHOW_PREVIEW_TAB4 = 0;
			Constants.SHOW_PREVIEW_TAB5 = 0;*/
			Intent intent = new Intent(VideoEditorActvityLolipop.this,HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_preview_play:{
			//Toast.makeText(VideoEditorActivity.this, "Start Preview", Toast.LENGTH_SHORT).show();
			String FinalVideo = db.getFinalVideo();
			System.out.println("FinalVideo: "+FinalVideo);
			if(FinalVideo!=null){
				if(!(FinalVideo.equals(""))){
			Intent intent_play = new Intent(VideoEditorActvityLolipop.this, VideoPlayerActivity.class);
			intent_play.putExtra(VIDEO_URL, FinalVideo);
			startActivity(intent_play);
				}
			}else{
				errorAlertDialog(getResources().getString(R.string.no_video_found));
			}
		}
		break;
		case R.id.btn_upload:{
			
			
			System.out.println("Topics: "+db.getTopics());
			if((db.getTopics()==null)||(db.getTopics()).equals(""))
			{
				showConfirmDialog();
			}
			else
			{
				int logoutStatus = db.getLogoutStatus();
				System.out.println("Logout Status Upload page: "+logoutStatus);
				if (logoutStatus == 0) {
					if(!(db.getTopics()==null)){
						if(!(db.getTopics()).equals("")){
					Intent i = new Intent(this, LoginActivity.class);
					i.putExtra(Constants.PRE_LOGIN_ACTIVITY, "upload_screen");
					startActivity(i);
						}
					}else{
						showConfirmDialog();
					}
				} else if(logoutStatus == 1){
					ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
					if (networkInfo != null && networkInfo.isConnected()) {
						// Network connection is available
						// upload screen
						startActivity(new Intent(VideoEditorActvityLolipop.this,
								Upload_Progress_Activity.class));

					} else {
						// Network connection is not available
						errorAlertDialog(getResources().getString(R.string.network_conn_error));
					}
					
				}
			}
		}
		break;
		case R.id.btn_save:
		{
			
				pd = new TransparentProgressDialog(this, R.drawable.spinner,getResources().getString(R.string.dialog_save));
				pd.show();
				pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				h.postDelayed(new Runnable() {

					@Override
					public void run() {
						//btn_save.setEnabled(false);
						Constants.SAVED = 1;
						pd.dismiss();
						//Return to Home screen
						Intent intent = new Intent(VideoEditorActvityLolipop.this,HomeScreen.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						startActivity(intent);
						finish();

					}
				},3000);
			
		}
		break;

		default:
			break;
		}

	
	}
	
	
	private void errorAlertDialog(String message) {
		
	DialogFragment newFragment = ErrorDialog.newInstance(VideoEditorActvityLolipop.this,message,"ffmpeg");
		newFragment.show( getFragmentManager(), "dialog");
		newFragment.setCancelable(false);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	public class CreateFinalVideo extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			showExportProgress();
			super.onPreExecute();

		}
		@Override
		protected Void doInBackground(Void... params) {
			//Constants.MERGED_STATUS_CHANGE = 0;
			
				 mv = mergeVideos();
				System.out.println("mv: "+mv);
				//rl.setVisibility(View.VISIBLE);
			if(mv == null){
				cancel(true);
				
				//errorAlertDialog(getResources().getString(R.string.no_video_found));
				//rl.setVisibility(View.GONE);
			}
			String mergedVideoOnly = db.getMergedVideoOnly(TabViewActivity.getProjectName());
			System.out.println("mergedVideoOnly: "+mergedVideoOnly);
			
			profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
			System.out.println("width video frame "+profile.videoFrameWidth);
			
			
			if(profile.videoFrameWidth==720)
			{
				Constants.SET_B_ROLL=1;
			}
			else
			{
				Constants.SET_B_ROLL=2;
			}
		

			//Moto-E not Working...
			//Add Lower Resolution video here
			
			if(Constants.SET_B_ROLL==1)
			{
			//InputStream aa = getApplicationContext().getAssets().open("b_roll_footage_lower");
		/*	String */broll = saveAssetAsFile("b_roll_footage_lower");
			}
			else if(Constants.SET_B_ROLL==2)
			{
				//InputStream aa = getApplicationContext().getAssets().open("b_roll_footage");
			/*	String*/ broll = saveAssetAsFile("b_roll_footage");
			}
		//	String broll = saveAssetAsFile("b_roll_footage");

		
			// add video item preeeeti
			//addBackgroundmusic();
			return null;
		}

		
		
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			
			initFFMPEG();
			addVideo(broll,mv);
			db.UpdateServerVideoUploadStatus(0);
			db.updateMergedStatus(Constants.MERGED_STATUS_INPROGRESS);
			executeUpdateofProgress();
			/*	h.removeCallbacks(r);
			
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					db.updateMergedStatus(Constants.MERGED_STATUS_INPROGRESS);
					mPendingExportFilename = FileUtils.createMovieName(
							MediaProperties.FILE_MP4);
					System.out.println("export file name: "+mPendingExportFilename);
					
					 CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
					    Log.d(TAG, "Max Width: " + profile.videoFrameWidth);
					    Log.d(TAG, "Max Height: " + profile.videoFrameHeight);
					    Log.d(TAG, "Max Bitrate: " + profile.videoBitRate);
					int Height = 720;
					int Bitrate = 8000000;
					    if(profile.videoFrameHeight < 720){
					    	Height = profile.videoFrameHeight;
					    }
					    if(profile.videoBitRate < 8000000){
					    	Bitrate = profile.videoBitRate;
					    }
				
					    ApiService.exportVideoEditor(VideoEditorActivity.this, mProjectPath,
								mPendingExportFilename, Height, Bitrate);
					showExportProgress();

				}
			}, 1000);
			*/
		}
	}

	private String saveAssetAsFile(String rawVideoPath) {
		String filePath = null;
		try {
			// int resId = getApplicationContext().getResources().getIdentifier(rawVideoPath, "raw", this.getPackageName());
			//InputStream in = getResources().openRawResource(resId);
			InputStream in = getApplicationContext().getAssets().open(rawVideoPath+".mp4");
			filePath = TabViewActivity.getProjectPath()+"/"+rawVideoPath+".mp4";
			FileOutputStream out = new FileOutputStream(filePath);
			byte[] buff = new byte[1024];
			int read = 0;

			try {
				while ((read = in.read(buff)) > 0) {
					out.write(buff, 0, read);
				}
			} finally {
				in.close();

				out.close();
			}

		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	private void showExportProgress() {
		
		
		mExportProgressDialog = new ProgressDialog(this,R.style.TransparentProgressDialog) {

			
			@Override
			public void onStart() {
				super.onStart();
				mCpuWakeLock.acquire();
			}
			@Override
			public void onStop() {
				super.onStop();
				mCpuWakeLock.release();
			}
		};

		
		mExportProgressDialog.setMessage(null);
		mExportProgressDialog.setIndeterminate(false);
		mExportProgressDialog.setCancelable(false);
		
		mExportProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mExportProgressDialog.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_loader));

		mExportProgressDialog.setMax(100);
		mExportProgressDialog.setCanceledOnTouchOutside(false);
		mExportProgressDialog.setCanceledOnTouchOutside(false);
		mExportProgressDialog.show();

		
		mExportProgressDialog.getWindow().setContentView(R.layout.horizontal_loader);
		mExportProgressDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		pb = (ProgressBar)mExportProgressDialog.findViewById(R.id.progressBar1);
        iv = (ImageView)mExportProgressDialog.findViewById(R.id.ImageView_spinner);
		 tv = (TextView)mExportProgressDialog.findViewById(R.id.textView1);
		 progpercent = (TextView)mExportProgressDialog.findViewById(R.id.tv_progress_percent);
		tv.setTypeface(abel_ttf);
		
		 anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(3000);
		iv.setAnimation(anim);
		iv.startAnimation(anim);

		
		mExportProgressDialog.setProgressNumberFormat("");
		mExportProgressDialog.setProgressPercentFormat(null);

	}
	
	public void initFFMPEG(){
		 ffmpeg = FFmpeg.getInstance(VideoEditorActvityLolipop.this);
	     loadFFMpegBinary();
	     
	     MergerTask_complete= false;
		 Increasevol_input= false;
		 OverlaymusictoMergedItem= false;
	}
		
	private void loadFFMpegBinary() {
	        try {
	            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
	                @Override
	                public void onFailure() {
	                    showUnsupportedExceptionDialog();
	                }
	            });
	        } catch (FFmpegNotSupportedException e) {
	            showUnsupportedExceptionDialog();
	        }
	    }
	
	public void addVideo(String broll2, String mv2) {
		
		
		 execFFmpegBinarymergeTrailer(mv2, broll2);
	}
	
	private void increaseVideoVoice(String video){
		execFFmpegBinaryVoiceOverVolincrease(video);
		
	}
	
	public void addBgMusic() {
		String BgmusicPath = db.getSelectedBGMusic(TabViewActivity
				.getProjectName());
		System.out.println("song path: " + BgmusicPath);
	
		if (BgmusicPath != null) {
			if (!(BgmusicPath.equals(""))) {
				String songPath = saveAsFile(BgmusicPath);
				System.out.println("saveAs: " + songPath);
				if (songPath != null) {
					if (!(songPath.equals(""))) {
						addFinalbackgroundtrack(songPath);
					}
				}
			}
		}
	}
	
	private void addFinalbackgroundtrack(String backgroundmusic){
		execFFmpegBinaryaddBackgroundMusic(backgroundmusic);
	}
	
	protected void showUnsupportedExceptionDialog() {
		errorAlertDialog(getResources().getString(R.string.ffmpegnotsup));
		}
	  
	private void execFFmpegBinarymergeTrailer(String mv2,String broll2) {


			 final String[] mergeTrailerCmd = {"-y","-i",mv2,
			 		    "-i",broll2,"-strict","experimental",
				    		    "-filter_complex",
				  "[0:v]scale=854x480,setsar=1:1[v0];[1:v]scale=854x480,setsar=1:1[v1];[v0][0:a][v1][1:a] concat=n=2:v=1:a=1",
				   "-ab","48000","-ac","2","-ar","22050","-s","854x480","-r","30","-vcodec","mpeg4","-b","2097k",TabViewActivity.getProjectPath()+"/mergerresult.mp4"};
		  
				
			 			 
	        try {
	            ffmpeg.execute(mergeTrailerCmd, new ExecuteBinaryResponseHandler() {
	                @Override
	                public void onFailure(String s) {
	                   display("FAILED---"+s);
	                }

	                @Override
	                public void onSuccess(String s) {
	                	 display("SUCCESS---"+s);
	                	 MergerTask_complete = true;
	                }

	                @Override
	                public void onProgress(String s) {
	                   Log.d("ron",s);
	                }

	                @Override
	                public void onStart() {
	                	
	                	display("------------START----------------------");
	                	 display(" "+getStringCommand(mergeTrailerCmd));
	                	 
	                }

	                @Override
	                public void onFinish() {
	                	PROG_PESUDO=40;
	                	 display("--------------END--------------------");
	                	 if(MergerTask_complete){
	                	 increaseVideoVoice(TabViewActivity.getProjectPath()+"/mergerresult.mp4");
	                	 }else{
	                		 errorAlertDialog(MergerFailed);
	                	 }
	                }
	            });
	        } catch (FFmpegCommandAlreadyRunningException e) {
	        	errorAlertDialog(MergeinProg);
	        }
	    }
		
	private void execFFmpegBinaryVoiceOverVolincrease(String video){
			
			 final String[] voiceOvervolincrease={"-y","-i",video,"-vcodec","copy","-af","volume=1.7","-strict","-2",TabViewActivity.getProjectPath()+"/mergerresultvoicehigh.mp4"};
			
			 try{
			 ffmpeg.execute(voiceOvervolincrease, new FFmpegExecuteResponseHandler() {
				
				@Override
				public void onStart() {
					display("------------START----------------------");
               	 display(" "+getStringCommand(voiceOvervolincrease));
				}
				
				@Override
				public void onFinish() {
					PROG_PESUDO=60;
					display("------------END----------------------");
					
					if(Increasevol_input){
						addBgMusic();
					}else{
						 errorAlertDialog(MergerFailed);
					}
					
				}
				
				@Override
				public void onSuccess(String message) {
					 display("SUCCESS---"+message);
					 Increasevol_input=true;
				}
				
				@Override
				public void onProgress(String message) {
					 Log.d("ron",message);
					
				}
				
				@Override
				public void onFailure(String message) {
					 display("FAILED---"+message);
				}
			});
			 
		} catch (FFmpegCommandAlreadyRunningException e) {
           errorAlertDialog(MergeinProg);
        }
		}
	
	private void execFFmpegBinaryaddBackgroundMusic(String backgroundmusic) {
			
			final String[] addBckgroundMusicCmd = {"-y","-i",TabViewActivity.getProjectPath()+"/mergerresultvoicehigh.mp4", "-i",backgroundmusic, "-c:v","copy", "-filter_complex", "[0:a][1:a]amerge,pan=stereo:c0<c0+c2:c1<c1+c3","-strict","-2",TabViewActivity.getProjectPath()+"/finalcomplete.mp4"};
		     
			
			
	        try {
	            ffmpeg.execute(addBckgroundMusicCmd, new ExecuteBinaryResponseHandler() {
	                @Override
	                public void onFailure(String s) {
	                   display("FAILED---"+s);
	                }

	                @Override
	                public void onSuccess(String s) {
	                	 display("SUCCESS---"+s);
	                	 OverlaymusictoMergedItem=true;
	                }

	                @Override
	                public void onProgress(String s) {
	                	 Log.d("ron",s);
	                }

	                @Override
	                public void onStart() {
	                	display("------------START----------------------");
	                	 display(" "+getStringCommand(addBckgroundMusicCmd));
	                }

	                @Override
	                public void onFinish() {
	                	 display("--------------END--------------------");
	                	 if(OverlaymusictoMergedItem){
	                	 PROG_PESUDO=100;
	                	 
	                	 if(timer!=null){
	                		 timer.cancel();
		                	 timer.purge();
	                	 }
	                	 
	                	 
	                	 if(mExportProgressDialog!=null){
	                		 if(mExportProgressDialog.isShowing()){
	                			 mExportProgressDialog.dismiss();
	                		 }
	 	       			
	                	 }
	 	       			
	 	       			db.updateMergedStatus(Constants.MERGED_STATUS_COMPLETED);
                     db.updateFinalVideo(TabViewActivity.getProjectPath()+"/finalcomplete.mp4");
                     
                     analytics_conveyer.sendScreenandEvent(Constants.UBC_SCREEN, Constants.UBC_CATEGORY, Constants.UBC_ACTION, Constants.UBC_LABEL);
             		 previewVideoView();
             		
             	
	                	 }
	                	 else{
	                		 errorAlertDialog(MergerFailed);
	                	 }
	                
	                }
	            });
	        } catch (FFmpegCommandAlreadyRunningException e) {
	        	errorAlertDialog(MergeinProg);
	        }
	    }
	
	private String saveAsFile(String rawSongPath) {
			String filePath = null;
			try {
				int resId = getApplicationContext().getResources().getIdentifier(rawSongPath, "raw", this.getPackageName());
				InputStream in = getResources().openRawResource(resId);
				filePath = TabViewActivity.getProjectPath()+"/"+rawSongPath+".mp3";
				FileOutputStream out = new FileOutputStream(filePath);
				byte[] buff = new byte[1024];
				int read = 0;

				try {
					while ((read = in.read(buff)) > 0) {
						out.write(buff, 0, read);
					}
				} finally {
					in.close();

					out.close();
				}

			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return filePath;
		}
	
	private void executeUpdateofProgress(){
    	
    	
    	 timer = new Timer();

         TimerTask task = new TimerTask() {
            @Override
            public void run() {
            	mHandler.obtainMessage().sendToTarget();    	
               
            }
        };

        timer.scheduleAtFixedRate(task,0,3000);
    	
    	
    	
		}
	
	public void display(String s){
	    	Log.d("PLS--",s);
	    }
	    
    protected String getStringCommand(String[] command) {
				StringBuilder mybuild = new StringBuilder();
				for(int i=0;i<command.length;i++){
					mybuild.append(command[i]+" ");
				}
				
				return mybuild.toString();
				
			}
		
	private void deleteOldFile(String existingFile) {
		System.out.println("existingFile: "+existingFile);
		System.out.println("projectpath: "+TabViewActivity.getProjectPath());
		String path = TabViewActivity.getProjectPath();
		if(path!=null){
			if(!(path.equals(""))){
		
		File dir = new File(TabViewActivity.getProjectPath());
		File file = new File(dir,existingFile );
		boolean deleted = file.delete();
		System.out.println("deleted previous Files: "+deleted);
		}
		}
	}
	
	private String mergeVideos() {

		//Delete the old merged file if present..
		String existingVideo = db.getMergedVideoOnly(TabViewActivity.getProjectName());
		if(existingVideo!=null){
			//new File(existingVideo).getName();
			deleteOldFile(new File(existingVideo).getName());
			//existingVideo
		}

		File mediaFile= null;
		DatabaseHelper db;
		db = new DatabaseHelper(this);
		String outputFilePath = null;
		try {
			String video1 = db.isVideo1Present();
			String video2 = db.isVideo2Present(TabViewActivity.getProjectName());
			String video3 = db.isVideo3Present(TabViewActivity.getProjectName());
			String video4 = db.isVideo4Present(TabViewActivity.getProjectName());
			String video5 = db.isVideo5Present(TabViewActivity.getProjectName());

			System.out.println("f1: " + video1);
			System.out.println("f2: " + video2);
			System.out.println("f3: " + video3);
			System.out.println("f4: " + video4);
			System.out.println("f5: " + video5);

			Movie BuildVideo1=null;
			Movie BuildVideo2 = null;
			Movie BuildVideo3 = null;
			Movie BuildVideo4 = null;
			Movie BuildVideo5 = null;

			if((video1!=null)||(video2!=null)||(video3!=null)||(video4!=null)||(video5!=null)){
					try {
						BuildVideo1 = MovieCreator.build(video1);
						

						BuildVideo2 = MovieCreator.build(video2);


						BuildVideo3 = MovieCreator.build(video3);


						BuildVideo4 = MovieCreator.build(video4);


						BuildVideo5 = MovieCreator.build(video5);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
						db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
					}
		}

			if((BuildVideo1!=null) && (BuildVideo2!=null) && (BuildVideo3!=null) 
					&& (BuildVideo4!=null )&& (BuildVideo5!=null)){

				/*Movie[] inMovies = new Movie[] { MovieCreator.build(video1),
							MovieCreator.build(video2), MovieCreator.build(video3),
							MovieCreator.build(video4),MovieCreator.build(video5) };*/
				System.out.println("Start merging...");
				Movie[] inMovies = new Movie[] { BuildVideo1,
						BuildVideo2, BuildVideo3,
						BuildVideo4,BuildVideo5};

				List<Track> videoTracks = new LinkedList<Track>();
				List<Track> audioTracks = new LinkedList<Track>();
				for (Movie m : inMovies) {
					for (Track t : m.getTracks()) {
						if (t.getHandler().equals("soun")) {
							audioTracks.add(t);
							System.out.println("soundddd");
						}
						if (t.getHandler().equals("vide")) {
							videoTracks.add(t);
							System.out.println("videooo");
						}
					}
				}
			
				//try chopping off audio samples here.
				
				
				
				
				

				System.out.println("audioTrack size: "+audioTracks.size());
				System.out.println("videoTracks size: "+videoTracks.size());
				Movie result = new Movie();
				if (audioTracks.size() > 0) {
					result.addTrack(new AppendTrack(audioTracks
							.toArray(new Track[audioTracks.size()])));
				}
				if (videoTracks.size() > 0) {
					result.addTrack(new AppendTrack(videoTracks
							.toArray(new Track[videoTracks.size()])));
				}

				Container out = new DefaultMp4Builder().build(result);
				//File mediaFile;
				String project_path = TabViewActivity.getProjectPath();
				File mediaStorageDir = new File(project_path);
				// Create the storage directory if it does not exist
				if (!mediaStorageDir.exists()) {
					if (!mediaStorageDir.mkdirs()) {
						Log.d("MyCameraApp", "failed to create directory");
						return null;
					}
				}
				String videoPrefix = "mergedVideo";
				String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());

				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ videoPrefix + timeStamp + ".mp4");

				FileOutputStream fos = new FileOutputStream(mediaFile);
				out.writeContainer(fos.getChannel());
				fos.close();
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//}
		String mergedVideoPath = null;
		if(mediaFile!=null){
			mergedVideoPath = mediaFile.getAbsolutePath();
			if(mergedVideoPath!=null){
				db.updateMergedVideo(mediaFile.getAbsolutePath());
			}else{
				db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
				return null;
			}
		}
		return mergedVideoPath;
	}


	public void showConfirmDialog()
	{
		custom_dialog.show();
	}
	public void gotoDialog()
	{
		custom_dialog = new Dialog(VideoEditorActvityLolipop.this);
		custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		custom_dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
		custom_dialog.setContentView(R.layout.topics_mandatory);
		custom_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		btnGoto=(Button)custom_dialog.findViewById(R.id.btn_goto);
		btnCancel=(Button)custom_dialog.findViewById(R.id.btn_cancel);
		btnGoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//Constants.SHOW_PREVIEW_TAB1 = 0;
				//Constants.SET_CURRENT_TAB = 1;
				tabViewActivity.setCurrentActivityTab(0);
				Constants.SET_TOPIC_SELECTED=1;
				custom_dialog.dismiss();
				//tabViewActivity.enabletab(1);

			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				custom_dialog.dismiss();
			}
		} );
	}


	@Override
	public void onPause() {
		super.onPause();
		System.out.println("onPause called videoeditoractivity:");
		

		// Dismiss the export progress dialog. If the export will still be pending
		// when we return to this activity, we will display this dialog again.
	/*if (mExportProgressDialog != null) {
			mExportProgressDialog.dismiss();
			
			
		}*/

		FrameLayout fl = (FrameLayout)findViewById(R.id.video_frame_view);
		
		
		Button preview_play = (Button)findViewById(R.id.btn_preview_play);
		LinearLayout ll_btn = (LinearLayout)findViewById(R.id.ll_button_layout);
		
		RelativeLayout rl = (RelativeLayout)findViewById(R.id.mainLayout);
		
		rl.removeView(fl);
		rl.removeView(preview_play);
		rl.removeView(ll_btn);

		
		//fl.setVisibility(View.GONE);
		//fl.removeAllViews();
		//fl.removeView(mSurfaceView);
	}

	@Override
	public void onResume() {
		super.onResume();
		Constants.SET_CURRENT_TAB = 9;
		System.out.println("OnResume merge activity");
		
		
		
		rl.removeView(fl);
		rl.removeView(button_PreviewPlay);
		rl.removeView(ll_btn);
		
		rl.addView(fl);
		rl.addView(button_PreviewPlay);
		rl.addView(ll_btn);
		
		
		String MergedStatus = db.getMergedStatus();
		System.out.println("OnResume merge status: " +MergedStatus);
		/*
		 * Check the status of merged video.
		 * MERGED_STATUS_INPROGRESS = Video not yet merged completely
		 * MERGED_STATUS_COMPLETED = Video merged completed
		 * MERGED_STATUS_NEW = Video not merged
		 */
		if(MergedStatus!=null){
			if(MergedStatus.equals(Constants.MERGED_STATUS_INPROGRESS)){
				//Video merging in progress
				System.out.println("mExportProgressDialog: "+mExportProgressDialog);
				if(mExportProgressDialog != null){
					if(!mExportProgressDialog.isShowing()){
					mExportProgressDialog.show();	
					if(iv!=null && anim!=null){
						iv.startAnimation(anim);
					}
					}
				}else{              
					deleteFiles();
					if(new CheckAvailableStorage().hasEnoughInternalMemory()){
					new CreateFinalVideo().execute();
					}else{
						errorAlertDialog(getResources().getString(R.string.no_enuf_mem));
					}
				}
				
				
			}else if(MergedStatus.equals(Constants.MERGED_STATUS_COMPLETED)){
				//Video merged
				mSurfaceView = (PreviewSurfaceView) findViewById(R.id.video_view);
				previewVideoView(); 
				db.updateCompletedScreen(10);
				if(mExportProgressDialog!=null){
					mExportProgressDialog.dismiss();
					mExportProgressDialog = null;
				}
			}/*else if(MergedStatus.equals(Constants.MERGED_STATUS_NEW)){
				System.out.println("Onresume: Merged status new ");
				//cancelExport();
				if(mExportProgressDialog!=null){
				mExportProgressDialog.dismiss();
				}
				deleteFiles();
				new CreateFinalVideo().execute();
				// Video not Merged
				//deleteFiles();
				//Starts the video merging process
				//new CreateFinalVideo().execute();
			}*/
		}
		
		if(Constants.EXPORT_ERROR == 1){
			Constants.EXPORT_ERROR = 0;
			//Re-Merge the video
			deleteFiles();
			if(new CheckAvailableStorage().hasEnoughInternalMemory()){
			 new CreateFinalVideo().execute();
			}else{
				errorAlertDialog(getResources().getString(R.string.no_enuf_mem));
			}
		}

		
		//startActivity(new Intent(this, VideoEditorActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
	}
	

}
