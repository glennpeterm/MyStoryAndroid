package com.test.socialnetwork;

import java.io.File;

import net.onehope.mystory.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.database.DatabaseHelper;
import com.database.menu.Database;
import com.dialog.DialogActivity;
import com.dialog.ErrorDialog;
import com.dialog.UploadSuccessDialog;
import com.google.ytdl.Constants;
import com.google.ytdl.MainActivity;
import com.google.ytdl.UploadService;
import com.model.UserDetail;
import com.supportclasses.menu.CustomRequest;
import com.supportclasses.menu.JsonParser;
import com.supportclasses.menu.SingletonKeyValueHolder;


/*
 * hareesh :  
 * Added a reference to the class SendAnalytics, for sending analytics on click to 
 * google account.
 */

public class Upload_Progress_Activity extends Activity 
{
	
	private ProgressBar mPbUpload;
	private TextView mTvUploadPercentage;
	private TextView mTvUPloading;
	long totalSize = 0;
	DatabaseHelper db;
	// ProgressDialog progressBar;  
	private int progressBarStatus = 0;  
	private Handler progressBarHandler = new Handler();  
	private long fileSize = 0;  
	private Handler handler;

	public Typeface abel_ttf;
	public static Activity fa;
	Database dbClass;
	private RequestQueue queue;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.upload_progress);
		fa = this;
		handler = new Handler();

		db = new DatabaseHelper(this);
		 dbClass=new Database(getApplicationContext());
		mPbUpload=(ProgressBar)findViewById(R.id.pbUpload);
	
		mTvUploadPercentage=(TextView)findViewById(R.id.tvPercentage);
		mTvUPloading=(TextView)findViewById(R.id.tvUploading);
		setTypeFonts();
		//new Thread(new Task()).start();

		// Execute only when the video fails to upload to server but got uploaded to utube.
		int ServerUploadStatus = db.GetServerUploadStatus();
		//ServerUploadStatus = 1;
			if (ServerUploadStatus == 1) {
				//Server upload starts
				UploadVideoOnlyToServer();
			}else if(ServerUploadStatus == 0){
				//Youtube Upload starts
				new Thread(new Task()).start();


				String FinalVideo = db.getFinalVideo();
				if (FinalVideo != null) {
					if (!(FinalVideo.equals(""))) {
						Uri videoUri = getImageContentUri(
								Upload_Progress_Activity.this, new File(FinalVideo));
						if (videoUri != null) {
							Intent uploadIntent = new Intent(
									Upload_Progress_Activity.this, UploadService.class);
							uploadIntent.setData(videoUri);
							uploadIntent.putExtra(MainActivity.ACCOUNT_KEY, "");
							startService(uploadIntent);
							System.out.println("Youtube upload started..");
							//Toast.makeText(Upload_Progress_Activity.this,
							//		R.string.youtube_upload_started, Toast.LENGTH_LONG)
							//		.show();
						}
					}

				}else{
					System.out.println("Video upload failed..22..");
					SuccessAlertDialog(getResources().getString(R.string.merged_video_notfound),getResources().getString(R.string.upload_heading_fail),"merge_failed");
					
				}
			}
		
		// async to upload to server

		//new UploadFileToServer().execute();
		
		
		int UploadProgress = db.getYouTubeUploadProgress();
		
		//publishProgress(UploadProgress);
		
		/*String FinalVideo = db.getFinalVideo();
		if (FinalVideo != null) {
			if (!(FinalVideo.equals(""))) {
				Uri videoUri = getImageContentUri(
						Upload_Progress_Activity.this, new File(FinalVideo));
				if (videoUri != null) {
					Intent uploadIntent = new Intent(
							Upload_Progress_Activity.this, UploadService.class);
					uploadIntent.setData(videoUri);
					uploadIntent.putExtra(MainActivity.ACCOUNT_KEY, "");
					startService(uploadIntent);
					System.out.println("Youtube upload started..");
					//Toast.makeText(Upload_Progress_Activity.this,
					//		R.string.youtube_upload_started, Toast.LENGTH_LONG)
					//		.show();
				}
			}

		}else{
			System.out.println("Video upload failed..22..");
			SuccessAlertDialog(getResources().getString(R.string.merged_video_notfound),getResources().getString(R.string.upload_heading_fail),"merge_failed");
			
		}*/
		//new Thread(new Task()).start();

		
		mHandler = new Handler(Looper.getMainLooper()) {
		    @Override
		    public void handleMessage(Message message) {
		    	mPbUpload.setProgress(100);
				mTvUploadPercentage.setText(Integer.toString(100)+" % Completed");
		    }
		};

		
	}

	private void UploadVideoOnlyToServer() {
		UserDetail user = dbClass.getUserLoginDetails();	
		String Email = user.getEmail();
		
		SendVideoDetails(createSelfie(getApplication(),Email,com.supportclasses.menu.Constants.LANGUAGE));
		
	}

	private void SendVideoDetails(final JSONObject jobjMain)
	{
		if(queue==null)
			queue=SingletonKeyValueHolder.getInstance().getRequestQueue(this);

		mTvUploadPercentage.setText(Integer.toString(10)+" % Completed");
		
		System.out.println("ADD INPcvbUT "+jobjMain.toString());

		CustomRequest jsonObjReq = new CustomRequest(Method.POST,
				com.supportclasses.menu.Constants.URL_BASE+com.supportclasses.menu.Constants.URL_ADD_SELFIE, jobjMain,
				new com.android.volley.Response.Listener<JSONObject>() {



			@Override
			public void onResponse(JSONObject response) {

				System.out.println("ADD USER SUCvbCESS "+response.toString());
						try {
							if (response != null) {
								if (response.getBoolean("Success")) {
									Log.d("roman","roman in here");
									//If value is 1 then Server upload fails.
									//If value is 0 then Server upload is Success.
									db.UpdateServerVideoUploadStatus(0);
									
									mPbUpload.setProgress(100);
									mTvUploadPercentage.setText(Integer.toString(100)+" % Completed");
									
									
									
									Intent intent = new Intent(Upload_Progress_Activity.this,DialogActivity.class);
									intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Upload_success_message");
									startActivity(intent);
									
								} else {
									//If value is 1 then Server upload fails.
									//If value is 0 then Server upload is Success.
									db.UpdateServerVideoUploadStatus(1);
									
									Intent intent = new Intent(Upload_Progress_Activity.this,DialogActivity.class);
									intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Upload_failed_message");
									startActivity(intent);
									

								}
							}else{
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			}
		}, new com.android.volley.Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			

				System.out.println("ADD FAIL "+error.toString());
				db.UpdateServerVideoUploadStatus(1);
				
				Intent intent = new Intent(Upload_Progress_Activity.this,DialogActivity.class);
				intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Upload_failed_message");
				startActivity(intent);
				
			}
		});

		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
				30000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 
	


		queue.add(jsonObjReq);


	}

	private  JSONObject createSelfie(Context context,String mail,String lang){
		// db = new DatabaseHelper(context);
		JsonParser jparser=new JsonParser();
		JSONObject jobj=new JSONObject();
		
			try {
				//YouTubeResponse response=jparser.YouTubeParser(returnedVideo.toPrettyString());
				String Desc = db.getDescription();
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_DESC,Desc);//descr
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_DESC, Desc);//video short desc
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_EMBEDCODE, "");//embed code
				
				String VideoId = db.getVideoId();
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_ID, VideoId);//youtube id
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_LANG, lang);//language
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_MAIL, mail);//email
				//scripture text
				
				String Scripture = db.getVerseText();
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_SCRIPTURE, Scripture);

				//Save Youtube Video Thumbnail url
				String Thumbnail_url = db.getThumbnailUrl();
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_THUMBNAIL, Thumbnail_url);//utube thumbnail url

				String Title = db.getTitle();
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_TITLE, Title);//title
				

				String video_url = db.getYoutubeVideoUrl();
				jobj.put(com.supportclasses.menu.Constants.KEY_VID_URL, "https://www.youtube.com/watch?v="+video_url);//utube url
				jobj.put(com.supportclasses.menu.Constants.KEY_BOOK_ID, "");//book id
				String BookName = db.getVerseIndex();
				String[] bk = BookName.split(" ");
				
				String bk1 = bk[0];
				String bk2 = "";
				String bk3 = null;
				String bk4 = null;
				String bk5 = null;
				if(bk1.matches("[0-9]+")){
					 bk2 = bk[1];
				}else{
					bk3 = bk[1];
					if(bk3!=null){
						String[] bkk = bk3.split(":");
						bk4 = bkk[0];
						bk5 = bkk[1];
						
					}
				}
				String bkname = bk1+" "+bk2;
				
				jobj.put(com.supportclasses.menu.Constants.KEY_BOOK_NAME, bkname);//book name
				
				
				
				jobj.put(com.supportclasses.menu.Constants.KEY_BOOK_ORDER, "");//book order
				jobj.put(com.supportclasses.menu.Constants.KEY_BOOK_CHAPTER, bk4);//book chapter
				jobj.put(com.supportclasses.menu.Constants.KEY_VERSE, bk5);//verse
				jobj.put(com.supportclasses.menu.Constants.KEY_BIBLE_NAME, "");//bible name
				
				String tags = db.getTags();
				
				jobj.put(com.supportclasses.menu.Constants.KEY_TAGS, tags);//tags
				
				//String topics = db.getTo
				String topics = db.getTopicsId();
				System.out.println("topics in db: "+topics);
				jobj.put(com.supportclasses.menu.Constants.KEY_TOPICS, topics);//topics
				
				String country = db.getCountry();
				jobj.put(com.supportclasses.menu.Constants.KEY_COUNTRY, country);//country
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
	
		return jobj;



	}
	

	private void setTypeFonts()
	{
		abel_ttf=Typeface.createFromAsset(this.getAssets(), com.supportclasses.Constants.FONT_ABEL_REGULAR);
		mTvUploadPercentage.setTypeface(abel_ttf,Typeface.BOLD);
		mTvUPloading.setTypeface(abel_ttf);
	}


	/**
	 * Uploading the file to server
	 * */
	/*private class UploadFileToServer extends AsyncTask<Void, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			// setting progress bar to zero
			mPbUpload.setProgress(0);
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress)
		{
			// Making progress bar visible
			mPbUpload.setVisibility(View.VISIBLE);

			// updating progress bar value
			mPbUpload.setProgress(progress[0]);

			
			// updating percentage value
			mTvUploadPercentage.setText(String.valueOf(progress[0]) + "%");
		}


		@Override
		protected void onPostExecute(String result)
		{

			// showing the server response in an alert dialog
			//      showAlert(result);
			super.onPostExecute(result);
			//SuccessAlertDialog(getResources().getString(R.string.upload_success),getResources().getString(R.string.upload_heading));
		}

		@Override
		protected String doInBackground(Void... params) 
		{
			
			//int prog = 45; // This number will represent your "progress"  give the progress here
			
			int UploadProgress = db.getYouTubeUploadProgress();
			
			publishProgress(UploadProgress);
			
			String FinalVideo = db.getFinalVideo();
			//Uri videoUri =Uri.fromFile(new File(FinalVideo));
			//Uri videoUri = MediaStore.Video.Media.getContentUri(FinalVideo);
			//Uri videoUri = Uri.fromFile(new File(FinalVideo));
			Uri videoUri = getImageContentUri(Upload_Progress_Activity.this,new File(FinalVideo));
			if (videoUri != null) {
	            Intent uploadIntent = new Intent(Upload_Progress_Activity.this, UploadService.class);
	            uploadIntent.setData(videoUri);
	            uploadIntent.putExtra(MainActivity.ACCOUNT_KEY, "");
	            startService(uploadIntent);
	            //Toast.makeText(Upload_Progress_Activity.this, R.string.youtube_upload_started,
	              //      Toast.LENGTH_LONG).show();
	            // Go back to MainActivity after upload
	            
	        }
			
			return null;
		}

	}*/
	public static Uri getImageContentUri(Context context, File videoFile) {
		String filePath = videoFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media._ID },
				MediaStore.Images.Media.DATA + "=? ",
				new String[] { filePath }, null);
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(MediaStore.MediaColumns._ID));
			
			return Uri.withAppendedPath(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id);
		} else {
			if (videoFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.DATA, filePath);
				//Error in Moto G
				return context.getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}
	private void SuccessAlertDialog(String msg,String title,String action)
	{
		DialogFragment newFragment = UploadSuccessDialog.newInstance(Upload_Progress_Activity.this,msg,title,action);
		//if(!(newFragment.isVisible())){
		newFragment.show(getFragmentManager(), "dialog");
		//}
	}
	public class Task implements Runnable {

		@Override
		public void run() {
			db.updateYouTubeUploadProgress("0");
			db.updateYouTubeUploadStatus(null);
			//int value1 = db.getYouTubeUploadProgress();
			
			//System.out.println("Inside TASK :..."+value1);
			
			//for (int i = 0; i <= 20; i++) {
			//while(db.getYouTubeUploadProgress()<=100){
			while(db.getYouTubeUploadProgress()<=100){
				if(db.getYouTubeUploadStatus()!=null){
					if(db.getYouTubeUploadStatus().equals(Constants.UPLOAD_FAILED)){
						System.out.println("Video upload failed..11..");
						//errorAlertDialog(getResources().getString(R.string.yt_upload_failed));
						//SuccessAlertDialog(getResources().getString(R.string.yt_upload_failed),getResources().getString(R.string.upload_heading_fail),"upload_failed");
						Intent intent = new Intent(Upload_Progress_Activity.this,DialogActivity.class);
						intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Upload_failed_message");
						startActivity(intent);
						//Update the server upload value to 0 , if Youtube upload fails
						db.UpdateServerVideoUploadStatus(0);
						break;
					}else if(db.getYouTubeUploadStatus().equals(Constants.UPLOAD_SUCCESS)){
					/**
					 * removed code and moved to diffreent place
					 * 
					 * */
						
						 Message message = mHandler.obtainMessage();
						    message.sendToTarget();
						
						//DatabaseHelper db = new DatabaseHelper(context);
					
						//Update the server upload value to 1 , if Youtube upload is Success
						
						
						//UploadFileToServer();
						//SuccessAlertDialog(getResources().getString(R.string.upload_success),getResources().getString(R.string.upload_heading),"homescreen_uploadSuccess");
						/*Intent intent = new Intent(Upload_Progress_Activity.this,DialogActivity.class);
						intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Upload_success_message");
						startActivity(intent);*/
						break;
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						int value1 = db.getYouTubeUploadProgress();
						
						mPbUpload.setProgress(value1);
						mTvUploadPercentage.setText(Integer.toString(value1)+" % Completed");
						
					}
				});
			}

		}

	}
	public void errorAlertDialog(String message) {
		DialogFragment newFragment = ErrorDialog.newInstance(Upload_Progress_Activity.this,message,null);
		newFragment.show(getFragmentManager(), "dialog");
		
	}
	/**
	 * Uploading the file to server
	 * */
	public void UploadFileToServer() {
		String FinalVideo = db.getFinalVideo();
		if (FinalVideo != null) {
			if (!(FinalVideo.equals(""))) {
				Uri videoUri = getImageContentUri(
						Upload_Progress_Activity.this, new File(FinalVideo));
				if (videoUri != null) {
					Intent uploadIntent = new Intent(
							Upload_Progress_Activity.this, UploadService.class);
					uploadIntent.setData(videoUri);
					uploadIntent.putExtra(MainActivity.ACCOUNT_KEY, "");
					startService(uploadIntent);
					System.out.println("Youtube upload started..");
					
				}
			}

		}else{
			System.out.println("Video upload failed..22..");
			SuccessAlertDialog(getResources().getString(R.string.merged_video_notfound),getResources().getString(R.string.upload_heading_fail),"merge_failed");
			
		}
		
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		//Thread.currentThread().interrupt();
		//handler.removeCallbacks(null);
	}
	@Override
	public void onBackPressed() {
		
		//super.onBackPressed();
	}

}
