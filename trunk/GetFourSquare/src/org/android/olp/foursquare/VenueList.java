package org.android.olp.foursquare;

import org.android.olp.foursquare.communicate.Four;
import org.android.olp.foursquare.communicate.IFourSquareAPI;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VenueList extends Activity {
	
	private IFourSquareAPI fsqAPI;
	private String accessToken;
	private EditText txt_venue_out;
	@Override
	public void onCreate(Bundle bundel) {
		super.onCreate(bundel);

		setContentView(R.layout.venue_list);
		accessToken = this.getIntent().getExtras().getString("token");
		fsqAPI = new Four(accessToken, (LocationManager)getSystemService(Context.LOCATION_SERVICE));
		txt_venue_out = (EditText)findViewById(R.id.venue_list_out);
		txt_venue_out.setText(fsqAPI.getVenues());
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.check:
			fsqAPI = new Four(accessToken, (LocationManager)getSystemService(Context.LOCATION_SERVICE));
			txt_venue_out.setText(fsqAPI.getVenues());
			return true;
		case R.id.Logout:
			return true;
		}
		return false;
	}
}
