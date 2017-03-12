package com.dhwanishah.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
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

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by DhwaniShah on 3/8/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    int orientation = getContext().getResources().getConfiguration().orientation;
    private static final int IS_POPULAR = 0;
    private static final int NOT_POPULAR = 1;
    private enum popularOrNot {
        IS_POPULAR,
        NOT_POPULAR
    }

    private static class ViewHolderLessPopularMovie {
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    private static class ViewHolderPopularMovie {
        ImageView ivBackdropImage;
    }

    static class PopularMoviesViewHolder {
        ImageView ivBackdropImagePopular;
    }
    static class LessPopularMoviesViewHolder {
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.movie_element_less_popular_view, movies);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        Movie movie = getItem(position);
//        int typeOfView = IS_POPULAR;
//        // Check if an existing view is being reused, otherwise inflate the view
//
//        ViewHolderLessPopularMovie viewHolderLessPopularMovie = null;
//        ViewHolderPopularMovie viewHolderPopularMovie = null;
//        if (convertView == null) {
//            //LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//            //convertView = layoutInflater.inflate(R.layout.movie_element_less_popular_view, parent, false);
//            typeOfView = getItemViewType(position);
//            convertView = getInflatedLayoutFromPopularity(typeOfView, parent);
//            if (typeOfView == IS_POPULAR) {
//                viewHolderPopularMovie = new ViewHolderPopularMovie();
//                viewHolderPopularMovie.ivBackdropImage = (ImageView) convertView.findViewById(R.id.ivMovieBackdropImagePopular);
//                convertView.setTag(viewHolderPopularMovie);
//            } else if (typeOfView == NOT_POPULAR) {
//                viewHolderLessPopularMovie = new ViewHolderLessPopularMovie();
//                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                    viewHolderLessPopularMovie.ivPosterImage = (ImageView) convertView.findViewById(R.id.ivMoviePosterImage);
//                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    viewHolderLessPopularMovie.ivBackdropImage = (ImageView) convertView.findViewById(R.id.ivMovieBackdropImage);
//                }
//                viewHolderLessPopularMovie.tvTitle = (TextView) convertView.findViewById(tvTitle);
//                viewHolderLessPopularMovie.tvOverview = (TextView) convertView.findViewById(tvOverview);
//                convertView.setTag(viewHolderLessPopularMovie);
//            }
//        } else {
//            // View is being recycled, retrieve the viewHolderLessPopularMovie object from tag
//            if (typeOfView == IS_POPULAR) {
//                viewHolderPopularMovie = (ViewHolderPopularMovie) convertView.getTag();
//            } else {
//                viewHolderLessPopularMovie = (ViewHolderLessPopularMovie) convertView.getTag();
//            }
//        }
//
//        if (typeOfView == IS_POPULAR) {
//            viewHolderPopularMovie.ivBackdropImage.setImageResource(0); // Clear out the image
//            // Populate image from url with picasso library
//            Picasso.with(getContext()).load(movie.getBackdropImage())
//                    .placeholder(R.drawable.placeholder_image)
//                    .into(viewHolderPopularMovie.ivBackdropImage);
//        } else if (typeOfView == NOT_POPULAR) {
//            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                viewHolderLessPopularMovie.ivPosterImage.setImageResource(0); // Clear out the image
//                // Populate image from url with picasso library
//                Picasso.with(getContext()).load(movie.getPosterPath())
//                        .placeholder(R.drawable.placeholder_image)
//                        .into(viewHolderLessPopularMovie.ivPosterImage);
//            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                viewHolderLessPopularMovie.ivBackdropImage.setImageResource(0); // Clear out the image
//                // Populate image from url with picasso library
//                Picasso.with(getContext()).load(movie.getBackdropImage())
//                        .placeholder(R.drawable.placeholder_image)
//                        .into(viewHolderLessPopularMovie.ivBackdropImage);
//            }
//            viewHolderLessPopularMovie.tvTitle.setText(movie.getOriginalTitle() + " " + movie.getVoteAverage());
//            viewHolderLessPopularMovie.tvOverview.setText(movie.getOverview());
//        }

        //second attempt
//        Movie movie = getItem(position);
//        int viewType = this.getItemViewType(position);
//        if(viewType == popularOrNot.IS_POPULAR.ordinal()) {
//            ViewHolderPopularMovie viewHolderPopularMovie;
//            if (convertView == null) {
//                convertView = getInflatedLayoutFromPopularity(viewType, parent);
//                viewHolderPopularMovie = new ViewHolderPopularMovie();
//                viewHolderPopularMovie.ivBackdropImage = (ImageView) convertView.findViewById(R.id.ivMovieBackdropImagePopular);
//                convertView.setTag(viewHolderPopularMovie);
//                viewHolderPopularMovie.ivBackdropImage.setImageResource(0);
//                Picasso.with(getContext()).load(movie.getBackdropImage())
//                    .placeholder(R.drawable.placeholder_image)
//                    .into(viewHolderPopularMovie.ivBackdropImage);
//            } else {
//                viewHolderPopularMovie = (ViewHolderPopularMovie) convertView.getTag();
//            }
//        } else {
//            ViewHolderLessPopularMovie viewHolderLessPopularMovie;
//            if (convertView == null) {
//                viewHolderLessPopularMovie = new ViewHolderLessPopularMovie();
//                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                    viewHolderLessPopularMovie.ivPosterImage = (ImageView) convertView.findViewById(R.id.ivMoviePosterImage);
//                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    viewHolderLessPopularMovie.ivBackdropImage = (ImageView) convertView.findViewById(R.id.ivMovieBackdropImage);
//                }
//                viewHolderLessPopularMovie.tvTitle = (TextView) convertView.findViewById(tvTitle);
//                viewHolderLessPopularMovie.tvOverview = (TextView) convertView.findViewById(tvOverview);
//                convertView.setTag(viewHolderLessPopularMovie);
//                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                    viewHolderLessPopularMovie.ivPosterImage.setImageResource(0); // Clear out the image
//                    // Populate image from url with picasso library
//                    Picasso.with(getContext()).load(movie.getPosterPath())
//                            .placeholder(R.drawable.placeholder_image)
//                            .into(viewHolderLessPopularMovie.ivPosterImage);
//                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    viewHolderLessPopularMovie.ivBackdropImage.setImageResource(0); // Clear out the image
//                    // Populate image from url with picasso library
//                    Picasso.with(getContext()).load(movie.getBackdropImage())
//                            .placeholder(R.drawable.placeholder_image)
//                            .into(viewHolderLessPopularMovie.ivBackdropImage);
//                }
//                viewHolderLessPopularMovie.tvTitle.setText(movie.getOriginalTitle() + " " + movie.getVoteAverage());
//                viewHolderLessPopularMovie.tvOverview.setText(movie.getOverview());
//            } else {
//                viewHolderLessPopularMovie = (ViewHolderLessPopularMovie) convertView.getTag();
//            }
//        }

        int viewType = this.getItemViewType(position);
        switch (viewType) {
            case IS_POPULAR:
                PopularMoviesViewHolder vhp;
                View v = convertView;
                if (v == null) {
                    v = getInflatedLayoutFromPopularity(IS_POPULAR, parent);
                    vhp = new PopularMoviesViewHolder();
                    vhp.ivBackdropImagePopular = (ImageView) v.findViewById(R.id.ivMovieBackdropImagePopular);
                    v.setTag(vhp);
                } else {
                    vhp = (PopularMoviesViewHolder) v.getTag();
                }
                Movie movie = getItem(position);
                if (movie != null) {
                    if (vhp.ivBackdropImagePopular != null) {
                        vhp.ivBackdropImagePopular.setImageResource(0);
                        Picasso.with(getContext()).load(movie.getBackdropImage())
                                .placeholder(R.drawable.placeholder_image)
                                .transform(new RoundedCornersTransformation(20, 40))
                                .fit()
                                .into(vhp.ivBackdropImagePopular);
                    }
                }
                return v;
            case NOT_POPULAR:
                LessPopularMoviesViewHolder vhlp;
                View v2 = convertView;
                if (v2 == null) {
                    v2 = getInflatedLayoutFromPopularity(NOT_POPULAR, parent);
                    vhlp = new LessPopularMoviesViewHolder();
                    vhlp.tvTitle = (TextView) v2.findViewById(R.id.tvTitle);
                    vhlp.tvOverview = (TextView) v2.findViewById(R.id.tvOverview);
                    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                        vhlp.ivPosterImage = (ImageView) v2.findViewById(R.id.ivMovieBackdropImage);
                    } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        vhlp.ivBackdropImage = (ImageView) v2.findViewById(R.id.ivMovieBackdropImage);
                    }
                    v2.setTag(vhlp);
                } else {
                    vhlp = (LessPopularMoviesViewHolder) v2.getTag();
                }
                Movie movie1 = getItem(position);
                if (movie1 != null) {
                    if (vhlp.tvTitle != null && vhlp.tvOverview != null) {
                        vhlp.tvTitle.setText(movie1.getOriginalTitle());
                        vhlp.tvOverview.setText(movie1.getOverview());
                        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                            vhlp.ivPosterImage.setImageResource(0); // Clear out the image
                            // Populate image from url with picasso library
                            Picasso.with(getContext()).load(movie1.getPosterPath())
                                    .placeholder(R.drawable.placeholder_image)
                                    .transform(new RoundedCornersTransformation(20, 40))
                                    .into(vhlp.ivPosterImage);
                        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            vhlp.ivBackdropImage.setImageResource(0); // Clear out the image
                            // Populate image from url with picasso library
                            Picasso.with(getContext()).load(movie1.getBackdropImage())
                                    .placeholder(R.drawable.placeholder_image)
                                    .transform(new RoundedCornersTransformation(20, 40))
                                    .into(vhlp.ivBackdropImage);
                        }
                    }
                }
                return v2;
            default:
                Log.e("Err", "err");
                return null;
        }


        //return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return isItPopular(getItem(position).getVoteAverage());
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private View getInflatedLayoutFromPopularity(int type, ViewGroup parent) {
        if (type == IS_POPULAR) {
            return LayoutInflater.from(getContext()).inflate(R.layout.movie_element_popular_view, parent, false);
        } else if (type == NOT_POPULAR) {
            return LayoutInflater.from(getContext()).inflate(R.layout.movie_element_less_popular_view, parent, false);
        }
        return null;
    }

    private int isItPopular(double popularity) {
        if (popularity >= 5.0) {
            return IS_POPULAR;
        } else {
            return NOT_POPULAR;
        }
    }
}
