/*
 * Know My Story 
 */
package com.database.menu;

import android.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.model.UserDetail;

public class Database extends SQLiteOpenHelper {

	static final String DATABASE_NAME = "knowmystory.db";

	private static final String TAG = Database.class.getSimpleName();
	private static final int DATABASE_VERSION = 1;
	private Context mContext;

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext=context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables( db );

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

	}



	private void createTables(SQLiteDatabase db) {
		createLoginTable(db);
		createCountryTable(db);
	}

	private void createCountryTable(SQLiteDatabase db) {
		StringBuilder table = new StringBuilder();
		table.append( "CREATE TABLE " + CountryTable.TABLE_NAME + " (" );
		table.append( CountryTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " );
		table.append( CountryTable.COUNTRY_NAME + " TEXT" );	
		table.append( ");" );
		String sql = table.toString();
		Log.i( TAG, "Creating DB table: " + CountryTable.TABLE_NAME );
		db.execSQL( sql );
	}

	private void createLoginTable(SQLiteDatabase db) {
		StringBuilder table = new StringBuilder();
		table.append( "CREATE TABLE " + LoginTable.TABLE_NAME + " (" );
		table.append( LoginTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " );
		table.append( LoginTable.PROVIDER + " TEXT, " );	
		table.append( LoginTable.FIRST_NAME + " TEXT DEFAULT 'preeti', " );
		table.append( LoginTable.LAST_NAME + " TEXT, " );
		table.append( LoginTable.PROFILE_PIC + " TEXT, " );
		table.append( LoginTable.EMAIL + " TEXT, " );		
		table.append( LoginTable.TOKEN + " TEXT, " );
		table.append( LoginTable.ADDRESS + " TEXT, " );
		table.append( LoginTable.GENDER + " TEXT, " );
		table.append( LoginTable.DOB + " TEXT, " );
		table.append( LoginTable.CITY + " TEXT, " );
		table.append( LoginTable.STATE + " TEXT, " );
		table.append( LoginTable.COUNTRY + " TEXT, " );
		table.append( LoginTable.PHONE + " TEXT, " );
		table.append( LoginTable.ZIPCODE + " TEXT, " );
		table.append( LoginTable.AUTH_TOKEN + " TEXT, " );
		table.append( LoginTable.ACTIVE + " TEXT" );
		table.append( LoginTable.LOGOUT + " TEXT" );
			        
		table.append( ");" );
		String sql = table.toString();
		Log.i( TAG, "Creating DB table: " + LoginTable.TABLE_NAME );
		db.execSQL( sql );


	}

	private void removeTables( SQLiteDatabase db ) {

		dropTable( db, LoginTable.TABLE_NAME );

	}

	private void dropTable( SQLiteDatabase db, final String tableName ) {

		Log.i( TAG, "Dropping table: " + tableName );
		db.execSQL( "DROP TABLE IF EXISTS " + tableName + ";" );
	}

	/**
	 * Convenience method for inserting a row into the database.
	 *
	 * @param table the table to insert the row into
	 * @param nullColumnHack optional; may be <code>null</code>.
	 *            SQL doesn't allow inserting a completely empty row without
	 *            naming at least one column name.  If your provided <code>values</code> is
	 *            empty, no column names are known and an empty row can't be inserted.
	 *            If not set to null, the <code>nullColumnHack</code> parameter
	 *            provides the name of nullable column name to explicitly insert a NULL into
	 *            in the case where your <code>values</code> is empty.
	 * @param values this map contains the initial column values for the
	 *            row. The keys should be the column names and the values the
	 *            column values
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public synchronized long insert( final String table, final String nullColumnHack, final ContentValues values ) {

		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();

		long row = -1;

		try {

			row = db.insert( table, nullColumnHack, values );
			db.setTransactionSuccessful();
		}
		finally {
			db.endTransaction();
		}
		return row;
	}

	/**
	 * Convenience method for updating rows in the database.
	 *
	 * @param table the table to update in
	 * @param values a map from column names to new column values. null is a
	 *            valid value that will be translated to NULL.
	 * @param whereClause the optional WHERE clause to apply when updating.
	 *            Passing null will update all rows.
	 * @return the number of rows affected
	 */
	public synchronized int update( final String table, final ContentValues values, final String whereClause, final String[] whereArgs ) {

		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();

		int rows = 0;

		try {
			System.out.println("Inside update: "+values);
			rows = db.update( table, values, whereClause, whereArgs );
			db.setTransactionSuccessful();
		}
		finally {
			db.endTransaction();
		}
		return rows;
	}
	/**
	 * Query the given table, returning a {@link Cursor} over the result set.
	 *
	 * @param table The table name to compile the query against.
	 * @param columns A list of which columns to return. Passing null will
	 *            return all columns, which is discouraged to prevent reading
	 *            data from storage that isn't going to be used.
	 * @param selection A filter declaring which rows to return, formatted as an
	 *            SQL WHERE clause (excluding the WHERE itself). Passing null
	 *            will return all rows for the given table.
	 * @param selectionArgs You may include ?s in selection, which will be
	 *         replaced by the values from selectionArgs, in order that they
	 *         appear in the selection. The values will be bound as Strings.
	 * @param groupBy A filter declaring how to group rows, formatted as an SQL
	 *            GROUP BY clause (excluding the GROUP BY itself). Passing null
	 *            will cause the rows to not be grouped.
	 * @param having A filter declare which row groups to include in the cursor,
	 *            if row grouping is being used, formatted as an SQL HAVING
	 *            clause (excluding the HAVING itself). Passing null will cause
	 *            all row groups to be included, and is required when row
	 *            grouping is not being used.
	 * @param orderBy How to order the rows, formatted as an SQL ORDER BY clause
	 *            (excluding the ORDER BY itself). Passing null will use the
	 *            default sort order, which may be unordered.
	 * @return A {@link Cursor} object, which is positioned before the first entry. Note that
	 * {@link Cursor}s are not synchronized, see the documentation for more details.
	 * @see Cursor
	 */
	public synchronized Cursor query( final String table, final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy ) {

		SQLiteDatabase db = getWritableDatabase();
		db.beginTransaction();

		Cursor cursor = null;

		try {

			cursor = db.query( table, columns, selection, selectionArgs, groupBy, having, orderBy );
			db.setTransactionSuccessful();
		}
		finally {
			db.endTransaction();
		}
		return cursor;
	}

	public long loginCount() {

		SQLiteDatabase db = getWritableDatabase();

		long numRows = DatabaseUtils.queryNumEntries( db, LoginTable.TABLE_NAME );

		System.out.println("lOGIN Count "+numRows);

		return numRows;
	}
	
	
	public long CountryCount() {

		SQLiteDatabase db = getWritableDatabase();

		long numRows = DatabaseUtils.queryNumEntries( db, CountryTable.TABLE_NAME );

		System.out.println("lOGIN Count "+numRows);

		return numRows;
	}
	
	
	public UserDetail getUserLoginDetails(){
		
		UserDetail user=null;
		Cursor cursor=	query(LoginTable.TABLE_NAME, null, null, null, null, null, null);
		if(cursor.getCount()>0)
		{
			cursor.moveToFirst();
			user=new UserDetail(cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.PROVIDER ) ) ,
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.FIRST_NAME ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.LAST_NAME ) ), 
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.PROFILE_PIC ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.EMAIL ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.TOKEN ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.ADDRESS ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.GENDER ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.DOB ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.CITY ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.STATE ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.COUNTRY ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.PHONE ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.ZIPCODE ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.AUTH_TOKEN ) ),
					cursor.getString( cursor.getColumnIndexOrThrow( LoginTable.ACTIVE ) ));
			
			System.out.println("email: "+user.getEmail());
		}
		return user;
		
		
	}

	



}
