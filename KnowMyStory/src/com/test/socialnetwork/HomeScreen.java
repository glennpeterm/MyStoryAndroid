/*
 * Know My Story 
 */
package com.test.socialnetwork;

import com.supportclasses.Constants;
import com.tabview.FinishActivity;
import com.tabview.TabViewActivity;
import com.test.socialnetwork.SplashScreen.GetVerses;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.videoeditor.media.VideoPreviewOption;
import com.videoeditor.util.FileUtils;

import io.fabric.sdk.android.Fabric;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.style.EasyEditSpan;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allchannelvideos.ChannelVideos;
import com.allchannelvideos.SelfieVideos;
import com.circlemenu.view.CameraPreview;
import com.circlemenu.view.CircleImageView;
import com.circlemenu.view.CircleLayout;
import com.circlemenu.view.CircleLayout.OnCenterClickListener;
import com.circlemenu.view.CircleLayout.OnItemClickListener;
import com.circlemenu.view.CircleLayout.OnItemSelectedListener;
import com.circlemenu.view.CircleLayout.OnRotationFinishedListener;
import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import com.database.Model;
import com.database.menu.CountryTable;
import com.database.menu.Database;
import com.dialog.TransparentProgressDialog;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.internal.ne;

import net.onehope.mystory.R;
//import com.google.ytdl.MainActivity;
import com.mediacontrols.VideoPlayerActivity;
import com.menuadapter.MenuAdapter;
import com.model.Menu;


/*
 * hareesh :  
 * Added a reference to the class SendAnalytics, for sending analytics on click to 
 * google account.
 * **/


public class HomeScreen  extends Activity implements OnItemSelectedListener,
OnItemClickListener, OnRotationFinishedListener, OnCenterClickListener {


	TextView selectedTextView,bottomtext;
	MenuAdapter adapter;
	CircleLayout circleMenu;
	private Camera mCamera;
	private CameraPreview mPreview;
	RelativeLayout rlViews;
	ArrayList<Menu> lsMenu;
	FrameLayout preview;
	String VIDEO_URL = "video_url";
	Database DbClass;
	DatabaseHelper db ;
	DatabaseCreator dbCreator;
	final String PREFS_NAME = "Home_MyPrefsFile";

	public Typeface abel_ttf;
	TransparentProgressDialog pd;
	private Handler h;
	private Runnable r;
	SharedPreferences settings;
	
	private Dialog custom_dialog;
	TextView tvCoachMarkText;
	Button btnGotIt;
	private ImageView viewagain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE); 

		setContentView(R.layout.activity_homescreen);

		System.out.println("OnCreate Homescreen called..");
		settings = getSharedPreferences(PREFS_NAME, 0);
		abel_ttf=Typeface.createFromAsset(HomeScreen.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		db = new DatabaseHelper(HomeScreen.this);
		DbClass=new Database(HomeScreen.this);
		dbCreator = new DatabaseCreator(this);
		h = new Handler();
		pd = new TransparentProgressDialog(this, R.drawable.spinner,getResources().getString(R.string.dialog_fetching_longdata));
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
				}
			}
		};
		if(DbClass.CountryCount()==0)
		{

			ContentValues cv=new ContentValues();
			String[] arr=getResources().getStringArray(R.array.country_array);
			for(int i=0;i<arr.length;i++)
			{
				cv.put(CountryTable.COUNTRY_NAME, arr[i]);
			}

			DbClass.insert(CountryTable.TABLE_NAME, null, cv);

		}


		/*mCamera = getCameraInstance();
		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);*/
		preview = (FrameLayout) findViewById(R.id.camera_preview);
		//		preview.addView(mPreview);


		viewagain = (ImageView)findViewById(R.id.view_again);
		
		viewagain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(HomeScreen.this,PhotoLaunchActivity.class );
				startActivity(i);
				finish();
			}
		});
		
		
		rlViews=(RelativeLayout)findViewById(R.id.rlViews);
		circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);
		circleMenu.setOnRotationFinishedListener(this);
		circleMenu.setOnCenterClickListener(this);


		selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
		selectedTextView.setTypeface(abel_ttf);
		//uptext = (TextView) findViewById(R.id.main_upper_textView);
		bottomtext = (TextView) findViewById(R.id.main_lower_textView);
		bottomtext.setTypeface(abel_ttf);

		selectedTextView.setText(((CircleImageView) circleMenu
				.getSelectedItem()).getName());

		/*uptext.setText(((CircleImageView) circleMenu
				.getUpperItem()).getName());*/
		bottomtext.setText(((CircleImageView) circleMenu
				.getLowerItem()).getSubName());
		
		rlViews.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MotionEvent ec = event;
				ec.setLocation(event.getY(), event.getX());				
				circleMenu.onTouchEvent(event);
				return true;
			}
		});

			
		
		InitializeCoachMarkDialog();
		GetVerses getVerses = new GetVerses();
		if(Constants.GET_VERSE_START == 0){
			Constants.GET_VERSE_START = 1;
			LoadBibleData();
		}
		/*if(!(getVerses.getStatus() == AsyncTask.Status.RUNNING)){
			System.out.println("getVerses not running..");
			System.out.println("getVerse status: "+getVerses.getStatus());
			LoadBibleData();
		}

		if(getVerses.getStatus() == AsyncTask.Status.PENDING){
			
		}*/
		Log.d("prick","end of the csreen");
	}

	

	private void LoadBibleData() {


		if (settings.getBoolean("my_first_time_verse", true)) 
		{
			//the app is being launched for first time, do something  
			new GetVerses().execute();
			Log.d("Comments", "First time");
			System.out.println("first tym \n");
			//Intent i = new Intent(HomeScreen.this, TutorialVideoLaunch.class);
			//startActivity(i);
			//finish();

			// record the fact that the app has been started at least once
			//settings.edit().putBoolean("my_first_time_verse", false).commit(); 
		}

	}
	public class GetVerses extends AsyncTask<Void, Void, Void> 
	{
		

		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
			if(!(pd.isShowing())){
				System.out.println("PreExecute...pd showing");
			pd.show();
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			}
			//h.postDelayed(r,5000);
		}
		@Override
		protected Void doInBackground(Void... params)
		{

			getVerseListFromJSON();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			h.removeCallbacks(r);
			if (pd.isShowing() ) {
				pd.dismiss();
			}
			showCoachmarkDialog();
			settings.edit().putBoolean("my_first_time_verse", false).commit(); 
			//dbHelp.updateDbComplete();
		}
	}
	private void getVerseListFromJSON()
	{
		try
		{
			String result =loadJSONFromAsset(Constants.BIBLE_VERSE_JSON);
			JSONArray json = new JSONArray(result);
			System.out.println("array is \n"+json);
			System.out.println("length of json is "+json.length());

			// increase heap size if needed
			for(int i=0;i<json.length();i++)
			{   
				JSONObject jsObj= json.getJSONObject(i);
				dbCreator.insertBibleFields(i,jsObj.getInt("book_order"),jsObj.getInt("chapter"),jsObj.getInt("verse"));
				
				//dbCreator.insertTestData(i,jsObj.getInt("book_order"),jsObj.getInt("chapter"),jsObj.getInt("verse"));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	public String loadJSONFromAsset(String file_name)
	{
		String json = null;
		try 
		{
			InputStream is = getAssets().open(file_name);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} 
		catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	
	public void InitializeCoachMarkDialog()
	{
		custom_dialog = new Dialog(HomeScreen.this,R.style.TransparentProgressDialog);
		custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		custom_dialog.setCancelable(false);
		//custom_dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
		//custom_dialog.getWindow().getAttributes().height=LayoutParams.FILL_PARENT;
		custom_dialog.setContentView(R.layout.coachmark_screen);
		custom_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		tvCoachMarkText=(TextView)custom_dialog.findViewById(R.id.tvCoachText);
		tvCoachMarkText.setTypeface(abel_ttf,Typeface.BOLD);
		btnGotIt=(Button)custom_dialog.findViewById(R.id.btn_gotit);
		btnGotIt.setTypeface(abel_ttf);

		btnGotIt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				custom_dialog.dismiss();
			}
		} );
	}
	
	public void showCoachmarkDialog()
	{
		custom_dialog.show();
	}


	@Override
	protected void onResume() {
		/*	mCamera = getCameraInstance();
		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		preview.addView(mPreview);*/
		super.onResume();
		if(pd!=null){
			if(pd.isShowing()){
				
			}
		}
	}


	@Override
	protected void onPause() {
		if(mCamera!=null)
		{
			mCamera.release();
			preview.removeAllViews();

		}
		super.onPause();
	}


	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
		Camera c = null;
		try {
			c = Camera.open(0); // attempt to get a Camera instance
		}
		catch (Exception e){
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}
	@Override
	public void onItemSelected(View view, String name,String upper,String lower) {
		selectedTextView.setText(name);
		//uptext.setText(upper);
		bottomtext.setText(lower);
		switch (view.getId()) {
		case R.id.menu_tell_your_story:
			// Handle facebook selection
			/*Toast.makeText(MainActivity.this, "Boo..Im Fb",2).show();*/
			break;
		case R.id.menu_my_profile:
			/*Toast.makeText(MainActivity.this, "Boo..Im g+",2).show();*/
			break;
		case R.id.menu_settings:
			/*Toast.makeText(MainActivity.this, "Boo..Im LInked",2).show();*/
			break;
		case R.id.menu_watch:
			/*Toast.makeText(MainActivity.this, "Boo..Im MySpace",2).show();*/
			break;
		case R.id.menu_tutorial_video:
			/*Toast.makeText(MainActivity.this, "Boo..Im Twitter",2).show();*/
			break;
		case R.id.menu_mystory:
			/*Toast.makeText(MainActivity.this, "Boo..Im Wordpress",2).show();*/
			break;
		}
	}

	@Override
	public void onItemClick(View view, String name) {
		/*	Toast.makeText(getApplicationContext(),
				getResources().getString(R.string.start_app) + " " + name,
				Toast.LENGTH_SHORT).show();*/

		
		
		switch (view.getId()) {
		case R.id.menu_tell_your_story:{
			
			
			/*
			 * Sending tracker to analytics server
			 * */
			
			
			
			db.updateRerecordState(0);
			db.updateCurrentMenuItem(0);
			int CompletedScreenNumber = db.getCompletedScreens();
			System.out.println("Completed screen Home: "+CompletedScreenNumber);
			if(CompletedScreenNumber == 0){

				com.supportclasses.Constants.ENABLED_TABS_STATE = 0;
				FileUtils.deleteDir(HomeScreen.this);
				com.supportclasses.Constants.SET_CURRENT_TAB = 0;
				
				Intent in=new Intent(HomeScreen.this,TabViewActivity.class);
				startActivity(in);
			}else {
				Intent iFinish = new Intent(HomeScreen.this,FinishActivity.class);
				startActivity(iFinish);
			}
			
			
		}
		break;
		case R.id.menu_my_profile:
			db.updateCurrentMenuItem(2);
			GenerateHashKey();
			/*Intent in1=new Intent(HomeScreen.this,Youtubeupload.class);
			startActivity(in1);*/
			int logoutStatus = db.getLogoutStatus();
			System.out.println("Logout Status Upload page: " + logoutStatus);
			if (logoutStatus == 0) {
				//User is not logged in.
				Intent intent_my_profile = new Intent(HomeScreen.this,
						LoginActivity.class);
				intent_my_profile.putExtra(Constants.PRE_LOGIN_ACTIVITY,
						"profile_screen");
				startActivity(intent_my_profile);
			} else if (logoutStatus == 1) {
				//User is  logged in.
				Intent intent_my_profile = new Intent(HomeScreen.this,
						ProfileActivity.class);
				startActivity(intent_my_profile);
			}
			break;
		case R.id.menu_settings:
			db.updateCurrentMenuItem(1);
			/*Intent in=new Intent(HomeScreen.this,UploadActivity.class);
			startActivity(in);*/
			Intent in=new Intent(HomeScreen.this,Settings_Activity.class);
			startActivity(in);
			/*if(DbClass.loginCount()>0)
			{
				Intent in=new Intent(HomeScreen.this,MyProfileActivity.class);
				in.putExtra("isEdit", true);
				startActivity(in);

			}
			else
			{
				Toast.makeText(HomeScreen.this, "User not signed in!", 2).show();

			}*/

			break;
		case R.id.menu_watch:
			db.updateCurrentMenuItem(4);

			/*Intent intent=new Intent(HomeScreen.this,com.google.ytdl.MainActivity.class);
				startActivity(intent);*/
			Intent intent_watch=new Intent(HomeScreen.this,ChannelVideos.class);
			startActivity(intent_watch);


			break;
		case R.id.menu_tutorial_video:
			db.updateCurrentMenuItem(3);
			/*Intent in4=new Intent(HomeScreen.this,MainActivity.class);

			startActivity(in4);*/
			/*Intent intent_tutorial=new Intent(HomeScreen.this,ExplanatoryVideo_Activity.class);

			startActivity(intent_tutorial);*/

			Intent intent_tutorial = new Intent(HomeScreen.this, VideoPlayerActivity.class);
			intent_tutorial.putExtra(VIDEO_URL, "android.resource://" + getPackageName() + "/" + R.raw.mystory_explanatory);
			startActivity(intent_tutorial);
			break;
		case R.id.menu_mystory:
			db.updateCurrentMenuItem(5);
			/*Intent in5=new Intent(HomeScreen.this,com.main.MainActivity.class);
			startActivity(in5);*/
			/*Intent in5=new Intent(HomeScreen.this,SelfieVideos.class);
			startActivity(in5);
			 */
			int logoutStatus1 = db.getLogoutStatus();
			System.out.println("Logout Status Upload page: " + logoutStatus1);
			if (logoutStatus1 == 0) {
				//User is not logged in.
				Intent intent_my_profile = new Intent(HomeScreen.this,
						LoginActivity.class);
				intent_my_profile.putExtra(Constants.PRE_LOGIN_ACTIVITY,
						"mystory_screen");
				startActivity(intent_my_profile);
			} else if (logoutStatus1 == 1) {
				//User is  logged in.
				Intent in5=new Intent(HomeScreen.this,SelfieVideos.class);
				startActivity(in5);
			}

			break;
		}
	}


	private void GenerateHashKey() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					"net.onehope.mystory", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String sign = Base64
						.encodeToString(md.digest(), Base64.DEFAULT);
				Log.e("MY KEY HASH:", sign);
				System.out.println("Key Hash: "+sign);
				//Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_LONG)
				//.show();
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}

	}


	@Override
	public void onCenterClick() {
		//Toast.makeText(getApplicationContext(), R.string.center_click,
				//Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRotationFinished(View view, String name,String upper,String lower) {
		System.out.println("ACTIVITY "+name);
		/*		Animation scaleup=new ScaleAnimation(view.getX(), view.getY(), view.getX()+5,  view.getY()+5);
		view.startAnimation(scaleup);*/
		//		scaleView(view, 0f, 2f);
		/*Animator anim=AnimatorInflater.loadAnimator(MainActivity.this, R.anim.scale_anim);
		anim.setTarget(view);
		anim.start();*/

		/*Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2,
				view.getHeight() / 2);
		animation.setDuration(250);
		view.startAnimation(animation);		
		 */



	}

	public void scaleView(View v, float startScale, float endScale) {
		Animation anim = new ScaleAnimation(
				1f, 2f, // Start and end values for the X axis scaling
				startScale, endScale, // Start and end values for the Y axis scaling
				Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
				Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
		anim.setFillAfter(true); // Needed to keep the result of the animation
		v.startAnimation(anim);
	}
	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}
}
