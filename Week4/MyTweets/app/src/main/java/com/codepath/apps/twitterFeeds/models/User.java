package com.codepath.apps.twitterFeeds.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DhwaniShah on 3/21/17.
 */

public class User {
    String name;
    String description;
    long uid;
    String screenName;
    String profileImageUrl;
    int favoriteCount;
    int friendsCount;
    int followersCount;


    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return "@" + screenName;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public static User fromJson(JSONObject jsonObject) {
        User user = new User();
        try {
            user.name = jsonObject.getString("name");
            user.description = jsonObject.getString("description");
            user.uid = jsonObject.getLong("id");
            user.screenName = jsonObject.getString("screen_name");
            user.profileImageUrl = jsonObject.getString("profile_image_url");
            user.favoriteCount = jsonObject.getInt("favourites_count");
            user.friendsCount = jsonObject.getInt("friends_count");
            user.followersCount = jsonObject.getInt("followers_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

}
