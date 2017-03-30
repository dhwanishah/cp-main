package com.codepath.apps.twitterFeeds.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.TwitterRestApplication;
import com.codepath.apps.twitterFeeds.TwitterRestClient;
import com.codepath.apps.twitterFeeds.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.twitterFeeds.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterFeeds.fragments.MentionsTimelineFragment;
import com.codepath.apps.twitterFeeds.fragments.TweetsListFragment;
import com.codepath.apps.twitterFeeds.fragments.UserInfoDialogFragment;
import com.codepath.apps.twitterFeeds.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeTimelineActivity extends AppCompatActivity {


    TweetsListFragment tweetsListFragment;
    HomeTimelineFragment homeTimelineFragment;
    TwitterRestClient twitterRestClient;

    FloatingActionButton composeTweetButton;
    ComposeTweetDialogFragment composeTweetDialogFragment;
    UserInfoDialogFragment userInfoDialogFragment;

    private User mCurrentUserInfo;

    ViewPager tabsViewPager;
    PagerSlidingTabStrip tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);

        twitterRestClient = TwitterRestApplication.getRestClient();
        composeTweetButton = (FloatingActionButton) findViewById(R.id.composeTweetFAB);
        tabsViewPager = (ViewPager) findViewById(R.id.tabsViewPager);
        tabsViewPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabsStrip);
        tabStrip.setViewPager(tabsViewPager);


        if (savedInstanceState == null) {
            homeTimelineFragment = new HomeTimelineFragment();
        }

//        composeTweetButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //createAndSendTweet("Hello, sample post3.");
//                //populateTheHomeTimeline();
//                FragmentManager fm = getSupportFragmentManager();
//                composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance("Send Tweet", mCurrentUserInfo);
//                composeTweetDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//                composeTweetDialogFragment.show(fm, "fragment_compose_tweet");
//
//                composeTweetDialogFragment.setmComposeDialogListener(new ComposeTweetDialogFragment.ComposeDialogListener() {
//                    @Override
//                    public void composeTweetToTwitter(String status) {
//                        createAndSendTweet(status);
//                        /*populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);*/
//                    }
//                });
//
//            }
//        });

        getCurrentUserInfoAndStore();
    }

    public void clickComposeNewTweetFAB(View v) {
        FragmentManager fm = getSupportFragmentManager();
        composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance("Send Tweet", mCurrentUserInfo);
        composeTweetDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        composeTweetDialogFragment.show(fm, "fragment_compose_tweet");

        composeTweetDialogFragment.setmComposeDialogListener(new ComposeTweetDialogFragment.ComposeDialogListener() {
            @Override
            public void composeTweetToTwitter(String status) {
                createAndSendTweet(status);
                        /*populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);*/
            }
        });
    }

    public void showUserInfoDialog() {
        FragmentManager fm = getSupportFragmentManager();
        userInfoDialogFragment = UserInfoDialogFragment.newInstance("User Info", mCurrentUserInfo);
        userInfoDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        userInfoDialogFragment.show(fm, "fragment_compose_tweet");
    }

    private void getCurrentUserInfoAndStore() {
        twitterRestClient.getCurrentUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mCurrentUserInfo = User.fromJson(response);
                Log.e("sdf", mCurrentUserInfo.getName() + " " + mCurrentUserInfo.getProfileImageUrl() + " " + mCurrentUserInfo.getScreenName() + " " + mCurrentUserInfo.getUid());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void createAndSendTweet(String status) {
        twitterRestClient.postTweet(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                homeTimelineFragment.addNewTweetToList(response);
                Log.e("CREATE", response.toString());
                Toast.makeText(getApplicationContext(), "Tweet successfully posted.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
                Toast.makeText(getApplicationContext(), "Error posting tweet.", Toast.LENGTH_LONG).show();
            }
        });
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
                showUserInfoDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter {

        private String[] tabsTitle = { "Home Timeline", "Mentions" };

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitle[position];
        }

        @Override
        public int getCount() {
            return tabsTitle.length;
        }
    }

}
