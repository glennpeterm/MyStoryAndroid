/*
 * Know My Story 
 */
package com.mediacontrols;

import java.io.IOException;

import com.dialog.ErrorDialog;
import net.onehope.mystory.R;

import android.app.Activity;
import android.app.DialogFragment;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class VideoPlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl, OnClickListener, OnCompletionListener {

    SurfaceView videoSurface;
    MediaPlayer player;
    VideoControllerView controller;
    String VIDEO_URL = "video_url";
    String videoUrl = null;
    Button btn_back;
    ImageButton btn_play;
    RelativeLayout rl;
	private int duration=0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_video_player);
        
        videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);
        
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        
        btn_play = (ImageButton)findViewById(R.id.btn_pause);
        btn_play.setOnClickListener(this);
        
        rl = (RelativeLayout)findViewById(R.id.rl);
        rl.setOnClickListener(this);
        

        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        controller = new VideoControllerView(this);
        videoUrl = getIntent().getStringExtra(VIDEO_URL);
		if (videoUrl != null) {
			if (!(videoUrl.equals(""))) {
				try {
					player.setAudioStreamType(AudioManager.STREAM_MUSIC);
					// player.setDataSource(this,
					// Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));

					player.setDataSource(VideoPlayerActivity.this,
							Uri.parse(videoUrl));
					player.setOnPreparedListener(this);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					
					errorAlertDialog(getResources().getString(R.string.no_video_found));
				} catch (SecurityException e) {
					e.printStackTrace();
					errorAlertDialog(getResources().getString(R.string.no_video_found));
				} catch (IllegalStateException e) {
					e.printStackTrace();
					errorAlertDialog(getResources().getString(R.string.no_video_found));
				} catch (IOException e) {
					e.printStackTrace();
					errorAlertDialog(getResources().getString(R.string.no_video_found));
				}
			}
		}else{
			errorAlertDialog(getResources().getString(R.string.no_video_found));
		}
    }
	private void errorAlertDialog(String message) {
		DialogFragment newFragment = ErrorDialog.newInstance(VideoPlayerActivity.this,message,"mediarecorder_not_created");
		newFragment.show(getFragmentManager(), "dialog");
	}
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	super.onTouchEvent(event);
       // controller.show();
    	//controller.doPauseResume();
		//controller.show();
        return false;
    }

    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	player.setDisplay(holder);
        try {
			player.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//ShowAlert that video cannot be played.
			//finish the activity.
		}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        
    }
    // End SurfaceHolder.Callback
 
    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
    	duration = mp.getDuration();
        controller.setMediaPlayer(this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
        mp.start();
        controller.show();
    }
    // End MediaPlayer.OnPreparedListener

    // Implement VideoMediaController.MediaPlayerControl
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void seekTo(int i) {
        player.seekTo(i);
    }

    @Override
    public void start() {
        player.start();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {
        
    }
    @Override
    public void onBackPressed() {
    	
    	//super.onBackPressed();
    	
    	
    }
    // End VideoMediaController.MediaPlayerControl

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			//player.pause();
			controller.hide();
			player.stop();
			player.reset();
			player.release();
			player=null;
			finish();
			
			break;
		case R.id.btn_pause:
			controller.doPauseResume(btn_play);
			controller.show();
			break;
		case R.id.rl:
			controller.doPauseResume(btn_play);
			controller.show();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		controller.hide();
		player.stop();
		player.reset();
		player.release();
		player=null;
		finish();
		
	}
	
	@Override
	protected void onPause() {
		
		super.onPause();
		System.out.println("pause tutorial");
/*		controller.doPauseResume(btn_play);
		controller.show();*/
		finish();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		System.out.println("resume tutorial");
	/*	controller.doPauseResume(btn_play);
		controller.show();*/
/*		controller.hide();
		player.stop();
		player.release();*/
	//	finish();
	}


	/*public void showPlayBtn() {
		btn_play.setVisibility(View.VISIBLE);
		
	}

	public void removePlayBtn() {
		btn_play.setVisibility(View.GONE);
		
	}*/

}


























/*package com.mediacontrols;

import java.io.IOException;

import net.onehope.mystory.R;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.widget.FrameLayout;

public class VideoPlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl {

    SurfaceView videoSurface;
    MediaPlayer player = null;
    VideoControllerView controller;
    String VIDEO_URL = "video_url";
    String videoUrl = null;
    private PhoneStateListener phoneStateListener = null;
 // If the player was paused before being stopped.
    private boolean isPaused = false; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_video_player);
        
        videoSurface = (SurfaceView) findViewById(R.id.videoSurface);
        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);

     // Register for phone events so we can pause/restart audio when a call happens.
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Incoming call: Pause music
                    boolean s = isPaused;
                    pause();
                    isPaused = s; // Want to restore the state before we paused for phone call.
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    //Not in call: Play music
                    if(isPaused == true) {
                        pause();
                    }
                    else {
                        if(!isPlaying()) { // Only start if it's not currently started.
                            start();
                        }
                    }
                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    //A call is dialing, active or on hold. Pause music
                    boolean s = isPaused;
                    pause();
                    isPaused = s; // Want to restore the state before we paused for phone call.
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        videoUrl = getIntent().getStringExtra(VIDEO_URL);
        player = new MediaPlayer();
        player.reset();
        player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				if(mp!=null)
				mp.release();
				if(player!=null)
				player.release();
				
			}
		});
        controller = new VideoControllerView(this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
        controller.show();
        
        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if(videoUrl!=null)
            //player.setDataSource(this, Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"));
            player.setDataSource(this, Uri.parse(videoUrl));
            player.setOnPreparedListener(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       // controller.show();
        return false;
    }

    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	player.setDisplay(holder);
        try {
			player.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			player.reset();
		}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	 System.out.println("Surface Destroyed called...");
    	if(player!=null){
    		if(player.isPlaying()){
    			player.pause();
    			player.release();
    		}
    		//player.reset();
    		finish();
    	}
    	
       
    }
    // End SurfaceHolder.Callback

    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        controller.setAnchorView((FrameLayout) findViewById(R.id.videoSurfaceContainer));
        player.start();
    }
    // End MediaPlayer.OnPreparedListener

    // Implement VideoMediaController.MediaPlayerControl
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
    	
    	System.out.println("current position: "+player.getCurrentPosition());
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void seekTo(int i) {
        player.seekTo(i);
    }

    @Override
    public void start() {
        player.start();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {
        
    }
    @Override
    public void onBackPressed() {
    	
    	super.onBackPressed();
    	if(player!=null){
    		if(player.isPlaying())
    			player.pause();
    	}
    }
    
    @Override
    protected void onPause() {
    	
    	super.onPause();
    	if(player!=null){
    		if(player.isPlaying()){
    			player.pause();
    			player.stop();
    			player.release();
    			player.reset();
    			finish();
    			
    		}
    			
    	}
    }
    // End VideoMediaController.MediaPlayerControl

}
*/