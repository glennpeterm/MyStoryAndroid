package com.scriptures;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.DatabaseCreator;
import com.database.DatabaseHelper;
import com.dialog.DialogActivity;
import com.dialog.LoaderActivity;
import com.dialog.ReloadDialogActivity;
import com.dialog.TransparentProgressDialog;
import net.onehope.mystory.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.ActionBar.LayoutParams;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;









//import com.main.MainActivity;
import com.supportclasses.Constants;
import com.supportclasses.JsonParser;
import com.tabview.DescriptionActivity;
import com.test.socialnetwork.AboutMyStory_Activity;
import com.test.socialnetwork.HomeScreen;
import com.videoeditor.media.CustomCameraActivity;
import com.videoeditor.media.VideoPreviewOption;
import com.videoeditor.media.VideoPreviewOptionTab4;

/**
 * Class for enabling wheel functions
 */
public class Wheel_Activity extends ActivityGroup implements OnClickListener
{
	// Scrolling flag
	private boolean scrolling = false;

	// Wheel scrolled flag
	private boolean wheelScrolled = false;

	// Declare the variables and the objects used
	HashMap<HashMap<String, String>, ArrayList<String>>hs = new HashMap<HashMap<String, String>, ArrayList<String>>();

	ArrayList<String> book_list = new ArrayList<String>();
	ArrayList<String> chapter_list = new ArrayList<String>();
	ArrayList<String> book_order_list= new ArrayList<String>();
	ArrayList<String> dam_id_list=new ArrayList<String>();
	ArrayList<String> book_id_list=new ArrayList<String>();

	public 	String[] arrayString ;
	public 	String[] chap_arr ;
	public String[] bookorder_arr;
	public String[] damid_arr;
	public String[] bookid_arr;

	DatabaseCreator db;
	DatabaseHelper dbHelp;
	Button btnHome,btnHelp,btnNext;
	TextView tvBook,tvChapter,tvVerse;
	int a;
	int x;

	String URL = Constants.BIBLE_VERSE_URL + Constants.BIBLE_DBT_KEY+"&dam_id=";
	String appended_URL="";
	String verse;
	String verse_index;
	String OPEN_CAM = "open_cam";

	TransparentProgressDialog pd ;
	private Handler h;
	private Runnable r;

	public Typeface abel_ttf;
	LoaderActivity loaderActivity = new LoaderActivity();
	

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		//getActionBar().hide();
		setContentView(R.layout.wheel_activity);
		db = new DatabaseCreator(this);
		dbHelp = new DatabaseHelper(this);

		Constants.WHEEL_ACTIVITY = 1;
		//	new GetVerses().execute();
		getBookListFromJSON();	
		initializeComponents();
		setFontStyle();
		setClickListeners();


		h = new Handler();
		pd = new TransparentProgressDialog(Wheel_Activity.this, R.drawable.spinner,getResources().getString(R.string.dialog_fetching));
		r =new Runnable() {
			@Override
			public void run() {
				if(LoaderActivity.loader!=null){
				LoaderActivity.loader.finish();
				}
				Intent i = new Intent(Wheel_Activity.this,ReloadDialogActivity.class);
				startActivity(i);
				/*if (pd.isShowing()) {
					pd.dismiss();
				}*/
			}
		};
	}

	/**
	 * Function to set up listeners for the buttons
	 */
	private void setClickListeners()
	{
		btnHelp.setOnClickListener(this);
		btnHome.setOnClickListener(this);
		btnNext.setOnClickListener(this);
	}

	/**
	 * Function to initialize the components
	 */
	private void initializeComponents()
	{
		abel_ttf=Typeface.createFromAsset(Wheel_Activity.this.getAssets(), Constants.FONT_ABEL_REGULAR);
		btnHome=(Button)findViewById(R.id.btn_home);
		btnHelp=(Button)findViewById(R.id.btn_help);
		btnNext=(Button)findViewById(R.id.btn_next);

		tvBook=(TextView)findViewById(R.id.tvBook);
		tvChapter=(TextView)findViewById(R.id.tvchapter);
		tvVerse=(TextView)findViewById(R.id.tvverse);

		final WheelView wvChapter = (WheelView) findViewById(R.id.wheel_chapter);
		initChapterWheel(wvChapter,50,5);
		wvChapter.setCurrentItem(0);
		wvChapter.setVisibleItems(3);

		final WheelView wvVerse = (WheelView) findViewById(R.id.wheel_verse);
		initVerseWheel(wvVerse,31,5);
		wvVerse.setCurrentItem(0);
		wvVerse.setVisibleItems(3);

		final WheelView wvBook = (WheelView) findViewById(R.id.wheel_book);
		wvBook.setVisibleItems(3);
		wvBook.setViewAdapter(new BookAdapter(this));

		wvBook.addChangingListener(new OnWheelChangedListener()
		{
			public void onChanged(WheelView wheel, int oldValue, int newValue)
			{
				if (!scrolling)
				{
					initChapterWheel(wvChapter,50,newValue);
					initVerseWheel(wvVerse,31,5);
					System.out.println("print");
					updateStatus();
					System.out.println(wheel.getCurrentItem());
				}
			}
		});

		wvBook.addScrollingListener( new OnWheelScrollListener() 
		{
			public void onScrollingStarted(WheelView wheel) 
			{
				scrolling = true;
			}
			public void onScrollingFinished(WheelView wheel)
			{
				try
				{
					scrolling = false;
					System.out.println("item is \n "+wvBook.getCurrentItem());
					initChapterWheel(wvChapter,Integer.parseInt(chap_arr[wvBook.getCurrentItem()]),wvBook.getCurrentItem());
					//	initVerseWheel(wvVerse,3,5);
					x=1+getWheel(R.id.wheel_chapter).getCurrentItem();
					int v=db.getBibleVerse(Integer.parseInt(bookorder_arr[getWheel(R.id.wheel_book).getCurrentItem()]),x);
					initVerseWheel(getWheel(R.id.wheel_verse), v, 0);
					//	updateCities(city, cities, country.getCurrentItem());
				}
				catch(Exception e)
				{

				}
			}
		});
		wvBook.setCurrentItem(0);
	}

	/**
	 * Function to set custom font
	 */
	private void setFontStyle()
	{
		tvBook.setTypeface(abel_ttf,Typeface.BOLD);
		tvChapter.setTypeface(abel_ttf,Typeface.BOLD);
		tvVerse.setTypeface(abel_ttf,Typeface.BOLD);
		btnHelp.setTypeface(abel_ttf);
	}

	/**
	 * Updates entered wheel status
	 */
	private void updateStatus()
	{
		System.out.println(arrayString[getWheel(R.id.wheel_book).getCurrentItem()] + " - " + getWheel(R.id.wheel_chapter).getCurrentItem());
		a=1+getWheel(R.id.wheel_chapter).getCurrentItem();
		System.out.println("a is \n"+a);
	}

	/**
	 * Function to load the chapter numbers for each book from the bible
	 */
	private void getBookListFromJSON()
	{
		try
		{
			String result =loadJSONFromAsset(Constants.BIBLE_BOOKS_JSON);
			System.out.println("result "+result);
			JSONArray json = new JSONArray(result);
			for(int i=0;i<json.length();i++)
			{                        
				JSONObject e = json.getJSONObject(i);

				book_list.add(e.getString("book_name"));
				chapter_list.add(String.valueOf(e.getInt("number_of_chapters")));
				book_order_list.add(String.valueOf(e.getInt("book_order")));
				dam_id_list.add(e.getString("dam_id"));
				book_id_list.add(e.getString("book_id"));
			}

			arrayString = book_list.toArray(new String[book_list.size()]);
			chap_arr=chapter_list.toArray(new String[chapter_list.size()]);
			bookorder_arr=book_order_list.toArray(new String[book_order_list.size()]);
			damid_arr=dam_id_list.toArray(new String[dam_id_list.size()]);
			bookid_arr=book_id_list.toArray(new String[book_id_list.size()]);

			for(String element : chap_arr)
			{
				System.out.println(element);
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 *  Wheel scrolled listener
	 */
	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() 
	{
		public void onScrollingStarted(WheelView wheel)
		{
			wheelScrolled = true;
		}
		public void onScrollingFinished(WheelView wheel)
		{
			wheelScrolled = false;

			try {
				x=1+getWheel(R.id.wheel_chapter).getCurrentItem();
				int v=db.getBibleVerse(Integer.parseInt(bookorder_arr[getWheel(R.id.wheel_book).getCurrentItem()]),x);
				initVerseWheel(getWheel(R.id.wheel_verse), v, 0);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (NullPointerException e) {
				// TODO: handle exception
			}
		}
	};

	// Wheel changed listener
	private OnWheelChangedListener changedListener = new OnWheelChangedListener()
	{
		public void onChanged(WheelView wheel, int oldValue, int newValue) 
		{
			if (!wheelScrolled) 
			{
				System.out.println("new value of position is +\n "+newValue);
				x=1+getWheel(R.id.wheel_chapter).getCurrentItem();
				int v=db.getBibleVerse(Integer.parseInt(bookorder_arr[getWheel(R.id.wheel_book).getCurrentItem()]),x);
				initVerseWheel(getWheel(R.id.wheel_verse), v, 0);
				updateStatus();
			}
		}
	};

	private void initChapterWheel(WheelView w,int val,int index)
	{
		w.setViewAdapter(new ChapterAdapter(this, 1, val,index));
		//w.setCurrentItem(Integer.valueOf(chap_arr[val]));
		w.setCurrentItem(0);

		w.addChangingListener(changedListener);
		w.addScrollingListener(scrolledListener);
		//wheel.setCyclic(true);
		// the code below causes issue while fast scrollin...texts goes up
		//	w.setInterpolator(new AnticipateOvershootInterpolator());
	}

	private void initVerseWheel(WheelView w,int val,int index)
	{
		w.setViewAdapter(new VerseAdapter(this, 1, val,index));
	//	w.setCurrentItem(val);
		w.setCurrentItem(0);

		w.addChangingListener(changedLstnr);
		w.addScrollingListener(scrolledLstnr);
		//wheel.setCyclic(true);
		//	w.setInterpolator(new AnticipateOvershootInterpolator());
	}

	private WheelView getWheel(int id) 
	{
		return (WheelView) findViewById(id);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) 
		{
		case R.id.btn_help:
			/*	Intent intent=new Intent(getApplicationContext(), ScriptureOfflineActivity.class);
			startActivity(intent);*/

			Intent intent = new Intent(this, ScriptureOfflineActivity.class);
			replaceContentView("ScriptureOfflineActivity", intent);
			break;

		case R.id.btn_next:
			//btnNext.setEnabled(false);
			System.out.println("clicked next btn");
			
		/*	Intent i = new Intent(this,LoaderActivity.class);
			startActivity(i);*/
			CallVerseAsync();
			break;

		case R.id.btn_home:
			/*			Intent i=new Intent(this,ExplanatoryVideo_Activity.class);
			startActivity(i);*/
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
			Intent intent_home = new Intent(Wheel_Activity.this,HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
			break;

		default:
			break;
		}
	}

	public String getVerseURL()
	{
		int ch;
		ch=1+getWheel(R.id.wheel_chapter).getCurrentItem();
		int v;
		v=1+getWheel(R.id.wheel_verse).getCurrentItem();
		appended_URL=URL
				+damid_arr[getWheel(R.id.wheel_book).getCurrentItem()]
						+"&book_id="
						+bookid_arr[getWheel(R.id.wheel_book).getCurrentItem()]
								+"&chapter_id="
								+String.valueOf(ch)
								+"&verse_start="
								+String.valueOf(v)
								+"&v=2";
		/*		appended_URL=URL
				+"ENGESVO2ET"
						+"&book_id="
						+"Gen"
								+"&chapter_id="
								+"1"
								+"&verse_start="
								+"1"
								+"&v=2";*/
		//	Toast.makeText(ths,/* damid_arr[getWheel(R.id.wheel_book).getCurrentItem()]+" "+bookid_arr[getWheel(R.id.wheel_book).getCurrentItem()]+" "+ch+" "+v*/10 , Toast.LENGTH_LONG).show();
		System.out.println("url is "+appended_URL);
		return appended_URL; 
	}

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

	private class BookAdapter extends AbstractWheelTextAdapter 
	{
		//	Typeface abel_ttf1;
		/**
		 * Constructor
		 */
		protected BookAdapter(Context context) 
		{
			super(context, R.layout.custom_wheel_text, NO_RESOURCE);
			//	abel_ttf1=Typeface.createFromAsset(getAssets(), "fonts/Abel-Regular.ttf");

			setItemResource(R.layout.custom_wheel_text);
			setItemTextResource(R.id.tvScriptureName);
			setTextSize(16);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) 
		{
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount()
		{
			return arrayString.length;
		}

		@Override
		protected void configureTextView(TextView view)
		{
			super.configureTextView(view);
			//	view.setTextSize(40);
			//	view.setTypeface(abel_ttf);
			view.setTypeface(Typeface.SANS_SERIF);
		}
		@Override
		protected CharSequence getItemText(int index)
		{

			return arrayString[index];
		}
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class ChapterAdapter extends NumericWheelAdapter
	{
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public ChapterAdapter(Context context, int minValue, int maxValue, int current) 
		{
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view)
		{
			super.configureTextView(view);
			/*	if (currentItem == currentValue)
			{
				view.setTextColor(0xFF0000F0);
			}*/

			view.setTextColor(getResources().getColor(R.color.light_grey));
			//		view.setTypeface(abel_ttf);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent)
		{
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	OnWheelScrollListener scrolledLstnr = new OnWheelScrollListener() 
	{
		public void onScrollingStarted(WheelView wheel)
		{
			wheelScrolled = true;
		}
		public void onScrollingFinished(WheelView wheel)
		{
			wheelScrolled = false;
			updateStatus();
		}
	};

	/**
	 *  Wheel changed listener
	 */
	private OnWheelChangedListener changedLstnr = new OnWheelChangedListener()
	{
		public void onChanged(WheelView wheel, int oldValue, int newValue) 
		{
			if (!wheelScrolled) 
			{
				System.out.println("new value of position is +\n "+newValue);
				updateStatus();
			}
		}
	};

	/**
	 * Class  to set an adapter for the verse wheel
	 */
	private class VerseAdapter extends NumericWheelAdapter
	{

		/**
		 * Constructor
		 */
		public VerseAdapter(Context context, int minValue, int maxValue, int current) 
		{
			super(context, minValue, maxValue);
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view)
		{
			super.configureTextView(view);

			//			view.setTypeface(abel_ttf);
			view.setTextColor(getResources().getColor(R.color.light_grey));

			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent)
		{
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Function to fetch the chosen verse
	 */
	private void CallVerseAsync() 
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) 
		{
			h.postDelayed(r,30000);
			Intent i = new Intent(this,LoaderActivity.class);
			startActivity(i);
			// Network connection is available
			// calling method to parse Channel list API
			// Calling async task to get json
			new GetBibleVerse().execute();
		}
		else
		{
			// Network connection is not available
			Intent intent=new Intent(Wheel_Activity.this, DialogActivity.class);
			startActivity(intent);
		}
	}

	/**
	 *  Calling async task to get json
	 */
	public class GetBibleVerse extends AsyncTask<Void, Void, Void> 
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
			h.postDelayed(r,10000);*/
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				JsonParser sh = new JsonParser();

				// Making a request to url and getting response
				//String jsonStr = sh.makeURLCall(getVerseURL(), JSONParser.POST);

				int ch;
				ch=1+getWheel(R.id.wheel_chapter).getCurrentItem();
				int v;
				v=1+getWheel(R.id.wheel_verse).getCurrentItem();
				/*			appended_URL=URL
						+damid_arr[getWheel(R.id.wheel_book).getCurrentItem()]
								+"&book_id="
								+bookid_arr[getWheel(R.id.wheel_book).getCurrentItem()]
										+"&chapter_id="
										+ch
										+"&verse_start="
										+v
										+"&v=2";*/


				String jsonStr = sh.getWebDataPostRequestURL(damid_arr[getWheel(R.id.wheel_book).getCurrentItem()],
						bookid_arr[getWheel(R.id.wheel_book).getCurrentItem()],
						String.valueOf(ch), String.valueOf(v));
				JSONArray json = new JSONArray(jsonStr);
				System.out.println("array is "+json);
				Log.d("Response: ", "> " + json);

				if (json != null)
				{
					// Creates json object
					JSONObject jsonObj = json.getJSONObject(0);
					verse = jsonObj.getString(Constants.BIBLE_RETURN_VERSETEXT);
					verse_index= jsonObj.getString(Constants.BIBLE_RETURN_BOOKNAME)+" "+jsonObj.getString(Constants.BIBLE_RETURN_CHAPTERINDEX)+":"+jsonObj.getString(Constants.BIBLE_RETURN_VERSEINDEX);
					System.out.println("verse is "+verse);

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
			if(verse != null && verse_index !=null )
			{
				 
	/*			h.removeCallbacks(r);
				if (pd.isShowing() ) 
				{
					pd.dismiss();
				}*/
				//btnNext.setActivated(true);
				//btnNext.setEnabled(true);
				h.removeCallbacks(r);
				if(LoaderActivity.loader!=null){
				LoaderActivity.loader.finish();
				}
				Intent intent=new Intent(Wheel_Activity.this, Verse_Activity.class);
				
				//Create the bundle
				Bundle bundle = new Bundle();
				//Add your data from getFactualResults method to bundle
				bundle.putString("verse_text", verse);
				bundle.putString("verse_index", verse_index);
				//Add the bundle to the intent
				intent.putExtras(bundle);
				startActivity(intent);
			}
			/*if (pDialog.isShowing())
				pDialog.dismiss();

			h.removeCallbacks(r);
			if (pd.isShowing() )
			{
				pd.dismiss();
			}*/

		}
	}
	@Override
	protected void onResume() {
		
		super.onResume();

		int verse_flag = Constants.SET_VERSE_FLAG;
		if (verse_flag == 1) {
			//pd.show();
			Intent intent = new Intent(Wheel_Activity.this, CustomCameraActivity.class);
			intent.putExtra(OPEN_CAM, "open_cam_4");
			replaceContentView("CustomCameraActivity", intent);
			if(pd!=null){
				//if(pd.isShowing())
				//pd.dismiss();
			}
		}else if(verse_flag == 2){
			Intent intent = new Intent(Wheel_Activity.this, VideoPreviewOptionTab4.class);
			intent.putExtra(OPEN_CAM, "open_cam_4");
			replaceContentView("VideoPreviewOptionTab4", intent);
		}
		
		if(Constants.VERSE_LOADING == 1){
			Constants.VERSE_LOADING = 0;
			btnNext.performClick();
		}
		
	}
	
	/**
	 * Function to bring the view under the wizard
	 */
	private void replaceContentView(String id, Intent newIntent) 
	{
		@SuppressWarnings("deprecation")
		View view = ((ActivityGroup) this)
				.getLocalActivityManager()
				.startActivity(id,
						newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
						.getDecorView();
		((ActivityGroup) this).setContentView(view);
		//finishActivityFromChild(getCurrentActivity(), 0);

	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		System.out.println("onDestroy +wheelactivity");
		if(pd!=null){
			if(pd.isShowing())
				pd.dismiss();
		}
	}
}
