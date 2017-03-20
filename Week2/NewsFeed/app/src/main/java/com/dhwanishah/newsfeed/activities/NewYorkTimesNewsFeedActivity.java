package com.dhwanishah.newsfeed.activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.dhwanishah.newsfeed.R;
import com.dhwanishah.newsfeed.adapters.NewYorkTimesArrayAdapter;
import com.dhwanishah.newsfeed.models.NewYorkTimeArticle;
import com.dhwanishah.newsfeed.utils.EndlessRecyclerViewScrollListener;
import com.dhwanishah.newsfeed.utils.GlobalFunctions;
import com.dhwanishah.newsfeed.utils.GlobalProperties;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import cz.msebera.android.httpclient.Header;

public class NewYorkTimesNewsFeedActivity extends AppCompatActivity {

    GlobalFunctions globalFunctions = new GlobalFunctions(this);
    RecyclerView resultsList;
    ArrayList<NewYorkTimeArticle> newYorkTimeArticles;
    NewYorkTimesArrayAdapter newYorkTimesArrayAdapter;

    AlertDialog.Builder alertBuilder;
    View dialogView;

    String lastSearchedQuery = "";
    int lastPageNumber = 0;
    String sortingFilter = "Newest";
    String sectionFilter = "";

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_news_feed);
        setTitle(getResources().getStringArray(R.array.newsFeedNames)[GlobalProperties.NEWS_TYPE.NY_TIMES.ordinal()]);
        Toolbar mainToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(mainToolbar);
        if (globalFunctions.isNetworkAvailable()) {
            if (globalFunctions.isOnline()) {
                newYorkTimeArticles = new ArrayList<>();
                resultsList = (RecyclerView) findViewById(R.id.newsFeedList);
                newYorkTimesArrayAdapter = new NewYorkTimesArrayAdapter(getApplicationContext(), newYorkTimeArticles);
                resultsList.setAdapter(newYorkTimesArrayAdapter);
                StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                resultsList.setLayoutManager(gridLayoutManager);

                // Retain an instance so that you can call `resetState()` for fresh searches
                scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        // Triggered only when new data needs to be appended to the list
                        // Add whatever code is needed to append new items to the bottom of the list
                        Log.e("SCROLLHIT", "NEWSEARCH " + lastPageNumber + " " + lastSearchedQuery);
                        fetchSearchResults(lastSearchedQuery, ++lastPageNumber);
                        Log.e("SCROLLHIT", "NEWSEARCH " + lastPageNumber + " " + lastSearchedQuery);
                    }
                };
                resultsList.addOnScrollListener(scrollListener);

                fetchSearchResults(lastSearchedQuery, lastPageNumber);
            } else {
                // TODO: Change these getResource getString to a function
                Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.errorStrings)[GlobalProperties.ERR_TYPE.NO_INTERNET.ordinal()], Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getStringArray(R.array.errorStrings)[GlobalProperties.ERR_TYPE.NO_NETWORK.ordinal()], Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_news_feed, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setBackgroundColor(Color.WHITE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query)) {
                    clearSearchResultsAndReset();
                    fetchSearchResults(query, lastPageNumber);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter a search value.", Toast.LENGTH_SHORT).show();
                }
                searchView.clearFocus();
                searchItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        final MenuItem filterButton = menu.findItem(R.id.filter);
        filterButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                createFilterDialogBox();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void clearSearchResultsAndReset() {
        lastPageNumber = 0;
        newYorkTimeArticles.clear();
        newYorkTimesArrayAdapter.notifyDataSetChanged();
        scrollListener.resetState();
    }

    private void fetchSearchResults(final String searchQuery, int pageNumber) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("api-key", GlobalProperties.NY_TIMES_API_KEY);
        params.add("page", String.valueOf(pageNumber));
        params.add("sort", sortingFilter);
        if (!TextUtils.isEmpty(sectionFilter)) {
            params.add("fq", "news_desk:(" + sectionFilter + ")");
        }
        if (!TextUtils.isEmpty(lastSearchedQuery)) {
            params.add("q", searchQuery);
        }
        client.get(GlobalProperties.NY_TIMES_API_ENDPOINT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray returnedResults;
                try {
                    returnedResults = response.getJSONObject("response").getJSONArray("docs");
                    //newYorkTimeArticles.clear();
                    newYorkTimeArticles.addAll(NewYorkTimeArticle.fromJSONArrayTo(returnedResults));
                    newYorkTimesArrayAdapter.notifyDataSetChanged();
                    lastSearchedQuery = searchQuery;
                    lastPageNumber = lastPageNumber++;
                    scrollListener.resetState();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getApplicationContext(), "There was an issue getting information.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createFilterDialogBox() {
        alertBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppTheme));
        dialogView = getLayoutInflater().inflate(R.layout.dialog_filter_results_newyorktimes, null);
        final String[] sortStringArray = getResources().getStringArray(R.array.newYorkTimesSortingArray);
        final Spinner sortSpinner = (Spinner) dialogView.findViewById(R.id.sortingSpinner);
        final CheckBox artsCheckbox = (CheckBox) dialogView.findViewById(R.id.artsCheckBox);
        final CheckBox fashionAndStyleCheckBox = (CheckBox) dialogView.findViewById(R.id.fashionAndStyleCheckBox);
        final CheckBox sportsCheckbox = (CheckBox) dialogView.findViewById(R.id.sportsCheckbox);
        if (!TextUtils.isEmpty(sortingFilter)) { sortSpinner.setSelection(Arrays.asList(sortStringArray).indexOf(sortingFilter)); }
        if (!TextUtils.isEmpty(sectionFilter)) {
            if (sectionFilter.contains(artsCheckbox.getText().toString())) { artsCheckbox.setChecked(true); }
            if (sectionFilter.contains(fashionAndStyleCheckBox.getText().toString())) { fashionAndStyleCheckBox.setChecked(true); }
            if (sectionFilter.contains(sportsCheckbox.getText().toString())) { sportsCheckbox.setChecked(true); }
        }
        alertBuilder.setView(dialogView)
                .setTitle("Filter")
                .setPositiveButton("Set Filter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sortingFilter = "";
                        sectionFilter = "";
                        sortingFilter = sortStringArray[sortSpinner.getSelectedItemPosition()];
                        sectionFilter += artsCheckbox.isChecked() ? "\"" + artsCheckbox.getText().toString() + "\"" : "";
                        sectionFilter += fashionAndStyleCheckBox.isChecked() ? "\"" + fashionAndStyleCheckBox.getText().toString() + "\"" : "";
                        sectionFilter += sportsCheckbox.isChecked() ? "\"" + sportsCheckbox.getText().toString() + "\"" : "";
                        clearSearchResultsAndReset();
                        fetchSearchResults(lastSearchedQuery, lastPageNumber);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertBuilder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
