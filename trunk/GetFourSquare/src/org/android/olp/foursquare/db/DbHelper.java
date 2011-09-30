package org.android.olp.foursquare.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "venues";
	private static final int DB_VERSION = 1;
	private static final String DB_CREATE = "create table venues (id integer primary key, name text, address text, distance text )";

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(DB_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int old, int newVer) {
		db.execSQL("DROP TABLE IF EXISTS venues");
		onCreate(db);
	}
}
