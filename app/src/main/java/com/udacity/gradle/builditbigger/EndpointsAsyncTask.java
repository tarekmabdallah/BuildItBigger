package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
    private static MyApi myApiService = null;
    private static final String ROOT_URL = "http://10.0.2.2:8080/_ah/api/";
    // app url "https://jokesapp-219517.appspot.com"
    // --- for  physical device  http://192.168.1.2:8080/_ah/api/
    // --- for emulator http://10.0.2.2:8080/_ah/api/
    @Override
    protected String doInBackground(Void... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(ROOT_URL)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    }).setApplicationName("JokesApp");
           myApiService = builder.build();
        }

        try {
            return  myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            final String CAUSE = "\nCAUSE\n";
            return e.getMessage() + CAUSE +  e.getCause();
        }
    }

    @Override
    protected void onPostExecute(String result) {
    }
}