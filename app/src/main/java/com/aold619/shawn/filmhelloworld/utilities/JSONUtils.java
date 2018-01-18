package com.aold619.shawn.filmhelloworld.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Format the film data
 *
 * Created by aold6 on 2018/1/12.
 */

public final class JSONUtils {
    private static final String STATUS_CODE = "status_code";
    private static final String FILM_ARRAY = "results";

    public static String[] getPosterFilmNames(String results) throws JSONException {

        JSONObject jsonObject = new JSONObject(results);
        if (jsonObject.has(STATUS_CODE)) {
            return null;
        }

        JSONArray jsonArray = jsonObject.getJSONArray(FILM_ARRAY);
        String[] posterNames = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject film = jsonArray.getJSONObject(i);
            posterNames[i] = film.getString("poster_path");
        }

        return posterNames;
    }

    public static void getFilmData() {

    }
}
