package org.android.olp.foursquare.db;

import java.util.List;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_DIST = "distance";
	
	private static final String DB_TABLE = "venues";
	
	private Context context;
	private SQLiteDatabase db;
	private DbHelper dbHelper;
	
	public DBAdapter (Context context) {
		this.context = context;
	}
	
	public DBAdapter open() throws 	SQLException {
		dbHelper = new DbHelper(context);
		db =dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		dbHelper.close();
	}
	
	/**
	 * Insert an record to the database
	 * 
	 * @param name
	 * @param address
	 * @param distance
	 * 
	 * @return the rowid of the new venue or -1 if have an error
	 */
	public long createRecord(String name, String address, String distance) {
		ContentValues value = createContent(name, address, distance);
		return db.insert(DB_TABLE, null, value);
	}
	
	/**
	 * Refresh the database
	 * 
	 * @param input
	 * @return true if success
	 */
	public boolean refresh(List<JSONObject> input) {
		db.execSQL("DELETE FROM "+DB_TABLE);
		for (int i = 0; i < input.size(); i++) {
			JSONObject js = input.get(i);
			createRecord(js.optString("name"), js.optString("address"), js.optString("distance"));
			Log.i("DB", js.toString() + " added");
		}
		Log.i("DB", "refresh databse! ");
		return true;
	}
	
	/**
	 * Fetch all the record in the database
	 * 
	 * @return Cursor object
	 */
	public Cursor fetchAll() {
		return db.query(DB_TABLE, new String[] {KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_DIST}, null, null, null, null, null);
	}
	
	private ContentValues createContent(String name, String address, String distance) {
		ContentValues value = new ContentValues();
		value.put(KEY_NAME, name);
		value.put(KEY_ADDRESS, address);
		value.put(KEY_DIST, distance);
		return value;
	}
}