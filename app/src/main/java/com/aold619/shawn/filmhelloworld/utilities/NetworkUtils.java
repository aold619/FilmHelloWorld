package com.aold619.shawn.filmhelloworld.utilities;

import java.net.URL;

/**
 * Created by aold6 on 2018/1/11.
 */

public final class NetworkUtils {
    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p";
    private static final String THUMBNAILS_SIZE = "/w185";
    private static final String NORMAL_SIZE = "/w342";
    private static final String SORT_BY = "sort_by";
    private static final String FAVORITE = "popularity.desc";
    private static final String VOTE = "vote_average.desc";

    /**
     * write your API Key here
     */
    private static final String API_KEY = "148de4c038b0812ca597b8d2b322195d";

    private static final String format = "json";

    public static URL buildUrl(String type) {

        return null;
    }
}
