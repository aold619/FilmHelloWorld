package com.aold619.shawn.filmhelloworld.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Fetch film data from tmdb.org
 *
 * Created by aold6 on 2018/1/11.
 */

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_BASE_URL = "https://api.themoviedb.org/3";
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p";
    public static final String POSTER_SIZE = "w185_and_h278_bestv2";
    private static final String SORT_BY = "sort_by";
    public static final String FEATURE_SEARCH = "search/movie";
    public static final String FEATURE_DISCOVER = "discover/movie";
    public static final String FEATURE_FIND = "find/movie";
    public static final String FAVORITE = "popularity.desc";
    public static final String VOTE = "vote_average.desc";

    /**
     * write your API Key here
     */
    private static final String API_KEY = "148de4c038b0812ca597b8d2b322195d";
    private static final String PARAM_API_KEY = "api_key";

    private static final String format = "json";

    /**
     * Build a Url to fetch Film Data
     *
     * @param sortKey
     * value: FAVORITE | VOTE
     *
     * @return
     * an URL
     */
    public static URL buildAPIUrl(String feature, String sortKey) {
        Uri uri = Uri.parse(API_BASE_URL).buildUpon()
                .appendEncodedPath(feature)
                .appendQueryParameter(SORT_BY, sortKey)
                .appendQueryParameter(PARAM_API_KEY, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * Build a Poster Image Url
     *
     * @param imageSize
     * @param imageCode
     * @return
     */
    public static Uri buildPosterUri(String imageSize, String imageCode) {
        Uri uri = Uri.parse(POSTER_BASE_URL).buildUpon()
                .appendPath(imageSize)
                .appendPath(imageCode)
                .build();
//        URL url = null;
//        try {
//            url = new URL(uri.toString());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        return uri;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
