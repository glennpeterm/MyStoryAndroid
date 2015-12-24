package com.test.socialnetwork;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.database.menu.Database;
import com.dialog.TransparentProgressDialog;
import com.dialog.UploadSuccessDialog;

import net.onehope.mystory.R;

import com.model.UserDetail;
import com.squareup.picasso.Picasso;
import com.supportclasses.CustomListAdapter;
import com.supportclasses.LoadJSONFRomAsset;
import com.supportclasses.menu.CommonFunctions;
import com.supportclasses.Constants;
import com.supportclasses.menu.CustomRequest;
import com.supportclasses.menu.JsonParser;
import com.supportclasses.menu.RotateOrientation;
import com.supportclasses.menu.SingletonKeyValueHolder;
import com.tabview.RegionActivity;
import com.tabview.Tab1Activity;


public class ProfileDetailsActivity extends Activity implements OnClickListener {
	
	
	EditText etxtvFname;
	EditText etxtvLname;
	TextView etxtvEmail;
	RadioButton rdbtnMale;
	RadioButton rdbtnFemale;
	TextView etxtvDOB;
	EditText etxtvAddress;
	EditText etxtvCity;
	EditText etxtvState;
	TextView actxtvCountry;
	EditText etxtvZipCode;
	EditText etxtvPhone;
	Button btn_Cancel;
	Button btn_Save;
	Database DbClass;
	Button btnImgPick;
	private Uri fileUri;
	private final int RESULT_LOAD_PROFILE_PIC=100;
	private String SmediaPath;
	private static File mediaFile;
	private CommonFunctions mCfObj;	
	private RequestQueue queue;
	JsonParser jpObj;
	private static String sProfilePicPath = null;
	ImageView profileImage;
	private final int RESULT_CAMERA_PROFILE_PIC = 110;
	private final int RESULT_CROP_IMAGE = 555;
	private static int rot;
	String strGender;
	Button rbtnMale, rbtnFemale;
	TextView tvGender,tvMyProfileheading;
	String strDefaultPic;
	String strProvider;
	String strProviderInfo;
	ProgressDialog dialog;
	RadioGroup rdgGender;
	Button button_home;
	RelativeLayout rl_pic;
	String Dob;
	TransparentProgressDialog pd;
	private Handler h;
	private Runnable r;
	ArrayList<HashMap<String, String>> CountryDetailsList = new ArrayList<HashMap<String, String>>();
	AlertDialog alertDialog;
	
	public Typeface abel_ttf;
	String profilePic_base64 = null;
	String profilepic_base64 = null;
	private DatePicker datePicker;
	private Calendar calendar;
	private int year, month, day;
	private int pre_yr,pre_month,pre_day;
	ArrayList<String> countryNameListArray = new ArrayList<String>();
	String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

	private LinearLayout llEdit;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		if(savedInstanceState!=null)
		{
			fileUri = savedInstanceState.getParcelable("file_uri");
			SmediaPath=savedInstanceState.getString("mediapath");
		}
		setContentView(R.layout.profile_myactivity);
		initializeObjects();
		setFontStyle();
		initalisecomponents();
		addOnClickListner();
		showDetails();
		DatePickerInitialize();
		
		h = new Handler();
		pd = new TransparentProgressDialog(this, R.drawable.spinner,getResources().getString(R.string.dialog_save));
		pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
					System.out.println("Connection Timeout!");
					Toast.makeText(ProfileDetailsActivity.this, getResources().getString(R.string.connection_time_out),Toast.LENGTH_LONG).show();
				}
			}
		};
		
	}
	private void DatePickerInitialize() {
		/*calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		System.out.println("year is "+year);
		month = calendar.get(Calendar.MONTH);
		System.out.println("mon is "+month);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("day is "+day);



		showDate(pre_yr, pre_month, pre_day);*/

		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);     


	}
	private void showDate(int year, int month, int day)
	{
		String mon=MONTHS[month];

		etxtvDOB.setText(new StringBuilder().append(day).append("-")
				.append(mon).append("-").append(year));
		System.out.println("check date is "+ day+"-"+mon+"-"+year);
	}
	private void initalisecomponents() {
		if(queue==null)
			queue=SingletonKeyValueHolder.getInstance().getRequestQueue(this);
		mCfObj=new CommonFunctions(ProfileDetailsActivity.this);
		jpObj=new JsonParser();

	}

	private void addOnClickListner() {
		btn_Save.setOnClickListener(this);
		btn_Cancel.setOnClickListener(this);
		btnImgPick.setOnClickListener(this);
		button_home.setOnClickListener(this);
		etxtvDOB.setOnClickListener(this);
		rbtnMale.setOnClickListener(this);
		rbtnFemale.setOnClickListener(this);
		actxtvCountry.setOnClickListener(this);
		llEdit.setOnClickListener(this);
	}

	private void initializeObjects() {
		
		button_home = (Button) findViewById(R.id.btn_home);
		DbClass = new Database(ProfileDetailsActivity.this);
		etxtvFname = (EditText)findViewById(R.id.etxtvFname);
		etxtvLname = (EditText)findViewById(R.id.etxtvLname);
		etxtvLname.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_NEXT) 
				{
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					//txtName is a reference of an EditText Field
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					return true;
				}
				return false;
			}
		});
		etxtvEmail = (TextView)findViewById(R.id.etxtvEmail);
		etxtvDOB = (TextView)findViewById(R.id.etxtvDOB);
		etxtvAddress = (EditText)findViewById(R.id.etxtvAddress);
		etxtvCity = (EditText)findViewById(R.id.etxtvCity);
		etxtvState = (EditText)findViewById(R.id.etxtvState);
		actxtvCountry = (TextView)findViewById(R.id.actxtvCountry);
		etxtvZipCode = (EditText)findViewById(R.id.etxtvZipCode);
		etxtvPhone = (EditText)findViewById(R.id.etxtvPhone);
		btn_Cancel = (Button)findViewById(R.id.btnCancel);
		btn_Save = (Button)findViewById(R.id.btnUpdate);
		btnImgPick=(Button) findViewById(R.id.btnImagePick);
		profileImage = (ImageView)findViewById(R.id.imgvPic);
		rbtnMale = (Button) findViewById(R.id.rdbtnMale);
		rbtnFemale = (Button) findViewById(R.id.rdbtnFemale);
		tvGender=(TextView)findViewById(R.id.tvGender);
		tvMyProfileheading=(TextView)findViewById(R.id.tvVerseIndex);
		llEdit=(LinearLayout)findViewById(R.id.llEdit);

		etxtvState.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_NEXT) 
				{
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					//txtName is a reference of an EditText Field
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					CountryPopUp();
					return true;
				}
				return false;
			}
		});
		//rdgGender = (RadioGroup) findViewById(R.id.rdgGender);
		/*rdgGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (group.getCheckedRadioButtonId()) {
				case R.id.rdbtnMale:
					strGender = "male";
					break;
				case R.id.rdbtnFemale:
					strGender = "female";
					break;
				}

			}
		});*/


	}
	
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(ProfileDetailsActivity.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		etxtvFname.setTypeface(abel_ttf);
		etxtvLname.setTypeface(abel_ttf);
		etxtvEmail.setTypeface(abel_ttf);
		etxtvDOB.setTypeface(abel_ttf);
		etxtvAddress.setTypeface(abel_ttf);
		etxtvCity.setTypeface(abel_ttf);
		etxtvState.setTypeface(abel_ttf);
		actxtvCountry.setTypeface(abel_ttf);
		etxtvZipCode.setTypeface(abel_ttf);
		etxtvPhone.setTypeface(abel_ttf);
		btn_Cancel.setTypeface(abel_ttf);
		btn_Save.setTypeface(abel_ttf);
		btnImgPick.setTypeface(abel_ttf);
		rbtnMale.setTypeface(abel_ttf);
		rbtnFemale.setTypeface(abel_ttf);
		tvGender.setTypeface(abel_ttf);
		tvMyProfileheading.setTypeface(abel_ttf);
	}
	
	private void showDetails() {
		if (DbClass.loginCount() > 0) {
			UserDetail user = DbClass.getUserLoginDetails();	
			String FirstName = user.getFirstName();
			String Lastname = user.getLastName();
			String Email = user.getEmail();
			String Gender = user.getGender();
			if(!(user.getDob().equals("0000-00-00")))
			{
			/*	String*/ Dob =dateFormat(user.getDob());
			}
			String Address = user.getAddress();
			String City = user.getCity();
			String State = user.getState();
			String Country = user.getCountry();
			String Zipcode = user.getZipCode();
			String Phone = user.getPhone();
			String Profilepic = user.getProfilepic();
			
			if(FirstName!=null)
				etxtvFname.setText(FirstName);
			if(Lastname!=null)
				etxtvLname.setText(Lastname);
			if(Email!=null)
				etxtvEmail.setText(Email);
			if(Gender!=null){
				//check radio button as per  male female 
				setGender(Gender);
			}
			if(Dob!=null)
				etxtvDOB.setText(Dob);
			if(Address!=null)
				etxtvAddress.setText(Address);
			if(City!=null)
				etxtvCity.setText(City);
			if(State!=null)
				etxtvState.setText(State);
			if(Country!=null)
				actxtvCountry.setText(Country);
			if(Zipcode!=null)
				etxtvZipCode.setText(Zipcode);
			if(Phone!=null)
				etxtvPhone.setText(Phone);
			
			System.out.println("Profilepic: "+Profilepic);
			if(Profilepic!=null && Profilepic.toString().length()>0){
				System.out.println("PROFILE PIC "+user.getProfilepic());
				Picasso.with(ProfileDetailsActivity.this)
				.load(user.getProfilepic())
				.placeholder(getResources().getDrawable(R.drawable.ic_launcher))
				.error(getResources().getDrawable(R.drawable.defauls_profile))
				.into(profileImage);

			}
			setProvider(user.getType());
			setProviderInfo(user.getToken());
		
		}
	}
	
	private String dateFormat(String parse_date)
	{
		String Dateformt = parse_date;
		
		String[] result = Dateformt.split("-");
		/*	if(Integer.parseInt(result[1])!=0)
		{*/
		String needed_format = null;
		
		System.out.println("integer value of month "+(Integer.valueOf(result[1])-1));
		if((Integer.valueOf(result[1])-1) >0){
		String string_month=MONTHS[Integer.valueOf(result[1])-1];
		 needed_format=result[2]+"-"+string_month+"-"+result[0];

		}
		return needed_format;
		//	}
	}
	
	
	private void setFields(UserDetail user) {

		etxtvFname.setText(user.getFirstName());
		etxtvLname.setText(user.getLastName());
		etxtvEmail.setText(user.getEmail());
		CheckAndSet(etxtvAddress, user.getAddress());
		CheckAndSet(etxtvCity, user.getCity());
		if (user.getDob() != null) {
			if (!(user.getDob().equals("0000-00-00"))) {
				etxtvDOB.setText(dateFormat(user.getDob()));
			}
		}
		//CheckAndSet(etxtvDOB, user.getDob());
		CheckAndSet(etxtvState, user.getState());
		CheckAndSet(etxtvPhone, user.getPhone());
		CheckAndSet(etxtvZipCode, user.getZipCode());		
		//CheckAndSet(actxtvCountry, user.getCountry());
		actxtvCountry.setText(user.getCountry());
		setGender(user.getGender());

		if(user.getProfilepic()!=null && user.getProfilepic().toString().length()>0){
			System.out.println("PROFILE PIC "+user.getProfilepic());
			Picasso.with(ProfileDetailsActivity.this)
			.load(user.getProfilepic())
			.placeholder(getResources().getDrawable(R.drawable.ic_launcher))
			.error(getResources().getDrawable(R.drawable.defauls_profile))
			.into(profileImage);

		}
	}
	private void CheckAndSet(EditText etxtv, String str) {
		if (str != null) {
			etxtv.setText(str);
		}

	}
	private void setProviderInfo(String token) {
		if(token.length()>0)
			strProviderInfo=token;
		else
		{
			strProviderInfo=DbClass.getUserLoginDetails().getToken();
		}

	}

	private void setProvider(String type) {
		if(type.length()>0)
			strProvider=type;
		else
			strProvider=DbClass.getUserLoginDetails().getType();

	}

	private String getZipCode()
	{
		return etxtvZipCode.getText().toString();
	}
	private void setGender(String gender) {
		if (gender != null) {

			if (gender.equalsIgnoreCase("male")) {
				strGender="male";
				//rbtnMale.setChecked(true);
				rbtnMale.setBackgroundColor(getResources().getColor(R.color.orange));
				rbtnMale.setTextColor(getResources().getColor(R.color.white));

			} else {
				strGender="female";
				//rbtnFemale.setChecked(true);
				rbtnFemale.setBackgroundColor(getResources().getColor(R.color.orange));
				rbtnFemale.setTextColor(getResources().getColor(R.color.white));
			}
		}
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnUpdate:
			new UpdateProfileAsynctask(Constants.URL_UPDATE_USER).execute();
			//updateProfile(Constants.URL_UPDATE_USER);
			//Show progress dialog
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					//Update databse 
					//Dismiss progress dialog
					updateProfile(Constants.URL_UPDATE_USER);
					
				}
			});*/
			break;
		case R.id.btnCancel:
			finish();
			
			break;
		case R.id.btnImagePick:
			ImageSelectorDialog(RESULT_LOAD_PROFILE_PIC);
			break;
		case R.id.btn_home:
			
	/*		Intent iHome = new Intent(ProfileDetailsActivity.this,HomeScreen.class);
			iHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(iHome);*/
			finish();
			if (ProfileActivity.profile_activity != null) {
				ProfileActivity.profile_activity.finish();
				if (LoginActivity.login_activity != null) {
					LoginActivity.login_activity.finish();
				}
			}
			break;
		case R.id.etxtvDOB:
			showDialog(999);
			break;
		case R.id.rdbtnFemale:
			strGender = "female";
			rbtnFemale.setBackgroundColor(getResources().getColor(R.color.orange));
			rbtnFemale.setTextColor(getResources().getColor(R.color.white));
			
			rbtnMale.setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_border));
			rbtnMale.setTextColor(getResources().getColor(R.color.orange));

			break;
		case R.id.rdbtnMale:
			strGender = "male";
			rbtnMale.setBackgroundColor(getResources().getColor(R.color.orange));
			rbtnMale.setTextColor(getResources().getColor(R.color.white));
			
			rbtnFemale.setBackgroundDrawable(getResources().getDrawable(R.drawable.gender_border));
			rbtnFemale.setTextColor(getResources().getColor(R.color.orange));

			break;
		case R.id.actxtvCountry:
			CountryPopUp();
			break;

		case R.id.llEdit:
			onBackPressed();
			break;

		default:
			break;
		}
		
	}
	 private void CountryPopUp() {
		 new GetCountryList().execute();
		
	}
	@Override
	   protected Dialog onCreateDialog(int id) {
	   
	      if (id == 999) {
	        // return new DatePickerDialog(ProfileDetailsActivity.this, myDateListener, year, month, day);
	       return new DatePickerDialog(this, myDateListener, year, month,day);
}
	      return null;
	   }

	   private DatePickerDialog.OnDateSetListener myDateListener
	   = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker arg0, int selectedYear, int selectedMonth, int selectedDay) {
			
			// arg1 = year
			// arg2 = month
			// arg3 = day

			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			showDate(year, month, day);
		}
	};
	private String getFirstName()
	{
		return etxtvFname.getText().toString();
	}
	private String getLastName()
	{
		return etxtvLname.getText().toString();
	}
	private String getEmail()
	{
		return etxtvEmail.getText().toString();
	}
	private String getGender()
	{
		return strGender;
	}
	private String getDob()
	{
		if( etxtvDOB.getText().toString().length()>0)
		{
			/*String dob = etxtvDOB.getText().toString();
			String[] date = dob.split("-");
			String day = date[0];
			String month = date[1];
			String year = date[2];*/

			String FormatDOB = year+"-"+(1+month)+"-"+day;
			System.out.println("format dob is "+FormatDOB);
			return FormatDOB;
			//return etxtvDOB.getText().toString();
		}
		return null;


	}
	private String getLanguage()
	{	
		//Fetch language from DB
		return Locale.getDefault().toString();
	}
	private String getAddress()
	{
		return etxtvAddress.getText().toString();
	}
	private String getCity()
	{
		return etxtvCity.getText().toString();
	}
	private String getState()
	{
		return etxtvState.getText().toString();
	}
	private String getCountry()
	{
		if( actxtvCountry.getText().toString().length()>0)
		{
			return actxtvCountry.getText().toString();
		}
		return null;
	}
	private String getPhone()
	{
		return etxtvPhone.getText().toString();
	}
	private String getProvider() {
		return strProvider;

	}
	private String getProviderInfo() {
		return strProviderInfo;

	}
	private void updateProfile(String url)
	{


		JSONObject jobjData=new JSONObject();
		try {
			jobjData.put(Constants.KEY_ADD_FNAME, getFirstName());
			jobjData.put(Constants.KEY_ADD_LNAME, getLastName());
			jobjData.put(Constants.KEY_ADD_EMAIL, getEmail());
			System.out.println("dob value is "+getDob());
			jobjData.put(Constants.KEY_ADD_DOB, getDob());
			jobjData.put(Constants.KEY_ADD_GENDER, getGender());
			jobjData.put(Constants.KEY_ADD_ADDRESS, getAddress());
			if(sProfilePicPath!=null)
			{
				if(rot!=0)
				{
					System.out.println("sProfilePicPath11: "+sProfilePicPath);
					jobjData.put(Constants.KEY_PHOTO, CommonFunctions.ProfileBase64Converter(new RotateOrientation().RotateOrientationCall(CommonFunctions.decodeSampledBitmapFromResource(sProfilePicPath,200,200),rot)));
					sProfilePicPath = null;
				}
				else
				{
					System.out.println("sProfilePicPath22: "+sProfilePicPath);
					jobjData.put(Constants.KEY_PHOTO, CommonFunctions.ProfileBase64Converter(CommonFunctions.decodeSampledBitmapFromResource(sProfilePicPath,200,200)));
					sProfilePicPath = null;
				}
			}
			else
			{
				//String profilepic_base64 =  setPic(getProPic());
				System.out.println("DEFAULT "+getProPic());
				System.out.println("profilepic_base64 ProfileDetailsActivity: "+profilepic_base64);
				jobjData.put(Constants.KEY_PHOTO,profilepic_base64);
				
				//jobjData.put(Constants.KEY_PHOTO,profilepic_base64);
				/*String aa = getProPic() ;
				if (aa != null) {
					if (getProPic() != null
							&& getProPic().toString().length() > 0) {
						System.out.println("PROFILE PIC " + getProPic());
						try {
							Picasso.with(ProfileDetailsActivity.this)
									.load(getProPic())
									.placeholder(
											getResources().getDrawable(
													R.drawable.ic_launcher))
									.error(getResources().getDrawable(
											R.drawable.defauls_profile))
									.into(profileImage);
						} catch (NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}*/
				//jobjData.put(Constants.KEY_PHOTO,profilepic_base64);
				//jobjData.put(Constants.KEY_PHOTO,DbHelp.getUserLoginDetails().getType());
				
			}

			jobjData.put(Constants.KEY_ADD_CITY, getCity());
			jobjData.put(Constants.KEY_ADD_STATE, getState());
			jobjData.put(Constants.KEY_ADD_COUNTRY, getCountry());
			jobjData.put(Constants.KEY_ADD_LANGUAGE, getLanguage());
			jobjData.put(Constants.KEY_ADD_PROVIDER, getProvider());
			jobjData.put(Constants.KEY_ADD_PROVIDER_INFO, getProviderInfo());
			jobjData.put(Constants.KEY_ADD_PHONE, getPhone());
			jobjData.put(Constants.KEY_ADD_ZIPCODE,getZipCode());
		}
		catch(JSONException e)
		{

		}
		
		showProgressDiallog();

		CustomRequest jsonObjReq = new CustomRequest(Method.POST,
				Constants.URL_BASE+url, jobjData,
				new com.android.volley.Response.Listener<JSONObject>() {



			@Override
			public void onResponse(JSONObject response) {

				System.out.println("UPDATE USER SUCCESS "+response.toString());
				hideProgressDialog();

				if(jpObj.IsJSON(response.toString()))
				{
					try {
						if(response.getBoolean("Success"))
						{
							UserDetail user=jpObj.USerDetailsParser(response.getJSONObject("Result").toString());
							setFields(user);							
							mCfObj.UpdateUserDeatils(user);
							SuccessAlertDialog(getResources().getString(R.string.userdetail_update_success),getResources().getString(R.string.online_title));

						}else{
							
							SuccessAlertDialog(getResources().getString(R.string.no_server_response),getResources().getString(R.string.online_title));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						SuccessAlertDialog(getResources().getString(R.string.no_server_response),getResources().getString(R.string.online_title));
					}
				}


			}
		}, new com.android.volley.Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				hideProgressDialog();
				SuccessAlertDialog(getResources().getString(R.string.no_server_response),getResources().getString(R.string.online_title));
				//Toast.makeText(ProfileDetailsActivity.this,error.toString(), 2).show();
				System.out.println("ADD FAIL "+error.toString());



			}
		});
		//		jsonObjReq.setTag(index);
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
				30000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 


		queue.add(jsonObjReq);


		System.out.println("UPDATE "+jobjData.toString());

	}
	private String setPic(final String profilepic) {

		
		

		try {
			if (profilepic != null) {
				if (profilepic
						.contains(Constants.FACEBOOK_IMAGE_URL_BASE)) {
					Bitmap b = Picasso
							.with(ProfileDetailsActivity.this)
							.load(profilepic.replace("http", "https"))
							.get();
					strDefaultPic = mCfObj.ProfileBase64Converter(b);
				} else {
					Bitmap b = Picasso
							.with(ProfileDetailsActivity.this)
							.load(profilepic).get();
					strDefaultPic = mCfObj.ProfileBase64Converter(b);

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strDefaultPic;
		

			/*AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {

					String token = null;


					try {

					if (profilepic != null) {
						if (profilepic
								.contains(Constants.FACEBOOK_IMAGE_URL_BASE)) {
							Bitmap b = Picasso
									.with(ProfileDetailsActivity.this)
									.load(profilepic.replace("http", "https"))
									.get();
							strDefaultPic = mCfObj.ProfileBase64Converter(b);
							token = strDefaultPic;
						} else {
							Bitmap b = Picasso
									.with(ProfileDetailsActivity.this)
									.load(profilepic).get();
							strDefaultPic = mCfObj.ProfileBase64Converter(b);
							token = strDefaultPic;

						}

					}



							
						Bitmap b=Picasso.with(MyProfileActivity.this).load(profilepic).get();


					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return token;
				}

				@Override
				protected void onPostExecute(String token) {

					System.out.println("POOOOOOOOOOOO "+token);
					profilePic_base64 = token;
				}
			};
			task.execute();
			return profilePic_base64;*/

		}
	private void showProgressDiallog()
	{
//		dialog = new ProgressDialog(this);
//		dialog.setMessage(getResources().getString(R.string.profile_progress_updating));
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.show();
		
		pd.show();
		//pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		h.postDelayed(r,30000);
	}
	private void hideProgressDialog()
	{
		//dialog.hide();
		
		h.removeCallbacks(r);
		if (pd.isShowing() ) {
			pd.dismiss();
		}
		//SuccessAlertDialog(getResources().getString(R.string.userdetail_update_success),getResources().getString(R.string.online_title));
	}
	private void SuccessAlertDialog(String msg,String title)
	{
		System.out.println("function save respomse");
		DialogFragment newFragment = UploadSuccessDialog.newInstance(ProfileDetailsActivity.this,msg,title,"go_back_page");
		newFragment.show(getFragmentManager(), "dialog");
	}
	private void ImageSelectorDialog(final int code) 
	{
		final CharSequence[] items = { /*"Camera",*/"Gallery"};//getResources().getString(R.string.upload_dialog_camera), getResources().getString(R.string.upload_dialog_gallery)};
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(ProfileDetailsActivity.this);
		myAlertDialog.setTitle("Upload Title");//getResources().getString(R.string.upload_dialog_title));
		myAlertDialog.setItems(items, new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				switch (which) {
				case 0:
					callGallery(code);
					break;

				default :
					
					callCamera(code+10);  
					break;
				}

			} });
		myAlertDialog.show();
	}
	// Function to capture image from the camera
		private void callCamera(int code)
		{

			final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri=getOutputMediaFileUri(Constants.CAMERA_REQUEST,getResources().getString(R.string.app_name)+File.separator+"KMS");
			if(fileUri!=null)
			{
				intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
				startActivityForResult(intent, code);
			}
			else
			{
				//				mCfObj.Alerter(getResources().getString(R.string.),"ERROR",R.drawable.ic_launcher);
			}
		}
		// Function to open Gallery
		private void callGallery(int code)
		{
			Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(i, code);
		}
		/**
		 * Function for getting the URI of the image
		 */
		private  Uri getOutputMediaFileUri(int type,String folderName)
		{
			mediaFile=mCfObj.getOutputMediaFile(type, folderName);
			SmediaPath=mediaFile.getAbsolutePath();
			return Uri.fromFile(mediaFile);
		}
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) 
		{
			super.onActivityResult(requestCode, resultCode, data);

			if (requestCode == RESULT_LOAD_PROFILE_PIC && resultCode == Activity.RESULT_OK && null != data) 
			{
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = null;
				 picturePath = cursor.getString(columnIndex);
				cursor.close();

				if(requestCode == RESULT_LOAD_PROFILE_PIC)
				{


					//					callCropper(picturePath,selectedImage);
					sProfilePicPath=picturePath;
					//loadImageInSubView(imgvProfilePic,selectedImage);
					if(sProfilePicPath!=null){
					loadImageInSubView1(profileImage,selectedImage, sProfilePicPath);
					}
					profileImage.setVisibility(View.VISIBLE);
				}
			}
			else if(requestCode == RESULT_CAMERA_PROFILE_PIC && resultCode == Activity.RESULT_OK ){

				//				callCropper(picturePath,selectedImage);

				Uri selectedImage = fileUri;
				String picturePath = SmediaPath;
				sProfilePicPath = picturePath;
				System.out.println("campic_path: "+sProfilePicPath);
				System.out.println("selected_image: "+selectedImage);
				loadImageInSubView1(profileImage,selectedImage, sProfilePicPath);
				profileImage.setVisibility(View.VISIBLE);

			}else if(requestCode == RESULT_CROP_IMAGE && resultCode == Activity.RESULT_OK){
				/* String picturePath = data.getStringExtra(CropImage.IMAGE_PATH);
		            Uri selectedImage=data.getParcelableExtra("FILEURI");*/


				/*String picturePath =data.getExtras().getString("cropImg");
					Uri selectedImage=data.getParcelableExtra("fileUri");
					sProfilePicPath = picturePath;
					loadImageInSubView1(imgvProfilePic,selectedImage, sProfilePicPath);
					imgvProfilePic.setVisibility(View.VISIBLE);*/

			}
		}
		@SuppressLint("NewApi")
		@SuppressWarnings("deprecation")
		private void loadImageInSubView1(ImageView ibSubView,Uri uri,String imagePath)
		{
			System.out.println("ibSubView: "+ibSubView);
			System.out.println("uri: "+uri);
			System.out.println("imagePath: "+imagePath);
			rot=CommonFunctions.getCameraPhotoOrientation(this,uri,imagePath);
			System.out.println("rot: "+rot);
			profileImage = (ImageView)findViewById(R.id.imgvPic);
			rl_pic = (RelativeLayout)findViewById(R.id.rl_pic);
			//int width=(profileImage.getBackground().getBounds().width())-10;
			int width = (rl_pic.getMeasuredWidth());
			//int width = 20;
			int sdk = android.os.Build.VERSION.SDK_INT;
			if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
			{
				profileImage.setBackgroundDrawable(null);
				try
				{
					profileImage.setImageDrawable(new BitmapDrawable(this.getResources(), (CommonFunctions.getCroppedBitmap(CommonFunctions.getCorrectlyOrientedImage(ProfileDetailsActivity.this, uri,imagePath),width))));
					if(rot!=0)
					{
						profileImage.setImageDrawable(new BitmapDrawable(this.getResources(), CommonFunctions.getCroppedBitmap(new RotateOrientation().RotateOrientationCall(CommonFunctions.decodeSampledBitmapFromResource(imagePath,width,width),rot),width)));
					}
					else
					{
						profileImage.setImageDrawable(new BitmapDrawable(this.getResources(), CommonFunctions.getCroppedBitmap(CommonFunctions.decodeSampledBitmapFromResource(imagePath,width,width),width)));
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				} 
			} 
			else 
			{   
				profileImage.setBackground(null);
				try 
				{
					 
					
					profileImage.setImageDrawable(new BitmapDrawable(this.getResources(), (CommonFunctions.getCroppedBitmap(CommonFunctions.getCorrectlyOrientedImage(ProfileDetailsActivity.this, uri,imagePath),width))));
					if(rot!=0)
					{
						profileImage.setImageDrawable(new BitmapDrawable(this.getResources(), CommonFunctions.getCroppedBitmap(new RotateOrientation().RotateOrientationCall(CommonFunctions.decodeSampledBitmapFromResource(imagePath,width,width),rot),width)));
					}
					else
					{
						profileImage.setImageDrawable(new BitmapDrawable(this.getResources(), CommonFunctions.getCroppedBitmap(CommonFunctions.decodeSampledBitmapFromResource(imagePath,width,width),width)));
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				} 
			}
		}
		private String getProPic()
		{
			UserDetail user = DbClass.getUserLoginDetails();	
			strDefaultPic = user.getProfilepic();
			//String profilepic_base64 = setPic(user.getProfilepic());
			return strDefaultPic;


		}
		public class GetCountryList extends AsyncTask<Void, Void, Void> {
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
			}

			@Override
			protected Void doInBackground(Void... params) {
				//Get Data From Text Resource File Contains Json Data.    
				//InputStream inputStream = getResources().openRawResource(R.raw.languages);
			
				    // Parse the data into jsonobject to get original data in form of json.
					LoadJSONFRomAsset loadJSONFRomAsset = new LoadJSONFRomAsset();
				    JSONArray jObject = null;
					try {
						jObject = new JSONArray(loadJSONFRomAsset.loadJSON(ProfileDetailsActivity.this,com.supportclasses.Constants.REGION_COUNTRY_JSON_FILE));
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   
					try {
						for(int i = 0; i<jObject.length() ;i++){
							 String CountryCode =	jObject.getJSONObject(i).getString(com.supportclasses.Constants.KEY_REGION_COUNTRY_CODE);
							String CountryName =	jObject.getJSONObject(i).getString(com.supportclasses.Constants.KEY_REGION_COUNTRY_NAME);
							String CountryId =	jObject.getJSONObject(i).getString(com.supportclasses.Constants.KEY_REGION_COUNTRY_ID);
							countryNameListArray.add(CountryName);
							 HashMap<String, String> countryDetails = new HashMap<String, String>();
							 
							// adding each child node to HashMap key => value
							 countryDetails.put(com.supportclasses.Constants.REGION_COUNTRY_CODE, CountryCode);
							 countryDetails.put(com.supportclasses.Constants.REGION_COUNTRY_NAME, CountryName);
							 countryDetails.put(com.supportclasses.Constants.REGION_COUNTRY_ID, CountryId);
	                        // adding language to languageList
							 CountryDetailsList.add(countryDetails);
	                        System.out.println("countries: "+countryNameListArray);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
			
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);

				/**
				 * Updating parsed JSON data into Spinner with checkbox Here Custom
				 * spinner class is used The parameters should be passed in String
				 * array format only.
				 * */
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ProfileDetailsActivity.this);
				ListView list = new ListView(ProfileDetailsActivity.this);
				list.setFastScrollEnabled(true);
				list.setBackgroundColor(getResources().getColor(R.color.white));
				list.setDivider(new ColorDrawable(R.color.lightest_grey));
				list.setDividerHeight(1);
				list.setPadding(30, 0, 30, 0);
				list.setAdapter(new CustomListAdapter(ProfileDetailsActivity.this,
						countryNameListArray));
				list.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View arg1,
							int position, long arg3) {
						actxtvCountry.setText(parent.getItemAtPosition(position).toString());
						//db.insertCountry(actxtvCountry.getText().toString());
						//Toast.makeText(getApplicationContext(),
							//	"" + parent.getItemAtPosition(position),
							//	Toast.LENGTH_LONG).show();
						alertDialog.dismiss();
					}
				});
				  
				builder.setView(list);
				alertDialog = builder.create();

				alertDialog.getWindow().setBackgroundDrawableResource(
						android.R.color.transparent);
				alertDialog.setCanceledOnTouchOutside(true);
				alertDialog.show();

			
				DisplayMetrics displayMetrics = getApplicationContext()
						.getResources().getDisplayMetrics();
				int width = displayMetrics.widthPixels;
				int height = displayMetrics.heightPixels;
				float w = (float) (width * 0.80);
				float h = (float) (height * 0.75);
				int wid = (int) w;
				int hei = (int) h;

				alertDialog.getWindow().setLayout(wid, hei);
			}
			
		}

	public class UpdateProfileAsynctask extends
			AsyncTask<String, String, String> {

		public UpdateProfileAsynctask(String urlUpdateUser) {
			
		}

		@Override
		protected String doInBackground(String... params) {
			
			 try {
				profilepic_base64 =  setPic(getProPic());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			updateProfile(Constants.URL_UPDATE_USER);
		}
	}
}
