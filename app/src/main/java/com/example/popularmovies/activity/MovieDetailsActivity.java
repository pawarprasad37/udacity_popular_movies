package com.example.popularmovies.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.popularmovies.Constant;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MovieDetailsActivity extends AppCompatActivity {

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

        Movie movie = new Gson().fromJson(selectedMoviewJson, new TypeToken<Movie>() {
        }.getType());
        setTitle(movie.getTitle());

        populateUI(movie);
    }

    private void populateUI(Movie movie) {
        Glide.with(this)
                .load(movie.getPosterFullPath())
                .into(((ImageView) findViewById(R.id.ivPoster)));
//        Picasso.get()
//                .load(movie.getPosterFullPath())
//                .into(((ImageView) findViewById(R.id.ivPoster)));

        ((TextView) findViewById(R.id.tvReleaseDate))
                .setText(movie.getReleaseDate());
        ((TextView) findViewById(R.id.tvVoteAverage))
                .setText(String.valueOf(movie.getVoteAverage()));
        ((TextView) findViewById(R.id.tvPlotSynopsis))
                .setText(movie.getOverview());
    }
}
