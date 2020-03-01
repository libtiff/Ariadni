package libtiff.ariadni;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
{
    private AdView mAdView;
    private WebView webview1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = findViewById(R.id.AdView);
        webview1  = (WebView) findViewById(R.id.webview1);

        mAdView = findViewById(R.id.AdView);
        webview1  = (WebView) findViewById(R.id.webview1);

        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().setAcceptCookie(true);
        MobileAds.initialize(this, "ca-app-pub-9082725429338291~9094125652");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        final Activity activity = this;

        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        webview1.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                // DO NOT CALL SUPER METHOD
                onReceivedSslError(view, handler, error);
            }
        });

        if (wifi.isWifiEnabled() || connectivityManager.getActiveNetworkInfo()!= null){
            //wifi/data is enabled
            webview1.getSettings().setSupportZoom(true);
            webview1.getSettings().setBuiltInZoomControls(true);
            webview1.getSettings().setDisplayZoomControls(false);
            webview1.getSettings().setDomStorageEnabled(true);
            webview1.getSettings().setJavaScriptEnabled(true);
            webview1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webview1.getSettings().setUseWideViewPort(true);
            webview1.getSettings().setLoadWithOverviewMode(true);
            webview1.getSettings().setDefaultTextEncodingName("utf-8");
            webview1 .loadUrl("https://eservices.cyprus.gov.cy/EL/Secure/Pages/Home.aspx");
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No internet connection , please enable your wifi or data connection. Ενεργοποιήστε το ίντερνετ ή τα δεδομένα σας για να μπορέσει να τρέξει η εφαρμογή.",Toast.LENGTH_LONG).show();
            finish();
        }
    }
    @Override
    public void onBackPressed()
    {
        if (webview1.canGoBack())
        {
            webview1.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}

