package com.dhwanishah.newsfeed.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dhwanishah.newsfeed.R;
import com.dhwanishah.newsfeed.activities.WebViewActivity;
import com.dhwanishah.newsfeed.models.NewYorkTimeArticle;

import java.util.List;

/**
 * Created by DhwaniShah on 3/14/17.
 */

public class NewYorkTimesArrayAdapter extends RecyclerView.Adapter<NewYorkTimesArrayAdapter.ViewHolder> {

    private Context mContext;
    private List<NewYorkTimeArticle> newYorkTimeArticles;

    public NewYorkTimesArrayAdapter(Context context, List<NewYorkTimeArticle> newYorkTimeArticles) {
        this.mContext = context;
        this.newYorkTimeArticles = newYorkTimeArticles;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newsView = inflater.inflate(R.layout.item_news_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewYorkTimesArrayAdapter.ViewHolder holder, int position) {
        NewYorkTimeArticle article = newYorkTimeArticles.get(position);

        ImageView thumbnail = holder.thumbnailImageView;
        thumbnail.setImageResource(0);
        String thumbnailUrl = article.getThumbnail();
        if (!TextUtils.isEmpty(thumbnailUrl)) {
            Glide.with(getContext())
                  .load(thumbnailUrl)
                  .diskCacheStrategy(DiskCacheStrategy.ALL)
                  .placeholder(R.mipmap.ic_launcher)
                  .override(600, 400)
                  .centerCrop()
                  .into(thumbnail);
        }
        TextView headline = holder.headlineTextView;
        headline.setText(article.getHeadline());
    }

    @Override
    public int getItemCount() {
        return newYorkTimeArticles.size();
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
                NewYorkTimeArticle article = newYorkTimeArticles.get(position);
                Intent startWebViewActivity = new Intent(mContext, WebViewActivity.class);
                startWebViewActivity.putExtra("webUrl", article.getWebUrl());
                mContext.startActivity(startWebViewActivity);
            }
        }
    }
}
