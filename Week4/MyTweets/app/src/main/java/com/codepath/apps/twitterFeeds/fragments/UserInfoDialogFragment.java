package com.codepath.apps.twitterFeeds.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.models.User;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by DhwaniShah on 3/26/17.
 */

public class UserInfoDialogFragment extends DialogFragment  {
    private static User mCurrentUserInfo;

    private ImageView mCurrentUserProfileImage;
    private TextView mCurrentUserScreenName;
    private TextView mName;
    private TextView mTagline;
    private TextView mFavoriteCount;
    private TextView mFriendsCount;
    private TextView mFollowersCount;

    private static String mScreenName;

    public UserInfoDialogFragment() {
    }

    public static UserInfoDialogFragment newInstance(String tweetTitle, String screenName, User currentUserInfo) {
        mCurrentUserInfo = currentUserInfo;
        mScreenName = screenName;
        UserInfoDialogFragment frag = new UserInfoDialogFragment();
        Bundle args = new Bundle();
        args.putString("tweetPostTitle", tweetTitle);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mScreenName = (mScreenName == null) ? mCurrentUserInfo.getScreenName() : mScreenName;
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(mScreenName);
            //FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.userTimelineContainer, userTimelineFragment);
            ft.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCurrentUserProfileImage = (ImageView) view.findViewById(R.id.currentUserProfileImage);
        mCurrentUserProfileImage.setImageResource(0);
        Glide
                .with(getContext())
                .load(mCurrentUserInfo.getProfileImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(mCurrentUserProfileImage);

        mCurrentUserScreenName = (TextView) view.findViewById(R.id.currentName);
        mCurrentUserScreenName.setText(mCurrentUserInfo.getScreenName());
        mName = (TextView) view.findViewById(R.id.currentName);
        mName.setText(mCurrentUserInfo.getName());
        mTagline = (TextView) view.findViewById(R.id.userTagline);
        mTagline.setText(mCurrentUserInfo.getDescription());

        mFavoriteCount = (TextView) view.findViewById(R.id.userTotalFavoriteCount);
        mFavoriteCount.setText(String.valueOf(mCurrentUserInfo.getFavoriteCount()));
        mFriendsCount = (TextView) view.findViewById(R.id.userTotalFriendsCount);
        mFriendsCount.setText(String.valueOf(mCurrentUserInfo.getFavoriteCount()) + " friends");
        mFollowersCount = (TextView) view.findViewById(R.id.userTotalFollowersCount);
        mFollowersCount.setText(String.valueOf(mCurrentUserInfo.getFollowersCount()) + " followers");


        String tweetPostTitle = getArguments().getString("tweetPostTitle", "Compose message");
        getDialog().setTitle(tweetPostTitle);

    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }
}
