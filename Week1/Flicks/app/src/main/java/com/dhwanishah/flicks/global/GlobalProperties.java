package com.dhwanishah.flicks.global;

/**
 * Created by DhwaniShah on 3/9/17.
 */

public class GlobalProperties {

    // Movie DB
    private static final String MOVIE_DB_URL = "https://api.themoviedb.org/3/";
    private static final String MOVIE_DB_API_KEY = "?" + "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    // Endpoints
    private enum endpointType {
        MOVIE_DB_NOWPLAYING_ENDPOINT("movie/now_playing");

        private String stringValue;
        endpointType(String toString) {
            stringValue = toString;
        }
        @Override
        public String toString() {
            return stringValue;
        }
    }



    public static String generateMovieUrl(String type) {
        switch (type) {
            case "movies_nowPlaying":
                return MOVIE_DB_URL + endpointType.MOVIE_DB_NOWPLAYING_ENDPOINT + MOVIE_DB_API_KEY;
            default:
                return "";
        }
    }
}
