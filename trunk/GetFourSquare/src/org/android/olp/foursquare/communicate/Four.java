package org.android.olp.foursquare.communicate;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class Four implements IFourSquareAPI {
	private String accessToken;
	private LocationManager locmana;
	private LocationListener loclis;
	private String l;
	private String long_l;
	private Location loc;

	public Four(String accessToken, LocationManager locmana) {
		this.accessToken = accessToken;
		this.locmana = locmana;
		this.loclis = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				Log.i("GPS", "OnStatusChanged");

			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				Log.i("GPS", "GPS in ON");

			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				Log.i("GPS", "GPS is OFF");

			}

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				l = Double.toString(location.getLatitude());
				long_l = Double.toString(location.getLongitude());
				Log.i("GSP", "Location is set to : Latitude: " + l
						+ " Longtitude: " + long_l);
			}
		};
		
	}

	public String getVenues() {
		String Json = "";
		this.locmana.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this.loclis);
		Log.i("GPS", "Request new location");
		String UriVenus = "https://api.foursquare.com/v2/venues/explore?ll="
				+ l + "," + long_l + "&oauth_token=" + accessToken;
		Log.i("FSQAPI", UriVenus);
		Log.i("GSP", "Latitude: " + l + " Longtitude: " + long_l);
		HttpGet get = new HttpGet(UriVenus);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(get);
			HttpEntity e = response.getEntity();
			Json = EntityUtils.toString(e);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Json;
	}
}
