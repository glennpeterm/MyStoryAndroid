package com.test.socialnetwork;

import com.database.DatabaseHelper;
import net.onehope.mystory.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class TutorialVideoLaunch extends Activity implements OnClickListener
{

	private VideoView myVideoView;
	private int position = 0;
	private MediaController mediaControls;
	private Button mBtnskip;
	//DatabaseHelper dbHelp;

	@SuppressLint("NewApi") @Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//getActionBar().hide();
		// set the main layout of the activity
		setContentView(R.layout.launch_video_activity);
		
		//dbHelp= new DatabaseHelper(this);
		//dbHelp.updateLaunch();


		//set the media controller buttons
		if (mediaControls == null)
		{
			mediaControls = new MediaController(TutorialVideoLaunch.this);
		}

		//initialize the VideoView
		myVideoView = (VideoView) findViewById(R.id.video_view);

		mBtnskip=(Button)findViewById(R.id.btnSkip);
		mBtnskip.setOnClickListener(this);

		try {
			//set the media controller in the VideoView
			myVideoView.setMediaController(null);

			//set the uri of the video to be played
			myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mystory_explanatory));
			System.out.println("\n android.resource://" + getPackageName() + "/" + R.raw.mystory_explanatory);

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		myVideoView.requestFocus();
		//we also set an setOnPreparedListener in order to know when the video file is ready for playback
		myVideoView.setOnPreparedListener(new OnPreparedListener()
		{

			public void onPrepared(MediaPlayer mediaPlayer)
			{
				// close the progress bar and play the video

				//if we have a position on savedInstanceState, the video playback should start from here
				myVideoView.seekTo(position);
				if (position == 0) 
				{
					myVideoView.start();
				}
				else 
				{
					//if we come from a resumed activity, video playback will be paused
					myVideoView.pause();
				}
			}
		});

		myVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
		{

			@Override
			public void onCompletion(MediaPlayer mp)
			{
				
				Intent i = new Intent(TutorialVideoLaunch.this, PhotoLaunchActivity.class);
				startActivity(i);
				TutorialVideoLaunch.this.finish();
			}
		});
		
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		//we use onSaveInstanceState in order to store the video playback position for orientation change
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		//we use onRestoreInstanceState in order to play the video playback from the stored position 
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.btnSkip:
			myVideoView.stopPlayback();
			Intent i = new Intent(TutorialVideoLaunch.this, PhotoLaunchActivity.class);
			startActivity(i);
			TutorialVideoLaunch.this.finish();
			break;

		default:
			break;
		}

	}

	@Override
	protected void onPause() {
		
		super.onPause();
		System.out.println("pause tutorial");
		myVideoView.pause();
		mBtnskip.performClick();
	//	finish();
/*		if (position == 0) 
		{
			myVideoView.start();
		}
		else 
		{
			//if we come from a resumed activity, video playback will be paused
			myVideoView.seekTo(position);
		}*/
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		
	}
}