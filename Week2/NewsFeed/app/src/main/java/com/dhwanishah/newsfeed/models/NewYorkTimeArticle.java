package com.dhwanishah.newsfeed.models;

import com.dhwanishah.newsfeed.utils.GlobalProperties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by DhwaniShah on 3/14/17.
 */

public class NewYorkTimeArticle {
    private String headline;
    private String webUrl;
    private String thumbnail;

    public NewYorkTimeArticle(JSONObject jsonObject) {
        try {
            this.headline = jsonObject.getJSONObject("headline").getString("main");
            this.webUrl = jsonObject.getString("web_url");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbnail = GlobalProperties.NY_TIMES_IMAGE_DOMAIN + multimediaJson.getString("url");
            } else {
                this.thumbnail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<NewYorkTimeArticle> fromJSONArrayTo(JSONArray array) {
        ArrayList<NewYorkTimeArticle> results = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new NewYorkTimeArticle(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getHeadline() {
        return headline;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
