/*
 * Know My Story 
 */
package com.supportclasses;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

import com.tabview.RegionActivity.GetLanguageList;

public class LoadJSONFRomAsset {
	
	public String loadJSON(Context context, String jsonFileName) {
	    String json = null;
	    try {

	        InputStream is = context.getAssets().open(jsonFileName);

	        int size = is.available();

	        byte[] buffer = new byte[size];

	        is.read(buffer);

	        is.close();

	        json = new String(buffer, "UTF-8");


	    } catch (IOException ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return json;

	}

}
