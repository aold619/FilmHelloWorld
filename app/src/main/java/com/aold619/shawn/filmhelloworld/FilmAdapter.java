package com.aold619.shawn.filmhelloworld;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aold619.shawn.filmhelloworld.utilities.JSONUtils;
import com.aold619.shawn.filmhelloworld.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aold6 on 2018/1/14.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmAdapterViewHolder> {

    private String[] posters;
    private String[] titles;
    private JSONArray allMovieData;
    private List<Uri> mUriList;
    private Context mContext;
    private final FilmAdapterOnClickHandler mClickHandler;

    public FilmAdapter(FilmAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        allMovieData = new JSONArray();
    }

    public interface FilmAdapterOnClickHandler {
        void onClick(JSONObject movie);
    }

    @Override
    public FilmAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForPosterItem = R.layout.poster_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForPosterItem, null, false);
        return new FilmAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmAdapterViewHolder holder, int position) {
        List<Uri> mUriList = new ArrayList<>();
        for (String posterName : posters) {
            mUriList.add(NetworkUtils.buildPosterUri(NetworkUtils.POSTER_SIZE, posterName));
        }
        Picasso.with(mContext).load(mUriList.get(position))
                .into(holder.posterImageView);
        holder.titleTextView.setText(titles[position]);
    }

    @Override
    public int getItemCount() {
        return allMovieData == null ? 0 : allMovieData.length();
    }

    public class FilmAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public ImageView posterImageView;
        public TextView titleTextView;

        public FilmAdapterViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster);
            titleTextView = itemView.findViewById(R.id.tv_movie_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            JSONObject movie = null;
            try {
                movie = (JSONObject) allMovieData.get(adapterPosition);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mClickHandler.onClick(movie);
        }

    }

    void setFilmData(JSONArray movieData) {
        allMovieData = movieData;
        Map<String, String[]> groupedData = null;
        try {
            groupedData = JSONUtils.getGroupedData(allMovieData);
            posters = groupedData.get("posters");
            titles = groupedData.get("titles");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }
}
