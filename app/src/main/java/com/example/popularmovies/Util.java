package com.example.popularmovies;

public class Util {
    public static boolean isConnectedToInternet() {
        final String command = "ping -c 1 google.com";
        try {
            return Runtime.getRuntime().exec(command).waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
