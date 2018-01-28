package com.aold619.shawn.filmhelloworld;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aold619.shawn.filmhelloworld.utilities.JSONUtils;
import com.aold619.shawn.filmhelloworld.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements FilmAdapter.FilmAdapterOnClickHandler {

    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private FilmAdapter filmAdapter;
    private TextView errorMessageDisplay;
//    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        errorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mRecyclerView = findViewById(R.id.recycleview_film);
        GridLayoutManager layoutManager = new GridLayoutManager(
                        this, 3,
                        GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        filmAdapter = new FilmAdapter(this);
        mRecyclerView.setAdapter(filmAdapter);

        loadFilmData(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        String[] params = new String[2];
        params[0] = NetworkUtils.FEATURE_DISCOVER;
        switch (itemId) {
            case R.id.menu_sort_by_popularity:
                filmAdapter.setFilmData(null);
                params[1] = NetworkUtils.FAVORITE;
                loadFilmData(params); break;
            case R.id.menu_sort_by_vote:
                params[1] = NetworkUtils.VOTE;
                loadFilmData(params); break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFilmData(String[] params) {
        new FetchFilmData().execute(params);
    }

    @Override
    public void onClick(JSONObject movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intent = new Intent(context, destinationClass);
        try {
            intent.putExtra("title", movie.getString("title"));
            intent.putExtra("poster_path", movie.getString("poster_path"));
            intent.putExtra("overview", movie.getString("overview"));
            intent.putExtra("vote_average", movie.getString("vote_average"));
            intent.putExtra("release_date", movie.getString("release_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intent);
    }

    public class FetchFilmData extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            if (params == null) {
                params = new String[2];
                params[0] = NetworkUtils.FEATURE_DISCOVER;
                params[1] = NetworkUtils.FAVORITE;
            }
            URL url = NetworkUtils.buildAPIUrl(params[0], params[1]);
            String result = null;
            JSONArray movieData = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                movieData = JSONUtils.getAllFilmData(result);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return movieData;
        }

        @Override
        protected void onPostExecute(JSONArray movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movieData != null) {
                showPosterDataView();
                filmAdapter.setFilmData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

    private void showPosterDataView() {
        errorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        errorMessageDisplay.setVisibility(View.VISIBLE);
    }

}
