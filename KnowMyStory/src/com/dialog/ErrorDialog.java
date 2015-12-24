/*
 * Know My Story 
 */
package com.dialog;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import net.onehope.mystory.R;

import com.mediacontrols.VideoPlayerActivity;
import com.supportclasses.Constants;
import com.test.socialnetwork.AboutMyStory_Activity;
import com.test.socialnetwork.HomeScreen;

public class ErrorDialog extends DialogFragment {

	static Context context;
	static String error_message;
	TextView textview;
	TextView tv_header;;
	static String action;

	public Typeface abel_ttf;
	Button btn_ok;

	/**
	 * Create an instance for the class
	 */
	public static ErrorDialog newInstance(Context ctx, String message , String actionmsg) {
		context = ctx;
		 error_message = message;
		ErrorDialog frag = new ErrorDialog();
		action = actionmsg;
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.errordialog, container, false);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setFontStyle();
		tv_header = (TextView)v.findViewById(R.id.text_header);
		textview = (TextView)v.findViewById(R.id.text);
		textview.setTypeface(abel_ttf);
		tv_header.setTypeface(abel_ttf,Typeface.BOLD);
		textview.setText(error_message);

		/*final Button */btn_ok = (Button) v.findViewById(R.id.btn_ok);
		btn_ok.setTypeface(abel_ttf);
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getDialog().dismiss();
				if(action !=null){
			if(!(action.equals(""))){
				if (action.equals("no_selfie_video")) {
			/*		Intent intent = new Intent(context, HomeScreen.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);*/
					getActivity().finish();
				}else if(action.equals("ffmpeg")){
					getActivity().finish();
				}
				else if(action.equals("ChannelVideo")){
					getActivity().finish();
				}else if(action.equals("export_error")){
					Constants.EXPORT_ERROR = 1;
				}else if(action.equals("mediarecorder_not_created")){
					getActivity().finish();
				}else if(action.equals("escape")){
					Intent mStartActivity = new Intent(getActivity(), HomeScreen.class);
					int mPendingIntentId = 123456;
					PendingIntent mPendingIntent = PendingIntent.getActivity(getActivity(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
					AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
					mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
					System.exit(0);
				}
			}
		}
			}
		});

		return v;
	}

	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(context.getAssets(), "fonts/Abel-Regular.ttf");
		//textview.setTypeface(abel_ttf);
		//btn_ok.setTypeface(abel_ttf);
	}

}