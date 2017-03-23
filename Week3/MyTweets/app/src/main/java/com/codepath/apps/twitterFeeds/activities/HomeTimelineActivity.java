package com.codepath.apps.twitterFeeds.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.TwitterRestApplication;
import com.codepath.apps.twitterFeeds.TwitterRestClient;
import com.codepath.apps.twitterFeeds.adapters.HomeTimelineAdapter;
import com.codepath.apps.twitterFeeds.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.twitterFeeds.models.Tweet;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        twitterRestClient = TwitterRestApplication.getRestClient();

        composeTweetButton = (FloatingActionButton) findViewById(R.id.composeTweetFAB);
        homeTimelineList = (RecyclerView) findViewById(R.id.homeTimelineRecyclerView);
        tweetsList = new ArrayList<>();
        homeTimelineAdapter = new HomeTimelineAdapter(getApplicationContext(), tweetsList);
        homeTimelineList.setAdapter(homeTimelineAdapter);
        homeTimelineList.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        homeTimelineList.addItemDecoration(itemDecoration);

        populateTheHomeTimeline();

        composeTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createAndSendTweet("Hello, sample post3.");
                //populateTheHomeTimeline();
                FragmentManager fm = getSupportFragmentManager();
                composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance("Compose Message");
                composeTweetDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                composeTweetDialogFragment.show(fm, "fragment_compose_tweet");

                composeTweetDialogFragment.setmComposeDialogListener(new ComposeTweetDialogFragment.ComposeDialogListener() {
                    @Override
                    public void composeTweetToTwitter(String status) {
                        createAndSendTweet(status);
                        populateTheHomeTimeline();
                    }
                });

            }
        });

    }

    private void clearAndResetTweetsList(ArrayList<Tweet> newTweetsList) {
        tweetsList.clear();
        tweetsList.addAll(newTweetsList);
        homeTimelineAdapter.notifyDataSetChanged();
    }

    private void populateTheHomeTimeline() {
        twitterRestClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                clearAndResetTweetsList(Tweet.fromJsonArray(response));
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
}
