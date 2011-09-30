package org.android.olp.foursquare.communicate;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class Four implements IFourSquareAPI {
	private String accessToken;
	private LocationManager locMana;
	private LocationLst locLst;

	public Four(String accessToken, LocationManager locmana) {
		this.accessToken = accessToken;
		this.locMana = locmana;
		this.locLst = new LocationLst();
	}


	public String getVenues() {
		String Json = "";
		Log.i("GSP", "Getting the location ...");
		locMana.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locLst);
		Log.i("GPS", "Location is requested. Longtitude: "+locLst.getLongtitude()+" Latitude: "+locLst.getLatitude());
		String UriVenus = "https://api.foursquare.com/v2/venues/explore?ll="
				+ locLst.getLatitude()+ "," + locLst.getLongtitude() + "&oauth_token=" + accessToken;
		Log.i("FSQAPI", UriVenus);
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
	
	private class LocationLst implements LocationListener {
		
		private double longtitude;
		private double latitude;

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			Log.d("GPS", "The location is set to: "+location.getLatitude()+ " " + location.getLongitude());
			longtitude = location.getLongitude();
			latitude = location.getLatitude();
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Log.d("GPS", "GPS is turned OFF");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Log.d("GPS", "GPS in turned ON");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			Log.d("GPS", "GPS status is changed");
		}
		
		public double getLongtitude() {
			return longtitude;
		}
		
		public double getLatitude() {
			return latitude;
		}
		
	}
}
