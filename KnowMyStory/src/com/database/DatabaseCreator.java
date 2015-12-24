package com.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseCreator extends SQLiteOpenHelper 
{
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "script";

	private static final String TABLE_VERSE = "bible_verse";
	private static final String TABLE_OFFLINE = "bible_offline";
	private static final String TABLE_SELECT = "bible_selection";

	//bible_verse Column names
	private static final String KEY_Id = "id";
	private static final String KEY_BOOKORDER = "book_order";
	private static final String KEY_CHAPTER = "chapter";
	private static final String KEY_VERSE = "verse";

	//bible_offline column names
	private static final String KEY_VERSEID = "verse_id";
	private static final String KEY_VERSEINDEX = "verse_index";
	private static final String KEY_VERSETEXT = "verse_text";
	
	private static final String KEY_SELECTED_ID = "select_id";
	private static final String KEY_INDEX = "indx";
	private static final String KEY_TEXT="vtext";

	// Table Create Statements
	private static final String CREATE_TABLE_VERSE = "CREATE TABLE "
			+ TABLE_VERSE + "(" + KEY_Id + " INTEGER," 
			+ KEY_BOOKORDER + " INTEGER," 
			+ KEY_CHAPTER + " INTEGER,"
			+ KEY_VERSE + "  INTEGER" + ")";
	private static final String CREATE_TABLE_VERSEOFFLINE = "CREATE TABLE "
			+ TABLE_OFFLINE + "(" + KEY_VERSEID + " INTEGER," 
			+ KEY_VERSEINDEX + " TEXT,"
			+ KEY_VERSETEXT + "  TEXT" + ")";
	private static final String CREATE_TABLE_SELECT = "CREATE TABLE "
			+ TABLE_SELECT + "(" + KEY_INDEX + " TEXT,"
			+ KEY_TEXT + "  TEXT" + ")";


	public DatabaseCreator(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		
		db.execSQL(CREATE_TABLE_VERSE);
		db.execSQL(CREATE_TABLE_VERSEOFFLINE);
	//	db.execSQL(CREATE_TABLE_SELECT);

		this.addDefaultScreen(db, "access_admin_fingers", "2");
	}

	private void addDefaultScreen(SQLiteDatabase db, String string,
			String string2) {
		ContentValues values = new ContentValues();
		db.insert(TABLE_VERSE, null, values);
		//	db.insert(TABLE_OFFLINE, null, values);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERSE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE);
	//	db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELECT);
		// create new tables
		onCreate(db);
	}

	/**
	 * Function to insert values to the TABLE_VERSE table
	 */
	public void insertBibleFields(int i,int book_ord,int chaptr,int vers)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		if(i==0)
		{
			db.beginTransaction();
		}
		ContentValues values = new ContentValues();
		values.put(KEY_Id, i);
		values.put(KEY_BOOKORDER, book_ord);
		values.put(KEY_CHAPTER,chaptr);
		values.put(KEY_VERSE,vers);
		System.out.println("insertBibleFields" + book_ord);
		// insert row
		db.insert(TABLE_VERSE, null, values);
		System.out.println("i is "+i);
		if(i==31101)
		{
			System.out.println("end value of i is "+i);
			db.setTransactionSuccessful();
			db.endTransaction();
		}
		
		// db.close();
	}

	/**
	 * Function to fetch KEY_VERSE from the TABLE_VERSE table
	 */
	public int getBibleVerse(int bk_order,int chapter)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = null;
		int currentScreen = 0;
		try 
		{
			cursor = db.rawQuery("SELECT " + KEY_VERSE + " FROM " + TABLE_VERSE + " WHERE " + KEY_BOOKORDER + "=?"+" AND "+ KEY_CHAPTER + "=?",
					new String[] { bk_order + "",chapter +"" });



			if (cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				currentScreen = cursor.getInt(cursor.getColumnIndex(KEY_VERSE));
			}
			System.out.println("bible verse: " + currentScreen);
			return currentScreen;
		} finally {

			//cursor.close();
			//db.close();
		}
	}

	/**
	 * Function to insert values to the TABLE_OFFLINE table
	 */
	public void insertVerseOfflineFields(int i,String vers_index,String vers_text)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_VERSEID, i);
		System.out.println("insert verse text "+i);
		values.put(KEY_VERSEINDEX, vers_index);
		values.put(KEY_VERSETEXT,vers_text);
		// insert row
		db.insert(TABLE_OFFLINE, null, values);
		// db.close();
	}

	/**
	 * Function to get the count of verses containing the keyword in the TABLE_OFFLINE table
	 */
	public int getOfflineVerseCount(String keyword)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = null;
		int count=0;
		try 
		{
			cursor =db.rawQuery("select * from " +TABLE_OFFLINE+" where "+KEY_VERSETEXT+" LIKE '%"+keyword+"%'", null);

			if (cursor == null) 
			{
				return count;
			}
			else 
			{
				if (cursor.getCount() > 0)
					count=cursor.getCount();
			}
		} 
		finally
		{

		}

		return count;
	}

	/**
	 * Function to return the verses that contain the keyword from the TABLE_OFFLINE table
	 */
	public ArrayList<HashMap<String, String>>  getOfflineVerse(String keyword)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		Cursor cursor = null;
		try 
		{
			cursor =db.rawQuery("select * from " +TABLE_OFFLINE+" where "+KEY_VERSETEXT+" LIKE '%"+keyword+"%'", null);

			ArrayList<HashMap<String, String>> list_params = new ArrayList<HashMap<String, String>>();

			if(cursor != null)
			{
				while(cursor.moveToNext())
				{
					/*String term = cursor.getString(cursor.getColumnIndex(KEY_VERSEINDEX));
					String type = cursor.getString(cursor.getColumnIndex(KEY_VERSETEXT));
					System.out.println(" terms \n "+term+ " "+type);*/
					HashMap<String, String> param_aux = new HashMap<String, String>();

					param_aux.put("index",
							cursor.getString(cursor.getColumnIndex(KEY_VERSEINDEX)));
					param_aux.put("v_text",
							cursor.getString(cursor.getColumnIndex(KEY_VERSETEXT)));

					list_params.add(param_aux);
				}
				System.out.println("list final is "+list_params);
				return list_params;
			}
			return null;
		} 
		finally
		{
			//cursor.close();
			//db.close();
		}
	}
	

}
