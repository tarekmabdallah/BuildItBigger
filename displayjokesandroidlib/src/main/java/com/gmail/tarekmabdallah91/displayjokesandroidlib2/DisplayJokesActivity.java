package com.gmail.tarekmabdallah91.displayjokesandroidlib2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayJokesActivity extends AppCompatActivity {

    public static final String JOKE_KEYWORD = "JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_jokes);

        String joke = getIntent().getStringExtra(JOKE_KEYWORD);

        TextView displayJokesTV = findViewById(R.id.display_jokes_tv);
        displayJokesTV.setText(joke);
    }
}
