package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.gmail.tarekmabdallah91.displayjokesandroidlib2.DisplayJokesActivity.JOKE_KEYWORD;

class EndpointsAsyncTask extends AsyncTask<Context, Void, Pair<Context, String>> {

    private static MyApi myApiService = null;
    private static final String APP_NAME = "JokesApp";
    private static final String ROOT_URL = "http://10.0.2.2:8080/_ah/api/";
    private static final int ZERO = 0;
    // app url "https://jokesapp-219517.appspot.com"
    // --- for  physical device  http://192.168.1.2:8080/_ah/api/
    // --- for emulator http://10.0.2.2:8080/_ah/api/
    @Override
    protected Pair<Context, String> doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    }).setApplicationName(APP_NAME);
           myApiService = builder.build();
        }

        try {
            return new Pair<>(params[ZERO], myApiService.getJoke().execute().getData());
        } catch (IOException e) {
            final String ERROR_MSG = "Error Retrieving Jokes";
            Log.i(ERROR_MSG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Pair<Context, String> contextStringPair) {
        super.onPostExecute(contextStringPair);
        if (null != contextStringPair)
            sendJokeToDisplayJokesLib(contextStringPair.first, contextStringPair.second);
    }

    private void sendJokeToDisplayJokesLib(Context context, String joke) {
        Intent displayJoke = new Intent(context, DisplayJokesActivity.class);
        displayJoke.putExtra(JOKE_KEYWORD, joke);
        displayJoke.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(displayJoke);
    }
}