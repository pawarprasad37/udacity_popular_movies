package com.example.popularmovies.behavior;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.R;
import com.example.popularmovies.Util;
import com.example.popularmovies.activity.MainActivity;
import com.example.popularmovies.adapter.HomeScreenMovieAdapter;
import com.example.popularmovies.interfaces.MovieFetchCallback;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.network.NetworkManager;

import java.util.List;

public class HomeScreenDataManager implements MovieFetchCallback {
    public static final int NUMBER_OF_COLUMNS = 3;
    private static boolean isFetching;

    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    private TextView tvActiveFilter;
    private ProgressBar progressBar;

    public HomeScreenDataManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.tvActiveFilter = mainActivity.findViewById(R.id.tvActiveFilter);
        this.progressBar = mainActivity.findViewById(R.id.progressBar);
        initRecyclerView();
    }

    public void pullListWithPath(String path) {
        if (!Util.isConnectedToInternet(mainActivity.getApplicationContext())) {
            Toast.makeText(mainActivity, mainActivity.getString(R.string.no_internet_error),
                    Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        NetworkManager.pullMovieList(mainActivity.getApplicationContext(), path, this);
    }

    private void initRecyclerView() {
        recyclerView = mainActivity.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mainActivity, NUMBER_OF_COLUMNS);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public static void setIsFetching(boolean isFetching) {
        HomeScreenDataManager.isFetching = isFetching;
    }

    public static boolean isFetching() {
        return isFetching;
    }

    @Override
    public void onSuccess(List<Movie> movies, String activeFilter) {
        if (movies == null || movies.isEmpty()) {
            return;
        }
        progressBar.setVisibility(View.GONE);
        tvActiveFilter.setText("");
        recyclerView.setAdapter(null);
        HomeScreenMovieAdapter adapter = new HomeScreenMovieAdapter(mainActivity, movies);
        recyclerView.setAdapter(adapter);
        tvActiveFilter.setText(activeFilter);
    }

    @Override
    public void onFailure() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(mainActivity, mainActivity.getString(R.string.no_internet_error),
                Toast.LENGTH_LONG).show();
    }
}
