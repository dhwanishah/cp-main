package com.codepath.apps.twitterFeeds.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.TwitterRestApplication;
import com.codepath.apps.twitterFeeds.TwitterRestClient;
import com.codepath.apps.twitterFeeds.adapters.HomeTimelineAdapter;
import com.codepath.apps.twitterFeeds.models.Tweet;
import com.codepath.apps.twitterFeeds.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.codepath.apps.twitterFeeds.fragments.HomeTimelineFragment.mLastMaxId;
import static com.codepath.apps.twitterFeeds.fragments.HomeTimelineFragment.mLastSinceId;

/**
 * Created by DhwaniShah on 3/28/17.
 */

public class TweetsListFragment extends Fragment {

    TwitterRestClient twitterRestClient;

    RecyclerView homeTimelineList;
    ArrayList<Tweet> tweetsList;
    HomeTimelineAdapter homeTimelineAdapter;
    LinearLayoutManager linearLayoutManager;

    EndlessRecyclerViewScrollListener scrollListener;


    private OnItemSelectedListener mClickListener;
    public interface OnItemSelectedListener {
        public void onProfileImageSelected(String screenName);
    }

    public void setCustomObjectListener(OnItemSelectedListener listener) {
        this.mClickListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        homeTimelineList = (RecyclerView) v.findViewById(R.id.homeTimelineRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        homeTimelineList.setLayoutManager(linearLayoutManager);
        homeTimelineList.setAdapter(homeTimelineAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                //Log.e("F", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                //mLastMaxId = mLastSinceId - 1;

                mLastMaxId = tweetsList.get(tweetsList.size()-1).getId();
                populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);
                //Log.e("A", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                //Log.e("H", "hit");
            }
        };

        // Adds the scroll listener to RecyclerView
        homeTimelineList.addOnScrollListener(scrollListener);

        return v;
    }

    private void populateTheHomeTimeline(int count, long sinceId, long maxId) {
        twitterRestClient.getHomeTimelineMaxId(count, maxId, new JsonHttpResponseHandler() {
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterRestClient = TwitterRestApplication.getRestClient();
        tweetsList = new ArrayList<>();
        homeTimelineAdapter = new HomeTimelineAdapter(getActivity(), tweetsList);
        homeTimelineAdapter.setCustomObjectListener(new HomeTimelineAdapter.OnItemSelectedListener() {
            @Override
            public void onProfileImageSelected(String screenName) {
                //Log.e("onProfileImageSelected", screenName);
                mClickListener.onProfileImageSelected(screenName);
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                userInfoDialogFragment = UserInfoDialogFragment.newInstance("User Info", screenName, ((HomeTimelineActivity) getActivity().getRe);
//                userInfoDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
//                userInfoDialogFragment.show(fm, "fragment_compose_tweet");
            }
        });
    }

    public void clearAndResetTweetsList(ArrayList<Tweet> newTweetsList) {
        //tweetsList.clear();
        tweetsList.addAll(newTweetsList);
        homeTimelineAdapter.notifyDataSetChanged();
        scrollListener.resetState();
        //mLastSinceId++;
        //mLastMaxId = mLastSinceId;
    }
}
