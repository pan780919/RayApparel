package com.jhengweipan.RayApparel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import com.adlocus.PushAd;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.igexin.sdk.PushManager;
import com.jhengweipan.Guandisignonehundred.R;
import com.jhengweipan.MyAPI.VersionChecker;
import com.jhengweipan.ga.MyGAManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import myAppKey.mykey;


public class MainActivity extends Activity {
    private WebView mCityStroeWebView;
    Context context = MainActivity.this;
    ProgressDialog myDialog = null;
    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private String uri;
    private boolean isNetWork = true;
    private boolean isFirst = true;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyGAManager.sendScreenName(MainActivity.this, "首頁");
        Intent promotionIntent = new Intent(this, MainActivity.class);
        PushAd.enablePush(MainActivity.this, mykey.AdLoucsKey, promotionIntent);
//        PushAd.test(this);


        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(mykey.ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);
        PushManager.getInstance().initialize(this.getApplicationContext());
        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }




    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.e("landscape", "landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.e("portrait", "portrait");
        }
        super.onConfigurationChanged(newConfig);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   //確定按下退出鍵

            ConfirmExit(); //呼叫ConfirmExit()函數

            return true;

        }

        return super.onKeyDown(keyCode, event);

    }


    public void ConfirmExit() {

        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this); //創建訊息方塊

        ad.setTitle("離開");

        ad.setMessage("確定要離開?");

        ad.setPositiveButton("是", new DialogInterface.OnClickListener() { //按"是",則退出應用程式

            public void onClick(DialogInterface dialog, int i) {
                interstitial.show();
                MainActivity.this.finish();//關閉activity

            }

        });

        ad.setNegativeButton("否", new DialogInterface.OnClickListener() { //按"否",則不執行任何操作

            public void onClick(DialogInterface dialog, int i) {

            }

        });

        ad.show();//顯示訊息視窗


    }



}



