/*
Copyright 2014 Yahoo Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.trim;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.onehope.mystory.R;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.TimeToSampleBox;
import com.database.DatabaseHelper;
import com.dialog.DialogActivity;
import com.dialog.TransparentProgressDialog;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;
import com.supportclasses.Constants;
import com.tabview.TabViewActivity;
import com.videoeditor.util.StringUtils;

public class TrimActivity extends Activity implements OnClickListener {
	static VideoView myVideoView;
	MediaController myMediaController;
	int maxVideoSize;
	static RangeSeekBar<Integer> rangeSeekBar;
	Button btn;
	MediaMetadataRetriever retriver;
	
	
	// static String viewSource = Environment.getExternalStorageDirectory() +
	// "/media/smoking-dog.3g2";
	static String viewSource;
	int deviceHeight;
	int deviceWidth;
	Button btn_trim;
	Button btn_cancel;
	DatabaseHelper db;
	MediaPlayer mp;
	static TextView textview_minvalue;
	static TextView textview_maxvalue;
	TransparentProgressDialog pd;
	private Handler h;
	private Runnable r;
	
	public Typeface abel_ttf;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.trim);
		System.out.println("onCreate called...");

		h=new Handler();
		db = new DatabaseHelper(this);
		
		initializeObjects();
		setFontStyle();
		setOnClickListners();
		
		/*h = new Handler();
		pd = new TransparentProgressDialog(this, R.drawable.spinner);
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		};*/
		
		viewSource = getIntent().getStringExtra("VIDEO1_PATH");
		System.out.println("viewsource: "+viewSource);
		System.out.println("viewsource: "+new File(viewSource).getAbsolutePath());
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		deviceHeight = displaymetrics.heightPixels;
		deviceWidth = displaymetrics.widthPixels;
		System.out.println("device height: " + deviceHeight);
		System.out.println("device width: " + deviceWidth);

		// mediaMetadataRetriever = new MediaMetadataRetriever();
		// mediaMetadataRetriever.setDataSource(viewSource);

		
		if (viewSource != null) {
			myVideoView.setVideoURI(Uri.parse(viewSource));
			myMediaController = new MediaController(this);
			// myVideoView.setMediaController(myMediaController);

			myVideoView.setOnCompletionListener(myVideoViewCompletionListener);
			myVideoView.setOnPreparedListener(MyVideoViewPreparedListener);
			myVideoView.setOnErrorListener(myVideoViewErrorListener);

			myVideoView.requestFocus();

			// myVideoView.seekTo(6000);
			// myVideoView.start();

			getVideoDuration();
		}

		// Setup the new range seek bar
		rangeSeekBar = new RangeSeekBar<Integer>(this);
		// Set the range
		rangeSeekBar.setRangeValues(0, maxVideoSize);
		rangeSeekBar.setSelectedMinValue(0);
		rangeSeekBar.setSelectedMaxValue(maxVideoSize);
		//rangeSeekBar.setOnRangeSeekBarChangeListener(this);
		// rangeSeekBar.getSelectedMinValue();

		// Add to layout
		LinearLayout layout = (LinearLayout) findViewById(R.id.seekbar_placeholder);
		layout.addView(rangeSeekBar);
		
		
		setTimervalue();
		getVideoFrames();
		
	}
	private void getVideoFrames() {
		
		new getFramesAsync().execute();
	}
	
	class getFramesAsync  extends  AsyncTask<Void, Void,ArrayList<Bitmap>>{

		@Override
		protected void onPreExecute() {
			pd = new TransparentProgressDialog(TrimActivity.this, R.drawable.spinner,getResources().getString(R.string.dialog_loading));
			pd.setCancelable(false);
			pd.show();
			super.onPreExecute();
		}
		
		@Override
		protected ArrayList<Bitmap> doInBackground(Void... params) {

		
			
			
			// int imageFramesNumber = PixelUtil.pxToDp(this, deviceWidth) / 100;
			int imageFramesNumber = deviceWidth / 100;
			int imageTimeInterval = (maxVideoSize*1000000) / imageFramesNumber;
			long timeIntervalValue = Long.parseLong(String.valueOf(imageTimeInterval));
			long looper = 1;
			System.out.println("image number: " + imageFramesNumber);
			System.out.println("imageTimeInterval: " + imageTimeInterval+"+"+maxVideoSize+"+"+imageTimeInterval);
			//for (int i = 0; i < imageFramesNumber; i++) {
			ArrayList<Bitmap> imageframe = new ArrayList<Bitmap>();
			
			retriver = new MediaMetadataRetriever();
			try {
				if(viewSource!=null)
				retriver.setDataSource(viewSource);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			for (int i = 0; i < imageFramesNumber; i++) {
			
			
				System.out.println("looper: "+looper);
			
				
			imageframe.add(retriver.getFrameAtTime(looper,MediaMetadataRetriever.OPTION_CLOSEST_SYNC));
				
				 looper +=timeIntervalValue;
					
			}
				
			return imageframe;
			
		}
		
		
		@Override
		protected void onPostExecute(ArrayList<Bitmap> result) {
		if(pd.isShowing()){
			pd.dismiss();
		}
		
		LinearLayout linearlayout = (LinearLayout) findViewById(R.id.imageview_container_layout);
			
		for(int i=0;i<result.size();i++){
			ImageView image = new ImageView(TrimActivity.this);

			int thumbnail_width = PixelUtil.pxToDp(TrimActivity.this, 200);
			RelativeLayout.LayoutParams vp = new RelativeLayout.LayoutParams(
					thumbnail_width, LayoutParams.MATCH_PARENT);
			vp.setMargins(30, 0, 30, 0);
			
			image.setScaleType(ScaleType.FIT_XY);
			image.setLayoutParams(vp);
	
			image.setImageBitmap(result.get(i));
			 linearlayout.addView(image);
		}
		
			
			super.onPostExecute(result);
		}
		
	}
		
	private void setTimervalue() {
		textview_minvalue.setText(convertSecondsToHMmSs(Long.parseLong(String.valueOf(rangeSeekBar.getSelectedMinValue()))));
		textview_maxvalue.setText(convertSecondsToHMmSs(Long.parseLong(String.valueOf(rangeSeekBar.getSelectedMaxValue()))));
	}
	private void setOnClickListners() {
		btn.setOnClickListener(this);
		btn_trim.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}
	private void initializeObjects() {
		btn = (Button) findViewById(R.id.play);
		btn_trim = (Button) findViewById(R.id.btn_videotrim);
		btn_cancel = (Button) findViewById(R.id.btn_trim_cancel);
		textview_minvalue = (TextView) findViewById(R.id.textview_minValue);
		textview_maxvalue = (TextView) findViewById(R.id.textview_maxValue);
		myVideoView = (VideoView) findViewById(R.id.videoview);
	}
	
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(TrimActivity.this.getAssets(), "fonts/Abel-Regular.ttf");
		btn_cancel.setTypeface(abel_ttf);
		btn_trim.setTypeface(abel_ttf);
		textview_minvalue.setTypeface(abel_ttf);
		textview_maxvalue.setTypeface(abel_ttf);
	}
	
	@Override
	protected void onStart() {
		
		super.onStart();
		
		//generateImageFrames();
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		/*pd.show();
		pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		h.postDelayed(r,5000);
		
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
	        @Override
	        public void run() {
	        	generateImageFrames();
	        	h.removeCallbacks(r);
				if (pd.isShowing() ) {
					pd.dismiss();
				}
	        }
	    }, 1000);*/
		
	}

@Override
public boolean onTouchEvent(MotionEvent event) {
	
	if (myVideoView.isPlaying()) {
		myVideoView.pause();
		btn.setVisibility(View.VISIBLE);
	}
	return super.onTouchEvent(event);
	
	
}

	private void getVideoDuration() {
		if (viewSource != null) {
			mp = MediaPlayer.create(TrimActivity.this, Uri.parse(viewSource));
			if (mp != null) {
				int duration = mp.getDuration();

				//System.out.println("duration: " + duration);
				maxVideoSize = duration / 1000;
				mp.reset();
				mp.release();
			}
		}

	}

	MediaPlayer.OnCompletionListener myVideoViewCompletionListener = new MediaPlayer.OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer arg0) {
			//Toast.makeText(TrimActivity.this, "End of Video", Toast.LENGTH_LONG)
			//		.show();
		}
	};

	MediaPlayer.OnPreparedListener MyVideoViewPreparedListener = new MediaPlayer.OnPreparedListener() {

		@Override
		public void onPrepared(MediaPlayer mp) {

			long duration = myVideoView.getDuration(); // in millisecond
			/*Toast.makeText(TrimActivity.this,
					"Duration: " + duration + " (ms)", Toast.LENGTH_LONG)
					.show();*/

		}
	};

	MediaPlayer.OnErrorListener myVideoViewErrorListener = new MediaPlayer.OnErrorListener() {

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {

			System.out.println("Trim Error!!!");
			//Toast.makeText(TrimActivity.this, "Error!!!", Toast.LENGTH_LONG)
					//.show();
			return true;
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			if (myVideoView.isPlaying()) {
				myVideoView.pause();
				btn.setVisibility(View.VISIBLE);
			} else {
				btn.setVisibility(View.INVISIBLE);
				//btn.setText("pause");
				int minValue = rangeSeekBar.getSelectedMinValue();
				int maxValue = rangeSeekBar.getSelectedMaxValue();
				System.out.println("play max : "+(maxValue-minValue));
				
				long endValue = Long.parseLong(String.valueOf(maxValue - minValue));
				System.out.println("play max long : "+endValue);
				myVideoView.seekTo(minValue);
				myVideoView.start();
				/* if(myVideoView.isPlaying()){
					if(myVideoView.getCurrentPosition() == maxValue ){
						System.out.println("cp == maxvalue");
						myVideoView.pause();
						btn.setText("PLAY");
					}
				 }*/
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					public void run() {
						myVideoView.pause();
						btn.setVisibility(View.VISIBLE);
					}
				}, endValue);

			}
			break;
		case R.id.btn_videotrim:
			
			int minValue = rangeSeekBar.getSelectedMinValue() * (150 / 10);
			System.out.println("seek..minValue: "+rangeSeekBar.getSelectedMinValue());
			long startValue = Long.parseLong(String.valueOf(minValue / 1000));
			int maxValue = rangeSeekBar.getSelectedMaxValue() * (150 / 10);
			System.out.println("seek..maxValue: "+rangeSeekBar.getSelectedMaxValue());
			long endValue = Long.parseLong(String.valueOf(maxValue / 1000));
			System.out.println("trim_min1: " + minValue);
			System.out.println("trim_max1: " + maxValue);
			int diff = maxValue - minValue;

			if (diff > 1000) {
				db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
				startTrim();
			} else {
				Intent intent = new Intent(TrimActivity.this,
						DialogActivity.class);
				intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "trim_activity");
				startActivity(intent);
			}
			
			/*int minValue = rangeSeekBar.getSelectedMinValue();
			int maxValue = rangeSeekBar.getSelectedMaxValue();
			
			videoPlayerState.setStop(maxValue);*/
			//refreshDetailView();
			
			
			//TrimVideoFunction(minValue,maxValue);
	
			break;
		case R.id.btn_trim_cancel:
			finish();

			break;
		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	private void startTrim() {

		pd.show();
		pd.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		h.postDelayed(r, 5000);

		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
			@Override
			public void run() {
				//int minValue = rangeSeekBar.getSelectedMinValue() * (150 / 10);
				int minValue = rangeSeekBar.getSelectedMinValue();
				long startValue = Long.parseLong(String
						.valueOf(minValue / 1000));
				//int maxValue = rangeSeekBar.getSelectedMaxValue() * (150 / 10);
				int maxValue = rangeSeekBar.getSelectedMaxValue();
				long endValue = Long.parseLong(String.valueOf(maxValue / 1000));
				System.out.println("trim_min2: " + minValue);
				System.out.println("trim_max2: " + maxValue);
				trimVideo(startValue, endValue);
				
				h.removeCallbacks(r);
				if (pd.isShowing()) {
					pd.dismiss();
					finish();
				}
			}
		}, 1000);

	}
	/*private void trimVideo(long startValue, long endValue) {
		try {
			String filePath = viewSource;
			Movie originalMovie = MovieCreator.build(filePath);
			
			// List<Track> tracks = originalMovie.getTracks();
			// originalMovie.setTracks(new LinkedList<Track>());
		        
			System.out.println("timescale: "+originalMovie.getTimescale());

			Track track1 = originalMovie.getTracks().get(0); //video
			Track track2 = originalMovie.getTracks().get(1); //sound
			System.out.println("track1: "+originalMovie.getTracks().get(0));
			System.out.println("track2: "+originalMovie.getTracks().get(1));
			Movie movie = new Movie();
			//movie.addTrack(new AppendTrack(new CroppedTrack(track, 0, 150))); //150 = 9 seconds(half of 300)
			//Track croopedVideo = new CroppedTrack(track1, startValue,endValue);
			//Track croopedAudio = new CroppedTrack(track2, startValue,endValue);
			
			
			//movie.addTrack(new AppendTrack(croopedVideo));
			//movie.addTrack(new AppendTrack(croopedAudio));
			
			//movie.addTrack(new CroppedTrack(track1, startValue,endValue)); 
			//movie.addTrack(new CroppedTrack(track2, startValue,endValue)); 
			
			movie.addTrack(new CroppedTrack(track1, 100,400)); 
			movie.addTrack(new CroppedTrack(track2, 200,500)); 
			
			Container out = new DefaultMp4Builder().build(movie);
			
			String outputFilePath = Environment.getExternalStorageDirectory() + "/media/output_crop.mp4";
			
			//String outputFilePath = getFilesDir()+"/KnowMyStory/"+TabViewActivity.getProjectName()+"/preeti.mp4";
			System.out.println("outputFilePath: "+outputFilePath);
			
			File mediaStorageDir = new File(TabViewActivity.getProjectPath());
			String existing_file = new File(viewSource).getName();
			System.out.println("existing_filename"+existing_file);
			String prefix = null;
			if(existing_file!=null){
				if(existing_file.contains("VideoClip1")){
					prefix = "VideoClip1_";
				}else if(existing_file.contains("VideoClip2")){
					prefix = "VideoClip2_";
				}else if(existing_file.contains("VideoClip3")){
					prefix = "VideoClip3_";
				}else if(existing_file.contains("VideoClip4")){
					prefix = "VideoClip4_";
				}else if(existing_file.contains("VideoClip5")){
					prefix = "VideoClip5_";
				}else if(existing_file.contains("VideoClip6")){
					prefix = "VideoClip6_";
				}
			}
			File mediaFile = new File(mediaStorageDir.getPath() + File.separator+prefix+StringUtils.randomStringOfNumbers(6)+".mp4");
			
			FileOutputStream fos = new FileOutputStream(mediaFile,false);
			out.writeContainer(fos.getChannel());
			fos.close();
			
			deleteOldFile(existing_file);
			updateDB(existing_file,mediaFile.getAbsolutePath());
			
		
			//copyFile(new File(outputFilePath));
			final File dir = new File(this.getApplicationContext().getFilesDir() +"/KnowMyStory/"+TabViewActivity.getProjectName());
			if(!(dir.exists()))
			dir.mkdirs(); //create folders where write files
			final File file = new File(dir, "/"+"preeti_cropped");
			FileOutputStream fos = new FileOutputStream(file);
			out.writeContainer(fos.getChannel());
			fos.close();
			//File mediaFile = new File(mediaStorageDir.getPath() + File.separator
			//		+ videoPrefix + timeStamp + ".mp4");
			
		} catch (Exception e) {
			e.printStackTrace();
			//return false;
		}
		
	}*/
private void trimVideo(long startValue, long endValue) {
		try {
			String filePath = viewSource;
			Movie originalMovie = MovieCreator.build(filePath);
			
			 List<Track> tracks = originalMovie.getTracks();
			 originalMovie.setTracks(new LinkedList<Track>());
		        // remove all tracks we will create new tracks from the old

		      //  double startTime = 0;
		       // double endTime = (double) getDuration(tracks.get(0)) / tracks.get(0).getTrackMetaData().getTimescale();
			 
		      //  double startTime = 2;
		      //  double endTime = 8;
			 
			 double startTime = startValue;
			 double endTime = endValue;
		        boolean timeCorrected = false;

		        // Here we try to find a track that has sync samples. Since we can only start decoding
		        // at such a sample we SHOULD make sure that the start of the new fragment is exactly
		        // such a frame
		        for (Track track : tracks) {
		            if (track.getSyncSamples() != null && track.getSyncSamples().length > 0) {
		                if (timeCorrected) {
		                    // This exception here could be a false positive in case we have multiple tracks
		                    // with sync samples at exactly the same positions. E.g. a single movie containing
		                    // multiple qualities of the same video (Microsoft Smooth Streaming file)

		                    throw new RuntimeException("The startTime has already been corrected by another track with SyncSample. Not Supported.");
		                }
		                //startTime = correctTimeToSyncSample(track, startTime, false);
		                //endTime = correctTimeToSyncSample(track, endTime, true);
		                System.out.println("starttime: "+startTime);
		                System.out.println("endTime: "+endTime);
		                timeCorrected = true;
		            }
		        }

		        for (Track track : tracks) {
		            long currentSample = 0;
		            double currentTime = 0;
		            long startSample = -1;
		            long endSample = -1;

		            for (int i = 0; i < track.getDecodingTimeEntries().size(); i++) {
		                TimeToSampleBox.Entry entry = track.getDecodingTimeEntries().get(i);
		                for (int j = 0; j < entry.getCount(); j++) {
		                    // entry.getDelta() is the amount of time the current sample covers.

		                    if (currentTime <= startTime) {
		                        // current sample is still before the new starttime
		                        startSample = currentSample;
		                        System.out.println("startSample: "+startSample+"currentTime: "+currentTime);
		                        
		                    }
		                    if (currentTime <= endTime) {
		                        // current sample is after the new start time and still before the new endtime
		                        endSample = currentSample;
		                        System.out.println("endSample: "+endSample+"currentTime: "+currentTime);
		                    } else {
		                        // current sample is after the end of the cropped video
		                        break;
		                    }
		                    currentTime += (double) entry.getDelta() / (double) track.getTrackMetaData().getTimescale();
		                    System.out.println("currentTime: "+currentTime);
		                    currentSample++;
		                    System.out.println("currentSample: "+currentSample);
		                }
		            }
		            System.out.println("sample_start..."+startSample+"sample_end.."+endSample);
		            originalMovie.addTrack(new CroppedTrack(track, startSample, endSample));
		        }

				Container out = new DefaultMp4Builder().build(originalMovie);
				
				String outputFilePath = Environment.getExternalStorageDirectory() + "/media/output_crop.mp4";
				
				//String outputFilePath = getFilesDir()+"/KnowMyStory/"+TabViewActivity.getProjectName()+"/preeti.mp4";
				System.out.println("outputFilePath: "+outputFilePath);
				
				File mediaStorageDir = new File(TabViewActivity.getProjectPath());
				String existing_file = new File(viewSource).getName();
				System.out.println("existing_filename"+existing_file);
				String prefix = null;
				if(existing_file!=null){
					if(existing_file.contains("VideoClip1")){
						prefix = "VideoClip1_";
					}else if(existing_file.contains("VideoClip2")){
						prefix = "VideoClip2_";
					}else if(existing_file.contains("VideoClip3")){
						prefix = "VideoClip3_";
					}else if(existing_file.contains("VideoClip4")){
						prefix = "VideoClip4_";
					}else if(existing_file.contains("VideoClip5")){
						prefix = "VideoClip5_";
					}else if(existing_file.contains("VideoClip6")){
						prefix = "VideoClip6_";
					}
				}
				File mediaFile = new File(mediaStorageDir.getPath() + File.separator+prefix+StringUtils.randomStringOfNumbers(6)+".mp4");
				
				FileOutputStream fos = new FileOutputStream(mediaFile,false);
				out.writeContainer(fos.getChannel());
				fos.close();
				
				deleteOldFile(existing_file);
				updateDB(existing_file,mediaFile.getAbsolutePath());
			
			} catch (Exception e) {
				e.printStackTrace();
				//return false;
			}  
	}
	 protected static long getDuration(Track track) {
		
	        long duration = 0;
	        for (TimeToSampleBox.Entry entry : track.getDecodingTimeEntries()) {
	            duration += entry.getCount() * entry.getDelta();
	        }
	        return duration;
	    }
	 private static double correctTimeToSyncSample(Track track, double cutHere, boolean next) {
	        double[] timeOfSyncSamples = new double[track.getSyncSamples().length];
	        long currentSample = 0;
	        double currentTime = 0;
	        for (int i = 0; i < track.getDecodingTimeEntries().size(); i++) {
	            TimeToSampleBox.Entry entry = track.getDecodingTimeEntries().get(i);
	            for (int j = 0; j < entry.getCount(); j++) {
	                if (Arrays.binarySearch(track.getSyncSamples(), currentSample + 1) >= 0) {
	                    // samples always start with 1 but we start with zero therefore +1
	                    timeOfSyncSamples[Arrays.binarySearch(track.getSyncSamples(), currentSample + 1)] = currentTime;
	                }
	                currentTime += (double) entry.getDelta() / (double) track.getTrackMetaData().getTimescale();
	                currentSample++;
	            }
	        }
	        double previous = 0;
	        for (double timeOfSyncSample : timeOfSyncSamples) {
	            if (timeOfSyncSample > cutHere) {
	                if (next) {
	                    return timeOfSyncSample;
	                } else {
	                    return previous;
	                }
	            }
	            previous = timeOfSyncSample;
	        }
	        return timeOfSyncSamples[timeOfSyncSamples.length - 1];
	    }
	private void updateDB(String existing_file, String outputMediaFile) {
		if(existing_file!=null){
			if(existing_file.contains("VideoClip1")){
				db.updateVideo1(outputMediaFile);
			}else if(existing_file.contains("VideoClip2")){
				db.updateVideo2(outputMediaFile);
			}else if(existing_file.contains("VideoClip3")){
				db.updateVideo3(outputMediaFile);
			}else if(existing_file.contains("VideoClip4")){
				db.updateVideo4(outputMediaFile);
			}else if(existing_file.contains("VideoClip5")){
				db.updateVideo5(outputMediaFile);
			}
				
		}
		
	}

	private void deleteOldFile(String existing_file) {
		File dir = new File(TabViewActivity.getProjectPath());
		File file = new File(dir,existing_file );
		boolean deleted = file.delete();
		System.out.println("deleted: "+deleted);
		/*File dir = getFilesDir();
		File file = new File(dir, "KnowMyStory/"+TabViewActivity.getProjectName()+"/"+existing_file);
		file.delete();*/
		
	}

	public  boolean copyFile(File src) throws IOException{
		File dst = new File(this.getApplicationContext().getFilesDir()+"/KnowMyStory/"+TabViewActivity.getProjectName()+"/"+new File(viewSource).getName());
		if(src.getAbsolutePath().toString().equals(dst.getAbsolutePath().toString())){

            return true;

        }else{
            InputStream is=new FileInputStream(src);
            OutputStream os=new FileOutputStream(dst);
            byte[] buff=new byte[1024];
            int len;
            while((len=is.read(buff))>0){
                os.write(buff,0,len);
            }
            is.close();
            os.close();
        }
        return true;
	/*{
		File dst = new File(this.getApplicationContext().getFilesDir()+"/KnowMyStory/"+TabViewActivity.getProjectName()+"/"+new File(viewSource).getName());
	    FileChannel inChannel = new FileInputStream(src).getChannel();
	    FileChannel outChannel = new FileOutputStream(dst).getChannel();
	    try
	    {
	        inChannel.transferTo(0, inChannel.size(), outChannel);
	    }
	    finally
	    {
	        if (inChannel != null)
	            inChannel.close();
	        if (outChannel != null)
	            outChannel.close();
	    }*/
	}

	public  void setCurrentVideoFrame(String thumbText) {
		//int minValue = rangeSeekBar.getSelectedMinValue();
		//int maxValue = rangeSeekBar.getSelectedMaxValue();
		myVideoView.seekTo(Integer.parseInt(thumbText));
		
		//myVideoView.seekTo(Integer.parseInt(max));
		/*
		 * Bitmap thumb = ThumbnailUtils.createVideoThumbnail(viewSource,
		 * MediaStore.Images.Thumbnails.MINI_KIND); BitmapDrawable
		 * bitmapDrawable = new BitmapDrawable(thumb);
		 * myVideoView.setBackgroundDrawable(bitmapDrawable);
		 */
		System.out.println("seek bar moved in DemoActivity...");

	}
	public void setMinTimer(String min) {
		//convertSecondsToHMmSs(Long.parseLong(String.valueOf(min)));
		
		textview_minvalue.setText(convertSecondsToHMmSs(Long.parseLong(String.valueOf(min))));
		//textview_maxvalue.setText(rangeSeekBar.getSelectedMaxValue().toString());
	}
	private String convertSecondsToHMmSs(long min) {
		
	    int m = (int) (min / (1000 * 60));
	    int s = (int) ((min / 1000) % 60);
	    int ms = (int) ((min / 10) % 100);
	    return String.format("%d:%02d:%02d", m, s, ms);
	}
	public void setMaxTimer(String max) {
		textview_maxvalue.setText(convertSecondsToHMmSs(Long.parseLong(String.valueOf(max))));
		
	}
	
	/*@Override
	public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
			Integer minValue, Integer maxValue) {
		System.out.println("seek_min: "+minValue);
		System.out.println("seek_max: "+maxValue);
		//myVideoView.seekTo(minValue);
		myVideoView.start();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				myVideoView.pause();
				btn.setText("PLAY");
			}
		}, 100);
		// myVideoView.pause();
		
		 * Bitmap thumb = ThumbnailUtils.createVideoThumbnail(viewSource,
		 * MediaStore.Images.Thumbnails.MINI_KIND);
		 
		
		 * BitmapDrawable bitmapDrawable = new
		 * BitmapDrawable(fmmr.getFrameAtTime(minValue*1000,
		 * FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC));
		 * myVideoView.setBackgroundDrawable(bitmapDrawable);
		 

		// image.setImageBitmap(fmmr.getFrameAtTime(looper,
		// FFmpegMediaMetadataRetriever.OPTION_CLOSEST_SYNC));

		System.out.println("onRangeSeekBarValuesChanged: " + minValue + ","
				+ maxValue);

	}*/

}
