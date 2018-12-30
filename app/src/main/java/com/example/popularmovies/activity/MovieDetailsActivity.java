package com.example.popularmovies.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.popularmovies.Constant;
import com.example.popularmovies.R;
import com.example.popularmovies.interfaces.TrailerFetchCallback;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.MovieTrailer;
import com.example.popularmovies.network.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String selectedMoviewJson = getIntent()
                .getStringExtra(Constant.IntentExtra.SELECTED_MOVIE_JSON);
        if (selectedMoviewJson == null) {
            Toast.makeText(this, getString(R.string.movie_details_unavailable_error),
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        movie = new Gson().fromJson(selectedMoviewJson, new TypeToken<Movie>() {
        }.getType());
        setTitle(movie.getTitle());

        populateUI(movie);

        fetchMovieData();
    }

    private void fetchMovieData() {
        NetworkManager.pullMovieTrailers(movie.getId(), new TrailerFetchCallback() {
            @Override
            public void onFailure() {
                //ignore
            }

            @Override
            public void onSuccess(List<MovieTrailer> trailerList) {
                if (trailerList == null || trailerList.isEmpty() || isFinishing()) {
                    return;
                }
                findViewById(R.id.llTrailers).setVisibility(View.VISIBLE);
                displayTrailerItems(trailerList);
            }

            private void displayTrailerItems(List<MovieTrailer> trailerList) {
                if (trailerList == null || trailerList.isEmpty()) {
                    return;
                }
                LinearLayout llTrailerItems = findViewById(R.id.llTrailerItems);
                llTrailerItems.removeAllViews();
                for (final MovieTrailer movieTrailer : trailerList) {
                    View itemRootView = LayoutInflater.from(getApplicationContext())
                            .inflate(R.layout.movie_details_trailer_list_item, null);
                    TextView textView = itemRootView.findViewById(R.id.textView);
                    textView.setText(movieTrailer.getName());
                    itemRootView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String uri = "http://www.youtube.com/watch?v=" + movieTrailer.getKey();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
                        }
                    });
                    llTrailerItems.addView(itemRootView,
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }
        });
    }

    private void populateUI(Movie movie) {
        Glide.with(this)
                .load(movie.getPosterFullPath())
                .into(((ImageView) findViewById(R.id.ivPoster)));

        ((TextView) findViewById(R.id.tvReleaseDate))
                .setText(movie.getReleaseDate());
        ((TextView) findViewById(R.id.tvVoteAverage))
                .setText(String.valueOf(movie.getVoteAverage()));
        ((TextView) findViewById(R.id.tvPlotSynopsis))
                .setText(movie.getOverview());
    }
}
