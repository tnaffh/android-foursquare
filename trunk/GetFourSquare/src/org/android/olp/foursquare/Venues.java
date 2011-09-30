package org.android.olp.foursquare;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Venues extends ListActivity {

	private String result;
	private JSONArray js;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		result = this.getIntent().getExtras().getString("result");
		try {
			js = new JSONArray(result);
			Log.i("DATA", result);
			List<JSONObject> venue_names = new ArrayList<JSONObject>();
			for (int i = 0; i< js.length(); i++) {
				venue_names.add(js.optJSONObject(i));
			}
			// Create an array of Strings, that will be put to our ListActivity
			String[] names = new String[] { "Linux", "Windows7", "Eclipse",
					"Suse", "Ubuntu", "Solaris", "Android", "iPhone" };
//			venue_names.toArray(names);
			// Create an ArrayAdapter, that will actually make the Strings above
			// appear in the ListView
			this.setListAdapter(new MyListAdapter(this, venue_names));
		} catch (JSONException e) {
			Log.e("JSON", e.getMessage());
		}

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Object o = this.getListAdapter().getItem(position);
		String keyword = o.toString();
		Toast.makeText(this, "You selected: " + keyword, Toast.LENGTH_SHORT)
				.show();
	}
}
