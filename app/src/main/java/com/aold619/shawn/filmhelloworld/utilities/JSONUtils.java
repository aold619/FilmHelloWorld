package com.aold619.shawn.filmhelloworld.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Format the film data
 *
 * Created by aold6 on 2018/1/12.
 */

public final class JSONUtils {
    private static final String STATUS_CODE = "status_code";
    private static final String FILM_ARRAY = "results";

    public static JSONArray getAllFilmData(String results) throws JSONException {
        JSONObject jsonObject = new JSONObject(results);
        if (jsonObject.has(STATUS_CODE)) {
            return null;
        }
        return jsonObject.getJSONArray(FILM_ARRAY);
    }

    public static Map<String, String[]> getGroupedData(JSONArray movieData) throws JSONException {
        Map<String, String[]> groupedData = new HashMap<>();
        if (movieData != null) {
            String[] posters = new String[movieData.length()];
            String[] titles = new String[movieData.length()];
            String[] overviews = new String[movieData.length()];
            String[] votes = new String[movieData.length()];
            String[] dates = new String[movieData.length()];

            for (int i = 0; i < movieData.length(); i++) {
                JSONObject film = movieData.getJSONObject(i);
                posters[i] = film.getString("poster_path").replace("/", "");
                titles[i] = film.getString("title");
                overviews[i] = film.getString("overview");
                votes[i] = film.getString("vote_average");
                dates[i] = film.getString("release_date");
            }
            groupedData.put("posters", posters);
            groupedData.put("titles", titles);
            groupedData.put("overviews", overviews);
            groupedData.put("votes", votes);
            groupedData.put("dates", dates);
        }
        return groupedData;
    }

}
