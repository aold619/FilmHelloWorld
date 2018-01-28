package com.aold619.shawn.filmhelloworld;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aold619.shawn.filmhelloworld.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPoster;
    private TextView mTitle;
    private TextView mVote;
    private TextView mOverwrite;
    private TextView mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPoster = findViewById(R.id.iv_detail_poster);
        mTitle = findViewById(R.id.tv_detail_title);
        mVote = findViewById(R.id.tv_detail_vote);
        mOverwrite = findViewById(R.id.tv_detail_overview);
        mDate = findViewById(R.id.tv_detail_date);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("title")) {
                mTitle.setText(intent.getStringExtra("title"));
            }
            if (intent.hasExtra("poster_path")) {
                Uri uri = NetworkUtils
                        .buildPosterUri(NetworkUtils.BIG_SIZE,
                                intent.getStringExtra("poster_path").replace("/", ""));
                Picasso.with(this).load(uri).into(mPoster);
            }
            if (intent.hasExtra("overview")) {
                mOverwrite.setText(intent.getStringExtra("overview"));
            }
            if (intent.hasExtra("vote_average")) {
                mVote.setText(intent.getStringExtra("vote_average"));
            }
            if (intent.hasExtra("release_date")) {
                mDate.setText(intent.getStringExtra("release_date"));
            }
        }
    }

}
