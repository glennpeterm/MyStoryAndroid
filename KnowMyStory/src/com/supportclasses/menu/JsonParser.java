/*
 * Know My Story 
 */
package com.supportclasses.menu;

import org.json.JSONException;
import org.json.JSONObject;

import com.model.UserDetail;
import com.model.VideoResponse;
import com.model.YouTubeResponse;

import android.util.Log;

public class JsonParser {


	private static final String TAG = JsonParser.class.getSimpleName().toString();

	/**
	 * Function for checking whether a string is in JSON format or not-Specifically for single level JSON
	 */
	public boolean IsJSON(String result)
	{
		try
		{
			new JSONObject(result);
			return true;
		}
		catch(Exception e)
		{
			Log.e(TAG, e.toString());
			return false;
		}
	}


	public UserDetail USerDetailsParser(String response)
	{

		UserDetail user=new UserDetail();
		try {
			JSONObject jobj=new JSONObject(response);
			if(jobj.has(Constants.KEY_FNAME))
			{
				System.out.println("first name : "+jobj.getString(Constants.KEY_FNAME));
				user.setFirstName(jobj.getString(Constants.KEY_FNAME));
			}
			if(jobj.has(Constants.KEY_LNAME))
				user.setLastName(jobj.getString(Constants.KEY_LNAME));
			if(jobj.has(Constants.KEY_EMAIL))
				user.setEmail(jobj.getString(Constants.KEY_EMAIL));
			if(jobj.has(Constants.KEY_ADDRESS))
				user.setAddress(jobj.getString(Constants.KEY_ADDRESS));
			if(jobj.has(Constants.KEY_CITY))
				user.setCity(jobj.getString(Constants.KEY_CITY));
			if(jobj.has(Constants.KEY_COUNTRY))
				user.setCountry(jobj.getString(Constants.KEY_COUNTRY));
			if(jobj.has(Constants.KEY_DOB))
				user.setDob(jobj.getString(Constants.KEY_DOB));
			if(jobj.has(Constants.KEY_GENDER))
				user.setGender(jobj.getString(Constants.KEY_GENDER));
			if(jobj.has(Constants.KEY_ACTIVE))
				user.setActive(jobj.getString(Constants.KEY_ACTIVE));
			if(jobj.has(Constants.KEY_PHOTO))
				user.setProfilepic(jobj.getString(Constants.KEY_PHOTO));
			if(jobj.has(Constants.KEY_STATE))
				user.setState(jobj.getString(Constants.KEY_STATE));
			if(jobj.has(Constants.KEY_PROVIDER))
				user.setType(jobj.getString(Constants.KEY_PROVIDER));
			if(jobj.has(Constants.KEY_PROVIDER_INFO))
				user.setToken(jobj.getString(Constants.KEY_PROVIDER_INFO));
			if(jobj.has(Constants.KEY_PHONE))
				user.setPhone(jobj.getString(Constants.KEY_PHONE));
			if(jobj.has(Constants.KEY_AUTHTOKEN))
				user.setAuthtoken(jobj.getString(Constants.KEY_AUTHTOKEN));
			if(jobj.has(Constants.KEY_ZIPCODE))
				user.setZipCode(jobj.getString(Constants.KEY_ZIPCODE));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;

	}

	public YouTubeResponse YouTubeParser(String response)
	{
		YouTubeResponse you=new YouTubeResponse();
		JSONObject jobj;

		try {
			jobj = new JSONObject(response);

			if(jobj.has("id"))
			{
				you.setId(jobj.getString("id"));
			}
JSONObject jSnipp=jobj.getJSONObject("snippet");
			if(jSnipp.has(Constants.KEY_VID_DESC))
			{
				you.setDescription(jSnipp.getString(Constants.KEY_VID_DESC));
			}
			if(jSnipp.has(Constants.KEY_VID_TITLE))
			{
				you.setTitle(jSnipp.getString(Constants.KEY_VID_TITLE));
			}
			you.setThumbanil(jSnipp.getJSONObject("thumbnails").getJSONObject("default").getString("url"));



		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return you;

	}
	public VideoResponse VideoParser(String response)
	{
		VideoResponse video=new VideoResponse();
		JSONObject jobj;
		try {
			jobj = new JSONObject(response);

			if(jobj.has(Constants.KEY_VID_ID))
			{
				video.setID(jobj.getString(Constants.KEY_VID_ID));
			}
			if(jobj.has(Constants.KEY_VID_DESC))
			{
				video.setDescription(jobj.getString(Constants.KEY_VID_DESC));
			}
			if(jobj.has(Constants.KEY_VID_EMBEDCODE))
			{
				video.setEmbedCode(jobj.getString(Constants.KEY_VID_EMBEDCODE));
			}
			if(jobj.has(Constants.KEY_VID_LANG))
			{
				video.setLanguge(jobj.getString(Constants.KEY_VID_LANG));
			}
			if(jobj.has(Constants.KEY_VID_MAIL))
			{
				video.setMail(jobj.getString(Constants.KEY_VID_MAIL));
			}
			if(jobj.has(Constants.KEY_VID_SCRIPTURE))
			{
				video.setScripture(jobj.getString(Constants.KEY_VID_SCRIPTURE));
			}if(jobj.has(Constants.KEY_VID_THUMBNAIL))
			{
				video.setThumbnail(jobj.getString(Constants.KEY_VID_THUMBNAIL));
			}
			if(jobj.has(Constants.KEY_VID_TITLE))
			{
				video.setTitle(jobj.getString(Constants.KEY_VID_TITLE));
			}
			if(jobj.has(Constants.KEY_VID_URL))
			{
				video.setYoutube_url(jobj.getString(Constants.KEY_VID_URL));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return video;		

	}

}
