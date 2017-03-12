package com.dhwanishah.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dhwanishah.flicks.adapters.MovieArrayAdapter;
import com.dhwanishah.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    ArrayList<Movie> mMovies = null;
    MovieArrayAdapter mMovieArrayAdapter;
    @BindView(R.id.lvMoviesList) ListView mMovieListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //mMovieListview = (ListView) findViewById(R.id.lvMoviesList);
        ButterKnife.bind(this);
        mMovies = new ArrayList<>();
        mMovieArrayAdapter = new MovieArrayAdapter(getApplicationContext(), mMovies);
        mMovieListview.setAdapter(mMovieArrayAdapter);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray returnedMoviesResult;
                try {
                    returnedMoviesResult = response.getJSONArray("results");
                    mMovies.addAll(Movie.fromJsonArray(returnedMoviesResult));
                    mMovieArrayAdapter.notifyDataSetChanged();
                    Log.d("Debug", mMovies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        mMovieListview.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) mMovieListview.getItemAtPosition(position);
                Intent startMovieDetailActivity = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("originalTitle", movie.getOriginalTitle());
                bundle.putString("overview", movie.getOverview());
                bundle.putString("backdropImage", movie.getBackdropImage());
                bundle.putDouble("voteAverage", movie.getVoteAverage());
//                startMovieDetailActivity.putExtra("originalTitle", movie.getOriginalTitle());
//                startMovieDetailActivity.putExtra("overview", movie.getOverview());
//                startMovieDetailActivity.putExtra("voteAverage", Double.toString(movie.getVoteAverage()));
                startMovieDetailActivity.putExtras(bundle);
                startActivity(startMovieDetailActivity);

                //Toast.makeText(getApplicationContext(), position + "" + movie.getOriginalTitle(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
