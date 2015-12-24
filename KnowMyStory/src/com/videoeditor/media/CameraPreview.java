/*
 * Know My Story 
 */
package com.videoeditor.media;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.database.DatabaseHelper;
import com.tabview.TabViewActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private Activity act;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static Uri mCaptureMediaUri;
	static Context ctx ;
	static String outputMediaFile = null;
	MediaRecorder m_recorder;
	DatabaseHelper db;
	String current_screen_status;
	static int camId;
	List<Camera.Size> supportSize = null;
	CustomCameraActivity customcameraactivity;
	int SupportedSizes_width ;
	int SupportedSizes_height ;
	//String tabDetail;
	public CameraPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
		System.out.println("constructor...");
		ctx = context;
		customcameraactivity = new CustomCameraActivity();
		db = new DatabaseHelper(ctx);
		
		current_screen_status = db.getCurrentScreen();
		m_recorder = null;
		m_recorder = new MediaRecorder();
		holder = getHolder();
		act = (Activity) context;
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	
	public Camera camera ;
	private SurfaceHolder holder;

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int format, int height,
			int width) {
		System.out.println("surfaceChanged...");
		
		Camera.Size previewSize = null;
		
			try {
				Camera.Parameters parameters = camera.getParameters();
				parameters.set( "cam_mode", 1 );   
				
				 supportSize = parameters
						.getSupportedPreviewSizes();
			/*
				 if (supportSize != null) {
					previewSize = getOptimalPreviewSize(supportSize, width, height);
					System.out.println("supportedsize: "+"w: "+previewSize.width+" h: "+previewSize.height);
					SupportedSizes_width = previewSize.width;
					SupportedSizes_height = previewSize.height;
					
				}
				*/
				
				
				parameters.setPreviewSize(supportSize.get(0).width,supportSize.get(0).height);
				//parameters.set("orientation", "portrait");
				//parameters.setPreviewFrameRate(30);
				/*parameters.setPreviewFpsRange( 30000, 30000 ); // 30 fps
				if ( parameters.isAutoExposureLockSupported() )
					parameters.setAutoExposureLock( true );*/
				camera.setPreviewDisplay(arg0);
				String i = customcameraactivity.fetchCamId();
				/*if(i!=null){
					if(i.equals("front")){
						
						setCameraDisplayOrientation(act, Camera.CameraInfo.CAMERA_FACING_FRONT, camera);
					}
				}*/
				camera.setParameters(parameters);
				camera.startPreview();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			camera.unlock();
			m_recorder.setPreviewDisplay(arg0.getSurface());
			m_recorder.setCamera(camera);
			m_recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
			m_recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
			//m_recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_720P));
			//m_recorder.setVideoSize(1280, 720);
			//m_recorder.setVideoFrameRate(30);
			m_recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			m_recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
			m_recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
			
			//m_recorder.setMaxDuration(1000);
			m_recorder.setOnInfoListener(oninfoLis);
			//Uncomment if we need fixed video resolution

			/*String i = customcameraactivity.fetchCamId();
			if(i!=null){
				if(i.equals("back")){*/
			m_recorder.setVideoSize(1280, 720);
				/*}else if(i.equals("front")){
					m_recorder.setVideoSize(SupportedSizes_width, SupportedSizes_height);
					
					
				}*/
		//	}
			//m_recorder.setVideoFrameRate(30);
			//m_recorder.setVideoFrameRate(30);
			
			//m_recorder.setAudioSamplingRate(48000);
			
			//Good quality value : 16000000
			m_recorder.setVideoEncodingBitRate(4000000); 
			//Good quality value : 1536000
		//--pree	m_recorder.setAudioEncodingBitRate(384000); //192 old value
			/*m_recorder.setOutputFile("/mnt/sdcard/myfile"
					+ SystemClock.elapsedRealtime() + ".mp4");*/
			String project_path = TabViewActivity.getProjectPath();
			//String project_path= "/storage/sdcard0/Android/data/dogtim.android.videoeditor/files/KnowMyStory/";
			
			//if(project_path!=null)
			//boolean reRecordStatus = CustomCameraActivity.getReRecordStatus();
			//boolean reRecordStatus = customcameraactivity.getReRecordStatus();
			if(project_path!=null){
				if(!(project_path.equals(""))){
			
			int reRecordStatus = db.getRerecordState();
			//if(customcameraactivity.startRecordingStatus())
			outputMediaFile = getOutputMediaFile(MEDIA_TYPE_VIDEO,project_path ,reRecordStatus,customcameraactivity.startRecordingStatus()).toString();
			//}
			//String tabDetail = CustomCameraActivity.getTabDetail();
			current_screen_status = db.getCurrentScreen();
			System.out.println("current_screen_status: "+current_screen_status);
			
			//Insert videopath in DB
       	 	//db.insertVideo(outputMediaFile,current_screen_status,TabViewActivity.getProjectName());
       	 	//db.insertVideo(new Model(outputMediaFile));
			if(current_screen_status!=null){
				 if(current_screen_status.equals("open_cam_1")){
					 if(reRecordStatus == 0)
					db.updateVideo1(outputMediaFile);
				}else if(current_screen_status.equals("open_cam_2")){
					if(reRecordStatus == 0)
					db.updateVideo2(outputMediaFile);
				}else if(current_screen_status.equals("open_cam_3")){
					if(reRecordStatus == 0)
					db.updateVideo3(outputMediaFile);
				}else if(current_screen_status.equals("open_cam_4")){
					if(reRecordStatus == 0)
					db.updateVideo4(outputMediaFile);
				}else if(current_screen_status.equals("open_cam_5")){
					if(reRecordStatus == 0)
					db.updateVideo5(outputMediaFile);
				}
					
			}
			/*else{
       	 		db.updateVideo1(outputMediaFile,TabViewActivity.getProjectName());
			}*/
			
			
			
			m_recorder.setOutputFile(outputMediaFile);
			/*saveToContentValue(getOutputMediaFile(MEDIA_TYPE_VIDEO,project_path)
					.toString());*/
			
			try {
				m_recorder.prepare();
			} catch (IllegalStateException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
				releaseMediaRecorder();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				releaseMediaRecorder();
			}
				}
			}
			//m_recorder.start();
	

	}
	
	/*
	 * To Remove the mirror image while recording from Front camera.
	 * (Currently not working with this code so un-used)
	 */
	public static void setCameraDisplayOrientation(Activity activity,
	         int cameraId, android.hardware.Camera camera) {
	     android.hardware.Camera.CameraInfo info =
	             new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo(cameraId, info);
	     int rotation = activity.getWindowManager().getDefaultDisplay()
	             .getRotation();
	     int degrees = 0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }

	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	    	 System.out.println("inside fronttt");
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  // compensate the mirror
	     } else {  // back-facing
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     camera.setDisplayOrientation(result);
	 }
	 


		private void releaseMediaRecorder(){
	        if (m_recorder != null) {
	        	m_recorder.reset();   // clear recorder configuration
	        	m_recorder.release(); // release the recorder object
	        	m_recorder = null;
	           // mCamera.lock();           // lock camera for later use
	        }
	    }
	

	public static Uri saveToContentValue() {
		
		final ContentValues values = new ContentValues();
       // String videoFilename = DIRECTORY + '/' + getVideoOutputMediaFileTitle() + ".mp4";
        System.out.println("outputMediaFile: "+outputMediaFile);
        values.put(MediaStore.Video.Media.DATA, outputMediaFile);
        //mCaptureMediaUri = ctx.getContentResolver().insert(
        //        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        System.out.println("mCaptureMediaUri: "+mCaptureMediaUri);
		return mCaptureMediaUri;
		
	}

	void stopRecording() {
		System.out.println("stop recording in preview");
	    if (null != m_recorder) {
	    	m_recorder.stop();
	    	m_recorder.reset();
	    	m_recorder.release();
	    	m_recorder = null;
	    }
	}
	/** Create a File for saving an image or video 
	 * @param project_path 
	 * @param reRecord 
	 * @param startRecordingStatus */
	private  File getOutputMediaFile(int type, String project_path, int reRecord, boolean startRecordingStatus) {
		
		String existingVideo = null;
		if(reRecord == 1){
			if(current_screen_status!=null){
				 if(current_screen_status.equals("open_cam_1")){
					 existingVideo = db.isVideo1Present();
				}else if(current_screen_status.equals("open_cam_2")){
					existingVideo = db.isVideo2Present(TabViewActivity.getProjectName());
				}else if(current_screen_status.equals("open_cam_3")){
					existingVideo = db.isVideo3Present(TabViewActivity.getProjectName());
				}else if(current_screen_status.equals("open_cam_4")){
					existingVideo = db.isVideo4Present(TabViewActivity.getProjectName());
				}else if(current_screen_status.equals("open_cam_5")){
					existingVideo = db.isVideo5Present(TabViewActivity.getProjectName());
				}
					
			}
			
			System.out.println("existing video: "+existingVideo);
		}
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.
		System.out.println("getOutputMediafile....");
		System.out.println("project_path...."+project_path);
		File mediaStorageDir = new File(project_path);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				System.out.println("MyCameraApp:  failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		/*
		 * if (type == MEDIA_TYPE_IMAGE){ mediaFile = new
		 * File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp +
		 * ".jpg"); } else
		 */
		String videoPrefix = "VID_";
		if (type == MEDIA_TYPE_VIDEO) {
			
			current_screen_status = db.getCurrentScreen();
			System.out.println("CS in CameraPreview:"+current_screen_status);
			if(current_screen_status!=null){
				if(current_screen_status.equals("open_cam_1")){
					videoPrefix = "VideoClip1";
				}else if(current_screen_status.equals("open_cam_2")){
					videoPrefix = "VideoClip2";
				}else if(current_screen_status.equals("open_cam_3")){
					videoPrefix = "VideoClip3";
				}else if(current_screen_status.equals("open_cam_4")){
					videoPrefix = "VideoClip4";
				}else if(current_screen_status.equals("open_cam_5")){
					videoPrefix = "VideoClip5";
				}else if(current_screen_status.equals("open_cam_6")){
					videoPrefix = "VideoClip6";
				}
			}
			/*mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ videoPrefix + timeStamp + ".mp4");*/
			if(reRecord==1){
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ videoPrefix + "_1.mp4");
				
				
				//db.updateVideo1(videoPrefix);
			}else{
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ videoPrefix + ".mp4");
				
			}
			
			//mediaFile = new File(mediaStorageDir.getPath() + File.separator
			//		+ videoPrefix + ".mp4");
			
			
			/*if(existingVideo!=null){
				//new File(existingVideo).getName();
				deleteOldFile(new File(existingVideo).getName());
				//existingVideo
			}*/
			if(startRecordingStatus){
				File dir = new File(TabViewActivity.getProjectPath()); 
				if (dir.isDirectory()) {
				        String[] children = dir.list();
				        for (int i = 0; i < children.length; i++) {
				        	if(children[i].contains(videoPrefix))
				            new File(dir, children[i]).delete();
				        }
				    }			}
		} else {
			return null;
		}
		System.out.println("mediaFile path: "+mediaFile.getAbsolutePath());
		//db.updateRerecordState(0);
		return mediaFile; 
	}

	private void deleteOldFile(String existingVideo) {
		File dir = new File(TabViewActivity.getProjectPath());
		File file = new File(dir,existingVideo );
		boolean deleted = file.delete();
		System.out.println("deleted: "+deleted);
		
	}

	//@SuppressLint("NewApi")
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {

		System.out.println("surfaceCreated called...");
		camera = openBackFacingCamera();
		if (camera != null) {
			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				camera.release();
				camera = null;
			}
		} else {
			//Toast.makeText(act, "Problem in opening Camera.", Toast.LENGTH_LONG)
				//	.show();
			act.finish();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		System.out.println("Inside surfaceDestroyed...");
		try {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
			
			m_recorder.reset();
			m_recorder.release();
			m_recorder = null;
		
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	MediaRecorder.OnInfoListener oninfoLis = new OnInfoListener() {

		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Log.i("Error =" + what, "Comes"+what);
		}
	};

	public Camera openBackFacingCamera() {
		System.out.println("open back facing camera");
		try {
			releaseCameraAndPreview();
			String i = customcameraactivity.fetchCamId();
			
			int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
			if(i!=null){
				if(i.equals("back")){
					 camId = Camera.CameraInfo.CAMERA_FACING_BACK;
				}else{
					 camId = Camera.CameraInfo.CAMERA_FACING_FRONT;
				}
			}
			return Camera.open(camId);
			
			//return Camera.open(camId);
		} catch (Exception e) {
			System.out.println("can't open camera");
			e.getMessage();
			return null;
		}
	}

	private void releaseCameraAndPreview() {
	    setCamera(null);
	    if (camera != null) {
	    	camera.release();
	    	camera = null;
	    }
	}
	private void setCamera(Camera mCamera) {
		if (mCamera == camera) { return; }
	    
	    stopPreviewAndFreeCamera();
	    
	     camera = mCamera;
	    
	    if (camera != null) {
	        List<Size> localSizes = camera.getParameters().getSupportedPreviewSizes();
	        supportSize = localSizes;
	        requestLayout();
	      
	        try {
	            mCamera.setPreviewDisplay(holder);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	      
	        // Important: Call startPreview() to start updating the preview
	        // surface. Preview must be started before you can take a picture.
	        mCamera.startPreview();
	    }
		
	}
	/**
	 * When this function returns, mCamera will be null.
	 */
	private void stopPreviewAndFreeCamera() {
		 if (camera != null) {
		        // Call stopPreview() to stop updating the preview surface.
		        camera.stopPreview();
		    
		        // Important: Call release() to release the camera for use by other
		        // applications. Applications should release the camera immediately
		        // during onPause() and re-open() it during onResume()).
		        camera.release();
		    
		        camera = null;
		    }
		
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		System.out.println("getOptimalPreviewSize");
		final double ASPECT_TOLERANCE = 0.1;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;
		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		int targetHeight = h;
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}
	private void onPause() {
		System.out.println("onPause in preview");
		if(camera!=null){
			camera.startPreview();
			camera.release();
			
		}
	//holder.removeCallback(this);

	}

	public static String getMediaFilePath() {
		System.out.println("getMediaFilePath");
		String mediafilepath = outputMediaFile ;
		return mediafilepath;
	}

	/*public static void flipCamera(String string) {
		 camId = Camera.CameraInfo.CAMERA_FACING_FRONT;
		
	}*/
	
}
