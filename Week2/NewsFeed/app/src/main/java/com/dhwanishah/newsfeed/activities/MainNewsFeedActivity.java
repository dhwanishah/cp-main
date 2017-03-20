package com.dhwanishah.newsfeed.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.dhwanishah.newsfeed.R;
import com.dhwanishah.newsfeed.adapters.StandardNewsListAdapter;
import com.dhwanishah.newsfeed.models.StandardNewsItem;
import com.dhwanishah.newsfeed.utils.GlobalFunctions;
import com.dhwanishah.newsfeed.utils.GlobalProperties;

import java.util.ArrayList;

public class MainNewsFeedActivity extends AppCompatActivity {

    GlobalFunctions globalFunctions = new GlobalFunctions(this);

    RecyclerView resultsList;
    StandardNewsListAdapter standardNewsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_feed);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        if (globalFunctions.isNetworkAvailable()) {
            if (globalFunctions.isOnline()) {
                ArrayList<StandardNewsItem> newsFeed = new ArrayList<>();
                newsFeed.add(new StandardNewsItem("New York Times", R.drawable.nytimes_logo));
                newsFeed.add(new StandardNewsItem("CNN", R.drawable.cnn_logo));
                resultsList = (RecyclerView) findViewById(R.id.newsFeedList);
                standardNewsListAdapter = new StandardNewsListAdapter(getApplicationContext(), newsFeed);
                resultsList.setAdapter(standardNewsListAdapter);
                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                resultsList.setLayoutManager(gridLayoutManager);


            } else {
                // TODO: Change these getResource getString to a function
                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.errorStrings)[GlobalProperties.ERR_TYPE.NO_INTERNET.ordinal()], Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.errorStrings)[GlobalProperties.ERR_TYPE.NO_NETWORK.ordinal()], Toast.LENGTH_LONG).show();
        }
    }
}
