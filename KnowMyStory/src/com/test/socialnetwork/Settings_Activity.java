package com.test.socialnetwork;

import com.database.DatabaseHelper;
import com.database.menu.Database;
import com.facebook.Session;

import net.onehope.mystory.R;

import com.model.UserDetail;
import com.supportclasses.menu.CommonFunctions;
import com.supportclasses.menu.Constants;
import com.twitter.sdk.android.core.models.User;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Settings_Activity extends Activity implements OnClickListener
{
	private Button btnAbtMyStory;
	private Button btnPrivacyPolicy;
	private Button btnRateApp;
	private Button btnCopyRight;
	private Button btnTermsConditions;
//	private Button btnLogout;
	private Button btnHome;

	private Dialog custom_dialog;
	Button btnYes;
	TextView custom_heading,custom_text,tvInfoHeading;
	
	public Typeface abel_ttf;
	int logoutStatus;

	//Database DbClass;
	DatabaseHelper dbHelper;
	//SignInActivity signinActivity = new SignInActivity();
	LoginActivity loginActivity = new LoginActivity();
	//private CommonFunctions mCfObj = new CommonFunctions(Settings_Activity.this);
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//getActionBar().hide();
		setContentView(R.layout.settings_display);

		dbHelper = new DatabaseHelper(getApplicationContext());
		logoutStatus = dbHelper.getLogoutStatus();
		initializeComponents();
		setClicklisteners();
		
		abel_ttf=Typeface.createFromAsset(Settings_Activity.this.getAssets(), com.supportclasses.Constants.FONT_ABEL_REGULAR);
		setFontStyle();
		
		// function to be written
/*		if (logoutStatus == 0)
		{
			btnLogout.setVisibility(View.INVISIBLE);
		}
		else if(logoutStatus == 1)
		{
			btnLogout.setVisibility(View.VISIBLE);
		}*/
		logoutDialog();
	}

	private void initializeComponents()
	{
		dbHelper = new DatabaseHelper(getApplicationContext());
		btnAbtMyStory=(Button)findViewById(R.id.btnAbtMyStory);
		btnPrivacyPolicy=(Button)findViewById(R.id.btnPrivacyPolicy);
		btnRateApp=(Button)findViewById(R.id.btnRateTheApp);
		btnCopyRight=(Button)findViewById(R.id.btnCopyRight);
		btnTermsConditions=(Button)findViewById(R.id.btnTermsandCond);
	//	btnLogout=(Button)findViewById(R.id.btnLogout);
		btnHome = (Button)findViewById(R.id.btn_home);
		tvInfoHeading=(TextView)findViewById(R.id.tvVerseIndex);
	}

	private void setClicklisteners()
	{
		btnAbtMyStory.setOnClickListener(this);
		btnPrivacyPolicy.setOnClickListener(this);
		btnRateApp.setOnClickListener(this);
		btnCopyRight.setOnClickListener(this);
		btnTermsConditions.setOnClickListener(this);
	//	btnLogout.setOnClickListener(this);
		btnHome.setOnClickListener(this);
	}
	
	private void setFontStyle()
	{
		btnAbtMyStory.setTypeface(abel_ttf);
		btnPrivacyPolicy.setTypeface(abel_ttf);
		btnRateApp.setTypeface(abel_ttf);
		btnCopyRight.setTypeface(abel_ttf);
		btnTermsConditions.setTypeface(abel_ttf);
	//	btnLogout.setTypeface(abel_ttf);
		tvInfoHeading.setTypeface(abel_ttf);
	}

	public void showConfirmDialog()
	{

		custom_dialog.show();

	}

	public void logoutDialog()
	{
		custom_dialog = new Dialog(Settings_Activity.this);
		custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		custom_dialog.getWindow().getAttributes().width = LayoutParams.FILL_PARENT;
		custom_dialog.setContentView(R.layout.log_out_popup);
		custom_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		btnYes=(Button)custom_dialog.findViewById(R.id.btn_ok);
		btnYes.setTypeface(abel_ttf);
		custom_heading=(TextView)custom_dialog.findViewById(R.id.text_heading);
		custom_heading.setTypeface(abel_ttf);
		custom_text=(TextView)custom_dialog.findViewById(R.id.text);
		custom_text.setTypeface(abel_ttf);
		btnYes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				custom_dialog.dismiss();
		//		btnLogout.setVisibility(View.INVISIBLE);
			}
		} );
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btnAbtMyStory:
			Intent i=new Intent(this,AboutMyStory_Activity.class);
			startActivity(i);
			break;

		case R.id.btnPrivacyPolicy:
			loadURL(Constants.URL_PRIVACY_POLICY);
			break;

		case R.id.btnRateTheApp:
			//loadURL("https://www.google.com/");
			LoadRateAppLink();
			break;

		case R.id.btnCopyRight:
			loadURL(Constants.URL_COPYRIGHT);
			break;

		case R.id.btnTermsandCond:
			loadURL(Constants.URL_TERMS_COND);
			break;

		case R.id.btnLogout:
			// call logout function
			//signinActivity.setLogout(true);
			/*DbClass = new Database(Settings_Activity.this);
			//dbHelper.UpdateLogoutStatus("true");
			Constants.SET_LOGOUT_STATUS = 1;

			 UserDetail user = DbClass.getUserLoginDetails();
			 String User_Email = null;
			 if(user!=null){
				 User_Email  = user.getEmail();
				 if(User_Email!=null)
				 mCfObj.UpdateUserEmail(User_Email);
				 //mCfObj.UpdateLogoutStatus("true",User_Email);

				 System.out.println("User_Email: "+User_Email);
			 }*/
			LogoutUser();
			showConfirmDialog();

			break;
		case R.id.btn_home:
			finish();
			//onBackPressed();
			break;

		default:
			break;
		}

	}
	public void LoadRateAppLink()
	{
		Uri uri = Uri.parse("market://details?id=" + Settings_Activity.this.getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try 
		{
			startActivity(goToMarket);
		} 
		catch (ActivityNotFoundException e) 
		{
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + Settings_Activity.this.getPackageName())));
		}
	}

	private void LogoutUser() {
		dbHelper.UpdateLogoutStatus(0);
		dbHelper.updateUserDetails(null, null, null, null, null, null, null, null, null, null, null);

	}

	public void loadURL(String url)
	{
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		startActivity(launchBrowser);
	}

	/**
	 * function to check if user is logged in or not
	 */
	public boolean usserLoggedIn()
	{

		return false;
	}
}
