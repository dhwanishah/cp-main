package com.codepath.apps.restclienttemplate.models;

/**
 * Created by DhwaniShah on 3/21/17.
 */

import com.codepath.apps.restclienttemplate.utils.TimeCalculation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**


 "text": "just another test",
 "contributors": null,
 "id": 240558470661799936,
 "retweet_count": 0,
 "in_reply_to_status_id_str": null,
 "geo": null,
 "retweeted": false,
 "in_reply_to_user_id": null,
 "place": null,
 "source": "OAuth Dancer Reborn",
 "user": {
 "name": "OAuth Dancer",
 "profile_sidebar_fill_color": "DDEEF6",
 "profile_background_tile": true,
 "profile_sidebar_border_color": "C0DEED",
 "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 "created_at": "Wed Mar 03 19:37:35 +0000 2010",
 "location": "San Francisco, CA",
 "follow_request_sent": false,
 "id_str": "119476949",
 "is_translator": false,
 "profile_link_color": "0084B4",
 "entities": {
 "url": {
 "urls": [
 {
 "expanded_url": null,
 "url": "http://bit.ly/oauth-dancer",
 "indices": [
 0,
 26
 ],
 "display_url": null
 }
 ]
 },
 "description": null
 },
 "default_profile": false,
 "url": "http://bit.ly/oauth-dancer",
 "contributors_enabled": false,
 "favourites_count": 7,
 "utc_offset": null,
 "profile_image_url_https": "https://si0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 "id": 119476949,
 "listed_count": 1,
 "profile_use_background_image": true,
 "profile_text_color": "333333",
 "followers_count": 28,
 "lang": "en",
 "protected": false,
 "geo_enabled": true,
 "notifications": false,
 "description": "",
 "profile_background_color": "C0DEED",
 "verified": false,
 "time_zone": null,
 "profile_background_image_url_https": "https://si0.twimg.com/profile_background_images/80151733/oauth-dance.png",
 "statuses_count": 166,
 "profile_background_image_url": "http://a0.twimg.com/profile_background_images/80151733/oauth-dance.png",
 "default_profile_image": false,
 "friends_count": 14,
 "following": false,
 "show_all_inline_media": false,
 "screen_name": "oauth_dancer"
 },
 "in_reply_to_screen_name": null,
 "in_reply_to_status_id": null
 },

 */
public class Tweet {
    long id;
    String body;
    String createdAt;
    User user;

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.id = jsonObject.getLong("id");
            tweet.body = jsonObject.getString("text");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                tweets.add(Tweet.fromJson(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
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

}
