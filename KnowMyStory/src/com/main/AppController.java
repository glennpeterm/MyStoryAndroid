/*
 * Know My Story 
 */
package com.main;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.test.socialnetwork.LruBitmapCache;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class AppController extends MultiDexApplication{/*Application{*/

	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.

	
	//private static final String TWITTER_KEY = "N2JPKkFzRmDgl7aXqpsq6CwTA";
	//private static final String TWITTER_SECRET = "413uDxafejXQY5PC3zvG1jTuvKx273YHILkjCQ8HZh0Z13vdkl";
	
	private static final String TWITTER_KEY = "oxewTBTgylqaGVCr5ZPzU5pRJ";
	private static final String TWITTER_SECRET = "pXhJ84umDj2Jxvn3HfVKE9L9xREsChrmKivBFkTeDO6w5gwYbi";
	public static final String TAG = AppController.class
			.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;


	@Override
	public void onCreate() {
		super.onCreate();
		
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(this, new Twitter(authConfig));
		mInstance = this;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(AppController.this);
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	/**
	 * Method to get the instance of the volley imageloader
	 * @return
	 */
	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		
		Log.d("nImagerloafer",mImageLoader+"  ");
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}


}
