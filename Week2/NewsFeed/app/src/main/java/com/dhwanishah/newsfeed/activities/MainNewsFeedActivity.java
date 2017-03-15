package com.dhwanishah.newsfeed.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.dhwanishah.newsfeed.R;
import com.dhwanishah.newsfeed.adapters.NewYorkTimesArrayAdapter;
import com.dhwanishah.newsfeed.models.NewYorkTimeArticle;
import com.dhwanishah.newsfeed.utils.GlobalProperties;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainNewsFeedActivity extends AppCompatActivity {

    RecyclerView resultsList;
    ArrayList<NewYorkTimeArticle> newYorkTimeArticles;
    NewYorkTimesArrayAdapter newYorkTimesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_feed);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);

        newYorkTimeArticles = new ArrayList<>();
        resultsList = (RecyclerView) findViewById(R.id.newsFeedList);
        newYorkTimesArrayAdapter = new NewYorkTimesArrayAdapter(getApplicationContext(), newYorkTimeArticles);
        resultsList.setAdapter(newYorkTimesArrayAdapter);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        resultsList.setLayoutManager(gridLayoutManager);


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("api-key", GlobalProperties.NY_TIMES_API_KEY);
        params.add("page", String.valueOf(0));
        params.add("q", "android");
        client.get(GlobalProperties.NY_TIMES_API_ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray returnedResults;
                try {
                    returnedResults = response.getJSONObject("response").getJSONArray("docs");
                    newYorkTimeArticles.addAll(NewYorkTimeArticle.fromJSONArrayTo(returnedResults));
                    newYorkTimesArrayAdapter.notifyDataSetChanged();
                    Log.e("DEBUG", newYorkTimeArticles.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_news_feed, menu);
        return true;
    }
}
