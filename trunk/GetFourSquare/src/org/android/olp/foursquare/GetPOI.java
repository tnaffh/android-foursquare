package org.android.olp.foursquare;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GetPOI extends Activity{
	private double locID;
	private Location loCation;
	private LocationManager locmana;
	private LocationListener loclis;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Bundle receiver = this.getIntent().getExtras();
        String accessToken = receiver.getString("token");
        String requestURL = "https://api.foursquare.com/v2/users/self/checkins?oauth_token="+accessToken;
        String CLIENT_ID="V1ITGJWM1UMEBYSBIUXWKSDU0HBSEJ15FDAGITC2M2N4WTUX";
        String CLIENT_SECRET="RMFY3ZW4U3U5NDI5ZN4WRSPRJCPHVDJ5T1LYPCQSOGYJNXFU";
        String First ="https://api.foursquare.com/v2/venues/search?ll=40.7,-74&client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET;
        locmana = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        loclis = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				loCation = location;
			}
		};
		locmana.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loclis);
		locmana.removeUpdates(loclis);
		if(loCation!=null)
		locID = loCation.getLatitude();
		int loc = (int)locID;
		Toast toast = Toast.makeText(getApplicationContext(), Integer.toString(loc), Toast.LENGTH_SHORT);
		toast.show();
		String UriVenus = "https://api.foursquare.com/v2/venues/explore?ll=40.7,-74"+/*+Integer.toString(loc)+*/"&oauth_token="+accessToken;
		HttpGet get = new HttpGet(UriVenus);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse rp = client.execute(get);
			HttpEntity e = rp.getEntity();
			TextView tv = (TextView)findViewById(R.id.text);
			tv.setText(EntityUtils.toString(e));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}