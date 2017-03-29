package com.codepath.apps.twitterFeeds.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.TwitterRestApplication;
import com.codepath.apps.twitterFeeds.TwitterRestClient;
import com.codepath.apps.twitterFeeds.adapters.HomeTimelineAdapter;
import com.codepath.apps.twitterFeeds.models.Tweet;
import com.codepath.apps.twitterFeeds.models.User;
import com.codepath.apps.twitterFeeds.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DhwaniShah on 3/28/17.
 */

public class TweetsListFragment extends Fragment {

    TwitterRestClient twitterRestClient;

    RecyclerView homeTimelineList;
    ArrayList<Tweet> tweetsList;
    HomeTimelineAdapter homeTimelineAdapter;
    LinearLayoutManager linearLayoutManager;

    FloatingActionButton composeTweetButton;
    ComposeTweetDialogFragment composeTweetDialogFragment;
    UserInfoDialogFragment userInfoDialogFragment;

    private EndlessRecyclerViewScrollListener scrollListener;

    private User mCurrentUserInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        composeTweetButton = (FloatingActionButton) v.findViewById(R.id.composeTweetFAB);
        homeTimelineList = (RecyclerView) v.findViewById(R.id.homeTimelineRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        homeTimelineList.setLayoutManager(linearLayoutManager);
        homeTimelineList.setAdapter(homeTimelineAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                /*Log.e("F", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                //mLastMaxId = mLastSinceId - 1;
                populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);
                Log.e("A", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                Log.e("H", "hit");*/
            }
        };

        // Adds the scroll listener to RecyclerView
        homeTimelineList.addOnScrollListener(scrollListener);

        composeTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createAndSendTweet("Hello, sample post3.");
                //populateTheHomeTimeline();
                FragmentManager fm = getActivity().getSupportFragmentManager();
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
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterRestClient = TwitterRestApplication.getRestClient();
        tweetsList = new ArrayList<>();
        homeTimelineAdapter = new HomeTimelineAdapter(getActivity(), tweetsList);

        getCurrentUserInfoAndStore();
    }

    public void clearAndResetTweetsList(ArrayList<Tweet> newTweetsList) {
        //tweetsList.clear();
        tweetsList.addAll(newTweetsList);
        homeTimelineAdapter.notifyDataSetChanged();
        scrollListener.resetState();
        //mLastSinceId++;
        //mLastMaxId = mLastSinceId;
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
                Toast.makeText(getActivity(), "Tweet successfully posted.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("Error", errorResponse.toString());
                Toast.makeText(getActivity(), "Error posting tweet.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showUserInfoDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
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


}
