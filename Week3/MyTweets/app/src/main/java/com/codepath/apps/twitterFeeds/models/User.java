package com.codepath.apps.twitterFeeds.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DhwaniShah on 3/21/17.
 */

public class User {
    String name;
    long uid;
    String screenName;
    String profileImageUrl;

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return "@" + screenName;
    }

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
