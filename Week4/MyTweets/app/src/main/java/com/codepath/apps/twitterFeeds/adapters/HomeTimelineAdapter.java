package com.codepath.apps.twitterFeeds.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterFeeds.R;
import com.codepath.apps.twitterFeeds.models.Tweet;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by DhwaniShah on 3/21/17.
 */

public class HomeTimelineAdapter extends RecyclerView.Adapter<HomeTimelineAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context mContext;
    private OnItemSelectedListener mClickListener;

    public HomeTimelineAdapter(Context context, List<Tweet> tweets) {
        this.mContext = context;
        this.mTweets = tweets;
    }

    public interface OnItemSelectedListener {
        public void onProfileImageSelected(String screenName);
    }

    public void setCustomObjectListener(OnItemSelectedListener listener) {
        this.mClickListener = listener;
    }

    @Override
    public HomeTimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_home_timeline, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tweet tweet = mTweets.get(position);

        ImageView profileImage = holder.profileImage;
        profileImage.setImageResource(0);
        Glide
                .with(mContext)
                .load(tweet.getUser().getProfileImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onProfileImageSelected(tweet.getUser().getScreenName());
                //Toast.makeText(mContext, "C " + tweet.getUser().getScreenName(), Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(mContext, ProfileViewActivity.class);
//                i.putExtra("screenName", tweet.getUser().getScreenName());
//                mContext.startActivity(i);
            }
        });

        TextView name = holder.name;
        name.setText(tweet.getUser().getName());
        TextView screenName = holder.screenName;
        screenName.setText(tweet.getUser().getScreenName());
        TextView body = holder.body;
        body.setText(tweet.getBody());
        TextView createdAt = holder.createdAt;
        createdAt.setText(tweet.getCreatedAt());
        TextView retweetCount = holder.retweetCount;
        retweetCount.setText(String.valueOf(tweet.getRetweetCount()));
        ImageView isFavorited = holder.isFavorited;
        if (tweet.isFavorited()) {
            isFavorited.setImageResource(R.drawable.star_selected_icon);
        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profileImage;
        public TextView name;
        public TextView screenName;
        public TextView body;
        public TextView createdAt;
        public TextView retweetCount;
        public ImageView isFavorited;

        public ViewHolder(View itemView) {
            super(itemView);

            profileImage = (ImageView) itemView.findViewById(R.id.profileImageView);
            name = (TextView) itemView.findViewById(R.id.nameTextView);
            screenName = (TextView) itemView.findViewById(R.id.screenNameTextView);
            body = (TextView) itemView.findViewById(R.id.postBodyTextView);
            createdAt = (TextView) itemView.findViewById(R.id.postTimeTextView);
            retweetCount = (TextView) itemView.findViewById(R.id.retweetTextView);
            isFavorited = (ImageView) itemView.findViewById(R.id.likeImageView);
        }
    }
}
