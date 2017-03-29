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
 * Created by DhwaniShah on 3/29/17.
 */

public class HomeTimelineFragment extends TweetsListFragment {

    TwitterRestClient twitterRestClient;

    public static long mLastSinceId;
    public static long mLastMaxId;
    long newMaxId;

    public long getNewMaxId() {
        return newMaxId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLastSinceId = 1;
        mLastMaxId = 1;
        twitterRestClient = TwitterRestApplication.getRestClient();
        populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);

    }

    private void populateTheHomeTimeline(int count, final long sinceId, long maxId) {
//        if (mLastSinceId != 1) {
//            newMaxId = mLastSinceId - 1;
//            mLastMaxId = newMaxId;
//        }
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
}
