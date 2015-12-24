/*
 * Know My Story 
 */
package com.supportclasses;

public class Constants {
	
	/**
	 * Urls
	 */

	public static final String FACEBOOK_IMAGE_URL_BASE = "http://graph.facebook.com/";
	public static final String FACEBOOK_IMAGE_SIZE = "/picture?type=large";
	
	public static final String URL_BASE ="http://mystory.buzz:80/api/";
	//public static final String URL_BASE ="http://kms.fingent.net/api/";
	
	public static final String URL_ADD_USER ="adduser";
	public static final String URL_VIEW_USER ="viewuser";
	public static final String URL_UPDATE_USER ="updateuser";
	public static final String URL_COUNTRY_LIST ="countrylist";
	public static final String URL_ADD_SELFIE ="addselfievideo";
	public static final String URL_TOPIC_LIST ="topiclist";
	
	public static final String URL_FACEBOOK ="https://m.facebook.com";
	public static final String URL_TWITTER ="https://twitter.com/";
	
	// API related
		public static final String API_KEY = "Access-Token";
		public static final String API_VALUE = "111222333444";
	
	/**
	 * JSON PARAMETERS
	 */
	public static final String KEY_ACCESS_TOKEN ="Access-Token";
	
	public static final String KEY_BODY ="body";
	
	public static final String KEY_ADD_FNAME ="first_name";
	public static final String KEY_ADD_LNAME ="last_name";
	public static final String KEY_ADD_EMAIL ="email";
	public static final String KEY_ADD_PHOTO ="photo";
	public static final String KEY_ADD_PROVIDER ="provider";
	public static final String KEY_ADD_DOB ="dob";
	public static final String KEY_ADD_ADDRESS ="address";
	public static final String KEY_ADD_CITY ="city";
	public static final String KEY_ADD_STATE ="state";
	public static final String KEY_ADD_COUNTRY ="country";
	public static final String KEY_ADD_GENDER ="gender";
	public static final String KEY_ADD_ZIPCODE ="zipcode";
	public static final String KEY_ADD_PHONE ="phone";
	public static final String KEY_ADD_PROVIDER_INFO ="provider_info";
	public static final String KEY_ADD_LANGUAGE ="language";
	public static final String KEY_TOPIC_LANGUAGE ="language";
	
	
	
	public static final String KEY_FNAME ="firstName";
	public static final String KEY_LNAME ="lastName";
	public static final String KEY_EMAIL ="email";
	public static final String KEY_PHOTO ="photo";
	public static final String KEY_PROVIDER ="provider";
	public static final String KEY_DOB ="dob";
	public static final String KEY_ADDRESS ="address";
	public static final String KEY_CITY ="city";
	public static final String KEY_STATE ="state";
	public static final String KEY_COUNTRY ="country";
	public static final String KEY_GENDER ="gender";
	public static final String KEY_ZIPCODE ="zipcode";
	public static final String KEY_PHONE ="phone";
	public static final String KEY_PROVIDER_INFO ="provider_info";
	public static final String KEY_LANGUAGE ="language";
	public static final String KEY_ACTIVE ="isactive";
	public static final String KEY_AUTHTOKEN ="authToken";
	
	
	/**
	 * JSON KEYS OF VIDEO
	 */
	
	public static final String KEY_VID_MAIL ="email";
	public static final String KEY_VID_TITLE ="title";
	public static final String KEY_VID_DESC ="description";
	public static final String KEY_VID_ID ="youtube_id";
	public static final String KEY_VID_EMBEDCODE ="embedcode";
	public static final String KEY_VID_URL ="youtube_url";
	public static final String KEY_VID_THUMBNAIL ="youtube_thumbnail_url";
	public static final String KEY_VID_SCRIPTURE ="scripture_text";
	public static final String KEY_VID_LANG ="language";
	
	// CAMERA REQUEST
	public static final int CAMERA_REQUEST = 100;
		/**
		 * JSON VALUES
		 */
	public static final String VALUE_TOPIC_LANGUAGE ="en";
	/**
	 * JSON ARRAY NODES
	 */
	public static final String ARRAYNODE_TOPIC ="Result";
	/**
	 * JSON OBJECT NODES
	 */
	public static final String OBJECT_TOPIC_NAME ="name";
	public static final String OBJECT_TOPIC_ID ="id";
	public static final String OBJECT_STATUSCODE="StatusCode";

	/**
	 * SHARED PREFERENCE STRINGS
	 */
	public static final String MY_PREFS_NAME = "pref_string";
	
	/**
	 * Other constants 
	 */
	//public static int SHOW_PREVIEW_TAB1 = 0;
	//public static int SHOW_PREVIEW_TAB2 = 0;
	//public static int SHOW_PREVIEW_TAB3 = 0;
	//public static int SHOW_PREVIEW_TAB4 = 0;
	//public static int SHOW_PREVIEW_TAB5 = 0;
	public static int CURRENT_SCREEN = 0;
	public static int SET_CURRENT_TAB = 0;
	
	/**
	 * Region constants 
	 */
	public static final String REGION_LANGUAGE ="RegionLanguage";
	public static final String REGION_COUNTRY_NAME ="RegionCountryName";
	public static final String REGION_COUNTRY_CODE ="RegionCountryCode";
	public static final String REGION_COUNTRY_ID ="RegionCountryId";
	public static final String REGION_CODE ="RegionCode";
	public static final String REGION_BIBLE_VERSION ="RegionBibleVersion";
	public static final String REGION_LANGUAGE_JSON_FILE ="languages.json";
	public static final String REGION_COUNTRY_JSON_FILE ="countries.json";
	public static final String KEY_REGION_LANGUAGE ="language";
	public static final String KEY_REGION_CODE ="code";
	public static final String KEY_REGION_BIBLE_VERSION ="bible_version";
	public static final String KEY_REGION_COUNTRY_CODE ="country_code";
	public static final String KEY_REGION_COUNTRY_NAME ="name";
	public static final String KEY_REGION_COUNTRY_ID ="country_id";
	
	/**
	 * Merging files
	 */
	public static final String MERGED_STATUS_INPROGRESS ="inProgress";
	public static final String MERGED_STATUS_COMPLETED ="completed";
	public static final String MERGED_STATUS_NEW ="new";
	//public static int MERGED_STATUS_CHANGE = 0;
	
	/**
	 * CHANNELS
	 */
	public static final String URL_ALLCHANNEL ="allvideos";
	public static final String OBJECT_URL_LINK="url";
	public static final String URL_SELFIEVIDEOS ="selfievideos";
	public static final String KEY_SELFIE_MAIL="email";

	/**
	 * SCRIPTURES
	 */
	public static final String BIBLE_VERSE_URL ="http://dbt.io/text/verse?key=";
	public static final String BIBLE_DBT_KEY ="c850c2830657874c0a1ca5e68724ed15";

	public static final String BIBLE_BOOKS_JSON ="bible_books.json";
	public static final String BIBLE_VERSE_JSON ="bible_verse.json";
	public static final String BIBLE_OFFLINE_JSON ="bible_offline.json";

	public static final String BIBLE_RETURN_VERSETEXT ="verse_text";
	public static final String BIBLE_RETURN_BOOKNAME ="book_name";
	public static final String BIBLE_RETURN_CHAPTERINDEX ="chapter_id";
	public static final String BIBLE_RETURN_VERSEINDEX ="verse_id";
	public static final String BIBLE_ONLINE_URL ="http://dbt.io/text/search?key=";
	public static int SET_VERSE_FLAG = 0;
	public static int SAVED = 0;
	
	
	public static final String PRE_LOGIN_ACTIVITY ="PRE_LOGIN_ACTIVITY";
	
	public static final String FONT_ABEL_REGULAR ="fonts/Abel-Regular.ttf";
	
	public static int SET_TOPIC_SELECTED = 0;
	public static int ENABLED_TABS_STATE = 0;
	public static int WHEEL_ACTIVITY = 0;
	
	public static int ENABLE_TAB1 = 0;
	public static int ENABLE_TAB2 = 0;
	public static int ENABLE_TAB3 = 0;
	public static int ENABLE_TAB4 = 0;
	public static int ENABLE_TAB5 = 0;
	public static int ENABLE_TAB6 = 0;
	public static int ENABLE_TAB7 = 0;
	public static int ENABLE_TAB8 = 0;
	public static int ENABLE_TAB9 = 0;
	
	public static int VERSE_LOADING = 0; 
	public static int WATCH_LOADING = 0; 
	public static int EXPORT_ERROR = 0; 
	public static int GET_VERSE_START = 0; 
	
	public static int SET_B_ROLL = 0;
	
	
	/**
	 * Google analytics related
	 * */
	
	//one hope
	public static String PROPERTY_ID = "UA-68280984-1";
	 
	//staging
	 //public static String PROPERTY_ID = "UA-68554886-1";
	 
	 

	 
	 /**
	  * tms = Tell_my_Story
	  * ubc = upload button 
	  * uac = upload completed
	  * */
	
	 public static String TMS_SCREEN = "Tell Your Story-Start Recording Video";
	 public static String TMS_CATEGORY = "Tell Your Story";
	 public static String TMS_ACTION = "Start Recording Video";
	 public static String TMS_LABEL = "User starts recording a selfie video";
	 
	 
	 
	 public static String UBC_SCREEN = "Tell Your Story-Finish Recording Video";
	 public static String UBC_CATEGORY = "Tell Your Story";
	 public static String UBC_ACTION = "Finish Recording Video";
	 public static String UBC_LABEL = "User finishes recording a selfie video";
	 
	 
	 
	 public static String UAC_SCREEN = "Tell Your Story-Upload Video";
	 public static String UAC_CATEGORY = "Tell Your Story";
	 public static String UAC_ACTION = "Upload Video";
	 public static String UAC_LABEL = "User uploads their selfie video to the website";
	 
	
	 public static long BUFFER_DATA_STOR = 400;
	 
	
	 
	
	 
	
}
