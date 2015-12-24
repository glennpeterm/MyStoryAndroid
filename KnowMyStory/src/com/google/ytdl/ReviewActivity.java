/*
 * Know My Story 
 */
package com.google.ytdl;


import java.io.File;

import net.onehope.mystory.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ReviewActivity extends Activity {
    VideoView mVideoView;
    MediaController mc;
//    private String mChosenAccountName;
    private Uri mFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* getActionBar().setDisplayHomeAsUpEnabled(true);*/
        setContentView(R.layout.activity_review);
        Button uploadButton = (Button) findViewById(R.id.upload_button);
        Intent intent = getIntent();
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            uploadButton.setVisibility(View.GONE);
            setTitle(R.string.playing_the_video_in_upload_progress);
        }
        mFileUri = intent.getData();
        loadAccount();

        reviewVideo(mFileUri);
    }

    private void reviewVideo(Uri mFileUri) {
        try {
            mVideoView = (VideoView) findViewById(R.id.videoView);
            mc = new MediaController(this);
            mVideoView.setMediaController(mc);
            mVideoView.setVideoURI(mFileUri);
            mc.show();
            mVideoView.start();
        } catch (Exception e) {
            Log.e(this.getLocalClassName(), e.toString());
        }
    }

    private void loadAccount() {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(this);
//        mChosenAccountName = sp.getString(MainActivity.ACCOUNT_KEY, null);
        invalidateOptionsMenu();
    }

    public void uploadVideo(View view) {
       /* if (mChosenAccountName == null) {
            return;
        }*/
        // if a video is picked or recorded.
    	//String s = "/storage/sdcard0/Android/data/com.fingent.knowmystory/files/KnowMyStory/mjk/finalvideo.mp4";

    	//Uri fileUri = Uri.fromFile(new File(s));
    	//Uri mFileUri = getImageContentUri(this,new File(s));
    	System.out.println("mFileUri in ReviewActivity: "+mFileUri);
        if (mFileUri != null) {
            Intent uploadIntent = new Intent(this, UploadService.class);
            uploadIntent.setData(mFileUri);
            uploadIntent.putExtra(MainActivity.ACCOUNT_KEY, "");
            startService(uploadIntent);
            System.out.println(R.string.youtube_upload_started);
           // Toast.makeText(this, R.string.youtube_upload_started,
            //        Toast.LENGTH_LONG).show();
            // Go back to MainActivity after upload
            finish();
        }
        
    }

	/*public static Uri getImageContentUri(Context context, File videoFile) {
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
				return context.getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}*/

}
