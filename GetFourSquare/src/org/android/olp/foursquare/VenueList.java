package org.android.olp.foursquare;

import java.util.ArrayList;
import java.util.List;

import org.android.olp.foursquare.communicate.Four;
import org.android.olp.foursquare.communicate.IFourSquareAPI;
import org.android.olp.foursquare.db.DBAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class VenueList extends Activity {

	private IFourSquareAPI fsqAPI;
	private String accessToken;
	private JSONObject js;

	@Override
	public void onCreate(Bundle bundel) {
		super.onCreate(bundel);

		setContentView(R.layout.venue_list);
		accessToken = this.getIntent().getExtras().getString("token");
		fsqAPI = new Four(accessToken, (LocationManager)getSystemService(Context.LOCATION_SERVICE));
		Log.i("FSQAPI", fsqAPI.getVenues());
		Button btn_CheckIn = (Button) findViewById(R.id.ChekIn);
		btn_CheckIn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = fsqAPI.getVenues();
				Log.i("GPS", s);
				//EditText txt_out = (EditText) findViewById(R.id.venue_list_out);
				try {
					js = new JSONObject(s);
					JSONArray groups = js.getJSONObject("response")
							.getJSONArray("groups");
					List<JSONObject> venue_names = new ArrayList<JSONObject>();
					for (int i = 0; i < groups.length(); i++) {
						JSONArray items = groups.optJSONObject(i).getJSONArray(
								"items");
						for (int j = 0; j < items.length(); j++) {
							JSONObject tempObj = new JSONObject();
							tempObj.put("name", items.optJSONObject(j)
									.getJSONObject("venue").optString("name"));
							tempObj.put(
									"address",
									items.optJSONObject(j)
											.getJSONObject("venue")
											.getJSONObject("location")
											.optString("address"));
							tempObj.put(
									"distance",
									items.optJSONObject(j)
											.getJSONObject("venue")
											.getJSONObject("location")
											.optInt("distance"));
							
							tempObj.put("icon", 
									items.optJSONObject(j)
										.getJSONObject("venue")
										.getJSONArray("categories")
										.getJSONObject(0)
										.optString("icon"));
							
							venue_names.add(tempObj);
							Log.i("JSON", tempObj.toString());
						}
					}
					Log.i("DATA", venue_names.toString());
					// txt_out.setText(venueList.toString(4));
					// Log.i("JSON", venueList.toString(4));
					Intent i = new Intent(VenueList.this, Venues.class);
					Bundle b = new Bundle();

					b.putString("result", new JSONArray(venue_names).toString());
					i.putExtras(b);
					startActivity(i);
				} catch (JSONException e) {
					Log.e("JSON", e.getMessage());
					Toast.makeText(VenueList.this, e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		Button btn_Update = (Button) findViewById(R.id.Update);
		btn_Update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s = fsqAPI.getVenues();
				Log.i("GPS", s);
				try {
					js = new JSONObject(s);
					JSONArray groups = js.getJSONObject("response")
							.getJSONArray("groups");
					List<JSONObject> venue_names = new ArrayList<JSONObject>();
					for (int i = 0; i < groups.length(); i++) {
						JSONArray items = groups.optJSONObject(i).getJSONArray(
								"items");
						for (int j = 0; j < items.length(); j++) {
							JSONObject tempObj = new JSONObject();
							tempObj.put("name", items.optJSONObject(j)
									.getJSONObject("venue").optString("name"));
							tempObj.put(
									"address",
									items.optJSONObject(j)
											.getJSONObject("venue")
											.getJSONObject("location")
											.optString("address"));
							tempObj.put(
									"distance",
									items.optJSONObject(j)
											.getJSONObject("venue")
											.getJSONObject("location")
											.optInt("distance"));
							tempObj.put("icon", 
									items.optJSONObject(j)
										.getJSONObject("venue")
										.getJSONArray("categories")
										.getJSONObject(0)
										.optString("icon"));
							
							venue_names.add(tempObj);
							Log.i("JSON", tempObj.toString());
							
						}
					}
					Log.i("DATA", venue_names.toString());
					DBAdapter db = new DBAdapter(VenueList.this);
					db.open();
					db.refresh(venue_names);
					db.close();

				} catch (JSONException e) {
					Log.e("JSON", e.getMessage());
					Toast.makeText(VenueList.this, e.getMessage(),
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		Button btn_CheckInOffline = (Button) findViewById(R.id.CheckInOffline);
		btn_CheckInOffline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBAdapter db = new DBAdapter(VenueList.this);
				db.open();
				Cursor cr = db.fetchAll();
				
				JSONArray jsarray = new JSONArray();
				cr.moveToFirst();
				while (cr.isAfterLast() == false) {
					Log.i("DB","" + cr.getColumnCount());
					JSONObject js = new JSONObject();
					try {
						Log.i("DB", cr.getString(1) + " " + cr.getString(2) + " " +cr.getString(3));
						js.put("name", cr.getString(1));
						js.put("address", cr.getString(2));
						js.put("distance", cr.getString(3));
						Log.i("JSON", "Add the Object " + js.toString() + " !");
						jsarray.put(js);

					} catch (JSONException e) {
						Log.e("JSON", e.getMessage());
						Toast.makeText(VenueList.this, e.getMessage(),
								Toast.LENGTH_SHORT).show();
					}

					cr.moveToNext();
				}
				cr.close();
				db.close();
				Intent i = new Intent(VenueList.this, Venues.class);
				Bundle b = new Bundle();

				b.putString("result", jsarray.toString());
				i.putExtras(b);
				startActivity(i);

			}
		});

	}
}
