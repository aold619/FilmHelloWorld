package com.aold619.shawn.filmhelloworld;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aold619.shawn.filmhelloworld.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aold6 on 2018/1/14.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmAdapterViewHolder> {

    private String[] posterNames;
    private List<Uri> mUriList;
    private Context mContext;
    private final FilmAdapterOnClickHandler mClickHandler;

    public FilmAdapter(FilmAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        mUriList = new ArrayList<>();
    }

    public interface FilmAdapterOnClickHandler {
        void onClick();
    }

    @Override
    public FilmAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForPosterItem = R.layout.poster_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForPosterItem, parent, shouldAttachToParentImmediately);
        return new FilmAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmAdapterViewHolder holder, int position) {
        Picasso.with(mContext).load(mUriList.get(position)).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return mUriList == null ? 0 : mUriList.size();
    }

    public class FilmAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public ImageView posterImageView;

        public FilmAdapterViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick();
        }

    }

    void setFilmData(String[] posterNames) {
        mUriList = new ArrayList<>();
        for (String posterName : posterNames) {
            mUriList.add(NetworkUtils.buildPosterUri(NetworkUtils.SMALL_SIZE, posterName));
        }
        notifyDataSetChanged();
    }
}
