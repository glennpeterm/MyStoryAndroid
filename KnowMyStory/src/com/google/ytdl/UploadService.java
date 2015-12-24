/*
 * Copyright (c) 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.ytdl;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import com.database.menu.Database;
import com.dialog.DialogActivity;

import net.onehope.mystory.R;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.common.collect.Lists;
import com.model.UserDetail;
import com.model.YouTubeResponse;
import com.scriptures.Wheel_Activity.GetBibleVerse;
import com.supportclasses.menu.Constants;
import com.supportclasses.menu.CustomRequest;
import com.supportclasses.menu.JsonParser;
import com.supportclasses.menu.SingletonKeyValueHolder;
import com.test.socialnetwork.Upload_Progress_Activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author Ibrahim Ulukaya <ulukaya@google.com>
 *         <p/>
 *         Intent service to handle uploads.
 */
public class UploadService extends IntentService {

	private RequestQueue queue;
	static DatabaseHelper db;

	/**
	 * defines how long we'll wait for a video to finish processing
	 */
	private static final int PROCESSING_TIMEOUT_SEC = 60 * 20; // 20 minutes

	/**
	 * controls how often to poll for video processing status
	 */
	private static final int PROCESSING_POLL_INTERVAL_SEC = 60;
	/**
	 * how long to wait before re-trying the upload
	 */
	private static final int UPLOAD_REATTEMPT_DELAY_SEC = 10;
	/**
	 * max number of retry attempts
	 */
	//private static final int MAX_RETRY = 3;
	private static final int MAX_RETRY = 0;
	private static final String TAG = "UploadService";
	/**
	 * processing start time
	 */
	private static long mStartTime;
	final HttpTransport transport = AndroidHttp.newCompatibleTransport();
	final JsonFactory jsonFactory = new GsonFactory();
	/* GoogleAccount*/Credential credential;
	/**
	 * tracks the number of upload attempts
	 */
	private int mUploadAttemptCount;

	public UploadService() {
		super("YTUploadService");
	}

	private static void zzz(int duration) throws InterruptedException {
		Log.d(TAG, String.format("Sleeping for [%d] ms ...", duration));
		//Thread.sleep(duration);
		Log.d(TAG, String.format("Sleeping for [%d] ms ... done", duration));
	}

	private static boolean timeoutExpired(long startTime, int timeoutSeconds) {
		long currTime = System.currentTimeMillis();
		long elapsed = currTime - startTime;
		if (elapsed >= timeoutSeconds * 1000) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Uri fileUri = intent.getData();

		List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");


		// Authorize the request.
		try {
			credential = Auth.authorize(scopes, "uploadvideo",getApplicationContext());

			String appName = getResources().getString(R.string.app_name);
			final YouTube youtube =
					new YouTube.Builder(transport, jsonFactory, null).setApplicationName(
							appName).setHttpRequestInitializer(credential).build();

			try {
				tryUploadAndShowSelectableNotification(fileUri, youtube);
			} catch (InterruptedException e) {
				// ignore
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		/*  credential =
                GoogleAccountCredential.usingOAuth2(getApplicationContext(), Lists.newArrayList(Auth.SCOPES));
        credential.setSelectedAccountName(chosenAccountName);
        credential.setBackOff(new ExponentialBackOff());*/




	}

	private void tryUploadAndShowSelectableNotification(final Uri fileUri, final YouTube youtube) throws InterruptedException {
		while (true) {
			Log.i(TAG, String.format("Uploading [%s] to YouTube", fileUri.toString()));
			String videoId = tryUpload(fileUri, youtube);
			if (videoId != null) {
				Log.i(TAG, String.format("Uploaded video with ID: %s", videoId));
				//Upload Finished.
				
				tryShowSelectableNotification(videoId, youtube);
				return;
			} else {
				Log.e(TAG, String.format("Failed to upload %s", fileUri.toString()));
				if (mUploadAttemptCount++ < MAX_RETRY) {
					Log.i(TAG, String.format("Will retry to upload the video ([%d] out of [%d] reattempts)",
							mUploadAttemptCount, MAX_RETRY));
					zzz(UPLOAD_REATTEMPT_DELAY_SEC * 1000);
				} else {
					Log.e(TAG, String.format("Giving up on trying to upload %s after %d attempts",
							fileUri.toString(), mUploadAttemptCount));
					return;
				}
			}
		}
	}

	private void tryShowSelectableNotification(final String videoId, final YouTube youtube)
			throws InterruptedException {
		mStartTime = System.currentTimeMillis();
		boolean processed = false;
		while (!processed) {
			processed = ResumableUpload.checkIfProcessed(videoId, youtube);
			if (!processed) {
				
				// wait a while
				Log.d(TAG, String.format("Video [%s] is not processed yet, will retry after [%d] seconds",
						videoId, PROCESSING_POLL_INTERVAL_SEC));
				if (!timeoutExpired(mStartTime, PROCESSING_TIMEOUT_SEC)) {
					zzz(PROCESSING_POLL_INTERVAL_SEC * 1000);
				} else {
					Log.d(TAG, String.format("Bailing out polling for processing status after [%d] seconds",
							PROCESSING_TIMEOUT_SEC));
					return;
				}
			} else {
				ResumableUpload.showSelectableNotification(videoId, getApplicationContext());
				return;
			}
		}
	}

	private String tryUpload(Uri mFileUri, YouTube youtube) {
		
		
		long fileSize;
		InputStream fileInputStream = null;
		String videoId = null;
		Video vid=null;
		Database dbClass=new Database(getApplicationContext());

		try {
			fileSize = getContentResolver().openFileDescriptor(mFileUri, "r").getStatSize();
			fileInputStream = getContentResolver().openInputStream(mFileUri);
			String[] proj = {MediaStore.Images.Media.DATA};
			Cursor cursor = getContentResolver().query(mFileUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			System.out.println("cursor path 1: "+cursor.getString(column_index));
			
			vid = ResumableUpload.upload(youtube, fileInputStream, fileSize, mFileUri, cursor.getString(column_index), getApplicationContext());
			if(vid!=null)
			{
				videoId=vid.getId()	;
				//Fetch login email from db and send
				UserDetail user = dbClass.getUserLoginDetails();	
				String Email = user.getEmail();
				
				SendVideoDetails(createSelfie(getApplication(),vid,Email,Constants.LANGUAGE));
			}
			
			//adding additional 

		} catch (FileNotFoundException e) {
			Log.e(getApplicationContext().toString(), e.getMessage());
		} finally {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				// ignore
			}
		}
		return videoId;
	}


	private static JSONObject createSelfie(Context context,Video returnedVideo,String mail,String lang){
		 db = new DatabaseHelper(context);
		JsonParser jparser=new JsonParser();
		JSONObject jobj=new JSONObject();
		try {
			YouTubeResponse response=jparser.YouTubeParser(returnedVideo.toPrettyString());
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_DESC, response.getDescription());//descr
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_DESC, response.getDescription());//video short desc
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_EMBEDCODE, "");//embed code
			
			//Save Youtube video id to DB
			db.SaveYoutubeVideoId(returnedVideo.getId().toString());
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_ID, returnedVideo.getId().toString());//youtube id
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_LANG, lang);//language
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_MAIL, mail);//email
			//scripture text
			
			String Scripture = db.getVerseText();
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_SCRIPTURE, Scripture);

			//Save Youtube Video Thumbnail url
			db.SaveYouTubeVideoThumbnailUrl(response.getThumbanil());
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_THUMBNAIL, response.getThumbanil());//utube thumbnail url

			jobj.put(com.supportclasses.menu.Constants.KEY_VID_TITLE, response.getTitle());//title
			
			//Save Youtube video URL to DB
			db.SaveYoutubeVideoUrl("https://www.youtube.com/watch?v="+response.getId());
			jobj.put(com.supportclasses.menu.Constants.KEY_VID_URL, "https://www.youtube.com/watch?v="+response.getId());//utube url
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobj;



	}
	
	
	private void SendVideoDetails(final JSONObject jobjMain)
	{

		if(queue==null)
			queue=SingletonKeyValueHolder.getInstance().getRequestQueue(this);

		System.out.println("ADD INPUT "+jobjMain.toString());

		CustomRequest jsonObjReq = new CustomRequest(Method.POST,
				Constants.URL_BASE+Constants.URL_ADD_SELFIE, jobjMain,
				new com.android.volley.Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {

				System.out.println("ADD USER SUCCESS "+response.toString());
						try {
							if (response != null) {
								if (response.getBoolean("Success")) {
									
									//If value is 1 then Server upload fails.
									//If value is 0 then Server upload is Success.
									db.UpdateServerVideoUploadStatus(0);
									
									Intent intent = new Intent(getApplicationContext(),DialogActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "Upload_success_message");
									startActivity(intent);
									
								} else {
									//If value is 1 then Server upload fails.
									//If value is 0 then Server upload is Success.
									db.UpdateServerVideoUploadStatus(1);
									
									Intent intent = new Intent(getApplicationContext(),DialogActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
				
				Intent intent = new Intent(getApplicationContext(),DialogActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

@Override
public void onDestroy() {
	
	super.onDestroy();
	System.out.println("OnDestroy called in UploadService...");
}



}
