package com.codepath.apps.twitterFeeds.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.twitterFeeds.TwitterRestApplication;
import com.codepath.apps.twitterFeeds.TwitterRestClient;
import com.codepath.apps.twitterFeeds.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DhwaniShah on 3/31/17.
 */

public class UserTimelineFragment extends TweetsListFragment {
    TwitterRestClient twitterRestClient;

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        userTimelineFragment.setArguments(args);
        return userTimelineFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mLastSinceId = 1;
//        mLastMaxId = 1;
        twitterRestClient = TwitterRestApplication.getRestClient();
        populateTheHomeTimeline();

    }

    public void populateTheHomeTimeline() {
        String screenName = getArguments().getString("screenName");

        twitterRestClient.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.e("DEBUG_GETMENTIONS", response.toString());
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
}
