package org.android.olp.foursquare;

import org.android.olp.foursquare.communicate.Four;
import org.android.olp.foursquare.communicate.IFourSquareAPI;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
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
		TextView txt_venue_list_title = (TextView)findViewById(R.id.venue_list_title);
		accessToken = this.getIntent().getExtras().getString("token");
		txt_venue_list_title.setText(accessToken);
		
		fsqAPI = new Four(accessToken, (LocationManager)getSystemService(Context.LOCATION_SERVICE));
		
		txt_venue_out = (EditText)findViewById(R.id.venue_list_out);
		txt_venue_out.setText(fsqAPI.getVenues());
		
		// use button to update GPS
		
		Button loadGPS = (Button)findViewById(R.id.ChekIn);
		loadGPS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				fsqAPI = new Four(accessToken, (LocationManager)getSystemService(Context.LOCATION_SERVICE));
				txt_venue_out.setText(fsqAPI.getVenues());
				
			}
		});
		
	}
}
