package com.test.socialnetwork;

import java.util.ArrayList;

import com.database.DatabaseHelper;
import com.database.menu.Database;
import net.onehope.mystory.R;
import com.model.UserDetail;
import com.squareup.picasso.Picasso;
import com.supportclasses.Constants;
import com.tabview.Tab1Activity;
import com.twitter.sdk.android.core.models.User;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class ProfileActivity extends Activity implements OnClickListener{
	Button btn_editProfile;
	TextView tv_username,tvMyProfileHeading;
	TextView tv_email;
	TextView tv_address;
	TextView tv_phone;
	ImageView imgv_pic;
	DatabaseHelper db;
	Database DbClass;
	Button button_home;
	private Button btnLogout;
	private Dialog custom_dialog;
	Button btnYes;
	TextView custom_heading,custom_text;
	public static Activity profile_activity;
	

	public Typeface abel_ttf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.profile_edit_activity);
		profile_activity = this;
		initializeObjects();
		setFontStyle();
		addOnClickListner();
		showUserDetails();
		logoutDialog();
	}
	
	private void addOnClickListner() {
		btn_editProfile.setOnClickListener(this);
		button_home.setOnClickListener(this);
		btnLogout.setOnClickListener(this);

	}
	private void initializeObjects() {
		DbClass = new Database(ProfileActivity.this);
		db=new DatabaseHelper(ProfileActivity.this);
		btn_editProfile = (Button)findViewById(R.id.btn_edit_profile);
		tv_username= (TextView)findViewById(R.id.tvUserNAme);
		tv_email = (TextView)findViewById(R.id.tvMail);
		tv_address = (TextView)findViewById(R.id.tvAddress);
		tv_phone = (TextView)findViewById(R.id.tvPhone);
		button_home = (Button) findViewById(R.id.btn_home);
		imgv_pic = (ImageView)findViewById(R.id.imgvPic);
		tvMyProfileHeading=(TextView)findViewById(R.id.tvMyProfileHeading);
		btnLogout=(Button)findViewById(R.id.btnLogout);
	}
	
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(ProfileActivity.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		btn_editProfile.setTypeface(abel_ttf);
		tv_username.setTypeface(abel_ttf);
		tv_email.setTypeface(abel_ttf);
		tv_address.setTypeface(abel_ttf);
		tv_phone.setTypeface(abel_ttf);
		btnLogout.setTypeface(abel_ttf);
		tvMyProfileHeading.setTypeface(abel_ttf);
	}

	public void showConfirmDialog()
	{
		custom_dialog.show();
	}

	public void logoutDialog()
	{
		custom_dialog = new Dialog(ProfileActivity.this);
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
				//btnLogout.setVisibility(View.INVISIBLE);
				/*Intent intent_my_profile = new Intent(ProfileActivity.this,
						LoginActivity.class);
				intent_my_profile.putExtra(Constants.PRE_LOGIN_ACTIVITY,
						"profile_screen");
				startActivity(intent_my_profile);*/
				finish();
				if(LoginActivity.login_activity!=null){
					LoginActivity.login_activity.finish();
				}
			}
		} );
	}

	private void LogoutUser() 
	{
		db.UpdateLogoutStatus(0);
		db.updateUserDetails(null, null, null, null, null, null, null, null, null, null, null);
	}


	private void showUserDetails() {
		//ArrayList<String> userDetails= db.getUserDetails();
		//System.out.println("user-details: "+userDetails);
		
		if (DbClass.loginCount() > 0) {
			UserDetail user = DbClass.getUserLoginDetails();
			
			System.out.println("user details in db : "+user);
			//String Username = user.getFirstName()+" "+user.getLastName();
			String Username = user.getFirstName()+" "+user.getLastName();
			System.out.println("username from db: "+user.getFirstName());
			DbClass.getUserLoginDetails().getEmail();
			String Mail = user.getEmail();
			String Address = user.getAddress();
			String Phone = user.getPhone();
			String Profilepic = user.getProfilepic();
			
			System.out.println("piccc: "+Profilepic);
			if(Username!=null)
				tv_username.setText(Username);
			if(Mail!=null)
				tv_email.setText(Mail);
			if((Address!=null)){
				if(!(Address.equals(""))){
				tv_address.setText(Address);
				}else{
					tv_address.setVisibility(View.GONE);
				}
				
			}else
				tv_address.setVisibility(View.GONE);
			if(Phone!=null){
				if(!(Phone.equals(""))){
			
				tv_phone.setText(Phone);
				}else{
					tv_phone.setVisibility(View.INVISIBLE);
				}
			}else
				tv_phone.setVisibility(View.INVISIBLE);
			
			
			if(Profilepic!=null && Profilepic.toString().length()>0){
				System.out.println("PROFILE PIC "+user.getProfilepic());
				Picasso.with(ProfileActivity.this)
				.load(user.getProfilepic())
				.placeholder(getResources().getDrawable(R.drawable.ic_launcher))
				.error(getResources().getDrawable(R.drawable.defauls_profile))
				.into(imgv_pic);

			}
			
		}
		
		
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit_profile:
			Intent intent = new Intent(this,ProfileDetailsActivity.class);
			startActivity(intent);
			
			break;
		case R.id.btn_home:

			/*			Intent iHome = new Intent(ProfileActivity.this,HomeScreen.class);
			iHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(iHome);*/
			finish();
			System.out.println("LoginActivity: "+LoginActivity.login_activity);
			if(LoginActivity.login_activity!=null){
				
			LoginActivity.login_activity.finish();
			}
			break;

		case R.id.btnLogout:
			LogoutUser();
			showConfirmDialog();

			break;

		default:
			break;
		}
		
	}
@Override
protected void onResume() {
	
	super.onResume();
	showUserDetails();
}
}
