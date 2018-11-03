package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tell_joke_btn)
    void onClickTellJokeBtn() {
        bar.setVisibility(VISIBLE);
        new EndpointsAsyncTask().execute(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        bar.setVisibility(GONE);
    }

}