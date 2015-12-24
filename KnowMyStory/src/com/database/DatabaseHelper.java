/*
 * Know My Story 
 */
package com.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	// Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "mystory";
 
    // Table Names
    private static final String TABLE_KMS = "kms";
 
    // Column names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VIDEO1 = "video1";
    private static final String KEY_VIDEO2 = "video2";
    private static final String KEY_VIDEO3 = "video3";
    private static final String KEY_VIDEO4 = "video4";
    private static final String KEY_VIDEO5 = "video5";
    private static final String KEY_VIDEO6 = "video6";
    private static final String KEY_VIDEO7 = "video7";
    private static final String KEY_BG_MUSIC = "bg_music";
    private static final String KEY_OUTPUT_VIDEO = "output_video";
    private static final String KEY_MERGED_VIDEO_ONLY = "merged_video_only";
    private static final String KEY_MERGED_STATUS = "merged_status";
    private static final String KEY_MERGED_PROGRESS = "merged_progress";
    private static final String KEY_UPLOAD_STATUS = "upload_status";
    private static final String KEY_APPROVED_STATUS = "approved_status";
    private static final String KEY_CURRENT_SCREEN = "current_screen";
    private static final String KEY_RERECORD_STATE = "rerecord_state";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_TAGS = "tags";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_FINAL_VIDEO = "finalvideo";
    private static final String KEY_COUNTRY = "country";
    private static final String KEY_LOGOUT_STATUS = "logout_status";
    private static final String KEY_LAUNCHSCREEN = "launch_screen";
	private static final String KEY_DBCOMPLETE = "db_complete";
	
	private static final String KEY_USER_FIRSTNAME = "user_firstname";
	private static final String KEY_USER_LASTNAME = "user_lastname";
	private static final String KEY_USER_GENDER = "user_gender";
	private static final String KEY_USER_BIRTHDAY = "user_birthday";
	private static final String KEY_USER_EMAIL = "user_email";
	private static final String KEY_USER_ADDRESS = "user_address";
	private static final String KEY_USER_PHONE = "user_phone";
	private static final String KEY_USER_DOB = "user_dob";
	private static final String KEY_USER_CITY = "user_city";
	private static final String KEY_USER_STATE = "user_state";
	private static final String KEY_USER_COUNTRY = "user_country";
	private static final String KEY_USER_ZIP = "user_zip";
	private static final String KEY_CURRENT_MENU_ITEM = "current_menu_item";
	
	
	
	private static final String KEY_YOUTUBE_UPLOAD_PROGRESS = "youtube_upload_progress";
	private static final String KEY_YOUTUBE_UPLOAD_STATUS = "youtube_upload_status";
	private static final String KEY_VERSEDBCOMPLETE = "versedb_complete";
	private static final String KEY_VERSE_INDEX = "verse_index";
	private static final String KEY_VERSE_TEXT = "verse_text";
	private static final String KEY_TOPICS = "topics";
	private static final String KEY_TOPIC_ID = "topics_id";
	private static final String KEY_TOPIC_POSITION = "topics_position";
	private static final String KEY_COMPLETED_SCREENS = "completed_screens";
	
	private static final String KEY_SHOW_PREVIEW_TAB1 = "show_preview_tab1";
	private static final String KEY_SHOW_PREVIEW_TAB2 = "show_preview_tab2";
	private static final String KEY_SHOW_PREVIEW_TAB3 = "show_preview_tab3";
	private static final String KEY_SHOW_PREVIEW_TAB4 = "show_preview_tab4";
	private static final String KEY_SHOW_PREVIEW_TAB5 = "show_preview_tab5";
	
	private static final String KEY_PROJECT_PATH = "project_path";
	private static final String KEY_SERVER_UPLOAD_STATUS = "server_upload";
	
	private static final String KEY_YOUTUBE_VIDEO_ID = "youtube_id";
	private static final String KEY_YOUTUBE_VIDEO_URL = "youtube_url";
	private static final String KEY_YOUTUBE_THUMBNAIL_URL = "youtube__thumbnail_url";
 
    // Table Create Statements
    private static final String CREATE_TABLE_KMS = "CREATE TABLE "
            + TABLE_KMS + "(" + KEY_ID + " INTEGER PRIMARY KEY," 
    		+ KEY_TITLE + " TEXT," 
            + KEY_VIDEO1 + " TEXT," 
    		+ KEY_VIDEO2 + " TEXT,"
    		+ KEY_VIDEO3 + " TEXT,"
    		+ KEY_VIDEO4 + " TEXT,"
    		+ KEY_VIDEO5 + " TEXT,"
    		+ KEY_VIDEO6 + " TEXT,"
    		+ KEY_VIDEO7 + " TEXT,"
    		+ KEY_BG_MUSIC + " TEXT,"
    		+ KEY_OUTPUT_VIDEO + " TEXT,"
    		+ KEY_MERGED_VIDEO_ONLY + " TEXT,"
    		+ KEY_FINAL_VIDEO + " TEXT,"
    		+ KEY_MERGED_STATUS + " TEXT,"
    		+ KEY_MERGED_PROGRESS + " TEXT,"
    		+ KEY_UPLOAD_STATUS + " TEXT,"
    		+ KEY_APPROVED_STATUS + " TEXT,"
    		+ KEY_RERECORD_STATE + " INTEGER DEFAULT 0,"
    		+ KEY_DESCRIPTION + " TEXT,"
    		+ KEY_TAGS + " TEXT,"
    		+ KEY_LANGUAGE + " TEXT,"
    		+ KEY_COUNTRY + " TEXT,"
    		+ KEY_LOGOUT_STATUS + " INTEGER,"
    		+ KEY_USER_FIRSTNAME + " TEXT,"
    		+ KEY_USER_LASTNAME + " TEXT,"
    		+ KEY_USER_GENDER + " TEXT,"
    		+ KEY_USER_BIRTHDAY + " TEXT,"
    		+ KEY_USER_EMAIL + " TEXT,"
    		+ KEY_USER_ZIP + " TEXT,"
    		+ KEY_USER_DOB + " TEXT,"
    		+ KEY_USER_CITY + " TEXT,"
    		+ KEY_USER_COUNTRY + " TEXT,"
    		+ KEY_USER_ADDRESS + " TEXT,"
			+ KEY_USER_PHONE + " TEXT,"
			+ KEY_USER_STATE + " TEXT,"
    		+ KEY_LAUNCHSCREEN + " TEXT,"
			+ KEY_DBCOMPLETE + " INTEGER,"
			+ KEY_VERSE_INDEX + " TEXT,"
			+ KEY_VERSE_TEXT + " TEXT,"
			+ KEY_TOPICS + " TEXT,"
			+ KEY_TOPIC_ID + " TEXT,"
			+ KEY_TOPIC_POSITION + " TEXT,"
			+ KEY_VERSEDBCOMPLETE + " INTEGER,"
			+ KEY_YOUTUBE_UPLOAD_STATUS + " INTEGER,"
			+ KEY_YOUTUBE_VIDEO_ID + " TEXT,"
			+ KEY_YOUTUBE_VIDEO_URL + " TEXT,"
			+ KEY_YOUTUBE_THUMBNAIL_URL + " TEXT,"
			+ KEY_COMPLETED_SCREENS + " INTEGER DEFAULT 0,"
			+ KEY_CURRENT_MENU_ITEM + " INTEGER DEFAULT 0,"
			+ KEY_SHOW_PREVIEW_TAB1 + " INTEGER DEFAULT 0,"
			+ KEY_SHOW_PREVIEW_TAB2 + " INTEGER DEFAULT 0,"
			+ KEY_SHOW_PREVIEW_TAB3 + " INTEGER DEFAULT 0,"
			+ KEY_SHOW_PREVIEW_TAB4 + " INTEGER DEFAULT 0,"
			+ KEY_SHOW_PREVIEW_TAB5 + " INTEGER DEFAULT 0,"
			+ KEY_SERVER_UPLOAD_STATUS + " INTEGER DEFAULT 0,"
			+ KEY_PROJECT_PATH + " TEXT,"
			+ KEY_YOUTUBE_UPLOAD_PROGRESS + " TEXT,"
    		+ KEY_CURRENT_SCREEN + " TEXT" + ")";
    	  
 
   
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_KMS);
        
       // this.addDefaultScreen(db, "access_admin_fingers", "2");
        
       
    }
 
    private void addDefaultScreen(SQLiteDatabase db, String string,
			String string2) {
    	 ContentValues values = new ContentValues();
    	    
    	    db.insert(TABLE_KMS, null, values);
		
	}

	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KMS);
       
        // create new tables
        onCreate(db);
    }

	public void insertStoryName(Model model) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_TITLE, model.getTitle());
	    System.out.println("storyname from model: "+model.getTitle());
	    // insert row
	    db.insert(TABLE_KMS, null, values);
	   // db.close();
	 
	}
	public void updateStoryName(Model model) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_TITLE, model.getTitle());
	    System.out.println("updated title: "+model.getTitle());
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
	public String getTitle() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String title = "";
		try {

			cursor = db.rawQuery("SELECT " + KEY_TITLE + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				title = cursor.getString(cursor
						.getColumnIndex(KEY_TITLE));
			}
			System.out.println("Title: " + title);
			return title;
		} finally {

			cursor.close();
			//db.close();
		}

	}
	public long loginCount() {

		SQLiteDatabase db = getWritableDatabase();

		long numRows = DatabaseUtils.queryNumEntries( db, TABLE_KMS );

		System.out.println("Rows number "+numRows);

		return numRows;
	}
	public String isVideo1Present() {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = null;
        String isVideo1Present = "";
       
            cursor = db.rawQuery("SELECT "+KEY_VIDEO1+" FROM "+TABLE_KMS+" WHERE "+KEY_ID+"=?", new String[] { 1 + ""});
     
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                isVideo1Present = cursor.getString(cursor.getColumnIndex(KEY_VIDEO1));
                System.out.println("isVideo1Present: "+isVideo1Present);
            }
            
            return isVideo1Present;
       
	}
	public String isVideo2Present(String projectName) {
		SQLiteDatabase db = this.getReadableDatabase();
	
		Cursor cursor = null;
        String isVideo2Present = "";
       

            cursor = db.rawQuery("SELECT "+KEY_VIDEO2+" FROM "+TABLE_KMS+" WHERE "+KEY_ID+"=?", new String[] { 1 + ""});
       
            if(cursor.getCount() > 0) {

                cursor.moveToFirst();
                isVideo2Present = cursor.getString(cursor.getColumnIndex(KEY_VIDEO2));
                System.out.println("isVideo2Present: "+isVideo2Present);
            }
            
            return isVideo2Present;
       
	}
	public String isVideo3Present(String projectName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
        String isVideo3Present = "";
        cursor = db.rawQuery("SELECT "+KEY_VIDEO3+" FROM "+TABLE_KMS+" WHERE "+KEY_ID+"=?", new String[] { 1 + ""});
      
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                isVideo3Present = cursor.getString(cursor.getColumnIndex(KEY_VIDEO3));
                System.out.println("isVideo3Present: "+isVideo3Present);
            }
            return isVideo3Present;
	}
	public String isVideo4Present(String projectName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
        String isVideo4Present = "";
        cursor = db.rawQuery("SELECT "+KEY_VIDEO4+" FROM "+TABLE_KMS+" WHERE "+KEY_ID+"=?", new String[] { 1 + ""});
      
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                isVideo4Present = cursor.getString(cursor.getColumnIndex(KEY_VIDEO4));
                System.out.println("isVideo4Present: "+isVideo4Present);
            }
            return isVideo4Present;
	}
	public String isVideo5Present(String projectName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
        String isVideo5Present = "";
        cursor = db.rawQuery("SELECT "+KEY_VIDEO5+" FROM "+TABLE_KMS+" WHERE "+KEY_ID+"=?", new String[] { 1 + ""});
      
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                isVideo5Present = cursor.getString(cursor.getColumnIndex(KEY_VIDEO5));
                System.out.println("isVideo5Present: "+isVideo5Present);
            }
            return isVideo5Present;
	}


	public void insertVideo(String videopath, String tabDetail, String storyname) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    System.out.println("tabDetail: "+tabDetail);
	    values.put(KEY_VIDEO1, "videopath");
	 
	    // insert row
	    db.insert(TABLE_KMS, null, values);
	    //db.close();
		
	}
	
	public void updateCurrentScreen(String current_screen) {
	
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_CURRENT_SCREEN, current_screen);
	    System.out.println("KEY_CURRENT_SCREEN "+current_screen);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}

	public String getCurrentScreen() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String currentScreen = "";
		try {

			cursor = db.rawQuery("SELECT " + KEY_CURRENT_SCREEN + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				currentScreen = cursor.getString(cursor
						.getColumnIndex(KEY_CURRENT_SCREEN));
			}
			System.out.println("current screen: " + currentScreen);
			return currentScreen;
		} finally {

			cursor.close();
			//db.close();
		}

	}
	public void updateRerecordState(int rerecord_state) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_RERECORD_STATE, rerecord_state);
	    System.out.println("KEY_RERECORD_STATE "+rerecord_state);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
	public int getRerecordState() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int rerecord_state = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_RERECORD_STATE + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				rerecord_state = cursor.getInt(cursor
						.getColumnIndex(KEY_RERECORD_STATE));
				System.out.println("rerecord_state inside cursor: " + rerecord_state);
			}
			System.out.println("rerecord_state: " + rerecord_state);
			return rerecord_state;
		} finally {

			cursor.close();
			//db.close();
		}
	}
	public void insertVideo(Model model) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_VIDEO1, model.getvideo1());
	    // insert row
	    db.insert(TABLE_KMS, null, values);
	   // db.close();
		
	}

	public void updateVideo1(String outputMediaFile) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_VIDEO1, outputMediaFile);
	 
	    // updating row
	     db.update(TABLE_KMS, values, KEY_ID + " = ?",
	            new String[] { 1+"" });
	}

	public void updateVideo2(String outputMediaFile) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_VIDEO2, outputMediaFile);
	 
	    // updating row
	     db.update(TABLE_KMS, values, KEY_ID + " = ?",
	            new String[] { 1+"" });
	}
	public void updateVideo3(String outputMediaFile) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_VIDEO3, outputMediaFile);
	 
	    // updating row
	     db.update(TABLE_KMS, values, KEY_ID + " = ?",
	            new String[] { 1+"" });
	}
	public void updateVideo4(String outputMediaFile) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_VIDEO4, outputMediaFile);
	 
	    // updating row
	     db.update(TABLE_KMS, values, KEY_ID + " = ?",
	            new String[] { 1+"" });
	}
	public void updateVideo5(String outputMediaFile) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_VIDEO5, outputMediaFile);
	 
	    // updating row
	     db.update(TABLE_KMS, values, KEY_ID + " = ?",
	            new String[] { 1+"" });
	}

	public void updateSelectedBGMusic(String songName) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_BG_MUSIC, songName);
	 
	    // updating row
	     db.update(TABLE_KMS, values, KEY_ID + " = ?",
	            new String[] { 1+"" });
		
	}

	public String getSelectedBGMusic(String projectName) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
        String selectedBGMusic = "";
        cursor = db.rawQuery("SELECT "+KEY_BG_MUSIC+" FROM "+TABLE_KMS+" WHERE "+KEY_TITLE+"=?", new String[] { projectName + ""});
      
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                selectedBGMusic = cursor.getString(cursor.getColumnIndex(KEY_BG_MUSIC));
                System.out.println("isVideo4Present: "+selectedBGMusic);
            }
            return selectedBGMusic;
		
	}

	public void updateMergedVideo(String mergedVideoOutput) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(KEY_MERGED_VIDEO_ONLY, mergedVideoOutput);
	 
	    // updating row
	     db.update(TABLE_KMS, values, KEY_ID + " = ?",
	            new String[] { 1+"" });
		
	}

	public String getMergedVideoOnly(String projectName) {
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = null;
        String mergedVideoOnly = "";
        cursor = db.rawQuery("SELECT "+KEY_MERGED_VIDEO_ONLY+" FROM "+TABLE_KMS+" WHERE "+KEY_TITLE+"=?", new String[] { projectName + ""});
      
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                mergedVideoOnly = cursor.getString(cursor.getColumnIndex(KEY_MERGED_VIDEO_ONLY));
                System.out.println("mergedVideoOnly: "+mergedVideoOnly);
            }
            return mergedVideoOnly;
	}

	/*
	 * Insert  the description entered by users
	 */
	public void insertDescription(String desc) {
		// ADD CODE to INSERT DESC
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_DESCRIPTION, desc);
	    System.out.println("Desc from model: "+desc);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}
	/*
	 * Get description entered by users in support of the video
	 */
	public String getDescription() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String descr = "";
		try {

			cursor = db.rawQuery("SELECT " + KEY_DESCRIPTION + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				descr = cursor.getString(cursor
						.getColumnIndex(KEY_DESCRIPTION));
			}
			System.out.println("Description in DB: " + descr);
			return descr;
		} finally {

			cursor.close();
		}

	}
	/*
	 * Insert Tags entered by users
	 */
	public void insertTags(String tagValue) {
		// ADD CODE TO INSERT TAGS
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_TAGS, tagValue);
	    System.out.println("Desc from model: "+tagValue);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}
	/*
	 * Get Tags entered by users
	 */
	public String getTags() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String tags = "";
		try {

			cursor = db.rawQuery("SELECT " + KEY_TAGS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				tags = cursor.getString(cursor
						.getColumnIndex(KEY_TAGS));
			}
			System.out.println("tags in DB: " + tags);
			return tags;
		} finally {
			cursor.close();
		}

	}
	/*
	 * Insert language selected by user
	 */
	public void insertLanguage(String language) {
		// ADD CODE TO INSERT TAGS
				SQLiteDatabase db = this.getWritableDatabase();
				// String name = model.getTitle();
			    ContentValues values = new ContentValues();
			    values.put(KEY_LANGUAGE, language);
			    System.out.println("lang in db "+language);
			    // insert row
			    db.update(TABLE_KMS, values, null, null);
		
	}
/*
 * Insert country selected by users 
 */
	public void insertCountry(String country) {
		// ADD CODE TO INSERT TAGS
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_COUNTRY, country);
	    System.out.println("country in db "+country);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
	/*
	 * Get country selected by user
	 */
	public String getCountry() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String country = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_COUNTRY + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				country = cursor.getString(cursor
						.getColumnIndex(KEY_COUNTRY));
			}
			System.out.println("country in db: " + country);
			return country;
		} finally {

			cursor.close();
			//db.close();
		}
	}
	/*
	 * Get Language selected by user
	 */
	public String getLanguage() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String lang = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_LANGUAGE + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				lang = cursor.getString(cursor
						.getColumnIndex(KEY_LANGUAGE));
			}
			System.out.println("language in db: " + lang);
			return lang;
		} finally {

			cursor.close();
			//db.close();
		}
	}
	
	/*
	 * Update merged progress of video
	 */
	public void updateMergedProgress(int progress) {
		// ADD CODE TO INSERT TAGS
				SQLiteDatabase db = this.getWritableDatabase();
				// String name = model.getTitle();
			    ContentValues values = new ContentValues();
			    values.put(KEY_MERGED_PROGRESS, progress);
			    System.out.println("progress in db "+progress);
			    // insert row
			    db.update(TABLE_KMS, values, null, null);
		
	}
/*
 * Update Merged status 
 */
	public void updateMergedStatus(String mergedStatus) {
		// ADD CODE TO INSERT TAGS
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_MERGED_STATUS, mergedStatus);
	    System.out.println("progress in db "+mergedStatus);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}

	/*
	 * Get merged status of the videos added by users.
	 */
	public String getMergedStatus() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String mergedStatus = "";
		try {

			cursor = db.rawQuery("SELECT " + KEY_MERGED_STATUS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				mergedStatus = cursor.getString(cursor
						.getColumnIndex(KEY_MERGED_STATUS));
			}
			System.out.println("getMergedStatus in db " + mergedStatus);
			return mergedStatus;
		} finally {

			cursor.close();
			//db.close();
		}
		
	}

	/*
	 * Get the merging progress of video
	 */
	public int getMergedProgress() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int mergedProgress = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_MERGED_PROGRESS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				mergedProgress = cursor.getInt(cursor
						.getColumnIndex(KEY_MERGED_PROGRESS));
			}
			System.out.println("getMergedProgress in db: " + mergedProgress);
			return mergedProgress;
		} finally {

			cursor.close();
			//db.close();
		}
		
	}

	/*
	 * Update Final video url to Db
	 */
	public void updateFinalVideo(String filename) {
		// ADD CODE TO INSERT TAGS
				SQLiteDatabase db = this.getWritableDatabase();
				// String name = model.getTitle();
			    ContentValues values = new ContentValues();
			    values.put(KEY_FINAL_VIDEO, filename);
			    System.out.println("finalvideo in db "+filename);
			    // insert row
			    db.update(TABLE_KMS, values, null, null);
		
	}

	/*
	 * Get the url of the final video saved in internal memory. i.e the video with merged videos and added background music  
	 */
	public String getFinalVideo() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String finalvideo = "";
		try {

			cursor = db.rawQuery("SELECT " + KEY_FINAL_VIDEO + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				finalvideo = cursor.getString(cursor
						.getColumnIndex(KEY_FINAL_VIDEO));
			}
			System.out.println("current screen: " + finalvideo);
			return finalvideo;
		} finally {

			cursor.close();
		}
		
	}

	/*
	 * Update Logout/Login status of user to app
	 */
	public void UpdateLogoutStatus(int logoutstatus) {
		
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_LOGOUT_STATUS, logoutstatus);
	    System.out.println("logoutstatus in db "+logoutstatus);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}
/*
 * Get the status of User login to app. Login and Logout status
 */
	public int getLogoutStatus() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int logout_status = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_LOGOUT_STATUS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				logout_status = cursor.getInt(cursor
						.getColumnIndex(KEY_LOGOUT_STATUS));
				System.out.println("logout_status inside cursor: " + logout_status);
			}
			System.out.println("current screen: " + logout_status);
			return logout_status;
		} finally {

			cursor.close();
			//db.close();
		}
	}
	/*
	 * Update the launch status to Db
	 */
	public void updateLaunch()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		// String name = model.getTitle();
		ContentValues values = new ContentValues();
		values.put(KEY_LAUNCHSCREEN,"1");
		// insert row
		db.update(TABLE_KMS, values, null, null);
		System.out.println("laounch inside: "+db.update(TABLE_KMS, values, null, null));
	}

	/*
	 * Get the status of app lauch. i.e if its first launch 
	 */
	public int getLaunch() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int launch = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_LAUNCHSCREEN + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) 
			{
				cursor.moveToFirst();
				launch = cursor.getInt(cursor.getColumnIndex(KEY_LAUNCHSCREEN));
			}
			System.out.println("get  launch in db: " + launch);
			return launch;
		} finally {

			cursor.close();
			//db.close();
		}
	}

	public void updateDbComplete()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("db complete vaalue ");
		// String name = model.getTitle();
		ContentValues values = new ContentValues();
		values.put(KEY_DBCOMPLETE,1);
		// insert row
		db.update(TABLE_KMS, values, null, null);
	}

	public int checkDbComplete() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int chk_db = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_DBCOMPLETE + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) 
			{
				cursor.moveToFirst();
				chk_db = cursor.getInt(cursor.getColumnIndex(KEY_DBCOMPLETE));
			}
			System.out.println("check db complete value: " + chk_db);
			return chk_db;
		} finally {

			cursor.close();
			//db.close();
		}
	}

	/*
	 * Update the users details fetched from social network login
	 */
	public void updateUserDetails(String firstName, String lastName,
			String birthday, String gender, String email, String address, String  country, String city, String phone, String state, String zip) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_USER_FIRSTNAME, firstName);
	    values.put(KEY_USER_LASTNAME, lastName);
	    values.put(KEY_USER_BIRTHDAY, birthday);
	    values.put(KEY_USER_GENDER, gender);
	    values.put(KEY_USER_EMAIL, email);
	    values.put(KEY_USER_ADDRESS, email);
	    values.put(KEY_USER_COUNTRY, country);
	    values.put(KEY_USER_CITY, city);
	    values.put(KEY_USER_PHONE, phone);
	    values.put(KEY_USER_STATE, state);
	    values.put(KEY_USER_ZIP, zip);
	
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}

	/*
	 * Update the Youtube video upload progress to Db
	 */

	public void updateYouTubeUploadProgress(String string) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_YOUTUBE_UPLOAD_PROGRESS, string);
	    System.out.println("youtube_progress in db "+string);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
/*
 * Get Youtube Video upload progress
 */
	public int getYouTubeUploadProgress() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int mergedProgress = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_YOUTUBE_UPLOAD_PROGRESS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				mergedProgress = cursor.getInt(cursor
						.getColumnIndex(KEY_YOUTUBE_UPLOAD_PROGRESS));
			}
			System.out.println("getYouTubeProgress in db: " + mergedProgress);
			return mergedProgress;
		} finally {

			cursor.close();
		}
		
	}
	/*
	 * Update verse export complete
	 */
	public void updateVerseDbComplete()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("db verse texts complete vaalue ");
		// String name = model.getTitle();
		ContentValues values = new ContentValues();
		values.put(KEY_VERSEDBCOMPLETE,1);
		// insert row
		db.update(TABLE_KMS, values, null, null);
	}

	/*
	 * Check the status of importing verses to DB
	 */
	public int checkVerseDbComplete() 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int chkverse_db = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_VERSEDBCOMPLETE + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) 
			{
				cursor.moveToFirst();
				chkverse_db = cursor.getInt(cursor.getColumnIndex(KEY_VERSEDBCOMPLETE));
			}
			System.out.println("check db  verse texts complete value: " + chkverse_db);
			return chkverse_db;
		} finally {

			cursor.close();
			//db.close();
		}
	}
	/*
	 * Update the selected scripture index and text 
	 */
	public void updateSelection(String index, String text) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
		ContentValues values = new ContentValues();
		values.put(KEY_VERSE_INDEX, index);
		values.put(KEY_VERSE_TEXT, text);
		System.out.println("index in db "+index+text);
		// insert row
		db.update(TABLE_KMS, values, null, null);

	}
	/*
	 * Get verse index number selected by user
	 */
	public String getVerseIndex() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String verseIndex = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_VERSE_INDEX + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				verseIndex = cursor.getString(cursor
						.getColumnIndex(KEY_VERSE_INDEX));
			}
			System.out.println("get index verse in db: " + verseIndex);
			return verseIndex;
		} finally {

			cursor.close();
			//db.close();
		}


	}
	/*
	 * Get verse text selected by user
	 */
	public String getVerseText() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String versetext = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_VERSE_TEXT + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				versetext = cursor.getString(cursor
						.getColumnIndex(KEY_VERSE_TEXT));
			}
			System.out.println("get  text verse in db: " + versetext);
			return versetext;
		} finally {

			cursor.close();
			//db.close();
		}
	}
/*
 * Update the status of video uploaded to youtube.
 */
	public void updateYouTubeUploadStatus(String upload_failed) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_YOUTUBE_UPLOAD_STATUS, upload_failed);
	    System.out.println("youtube_status in db "+upload_failed);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}
	/*
	 * Get the status of video uploaded to youtube.
	 */
	public String getYouTubeUploadStatus() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String upload_status = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_YOUTUBE_UPLOAD_STATUS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				upload_status = cursor.getString(cursor
						.getColumnIndex(KEY_YOUTUBE_UPLOAD_STATUS));
			}
			System.out.println("getYouTubeStatus in db: " + upload_status);
			return upload_status;
		} finally {

			cursor.close();
		}
	}
/*
 * Get User details i.e User firstname fetched from social network login.
 */
	public String getUserDetails() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String upload_status = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_USER_FIRSTNAME + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				upload_status = cursor.getString(cursor
						.getColumnIndex(KEY_USER_FIRSTNAME));
			}
			System.out.println("firstname in db: " + upload_status);
			return upload_status;
		} finally {

			cursor.close();
		}
		
	}
/*
 * Update Topics strings selected by user to DB
 */
	public void updateTopics(String topicId) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_TOPICS, topicId);
	    System.out.println("topicId in db "+topicId);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
/*
 * Get the Topics strings selected by user 
 */
	public String getTopics() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String getTopcs = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_TOPICS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				getTopcs = cursor.getString(cursor
						.getColumnIndex(KEY_TOPICS));
			}
			System.out.println("getTopics in db: " + getTopcs);
			return getTopcs;
		} finally {

			cursor.close();
			//db.close();
		}
		
	}

	/*
	 * Update Topics ID
	 */
	public void updateTopicId(String topicId) {
		SQLiteDatabase db = this.getWritableDatabase();
		// String name = model.getTitle();
	    ContentValues values = new ContentValues();
	    values.put(KEY_TOPIC_ID, topicId);
	    System.out.println("topicId in db "+topicId);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}

	/*
	 * Get Topics id selected by user 
	 */
	public String getTopicsId() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String getTopic_id = null;
		try {

			cursor = db.rawQuery("SELECT " + KEY_TOPIC_ID + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				getTopic_id = cursor.getString(cursor
						.getColumnIndex(KEY_TOPIC_ID));
			}
			System.out.println("getTopicsId in db: " + getTopic_id);
			return getTopic_id;
		} finally {

			cursor.close();
			//db.close();
		}
	}
/*
 * Update the latest screen completed by user
 */
	public void updateCompletedScreen(int completed_screen) {
		
			SQLiteDatabase db = this.getWritableDatabase();
			// String name = model.getTitle();
		    ContentValues values = new ContentValues();
		    values.put(KEY_COMPLETED_SCREENS, completed_screen);
		    System.out.println("completed_screen in db "+completed_screen);
		    // insert row
		    db.update(TABLE_KMS, values, null, null);
	}
/*
 * Get the number of screens completed by user
 */
	public int getCompletedScreens() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int completed_scrren = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_COMPLETED_SCREENS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				completed_scrren = cursor.getInt(cursor
						.getColumnIndex(KEY_COMPLETED_SCREENS));
			}
			System.out.println("getYouTubeStatus in db: " + completed_scrren);
			return completed_scrren;
		} finally {

			cursor.close();
		}
		
	}
/*
 * Delete all rows from DB
 */
	public int deleteAllRows() {

	    SQLiteDatabase db = this.getWritableDatabase();
	    int count = db.delete(DatabaseHelper.TABLE_KMS, null, null);
	    db.close();
	    return count;
		
	}

	/*
	 * Update current menu item
	 */
	public void updateCurrentMenuItem(int menu_item) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_CURRENT_MENU_ITEM, menu_item);
	    System.out.println("menu_item in db "+menu_item);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
	/*
	 * Get current menu item from DB
	 */
	public int getCurrentMenuItem() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int menu_item = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_CURRENT_MENU_ITEM + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				menu_item = cursor.getInt(cursor
						.getColumnIndex(KEY_CURRENT_MENU_ITEM));
			}
			System.out.println("get_menu_item in db: " + menu_item);
			return menu_item;
		} finally {

			cursor.close();
		}
		
	}
	/*
	 * Fetch the preview tab constant 
	 */
	public int getShowPreviewTabConstant1() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int show_preview_tab1 = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_SHOW_PREVIEW_TAB1 + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				show_preview_tab1 = cursor.getInt(cursor
						.getColumnIndex(KEY_SHOW_PREVIEW_TAB1));
			}
			System.out.println("show_preview_tab1 in db: " + show_preview_tab1);
			return show_preview_tab1;
		} finally {

			cursor.close();
		}
	}
	/*
	 * Update preview tab if user's current state is preview tab 1
	 */
	public void updateShowPreviewTab1(int i) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_SHOW_PREVIEW_TAB1, i);
	    System.out.println("update show_preview_tab1 in db "+i);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
		
	}

	/*
	 * Fetch the preview tab constant 
	 */
	public int getShowPreviewTabConstant2() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int show_preview_tab2 = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_SHOW_PREVIEW_TAB2 + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				show_preview_tab2 = cursor.getInt(cursor
						.getColumnIndex(KEY_SHOW_PREVIEW_TAB2));
			}
			System.out.println("show_preview_tab2 in db: " + show_preview_tab2);
			return show_preview_tab2;
		} finally {

			cursor.close();
		}
	}
	/*
	 * Update preview tab if user's current state is preview tab 2
	 */
	public void updateShowPreviewTab2(int i) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_SHOW_PREVIEW_TAB2, i);
	    System.out.println("update show_preview_tab2 in db "+i);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}

	/*
	 * Fetch the preview tab constant 
	 */
	public int getShowPreviewTabConstant3() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int show_preview_tab3 = 0;
		try {
			cursor = db.rawQuery("SELECT " + KEY_SHOW_PREVIEW_TAB3 + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				show_preview_tab3 = cursor.getInt(cursor
						.getColumnIndex(KEY_SHOW_PREVIEW_TAB3));
			}
			System.out.println("show_preview_tab3 in db: " + show_preview_tab3);
			return show_preview_tab3;
		} finally {

			cursor.close();
		}
	}

	/*
	 * Update preview tab if user's current state is preview tab 3
	 */
	public void updateShowPreviewTab3(int i) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_SHOW_PREVIEW_TAB3, i);
	    System.out.println("update show_preview_tab3 in db "+i);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
	/*
	 * Fetch the preview tab id
	 */
	public int getShowPreviewTabConstant4() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int show_preview_tab4 = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_SHOW_PREVIEW_TAB4 + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				show_preview_tab4 = cursor.getInt(cursor
						.getColumnIndex(KEY_SHOW_PREVIEW_TAB4));
			}
			System.out.println("show_preview_tab4 in db: " + show_preview_tab4);
			return show_preview_tab4;
		} finally {

			cursor.close();
		}
	}

	/*
	 * Update preview tab if user's current state is preview tab 4
	 */
	public void updateShowPreviewTab4(int i) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_SHOW_PREVIEW_TAB4, i);
	    System.out.println("update show_preview_tab4 in db "+i);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}
	
	/*
	 * Fetch the preview tab constant 
	 */
	public int getShowPreviewTabConstant5() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int show_preview_tab5 = 0;
		try {
			cursor = db.rawQuery("SELECT " + KEY_SHOW_PREVIEW_TAB5 + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				show_preview_tab5 = cursor.getInt(cursor
						.getColumnIndex(KEY_SHOW_PREVIEW_TAB5));
			}
			System.out.println("show_preview_tab5 in db: " + show_preview_tab5);
			return show_preview_tab5;
		} finally {

			cursor.close();
		}
	}
/*
 * Update the preview tab status if the user is in tab 5 
 */
	public void updateShowPreviewTab5(int i) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_SHOW_PREVIEW_TAB5, i);
	    System.out.println("update show_preview_tab5 in db "+i);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}
	
/*
 * Insert project path to new row created.
 */
	public void insertProjectPath(String projectPath) {
		
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_PROJECT_PATH, projectPath);
	    System.out.println("projectPath in db: "+projectPath);
	    // insert row
	    db.insert(TABLE_KMS, null, values);
	}
	/*
	 * Get project path i.e the path where all the user created videos are saved.
	 */
	public String getProjectPath() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String path = null;
		try {
			cursor = db.rawQuery("SELECT " + KEY_PROJECT_PATH + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				path = cursor.getString(cursor
						.getColumnIndex(KEY_PROJECT_PATH));
			}
			System.out.println("get path in db: " + path);
			return path;
		} finally {

			cursor.close();
		}
	}

	/*
	 * Update Topic positions selected by user.
	 */
	public void updateTopicPosition(String pos) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_TOPIC_POSITION, pos);
	    System.out.println("topic position in db "+pos);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
	/*
	 * Get Topic items positons selected
	 */
	public String getTopicPosition() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String topic_pos = null;
		try {
			cursor = db.rawQuery("SELECT " + KEY_TOPIC_POSITION + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				topic_pos = cursor.getString(cursor
						.getColumnIndex(KEY_TOPIC_POSITION));
			}
			System.out.println("topic_pos in db: " + topic_pos);
			return topic_pos;
		} finally {

			cursor.close();
		}
		
	}
/*
 * Update the video upload status to server 
 */
	public void UpdateServerVideoUploadStatus(int i) {
		
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_SERVER_UPLOAD_STATUS, i);
	    System.out.println("update server_upload_status in db "+i);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
	}
	/*
	 * Get status of video upload to server
	 */
	public int GetServerUploadStatus() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		int server_upload_status = 0;
		try {

			cursor = db.rawQuery("SELECT " + KEY_SERVER_UPLOAD_STATUS + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				server_upload_status = cursor.getInt(cursor
						.getColumnIndex(KEY_SERVER_UPLOAD_STATUS));
			}
			System.out.println("server_upload_status in db: " + server_upload_status);
			return server_upload_status;
		} finally {

			cursor.close();
		}
	}
/*
 * Save youtube video url to DB
 */
	public void SaveYoutubeVideoId(String video_id) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_YOUTUBE_VIDEO_ID, video_id);
	    System.out.println("video_id in db "+video_id);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
/*
 * Save youtube video url to Db
 */
	public void SaveYoutubeVideoUrl(String video_url) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_YOUTUBE_VIDEO_URL, video_url);
	    System.out.println("video_url in db "+video_url);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
/*
 * Save Youtube video thumbnail url to DB
 */
	public void SaveYouTubeVideoThumbnailUrl(String thumbanil) {
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues values = new ContentValues();
	    values.put(KEY_YOUTUBE_THUMBNAIL_URL, thumbanil);
	    System.out.println("thumbanil in db "+thumbanil);
	    // insert row
	    db.update(TABLE_KMS, values, null, null);
		
	}
/*
 * Get Youtube video ID from DB
 */
	public String getVideoId() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String video_id = null;
		try {
			cursor = db.rawQuery("SELECT " + KEY_YOUTUBE_VIDEO_ID + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				video_id = cursor.getString(cursor
						.getColumnIndex(KEY_YOUTUBE_VIDEO_ID));
			}
			System.out.println("video_id in db: " + video_id);
			return video_id;
		} finally {

			cursor.close();
		}
	}
/*
 * Fetch Youtube video thumbnail Url from DB
 */
	public String getThumbnailUrl() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String thumbnail_url = null;
		try {
			cursor = db.rawQuery("SELECT " + KEY_YOUTUBE_THUMBNAIL_URL + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				thumbnail_url = cursor.getString(cursor
						.getColumnIndex(KEY_YOUTUBE_THUMBNAIL_URL));
			}
			System.out.println("thumbnail_url in db: " + thumbnail_url);
			return thumbnail_url;
		} finally {

			cursor.close();
		}
		
	}
/*
 * Fetch Youtube URL from DB
 */
	public String getYoutubeVideoUrl() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = null;
		String youtube_video_url = null;
		try {
			cursor = db.rawQuery("SELECT " + KEY_YOUTUBE_VIDEO_URL + " FROM "
					+ TABLE_KMS + " WHERE " + KEY_ID + "=?",
					new String[] { 1 + "" });

			if (cursor.getCount() > 0) {

				cursor.moveToFirst();
				youtube_video_url = cursor.getString(cursor
						.getColumnIndex(KEY_YOUTUBE_VIDEO_URL));
			}
			System.out.println("youtube_video_url in db: " + youtube_video_url);
			return youtube_video_url;
		} finally {

			cursor.close();
		}
	}
}
