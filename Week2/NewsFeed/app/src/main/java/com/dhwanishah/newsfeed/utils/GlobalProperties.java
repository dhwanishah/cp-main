package com.dhwanishah.newsfeed.utils;

/**
 * Created by DhwaniShah on 3/14/17.
 */

public class GlobalProperties {

    public enum NEWS_TYPE {
        NY_TIMES,
        CNN
    }

    public enum ERR_TYPE {
        NO_NETWORK,
        NO_INTERNET
    }

    public final static String NY_TIMES_API_ENDPOINT = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
    public final static String NY_TIMES_API_KEY = "e126d2d820f047579e118bbda77defef"; //227c750bb7714fc39ef1559ef1bd8329
    public final static String NY_TIMES_IMAGE_DOMAIN = "http://www.nytimes.com/";

}