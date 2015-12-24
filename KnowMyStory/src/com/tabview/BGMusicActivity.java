/*
 * Know My Story 
 */
package com.tabview;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.onehope.mystory.R;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.database.DatabaseHelper;
import com.dialog.UploadSuccessDialog;
import com.supportclasses.Constants;
import com.test.socialnetwork.HomeScreen;

public class BGMusicActivity extends Activity implements OnItemClickListener, OnScrollListener, OnClickListener {
	ListView listview;
	private ArrayAdapter<String> listAdapter;
	boolean PlayBGMusic = false;
	MediaPlayer mediaPlayer;
	// Database Helper
    DatabaseHelper db;
    CustomListAdapter adapter;
    private int current = -1;
    TabViewActivity tabViewActivity;
    Button btn_next;
    Button btn_home;
    boolean SongSelectedFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bg_music_listview);
		InitializeObjects();
		SetOnClickListners();
		
		Field[] fields = R.raw.class.getFields();
		// ListView list;
		String sample[] = new String[R.raw.class.getFields().length];
		ArrayList<String> a = new ArrayList<String>();
		int count = 0;
		for (Field f : fields) {
			System.out.println("song names: "+f.getName());
			
			//sample[count] = f.getName();
			
			String songnames = DisplayMusicnames(f.getName());
			if(songnames!=null){
				a.add(songnames);
				count++;
			}
			
		}

		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	/*	a.remove(5);
		a.remove(6);
		a.remove(9);*/
		
		/*
		a.remove("into_the_wild_something_beautiful");
		a.remove("mystory_explanatory");
		a.remove("solemn_drone_good_to_be_here");
		*/
		
		//a.remove(7);
		//a.remove(11);
		
		
		/*List x = new ArrayList(Arrays.asList(sample));
		x.remove(String.valueOf(4));
		sample = (String[]) x.toArray();*/
		// Create ArrayAdapter using the planet list.
		
		//remove whitespaces acquired.
		
		
		
		
		listAdapter = new ArrayAdapter<String>(this, R.layout.listview_row,
				a);
		 adapter=new CustomListAdapter(BGMusicActivity.this, a, R.drawable.ic_launcher);
		listview.setAdapter(adapter);
		//String item = listview.getItemAtPosition(0).toString();
		//listAdapter.remove(item);
	
		// Set the ArrayAdapter as the ListView's adapter.
		//listview.setAdapter(listAdapter);
		listview.setOnItemClickListener(this);
		listview.setOnScrollListener(this);
		setInitialValues();
		
	
		
	}
	
	
	
	//helper method moved from Customlistadaopter to filter the strings.
	private String DisplayMusicnames(String itemname) {
		String musicName = null;
		
		if(itemname!=null){
			if (itemname.equals("a_a_no_song")) {
				musicName = getResources().getString(
						R.string.music_no_music);
			} else if (itemname.equals("a_grey_sky")) {
				musicName = getResources().getString(
						R.string.music_grey_sky);
			} else if (itemname.equals("arctic_underneath_it_all")) {
				musicName = getResources().getString(
						R.string.music_arctic);
			} else if (itemname.equals("darkness_falls")) {
				musicName = getResources().getString(
						R.string.music_darkness);
			} else if (itemname.equals("elevated")) {
				musicName = getResources().getString(
						R.string.music_elevated);
			} else if (itemname.equals("into_the_wild_something_beautiful")) {
				musicName = getResources().getString(
						R.string.music_into_wild);
			} else if (itemname.equals("outreach_changing")) {
				musicName = getResources().getString(
						R.string.music_outreach);
			} else if (itemname.equals("secret_place")) {
				musicName = getResources().getString(
						R.string.music_secret_place);
			} else if (itemname.equals("solemn_drone_good_to_be_here")) {
				musicName = getResources().getString(
						R.string.music_solemn_drone);
			}

		}
		return musicName;

	};
	
	
	
	
	
	
	
	
	
	
	
	
	private void setInitialValues() {
		tabViewActivity.enabletab(0);
		tabViewActivity.enabletab(1);
		tabViewActivity.enabletab(2);
		tabViewActivity.enabletab(3);
		tabViewActivity.enabletab(4);
		tabViewActivity.enabletab(5);
		tabViewActivity.enabletab(6);
	}
	private void SetOnClickListners() {
		btn_next.setOnClickListener(this);
		btn_home.setOnClickListener(this);
		
	}

	private void InitializeObjects() {
		db = new DatabaseHelper(this);
		listview = (ListView) findViewById(R.id.bg_listview);
		btn_next= (Button)findViewById(R.id.btn_next);
		tabViewActivity = new TabViewActivity();
		btn_home = (Button)findViewById(R.id.btn_home);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		SongSelectedFlag = true;
		System.out.println("position: "+position);
		SavePosition(position);
		
		String alreadySelectedBgMusic = db.getSelectedBGMusic(TabViewActivity.getProjectName());
		
		TextView txtView = (TextView) view.findViewById(R.id.Itemname);
		ImageView imageView = (ImageView)view.findViewById(R.id.icon);
		System.out.println("childcount: "+listview.getChildCount());
		System.out.println("getcount: "+listview.getCount());
		/*for(int i = 0; i<=7;i++){
			View selectedItem = listview.getChildAt(i); // the last one clicked
			selectedItem = selectedItem.findViewById(R.id.icon);
			selectedItem.setVisibility(View.INVISIBLE);
		}*/
		int visibleChildCount = (listview.getLastVisiblePosition() - listview.getFirstVisiblePosition()) + 1;
		System.out.println("visibleChildCount: "+visibleChildCount);
		System.out.println("last visible position: "+listview.getLastVisiblePosition());
		System.out.println("first visible position: "+listview.getFirstVisiblePosition());
		System.out.println("getItem name: "+listview.getItemAtPosition(position));
		for (int i = 0; i<visibleChildCount;i++) {
	        View last = listview.getChildAt(i); // the last one clicked
	        last.findViewById(R.id.icon).setVisibility(View.INVISIBLE); // kill it
	    }
		//imageView.setVisibility(View.INVISIBLE);
		//String fname = txtView.getText().toString().toLowerCase();
		String MusicName = getItem(txtView.getText().toString());

		Log.d("music name",MusicName);
		System.out.println("itemId "+listview.getItemIdAtPosition(position));
		//View selectedItem = listview.getChildAt(position); // the last one clicked
		//selectedItem = selectedItem.findViewById(R.id.icon);
		View selectedItem = getViewByPosition(position,listview);
		System.out.println("selectedItem: "+selectedItem);
		selectedItem = selectedItem.findViewById(R.id.icon);
		current = position;
		System.out.println("BgMusic_alreadyselected: "+alreadySelectedBgMusic);
		System.out.println("BgMusic_current: "+MusicName);
		//Insert selected Bg music file name to DB
		db.updateSelectedBGMusic(MusicName);
		
		//Update DB to MERGED_STATUS_NEW to notify the change in recording, so that it can re-merge
		db.updateMergedStatus(Constants.MERGED_STATUS_NEW);
		
		int resID = getResources()
				.getIdentifier(MusicName, "raw", getPackageName());
		
		// String currentItemSelected = listview.getItemAtPosition(position).toString();
		if(MusicName.equals(alreadySelectedBgMusic)) {
			if(mediaPlayer!=null){
				if(mediaPlayer.isPlaying()){
					//2nd click , click on same item which is playing 
					selectedItem.setVisibility(View.INVISIBLE);
					stopPlaying();
				}
			}else{
				
				selectedItem.setVisibility(View.VISIBLE);
				mediaPlayer = MediaPlayer.create(this, resID);
				mediaPlayer.start();
			}
		}else{
			
			if(mediaPlayer!=null){
				if(mediaPlayer.isPlaying()){
					//clickkkkk
					
					stopPlaying();
					mediaPlayer = MediaPlayer.create(this, resID);
					
					mediaPlayer.start();
					
					selectedItem.setVisibility(View.VISIBLE);
				}
			}else{
				//1st click
				selectedItem.setVisibility(View.VISIBLE); 
		        
				mediaPlayer = MediaPlayer.create(this, resID);
				mediaPlayer.start();
			}
		}
		//adapter.notifyDataSetChanged();
	}

	private void SavePosition(int position) {
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("ITEM_SELECTED", position);
		editor.commit();
		
	}
	private String getItem(String fname) {
		String musicItem = null;
		if(fname!=null){
			if (fname.equals(getResources().getString(R.string.music_no_music))) {
				musicItem = "a_a_no_song";
			} else if (fname.equals(getResources().getString(
					R.string.music_grey_sky))) {
				musicItem = "a_grey_sky";
			} else if (fname.equals(getResources().getString(
					R.string.music_arctic))) {
				musicItem = "arctic_underneath_it_all";
			} else if (fname.equals(getResources().getString(
					R.string.music_darkness))) {
				musicItem = "darkness_falls";
			} else if (fname.equals(getResources().getString(
					R.string.music_elevated))) {
				musicItem = "elevated";
			} else if (fname.equals(getResources().getString(
					R.string.music_into_wild))) {
				musicItem = "into_the_wild_something_beautiful";
			} else if (fname.equals(getResources().getString(
					R.string.music_outreach))) {
				musicItem = "outreach_changing";
			} else if (fname.equals(getResources().getString(
					R.string.music_secret_place))) {
				musicItem = "secret_place";
			} else if (fname.equals(getResources().getString(
					R.string.music_solemn_drone))) {
				musicItem = "solemn_drone_good_to_be_here";
			}
		}
		return musicItem;
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		Constants.SET_CURRENT_TAB = 6;
		//GetPreSelectedItem();
		
			if(db.getSelectedBGMusic(TabViewActivity.getProjectName())!=null){
				if(!(db.getSelectedBGMusic(TabViewActivity.getProjectName()).equals(""))){
					SongSelectedFlag = true;
					listview.setItemChecked(GetPreSelectedItem(), true);
				}
			}
		
		
		//listview.getChildAt(0).setBackgroundColor(getResources().getColor(R.color.orange));
		//listview.setSelection(0);
	}

	private int GetPreSelectedItem() {
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
	//	int defaultValue = getResources().getInteger(0);
		int selected_position = sharedPref.getInt("ITEM_SELECTED", 0);
		return selected_position;
		
	}
	private View getViewByPosition(int position, ListView listview) {
		final int firstListItemPosition = listview.getFirstVisiblePosition();
	    final int lastListItemPosition = firstListItemPosition + listview.getChildCount() - 1;

	    if (position < firstListItemPosition || position > lastListItemPosition ) {
	    	System.out.println("inside 111");
	        return listview.getAdapter().getView(position, null, listview);
	    } else {
	    	System.out.println("inside 222");
	        final int childIndex = position - firstListItemPosition;
	        System.out.println("ChildIndex: "+childIndex);
	        return listview.getChildAt(childIndex);
	    }
	}

	private void stopPlaying() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.reset();
			mediaPlayer.release();
			mediaPlayer = null;
       }
		
	}
	@Override
	protected void onPause() {
		
		super.onPause();
		stopPlaying();
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		stopPlaying();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:{
			
			if(SongSelectedFlag == false){
				//Show Mandatory field Dialog
				SuccessAlertDialog(getResources().getString(R.string.music_select_error),getResources().getString(R.string.music_select_error_heading));
				
			}else{
			
				Constants.SET_CURRENT_TAB = 7;
				//Set the track of Enabled tabs. 
				Constants.ENABLE_TAB7 = 1;
				tabViewActivity.setCurrentActivityTab(7);
				tabViewActivity.enabletab(7);
				if(db.getCompletedScreens()<7){
				db.updateCompletedScreen(7);
			}
			}
		}
			break;
		case R.id.btn_home:{
			/*Constants.SHOW_PREVIEW_TAB1 = 0;
			Constants.SHOW_PREVIEW_TAB2 = 0;
			Constants.SHOW_PREVIEW_TAB3 = 0;
			Constants.SHOW_PREVIEW_TAB4 = 0;
			Constants.SHOW_PREVIEW_TAB5 = 0;*/
			Intent intent_home = new Intent(BGMusicActivity.this,
					HomeScreen.class);
			intent_home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent_home);
			finish();
		}
			
		default:
			break;
		}
		
	}
	private void SuccessAlertDialog(String msg,String title)
	{
		DialogFragment newFragment = UploadSuccessDialog.newInstance(BGMusicActivity.this,msg,title,"doNothing");
		newFragment.show(getFragmentManager(), "dialog");
	}
}
