package com.example.popularmovies;

public class Constant {
    public final static String SERVER_BASE_URL = "https://api.themoviedb.org/3/movie";
    public final static String PARAM_API_KEY = "api_key";
    public final static String YOUR_API_KEY = "d5c18b182e754334e749c92f4ee9f19e";
    public final static String POPULAR_URL_PATH = "/popular";
    public final static String TOP_RATED_URL_PATH = "/top_rated";

    public interface IntentExtra {
        String SELECTED_MOVIE_JSON = "SELECTED_MOVIE_JSON";
    }
}
