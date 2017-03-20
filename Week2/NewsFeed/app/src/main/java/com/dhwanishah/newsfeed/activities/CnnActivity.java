package com.dhwanishah.newsfeed.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dhwanishah.newsfeed.R;
import com.dhwanishah.newsfeed.utils.GlobalProperties;

public class CnnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_feed);
        setTitle(getResources().getStringArray(R.array.newsFeedNames)[GlobalProperties.NEWS_TYPE.CNN.ordinal()]);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
    }
}
