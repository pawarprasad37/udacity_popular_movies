package com.example.popularmovies.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.popularmovies.Constant;
import com.example.popularmovies.behavior.HomeScreenDataManager;
import com.example.popularmovies.R;
import com.example.popularmovies.viewmodel.FavouriteMovieViewModel;

public class MainActivity extends AppCompatActivity {
    private HomeScreenDataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = new HomeScreenDataManager(this);
        dataManager.pullListWithPath(Constant.POPULAR_URL_PATH);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home_menu_filter) {
            onFilterClicked(findViewById(R.id.home_menu_filter));
        }
        return true;
    }

    private void onFilterClicked(View item) {
        if (HomeScreenDataManager.isFetching()) {
            return;
        }
        PopupMenu popupMenu = new PopupMenu(this, item);
        popupMenu.getMenu().add(getString(R.string.most_popular));
        popupMenu.getMenu().add(getString(R.string.highest_rated));
        popupMenu.getMenu().add(getString(R.string.favourites));
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().toString().contentEquals(getString(R.string.most_popular))) {
                    dataManager.pullListWithPath(Constant.POPULAR_URL_PATH);
                } else if (menuItem.getTitle().toString()
                        .contentEquals(getString(R.string.highest_rated))) {
                    dataManager.pullListWithPath(Constant.TOP_RATED_URL_PATH);
                } else {
                    //favourites selected
                    dataManager.displayFavourites();
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
