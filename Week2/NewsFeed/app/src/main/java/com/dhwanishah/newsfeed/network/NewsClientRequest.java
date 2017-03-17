package com.dhwanishah.newsfeed.network;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

/**
 * Created by DhwaniShah on 3/15/17.
 */

public class NewsClientRequest {

    Context mContext;
    String mNewsService;
    ArrayList<?> mNewsList;
    RequestParams mParams;

    public NewsClientRequest(Context context, String newsService, ArrayList<?> newsList, RequestParams params) {
        this.mContext = context;
        this.mNewsService = newsService;
        this.mNewsList = newsList;
        this.mParams = params;
    }

//    private void sendGetRequest() {
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("api-key", GlobalProperties.NY_TIMES_API_KEY);
//        params.add("page", String.valueOf(0));
//        params.add("q", "android");
//        client.get(GlobalProperties.NY_TIMES_API_ENDPOINT, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                JSONArray returnedResults;
//                try {
//                    returnedResults = response.getJSONObject("response").getJSONArray("docs");
//                    newYorkTimeArticles.addAll(NewYorkTimeArticle.fromJSONArrayTo(returnedResults));
//                    newYorkTimesArrayAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

}
