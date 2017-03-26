package com.codepath.apps.twitterFeeds.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.models.User;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by DhwaniShah on 3/22/17.
 */

public class ComposeTweetDialogFragment extends DialogFragment {

    private static User mCurrentUserInfo;

    private ImageView mCurrentUserProfileImage;
    private TextView mCurrentUserScreenName;
    private TextView mPostCharsLeft;
    private EditText mTweetPost;
    private Button mPostTweetButton;

    private ComposeDialogListener mComposeDialogListener;

    private int charsLeftOnPost = 140;

    public ComposeTweetDialogFragment() {
        this.mComposeDialogListener = null;
    }

    public static ComposeTweetDialogFragment newInstance(String tweetTitle, User currentUserInfo) {
        mCurrentUserInfo = currentUserInfo;
        ComposeTweetDialogFragment frag = new ComposeTweetDialogFragment();
        Bundle args = new Bundle();
        args.putString("tweetPostTitle", tweetTitle);
        frag.setArguments(args);
        return frag;
    }

    public interface ComposeDialogListener {
        void composeTweetToTwitter(String status);
    }

    public void setmComposeDialogListener(ComposeDialogListener mComposeDialogListener) {
        this.mComposeDialogListener = mComposeDialogListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose_tweet, container);
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

        mCurrentUserScreenName = (TextView) view.findViewById(R.id.currentUserScreenName);
        mCurrentUserScreenName.setText(mCurrentUserInfo.getScreenName());

        mPostCharsLeft = (TextView) view.findViewById(R.id.postCharsLeft);
        mTweetPost = (EditText) view.findViewById(R.id.tweetPostEditText);
        mPostTweetButton = (Button) view.findViewById(R.id.postTweetButton);
        String tweetPostTitle = getArguments().getString("tweetPostTitle", "Compose message");
        getDialog().setTitle(tweetPostTitle);
        mTweetPost.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mPostTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mTweetPost.getText().toString())) {
                    mComposeDialogListener.composeTweetToTwitter(mTweetPost.getText().toString());
                    //composeDialogListener(mTweetPost.getText().toString());
                    Log.e("POST", mTweetPost.getText().toString());
                    getDialog().dismiss();
                }
            }
        });

        mTweetPost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPostCharsLeft.setText(String.valueOf(charsLeftOnPost - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
