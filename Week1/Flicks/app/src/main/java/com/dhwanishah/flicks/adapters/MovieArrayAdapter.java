package com.dhwanishah.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhwanishah.flicks.R;
import com.dhwanishah.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.dhwanishah.flicks.R.id.tvOverview;
import static com.dhwanishah.flicks.R.id.tvTitle;

/**
 * Created by DhwaniShah on 3/8/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.movie_element_view, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        int orientation = getContext().getResources().getConfiguration().orientation;

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.movie_element_view, parent, false);
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                viewHolder.ivPosterImage = (ImageView) convertView.findViewById(R.id.ivMoviePosterImage);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                viewHolder.ivBackdropImage = (ImageView) convertView.findViewById(R.id.ivMovieBackdropImage);
            }
            viewHolder.tvTitle = (TextView) convertView.findViewById(tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            viewHolder.ivPosterImage.setImageResource(0); // Clear out the image
            // Populate image from url with picasso library
            Picasso.with(getContext()).load(movie.getPosterPath())
                                        .placeholder(R.drawable.placeholder_image)
                                        .into(viewHolder.ivPosterImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewHolder.ivBackdropImage.setImageResource(0); // Clear out the image
            // Populate image from url with picasso library
            Picasso.with(getContext()).load(movie.getBackdropImage())
                                        .placeholder(R.drawable.placeholder_image)
                                        .into(viewHolder.ivBackdropImage);
        }

        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        return convertView;
    }
}
