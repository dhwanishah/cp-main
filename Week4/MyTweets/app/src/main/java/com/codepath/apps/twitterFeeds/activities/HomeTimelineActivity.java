package com.codepath.apps.twitterFeeds.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.fragments.TweetsListFragment;

public class HomeTimelineActivity extends AppCompatActivity {


    TweetsListFragment tweetsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);

        if (savedInstanceState == null) {
            tweetsListFragment = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.userInfo:
                tweetsListFragment.showUserInfoDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
