package com.codepath.apps.twitterFeeds.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.twitterFeeds.R;

/**
 * Created by DhwaniShah on 3/22/17.
 */

public class ComposeTweetDialogFragment extends DialogFragment {

    private EditText mTweetPost;
    private Button mPostTweetButton;

    private ComposeDialogListener mComposeDialogListener;

    public ComposeTweetDialogFragment() {
        this.mComposeDialogListener = null;
    }

    public static ComposeTweetDialogFragment newInstance(String tweetTitle) {
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
