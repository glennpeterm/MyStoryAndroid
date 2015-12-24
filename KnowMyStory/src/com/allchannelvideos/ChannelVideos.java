package com.allchannelvideos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.dialog.ErrorDialog;
import com.dialog.ReloadDialogActivity;
import com.dialog.TransparentProgressDialog;

import net.onehope.mystory.R;

import com.supportclasses.Constants;
import com.supportclasses.JsonParser;

import android.app.Activity;
import android.app.DialogFragment;
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
/*
 * ChannelVideos class loads the WATCH section of the menu. It lists all the videos uploaded. The user can view the videos uploaded 
 * by other users.It loads the url in webview.
 */
public class ChannelVideos extends Activity implements OnClickListener
{
	// Declare the variables and the objects used
	WebView wbChannel;
	Button btnHome;

	String url;
	String URL = Constants.URL_BASE + Constants.URL_ALLCHANNEL;

	TransparentProgressDialog pd;
	private Runnable r;
	private Handler h;

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

	private void addOnClickListner() {
		btnHome.setOnClickListener(this);
	}

	/**
	 * Function to initialize the components
	 */
	private void initializeComponents() 
	{
		wbChannel = (WebView) findViewById(R.id.webview);
		btnHome=(Button)findViewById(R.id.btn_home);
	}

	/**
	 * Function to initialize the objects
	 */
	private void initializeObjects() 
	{
		h = new Handler();
		//Loader set for max 30 seconds.
		h.postDelayed(r,30000);
		//Loads a custom loader with custom message.
		pd = new TransparentProgressDialog(this, R.drawable.spinner,getResources().getString(R.string.dialog_loading));
		r =new Runnable()
		{
			@Override
			public void run() 
			{
				if (pd.isShowing())
				{
					System.out.println("handler stops...");
					pd.dismiss();
					btnHome.setVisibility(View.VISIBLE);
					//Reloads the activity if loading fails.
					Intent intent=new Intent(ChannelVideos.this, ReloadDialogActivity.class);
					startActivity(intent);
				}
			}
		};
	}

	/**
	 * Calling method to parse Channel list API
	 */
	public void CallWebViewAsync() 
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		System.out.println("inside");
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
			errorAlertDialog(getResources().getString(R.string.network_conn_error));
		}
	}

	private void errorAlertDialog(String message)
	/*
	 * Error dialog shown if network fails
	 */
	{
		DialogFragment newFragment = ErrorDialog.newInstance(ChannelVideos.this,message,"ChannelVideo");
		newFragment.setCancelable(false);
		newFragment.show(getFragmentManager(), "dialog");
	}


	public class GetChannelUrl extends AsyncTask<Void, Void, Void> 
	{
		/*
		 * (non-Javadoc)
		 * GetChannelUrl is Asynctask which allows to load the url to webview.
		 */
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			pd.show();
			btnHome.setVisibility(View.GONE);
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			h.postDelayed(r,10000);
			pd.setCancelable(false);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			JsonParser sh = new JsonParser();
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair(Constants.KEY_TOPIC_LANGUAGE,Constants.VALUE_TOPIC_LANGUAGE));

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(URL, JsonParser.POST, param);

			Log.d("Response: ", "> " + jsonStr);

			// Checks if json response is not null
			if (jsonStr != null)
			{
				// Checks if json response is not null
				try 
				{
					// Creates json object
					JSONObject jsonObj = new JSONObject(jsonStr);
					url = jsonObj.getString(Constants.OBJECT_URL_LINK);
					System.out.println("url is "+url);
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
			//Loads URL
			/*	view.loadUrl(url);
			return true;*/

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
		
		
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// TODO Auto-generated method stub
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_home:
			finish();
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
	protected void onResume() {
		super.onResume();
		//Checks if the watch is loading when the app is resumed.
		if(Constants.WATCH_LOADING ==1){
			Constants.WATCH_LOADING =0;
			CallWebViewAsync();
		}else if(Constants.WATCH_LOADING ==2){
			Constants.WATCH_LOADING = 0;
			finish();
		}
	}
}
