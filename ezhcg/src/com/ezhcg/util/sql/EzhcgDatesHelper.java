// Author: Garrett Haptonstall //
// =========================== //
// ==================================================================================== //
// INFO:                                                                                //
// This is the DB helper that creates the dates table                            		//
// used for Android API 3.0 +, used by EzhcgDateFragment Class and EzhcgDateProvider    //
// ==================================================================================== //


package com.ezhcg.util.sql;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.ezhcg.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class EzhcgDatesHelper extends SQLiteOpenHelper {

	private static final String TAG = "EzhcgDateHelper";
	private static final String DB_NAME = "dates_db";
	
	private static final int DB_VERSION = 3; //if this changes, onUpgrade() is called
	

	public static final SimpleDateFormat LONG_DATE_FORMAT =
			new SimpleDateFormat("EEEEEEE, MMMMMMMMM dd, yyyy",Locale.US);

	public static final SimpleDateFormat SHORT_DATE_FORMAT =
			new SimpleDateFormat("MM-dd-yy",Locale.US);

	//SQL CREATE statement that creates the inspection types table
	private static final String CREATE_DATES_TABLE = "CREATE TABLE dates (_id integer primary key autoincrement, date text);";	

	Context mContext;

		public EzhcgDatesHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
				mContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {	
			//create the dates table
			db.execSQL(CREATE_DATES_TABLE);
			
				populateDatesTable(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//just drop and load
			db.execSQL("DROP TABLE dates");
			db.execSQL(CREATE_DATES_TABLE);
			
				populateDatesTable(db);
		}

			private void populateDatesTable(SQLiteDatabase db){
				String[] dates = mContext.getResources().getStringArray(R.array.dates);
				
					for (String date : dates){
						ContentValues cv = new ContentValues();
							cv.put("date", date);
							db.insert("dates", null, cv);
						}
			}
}



