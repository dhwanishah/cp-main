package com.dhwanishah.newsfeed.models;

/**
 * Created by DhwaniShah on 3/19/17.
 */

public class StandardNewsItem {
    private String headline;
    private int thumbnail;

    public StandardNewsItem(String headline, int thumbnail) {
        this.headline = headline;
        this.thumbnail = thumbnail;
    }

    public String getHeadline() {
        return headline;
    }

    public int getThumbnail() {
        return thumbnail;
    }
}
