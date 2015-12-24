
package com.test.socialnetwork;


import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import com.database.Model;
import net.onehope.mystory.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.supportclasses.Constants;
/*
 * SplashScreen class launches the very first activity of the app which 
 * is the ChargeItPro spalshscreen
 */
public class SplashScreen extends Activity{
	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
	//private static final String TWITTER_KEY = "N2JPKkFzRmDgl7aXqpsq6CwTA";
	//private static final String TWITTER_SECRET = "413uDxafejXQY5PC3zvG1jTuvKx273YHILkjCQ8HZh0Z13vdkl";

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2000;
	private SharedPreferences sharedPreferences;

	DatabaseCreator db;
	DatabaseHelper dbHelp;
	final String PREFS_NAME = "MyPrefsFile";
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		//Fabric.with(this, new Twitter(authConfig));
		//TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		//Fabric.with(this, new Twitter(authConfig));
		setContentView(R.layout.splash_screen);

		//hides the action bar
		//ActionBar actionbar = getActionBar();
		//actionbar.hide();
		db = new DatabaseCreator(this);
		dbHelp= new DatabaseHelper(this);
		dbHelp.insertStoryName(new Model(""));

		//int check_db=dbHelp.checkDbComplete();
		/*if(check_db==0)
		{
			new GetVerses().execute();
		}*/

		/*	
		int checkverse_db=dbHelp.checkVerseDbComplete();
		if(checkverse_db==0)
		{
		new GetVersesText().execute();
		}*/

		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		new Handler().postDelayed(new Runnable() {
			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() 
			{
				/*int launch=dbHelp.getLaunch();
				System.out.println(" launch value is "+launch);
				if(launch == 0)
				{
					Intent i = new Intent(SplashScreen.this, TutorialVideoLaunch.class);
					startActivity(i);
					finish();
				}
				else
				{
					Intent i = new Intent(SplashScreen.this, HomeScreen.class);
					startActivity(i);
					// close this activity
					finish();
				}*/
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

				if (settings.getBoolean("my_first_time", true)) 
				{
					//the app is being launched for first time, do something  
					//new GetVerses().execute();
					Log.d("Comments", "First time");
					System.out.println("first tym \n");
					Intent i = new Intent(SplashScreen.this, TutorialVideoLaunch.class);
					startActivity(i);
					finish();

					// record the fact that the app has been started at least once
					settings.edit().putBoolean("my_first_time", false).commit(); 
				}
				else
				{
					System.out.println("second tym \n");
					Intent i = new Intent(SplashScreen.this, HomeScreen.class);
					Log.d("prick","splash");
					startActivity(i);
					// close this activity
					finish();
				}
			}
		}, SPLASH_TIME_OUT);
	}

	/* (non-Javadoc)
	 * Called when app destroys
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(sharedPreferences!=null){
			//clears the old data from shared preference when activity goes in background
			sharedPreferences.edit().clear().commit();
		}
	}

	public class GetVerses extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
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
				db.insertBibleFields(i,jsObj.getInt("book_order"),jsObj.getInt("chapter"),jsObj.getInt("verse"));
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

	public class GetVersesText extends AsyncTask<Void, Void, Void> 
	{
		// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
		//private static final String TWITTER_KEY = "HWjfKOH4PszvRm9Ij0AzJlqZz";
		//private static final String TWITTER_SECRET = "GNtQ1PDZnTIYmT1CLYHcmuwqWY90IMJd5BAJgTwkOQ6hieAmfj";
		

		@Override
		protected Void doInBackground(Void... params)
		{

			getVerseTextFromJSON();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			dbHelp.updateVerseDbComplete();
		}

	}

	private void getVerseTextFromJSON()
	{
		try
		{
			String result =loadJSONFromAsset(Constants.BIBLE_OFFLINE_JSON);
			JSONArray json = new JSONArray(result);
			System.out.println("length of json is "+json.length());

			// increase heap size if needed
			for(int i=0;i<json.length();i++)
			{   
				JSONObject jsObj= json.getJSONObject(i);
				db.insertVerseOfflineFields(i,jsObj.getString("book_name")+" "+jsObj.getString("chapter")+":"+jsObj.getString("verse"),jsObj.getString("verse_text"));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
}




















/*package com.test.socialnetwork;


import com.database.DatabaseHelper;
import net.onehope.mystory.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

 * SplashScreen class launches the very first activity of the app which 
 * is the ChargeItPro spalshscreen
 
public class SplashScreen extends Activity{
	  // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
	private SharedPreferences sharedPreferences;
	DatabaseHelper dbHelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        dbHelp= new DatabaseHelper(SplashScreen.this);
        //hides the action bar
        ActionBar actionbar = getActionBar();
		actionbar.hide();
	
		

		this.getWindow().setSoftInputMode(
   				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        new Handler().postDelayed(new Runnable() {
            
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             
 
            @Override
            public void run() {
            	String launch=dbHelp.getLaunch();
				System.out.println(" launch value is "+launch);
				if(launch == null)
				{
					
					Intent i = new Intent(SplashScreen.this, TutorialVideoLaunch.class);
					startActivity(i);
					finish();
				}
				else
				{
					Intent i = new Intent(SplashScreen.this, HomeScreen.class);
					startActivity(i);
					// close this activity
					finish();
				}
			}
		}, SPLASH_TIME_OUT);

         
    }
 
	 (non-Javadoc)
	 * Called when app destroys
	 
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(sharedPreferences!=null){
			//clears the old data from shared preference when activity goes in background
			sharedPreferences.edit().clear().commit();
		}
	}
}
*/