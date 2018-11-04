package com.example.popularmovies.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.popularmovies.Constant;
import com.example.popularmovies.R;
import com.example.popularmovies.adapter.HomeScreenMovieAdapter;
import com.example.popularmovies.behavior.HomeScreenDataManager;
import com.example.popularmovies.interfaces.NetworkCallback;
import com.example.popularmovies.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkManager {

    public static void pullMovieList(final Context mContext, final String path,
                                     final NetworkCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HomeScreenDataManager.setIsFetching(true);
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(Constant.SERVER_BASE_URL + path + "?" +
                            Constant.PARAM_API_KEY + "=" + Constant.YOUR_API_KEY);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader inputStreamReader = new InputStreamReader(in);

                    int data = inputStreamReader.read();
                    StringBuilder stringBuilder = new StringBuilder();
                    while (data != -1) {
                        char character = (char) data;
                        data = inputStreamReader.read();
                        stringBuilder.append(character);
                    }

                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONArray movieJsonArray = jsonObject.getJSONArray("results");
                    final List<Movie> movies = new Gson().fromJson(movieJsonArray.toString(),
                            new TypeToken<List<Movie>>() {
                            }.getType());
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(movies,
                                    path.contentEquals(Constant.POPULAR_URL_PATH) ?
                                            mContext.getString(R.string.most_popular) :
                                            mContext.getString(R.string.highest_rated));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    HomeScreenDataManager.setIsFetching(false);
                }
            }
        }).start();
    }
}
