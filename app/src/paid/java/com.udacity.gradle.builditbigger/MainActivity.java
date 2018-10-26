package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity;

import java.util.concurrent.ExecutionException;

import static com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity.JOKE_KEYWORD;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
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