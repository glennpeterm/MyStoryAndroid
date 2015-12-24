/*
 * Know My Story 
 */
package com.tabview;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.allchannelvideos.ChannelVideos;
import com.analytics.util.SendAnalytics;
import com.database.DatabaseHelper;
import com.database.Model;
import com.dialog.DialogActivity;
import com.dialog.ErrorDialog;
import com.dialog.MultiSelectionSpinner;
import com.dialog.ReloadDialogActivity;
import com.dialog.UploadSuccessDialog;
import net.onehope.mystory.R;
import com.scriptures.ScriptureOfflineActivity;
import com.supportclasses.Constants;
import com.supportclasses.JsonParser;
import com.test.socialnetwork.HomeScreen;
import com.videoeditor.util.FileUtils;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dialog.TransparentProgressDialog;
public class Tab1Activity extends Activity implements OnClickListener, OnFocusChangeListener, TextWatcher {

	EditText editText_enter_storyname;
	TextView textview_select_topic;
	MultiSelectionSpinner spinner;
	// public static com.supportclasses.Constants Const;
	private JsonParser jparser;
	private ProgressDialog pDialog;
	JSONArray topics = null;
	LinearLayout ll;
	//MultiSelectionSpinner spinnerView;
	static TextView tv_select_topic;
	Button button_next;
	Button button_home;
	// Database Helper
	DatabaseHelper db;
	SharedPreferences.Editor editor;
	TabViewActivity tabViewActivity ;
	 TransparentProgressDialog pd;
	private Handler h;
	private Runnable r;
	ImageView ivMandatory_storyName;
	ImageView ivMandatory_Topic;
	
	TextView tv_heading;
	public Typeface abel_ttf;
	SendAnalytics analytics_conveyer;


	ArrayList<String> topicList = new ArrayList<String>();

	String URL = Constants.URL_BASE + Constants.URL_TOPIC_LIST;
	HashMap<String, String> hm = new HashMap<String, String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.title);
	
		analytics_conveyer = new SendAnalytics(Tab1Activity.this);
		tabViewActivity = new TabViewActivity();
		initializeButtonObject();
		addOnClickListner();
		
		
		h = new Handler();
		pd = new TransparentProgressDialog(this, R.drawable.spinner,getResources().getString(R.string.dialog_loading));
		r =new Runnable() {
			@Override
			public void run() {
				if (pd.isShowing()) {
					pd.dismiss();
					Intent intent=new Intent(Tab1Activity.this, DialogActivity.class);
					intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "no_network");
					startActivity(intent);
				}
			}
		};

		db = new DatabaseHelper(this);
		editor = this.getSharedPreferences(Constants.MY_PREFS_NAME,
				Context.MODE_PRIVATE).edit();

		ll = (LinearLayout) findViewById(R.id.ll_spinner);
		setInitialvalues();
		ll.setOnFocusChangeListener(this);
		
		ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				TopicDropDown();
			}
		});

		String finishStory = getIntent().getStringExtra("FinishStory");
		if(finishStory!=null){
			if(finishStory.equals("FromFinisnStory")){
				RestoreAllvalues();
			}
		}
		//setInitialvalues();
		// TopicDropDown();
		setFontStyle();
		
		
	}
	private void setFontStyle()
	{
		abel_ttf=Typeface.createFromAsset(Tab1Activity.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		tv_heading.setTypeface(abel_ttf/*,Typeface.BOLD*/);
		editText_enter_storyname.setTypeface(abel_ttf);
		tv_select_topic.setTypeface(abel_ttf);
	}

	public void setInitialvalues() {
		String Title = db.getTitle();
		// if((Title!=null)||!(Title.equals(""))){
		if (Title != null) {
			if (!(Title.equals(""))) {
				editText_enter_storyname.setText(Title);
			}
		}
		String Topic = db.getTopics();
		// if((Topic!=null)||!(Topic.equals(""))){
		if (Topic != null) {
			if (!(Topic.equals(""))) {
				tv_select_topic.setVisibility(View.VISIBLE);
				spinner.setVisibility(View.GONE);
				tv_select_topic.setText(Topic);
			}
		}else{
			
			tv_select_topic.setText(getResources().getString(R.string.select_topic));
		}
	}

	private void RestoreAllvalues() {
		//Restore all values from databse and show.
		//Also make other pre-tabs selected and on moving to them please restore thier values also.
		
	}

	private void initializeButtonObject() {
		button_next = (Button) findViewById(R.id.btn_next);
		button_home = (Button) findViewById(R.id.btn_home);
		ivMandatory_storyName=(ImageView)findViewById(R.id.ivStoryNAme);
		ivMandatory_Topic=(ImageView)findViewById(R.id.ivTopic);
		editText_enter_storyname = (EditText) findViewById(R.id.enter_story_name);
		editText_enter_storyname.addTextChangedListener(this);
		
		tv_select_topic = (TextView) findViewById(R.id.textview_select_topic);
		spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
		tv_heading=(TextView)findViewById(R.id.tvHeading);
	}

	private void addOnClickListner() {
		button_next.setOnClickListener(this);
		button_home.setOnClickListener(this);
		editText_enter_storyname.setOnFocusChangeListener(this);
		
		//editText_enter_storyname.setNextFocusDownId(R.id.ll_spinner);

	}

	private void TopicDropDown() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			// Network connection is available
			// calling method to parse Topic list API
			// Calling async task to get json
			// tv_select_topic.setVisibility(View.GONE);
			// rl.addView(spinnerView);
			tv_select_topic.setVisibility(View.GONE);
			spinner.setVisibility(View.VISIBLE);
			topicList.clear();
			new GetTopics().execute();

		} else {
			// Network connection is not available
			errorAlertDialog(getResources().getString(R.string.network_conn_error));
		}

	}

	private void errorAlertDialog(String message) {
		DialogFragment newFragment = ErrorDialog.newInstance(Tab1Activity.this,message,null);
		newFragment.show(getFragmentManager(), "dialog");
	}

	public class GetTopics extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(Tab1Activity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			//pDialog.show();
			
			pd.show();
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			h.postDelayed(r,30000);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// Creating service handler class instance
			JsonParser sh = new JsonParser();
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair(Constants.KEY_TOPIC_LANGUAGE,
					Constants.VALUE_TOPIC_LANGUAGE));

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(URL, JsonParser.POST, param);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				// Checks if json response is not null
				try {
					// Creates json object
					JSONObject jsonObj = new JSONObject(jsonStr);
					// Getting JSON Array node
					topics = jsonObj.getJSONArray(Constants.ARRAYNODE_TOPIC);

					// looping through All Topics
					for (int i = 0; i < topics.length(); i++) {
						JSONObject c = topics.getJSONObject(i);
						// Parse json string using object node for topics
						
						String name = c.getString(Constants.OBJECT_TOPIC_NAME);
						
						String id = c.getString(Constants.OBJECT_TOPIC_ID);
						hm.put(name, id);
						/* String topic_id = hm.get("Abuse");
						System.out.println("topic id: "+topic_id);*/
						 
						// Add each parsed json strings to arraylist
						topicList.add(name);
						// System.out.println("topicLst"+topicList);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				
				//Show alert with no netwrk 
				// Network connection is not available
				Intent intent=new Intent(Tab1Activity.this, DialogActivity.class);
				intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "no_network");
				startActivity(intent);
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			if (pDialog.isShowing())
				pDialog.dismiss();
			
			h.removeCallbacks(r);
			if (pd.isShowing() ) {
				pd.dismiss();
			}
			/**
			 * Updating parsed JSON data into Spinner with checkbox Here Custom
			 * spinner class is used The parameters should be passed in String
			 * array format only.
			 * */

			if (topicList != null) {
				if(!(topicList.equals(""))){
				// Converting arraylist to string array
				String[] mStringArray = new String[topicList.size()];
				mStringArray = topicList.toArray(mStringArray);

				/*ArrayList<String> topic_id = new ArrayList<String>();
				for(String m: mStringArray){
					topic_id.add(hm.get(m));
				}
				System.out.println("iddd: "+topic_id);*/
				// Updating the spinner with converted string array
				System.out.println("items selected: "+mStringArray);
				spinner.setItems(mStringArray);
				}
				
			} else {
				// Notifies the user with a error toast message if topic list
				// returns null value
				System.out.println("Topic list null");
				//Toast.makeText(getBaseContext(), R.string.network_error,
					//	Toast.LENGTH_SHORT).show();
			}

		}
	}

	private void createProject(String storyName) {

		try {
			String projectPath = FileUtils
					.createNewProjectPath(this, storyName);
			System.out.println("projectPath: " + projectPath);
			// save projectpath in DB
			db.insertProjectPath(projectPath);
			savePref(projectPath, storyName, "newProject");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tabViewActivity.test(1);
		tabViewActivity.setCurrentActivityTab(1);
		if(db.getCompletedScreens()<1)
		{
		db.updateCompletedScreen(1);
		}
		
		
		// try {
		//
		//
		// Intent intent = new Intent(Tab1Activity.this,TabViewActivity.class);
		// String projectPath = FileUtils.createNewProjectPath(this,storyName);
		// System.out.println("projectPath: "+projectPath);
		// //save projectpath in DB
		// savePref(projectPath,storyName,"newProject");
		//
		// System.out.println("storyName: "+storyName);
		// System.out.println("projectPath: "+projectPath);
		// startActivity(intent);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void savePref(String projectPath, String storyName,
			String newProject) {
		editor.putString("path_name", projectPath);
		editor.putString("project_name", storyName);
		editor.putString("new_project", newProject);
		editor.commit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			//db.updateShowPreviewTab1(0);
			
			analytics_conveyer.sendScreenandEvent(Constants.TMS_SCREEN,Constants.TMS_CATEGORY, Constants.TMS_ACTION, Constants.TMS_LABEL);
			
			
			System.out.println("constavts value is "
					+ Constants.SET_TOPIC_SELECTED);
			if (Constants.SET_TOPIC_SELECTED == 1) {
				if((db.getTopics() == null)){
					ivMandatory_Topic.setVisibility(View.VISIBLE);
				}else{
					if(!(db.getTopics()).equals("")){
						
						//Insert Topic Id to DB
						String Topics = db.getTopics();
						ArrayList<String> topic_id = new ArrayList<String>();
						if (Topics != null) {
							for (String item : Topics.split(", ")) {
								topic_id.add(hm.get(item));
							}
							String regex = "\\[|\\]";
							String topicid = null;
							topicid = topic_id.toString();
							if (topicid != null) {
								topicid = topicid.replaceAll(regex, "");
								// db.updateTopicId(topic_id.toString() + ",");
								db.updateTopicId(topicid);
							}
							System.out.println("iddd: " + topicid);
						}
						
						ivMandatory_Topic.setVisibility(View.INVISIBLE);
						tabViewActivity.setCurrentActivityTab(9);
					}else{
						ivMandatory_Topic.setVisibility(View.VISIBLE);
					}
					
				}
				
			} else {

				if (isEmpty(editText_enter_storyname)) {
					ivMandatory_storyName.setVisibility(View.VISIBLE);
				} else {
					//String title = db.getTitle();
					//if (title != null) {
					//	if (!(title.equals(""))) {

							ivMandatory_storyName.setVisibility(View.INVISIBLE);
							// Constants.SHOW_PREVIEW_TAB1 = 0;
							db.updateShowPreviewTab1(0);
							Constants.SET_CURRENT_TAB = 1;
							tabViewActivity.enabletab(1);
							// Set the track of Enabled tabs.
							Constants.ENABLE_TAB1 = 1;
							final String storyName = editText_enter_storyname
									.getText().toString();
							System.out.println("storyName: " + storyName);
							System.out.println("Login count : "
									+ db.loginCount());
							if (storyName != null) {
								System.out.println("row in tab1activity: "
										+ db.loginCount());
								// Insert storyname in DB
								/*
								 * if(db.loginCount()>0){ db.updateStoryName(new
								 * Model(storyName));
								 * TabViewActivity.setCurrentActivityTab(1);
								 * }else{ db.insertStoryName(new
								 * Model(storyName));
								 * db.updateMergedStatus(Constants
								 * .MERGED_STATUS_NEW);
								 * createProject(storyName); }
								 */
								if (db.loginCount() > 1) {
									System.out.println("login count >1");
									/*
									 * Delete all values from row
									 */
									//db.updateSelectedBGMusic(null);
									//db.updateStoryName(new Model(""));
									//db.updateTopics(null);
									//db.insertDescription(null);
									//db.insertTags(null);
									//db.insertLanguage(null);
									//db.insertCountry(null);
 
									//db.updateVideo1("");
									//db.updateVideo2("");
									//db.updateVideo3("");
									//db.updateVideo4("");
									//db.updateVideo5("");
									// db.deleteAllRows();
									db.updateStoryName(new Model(storyName));
									//db.insertStoryName(new Model(storyName));
									//db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
									createProject(storyName);
								} else if (db.loginCount() == 1) {
									System.out.println("login count = 1");
									//db.updateStoryName(new Model(storyName));
									db.insertStoryName(new Model(storyName));
									//db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
									createProject(storyName);
									tabViewActivity.setCurrentActivityTab(1);
								} else if (db.loginCount() == 0) {
									System.out.println("login count = 0");
									db.insertStoryName(new Model(storyName));
									//db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
									createProject(storyName);
								}
								// db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
								// createProject(storyName);
						//	}
						//}
					} else {
						Toast.makeText(this, "Please enter the story name",
								Toast.LENGTH_SHORT).show();
					}

					// Add TOPIC ID

					String Topics = db.getTopics();

					ArrayList<String> topic_id = new ArrayList<String>();
					if (Topics != null) {
						for (String item : Topics.split(", ")) {
							topic_id.add(hm.get(item));
						}
						String regex = "\\[|\\]";
						String topicid = null;
						topicid = topic_id.toString();
						if (topicid != null) {
							topicid = topicid.replaceAll(regex, "");
							// db.updateTopicId(topic_id.toString() + ",");
							db.updateTopicId(topicid);
						}
						System.out.println("iddd: " + topicid);
					}

				}
			}
			/*System.out.println("constavts value is "
					+ Constants.SET_TOPIC_SELECTED);*/
			/*if (Constants.SET_TOPIC_SELECTED == 1) {
				if ((db.getTopics() == null) || (db.getTopics()).equals("")) {
					ivMandatory_Topic.setVisibility(View.VISIBLE);
				} else {
					ivMandatory_Topic.setVisibility(View.INVISIBLE);
					tabViewActivity.setCurrentActivityTab(9);
				}
			}*/
			
			break;

			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SET_CURRENT_TAB = 1;
			tabViewActivity.enabletab(1);
			final String storyName = editText_enter_storyname.getText()
					.toString();
			System.out.println("storyName: " + storyName);
			if (storyName != null) {

				// Insert storyname in DB
				db.insertStoryName(new Model(storyName));
				db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
				createProject(storyName);

			} else {
				Toast.makeText(this, "Please enter the story name",
						Toast.LENGTH_SHORT).show();

			}*/
		
		case R.id.btn_home:
			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SHOW_PREVIEW_TAB2 = 0;
			Constants.SHOW_PREVIEW_TAB3 = 0;
			Constants.SHOW_PREVIEW_TAB4 = 0;
			Constants.SHOW_PREVIEW_TAB5 = 0;*/
			Intent intent = new Intent(Tab1Activity.this,HomeScreen.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}

	}
	private boolean isEmpty(EditText etText) 
	{
		if (etText.getText().toString().trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.ll_spinner:{
			if(hasFocus){
				System.out.println("Spinner has focus: ");
				TopicDropDown();
			}
		}
			break;
		case R.id.enter_story_name:{
			if(hasFocus){
				System.out.println("enter_story_name has focus: ");
				//editText_enter_storyname.setNextFocusDownId(R.id.ll_spinner);
				//ll.requestFocus();
			}
		}

		default:
			break;
		}
		
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		 Constants.SET_CURRENT_TAB = 0;
	}
@Override
protected void onPause() {
	
	super.onPause();
	System.out.println("onPause Tab1Activity...");
	if(db.loginCount()>0){
		final String storyName = editText_enter_storyname.getText()
				.toString();
		db.updateStoryName(new Model(storyName));
	}
	ll.clearFocus();
}

@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	
	System.out.println("Title..beforeTextChanged: "+s);
	
}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
	
	System.out.println("Title..onTextChanged: "+s);
	
}
@Override
public void afterTextChanged(Editable s) {
	
	String a = s.toString();
	System.out.println("Title..afterTextChanged: "+a);
		if(a.equals("")){
			System.out.println("Title..no text..");
			//tabViewActivity.DisableTabs();
			ivMandatory_storyName.setVisibility(View.VISIBLE);
			//Intent intent=new Intent(Tab1Activity.this, DialogActivity.class);
			//intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "text_empty");
			//startActivity(intent);
		}else{
			ivMandatory_storyName.setVisibility(View.INVISIBLE);
		}
	
	}

public static void setTopicHolder() {
	//spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
	//spinner.setVisibility(View.GONE);
	//tv_select_topic = (TextView) findViewById(R.id.textview_select_topic);
	tv_select_topic.setVisibility(View.VISIBLE);
	tv_select_topic.setText("Select a Topic");
	
}

//}
}