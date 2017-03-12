package com.dhwanishah.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle) TextView mTitle;
    @BindView(R.id.tvOverview) TextView mOverview;
    @BindView(R.id.ratingScore) TextView mRatingScore;
    @BindView(R.id.ivMovieBackdropImage) ImageView mImage;
    @BindView(R.id.ratingBar) RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle bundle = getIntent().getExtras();
        ButterKnife.bind(this);

        mTitle.setText(bundle.getString("originalTitle"));
        mOverview.setText(bundle.getString("overview"));
        mRatingScore.setText(Double.toString(bundle.getDouble("voteAverage")) + "/10");
        Picasso.with(getApplicationContext()).load(bundle.getString("backdropImage"))
                .placeholder(R.drawable.placeholder_image)
                .transform(new RoundedCornersTransformation(20, 0))
                .fit()
                .into(mImage);

        mRatingBar.setEnabled(false);
        mRatingBar.setMax(10);
        mRatingBar.setStepSize(0.01f);
        mRatingBar.setRating((float) bundle.getDouble("voteAverage"));
        mRatingBar.invalidate();
    }
}
