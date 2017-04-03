package com.codepath.apps.twitterFeeds.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.TwitterRestApplication;
import com.codepath.apps.twitterFeeds.TwitterRestClient;
import com.codepath.apps.twitterFeeds.adapters.HomeTimelineAdapter;
import com.codepath.apps.twitterFeeds.models.Tweet;
import com.codepath.apps.twitterFeeds.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;

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

                /*Log.e("F", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                //mLastMaxId = mLastSinceId - 1;
                populateTheHomeTimeline(25, mLastSinceId, mLastMaxId);
                Log.e("A", mLastMaxId + " " + (mLastSinceId-1) + " " + newMaxId);
                Log.e("H", "hit");*/
            }
        };

        // Adds the scroll listener to RecyclerView
        homeTimelineList.addOnScrollListener(scrollListener);

        return v;
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
