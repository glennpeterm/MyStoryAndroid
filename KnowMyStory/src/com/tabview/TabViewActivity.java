/*
 * Know My Story 
 */
package com.tabview;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.database.DatabaseHelper;
import com.database.Model;
import com.scriptures.ScriptureOfflineActivity;
import com.scriptures.Wheel_Activity;
import com.supportclasses.Constants;
import com.test.socialnetwork.AboutMyStory_Activity;
import com.videoeditor.media.CustomCameraActivity;
import com.videoeditor.media.VideoPreviewOption;
import com.videoeditor.media.VideoPreviewOptionTab2;
import com.videoeditor.media.VideoPreviewOptionTab3;
import com.videoeditor.media.VideoPreviewOptionTab4;
import com.videoeditor.media.VideoPreviewOptionTab5;
import net.onehope.mystory.R;

import dogtim.android.videoeditor.util.FileUtils;
import dogtim.android.videoeditor.util.VideoEditorActvityLolipop;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class TabViewActivity extends TabActivity {
	static TabHost tabHost;
	static TabWidget tabWidget;
	HorizontalScrollView tabsHorizontalScrollView;
	public static final String CURRENT_ACTIVITY_INFO = "current_activity_info";
	static SharedPreferences prefs;
	String OPEN_CAM = "open_cam";
	// Database Helper
	 DatabaseHelper db;
	View tabIndicator_tab0;
	View tabIndicator_tab1;
	View tabIndicator_tab2;
	View tabIndicator_tab3;
	View tabIndicator_tab4;
	View tabIndicator_tab5;
	View tabIndicator_tab6;
	View tabIndicator_tab7;
	View tabIndicator_tab8;
	View tabIndicator_tab9;

	boolean tab1Done = false;
	boolean tab2Done = false;
	boolean tab3Done = false;
	boolean tab4Done = false;
	boolean tab5Done = false;
	boolean tab6Done = false;
	boolean tab7Done = false;
	boolean tab8Done = false;
	boolean tab9Done = false;
	boolean tab3ticked = false;
	boolean tab4ticked = false;
	boolean tab5ticked = false;
	boolean tab6ticked = false;
	boolean tab7ticked = false;
	boolean tab8ticked = false;
	boolean tab9ticked = false;
	boolean tab10ticked = false;

	static String MY_PREFS_NAME = "pref_string";

	public static final String PARAM_CREATE_PROJECT_NAME = "name";
	public static final String PARAM_OPEN_PROJECT_PATH = "path";
	private static final int REQUEST_CODE_CREATE_PROJECT = 0;

	public Typeface abel_ttf;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.main);

		abel_ttf=Typeface.createFromAsset(this.getAssets(), Constants.FONT_ABEL_REGULAR);

		System.out.println("oncreate called in TabViewActivity...");
		db = new DatabaseHelper(this);
		prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE); 
		tabsHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.tabsHorizontalScrollView);
		

		 tabHost = getTabHost(); 
		 tabWidget = tabHost.getTabWidget();
		
	
		TabSpec tabSpecTab0 = null;
		TabSpec tabSpecTab1 = null;
		TabSpec tabSpecTab2 = null;
		TabSpec tabSpecTab3 = null;
		TabSpec tabSpecTab4 = null;
		TabSpec tabSpecTab5 = null;
		
		/**
		 * Adding all tabs..start...
		 */
		
		/*
		 * Loading First tab view
		 * TAB0
		 * Name of the tab : Title
		 */
	
		//ReloadTabsSelected();
		if(Constants.ENABLED_TABS_STATE == 1){
			//Finish your Story
			//tabs 1 already selected
			 tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}else{
		
		 tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		View tabIndicator_tab0_unselected = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		
		if(Constants.ENABLED_TABS_STATE == 2){
			//Finish your Story
			//tabs 1,2 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				
		}else{
			
		tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}

		View tabIndicator_tab1_unselected = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);

		if(Constants.ENABLED_TABS_STATE == 3){
			//Finish your Story
			//tabs 1,2,3 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			
		}else{
			tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 4){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}else{
			tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 5){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
				//((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_5));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			
			
		}else{
			tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_5));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 6){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
				//((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_5));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_6));
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			
			
		}else{
			tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_6));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}

		if(Constants.ENABLED_TABS_STATE == 7){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
			
			
		}else{
			tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_7));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 8){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
		}else{
			tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_8));
		}
		
		if(Constants.ENABLED_TABS_STATE == 9){
			
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab8 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_region));
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
			
		}else{
			tabIndicator_tab8 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_region));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_9));
		}
		
		if(Constants.ENABLED_TABS_STATE == 10){
			System.out.println("ENABLED_TABS_STATE: "+Constants.ENABLED_TABS_STATE);
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab8 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_region));
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			
				tabIndicator_tab9 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_merge));
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setText("");
		}else{
			tabIndicator_tab9 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_merge));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_10));
		}
		Intent intentTab0 = new Intent().setClass(this, Tab1Activity.class);
		tabSpecTab0 = tabHost
			.newTabSpec("Tab0")
			//.setIndicator("Title")
			//.setIndicator("Title", getResources().getDrawable(R.drawable.tab_active))
			.setIndicator(tabIndicator_tab0)
			.setContent(intentTab0);
		
		
		//int TAB1_STATUS = Constants.SHOW_PREVIEW_TAB1;
		int TAB1_STATUS = db.getShowPreviewTabConstant1();
		System.out.println("TAB1_STATUS: "+TAB1_STATUS);
		//if(isVideo1Present!=null){
		switch (TAB1_STATUS) {
		case 1:{
			System.out.println("Preview activity...");
			Intent intentTab1 = new Intent().setClass(TabViewActivity.this, VideoPreviewOption.class);
			//intentTab1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Constants.CURRENT_SCREEN = 1;
			intentTab1.putExtra(OPEN_CAM, "open_cam_1");
			 tabSpecTab1 = tabHost
				.newTabSpec("Tab1")
				.setIndicator(tabIndicator_tab1)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_android_config))
				.setContent(intentTab1);
		}
		break;

		default:{
			
			//System.out.println("videopath from db: "+isVideo1Present);
			 Intent intentTab1 = new Intent().setClass(TabViewActivity.this, CustomCameraActivity.class);
			// intentTab1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intentTab1.putExtra(OPEN_CAM, "open_cam_1");
				Constants.CURRENT_SCREEN = 1;
				//db.updateCurrentScreen(new Model("open_cam_1"));
				db.updateCurrentScreen("open_cam_1");
				 tabSpecTab1 = tabHost
					.newTabSpec("Tab1")
					.setIndicator(tabIndicator_tab1)
					//.setIndicator("", getResources().getDrawable(R.drawable.icon_android_config))
					.setContent(intentTab1);
		}
			break;
		}
		
		/*
		 * Loading Second tab view
		 * TAB1
		 * Name of the tab : Introduction
		 */
		//int TAB2_STATUS = Constants.SHOW_PREVIEW_TAB2;
		int TAB2_STATUS = db.getShowPreviewTabConstant2();
		System.out.println("TAB2_STATUS: "+TAB2_STATUS);
		//if(isVideo1Present!=null){
		switch (TAB2_STATUS) {
		case 1:{
			//System.out.println("videopath from db: "+isVideo2Present);
			Intent intentTab2 = new Intent().setClass(TabViewActivity.this, VideoPreviewOptionTab2.class);
			Constants.CURRENT_SCREEN = 2;
			intentTab2.putExtra(OPEN_CAM, "open_cam_2");
			 tabSpecTab2 = tabHost
				.newTabSpec("Tab2")
				.setIndicator(tabIndicator_tab2)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_android_config))
				.setContent(intentTab2);
		}
			break;

		default:{
			System.out.println("Preview activity...");
			Intent intentTab2 = new Intent().setClass(TabViewActivity.this, CustomCameraActivity.class);
			Constants.CURRENT_SCREEN = 2;
			intentTab2.putExtra(OPEN_CAM, "open_cam_2");
			 tabSpecTab2 = tabHost
				.newTabSpec("Tab2")
				.setIndicator(tabIndicator_tab2)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_apple_config))
				.setContent(intentTab2);
		}
			break;
		}
		
		/*
		 * Loading Third tab view
		 * TAB2
		 * Name of the tab : Struggle Moment
		 */
		//int TAB3_STATUS = Constants.SHOW_PREVIEW_TAB3;
		int TAB3_STATUS = db.getShowPreviewTabConstant3();
		System.out.println("TAB3_STATUS: "+TAB3_STATUS);
		//if(isVideo1Present!=null){
		switch (TAB3_STATUS) {
		case 1:{
			Intent intentTab3 = new Intent().setClass(TabViewActivity.this, VideoPreviewOptionTab3.class);
			intentTab3.putExtra(OPEN_CAM, "open_cam_3");
			 tabSpecTab3 = tabHost
				.newTabSpec("Tab3")
				.setIndicator(tabIndicator_tab3)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_android_config))
				.setContent(intentTab3);
		}
			break;

		default:{
			Intent intentTab3 = new Intent().setClass(TabViewActivity.this, CustomCameraActivity.class);
			intentTab3.putExtra(OPEN_CAM, "open_cam_3");
			 tabSpecTab3 = tabHost
				.newTabSpec("Tab3")
				.setIndicator(tabIndicator_tab3)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_windows_config))
				.setContent(intentTab3);
		}
			break;
		}
		
		/*
		 * Loading Fourth tab view
		 * TAB3
		 * Name of the tab : How God Intervened
		 */
		//int TAB4_STATUS = Constants.SHOW_PREVIEW_TAB4;
		int TAB4_STATUS = db.getShowPreviewTabConstant4();
		System.out.println("TAB4_STATUS: "+TAB4_STATUS);
		//if(isVideo1Present!=null){
		switch (TAB4_STATUS) {
		case 1:{
			
			System.out.println("WHEEL_ACTIVITY: "+Constants.WHEEL_ACTIVITY);
			if(Constants.WHEEL_ACTIVITY == 1){
				Constants.SET_VERSE_FLAG = 0;
				Intent intentTab4=new Intent(TabViewActivity.this, Wheel_Activity.class);
				intentTab4.putExtra(OPEN_CAM, "open_cam_4");
				 tabSpecTab4 = tabHost
					.newTabSpec("Tab4")
					.setIndicator(tabIndicator_tab4)
					//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
					.setContent(intentTab4);
			}else if(Constants.WHEEL_ACTIVITY == 2){
				Intent intentTab4=new Intent(TabViewActivity.this, ScriptureOfflineActivity.class);
				intentTab4.putExtra(OPEN_CAM, "open_cam_4");
				 tabSpecTab4 = tabHost
					.newTabSpec("Tab4")
					.setIndicator(tabIndicator_tab4)
					//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
					.setContent(intentTab4);
				
			}else{
			Intent intentTab4 = new Intent().setClass(TabViewActivity.this, VideoPreviewOptionTab4.class);
			intentTab4.putExtra(OPEN_CAM, "open_cam_4");
			 tabSpecTab4 = tabHost
				.newTabSpec("Tab4")
				.setIndicator(tabIndicator_tab4)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_android_config))
				.setContent(intentTab4);
			}
		}
			break;

		default:{
/*		Intent intentTab4 = new Intent().setClass(this, CustomCameraActivity.class);
			intentTab4.putExtra(OPEN_CAM, "open_cam_4");
			 tabSpecTab4 = tabHost
				.newTabSpec("Tab4")
				.setIndicator(tabIndicator_tab4)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
				.setContent(intentTab4);*/
			Constants.SET_VERSE_FLAG = 0;
			Intent intentTab4=new Intent(TabViewActivity.this, Wheel_Activity.class);
			intentTab4.putExtra(OPEN_CAM, "open_cam_4");
			 tabSpecTab4 = tabHost
				.newTabSpec("Tab4")
				.setIndicator(tabIndicator_tab4)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
				.setContent(intentTab4);
			
		}
			break;
		}
		
		/*
		 * Loading Fifth tab view
		 * TAB4
		 * Name of the tab : Favourite Scripture
		 */
		//int TAB5_STATUS = Constants.SHOW_PREVIEW_TAB5;
		
		int TAB5_STATUS = db.getShowPreviewTabConstant5();
		System.out.println("TAB5_STATUS: "+TAB5_STATUS);
		//if(isVideo1Present!=null){
		switch (TAB5_STATUS) {
		case 1:{
			Intent intentTab5 = new Intent().setClass(TabViewActivity.this, VideoPreviewOptionTab5.class);
			intentTab5.putExtra(OPEN_CAM, "open_cam_5");
			 tabSpecTab5 = tabHost
				.newTabSpec("Tab5")
				.setIndicator(tabIndicator_tab5)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_android_config))
				.setContent(intentTab5);
		}
			break;

		default:{
			Intent intentTab5 = new Intent().setClass(this, CustomCameraActivity.class);
			intentTab5.putExtra(OPEN_CAM, "open_cam_5");
			 tabSpecTab5 = tabHost
				.newTabSpec("Tab5")
				.setIndicator(tabIndicator_tab5)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
				.setContent(intentTab5);
		}
			break;
		}
		 
		/*
		 * Loading Sixth tab view
		 * TAB5
		 * Name of the tab : Music
		 */
		Intent intentTab6 = new Intent().setClass(TabViewActivity.this, BGMusicActivity.class);
		TabSpec tabSpecTab6 = tabHost
			.newTabSpec("Tab6")
			.setIndicator(tabIndicator_tab6)
			//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
			.setContent(intentTab6); 
			
		/*
		 * Loading SEVENTH tab view
		 * TAB6
		 * Name of the tab : Description
		 */
		Intent intentTab7= new Intent().setClass(TabViewActivity.this, DescriptionActivity.class);
		TabSpec tabSpecTab7 = tabHost
			.newTabSpec("Tab7")
			.setIndicator(tabIndicator_tab7)
			//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
			.setContent(intentTab7); 
		
		/*
		 * Loading EIGTH tab view
		 * TAB7
		 * Name of the tab : Region
		 */
		Intent intentTab8= new Intent().setClass(TabViewActivity.this, RegionActivity.class);
		TabSpec tabSpecTab8 = tabHost
			.newTabSpec("Tab8")
			.setIndicator(tabIndicator_tab8)
			//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
			.setContent(intentTab8); 
		/*
		 * Loading NINTH tab view
		 * TAB8
		 * Name of the tab : UPLOAD
		 */
		
		TabSpec tabSpecTab9 = null;
		try {
			//Intent intentTab7 = new Intent().setClass(this, Tab4Activity.class);
			
				Intent intentTab9 = new Intent().setClass(TabViewActivity.this, VideoEditorActvityLolipop.class);
				//if(Constants.MERGED_STATUS_CHANGE == 1){
					//System.out.println("Change noted...");
				intentTab9.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//}else{
				//	System.out.println("No Change noted...");
				//}
				//intentTab1.putExtra(OPEN_CAM, "open_cam_1");
				intentTab9.setAction(Intent.ACTION_INSERT);
				intentTab9.putExtra(PARAM_CREATE_PROJECT_NAME, "storyname");
				final String projectPath = FileUtils.createNewProjectPath(TabViewActivity.this);
				intentTab9.putExtra(PARAM_OPEN_PROJECT_PATH, projectPath);
				//startActivityForResult(intent, REQUEST_CODE_CREATE_PROJECT);
				 
			
			tabSpecTab9 = tabHost
				.newTabSpec("Tab9")
				.setIndicator(tabIndicator_tab9)
				//.setIndicator("", getResources().getDrawable(R.drawable.icon_blackberry_config))
				.setContent(intentTab9);
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		 * Adding all tabs..end...
		 */
		
		tabHost.addTab(tabSpecTab0);
		tabHost.addTab(tabSpecTab1);
		tabHost.addTab(tabSpecTab2);
		tabHost.addTab(tabSpecTab3);
		tabHost.addTab(tabSpecTab4);
		tabHost.addTab(tabSpecTab5);
		tabHost.addTab(tabSpecTab6);
		tabHost.addTab(tabSpecTab7);
		tabHost.addTab(tabSpecTab8);
		tabHost.addTab(tabSpecTab9);
		
		getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.actionbar_tab_indicator);
		
		
		for(int i = 0; i<10 ; i++){
			System.out.println("Disable tabs...");
			
			getTabHost().getTabWidget().getChildAt(i).setEnabled(false);
			getTabHost().getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.actionbar_tab_indicator);
			
		}
		int CompletedScree = db.getCompletedScreens();
		for(int i = 0;i<CompletedScree;i++){
			getTabHost().getTabWidget().getChildAt(i).setEnabled(true);
		}
		/*int com = db.getCompletedScreens();
		for(int i = 1; i<com ; i++){
			System.out.println("Disable tabs...");
			getTabHost().getTabWidget().getChildAt(i).setEnabled(true);
		}*/
		
		if(Constants.ENABLED_TABS_STATE>0){
			for(int i = 0; i<Constants.ENABLED_TABS_STATE;i++){
				getTabHost().getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_enabled);
			}
		}
		/*
		 * To fix the size of each tab seperately use below code
		 */
		//tabWidget.getChildAt(0).setLayoutParams(new
        //        LinearLayout.LayoutParams(20,50));
		
		/*switch (Constants.SHOW_PREVIEW_TAB1) {
		case 1:
			getTabHost().getTabWidget().getChildAt(1).setEnabled(true);
			break;

		default:
			getTabHost().getTabWidget().getChildAt(1).setEnabled(false);
			break;
		}*/
		
		
		//getTabHost().getTabWidget().getChildAt(1).setEnabled(false);
		//getTabHost().getTabWidget().getLayoutParams().width = 500;
		/*for(int i =0;i<4;i++){
			
		getTabHost().getTabWidget().getChildAt(i).getLayoutParams().width = 300;
		}*/
		//set Windows tab as default (zero based)
		/*if(isVideo1Present!=null && isVideo2Present==null){
			tabHost.setCurrentTab(0);
		}else if(isVideo2Present!=null && isVideo3Present==null){
			tabHost.setCurrentTab(1);
		}else if(isVideo3Present!=null && isVideo4Present==null ){
			tabHost.setCurrentTab(2);
		}else if(isVideo4Present!=null && isVideo5Present==null ){
			tabHost.setCurrentTab(3);
		}else if(isVideo5Present!=null && isVideo6Present==null ){
			tabHost.setCurrentTab(4);
		}else{
			
			tabHost.setCurrentTab(0);
		}*/
		//int 
		System.out.println("currentTab: "+Constants.SET_CURRENT_TAB);
		switch (Constants.SET_CURRENT_TAB) {
		case 0:
			System.out.println("Current tab = 0");
			tabHost.setCurrentTab(0);
			
			break;
		case 1:
			tabHost.setCurrentTab(1);
			break;
		case 2:
			tabHost.setCurrentTab(2);
			break;
		case 3:
			tabHost.setCurrentTab(3);
			break;
		case 4:
			tabHost.setCurrentTab(4);
			break;
		case 5:
			tabHost.setCurrentTab(5);
			break;
		case 6:
			tabHost.setCurrentTab(6);
			//tabHost.getTabWidget().getChildAt(6).setFocusableInTouchMode(true);
			System.out.println("current tab 6 ...");
			break;
		case 7:
			tabHost.setCurrentTab(7);
			break;
		case 8:
			tabHost.setCurrentTab(8);
			break;
		case 9:
			tabHost.setCurrentTab(9);
			break;
		case 10:
			tabHost.setCurrentTab(9);
			break;
		
		default:
			
			break;
		}
		//tabHost.setCurrentTab(0);
		//tabHost.getTabWidget().getChildAt(0).setFocusableInTouchMode(true);
		
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				System.out.println("tab changed...");
				View tabView = tabHost.getCurrentTabView();
			    int scrollPos = tabView.getLeft() - (tabsHorizontalScrollView.getWidth() - tabView.getWidth()) / 2;
			    System.out.println("scrollPos2: "+scrollPos);
			    tabsHorizontalScrollView.smoothScrollTo(scrollPos, 0);
			    tabView.setBackgroundResource(R.drawable.tab_enabled_bg);
			    ((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			    ((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
				tabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			    
				//_______________________________________start_____________________________________
				
				if(tabId.equals("Tab1")) {
					//Intro tab
					((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
					
					//ReloadTabStatus();
				}else if(tabId.equals("Tab2")){
					//Setup tab
					((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
					//ReloadTabStatus();
				}else if(tabId.equals("Tab3")){
					//Setup tab
					((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
					//ReloadTabStatus();
				}else if(tabId.equals("Tab4")){
					//Setup tab
					((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_5));
				}else if(tabId.equals("Tab5")){
					//Setup tab
					((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_6));
				}else if(tabId.equals("Tab6")){
					//Setup tab
					((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(6).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_7));
				}else if(tabId.equals("Tab7")){
					//Setup tab
					((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(7).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_8));
				}else if(tabId.equals("Tab8")){
					//Setup tab
					((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					getTabHost().getTabWidget().getChildAt(8).setBackgroundResource(R.drawable.tab_enabled_bg);
					((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_9));
				}else if(tabId.equals("Tab9")){
					//Setup tab
					((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
					//((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
					((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
					((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setText("");
					getTabHost().getTabWidget().getChildAt(9).setBackgroundResource(R.drawable.tab_enabled_bg);
				}
				ReloadTabStatus(1);
				
				
			}
		});
			
	}

	private void ReloadTabsSelected() {
		if(Constants.ENABLED_TABS_STATE == 1){
			//Finish your Story
			//tabs 1 already selected
			 tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				//((RelativeLayout) tabIndicator_tab0.findViewById(R.id.tab_rl)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_enabled));
		}else{
		
		 tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		//((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
		//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
		((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		/*View tabIndicator_tab0_unselected = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		//((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
		//((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
		((TextView) tabIndicator_tab0_unselected.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);*/
		if(Constants.ENABLED_TABS_STATE == 2){
			//Finish your Story
			//tabs 1,2 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
				//((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				
		}else{
			
		tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
		//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
		((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}

		/*View tabIndicator_tab1_unselected = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
		//((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
		//((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
		((TextView) tabIndicator_tab1_unselected.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
*/
		if(Constants.ENABLED_TABS_STATE == 3){
			//Finish your Story
			//tabs 1,2,3 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
				//((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			
		}else{
			tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 4){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
				//((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}else{
			tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 5){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
				//((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_5));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			
			
		}else{
			tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_5));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 6){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_1));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				//((TextView) tabIndicator_tab1_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_orange_circle));
				//((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_2));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_3));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_4));
				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_5));
				//((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_6));
				//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			
			
		}else{
			tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_6));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}

		if(Constants.ENABLED_TABS_STATE == 7){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
			
			
		}else{
			tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			//((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_7));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
		if(Constants.ENABLED_TABS_STATE == 8){
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
		}else{
			tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_8));
		}
		
		if(Constants.ENABLED_TABS_STATE == 9){
			
			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab8 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_region));
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
			
		}else{
			tabIndicator_tab8 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_region));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setText(getResources().getString(R.string.tab_count_9));
		}
		
		if(Constants.ENABLED_TABS_STATE == 10){

			//Finish your Story
			//tabs 1,2,3,4 already selected
			tabIndicator_tab0 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			 tabIndicator_tab0.setBackgroundColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_title));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab1 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_intro));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				
				tabIndicator_tab2 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_setup));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));				//((TextView) tabIndicator_tab2_selected.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				
				tabIndicator_tab3 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_god_moment));
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));				//((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
				
				tabIndicator_tab4 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_the_word));
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				
				tabIndicator_tab5 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_conclusion));
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab6 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_music));
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab7 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_description));
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));	
				
				tabIndicator_tab8 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_region));
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			
				tabIndicator_tab9 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_merge));
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));	
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
				((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setText("");
		}else{
			tabIndicator_tab9 = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTypeface(abel_ttf);
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setText(getResources().getString(R.string.tab_merge));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.light_grey));
			//((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_grey_circle));
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setTypeface(abel_ttf);
		}
		
	}

	public static void setCurrentActivityTab(int i) {
		//tabHost.clearAllTabs();
		tabHost.setCurrentTab(i);
		tabHost.setFocusable(true);
		//tabHost.getTabWidget().getChildAt(i).setFocusableInTouchMode(true);
	}
	public static String getProjectPath(){
		//if(path!=null)
		//path = "/storage/sdcard0/KnowMyStory/b";

		  String name = prefs.getString("path_name", null);
		  //String name =  db.getProjectPath();
		  System.out.println("name inTabviewAcitivity: "+name);
		// String name = 
		return name;
	}
	public static String getProjectName(){
		//if(path!=null)
		//path = "/storage/sdcard0/KnowMyStory/b";

		  String projectName = prefs.getString("project_name", null);
		 
		return projectName;
	}

	public void hideAllTabs() {
		/*for(int i =0;i<=7;i++){
		tabHost.getTabWidget().getChildAt(i).setVisibility(View.GONE);
		}*/
		tabWidget.setVisibility(TabWidget.INVISIBLE);  
		
		
		
	}
	
	
	/*
	 * function to disable or enable the tabviewhost as soon asthe record button is clicked.
	 * **/
	

	public void showAllTabs() {
		tabWidget.setVisibility(TabWidget.VISIBLE);  
		
	}

	public void enabletab(int i) {
		tabWidget.getChildAt(i).setEnabled(true);
		//getTabHost().getTabWidget().getChildAt(i).setEnabled(true);
		//tabWidget.getChildAt(i-1).setBackgroundResource(R.drawable.tab_enabled_bg);
		
		
	}
	public void DisableTabs() {
		for(int i = 1; i<10 ; i++){
			tabWidget.getChildAt(i).setEnabled(false);
		}
		
		
		
	}

	public void setButtonActive() {
		//tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_active);
		
		for(int i=0;i<tabWidget.getChildCount();i++) {
			//tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_inactive); //unselected
			
	    }
		//tabWidget.getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab_active); // selected
		
		
	}	
	@Override
	protected void onResume() {
		
		super.onResume();
		System.out.println("onResume called Tabviewactivity..");
		/*View tabView = tabHost.getCurrentTabView();
	    int scrollPos = tabView.getLeft() - (tabsHorizontalScrollView.getWidth() - tabView.getWidth()) / 2;
	    tabsHorizontalScrollView.smoothScrollTo(5, 0);*/
		ReloadTabStatus(0);
	/*
	 * Scroll to Active tab
	 */
		tabsHorizontalScrollView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				View tabView = tabHost.getCurrentTabView();
			    int scrollPos = tabView.getLeft() - (tabsHorizontalScrollView.getWidth() - tabView.getWidth()) / 2;
			    System.out.println("scrollPos2: "+scrollPos);
			    tabsHorizontalScrollView.scrollTo(scrollPos, 0);
				//tabsHorizontalScrollView.smoothScrollTo(788, 0);
				
			}
		}, 100);
	}

	private void ReloadTabStatus(int i) {
		int CompletedScreen = db.getCompletedScreens();
		System.out.println("Completed screen: "+CompletedScreen);
		switch (CompletedScreen+i) {
		case 1:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			break;
		case 2:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			break;
		case 3:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			break;
			
		case 4:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			break;
			
		case 5:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText("");
			break;
			
		case 6:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText("");
			break;
			
		case 7:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(6).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText("");
			break;
			
		case 8:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(6).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(7).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setText("");
			break;
			
		case 9:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(6).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(7).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(8).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setText("");
			break;
			
		case 10:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(6).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(7).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(8).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(9).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setText("");
			break;
		case 11:
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab0.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab1.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab2.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab3.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab4.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab5.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(6).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab6.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(7).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab7.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(8).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab8.findViewById(R.id.tab_icon)).setText("");
			
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_text)).setTextColor(getResources().getColor(R.color.orange));
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_icon_tick));
			getTabHost().getTabWidget().getChildAt(9).setBackgroundResource(R.drawable.tab_enabled_bg);
			((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setText("");
			//((TextView) tabIndicator_tab9.findViewById(R.id.tab_icon)).setText("");
			break;
		default:
			break;
		}
		
		
	}
}