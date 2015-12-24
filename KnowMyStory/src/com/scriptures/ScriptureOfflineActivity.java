package com.scriptures;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import com.dialog.DialogActivity;
import com.dialog.ErrorDialog;
import com.dialog.LoaderActivity;
import com.dialog.TransparentProgressDialog;
import com.dialog.UploadSuccessDialog;
import net.onehope.mystory.R;
import com.scriptures.Wheel_Activity.GetBibleVerse;
import com.supportclasses.Constants;
import com.supportclasses.JsonParser;
import com.test.socialnetwork.HomeScreen;
import com.videoeditor.media.CustomCameraActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Class for displaying the offline and online lists 
 */
public class ScriptureOfflineActivity extends ActivityGroup implements OnItemClickListener,OnClickListener
{
	private ListView list,lvOnline/*,lvSearch*/;
	DatabaseCreator db;
	private EditText etSelectKey;

	CustomAdapter_VerseOffline adapter;
	public  ScriptureOfflineActivity CustomListView = null;
	public  ArrayList<VerseModal> CustomListViewValuesArr = new ArrayList<VerseModal>();

	CustomAdapter_OnlineVerse online_adapter;
	public  ScriptureOfflineActivity OnlineView = null;
	public  ArrayList<OnlineModal> OnlineListViewValuesArr = new ArrayList<OnlineModal>();

	//	CustomAdapter_SearchOffine search_adapter;
	public  ScriptureOfflineActivity SeachView = null;
	//public  ArrayList<SearchModal> SearchListViewValuesArr = new ArrayList<SearchModal>();
	public  ArrayList<VerseModal> SearchListViewValuesArr = new ArrayList<VerseModal>();

	private Button btnOnline,btnOffline,btnNext,btnHelp;
	private RelativeLayout lOnline,lOffline/*,lSearch*/;

	//private LinearLayout footer;
	//	private ProgressBar progressBar1,progressBar2;

	private ImageButton ibBack;

	String indx,txt;
	int pos;
	String online_count= null;

	int verse_count;
	//	boolean offline_flag_loading=false;
	//	int offline_start_count=0;
	//	int offline_end_count=10;

	boolean online_flag_loading=false;
	int online_start_count=0;
	int online_end_count=10;
	View footerView;

	DatabaseHelper dbHelp;

	private TextView mTvEmpty;
	
	public Typeface abel_ttf;
	final String PREFS_NAME = "Scripture_MyPrefsFile";

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scripture_offline_activity);
		Constants.WHEEL_ACTIVITY = 2;
		dbHelp= new DatabaseHelper(this);

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		if (settings.getBoolean("first_time_scripture_verse", true)) 
		{
			new GetVerses().execute();
		}


		initializeComponents();
		setFontStyle();
		setUpListeners();
		mTvEmpty.setVisibility(View.GONE);
		CustomListView = this;

		/******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
		setListData(/*offline_end_count*/);

		Resources res =getResources();

		/**************** Create Custom Adapter *********/
		System.out.println("array \n \n "+CustomListViewValuesArr);
		
		adapter=new CustomAdapter_VerseOffline( CustomListView, CustomListViewValuesArr,res );
		list.setAdapter( adapter );
		adapter.notifyDataSetChanged();
	}

	/**
	 * Function to initialize the components
	 */
	private void initializeComponents()
	{
		list=(ListView)findViewById(R.id.lvVerse);
		abel_ttf=Typeface.createFromAsset(ScriptureOfflineActivity.this.getAssets(), "fonts/Abel-Regular.ttf");
		


		lvOnline=(ListView)findViewById(R.id.lvOnline);
		mTvEmpty=(TextView)findViewById(R.id.tvEmpty);
		

		ibBack=(ImageButton)findViewById(R.id.ibBack);

		btnOffline=(Button)findViewById(R.id.btnOffline);
		btnOnline=(Button)findViewById(R.id.btnOnline);
		btnNext=(Button)findViewById(R.id.btn_next);
		//btnNext.setEnabled(true);
		btnHelp=(Button)findViewById(R.id.btn_home);

		etSelectKey=(EditText)findViewById(R.id.etSearch);
		

		etSelectKey.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId,	KeyEvent event) 
			{
				if (actionId == EditorInfo.IME_ACTION_SEARCH) 
				{
					hideKeyboard();
					System.out.println("test entered "+etSelectKey.getText().toString().trim());
					new GetCountVerse().execute();
				
					return true;
				}

				return false;
			}
		});
		lOffline=(RelativeLayout)findViewById(R.id.lOffline);
		lOnline=(RelativeLayout)findViewById(R.id.lOnline);

		db = new DatabaseCreator(this);
	}

	/**
	 * Function to set custom font
	 */
	private void setFontStyle()
	{
		mTvEmpty.setTypeface(abel_ttf);
		btnOffline.setTypeface(abel_ttf);
		btnOnline.setTypeface(abel_ttf);
		etSelectKey.setTypeface(abel_ttf);
	}

	/**
	 * Function to hide the keyboard
	 */
	private void hideKeyboard() 
	{   
		// Check if no view has focus:
		View view = this.getCurrentFocus();
		if (view != null) 
		{
			InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * Function to set up listeners for the buttons
	 */
	private void setUpListeners()
	{
		
		lvOnline.setOnItemClickListener(this);

		btnOnline.setOnClickListener(this);
		btnOffline.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnHelp.setOnClickListener(this);

		ibBack.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3)
	{
		
		System.out.println("print "+adapter.getItem(arg2));
		

	}

	/**
	 * Asynchronous task to insert all the verse text to the database
	 */
	public class GetVerses extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			getVerseListFromJSON();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			dbHelp.updateVerseDbComplete();
		}

	}

	/**
	 * Function to insert verse text to the database
	 */
	private void getVerseListFromJSON()
	{
		try
		{
			String result =loadJSONFromAsset(Constants.BIBLE_OFFLINE_JSON);
			JSONArray json = new JSONArray(result);
			System.out.println("length of json is "+json.length());

			// increase heap size if needed
			for(int i=0;i<json.length();i++)
			{   
				JSONObject jsObj= json.getJSONObject(i);
				db.insertVerseOfflineFields(i,jsObj.getString("book_name")+" "+jsObj.getString("chapter")+":"+jsObj.getString("verse"),jsObj.getString("verse_text"));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Function to load json file from the assets folder
	 */
	public String loadJSONFromAsset(String file_name)
	{
		String json = null;
		try 
		{
			InputStream is = getAssets().open(file_name);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} 
		catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	/**
	 * Function to add data to the list
	 */
	public void setListData()
	{
		try
		{
			String result =loadJSONFromAsset(Constants.BIBLE_OFFLINE_JSON);
			JSONArray json = new JSONArray(result);

		
			// increase heap size if needed
			for(int i=0/*offline_start_count*/;i<json.length()/*count*/;i++)

			{
				JSONObject jsObj= json.getJSONObject(i);
				/******* Firstly take data in model object ******/
				final VerseModal sched = new VerseModal();
				sched.setId(jsObj.getInt("id"));
				String str=jsObj.getString("book_name")+" "+jsObj.getString("chapter")+":"+jsObj.getString("verse");
				System.out.println("string valus is \n "+str);
				sched.setVerseIndex(str);
				sched.setVerseText(jsObj.getString("verse_text"));


				/******** Take Model Object in ArrayList **********/
				CustomListViewValuesArr.add( sched );

			}

			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Function to get the position of the item clicked from the offline list
	 */
	public void onOfflineItemClick(String v,String t,int mPosition )
	{
		indx=v;
		pos=mPosition;
		txt=t;
		list.setItemChecked(mPosition, true);
	}

	/**
	 * Function to get the position of the item clicked from the online list
	 */
	public void onOnlineItemClick(String v,String t,int mPosition )
	{
		indx=v;
		pos=mPosition;
		txt=t;
		lvOnline.setItemChecked(mPosition, true);

	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.btnOnline:
			changeColorButtonsOnline();

			if(etSelectKey.getText().toString().trim().length() != 0)
			{
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) 
				{
					// Network connection is available
					CallOnlineAsync(etSelectKey.getText().toString().trim());
				}
				else
				{
					// Network connection is not available
					Intent intent=new Intent(ScriptureOfflineActivity.this, DialogActivity.class);
					startActivity(intent);
				}
				
			}
			else
			{
			
				//Toast.makeText(this,getResources().getString(R.string.online_message),Toast.LENGTH_LONG).show();
				Intent intent=new Intent(ScriptureOfflineActivity.this, DialogActivity.class);
				intent.putExtra("DIALOG_MESSAGE_VALID_KEYWORD", "valid_keyword");
				startActivity(intent);
				

			}
			break;

		case R.id.btnOffline:
			changeColorButtonsOffline();
			
			System.out.println("size is "+etSelectKey.getText().toString().length());
			if(etSelectKey.getText().toString().length() >0)
			{
				new GetCountVerse().execute();
			
			}
			else
			{
				setListData(/*offline_end_count*/);

				Resources res =getResources();

				adapter=new CustomAdapter_VerseOffline( CustomListView, CustomListViewValuesArr,res );

				list.setAdapter( adapter );
				adapter.notifyDataSetChanged();
				mTvEmpty.setVisibility(View.GONE);
				lOnline.setVisibility(View.GONE);
				lOffline.setVisibility(View.VISIBLE);

			}

			break;

		case R.id.btn_next:
			
			
			if((indx != null)&&(txt != null))
			{
			
				Intent i = new Intent(this,LoaderActivity.class);
				startActivity(i);
				
				Intent intent=new Intent(ScriptureOfflineActivity.this, Verse_Activity.class);
				//Create the bundle
				Bundle bundle = new Bundle();
				//Add your data from getFactualResults method to bundle
				bundle.putString("verse_text", txt);
				bundle.putString("verse_index", indx);
				//Add the bundle to the intent
				intent.putExtras(bundle);
				startActivity(intent);
			}
			else
			{
				//	errorAlertDialog("Verse not selected");
				Toast.makeText(this, "Verse not selected", Toast.LENGTH_LONG).show();
			}
			break;

		case R.id.btn_home:

			//Constants.SHOW_PREVIEW_TAB1 = 0;
			dbHelp.updateShowPreviewTab1(0);
			dbHelp.updateShowPreviewTab2(0);
			dbHelp.updateShowPreviewTab3(0);
			dbHelp.updateShowPreviewTab4(0);
			dbHelp.updateShowPreviewTab5(0);
			
			//Constants.SHOW_PREVIEW_TAB2 = 0;
			//Constants.SHOW_PREVIEW_TAB3 = 0;
			//Constants.SHOW_PREVIEW_TAB4 = 0;
			//Constants.SHOW_PREVIEW_TAB5 = 0;
			Intent intent_home = new Intent(ScriptureOfflineActivity.this,HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
			break;

		case R.id.ibBack:
			//onBackPressed();
			Constants.SET_VERSE_FLAG=0;
			Intent intent_choose = new Intent(ScriptureOfflineActivity.this, Wheel_Activity.class);
		//	intent_choose.putExtra(OPEN_CAM, "open_cam_4");
			replaceContentView("Wheel_Activity", intent_choose);
			
			break;

		default:
			break;
		}
		}

	/**
	 * Function to bring the view under the wizard
	 */
	private void replaceContentView(String id, Intent newIntent)
	{
		View view = ((ActivityGroup) this)
				.getLocalActivityManager()
				.startActivity(id,
						newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView();
		((ActivityGroup) this).setContentView(view);
	}

	/**
	 * Function to change online button colors
	 */
	private void changeColorButtonsOnline()
	{
		btnOnline.setBackgroundColor(getResources().getColor(R.color.orange));
		btnOnline.setTextColor(getResources().getColor(R.color.white));
		btnOffline.setBackgroundColor(getResources().getColor(R.color.white));
		btnOffline.setTextColor(getResources().getColor(R.color.orange));
	}

	/**
	 * Function to change offline button colors
	 */
	private void changeColorButtonsOffline()
	{
		btnOnline.setBackgroundColor(getResources().getColor(R.color.white));
		btnOnline.setTextColor(getResources().getColor(R.color.orange));
		btnOffline.setBackgroundColor(getResources().getColor(R.color.orange));
		btnOffline.setTextColor(getResources().getColor(R.color.white));
	}

	/**
	 * Function to call the alert dialog
	 */
	public void showDialog()// throws Exception
	{
		System.out.println("outtt");
		/*		AlertDialog.Builder builder = new AlertDialog.Builder(ScriptureOfflineActivity.this);
		builder.setTitle(getResources().getString(R.string.online_title));
		builder.setMessage(getResources().getString(R.string.online_message));       
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				dialog.dismiss();
			}
		});
		builder.show();*/

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScriptureOfflineActivity.this);
		// set title
		alertDialogBuilder.setTitle(getResources().getString(R.string.online_title));

		// set dialog message
		alertDialogBuilder
		.setMessage(getResources().getString(R.string.online_message))
		.setCancelable(false)
		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, close
				// current activity
				dialog.cancel();
			}
		});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	/**
	 * Function to call the online list
	 */
	private void CallOnlineAsync(String key_word) 
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) 
		{
			// Network connection is available
			// calling method to parse Channel list API
			// Calling async task to get json
			new GetOnlineVerse().execute(key_word/*,String.valueOf(online_end_count)*/);
		}
		else
		{
			// Network connection is not available
			//errorAlertDialog();
		}
	}

	/**
	 * Async task for searching the online list
	 */
	public class GetOnlineVerse extends AsyncTask<String, Void, Void> 
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();

			/*			pDialog = new ProgressDialog(ChannelVideos.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);

			pd.show();*/
			/*			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			h.postDelayed(r,5000);*/

/*			pd.show();
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			h.postDelayed(r,5000);*/
		}

		@SuppressLint("NewApi") @Override
		protected Void doInBackground(String... params)
		{
			try
			{
				if(OnlineListViewValuesArr != null)
				{
					OnlineListViewValuesArr.clear();
				}
				JsonParser sh = new JsonParser();

				String jsonStr = sh.getWebDataPostRequestOnline(params[0]);
				JSONArray json = new JSONArray(jsonStr);

				if (json != null)
				{
					JSONArray json1 = json.getJSONArray(1);
					//	int online_count=Integer.parseInt(params[1]);
					//	System.out.println("count is * "+online_count+" and end count is "+online_end_count+" strt count is "+online_start_count);
					for(int i=/*online_start_count*/0;i< /*online_count*/json1.length();i++)
					{                        
						JSONObject e = json1.getJSONObject(i);
						System.out.println("val is "+e.getString("book_id"));

						final OnlineModal sched = new OnlineModal();
						int k=1+i;
						sched.setId(k);
						String ver_ind=e.getString("book_name")+" "+e.getString("chapter_id")+":"+e.getString("verse_id");
						System.out.println("string valus is \n "+ver_ind);
						sched.setVerseIndex(ver_ind);
						sched.setVerseText(e.getString("verse_text"));

						OnlineListViewValuesArr.add( sched );
					}

					/*					online_start_count=online_end_count;
					online_end_count=online_end_count+10;
					System.out.println("count is "+online_count+" and end count is "+online_end_count+" strt count is "+online_start_count);
					online_flag_loading=false;*/
				} 

				if(json != null)
				{
					JSONArray json2 = json.getJSONArray(0);
					JSONObject e = json2.getJSONObject(0);
					System.out.println("count is 0 check "+e.getString("total_results"));
					online_count=e.getString("total_results");
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);

/*			h.removeCallbacks(r);
			if (pd.isShowing() ) {
				pd.dismiss();
			}*/
			if(Integer.parseInt(online_count) ==0)
			{
				mTvEmpty.setVisibility(View.VISIBLE);
				lOnline.setVisibility(View.GONE);
				lOffline.setVisibility(View.GONE);

				if(online_adapter != null)
				{
					online_adapter= null;
				}
				/*		online_adapter=new CustomAdapter_OnlineVerse( ScriptureOfflineActivity.this, OnlineListViewValuesArr,getResources() );
				lvOnline.setAdapter( online_adapter );*/
			}
			else
			{
				mTvEmpty.setVisibility(View.GONE);
				lOnline.setVisibility(View.VISIBLE);
				lOffline.setVisibility(View.GONE);
				Resources online_res =getResources();

				/**************** Create Custom Adapter *********/
				if(online_adapter != null)
				{
					online_adapter= null;
				}
				online_adapter=new CustomAdapter_OnlineVerse( ScriptureOfflineActivity.this, OnlineListViewValuesArr,online_res );
				lvOnline.setAdapter( online_adapter );
				online_adapter.notifyDataSetChanged();


				/*			if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			{
				AnimationAdapter animAdapter = new ScaleInAnimationAdapter(adapter);
				animAdapter.setAbsListView(lvOnline);
				lvOnline.setAdapter(animAdapter);
			}
			else
			{
				lvOnline.setAdapter( online_adapter );
				online_adapter.notifyDataSetChanged();
			}*/

				//	online_adapter.notifyDataSetChanged();
			}
		}
	}

	/**
	 * Async task for returning the number of items in the offline list
	 * that has the keyword
	 */
	public class GetCountVerse extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected Void doInBackground(Void... params)
		{
			verse_count=db.getOfflineVerseCount(etSelectKey.getText().toString().trim());
			System.out.println("count is "+verse_count);
			return  null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			
			super.onPostExecute(result);
			System.out.println("reuslt value "+verse_count);
			System.out.println("verse number is "+verse_count);
			if(verse_count != 0)
			{
				//	lSearch.setVisibility(View.VISIBLE);
				mTvEmpty.setVisibility(View.GONE);
				new SearchVerseResults().execute();
				lOffline.setVisibility(View.VISIBLE);
				lOnline.setVisibility(View.GONE);
			}
			else
			{
				//	lSearch.setVisibility(View.GONE);

				// show empty statement
				mTvEmpty.setVisibility(View.VISIBLE);
				lOffline.setVisibility(View.GONE);
				lOnline.setVisibility(View.GONE);

				// empty alerter
			}

		}

	}

	public class SearchVerseResults extends AsyncTask<Void, Void, Void> 
	{

		protected void onPreExecute() 
		{
			super.onPreExecute();

			/*			pDialog = new ProgressDialog(ChannelVideos.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);*/

			/*			pd.show();
			pd.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			h.postDelayed(r,5000);*/
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			if(SearchListViewValuesArr != null)
			{
				SearchListViewValuesArr.clear();
			}
			ArrayList<HashMap<String, String>> list=db.getOfflineVerse(etSelectKey.getText().toString().trim());
			System.out.println(" the search list is in acivity \n "+list);
			for(int k=0;k<list.size();k++)
			{
				final VerseModal searc = new VerseModal();
				int g=1+k;
				searc.setId(g);
				searc.setVerseIndex(list.get(k).get("index"));
				searc.setVerseText(list.get(k).get("v_text"));

				/******** Take Model Object in ArrayList **********/
				SearchListViewValuesArr.add( searc );
			}
			return  null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);

			/*		h.removeCallbacks(r);
			if (pd.isShowing() ) 
			{
				pd.dismiss();
			}*/
			if(adapter != null)
			{
				adapter= null;
				//	search_adapter.notifyDataSetChanged();
			}
			adapter=new CustomAdapter_VerseOffline( ScriptureOfflineActivity.this, SearchListViewValuesArr,getResources() );
			list.setAdapter( adapter );

			adapter.notifyDataSetChanged();
		}

	}

	@Override
	public void onBackPressed() {
		
		super.onBackPressed();
	}

	

}
