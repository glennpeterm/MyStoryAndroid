/*
 * Know My Story 
 */
package com.tabview;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.DatabaseHelper;
import com.dialog.ErrorDialog;
import com.dialog.MultiSelectionSpinner;
import net.onehope.mystory.R;
import com.supportclasses.Constants;
import com.supportclasses.JsonParser;
import com.supportclasses.CustomListAdapter;
import com.supportclasses.LoadJSONFRomAsset;
import com.tabview.Tab1Activity.GetTopics;
import com.test.socialnetwork.HomeScreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnShowListener;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
//import android.view.LayoutInflater.Filter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RegionActivity extends Activity implements OnClickListener
{

	Button btn_next;
	Button btn_home;
	TabViewActivity tabViewActivity;
	TextView tv_language,tvRegionHeading;
	TextView tv_country;
	ArrayAdapter<String> adapter;
	PopupWindow popupWindow;
	ArrayList<HashMap<String, String>> languageList = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> CountryDetailsList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> languageListArray = new ArrayList<String>();
	ArrayList<String> countryNameListArray = new ArrayList<String>();
	MultiSelectionSpinner spinner;
	DatabaseHelper db;
	AlertDialog alertDialog;
	ImageView ivMandatory_lang;
	ImageView ivMandatory_country;

	public Typeface abel_ttf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
				| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.region);
		initializeObjects();
		setTypeFonts();
		setOnClickListners();
		setInitialValues();
	}
	private void setInitialValues() {
		tabViewActivity.enabletab(0);
		tabViewActivity.enabletab(1);
		tabViewActivity.enabletab(2);
		tabViewActivity.enabletab(3);
		tabViewActivity.enabletab(4);
		tabViewActivity.enabletab(5);
		tabViewActivity.enabletab(6);
		tabViewActivity.enabletab(7);
		tabViewActivity.enabletab(8);
	}
	private void setOnClickListners() {
		btn_next.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		tv_language.setOnClickListener(this);
		tv_country.setOnClickListener(this);

	}

	private void initializeObjects() {
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_home = (Button) findViewById(R.id.btn_home);
		tabViewActivity = new TabViewActivity();
		//popupWindow = popupWindow();
		tv_language = (TextView) findViewById(R.id.enter_language);
		tvRegionHeading=(TextView)findViewById(R.id.tvRegionHeading);
		tv_country = (TextView) findViewById(R.id.textview_select_country);
		spinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
		db = new DatabaseHelper(this);
		ivMandatory_lang=(ImageView)findViewById(R.id.ivLang);
		ivMandatory_country=(ImageView)findViewById(R.id.ivCountry);
	}
	private void setTypeFonts()
	{
		abel_ttf=Typeface.createFromAsset(RegionActivity.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		tvRegionHeading.setTypeface(abel_ttf/*,Typeface.BOLD*/);
		tv_language.setTypeface(abel_ttf);
		tv_country.setTypeface(abel_ttf);
	}

	/*
	 * adapter where the list values will be set
	 */
	private ArrayAdapter<String> LanguageAdapter(String dogsArray[]) {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, dogsArray) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {

				// setting the ID and text for every items in the list
				String item = getItem(position);
				String[] itemArr = item.split("::");
				String text = itemArr[0];
				String id = itemArr[1];

				// visual settings for the list item
				TextView listItem = new TextView(RegionActivity.this);
				listItem.setTypeface(abel_ttf);
				listItem.setText(text);
				listItem.setTag(id);
				listItem.setTextSize(22);
				listItem.setPadding(10, 10, 10, 10);
				listItem.setTextColor(getResources().getColor(R.color.list_selector));
				listItem.setTextSize(5);

				return listItem;
			}
		};

		return adapter;
	}

	private boolean isEmpty(TextView tvText) 
	{
		if (tvText.getText().toString().trim().length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next: {


			if((isEmpty(tv_language))&&(isEmpty(tv_country)))
			{
				ivMandatory_lang.setVisibility(View.VISIBLE);
				ivMandatory_country.setVisibility(View.VISIBLE);
			}
			else if(isEmpty(tv_language))
			{
				ivMandatory_lang.setVisibility(View.VISIBLE);
				ivMandatory_country.setVisibility(View.INVISIBLE);
			}
			else if(isEmpty(tv_country))
			{
				ivMandatory_country.setVisibility(View.VISIBLE);
				ivMandatory_lang.setVisibility(View.INVISIBLE);
			}
			else
			{
				ivMandatory_lang.setVisibility(View.INVISIBLE);
				ivMandatory_country.setVisibility(View.INVISIBLE);
				Constants.SET_CURRENT_TAB = 9;
				tabViewActivity.setCurrentActivityTab(9);
				//Set the track of Enabled tabs. 
				Constants.ENABLE_TAB9 = 1;
				tabViewActivity.enabletab(9);
				if(db.getCompletedScreens()<9){
					db.updateCompletedScreen(9);
				}
			}



			/*Constants.SET_CURRENT_TAB = 9;
			TabViewActivity.setCurrentActivityTab(9);
			tabViewActivity.enabletab(9);*/

		}

		break;
		case R.id.btn_home: {
			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SHOW_PREVIEW_TAB2 = 0;
			Constants.SHOW_PREVIEW_TAB3 = 0;
			Constants.SHOW_PREVIEW_TAB4 = 0;
			Constants.SHOW_PREVIEW_TAB5 = 0;*/
			Intent intent_home = new Intent(RegionActivity.this,
					HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
		}
		break;
		case R.id.enter_language: {

			LanguageDropDown();
		}
		break;
		case R.id.textview_select_country: {

			CountryPopUp();
		}
		break;
		default:
			break;
		}

	}


	private void CountryPopUp() {

		new GetCountryList().execute();

	}

	private void LanguageDropDown() {

		new GetLanguageList().execute();

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
				jObject = new JSONArray(loadJSONFRomAsset.loadJSON(RegionActivity.this,Constants.REGION_COUNTRY_JSON_FILE));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				for(int i = 0; i<jObject.length() ;i++){
					String CountryCode =	jObject.getJSONObject(i).getString(Constants.KEY_REGION_COUNTRY_CODE);
					String CountryName =	jObject.getJSONObject(i).getString(Constants.KEY_REGION_COUNTRY_NAME);
					String CountryId =	jObject.getJSONObject(i).getString(Constants.KEY_REGION_COUNTRY_ID);
					countryNameListArray.add(CountryName);
					HashMap<String, String> countryDetails = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					countryDetails.put(Constants.REGION_COUNTRY_CODE, CountryCode);
					countryDetails.put(Constants.REGION_COUNTRY_NAME, CountryName);
					countryDetails.put(Constants.REGION_COUNTRY_ID, CountryId);
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
					new ContextThemeWrapper(RegionActivity.this, android.R.style.Theme_Holo));
			ListView list = new ListView(RegionActivity.this);
			list.setFastScrollEnabled(true);
			list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			list.setBackgroundColor(getResources().getColor(R.color.white));
			list.setSelector(R.color.white);
			list.setDivider(new ColorDrawable(R.color.lightest_grey));
			list.setDividerHeight(1);
			list.setPadding(30, 0, 30, 0);
			list.setAdapter(new CustomListAdapter(RegionActivity.this,
					countryNameListArray));
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View arg1,
						int position, long arg3) {
					((com.supportclasses.TextView_Normal)arg1).setTextColor(getResources().getColor(R.color.orange));
					tv_country.setText(parent.getItemAtPosition(position).toString());
					db.insertCountry(tv_country.getText().toString());
					//Toast.makeText(getApplicationContext(),
					//		"" + parent.getItemAtPosition(position),
					//		Toast.LENGTH_LONG).show();
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

	public class GetLanguageList extends AsyncTask<Void, Void, Void> {
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
				jObject = new JSONArray(loadJSONFRomAsset.loadJSON(RegionActivity.this,Constants.REGION_LANGUAGE_JSON_FILE));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*try {
					for(int i = 0; i<jObject.length() ;i++){
						 String language =	jObject.getJSONObject(i).getString(Constants.KEY_REGION_LANGUAGE);
						String code =	jObject.getJSONObject(i).getString(Constants.KEY_REGION_CODE);
						String bible_version =	jObject.getJSONObject(i).getString(Constants.KEY_REGION_BIBLE_VERSION);
						languageListArray.add(language);
						 HashMap<String, String> languages = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						 languages.put(Constants.REGION_LANGUAGE, language);
						 languages.put(Constants.REGION_CODE, code);
						 languages.put(Constants.REGION_BIBLE_VERSION, bible_version);
                        // adding language to languageList
                        languageList.add(languages);
                        System.out.println("languages: "+languageList);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}*/

			try {
				System.out.println("languages b4: "+languageListArray);
				if(!languageListArray.isEmpty())
				{
					languageListArray.clear();
				}
				for(int i = 0; i<jObject.length() ;i++){
					String language = jObject.getJSONObject(i).getString(Constants.KEY_REGION_LANGUAGE);
					String code = jObject.getJSONObject(i).getString(Constants.KEY_REGION_CODE);
					String bible_version = jObject.getJSONObject(i).getString(Constants.KEY_REGION_BIBLE_VERSION);

					languageListArray.add(language);
					HashMap<String, String> languages = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					languages.put(Constants.REGION_LANGUAGE, language);
					languages.put(Constants.REGION_CODE, code);
					languages.put(Constants.REGION_BIBLE_VERSION, bible_version);
					// adding language to languageList
					languageList.add(languages);

				}
				System.out.println("languages after: "+languageListArray);
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
					new ContextThemeWrapper(RegionActivity.this, android.R.style.Theme_Holo));
			ListView list = new ListView(RegionActivity.this);
			list.setFastScrollEnabled(true);
			list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			list.setBackgroundColor(getResources().getColor(R.color.white));
			list.setSelector(R.color.white);
			list.setDivider(new ColorDrawable(R.color.lightest_grey));
			list.setDividerHeight(1);
			list.setPadding(30, 0, 30, 0);
			list.setAdapter(new CustomListAdapter(RegionActivity.this,
					languageListArray));
			list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View arg1,
						int position, long arg3) {
					((com.supportclasses.TextView_Normal)arg1).setTextColor(getResources().getColor(R.color.orange));
					tv_language.setText(parent.getItemAtPosition(position).toString());
					db.insertLanguage(tv_language.getText().toString());
					//Toast.makeText(getApplicationContext(),
					//		"" + parent.getItemAtPosition(position),
					//		Toast.LENGTH_LONG).show();
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
	@Override
	protected void onResume() {

		super.onResume();
		Constants.SET_CURRENT_TAB = 8;
		if(db.getLanguage()!=null){
			if(!(db.getLanguage().equals(""))){
				tv_language.setText(db.getLanguage());
			}
		}
		if(db.getCountry()!=null){
			if(!(db.getCountry().equals(""))){
				tv_country.setText(db.getCountry());
			}
		}
	}
}
