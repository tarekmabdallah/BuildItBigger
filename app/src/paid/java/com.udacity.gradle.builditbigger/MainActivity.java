package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity.JOKE_KEYWORD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tell_joke_btn)
    void onClickTellJokeBtn() {
        try {
            String joke = new EndpointsAsyncTask().execute().get();
            sendJokeToDisplayJokesLib(joke);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void sendJokeToDisplayJokesLib(String joke) {
        Intent displayJoke = new Intent(this, DisplayJokesActivity.class);
        displayJoke.putExtra(JOKE_KEYWORD, joke);
        startActivity(displayJoke);
    }

}