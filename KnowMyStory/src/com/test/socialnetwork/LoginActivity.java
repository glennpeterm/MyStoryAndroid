package com.test.socialnetwork;

import io.fabric.sdk.android.Fabric;

import java.io.IOException;
import java.util.List;

import com.allchannelvideos.SelfieVideos;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.database.DatabaseHelper;
import com.database.menu.Database;
import com.dialog.TransparentProgressDialog;
import com.dialog.UploadSuccessDialog;
import net.onehope.mystory.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.model.UserDetail;
import com.squareup.picasso.Picasso;
import com.supportclasses.Constants;
import com.supportclasses.menu.CommonFunctions;
import com.supportclasses.menu.CustomRequest;
import com.supportclasses.menu.JsonParser;
import com.supportclasses.menu.SingletonKeyValueHolder;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

public class LoginActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {

	//	ImageButton btn_facebook;
//	ImageButton btn_twitter;
//	ImageButton btn_google;
	RelativeLayout btn_facebook;
	RelativeLayout btn_twitter;
	RelativeLayout btn_google;
	
	private Session currentSession;
	private Session.StatusCallback sessionStatusCallback;
	private static List<String> permissions;
	//Session.StatusCallback statusCallback = new SessionStatusCallback();
	//ProgressDialog dialog;
	DatabaseHelper db;
	Database dbClass;
	String PreLoginActivityString= null;
	// Used to store the PendingIntent most recently returned by Google Play
		// services until the user clicks 'sign in'.
	private PendingIntent mSignInIntent;
	UserDetail userModel;
	private static final String SAVED_PROGRESS = "sign_in_progress";
	 TransparentProgressDialog pd;
	 
	public static Activity login_activity;
	// GoogleApiClient wraps our service connection to Google Play services and
	// provides access to the users sign in state and Google's APIs.
	private GoogleApiClient mGoogleApiClient;
	// We use mSignInProgress to track whether user has clicked sign in.
		// mSignInProgress can be one of three values:
		//
		//       STATE_DEFAULT: The default state of the application before the user
		//                      has clicked 'sign in', or after they have clicked
		//                      'sign out'.  In this state we will not attempt to
		//                      resolve sign in errors and so will display our
		//                      Activity in a signed out state.
		//       STATE_SIGN_IN: This state indicates that the user has clicked 'sign
		//                      in', so resolve successive errors preventing sign in
		//                      until the user has successfully authorized an account
		//                      for our app.
		//   STATE_IN_PROGRESS: This state indicates that we have started an intent to
		//                      resolve an error, and so we should not start further
		//                      intents until the current intent completes.
		private int mSignInProgress;

	/**
	 * G+ Specific 
	 */

	private static final int STATE_IN_PROGRESS = 2;
	private static final int RC_SIGN_IN = 0;
	private static final int STATE_SIGN_IN = 1;
	private static final int DIALOG_PLAY_SERVICES_ERROR = 0;
	// Used to store the error code most recently returned by Google Play services
	// until the user clicks 'sign in'.
	private int mSignInError;
	private static final int STATE_DEFAULT = 0;
	SharedPreferences sharedpreferences;
	String strDefaultPic;
	CheckBox checkbox;
	ImageButton btn_back;

	TextView tvSignin,tvAgree,tvFb,tvGoogle,tvTwitter;
	public Typeface abel_ttf;
	private Handler h;
	private Runnable r;

	/**
	 * Twitter Specific
	 */
	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
	private static final String TWITTER_KEY = "HWjfKOH4PszvRm9Ij0AzJlqZz";
	private static final String TWITTER_SECRET = "GNtQ1PDZnTIYmT1CLYHcmuwqWY90IMJd5BAJgTwkOQ6hieAmfj";
	private TwitterAuthClient client;
	private RequestQueue queue;
	JsonParser jpObj;
	private CommonFunctions mCfObj;	
	Button button_home;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.sign_in);
		//dialog = new ProgressDialog(this);
		login_activity = this;
		PreLoginActivityString = getIntent().getStringExtra(Constants.PRE_LOGIN_ACTIVITY);
		initializeButtonObject();
		setFontStyle();
		mGoogleApiClient = buildGoogleApiClient();
		db = new DatabaseHelper(this);
		dbClass = new Database(this);
		addOnClickListner();
		h = new Handler();
		pd = new TransparentProgressDialog(this, R.drawable.spinner,getResources().getString(R.string.dialog_signin));
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
					//SuccessAlertDialog(getResources().getString(R.string.TimeOut_error),getResources().getString(R.string.online_title));

				}
			}
		};
		/***** FB Permissions *****/

		permissions = new ArrayList<String>();
		permissions.add("email");
		permissions.add("user_birthday");

		/***** End FB Permissions *****/
		// create instace for sessionStatusCallback
		sessionStatusCallback = new Session.StatusCallback() {

			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				onSessionStateChange(session, state, exception);

			}
		};
		
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
	    Fabric.with(this, new Twitter(authConfig));
	    client = new TwitterAuthClient();
	    sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
	}

	private GoogleApiClient buildGoogleApiClient() {
		// When we build the GoogleApiClient we specify where connected and
		// connection failed callbacks should be returned, which Google APIs our
		// app uses and which OAuth 2.0 scopes our app requests.
		return new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API, Plus.PlusOptions.builder().build())
		.addScope(Plus.SCOPE_PLUS_LOGIN)
		.addScope(Plus.SCOPE_PLUS_PROFILE)
		.build();


	}

	private void addOnClickListner() {
		btn_facebook.setOnClickListener(this);
		btn_google.setOnClickListener(this);
		btn_twitter.setOnClickListener(this);
		checkbox.setOnClickListener(this);
		button_home.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	private void initializeButtonObject() {
		btn_facebook = (RelativeLayout) findViewById(R.id.btn_facebook);
		btn_facebook.setEnabled(false);
		
		btn_google = (RelativeLayout) findViewById(R.id.btn_google);
		btn_google.setEnabled(false);
		
		btn_twitter =  (RelativeLayout) findViewById(R.id.btn_twitter);
		btn_twitter.setEnabled(false);
		
		checkbox = (CheckBox) findViewById(R.id.checkbox_signin);
		button_home = (Button) findViewById(R.id.btn_home);
		
		btn_back = (ImageButton)findViewById(R.id.btn_back);
		
		
		if(queue==null)
			queue=SingletonKeyValueHolder.getInstance().getRequestQueue(this);
		
		mCfObj=new CommonFunctions(LoginActivity.this);
		jpObj=new JsonParser();
	}

	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(LoginActivity.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		tvSignin=(TextView)findViewById(R.id.tvSignin);
		tvSignin.setTypeface(abel_ttf);
		tvAgree=(TextView)findViewById(R.id.tvAgree);
		tvAgree.setTypeface(abel_ttf);
		tvFb=(TextView)findViewById(R.id.tvFb);
		tvFb.setTypeface(abel_ttf);
		tvGoogle=(TextView)findViewById(R.id.tvGoogle);
		tvGoogle.setTypeface(abel_ttf);
		tvTwitter=(TextView)findViewById(R.id.tvTwitt);
		tvTwitter.setTypeface(abel_ttf);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.checkbox_signin: {
			if (((CheckBox) v).isChecked()) {
				btn_facebook.setEnabled(true);
				btn_google.setEnabled(true);
				btn_twitter.setEnabled(true);
			} else {
				btn_facebook.setEnabled(false);
				btn_google.setEnabled(false);
				btn_twitter.setEnabled(false);
			}
		}
		break;
		case R.id.btn_facebook:

			//btn_facebook.setEnabled(false);
			List<String> permissions = new ArrayList<String>();
			permissions.add("publish_stream");
			permissions.add("email");
			permissions.add("user_birthday");

			currentSession = new Session.Builder(this).build();
			currentSession.addCallback(sessionStatusCallback);

			Session.OpenRequest openRequest = new Session.OpenRequest(
					LoginActivity.this);
			openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
			openRequest.setRequestCode(Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE);
			openRequest.setPermissions(permissions);
			currentSession.openForPublish(openRequest);
			break;
		case R.id.btn_google:{
			
			System.out.println("Google+ login...");
			
			if(!mGoogleApiClient.isConnected())
			{
				//onSignedOut();
				mGoogleApiClient.connect();					
			}
			//revokeGplusAccess();
			resolveSignInError();
		}
			
			break;
		case R.id.btn_twitter:
			client.authorize(LoginActivity.this, new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> twitterSessionResult) {
                   // Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                    
                    TwitterSession session =
    						Twitter.getSessionManager().getActiveSession();
    				TwitterAuthToken authToken = session.getAuthToken();
    				String token = authToken.token;
    				String secret = authToken.secret;
    				
    				
                    new GetUserDetail().execute(authToken);
                    
                  //User is logged in
					//db.UpdateLogoutStatus(1);
					//LogoutCurrentUser();
					//  Login successfull Start your next activity
					/*if(PreLoginActivityString!=null){
						if(PreLoginActivityString.equals("upload_screen")){
							startActivity(new Intent(LoginActivity.this,
									Upload_Progress_Activity.class));
						}else if(PreLoginActivityString.equals("profile_screen")){
							startActivity(new Intent(LoginActivity.this,
									ProfileActivity.class));
						}
					}*/
                }

                @Override
                public void failure(TwitterException e) {
                	System.out.println("Twitter failed");
                    //Toast.makeText(LoginActivity.this, "failure", Toast.LENGTH_SHORT).show();
                }
            });
			break;
		case R.id.btn_home:
						
			Intent intent = new Intent(LoginActivity.this,HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_back:
			/*		Intent iBack = new Intent(LoginActivity.this,HomeScreen.class);
			iBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(iBack);*/
			finish();
			break;
		
		default:
			break;
		}

	};

	@Override
	public void onBackPressed() 
	{
	}

	/* Starts an appropriate intent or dialog for user interaction to resolve
	 * the current error preventing the user from being signed in.  This could
	 * be a dialog allowing the user to select an account, an activity allowing
	 * the user to consent to the permissions being requested by your app, a
	 * setting to enable device networking, etc.
	 */
	private void resolveSignInError() {
		if (mSignInIntent != null) {
			// We have an intent which will allow our user to sign in or
			// resolve an error.  For example if the user needs to
			// select an account to sign in with, or if they need to consent
			// to the permissions your app is requesting.

			try {
				// Send the pending intent that we stored on the most recent
				// OnConnectionFailed callback.  This will allow the user to
				// resolve the error currently preventing our connection to
				// Google Play services.
				mSignInProgress = STATE_IN_PROGRESS;
				startIntentSenderForResult(mSignInIntent.getIntentSender(),
						RC_SIGN_IN, null, 0, 0, 0);
			} catch (SendIntentException e) {
				Log.i("TAG", "Sign in intent could not be sent: "
						+ e.getLocalizedMessage());
				// The intent was canceled before it was sent.  Attempt to connect to
				// get an updated ConnectionResult.
				mSignInProgress = STATE_SIGN_IN;
				mGoogleApiClient.connect();
			}
		} else {
			// Google Play services wasn't able to provide an intent for some
			// error types, so we show the default Google Play services error
			// dialog which may still start an intent on our behalf if the
			// user can resolve the issue.
			showDialog(DIALOG_PLAY_SERVICES_ERROR);
		}


	}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case DIALOG_PLAY_SERVICES_ERROR:
			if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
				return GooglePlayServicesUtil.getErrorDialog(
						mSignInError,
						this,
						RC_SIGN_IN,
						new DialogInterface.OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								Log.e("TAG", "Google Play services resolution cancelled");
								mSignInProgress = STATE_DEFAULT;
								/* mStatus.setText(R.string.status_signed_out);*/
							}
						});
			} else {
				return new AlertDialog.Builder(this)
				.setMessage(R.string.play_services_error)
				.setPositiveButton(R.string.close,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.e("TAG", "Google Play services error could not be "
								+ "resolved: " + mSignInError);
						mSignInProgress = STATE_DEFAULT;
						/*  mStatus.setText(R.string.status_signed_out);*/
					}
				}).create();
			}
		default:
			return super.onCreateDialog(id);
		}
	}

	/**
	 * this method is used by the facebook API
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		client.onActivityResult(requestCode, resultCode, data);
		if (currentSession != null) {
			currentSession
					.onActivityResult(this, requestCode, resultCode, data);
		}
		
		
		switch (requestCode) {
		case RC_SIGN_IN:
			if (resultCode == RESULT_OK) {
				// If the error resolution was successful we should continue
				// processing errors.
				mSignInProgress = STATE_SIGN_IN;
			} else {
				// If the error resolution was not successful or the user canceled,
				// we should stop processing errors.
				mSignInProgress = STATE_DEFAULT;
			}


			
			if (!mGoogleApiClient.isConnecting()) {
				// If Google Play services resolved the issue with a dialog then
				// onStart is not called so we need to re-attempt connection here.
				mGoogleApiClient.connect();
			}
			break;
		}

	}

	@Override
	protected void onStart() {
		// TODO Add status callback
		super.onStart();
		mGoogleApiClient.connect();
		// Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	protected void onStop() {
		// TODO Remove callback
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
		// Session.getActiveSession().removeCallback(statusCallback);
	}

	/**
	 * manages the session state change. This method is called after the
	 * <code>connectToFB</code> method.
	 * 
	 * @param session
	 * @param state
	 * @param exception
	 */
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (session != currentSession) {
			return;
		}

		if (state.isOpened()) {
			// Log in just happened.
			/*Toast.makeText(getApplicationContext(), "session opened",
					Toast.LENGTH_SHORT).show();*/
			OpenActivity(session);
		} else if (state.isClosed()) {
			// Log out just happened. Update the UI.
			// Toast.makeText(getApplicationContext(), "session closed",
			// Toast.LENGTH_SHORT).show();
		}
	}

	@SuppressWarnings("deprecation")
	private void OpenActivity(Session session) {
		Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

			@Override
			public void onCompleted(GraphUser user, Response response) {

				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				
				if (user != null) {
					Map<String, Object> responseMap = new HashMap<String, Object>();
					GraphObject graphObject = response.getGraphObject();
					responseMap = graphObject.asMap();
					Log.i("FbLogin",
							"Response Map KeySet - " + responseMap.keySet());
					// TODO : Get Email responseMap.get("email");

					String fb_id = user.getId();
					String email = null;
					String name = (String) responseMap.get("name");
					System.out.println("firstname: " + user.getFirstName()
							+ " Lastname: " + user.getLastName()
							+ " birthday: " + responseMap.get("birthday")
							+ " Gender: " + responseMap.get("gender")
							+ " Email: " + responseMap.get("email"));
					if (responseMap.get("email") != null) {
						email = responseMap.get("email").toString();
						
						/*db.updateUserDetails(user.getFirstName(),
								user.getLastName(),
								null,
								responseMap.get("gender").toString(),
								responseMap.get("email").toString(),
								null,
								null,
								null,
								null,
								null,
								null);
						System.out.println("User deatils: "+db.getUserDetails());*/
						//User is logged in
						getUserDetails(user);
						//System.out.println("USER FACEBOOK "+getUserDetails(user));
						//db.UpdateLogoutStatus(1);
						
						//  Login successfull Start your next activity
						/*if(PreLoginActivityString!=null){
							if(PreLoginActivityString.equals("upload_screen")){
								startActivity(new Intent(LoginActivity.this,
										Upload_Progress_Activity.class));
							}else if(PreLoginActivityString.equals("profile_screen")){
								startActivity(new Intent(LoginActivity.this,
										ProfileActivity.class));
							}
						}*/
						
					} else {
						// Clear all session info & ask user to login again
						Session session = Session.getActiveSession();
						if (session != null) {
							session.closeAndClearTokenInformation();
						}
					}
				}
			}
		});

	}

	public String getUserDetails(GraphUser user) {
		String str = null;
		str = user.getId() + "\n" + user.getLink() + "\n" + user.getName()
				+ "\n" + user.getFirstName() + "\n" + user.getLastName() + "\n"
				+ user.getProperty("user_birthday") + "\n" + user.getUsername()
				+ "\n" + user.getLocation() + "\n"
				+ user.getProperty("email").toString();

		//		String birthday = user.asMap().get("birthday").toString();
		//		String location = user.asMap().get("location").toString();

		//		System.out.println("Birthday "+birthday);

		System.out.println("Gender "+user.asMap().get("gender").toString());
		//		System.out.println("Location "+location);
		Object userLocation = user.getLocation();
		String strLocation = (userLocation != null)? userLocation.toString() : "Address not mentioned";

		String strSession=null;
		Session session = Session.getActiveSession();
		if (session != null) {
			if (session.isOpened()) {
				strSession = session.getAccessToken();
			}
		}
		String image=Constants.FACEBOOK_IMAGE_URL_BASE+user.getId()+Constants.FACEBOOK_IMAGE_SIZE;
		//preeti...
		//setPic(image);
		System.out.println("PLACE FB "+strLocation);
		userModel=new UserDetail("FACEBOOK", user.getFirstName(), user.getLastName(), image, user.getProperty("email").toString(),strSession,null,user.getProperty("gender").toString(),null,null,null,null,null,null,null,null);

		//mCfObj.SaveUserDeatils(userModel);
		SendData(userModel);
		LogoutCurrentUser();

		return str;
	}

	public void LogoutCurrentUser() {
		if (currentSession != null) {
			currentSession.closeAndClearTokenInformation();

		}
	}

	/* onConnected is called when our Activity successfully connects to Google
	 * Play services.  onConnected indicates that an account was selected on the
	 * device, that the selected account has granted any requested permissions to
	 * our app and that we were able to establish a service connection to Google
	 * Play services.
	 */

	@Override
	public void onConnected(Bundle arg0) {
		//		RevokeAccess();
		//btnGLogin.setEnabled(false);
		// Indicate that the sign in process is complete.
		mSignInProgress = STATE_DEFAULT;
		//Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

		if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
			Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);		
			String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
			System.out.println("Gender G+"+""+currentPerson.getGender());
			System.out.println("Place G+"+""+currentPerson.getCurrentLocation());
			System.out.println("currentPerson G+"+""+currentPerson);
			System.out.println("email G+"+""+email);

			getGoogleToken(currentPerson,email);

		}

	}

	public void getGoogleToken(final Person currentPerson, final String email)
	{

		
		AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				if (!(pd.isShowing())) {
					pd.show();
					pd.getWindow().setLayout(LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT);
					h.postDelayed(r, 5000);
				}
			}
			@Override
			protected String doInBackground(Void... params) {
				String scope = "oauth2:" + Scopes.PLUS_LOGIN;
				String token;
				try {
					token = GoogleAuthUtil.getToken(LoginActivity.this,
							Plus.AccountApi.getAccountName(mGoogleApiClient), scope);
				} catch (Exception e) {
					e.printStackTrace();
					return e.getMessage();
				}
				return token;
			}

			@Override
			protected void onPostExecute(String token) {
				System.out.println("TOKENN  "+token);
				String Gender = null;
				if(token != null) {
					switch (currentPerson.getGender()) {
					case 0:
						 
						//Gender is = Male
						userModel=new UserDetail("GOOGLE", currentPerson.getName().getGivenName(), currentPerson.getName().getFamilyName(), currentPerson.getImage().getUrl(), email,token,null,"male",null,null,null,null,null,null,null,null);
						//Gender = "male";
						break;
					case 1:
						//Gender is = Female
						userModel=new UserDetail("GOOGLE", currentPerson.getName().getGivenName(), currentPerson.getName().getFamilyName(), currentPerson.getImage().getUrl(), email,token,null,"female",null,null,null,null,null,null,null,null);
						//Gender = "female"; 
						break;
					}

					//        			RevokeAccess();

					/*String Address = null;
					String Country = null;
					String City = null;
					String Phone = null;
					String State = null;
					String Zip = null;
					
					db.updateUserDetails(currentPerson.getDisplayName(),currentPerson.getDisplayName(),currentPerson.getBirthday(),
							Gender,email, Address, Country, City, Phone, State, Zip);*/
					//System.out.println("User deatils: "+db.getUserDetails());
					//User is logged in
					//db.UpdateLogoutStatus(1);
					onSignedOut();
					SendData(userModel);
				
				//  Login successfull Start your next activity
					/*if(PreLoginActivityString!=null){
						if(PreLoginActivityString.equals("upload_screen")){
							startActivity(new Intent(LoginActivity.this,
									Upload_Progress_Activity.class));
						}else if(PreLoginActivityString.equals("profile_screen")){
							startActivity(new Intent(LoginActivity.this,
									ProfileActivity.class));
						}
					}*/
					
					


				} else {

					//                    mLocalListeners.get(REQUEST_ACCESS_TOKEN).onError(getID(), REQUEST_ACCESS_TOKEN, token, null);
				}
			}
		};
		task.execute();


	}


	//protected void ViewUserApi(final String email, String langauge) {
		protected void ViewUserApi(final UserDetail user) {
		JSONObject jobjData=new JSONObject();
		try {

			jobjData.put(Constants.KEY_ADD_EMAIL, user.getEmail());

			System.out.println("user email: "+user.getEmail());
			System.out.println("pro pic: "+user.getProfilepic());
			//setPic(user.getProfilepic());
			jobjData.put(Constants.KEY_ADD_LANGUAGE, "fetch language from db");
		}
		catch(JSONException e)
		{

		}

		System.out.println("GET INPUT "+jobjData.toString());
		//showProgressDiallog();
		CustomRequest jsonObjReq = new CustomRequest(Method.POST,
				Constants.URL_BASE+Constants.URL_VIEW_USER, jobjData,
				new com.android.volley.Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {

				System.out.println("GET USER SUCCESS "+response.toString());
				//hideProgressDialog();
				if(jpObj.IsJSON(response.toString()))
				{
					try {
						if(response.getBoolean("Success"))
						{
							UserDetail userDetail=jpObj.USerDetailsParser(response.getJSONObject("Result").toString());
							System.out.println("EMAIL 1: "+user.getEmail());
							System.out.println("EMAIL 111: "+userDetail.getFirstName());
							//mCfObj.insertEmail("ftsmob@gmail.com");
							userDetail.setEmail(user.getEmail());
							//setFields(user);
							//mCfObj.UpdateUserDeatils(userDetail);
							if(dbClass.loginCount()>0){
								mCfObj.UpdateUserDeatils(userDetail);
							}else{
								mCfObj.SaveUserDeatils(userDetail);
							}
							 //User is logged in
							db.UpdateLogoutStatus(1);
							
							//Dismiss progress Dialog
							h.removeCallbacks(r);
							if (pd.isShowing() ) {
								pd.dismiss();
							}
							pd.dismiss();
							//Switch to Profile page
							if(PreLoginActivityString!=null){
								if(PreLoginActivityString.equals("upload_screen")){
									startActivity(new Intent(LoginActivity.this,
											Upload_Progress_Activity.class));
									finish();
								}else if(PreLoginActivityString.equals("profile_screen")){
									startActivity(new Intent(LoginActivity.this,
											ProfileActivity.class));
									finish();
								}else if(PreLoginActivityString.equals("mystory_screen")){
									startActivity(new Intent(LoginActivity.this,
											SelfieVideos.class));
									finish();
								}
								
							}

						}else {
							JSONObject jobjData=new JSONObject();
							//UserDetail data = new UserDetail();
							try {
								jobjData.put(Constants.KEY_ADD_FNAME, user.getFirstName());
								jobjData.put(Constants.KEY_ADD_LNAME, user.getLastName());
								jobjData.put(Constants.KEY_ADD_EMAIL, user.getEmail());
								jobjData.put(Constants.KEY_ADD_PHOTO, getProPic());
								System.out.println("photo url: "+user.getProfilepic());
								jobjData.put(Constants.KEY_ADD_PROVIDER, user.getType());
								//jobjData.put(Constants.KEY_ADD_PROVIDER_INFO, user.getToken());
								jobjData.put(Constants.KEY_ADD_PROVIDER_INFO, user.getType());
								jobjData.put(Constants.KEY_ADD_LANGUAGE, "fetch language from db" );
								/*jobjData.put(Constants.KEY_ADD_ADDRESS, "");
								jobjData.put(Constants.KEY_ADD_CITY, "");
								jobjData.put(Constants.KEY_ADD_STATE, "");
								jobjData.put(Constants.KEY_ADD_COUNTRY, "");
								jobjData.put(Constants.KEY_ADD_LANGUAGE, "");
								jobjData.put(Constants.KEY_ADD_PHONE, "");
								jobjData.put(Constants.KEY_ADD_ZIPCODE, "");*/


							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							JSONObject jobjMain=new JSONObject();
							try {
								//			jobjMain.put(Constants.KEY_ACCESS_TOKEN,"111222333444");
								jobjMain.put(Constants.KEY_BODY, jobjData.toString());
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							System.out.println("DATA FROM "+jobjData.toString());
							System.out.println("User Value: "+user);
						//	mCfObj.UpdateUserDeatils(user);
							
							
							AddUser(jobjData);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, new com.android.volley.Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//hideProgressDialog();
				//Toast.makeText(LoginActivity.this,error.toString(), 2).show();
				System.out.println("GEt FAIL "+error.toString());



			}
		});
		//		jsonObjReq.setTag(index);
		jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
				30000, 
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 


		queue.add(jsonObjReq);

	}


	
		private void setPic(final String profilepic) {
			try {


				if(profilepic.contains(Constants.FACEBOOK_IMAGE_URL_BASE))
				{
					Bitmap b=Picasso.with(LoginActivity.this)
							.load(profilepic.replace("http","https")).get();
					strDefaultPic=	mCfObj.ProfileBase64Converter(b);
					//token=strDefaultPic;
				}
				else
				{
					Bitmap b=Picasso.with(LoginActivity.this)
							.load(profilepic).get();
					strDefaultPic=	mCfObj.ProfileBase64Converter(b);
				//	token=strDefaultPic;

				}
				System.out.println("pic 111: "+strDefaultPic);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			//		 Picasso.with(this).load(profilepic).into(target);

			/*AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... params) {

					String token = null;


					try {


						if(profilepic.contains(Constants.FACEBOOK_IMAGE_URL_BASE))
						{
							Bitmap b=Picasso.with(LoginActivity.this)
									.load(profilepic.replace("http","https")).get();
							strDefaultPic=	mCfObj.ProfileBase64Converter(b);
							token=strDefaultPic;
						}
						else
						{
							Bitmap b=Picasso.with(LoginActivity.this)
									.load(profilepic).get();
							strDefaultPic=	mCfObj.ProfileBase64Converter(b);
							token=strDefaultPic;

						}


					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return token;
				}

				@Override
				protected void onPostExecute(String token) {

					System.out.println("POOOOOOOOOOOO "+token);

				}
			};
			task.execute();*/


	//	}
		private String getProPic()
		{
			System.out.println("pic 222: "+strDefaultPic);
			System.out.println("strDefaultPic: "+strDefaultPic);
			//String ss = "/9j/4AAQSkZJRgABAgAAAQABAAD/7QCcUGhvdG9zaG9wIDMuMAA4QklNBAQAAAAAAIAcAmcAFGhwSEhVaU1ZZlAyUHNUQjZORExBHAIoAGJGQk1EMDEwMDBhYzAwMzAwMDA4YjA2MDAwMGY1MGEwMDAwY2IwYjAwMDBkNjBjMDAwMDM4MTAwMDAwNDgxNzAwMDBmNjE3MDAwMDE4MTkwMDAwNTYxYTAwMDA2NzI2MDAwMP/iAhxJQ0NfUFJPRklMRQABAQAAAgxsY21zAhAAAG1udHJSR0IgWFlaIAfcAAEAGQADACkAOWFjc3BBUFBMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD21gABAAAAANMtbGNtcwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACmRlc2MAAAD8AAAAXmNwcnQAAAFcAAAAC3d0cHQAAAFoAAAAFGJrcHQAAAF8AAAAFHJYWVoAAAGQAAAAFGdYWVoAAAGkAAAAFGJYWVoAAAG4AAAAFHJUUkMAAAHMAAAAQGdUUkMAAAHMAAAAQGJUUkMAAAHMAAAAQGRlc2MAAAAAAAAAA2MyAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHRleHQAAAAARkIAAFhZWiAAAAAAAAD21gABAAAAANMtWFlaIAAAAAAAAAMWAAADMwAAAqRYWVogAAAAAAAAb6IAADj1AAADkFhZWiAAAAAAAABimQAAt4UAABjaWFlaIAAAAAAAACSgAAAPhAAAts9jdXJ2AAAAAAAAABoAAADLAckDYwWSCGsL9hA/FVEbNCHxKZAyGDuSRgVRd13ta3B6BYmxmnysab9908PpMP///9sAQwAGBAUGBQQGBgUGBwcGCAoQCgoJCQoUDg8MEBcUGBgXFBYWGh0lHxobIxwWFiAsICMmJykqKRkfLTAtKDAlKCko/9sAQwEHBwcKCAoTCgoTKBoWGigoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgo/8IAEQgAyADIAwAiAAERAQIRAf/EABsAAAIDAQEBAAAAAAAAAAAAAAMEAQIFAAYH/8QAGQEAAwEBAQAAAAAAAAAAAAAAAAECAwQF/8QAGQEAAwEBAQAAAAAAAAAAAAAAAAECAwQF/9oADAMAAAERAhEAAAH1DiluzkZfy7Re4PMPnbNw2TYVsUEXoEJlMpU1jwNjF1DJm4MphDKgM3zRg+FKoWrfu3jGSHE+DsLY63UXJSZ0POyHoVFSxR9LzuvNFEa8XUJRAGlVNc20Tml5AtcA0ONZppf0uRUIwWNcasEDNiGTqmk2MBoDeLdZAPHY1VVk9Q2W0h1aYHmaxVnJBrAHU5y3NwrXhhprUuE2RpqtBF5lGNZ8WmcdUsslb0jSFmCNLnssm7fNhVoqq1A6NJpa7WMWWOPnQdI+loeAtJ9Xr801U/cl+f3o9+PFegeFlZtL1NvF7E1uZuTeK1o8jg7R9Qt8120ekrja4WGQQMMZnJ+Jorbj3OREItHkjQMcMNJisdSLZa2k77Hh7mmtnYQGauv5P1LR63gmnEWFbliiGJ7gRJbo1quxRMRwESJFLw+ZDrqs1FmlXkOuLsFlaSwLek8tvXi7WKZqFXOaGSsInq1bvZFp1ehFgmy5RcbP9bLJgyutWDKCrIyoG3SNdJedMB5SOjm9WZFrlqOWWJavd8aUavKs8WbDh22Qy1pURcHoV7KXSLaU6pd88HN2xvs4c0vW28yLLXaw6bOmd2k3MVcSsKX3/Lmb9bVY+Ovkiu33zxXNBlGU04xFZNdrp083O7NLHwPa+L2zju7WJjoCfTec35pXh7mbwA64XIdRQ0VpVu5jbXXpiVgsDHxoAfRcKQWAR8B7fxHZnHdHREx3DYOtpZ3n/Q/Kew56pas41XrQEVvzRIvWCtDUCtrjZMXujqTVGD4/0fm/Qy6JjVd3cDWpl6mHRo+jxtrlUQTokVSyAyVXY3S9noOrQWQOSTIbcNIswqHz8Ex6eHR3N93cDOrla+O/pX85viCxaEihKBHRW1L/xAAqEAACAgIBAwQCAgIDAAAAAAABAgADERIEEBMhBSAiMRQjMkEVJDAzNP/aAAgBAAABBQJGmFYAYlbifcPQGfceqBcRHEMKxVgg6Zh+iYWm03jtB0DYm8V4LYrgnbMzPIn3DA0HmGZm4gyRmE+HMYwtC8LTHUKxFqdtUeBcruyxHG/cBgSOxQo25+o65laZnbQTbEsfwLNg6mMIZn202axXWwW8ddUcrGfbrRvlrpW+jh9oIGj+YfktqsZh653i0uQgLx95ZTqdRHpZfaLWwT7KnVRZh4KkitLLPIsz0UZhOJZqRSGNrHWM40U/r7ObG+rkxMdFpyrVsvs+xiJjcsImc2qYCVO+Yuw6KJrGjLgCyKTFfMx4KkTEQkyp8yyktGQqYMQiCtiHACKqgOphrMSvBZ8QfIfUZhjbzYcz+6WACZEG5PmHWWDAW3UpZmZVh2RO3NCIrzuiOPjW5UZzNsS3yEfUNdDfle6MNZ5Fgi2hZVy+K5s5XGU/mcfC+o0tYuGXRYKq52VBOZ5M0xCkxPjMRubUjW8gMK7kgAdnqWHjNPxnz+LPxGicNpkQ2DPczNpVdZXE9T5An+RvieochZR6nU4bm0LLPUqVh9UxG9S5LSvatbfUyhTm96eoJX2g0R2ren1S5APVW2r5/HePy6Mjn8bP+X46ndZ+ufDOqmdsTtmdozttNWEEDGZnp2BdyuWHhnLdlp4FtggA6YmSICZgzEcGazOsctqjNgW4UWZgbIFpz3Y1k2ExmfmWFnNhFl96FuRYwSzZkbA+58YSgndTHeitAwh2BO0JeFmMGDBifcxrDKE2VL1351osnCrYuKX2tHxFeC//AHzOZmNNcwLM4mTMzZoczVQT4gn94gMf9VdnbERMQ93BJQs2ZvoKW/dW24GQCpmmOhaZmYGWb5jGIMhlKgbRk8+Zx0XiI9jWuqzcKH5KMLG1HlpcMVyh9TXYYwUjys+JDuVYEb6kxa/KKJgzHy+WYNls49K8ZOTabGV9V7mEFLavqSLu9WB4sTYOpUq2Jxs6krjQiCokJUYq46N5gGse8iAsxZFhOjcCoA8u/Y1uzWV8UleHyOMKeXzG5NrHLcPDV6zAnIekDGZxWJQjSZ8Pedu8ZVcCdvHfYSyyBoDgF8zjoLbLrdiwZyoCAsJyvDdBO/bGdm6cFfgR2+Q7eS/lmyMnKNk02BkTBa36gxhWE3EJOW2ENkzHpHa93GX/AF7/AC4UrLTmAyunZauKkFQUHhWsx4NjRPTyIOF4HDi8fENJM7GB+Nk/jLORV+j21jZ628ucXnj2EfgWE/gWz8K7CcfkCVCwNjpgzBmDPMwZgzHTleOL7amCTv8AyDZdRMQqJjz/AMA8zWDAmMD1P48L21LsxrWcbjrbbgRh4AzB9+Jr7M9czxPqHzPWP/N7eP8AzM4H88eAczOBjp/f9ETEAhmTB5MEJGfV79m9vH/nPT1/Us85mDBHMbMUGMuZqMYXqD0yuLm3t9tH82nCwOJtNjAfP9eITif/xAAjEQACAgICAgIDAQAAAAAAAAAAAQIREBIhMSBBAxMiMmFR/9oACAECEQE/AUJ0bY7OjvF+S/o4+0bC+Sja0QkUMb5NrxZOFdY/JZv0ddFl4UaxrE7PrFwOCZo0Kr5KNUyq8bGpY2WGkRVmo3T5LOCjjPBqjU0IwOL5JRh6NDQ1Pr8Yxsl0QiqJJHeGvKC2G1EciE/Q1wdF4USizs7EtVR9f+kuOCLpn2ob2PWFLFMUWUzaRtMnfvKKs1Yl/POeYwbQo6+FFFZl3n4v1H40N+Xx9eP/xAAkEQACAgIBBQACAwAAAAAAAAAAAQIREBIxAxMhQVEEIDAyYf/aAAgBAREBPwFocbNCjg5ODkaKKKzLxwKfqRq0PpWatMnGixf6JJoqucash1L8PFxeGyvaPL5KKESlsyjeRVC6g/IptG6Y79Fik0XfJRWKGor2bQXs8PhmrwrJOuTcitkNMtlsuRqhxPIpyF1Wd0k/pRvJHdO8d1nfy8r6ORFsli8Vlj8CV4cRM5JcnPss2YoM0or6cjfwj9GrR2hLUkvIojgM2iOaHJGyNonSd5kSdClEk79jLxZZZ+P/AFzKSTOtJP8Ah6KqGepyT5/a8L9J8kuR5//EADQQAAIABAMGAwYGAwAAAAAAAAABAhEhMRASQQMgIlFhcTAygRMzQlKRoQQjQGKxwXKCkv/aAAgBAAAGPwKqKUe/U4Sv6Oni08C+M0qC6lUUkTZMqUY08aHGeVHTw0tBqIns7lNyjoWqT3KEjLAuErcSKcWDpRalCq3rLckxSUjluzxWXDrg5WwzaPGeaRVbtULB7ti0zNrjKR5cHC/TGRJk9SuPAy6mWJxMvTCbxpjTC5RIthxFMPNQky5fGqOFHEsKlESaeNsaIko1/sS9spnv4SU2l8zROFprmiqwmmz4jUqy4qHykyUWZd4RccCh55iu12f/AEUaaJFGjzIrGUjOKLGxY/Liih7FcsXdDqvoeefoS2vBF9iu0hL5uxw7L6slDlh7Iz/iG49r+7Q4TK3UW1gWWKcolgooYnC+hxyj73OLZrL0Z53C+qPewnvPsXifoWhKwwlzUptGeZfQ86KNMqtx7SO0CHKeDyXdCLZbSKL2crN4X39SkI6Mm7lVLcuKdSzOGIex2GXL8URSOTKxfY4opiTElZFYfXCrNZ9MLFi6l2OGJHUuVRqcOFCOKLyQoSls0p6oybBf5R/MROTyw3KRcJWpMUvAoURaRXUpM1WF68lhJKZ7F3vEShgzMnqNQ0zOxJeuDcp8hRRE07nLCv33bzLYTsyeeZQhzebD222978MPIcvVnUrEonyQ1lcJKTW4izkXl2PMnCUJOCSEq9zVkqfQ5ssx8UcLPzI1EuRSg80R7Xa+80h5HVkqEp30RmeyyQ3m2TjjX0HSmNcIepcq5o1uLNp1LKeEoJQpW6nUSp3QlC0/6JTmZB/iNqqJ8Cf84SghzMzRR7GfJsjmsu2hd+gm6QqkK5YZdVjXifJFEUOJCZwsoxJkzTF1EhJxUu+xJUhVkckSwzw633POziibwjilyRS2ouR0KFXIvhWxSSRQX3JnJ41nhmbn238qq7yq/X0JQw3RW+EhRROhclBJE5wlYoCu0X0KxT9DzfY0maYTbRpMj6LeSJRRNQ3neQqyauidJsq0j4fqV/ku16nH/Hh7Vv5d5vWQmlOXzaip0Elcrh/Xg33Iurlv2ErJVNcfN4mpAv3eBEWJLCxVV37YXwqQ7JfDV+BE+pLCuPMlM5dzmUqa0KIvTCw3TqRRc34EPcnhz7lDuSP/xAAmEAEAAgICAgICAwEBAQAAAAABABEhMUFRYXGBkRCxIKHB0fDx/9oACAEAAAE/IaKoY2r8DOt9QRVyzRliXEJYaFzl+kbcjKFRXHHuUfgpqEfVM0yQpD8WkKJxpV2ZnJaDdM2iNmIW5lFmXTUBeCog5zFS6ijVyzyShcpg1+JRMv4UiseomZbLkHS47835QRAWHYxjENArTMIRviBkZCTPBpwcwBMQAYxKt7xbU9EMiCdSuEMoblCfcr+IsdfhcqVKjuyoohTpiVocXB0WGFa7phiXEtIblpm0aXY8SgOQY0ICZipnXcrlVKo8PRcIYDlxCVXtEyK+ZvhC0aluI/w3MqVKltQVMMs/wJUqFdThN+4GrZcTpLli4zKa4gY1LhYDMBRpGl4eoR0szmFq5hsWCck9PMERzbRpl4B/1/CpTsW4qAW4dypUqYoBhV2QBEa8xRV4jIVh5i1TMKsbMDKWmoiFsA9EBdgKcQYbzcLuldqK5SfiGU+pd0O1c2h+HYSO93EVSnhKkaZUNsqepq+ln0YRMVe4ZsL/AFHa95DPcfGPiHuMpZeGHRHZWMDtqXSDGZmbwV7l/wC3cFgLnhlKrw8wLCz6g3awnEEgZUVovUdsRzZfUamIpWfuUheyCfTNIK9ys5O4G92Mpoal6LCLqCUrkg+ScgM2dETuc0SgE64XAdHwzXyTZavcevhsBlU16WyOZJqcP7IBgPhlWIBKuH2cpcBIE2D+4qM3TvEK7WepyI9qH3EoQyUZlKqfpL12gbijt3+BrtKJzcMsLKdV9Exbh6dQvx8rg4X8tlBKz4GEDfWkesq6RBnstxUj3isxDCmabvEc4WYDPAFEyqx4hEwLeC5XwXoZw3m6uUQA85VSoEeHCF/BMkMD/wBvuYZ++GpX3aotFTsm7s9RaaXdS3hQ6vEes9MuySeX9xpqjqV/tI7D8ZljX1LHEJxozvniVD5LiuZomMjUZtm5gCPEVFKrD3DkI/M4QsS5xHkZhzcW2DK8C83P/rQVSNa7g9HlmDZ+7BwuUucnEIs2d9Qf/UAHyfU7AzVnMKLt+ajXBa6bWILfglJ+WkpgC7qolDy1cepmAuJ2n4QC1QsFaT1HlDwRGcLP/JiNW9x1G11pEzMrhlTd10YPubqnncAN9O+JRrwqXpt9RDsq2WTDHqZpbVcvUV3y4j3GL9F2P9DglGgrf6INNUHY/ole8l6gEzmy408S26+JhdrjuUtXXUst0dytjRG7KoYGb4jVsvEuMAOxH5KcTM2+w/2ZAl557j5AfJAVyxVAJah+UB31NTLF8nmqlSAQu3iXwWKmZoTCBMjmWnpHiDOf8RDeZ0M9qeInJ4mXruIOYrh3zCzaJlAaOG4Ntew4+JkBn6v4j0Q93iNfYGStRtdv1NpWZ/7vmX0ctwyYW2rhjqyXl7Vz7xkhwTEQl5zhz6jOz3En3KbFc7KSXkR8XNBhwRGi34T0DxOAVfSAMUEFwIXxEYEe3ET6yoJjP3lj2ut38Q2Glt/7mLc2k9nZqVsA1rzUGMNTSU8DERPkyL8SkOBq4RGKZShEv5izKXb1MNw6iJV5eaInoyw9RwM+FwHceJmuIAvIgKggljSw7leQD43DimL9rHBwnmFYy7cTTIGZFs3ieQ4swEWCEujb4COwYym/S/uYkX4D3yzNX4jyGbUDAiIhkTIuLqUF5unuMrSuK5giwldQQwP6gFNC7siw54j/ANE0U9GIlt5OOJYFBjlmsKc+YhRxrBKTBu1yCNUaRcErfyysPwhlarVPykbFHxAij75/Zk/hjIWgU5NsM1AtdUuZUe2WMoxv2ZkFXuUVW26lwKQbsggC6wzMmHsQWb3DLj/cqIKxkzls/wBS2gbOKyzGeajFvjLmV1yaxM6zh5lhSVr+a4HixZL2OBEDjGlCs7nKGmE4JcCqoo9TOQQBdrMs7NHcU5HrM5iG+oVQPM5c+FNfau4qbS/1lgbo01NhZ75nEf65mbY+DMeb2RBBC2P8fL73UJCAWKr4fQ+5i/DBQeIj0ncyzXuL+n2h4s0EP5q/CPLPrWAMVmcdRoxLPiddSpadywyxzrETzOIC38nGvQYuNc5NKwcWRXo6BiYOAqpc6TgQpk0QmOY/uepc/wDVD3/UPc7y4nR/1PKDB+2IwH3H6F/KFLKOsSfMpH53U7GPuWN1CW4byRuuwlq6iVHGxi+JS57+4GCjUVzgebmwrMZAy2oDUdAv/wCY/wAf1/hi1XijxLbYd9S5svDmOAlIwd8b8Tyips/7KFFZ9TiLfTOTjEd5r5mGmk4j7HOJkZojjl4IGsrBBtXQ8zgH9j+W71HUbLS4DgVGlDdaSHSlmHNcanc48wKsQcywfoOp8Dz/AIjsu+OOJqJy44glcuF1M6Qt5mbsX+YmDmvqWAm3cS4oy1RFb5n+X64MSoJlTZDwV3M+Lzu5dYFENdndytDhnOoCsqVutz//2gAMAwAAARECEQAAEBtEQLZ94MPMq/6ZWQLDaBJIA1WVl6fyEWGtzkg+A4gYmMyVRWEok5LXiG4LfRHhYbH4ZwSw6KTPIKJacwbnQzn6rUw8Fn/okH6ZUJV7hqNrc5R4M/g6XLWH6OJ1F1zRkdhL8tD5a5XK5jErr9tAb5VPCScehMeR7MS3PFkkloP/xAAfEQEBAQEBAAMBAAMAAAAAAAABABEhMRBBUWEgcYH/2gAIAQIRAT8QeGMMC2/Zc9R+Lk4RryG3tts/pEXqT+MhOcZRljTyBP7YXW48lP4gDSXm2YTsywdnvsGsNNQiyOHsBnvLLksfdaCfzF5W7p7bty0xxY3SYgPLS0tGGF4Ej6tziX8JB6smDgsnjNQD7BMb8M/bAfIFMpfL/SZOQjHmdi9srOfuD9fDCRvu3YlthwEADbtbBrPLT2MPPgVn2ZVyFwk+pvVsi6p0X/JPi76WsFWEeKNOubPWnLhOuzF/JBhHsZZkD6jfYMy2lr4ZfuFq+zLD2AW/G22y7nzohkWNp8bay0XqD9jvJw+Tt+O2WfcjZBwlSPH/AA959lQl5Df/xAAeEQEBAQEAAwEBAQEAAAAAAAABABEhEDFBUWEgcf/aAAgBAREBPxDb1N7SiYauT+o2dmHufBqxj+yRoi9HYZj0iW7ch7Ov5aDDwZj7LWTDcsPkqjbTjHPXgQP0kDYbOSi47ADAzk/C0r9bPYXNfVk9yx9Wsx8Cp7NbDyBCdMhrDmg+iZ/aNOZYydz+iDUhdGQ92Uw7cvoM5v1wPZH6brBvSf4YQe5T6sDts5nj1b92h7tMsEZdsWEEwtNyPgsX34Oeth+DenZ8be42b6sTbtOCQYIDqcevBhxb8bQdXtrDCPNzoX1hHL5fsO6kOcljjdt2f9vnukXmfK+WcsWbfFEj2Zrmtq/6tQdL++cEygFvbYSzfdmdg/Jz7HTJSH/Cy6Qk8g7JKzlv27cszn+P2ww5bLf/xAAnEAEBAAICAgICAgMBAQEAAAABEQAhMUFRYXGBkaGxwRDR8OEg8f/aAAgBAAABPxB2gPARwJ3Fw5KE84PsnzkVP6cGFnWecPnCKg4Cq65wEtdxMgyj5e8DU7y5nLNiCHWaS4Pzgg4h7yvGO1L4OEcAwjHlYMmj5xe3WE6uLVlm/wC8el+Q4sQk5TrN5pO8CRfjnNxt0zLibOoY0hTyuPMvNrR6yCR4HeI20DfrIGk3+sDZc51yJlkISxxVHPrFDbMDbzgVHI955eByuClv8OsUM34/wLSREDvKLGremUkLKnWbZnYa34cmBB9V8YyEbCeHPywGUjvd1lae0PZlyAUFefrNOg7mCuhGGJQOQjlkUm1Llu3HpDN2WbRwKaTSesUpsGk7wtWmOnHyw98EYkMPTIxBdpxh5OadmUDNwqEzZmKjghegpO/OUqW5V5ec3mHa4/8A3BgRU2dYobI2JMTSVdnGUxx1TnKjTFNHaB7wqS8ydYHqyLNn3hqLdQbEyeoUryrieRYA495AEixVJrxkbLhIL1l5Vc5sRFHxZxyc4ZoRdcYqZqgpxmzQXuGPDDBotHzMAceqhr5YiFaPaZdQlEVvGHPQZb1iSGmtmaIvWNtvHVwAB5Vw0DF5wEUCJzgEbaRoDnAYFd+HKUBG01vNnza7MKFA8ulHNnzXnGUzYwgf+uccOuXZjpJ/OPqHK4c45eX3gbpTbLvHagxSK7NJ7zUVZqO8Rg4tuPGsQk0tMbJY7HA0BejeDujc7wQz3W4k2FrrWDegxw1hPJab6xpzXXWa/B1DDsWlpwMsgdtXrHe9MHB6MsXNzeONtkFFZMMJRdt3CCYCSDMrKhTD1x45rCxpElbXE6bEX4+c4RCQRPd8Y07hgmEBong68fOK4u+rrBqQNByF2N+HHT0Ne8I6HGywzgpPGKQxq7wqgajgFWjzlgkNi1lQr7r/AHid8cov7wArgituGQi4YxFGiCcucOYKcnnNM8UDhkZrvtM/trhhpFfEypBDetjFMClQOcvG501pPGRh4ddM+KJyHNAfBzRDdGY6vz0TWAx+jpDEZFvLtfrJgRqI4CTT9swaB4i2ZdCKm3vOIB5XebVFAH8zrJ5p4aflQx1dL0qvxLhRSz81Bs+8O/43DEvYzWa4ou6aMKFeKxxVrbsRwEC9of7zW2GhvjPjOOTAeRmzzx6tBHg/LiFWvSKTHxnqkv2OPebNQqHsbwesjmT/ADnYmox+snlLkG34yEV/KmERB2jhiEeqONBa9iZpz9aZuQuIgr2STHgCdBcfjEd5xrcyqFpsgvs4xG4cmCvnUw4BeD+GCki7cH1xgevApo+nk+8YVEa0HyaxgePjX5dY5oV7wPmBgsCio9It3hrV3kfoD7nObw7EqfOM6PWC+A/rHAtbZybnTTrTc70bq4UPqKXxmjTxEDxTn7M4TuqQ/kjj5hcMF+FMWqZwlD95RT1f7KZFF6on7zzDjuf6xTHPpQ5N4DjEfj+8ELx8nvNjpWwcnoJeUH9OVAPshb950xeRjOKL282JpfPLr85EoDjWbnSeNc4N8wV4vS/lxvTcGn9Yad7feW8hsgHlHzLnKJbwybaGXjNsWdVySgDXTORL/wA4yGsvMMorc1H/AFjNEPvbjDQxZMB1vIhZ4ogfgxtFtusMvEzFF6TAwrlCCPi9Zq23cdm+sqaNc3n2ZRMltb/eDMVAB+GMAQUgjl79XATQxR/JMa2zDeYhAC6GmadFUJdaXQGz3znhDDF+sok+KB/WCimCBHDFAUOr6wXhLaWYJAOBRD75zz1jevvIoXgXE15Pg/eaB8PKFSHg2ygUcbd4ZowxL9jvDXFtCPxrvBEWFUT+YyPXhQEffDPeaQ1EG3tDRgmaAiB/K95E0Ki2ME2bnc/PWNlKdhZi6LT53g+Ly+jE08hlPip2A/n3ggXUCMeonwc3DaCRJLpPl/jBhcnOj/eeP6KxXNQhoXeGtF+9d/WNx23amjIHdHtvNgqUFcfGKwI0cplR5k6XI0XXjOkNMY1mP+GD/wAyQEJEVz94BsDwC4xd0NU/rKQbFDMMUWWJH4dYhwTT9OSbGLyI+MkQAOE2YAYAJurITvONktDQz8oaxGjMGl0P8YypEOTj18GQxCANnJeE13jqoqqJfUyhVXzlaORTob39Y6aKVHdzZhWv7HnKKjsT+WI0ttG8/fWUBrUgd4vITT8mISbYE1frP+jrDIJ8T+8g6ZsJ08/9M4ZIQ2k/WJjG0SPkecdKLCgD1iouI2A8bW5gCvnBsY+kpAq9UxOkq2azxMFRYQcf+nr5xVk0Chcgg3M2X+nFgZdA3xfPnApLjS1vnH0InLD85ZozLHgYpzmzZ1Bo9tZVUKcK/jDIZTaj9SYoPuAMDsws2WOnB9pQUr8v9Y86RJ49E/WUsASDVvkcsUEw0A+8VBYqhd/eLm8EMJ7XnBN7iR41MakzUaqfB2Y4i3QEX84dFKAEemNlbWUvtP4dfODi+6eMSdTdCq+s5Fi0fIa7494WvsL5vkScTrnAKhFkPocfMxNcWnC8wM07x1jKnPgxuwLp84AAE5U39YdpNiVI1fGMbSc+EXjGgjtPexdlgOHei/X85SFidA9nxg9qgXknmuXmRvV0+9YPNDQPl7brPkIYL0jZ+OM0KMoN+sXC0GV89/8AaxAv4DYOnTuG8OqmVRq9esk2UgUnP06+3rGpHpjIC9Z+R04pjoOXmDVfcxQdwBsNRBJEVNTldF2MBCr5io9vxxi0UAD4MWgCFvaPeA0EM6V7XIzFDbPl4Mpyje1hmjMoRAD8/wB4UreQ1X31/eKjswc3j7yoCHRv6ecRcJ4i9s5McJYHSv33/OAQaNIO7x/GIBgQ7hPXWEkrQ814X844kVVK29GXaE9NB7NbmKGZ01D5v/cYCowbgbvz0e8FRY4oOMjaFy+X4MCpo6XAupl9VhqW8/nvPn/AoQ2KiZqg/bP3gqf4BSd0pOmEYFTmYQURiyYp+N8OvOUMUauXls63kwQXopgiZGxFr4v+8dxUK6fOJ3EALO87+U9nv4yEJCKJr4cdASQ8hdYUizFhGLWLS9uso5YRJiPlYmvwd4jcCQrt/GQ2AaFB/wC5yoTaW/jBliFgjV8XKB7nQfLj/gcuX/AxnSeiICDWi7HO1RQaGiVoGj1M3hbIZTfWRLpgft84t7fd1ghkUSA/OJumjND1O8ceGg3+W3HxDcV28ushrEgX+Ayw3NxjMAXQD/pgoiWwjhOLmu7jdj6mO28nFEe05z4g2NX2ZlbWBCdebjo2LRL+coqseoVfnB/H+blxQJxbH7PGJms0DqiFQ9LBve4oHmJeeMFVDTypTfXPGFoj57y0FI0mn6ZouDrkv5wTGekqHBZINiOHm3P0QDTiIgCigGMrffZr5yhQD9nFSindlmJxrffeH2sXzgK11vc3i2EvcxPB63xgVhH4nWv3jjl/y1dkYA3Tb6v3kLy6u4oFDRr3ixbwPbj73lJujbvROMBUjNccYC7JNJu4aw8hsPWAAkGtvWSbUI6wKVPHAQy1HevHP+sU3qarT+cYqSD0RvOBstksxGALsnVwL1O55T5xTbB5IyZFetboz7yESrUSYcjsaNJa/HGP/wAkt5LMWTj7mKtRD3rr7wGjfQUf85EL4AavzkCoBVaYnQlrRTANeO3V9+MmRDdQ0v7wBUjIF4xFDF4fOJHolqx+8Zl7dLtPibxoqwDaquNsYozSnnzgkkZD+rIqJrFKj98YoNAiJ3/5i2BvS4SeYUdnLvOWv8P+Ra8LBRwCLSWOKf8AzFdhBwfyfWJAFuC1ffE1zkyqLv8AvFhAkpxh2nnnnGIa0j5TOBHiafmZ48xXYfvOEH3Yc4GCJ4Qg4m69gNMmlqCDRkBJ5JRD1gYxTUOcg9r8D11luK7FJPj7x9SdFdjIIkLm6NH0P7x/+XLY18MUDRaFED/3K2ab3vrlyhshIO+sM7HLCKe/jJGp5VT7M2VbDRgH/u8hTBfCP9zGpDISiovP+skt4g3c89M0aDkoaePGBay48vhg0LBek+82JfQM2cdZSNGBo/hcEtpcFDwv/nvIANK1fnrDSSyAAOXFXrV+XX6/+uM8/wBsQGsu8fYSb+MAEaSQcNlr03NiTyAur13MBmRNLD15yFTetin95Pl1C0MBdBK2+R/rP//Z";
			return strDefaultPic;
			


		}
		//private void AddUser(final JSONObject jobjMain)
		private void AddUser(final JSONObject jobjMain)
			{

				if(queue==null)
					queue=SingletonKeyValueHolder.getInstance().getRequestQueue(this);

				System.out.println("ADD INPUT "+jobjMain.toString());

				 
				
				CustomRequest jsonObjReq = new CustomRequest(Method.POST,
						Constants.URL_BASE+Constants.URL_ADD_USER, jobjMain,
						new com.android.volley.Response.Listener<JSONObject>() {


					@Override
					public void onResponse(JSONObject response) {

						System.out.println("ADD USER SUCCESS "+response.toString());
						
						if(jpObj.IsJSON(response.toString()))
						{
							
							try {
								System.out.println("responseeee: "+response.getBoolean("Success"));
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
								try {
									if(response.getBoolean("Success"))
									{
										UserDetail user=jpObj.USerDetailsParser(response.getJSONObject("Result").toString());
										user.setEmail(user.getEmail());
										System.out.println("EMAIL 2: "+user.getEmail());
										System.out.println("EMAIL 2: "+user.getFirstName());
										if(dbClass.loginCount()>0){
											mCfObj.UpdateUserDeatils(user);
										}else{
											mCfObj.SaveUserDeatils(user);
										}
										//mCfObj.UpdateUserDeatils(user);
										//mCfObj.SaveUserDeatils(user);
										 //User is logged in
										db.UpdateLogoutStatus(1);
										
										//Dismiss Progress dialog
										h.removeCallbacks(r);
										if (pd.isShowing() ) {
											pd.dismiss();
										}
										
										//Switch to Profile page
										if(PreLoginActivityString!=null){
											if(PreLoginActivityString.equals("upload_screen")){
												startActivity(new Intent(LoginActivity.this,
														Upload_Progress_Activity.class));
												finish();
											}else if(PreLoginActivityString.equals("profile_screen")){
												startActivity(new Intent(LoginActivity.this,
														ProfileActivity.class));
												finish();
											}else if(PreLoginActivityString.equals("mystory_screen")){
												startActivity(new Intent(LoginActivity.this,
														SelfieVideos.class));
												finish();
											}
										}
										
									}else{
										//Toast.makeText(getBaseContext(), "Invalid entries..", Toast.LENGTH_SHORT).show();
										SuccessAlertDialog(getResources().getString(R.string.no_server_response),getResources().getString(R.string.online_title));
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
						
					}
				}, new com.android.volley.Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						NetworkResponse response = error.networkResponse;
						String json = new String(response.data);
						System.out.println("ADD FAIL "+json.toString());
						json = trimMessage(json, "message");
						if(json != null) displayMessage(json);

						System.out.println("ADD FAIL "+error.toString());
						SuccessAlertDialog(getResources().getString(R.string.no_server_response),getResources().getString(R.string.online_title));


					}
				});

				jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
						30000, 
						DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
						DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); 


				queue.add(jsonObjReq);

				/* //User is logged in
				db.UpdateLogoutStatus(1);
				//Switch to Profile page
				if(PreLoginActivityString!=null){
					if(PreLoginActivityString.equals("upload_screen")){
						startActivity(new Intent(LoginActivity.this,
								Upload_Progress_Activity.class));
					}else if(PreLoginActivityString.equals("profile_screen")){
						startActivity(new Intent(LoginActivity.this,
								ProfileActivity.class));
					}
				}*/


			}

		private void SuccessAlertDialog(String msg,String title)
	{
		DialogFragment newFragment = UploadSuccessDialog.newInstance(LoginActivity.this,msg,title,"doNothing");
		newFragment.show(getFragmentManager(), "dialog");
	}

	//Somewhere that has access to a context
	public void displayMessage(String toastString){

		System.out.println("ADD FAIL "+toastString);
		//Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
	}
		public String trimMessage(String json, String key){
		String trimmedString = null;

		try{
			JSONObject obj = new JSONObject(json);
			trimmedString = obj.getString(key);
		} catch(JSONException e){
			e.printStackTrace();
			return null;
		}

		return trimmedString;
	}

		protected void hideProgressDialog() {
			if(pd.isShowing()){
			pd.dismiss();
			}
		
	}

		private void showProgressDiallog()
		{
			pd.show();
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			h.postDelayed(r,30000);
			/*dialog.setMessage(getResources().getString(R.string.profile_progress_updating));
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();*/
		}
		
	

	@Override
	public void onConnectionSuspended(int cause) {
		// The connection to Google Play services was lost for some reason.
		// We call connect() to attempt to re-establish the connection or get a
		// ConnectionResult that we can attempt to resolve.
		mGoogleApiClient.connect();
	}
	public void Revoke(View v)
	{
		if(v.getId()==R.id.btnRevoke)
		{
			RevokeAccess();
		}

	}
	private void RevokeAccess()
	{	
		if(mGoogleApiClient.isConnected()){
			// After we revoke permissions for the user with a GoogleApiClient
			// instance, we must discard it and create a new one.
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			// Our sample has caches no user data from Google+, however we
			// would normally register a callback on revokeAccessAndDisconnect
			// to delete user data so that we comply with Google developer
			// policies.
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);
		}
		mGoogleApiClient = buildGoogleApiClient();
		mGoogleApiClient.connect();

	}
	/**
	 * Google + Specific
	 */


	/* onConnectionFailed is called when our Activity could not connect to Google
	 * Play services.  onConnectionFailed indicates that the user needs to select
	 * an account, grant permissions or resolve an error in order to sign in.
	 */

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		System.out.println("Cancelled ");

		// Refer to the javadoc for ConnectionResult to see what error codes might
		// be returned in onConnectionFailed.
		Log.i("TAG", "onConnectionFailed: ConnectionResult.getErrorCode() = "
				+ result.getErrorCode());
//		Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//		Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient);


		if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
			// An API requested for GoogleApiClient is not available. The device's current
			// configuration might not be supported with the requested API or a required component
			// may not be installed, such as the Android Wear application. You may need to use a
			// second GoogleApiClient to manage the application's optional APIs.
		} else if (mSignInProgress != STATE_IN_PROGRESS) {
			// We do not have an intent in progress so we should store the latest
			// error resolution intent for use when the sign in button is clicked.
			mSignInIntent = result.getResolution();
			mSignInError = result.getErrorCode();

			if (mSignInProgress == STATE_SIGN_IN) {
				// STATE_SIGN_IN indicates the user already clicked the sign in button
				// so we should continue processing errors until the user is signed in
				// or they click cancel.
				resolveSignInError();
			}
		}

		// In this sample we consider the user signed out whenever they do not have
		// a connection to Google Play services.
		onSignedOut();
	}
	private void onSignedOut() {
		// Update the UI to reflect that the user is signed out.
		//btnGLogin.setEnabled(true);
		if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        mGoogleApiClient.disconnect();
	        mGoogleApiClient.connect();
	        revokeGplusAccess();
	        System.out.println("User logged out..");
	       // updateUI(false);
	    }

	}
	/**
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
	    if (mGoogleApiClient.isConnected()) {
	        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
	        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
	                .setResultCallback(new ResultCallback<Status>() {
	                    @Override
	                    public void onResult(Status arg0) {
	                        Log.e("TAG", "User access revoked!");
	                        mGoogleApiClient.connect();
	                       // updateUI(false);
	                    }
	 
	                });
	    }
	} 
	/**
	 * AsyncTask to get the user details from the Authtoken obtained after succesfull login.
	 * @author sijeesh
	 *
	 */
	class GetUserDetail extends AsyncTask<TwitterAuthToken, Void, Void>
	{
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.show();
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			h.postDelayed(r, 5000);
		}

	@Override
		protected Void doInBackground(TwitterAuthToken... params) {
			try {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_SECRET);
				twitter4j.User user;
				AccessToken newAcc = new AccessToken(params[0].token, params[0].secret);
				twitter4j.Twitter twitter = new TwitterFactory(builder.build())
				.getInstance(newAcc);

				String mUserName = twitter.getAccountSettings().getScreenName();
				user = twitter.showUser(mUserName);
				//String username = user.getName();

				System.out.println("Twitter "+user.getLocation());

				String oauth_uid = Integer.toString((int) user.getId());
				
				/*String Firstname = user.getName();
				String LastName = null;
				String birthday = null;
				String gender = null;
				String email = null;
				String address = null;
				String country = null;
				String city = null;
				String phone = null;
				String state = null;
				String zip = null;
				
				db.updateUserDetails(Firstname,
						LastName,
						birthday,
						gender,
						email,
						address,
						country,
						city,
						phone,
						state,
						zip);*/
				
				
				userModel=new UserDetail("TWITTER", user.getName(), user.getScreenName(), user.getOriginalProfileImageURL(), twitter.getAccountSettings().getScreenName(),twitter.getOAuthAccessToken().getToken(),null,null,null,null,null,null,null,null,null,null);
				
				SendDatatwitter(userModel);

				//System.out.println("USER NAME "+Firstname+"\t"+user.getOriginalProfileImageURL()+"\t"+user.getScreenName()+"\t"+user.getCreatedAt());

				
				//Twitter.getSessionManager().clearActiveSession();
	           // Digits.getSessionManager().clearActiveSession();
	            
	            
			} catch (twitter4j.TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
@Override
protected void onPostExecute(Void result) {

	if(userModel!=null){
		new ViewUserAsyntask(userModel).execute();
	}else{
		SuccessAlertDialog(getResources().getString(R.string.ratelimiting),getResources().getString(R.string.online_title));
	}

	
	
	super.onPostExecute(result);
}
	}
	public void SendData(UserDetail data)
	{
		Editor editor = sharedpreferences.edit();
		editor.putBoolean("iSLogged", true);
		editor.commit();



		JSONObject jobjData=new JSONObject();
		try {
			jobjData.put(Constants.KEY_ADD_FNAME, data.getFirstName());
			jobjData.put(Constants.KEY_ADD_LNAME, data.getLastName());
			jobjData.put(Constants.KEY_ADD_EMAIL, data.getEmail());
			jobjData.put(Constants.KEY_ADD_PHOTO, data.getProfilepic());
			System.out.println("Profile pic : "+data.getProfilepic());
			jobjData.put(Constants.KEY_ADD_PROVIDER, data.getType());
			//jobjData.put(Constants.KEY_ADD_PROVIDER_INFO, data.getToken());
			jobjData.put(Constants.KEY_ADD_PROVIDER_INFO, data.getType());
			jobjData.put(Constants.KEY_ADD_LANGUAGE, Locale.getDefault().toString() );
			/*jobjData.put(Constants.KEY_ADD_ADDRESS, "");
			jobjData.put(Constants.KEY_ADD_CITY, "");
			jobjData.put(Constants.KEY_ADD_STATE, "");
			jobjData.put(Constants.KEY_ADD_COUNTRY, "");
			jobjData.put(Constants.KEY_ADD_LANGUAGE, "");
			jobjData.put(Constants.KEY_ADD_PHONE, "");
			jobjData.put(Constants.KEY_ADD_ZIPCODE, "");*/


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jobjMain=new JSONObject();
		try {
			//			jobjMain.put(Constants.KEY_ACCESS_TOKEN,"111222333444");
			jobjMain.put(Constants.KEY_BODY, jobjData.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("DATA FROM sendData"+jobjData.toString());
		//dbClass.SaveuserDeatils(data);
		//		AddUser(jobjData);
		Log.d("here","here");
		//ViewUserApi(data.getEmail(),"fetch language from db");
		
		//ViewUserApi(data);
		new ViewUserAsyntask(userModel).execute();

	}
	
	
	
	
	
	/// ADDING DUPLICATE FUNCTION WITH SAME NAME
	public void SendDatatwitter(UserDetail data)
	{
		Editor editor = sharedpreferences.edit();
		editor.putBoolean("iSLogged", true);
		editor.commit();



		JSONObject jobjData=new JSONObject();
		try {
			jobjData.put(Constants.KEY_ADD_FNAME, data.getFirstName());
			jobjData.put(Constants.KEY_ADD_LNAME, data.getLastName());
			jobjData.put(Constants.KEY_ADD_EMAIL, data.getEmail());
			jobjData.put(Constants.KEY_ADD_PHOTO, data.getProfilepic());
			System.out.println("Profile pic : "+data.getProfilepic());
			jobjData.put(Constants.KEY_ADD_PROVIDER, data.getType());
			//jobjData.put(Constants.KEY_ADD_PROVIDER_INFO, data.getToken());
			jobjData.put(Constants.KEY_ADD_PROVIDER_INFO, data.getType());
			jobjData.put(Constants.KEY_ADD_LANGUAGE, Locale.getDefault().toString() );
			/*jobjData.put(Constants.KEY_ADD_ADDRESS, "");
			jobjData.put(Constants.KEY_ADD_CITY, "");
			jobjData.put(Constants.KEY_ADD_STATE, "");
			jobjData.put(Constants.KEY_ADD_COUNTRY, "");
			jobjData.put(Constants.KEY_ADD_LANGUAGE, "");
			jobjData.put(Constants.KEY_ADD_PHONE, "");
			jobjData.put(Constants.KEY_ADD_ZIPCODE, "");*/


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jobjMain=new JSONObject();
		try {
			//			jobjMain.put(Constants.KEY_ACCESS_TOKEN,"111222333444");
			jobjMain.put(Constants.KEY_BODY, jobjData.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("DATA FROM sendData"+jobjData.toString());
		//dbClass.SaveuserDeatils(data);
		//		AddUser(jobjData);
		Log.d("here","here");
		//ViewUserApi(data.getEmail(),"fetch language from db");
		
		
	}
	
	
	
	
	
	
	
	
	
	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			
		}
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			//btn_facebook.setEnabled(true);
		}

	public class ViewUserAsyntask extends AsyncTask<String, String, String> {
		UserDetail data;

		public ViewUserAsyntask(UserDetail data) {
			this.data = data;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd.show();
		}

		@Override
		protected String doInBackground(String... params) {
		
			setPic(data.getProfilepic());

			

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ViewUserApi(data);
		}
	}
}
