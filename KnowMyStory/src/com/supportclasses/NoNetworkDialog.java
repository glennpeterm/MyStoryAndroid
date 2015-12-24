package com.supportclasses;

import java.util.List;

import com.dialog.UploadSuccessDialog;
import net.onehope.mystory.R;
import com.google.ytdl.UploadService;
import com.test.socialnetwork.Upload_Progress_Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class NoNetworkDialog extends Activity {
	Upload_Progress_Activity uploadProgress ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		
		//isAppOnForeground(this);
		//System.out.println("appOnForeground: "+isAppOnForeground(this));
		
		uploadProgress = new Upload_Progress_Activity();
		String a = getIntent().getStringExtra("CloseActivity");
		if(a!=null){
			if(a.equals("close")){
				System.out.println("closeee");
				
				/*Intent i = new Intent(this, UploadService.class);
		       this.stopService(i);*/
				finish();
			}
		}
		SuccessAlertDialog(
				this.getResources().getString(R.string.userdetail_update_fail),
				this.getResources().getString(R.string.online_title),
				"no_network");
	}

	 

	private void SuccessAlertDialog(String msg,String title,String action)
	{
		DialogFragment newFragment = UploadSuccessDialog.newInstance(this,msg,title,action);
		newFragment.show(getFragmentManager(), "dialog");
	}
	
}
