


package com.ezhcg.util;


import com.ezhcg.util.sql.EzhcgDatesHelper;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class EzhcgDatesProvider extends ContentProvider {

	private static final String TAG = "EzhcgDatesProvider";
	public static final String AUTHORITY = "com.ezhcg.util.EzhcgDatesProvider";
	
	private static final UriMatcher mUriMatcher;
	
	private EzhcgDatesHelper mDbHelper;

		@Override
		public int delete(Uri uri, String where, String[] whereArgs) {
			//not supporting deletion
			return 0;
		}
		
		@Override
		public String getType(Uri uri) {
			switch (mUriMatcher.match(uri)){

				case 100:
					return ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.ezhcg.dates";

				case 101:
					return ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.ezhcg.dates";

				default:
					throw new IllegalArgumentException("Unknown Uri " + uri);
				}
		}

		/*
		 * (non-Javadoc)
		 * @see android.content.ContentProvider#insert(android.net.Uri, android.content.ContentValues)
		 * Inserts a new item
		 */
		@Override
		public Uri insert(Uri uri, ContentValues initialValues) {	
			//not supporting insertion
			//we did not successfully insert the new date
			throw new SQLException("Currently not supporting insertion, implement DatesProvider.insert" + uri);
		}

			@Override
			public boolean onCreate() {
				//create a new db helper
				mDbHelper = new EzhcgDatesHelper(getContext());

			return false;
			}

				@Override
					/*
					 * (non-Javadoc)
					 * @see android.content.ContentProvider#query(android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String)
					 * queries an item from the db
					 */
				public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

					SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();	
					queryBuilder.setTables("dates");

					SQLiteDatabase db = mDbHelper.getReadableDatabase();
						
						switch (mUriMatcher.match(uri)){
							//all dates
							case 100:
								//not putting any where on the query
								break;

							//single date
							case 101:
								//append the id given at the end of the uri in the where clause
								queryBuilder.appendWhere("_id="+uri.getLastPathSegment());
								break;

							default:
								//exit, yelling at the caller
								throw new IllegalArgumentException("Unknown Uri " + uri);	
						}

						Cursor result = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
						result.setNotificationUri(getContext().getContentResolver(), uri);
							
							if (result != null){

								result.moveToFirst();
							}	

					return result;
				}

				@Override
				public int update(Uri uri, ContentValues newValues, String where, String[] whereArgs) {
					ContentValues values;

						if (newValues != null){
							values = newValues;

						}else{
							values = new ContentValues();
						}

						int result = 0;

							if (mUriMatcher.match(uri)==101){
								SQLiteDatabase db = mDbHelper.getWritableDatabase();
								result = db.update("dates", values, "_id=?", new String[]{uri.getLastPathSegment()});	
								getContext().getContentResolver().notifyChange(uri, null);

							}else{

								throw new IllegalArgumentException("Unknown Uri "+uri);
							}

					return result;
				}

				static{
					mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
					//for getting all dates
					mUriMatcher.addURI(AUTHORITY, "dates", 100);
					//for getting single date
					mUriMatcher.addURI(AUTHORITY, "dates/#", 101);
				}
}



