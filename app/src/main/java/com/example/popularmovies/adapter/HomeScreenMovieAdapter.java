package com.example.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.popularmovies.Constant;
import com.example.popularmovies.activity.MovieDetailsActivity;
import com.example.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeScreenMovieAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Movie> movies;

    public HomeScreenMovieAdapter(Context mContext, List<Movie> movies) {
        this.mContext = mContext;
        this.movies = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        ViewHolder viewHolder = new ViewHolder(imageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Movie movie = movies.get(i);
        Picasso.get()
                .load(movie.getPosterFullPath())
                .into(((ViewHolder) viewHolder).imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGridItemClicked(ViewHolder.this.getAdapterPosition());
                }
            });
        }
    }

    private void onGridItemClicked(int adapterPosition) {
        Movie selectedMovie = movies.get(adapterPosition);

        Intent intent = new Intent(mContext, MovieDetailsActivity.class);
        intent.putExtra(Constant.IntentExtra.SELECTED_MOVIE_JSON, new Gson().toJson(selectedMovie));
        mContext.startActivity(intent);
    }
}
