package com.codepath.apps.twitterFeeds.models;

/**
 * Created by DhwaniShah on 3/21/17.
 */

import android.util.Log;

import com.codepath.apps.twitterFeeds.activities.HomeTimelineActivity;
import com.codepath.apps.twitterFeeds.utils.TimeCalculation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Tweet {
    long id;
    String body;
    String createdAt;
    int replyCount;
    int retweetCount;
    boolean isFavorited;
    User user;

    //long sinceId;
    //long maxId;

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.id = jsonObject.getLong("id");
            tweet.body = jsonObject.getString("text");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.isFavorited = jsonObject.getBoolean("favorited");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        //long previousSinceId = 0;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                tweets.add(Tweet.fromJson(jsonArray.getJSONObject(i)));
                //if (HomeTimelineActivity.mLastSinceId == 1) {
                    if (i == 0 && HomeTimelineActivity.mLastMaxId == 1) {
                        Log.e("M", Tweet.fromJson(jsonArray.getJSONObject(i)).getId() + "");
                        HomeTimelineActivity.mLastMaxId = Tweet.fromJson(jsonArray.getJSONObject(i)).getId();
                    }
//                    else if (i == 0 && HomeTimelineActivity.mLastMaxId != 1) {
//                        Log.e("M2", Tweet.fromJson(jsonArray.getJSONObject(i)).getId() + "");
//                        HomeTimelineActivity.mLastMaxId = HomeTimelineActivity.mLastSinceId;
//                    }
                //}
//                else {
//                    HomeTimelineActivity.mLastMaxId = HomeTimelineActivity.mLastSinceId;
//                }
                if (i == jsonArray.length()-1) {
                    Log.e("S", Tweet.fromJson(jsonArray.getJSONObject(i)).getId() + "");
                    HomeTimelineActivity.mLastSinceId = Tweet.fromJson(jsonArray.getJSONObject(i)).getId();
                    //previousSinceId = Tweet.fromJson(jsonArray.getJSONObject(i)).getId();
                    Log.e("sinceAndMax", HomeTimelineActivity.mLastMaxId + " " + HomeTimelineActivity.mLastSinceId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        Log.e("Total", HomeTimelineActivity.mLastMaxId + " " + HomeTimelineActivity.mLastSinceId);
        return tweets;
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        DateFormat dateTimeFormatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        Date currentDateTime = new Date();
        TimeCalculation timeCalculation = new TimeCalculation(
                                                TimeCalculation.getTwitterDate(dateTimeFormatter.format(currentDateTime).toString()),
                                                TimeCalculation.getTwitterDate(createdAt));
        return timeCalculation.getDifferenceString();
    }

    public User getUser() {
        return user;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public boolean isFavorited() {
        return isFavorited;
    }

//    public long getSinceId() {
//        return sinceId;
//    }
//
//    public long getMaxId() {
//        return maxId;
//    }

}
