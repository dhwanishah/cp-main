package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.restclienttemplate.adapters.HomeTimelineAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        twitterRestClient = TwitterRestApplication.getRestClient();

        homeTimelineList = (RecyclerView) findViewById(R.id.homeTimelineRecyclerView);
        tweetsList = new ArrayList<>();
        homeTimelineAdapter = new HomeTimelineAdapter(getApplicationContext(), tweetsList);
        homeTimelineList.setAdapter(homeTimelineAdapter);
        homeTimelineList.setLayoutManager(new LinearLayoutManager(this));



        populateTheHomeTimeline();
    }

    private void populateTheHomeTimeline() {
        twitterRestClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweetsList.addAll(Tweet.fromJsonArray(response));
                homeTimelineAdapter.notifyDataSetChanged();
                Log.e("DEBUG", tweetsList.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
