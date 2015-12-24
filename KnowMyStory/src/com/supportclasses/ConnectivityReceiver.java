package com.supportclasses;

import java.util.List;

import com.dialog.UploadSuccessDialog;
import net.onehope.mystory.R;
import com.test.socialnetwork.Upload_Progress_Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DialogFragment;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
public class ConnectivityReceiver extends BroadcastReceiver {
Context ctx ;
    @Override
    public void onReceive(Context context, Intent intent) {

     String action = intent.getAction();
     ctx = context;
    boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);

    if(isAppOnForeground(context)){
     if(noConnectivity){
			//SuccessAlertDialog(context,context.getResources().getString(R.string.userdetail_update_fail),context.getResources().getString(R.string.online_title),"no_network");
    	
    	 Intent i = new Intent(context, NoNetworkDialog.class); 
    	    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    	    context.startActivity(i);
       
     }
    }else{
    	System.out.println("app is invisible...");
    }
    }
    private boolean isAppOnForeground(Context context) {
	    ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
	    if (appProcesses == null) {
	      return false;
	    }
	    final String packageName = context.getPackageName();
	    for (RunningAppProcessInfo appProcess : appProcesses) {
	      if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_VISIBLE ) {
	    	  if(appProcess.processName.equals(packageName)){
	        return true;
	    	  }
	      }
	    }
	    return false;
	  }    
}