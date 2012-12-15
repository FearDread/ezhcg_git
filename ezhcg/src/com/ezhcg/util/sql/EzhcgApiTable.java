package com.ezhcg.util.sql;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class EzhcgApiTable {
	
	public static final String TABLE_API = "apikeys";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_API_KEY = "api";

	
		// Database creation SQL statement
	  	private static final String DATABASE_CREATE = "CREATE TABLE " 
	  			+ TABLE_API
	  			+ "(" 
	  			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
	  			+ COLUMN_API_KEY + " TEXT NOT NULL, " 
	  			+ ");";
	
	
	  		public static void onCreate(SQLiteDatabase database) {
	  			database.execSQL(DATABASE_CREATE);
	  		}
	
	
	  		public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
	  				Log.w(EzhcgApiTable.class.getName(), "Upgrading database from version "
	  						+ oldVersion + " to " + newVersion
	  						+ ", which will destroy all old data");
	  		    
	  					database.execSQL("DROP TABLE IF EXISTS " + TABLE_API);
	  		    
	  				onCreate(database);
	  		  }
}