package com.aold619.shawn.filmhelloworld;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.aold619.shawn.filmhelloworld.utilities.JSONUtils;
import com.aold619.shawn.filmhelloworld.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements FilmAdapter.FilmAdapterOnClickHandler {

//    private GridView imagesGridView;
    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private FilmAdapter filmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mRecyclerView = findViewById(R.id.recycleview_film);
        GridLayoutManager layoutManager = new GridLayoutManager(
                        this, 2,
                        GridLayoutManager.HORIZONTAL, false);
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
            String[] postersFileName = null;
            try {
                result = NetworkUtils.getResponseFromHttpUrl(url);
                postersFileName = JSONUtils.getPosterFilmNames(result);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            System.out.println();

            return postersFileName;
        }

        @Override
        protected void onPostExecute(String[] postersFileName) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (postersFileName != null) {
                filmAdapter.setFilmData(postersFileName);
            } else {
                // TODO: show the error message
            }
        }


    }

}
