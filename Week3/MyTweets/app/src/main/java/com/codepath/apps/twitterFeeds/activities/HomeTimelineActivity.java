package com.codepath.apps.twitterFeeds.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.TwitterRestApplication;
import com.codepath.apps.twitterFeeds.TwitterRestClient;
import com.codepath.apps.twitterFeeds.adapters.HomeTimelineAdapter;
import com.codepath.apps.twitterFeeds.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.twitterFeeds.fragments.UserInfoDialogFragment;
import com.codepath.apps.twitterFeeds.models.Tweet;
import com.codepath.apps.twitterFeeds.models.User;
import com.codepath.apps.twitterFeeds.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class HomeTimelineActivity extends AppCompatActivity {

    TwitterRestClient twitterRestClient;

    RecyclerView homeTimelineList;
    ArrayList<Tweet> tweetsList;
    HomeTimelineAdapter homeTimelineAdapter;

    FloatingActionButton composeTweetButton;
    ComposeTweetDialogFragment composeTweetDialogFragment;
    UserInfoDialogFragment userInfoDialogFragment;

    private User mCurrentUserInfo;
    private EndlessRecyclerViewScrollListener scrollListener;
    public static long mLastSinceId;
    public static long mLastMaxId;
    long newMaxId;


    public long getNewMaxId() {
        return newMaxId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);

        mLastSinceId = 1;
        mLastMaxId = 1;

        twitterRestClient = TwitterRestApplication.getRestClient();

        composeTweetButton = (FloatingActionButton) findViewById(R.id.composeTweetFAB);
        homeTimelineList = (RecyclerView) findViewById(R.id.homeTimelineRecyclerView);
        tweetsList = new ArrayList<>();
        homeTimelineAdapter = new HomeTimelineAdapter(getApplicationContext(), tweetsList);
        homeTimelineList.setAdapter(homeTimelineAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        homeTimelineList.setLayoutManager(linearLayoutManager);
        //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        //homeTimelineList.addItemDecoration(itemDecoration);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                Log.e("F", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                //mLastMaxId = mLastSinceId - 1;
                populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);
                Log.e("A", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                Log.e("H", "hit");
            }
        };
        // Adds the scroll listener to RecyclerView
        homeTimelineList.addOnScrollListener(scrollListener);

        populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);
        getCurrentUserInfoAndStore();

        // Make sure the current user info is retrieved first from the network
        //if (mCurrentUserInfo != null) {
            composeTweetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //createAndSendTweet("Hello, sample post3.");
                    //populateTheHomeTimeline();
                    FragmentManager fm = getSupportFragmentManager();
                    composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance("Send Tweet", mCurrentUserInfo);
                    composeTweetDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                    composeTweetDialogFragment.show(fm, "fragment_compose_tweet");

                    composeTweetDialogFragment.setmComposeDialogListener(new ComposeTweetDialogFragment.ComposeDialogListener() {
                        @Override
                        public void composeTweetToTwitter(String status) {
                            createAndSendTweet(status);
                            populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);
                        }
                    });

                }
            });
        //} else {
        //    Toast.makeText(getApplicationContext(), "Seems like your network is slow, please wait...", Toast.LENGTH_LONG).show();
        //}
    }

    private void clearAndResetTweetsList(ArrayList<Tweet> newTweetsList) {
        //tweetsList.clear();
        tweetsList.addAll(newTweetsList);
        homeTimelineAdapter.notifyDataSetChanged();
        scrollListener.resetState();
        //mLastSinceId++;
        //mLastMaxId = mLastSinceId;
    }

    private void populateTheHomeTimeline(int count, final long sinceId, long maxId) {
        if (mLastSinceId != 1) {
            newMaxId = mLastSinceId - 1;
            mLastMaxId = newMaxId;
        }
        twitterRestClient.getHomeTimeline(count, sinceId, maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.e("DEBUG", response.toString());
                clearAndResetTweetsList(Tweet.fromJsonArray(response));//845130414020747264, 38803325
                // TODO : START HERE->mLastMaxId = mLastSinceId;
                //tweetsList.addAll(Tweet.fromJsonArray(response));
                //homeTimelineAdapter.notifyDataSetChanged();
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
                tweetsList.add(0, Tweet.fromJson(response));
                homeTimelineAdapter.notifyItemInserted(0);
                homeTimelineList.scrollToPosition(0);
                scrollListener.resetState();
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

    private void showUserInfoDialog() {
        FragmentManager fm = getSupportFragmentManager();
        userInfoDialogFragment = UserInfoDialogFragment.newInstance("User Info", mCurrentUserInfo);
        userInfoDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        userInfoDialogFragment.show(fm, "fragment_compose_tweet");
    }
}
