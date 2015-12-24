package com.allchannelvideos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.menu.Database;
import com.dialog.ErrorDialog;
import com.dialog.TransparentProgressDialog;
import net.onehope.mystory.R;
import com.model.UserDetail;
import com.supportclasses.Constants;
import com.supportclasses.JsonParser;
import com.test.socialnetwork.HomeScreen;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * SelfieVideos class loads the users personal videos.
 */
public class SelfieVideos extends Activity implements OnClickListener
{
	// Declare the variables and the objects used
	WebView wbChannel;
	Button btnHome;
	String url;
	int status_code;
	String URL = Constants.URL_BASE + Constants.URL_SELFIEVIDEOS;

	TransparentProgressDialog pd;
	private Runnable r;
	private Handler h;
	Database DbClass;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel);
		initializeComponents();
		initializeObjects();
		addOnClickListner();
		CallWebViewAsync();
	}

	/**
	 * Function to set up listeners for the buttons
	 */
	private void addOnClickListner()
	{
		btnHome.setOnClickListener(this);
	}

	/**
	 * Function to initialize the components
	 */
	private void initializeComponents() 
	{
		DbClass = new Database(SelfieVideos.this);
		wbChannel = (WebView) findViewById(R.id.webview);
		btnHome=(Button)findViewById(R.id.btn_home);
	}

	/**
	 * Function to initialize the objects
	 */
	private void initializeObjects() 
	{
		h = new Handler();
		pd = new TransparentProgressDialog(this, R.drawable.spinner,getResources().getString(R.string.dialog_loading));
		r =new Runnable()
		{
			@Override
			public void run() 
			{
				if (pd.isShowing())
				{
					pd.dismiss();
				}
			}
		};
	}

	/**
	 * Calling method to parse Selfie list API
	 */
	private void CallWebViewAsync() 
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) 
		{
			// Network connection is available
			// calling method to parse Channel list API
			// Calling async task to get json
			new GetChannelUrl().execute();
		}
		else
		{
			// Network connection is not available
			errorAlertDialog(getResources().getString(R.string.network_conn_error),"selfie_video");
		}
	}

	/**
	 * Error dialog shown if network fails
	 */
	private void errorAlertDialog(String message,String actionmsg)
	{
		DialogFragment newFragment = ErrorDialog.newInstance(SelfieVideos.this, message,actionmsg);
		newFragment.setCancelable(false);
		newFragment.show(getFragmentManager(), "dialog");
	}

	/**
	 * GetChannelUrl is an Asynctask which allows to load the url to webview.
	 */
	public class GetChannelUrl extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pd.show();
			btnHome.setVisibility(View.GONE);
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			h.postDelayed(r,5000);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			JsonParser sh = new JsonParser();
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair(Constants.KEY_TOPIC_LANGUAGE,Constants.VALUE_TOPIC_LANGUAGE));

			// logged in User's mail id to be parsed which has to be fetched from the db
			UserDetail user = DbClass.getUserLoginDetails();	
			String Email = user.getEmail();
			
			param.add(new BasicNameValuePair(Constants.KEY_SELFIE_MAIL,/*Constants.VALUE_TOPIC_LANGUAGE*/Email));

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(URL, JsonParser.POST, param);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null)
			{
				// Checks if json response is not null
				try 
				{
					// Creates json object
					JSONObject jsonObj = new JSONObject(jsonStr);
					if(jsonObj.getInt(Constants.OBJECT_STATUSCODE) == 200)
					{
						url = jsonObj.getString(Constants.OBJECT_URL_LINK);
					}
					status_code=jsonObj.getInt((Constants.OBJECT_STATUSCODE));
					System.out.println("print the status code "+status_code);
					//	System.out.println("url is "+url);
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			} 
			else 
			{
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

			h.removeCallbacks(r);
			if (pd.isShowing() )
			{
				//pd.dismiss();
				btnHome.setVisibility(View.VISIBLE);
			}
			if(status_code == 404)
			{
				errorAlertDialog(getResources().getString(R.string.selfievideo_novideo),"no_selfie_video");
			}

			if(url != null)
			{
				wbChannel.setWebViewClient(new MyWebViewClient());
				wbChannel.getSettings().setJavaScriptEnabled(true);
				wbChannel.loadUrl(url);
			}
		}
	}

	/**
	 * Class to load the url to the webview
	 */
	private class MyWebViewClient extends WebViewClient
	{
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) 
		{
			System.out.println("url is "+url);
			if(url.startsWith("mailto:")){
				Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
				
				//i.setData(Uri.parse("mailto:"));
				startActivity(Intent.createChooser(i, "Choose email provider... "));   
			}
			else if(url.startsWith(Constants.URL_FACEBOOK))
			{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);     
			}
			else if(url.startsWith(Constants.URL_TWITTER))
			{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);     
			}
			else
			{
				view.loadUrl(url);  
			}
			return true;
		}
		@Override
		public void onPageFinished(WebView view, String url) {
			
			super.onPageFinished(view, url);
			btnHome.setVisibility(View.VISIBLE);

			if (pd.isShowing())
				pd.dismiss();

		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home:
			Outside();
			break;

		default:
			break;
		}

	}
	@Override
	protected void onPause() {
		
		super.onPause();
		System.out.println("OnPause called...");
		if(wbChannel!=null){
		wbChannel.onPause();
		wbChannel.clearCache(true); 
		wbChannel.getSettings().setAppCacheEnabled(false);
		}
	}

	
	@Override
	public void onBackPressed() {
		Outside();
	}
	
	public void Outside(){
		Intent iHome = new Intent(SelfieVideos.this,HomeScreen.class);
		iHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(iHome);
		finish();
	}



}
