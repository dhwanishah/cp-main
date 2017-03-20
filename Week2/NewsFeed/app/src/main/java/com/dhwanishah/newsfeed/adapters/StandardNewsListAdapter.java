package com.dhwanishah.newsfeed.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhwanishah.newsfeed.R;
import com.dhwanishah.newsfeed.activities.CnnActivity;
import com.dhwanishah.newsfeed.activities.NewYorkTimesNewsFeedActivity;
import com.dhwanishah.newsfeed.models.StandardNewsItem;

import java.util.List;

/**
 * Created by DhwaniShah on 3/19/17.
 */

public class StandardNewsListAdapter extends RecyclerView.Adapter<StandardNewsListAdapter.ViewHolder> {

    private Context mContext;
    List<StandardNewsItem> mStandardNewsItemsList;

    public StandardNewsListAdapter(Context context, List<StandardNewsItem> standardNewsItemsList) {
        this.mContext = context;
        this.mStandardNewsItemsList = standardNewsItemsList;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newsView = inflater.inflate(R.layout.item_news_feed_list, parent, false);
        StandardNewsListAdapter.ViewHolder viewHolder = new StandardNewsListAdapter.ViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StandardNewsItem newsItem = mStandardNewsItemsList.get(position);
        ImageView thumbnail = holder.thumbnailImageView;
        thumbnail.setImageResource(0);
        thumbnail.setMaxHeight(1);
        int thumbnailUrl = newsItem.getThumbnail();
        if (thumbnailUrl != -1) {
            thumbnail.setImageResource(thumbnailUrl);
        }
        TextView headline = holder.headlineTextView;
        headline.setText(newsItem.getHeadline());
    }

    @Override
    public int getItemCount() {
        return mStandardNewsItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView thumbnailImageView;
        public TextView headlineTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnailImage);
            headlineTextView = (TextView) itemView.findViewById(R.id.headline);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                StandardNewsItem newsItem = mStandardNewsItemsList.get(position);
                if (position == 0) {
                    Intent startSelectedNewsFeedActivity = new Intent(mContext, NewYorkTimesNewsFeedActivity.class);
                    mContext.startActivity(startSelectedNewsFeedActivity);
                } else if (position == 1) {
                    Intent startSelectedNewsFeedActivity = new Intent(mContext, CnnActivity.class);
                    mContext.startActivity(startSelectedNewsFeedActivity);
                }
            }
        }
    }
}
