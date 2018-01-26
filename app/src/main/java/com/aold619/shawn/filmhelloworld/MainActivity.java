package com.aold619.shawn.filmhelloworld;

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
import android.widget.TextView;

import com.aold619.shawn.filmhelloworld.utilities.JSONUtils;
import com.aold619.shawn.filmhelloworld.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements FilmAdapter.FilmAdapterOnClickHandler {

//    private GridView imagesGridView;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private FilmAdapter filmAdapter;
    private TextView errorMessageDisplay;

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

        loadFilmData();
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
        if (itemId == R.id.menu_refresh) {
            loadFilmData();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFilmData() {
        showPosterDataView();
        new FetchFilmData().execute();
    }

    @Override
    public void onClick() {

    }

    public class FetchFilmData extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {
            URL url = NetworkUtils.buildAPIUrl(
                    NetworkUtils.FEATURE_DISCOVER, NetworkUtils.FAVORITE);
            String result = null;
            String[] postersFileNames = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                postersFileNames = JSONUtils.getPosterFilmNames(result);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            System.out.println();

            return postersFileNames;
        }

        @Override
        protected void onPostExecute(String[] postersFileNames) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (postersFileNames != null) {
                filmAdapter.setFilmData(postersFileNames);
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
