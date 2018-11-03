package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.widget.Toast.LENGTH_LONG;


public class MainActivity extends AppCompatActivity {

    @BindString(R.string.app_id_for_test_interstitial_ad)
    String appIdForTest;
    @BindString(R.string.back)
    String backMsg;
    @BindString(R.string.interstitial_not_loaded_msg)
    String interstitialNotLoadedMsg;
    @BindView(R.id.adView)
    AdView mAdView;
    @BindView(R.id.progress_bar)
    ProgressBar bar;

    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setBannerAd();
        setInterstitialAd();
    }

    private void setBannerAd (){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void setInterstitialAd (){
        MobileAds.initialize(this, appIdForTest);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(appIdForTest);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Toast.makeText(getBaseContext(), backMsg, LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @OnClick(R.id.tell_joke_btn)
    void onClickTellJokeBtn (){
        bar.setVisibility(VISIBLE);
        new EndpointsAsyncTask().execute(this);
        if (interstitialAd.isLoaded()) {
            interstitialAd.show(); // shown after the user return back to MainActivity
        } else { // if the ads not loaded ask the user to try again after some seconds
            interstitialAd.loadAd(new AdRequest.Builder().build());
            Toast.makeText(getBaseContext(), interstitialNotLoadedMsg, LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        bar.setVisibility(GONE);
    }
}