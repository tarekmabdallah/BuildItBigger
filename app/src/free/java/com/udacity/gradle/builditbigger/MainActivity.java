package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.concurrent.ExecutionException;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity.JOKE_KEYWORD;


public class MainActivity extends AppCompatActivity {

    @BindString(R.string.app_id_for_test_interstitial_ad)
    String appIdForTest;
    @BindString(R.string.back)
    String backMsg;
    @BindString(R.string.interstitial_not_loaded_msg)
    String interstitialNotLoadedMsg;

    @BindView(R.id.adView)
    AdView mAdView;

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
                Toast.makeText(getBaseContext(), backMsg , Toast.LENGTH_LONG).show();
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
        if (interstitialAd.isLoaded()) {
            interstitialAd.show(); // shown after the user return back to MainActivity
        } else {
            Toast.makeText(getBaseContext(), interstitialNotLoadedMsg , Toast.LENGTH_LONG).show();
        }

        try {
            String joke = new EndpointsAsyncTask().execute().get();
            sendJokeToDisplayJokesLib(joke);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void sendJokeToDisplayJokesLib(String joke){
        Intent displayJoke = new Intent(this, DisplayJokesActivity.class);
        displayJoke.putExtra(JOKE_KEYWORD, joke);
        startActivity(displayJoke);
    }

}