/*
 * Know My Story 
 */
package com.supportclasses.menu;

import java.util.HashMap;
import java.util.Map;    

import org.json.JSONObject;    

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class CustomRequest  extends JsonObjectRequest 
{
	 public CustomRequest(int method, String url, JSONObject jsonRequest,Listener listener, ErrorListener errorListener)
	 {
	   super(method, url, jsonRequest, listener, errorListener);
	 }

	 @Override
	 public Map getHeaders() throws AuthFailureError {
	   Map headers = new HashMap();
	   headers.put(Constants.API_KEY, Constants.API_VALUE);
	   return headers;
	 }

	}