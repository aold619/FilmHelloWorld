package com.aold619.shawn.filmhelloworld;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends AppCompatActivity {

    private Button posterButton;
    private Button trailerButton;
    private GridView imagesGridView;
    private VideoView trailerView;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagesGridView = findViewById(R.id.gv_images);
        trailerView = findViewById(R.id.vv_trailer);
        posterButton = findViewById(R.id.bt_poster);
        trailerButton = findViewById(R.id.bt_trailer);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        posterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trailerView.setVisibility(View.GONE);
                imagesGridView.setVisibility(View.VISIBLE);
            }
        });
        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesGridView.setVisibility(View.GONE);
                trailerView.setVisibility(View.VISIBLE);
            }
        });

        loadFilmData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void loadFilmData() {
        new FetchFilmData().execute();
    }

    public class FetchFilmData extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String... params) {
            Uri uri = Uri.parse("https://api.themoviedb.org/3/discover/movie?api_key=148de4c038b0812ca597b8d2b322195d")
                    .buildUpon().build();


            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] filmData) {
            mLoadingIndicator.setVisibility(View.GONE);
            if (filmData != null) {
                // TODO: show the film's poster
            } else {
                // TODO: show the error message
            }
        }
    }

}
