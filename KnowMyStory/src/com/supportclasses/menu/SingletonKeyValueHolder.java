/*
 * Copyright (C) 2014 The Android Open Source Project 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.supportclasses.menu;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class to create a single instance of the class
 */
public class SingletonKeyValueHolder 
{
	// Declaring the variables
	private static SingletonKeyValueHolder sSkvObj;
	private boolean isInHome=true;
	private boolean isInCategoryHome=true;
	private int currentSelection;
	public String tweetMessage="";
	public String twitteremail="";
    public boolean isProductPulledWithoutLoggedin=false;
    public boolean isProductPulledWithoutLoggedinCategory=false;
    public boolean isReconnectingChat=true;
    public boolean isAuthenticated=false;
    public int pingCounter=0;
    
    private RequestQueue mRequestQueue;
	// Constructor for the class
	private SingletonKeyValueHolder()
	{
		isInHome=true;
	}

	/**
	 * Function for returning the instance of the class
	 */
	public static SingletonKeyValueHolder getInstance()
	{
		if(sSkvObj==null)
		{
			sSkvObj=new SingletonKeyValueHolder();
		}
		return sSkvObj;
	}

	public RequestQueue getRequestQueue(Context mCtx) {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        
        return mRequestQueue;
    }
	/**
	 * Function to set value based on in Home screen or not
	 */
	public void setHomeValue(boolean value)
	{
		isInHome=value;
	}

	/**
	 * Function to get the value whether in Home or not 
	 */
	public boolean getHomeValue()
	{
		return isInHome;
	}

	/**
	 * Function to set the value for the selected item
	 */
	public void setSelectedValue(int value)
	{
		currentSelection=value;
	}

	/**
	 * Function to get the value for the selected item
	 */
	public int getSelectedValue()
	{
		return currentSelection;
	}
	
	/**
     * Function to set value based on in Home screen or not
     */
    public void setCatHomeValue(boolean value)
    {
        isInCategoryHome=value;
    }

    /**
     * Function to get the value whether in Home or not 
     */
    public boolean getCatHomeValue()
    {
        return isInCategoryHome;
    }
}
