package org.android.olp.foursquare;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class FourSquareLogin extends Activity {
    public static final String CALLBACK_URL = "http://www.google.com";
    public static final String CLIENT_ID = "V1ITGJWM1UMEBYSBIUXWKSDU0HBSEJ15FDAGITC2M2N4WTUX";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        String url =
            "https://foursquare.com/oauth2/authenticate" + 
                "?client_id=" + CLIENT_ID + 
                "&response_type=token" + 
                "&redirect_uri=" + CALLBACK_URL;
        WebView webview = (WebView)findViewById(R.id.view);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String fragment = "#access_token=";
                int start = url.indexOf(fragment);
                if (start > -1) {
                    // You can use the accessToken for api calls now.
                    String accessToken = url.substring(start + fragment.length(), url.length());
                    Bundle sendAccessToken = new Bundle();
                    sendAccessToken.putString("token", accessToken);
                    Intent i = new Intent(FourSquareLogin.this,GetPOI.class);
                    i.putExtras(sendAccessToken);
                    startActivity(i);
//                    /Log.v(TAG, "OAuth complete, token: [" + accessToken + "].");
                    Toast.makeText(FourSquareLogin.this, "Token: " + accessToken, Toast.LENGTH_SHORT).show();
                }
            }
        });
        webview.loadUrl(url);
        //finish();
    }
}