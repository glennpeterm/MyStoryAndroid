package com.analytics.util;



import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.supportclasses.Constants;

/*
 * Analytics class which sends all data regarding the 
 * 
 * **/

public class SendAnalytics
{
	
	
	 private GoogleAnalytics analytics;
     private Tracker t;
     Context context;
     
	
	
	public SendAnalytics(Context ctx) {
		context = ctx;
		analytics = GoogleAnalytics.getInstance(context);
	}
	
	

	
	
	public void sendScreenandEvent(String screen_Name, String event_Categ,String event_Action,String event_Label) {
		
		
		
		analytics.setLocalDispatchPeriod(30);
		t = analytics.newTracker(Constants.PROPERTY_ID); 
		t.setSessionTimeout(300); 
		t.enableExceptionReporting(true);
		
		
		
		t.setScreenName(screen_Name);
		t.send(new HitBuilders.AppViewBuilder().build());
		
		 
		t.send(new HitBuilders.EventBuilder()
		    .setCategory(event_Categ)
		    .setAction(event_Action)
		    .setLabel(event_Label)
		    .build());
	}
	

	
     public void sendEventAlone(String event_Categ,String event_Action,String event_Label) {
		
    	 analytics.setLocalDispatchPeriod(30);
 		t = analytics.newTracker(Constants.PROPERTY_ID); 
 		t.setSessionTimeout(300); 
 		t.enableExceptionReporting(true);
 		
 		
		t.send(new HitBuilders.EventBuilder()
		    .setCategory(event_Categ)
		    .setAction(event_Action)
		    .setLabel(event_Label)
		    .build());
	}
	
	
}
