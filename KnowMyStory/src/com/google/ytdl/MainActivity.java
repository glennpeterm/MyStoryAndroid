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

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.onehope.mystory.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.api.client.extensions.android.http.AndroidHttp;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
//import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
//import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;
//import com.google.ytdl.util.ImageFetcher;
//import com.google.ytdl.util.Upload;
//import com.google.ytdl.util.Utils;
//import com.google.ytdl.util.VideoData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Ibrahim Ulukaya <ulukaya@google.com>
 *         <p/>
 *         Main activity class which handles authorization and intents.
 */
public class MainActivity extends Activity {
	// private static final int MEDIA_TYPE_VIDEO = 7;
	public static final String ACCOUNT_KEY = "accountName";
	public static final String MESSAGE_KEY = "message";
	public static final String YOUTUBE_ID = "youtubeId";
	public static final String YOUTUBE_WATCH_URL_PREFIX = "http://www.youtube.com/watch?v=";
	static final String REQUEST_AUTHORIZATION_INTENT = "com.google.example.yt.RequestAuth";
	static final String REQUEST_AUTHORIZATION_INTENT_PARAM = "com.google.example.yt.RequestAuth.param";
	private static final int REQUEST_GOOGLE_PLAY_SERVICES = 0;
	private static final int REQUEST_GMS_ERROR_DIALOG = 1;
	private static final int RESULT_PICK_IMAGE_CROP = 4;
	private static final int RESULT_VIDEO_CAP = 5;
	private Uri mFileURI = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_home);

	}

	/**
	 * This method checks various internal states to figure out at startup time
	 * whether certain elements have been configured correctly by the developer.
	 * Checks that:
	 * <ul>
	 * <li>the API key has been configured</li>
	 * <li>the playlist ID has been configured</li>
	 * </ul>
	 *
	 * @return true if the application is correctly configured for use, false if
	 *         not
	 */
	private boolean isCorrectlyConfigured() {
		// This isn't going to internationalize well, but we only really need
		// this for the sample app.
		// Real applications will remove this section of code and ensure that
		// all of these values are configured.
		if (Auth.KEY.startsWith("Replace")) {
			return false;
		}
		if (Constants.UPLOAD_PLAYLIST.startsWith("Replace")) {
			return false;
		}
		return true;
	}

	

	/*@Override
	protected void onResume() {
		super.onResume();
		if (broadcastReceiver == null)
			broadcastReceiver = new UploadBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(
				REQUEST_AUTHORIZATION_INTENT);
		LocalBroadcastManager.getInstance(this).registerReceiver(
				broadcastReceiver, intentFilter);
	}

	

	@Override
	protected void onPause() {
		super.onPause();
		if (broadcastReceiver != null) {
			LocalBroadcastManager.getInstance(this).unregisterReceiver(
					broadcastReceiver);
		}
		if (isFinishing()) {
			// mHandler.removeCallbacksAndMessages(null);
		}
	}*/


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_GMS_ERROR_DIALOG:
			break;
		case RESULT_PICK_IMAGE_CROP:
			if (resultCode == RESULT_OK) {
				mFileURI = data.getData();
				System.out.println("Utube image file uri: "+mFileURI);
				if (mFileURI != null) {
					Intent intent = new Intent(this, ReviewActivity.class);
					intent.setData(mFileURI);
					startActivity(intent);
				}
			}
			break;

		case RESULT_VIDEO_CAP:
			if (resultCode == RESULT_OK) {
				mFileURI = data.getData();
				System.out.println("Utube video file uri: "+mFileURI);
				if (mFileURI != null) {
					Intent intent = new Intent(this, ReviewActivity.class);
					intent.setData(mFileURI);
					startActivity(intent);
				}
			}
			break;
		
		}
	}

	

	public void pickFile(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("video/*");
		startActivityForResult(intent, RESULT_PICK_IMAGE_CROP);
	}

	public void recordVideo(View view) {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		// Workaround for Nexus 7 Android 4.3 Intent Returning Null problem
		// create a file to save the video in specific folder (this works for
		// video only)
		// mFileURI = getOutputMediaFile(MEDIA_TYPE_VIDEO);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, mFileURI);

		// set the video image quality to high
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

		// start the Video Capture Intent
		startActivityForResult(intent, RESULT_VIDEO_CAP);
	}

	public void showGooglePlayServicesAvailabilityErrorDialog(
			final int connectionStatusCode) {
		runOnUiThread(new Runnable() {
			public void run() {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						connectionStatusCode, MainActivity.this,
						REQUEST_GOOGLE_PLAY_SERVICES);
				dialog.show();
			}
		});
	}

	/**
	 * Check that Google Play services APK is installed and up to date.
	 */
	private boolean checkGooglePlayServicesAvailable() {
		final int connectionStatusCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (GooglePlayServicesUtil.isUserRecoverableError(connectionStatusCode)) {
			showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
			return false;
		}
		return true;
	}

	
	/*private class UploadBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(REQUEST_AUTHORIZATION_INTENT)) {
				Log.d(TAG, "Request auth received - executing the intent");
				Intent toRun = intent
						.getParcelableExtra(REQUEST_AUTHORIZATION_INTENT_PARAM);
				startActivityForResult(toRun, REQUEST_AUTHORIZATION);
			}
		}
	}*/
}
