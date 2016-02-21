package com.example.al.moviesp1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Al on 2/10/2016.
 */

public class GetTrailerTask extends AsyncTask<MovieInfo, Void, Boolean> {

    ArrayList<String> youtubeKeys = new ArrayList<>();
    private DetailActivity.PlaceholderFragment.FragmentCallback mFragmentCallback;

    public GetTrailerTask(DetailActivity.PlaceholderFragment.FragmentCallback fragmentCallback) {
        mFragmentCallback = fragmentCallback;
    }

    private ArrayList<String> getMovieDataFromJson(String trailerJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String Movie_Results = "results";
        final String MOVIE_key = "key";

        JSONObject moviesJson = new JSONObject(trailerJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(Movie_Results);

        for (int i = 0; i < moviesArray.length(); i++) {
            String key = "";

            // Get the JSON object representing a movie
            JSONObject movieObject = moviesArray.getJSONObject(i);
            key = movieObject.getString(MOVIE_key);

            Log.i("sort", "key: " + key);
            youtubeKeys.add(key);
        }
        return youtubeKeys;
    }


    @Override
    protected Boolean doInBackground(MovieInfo... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.

        Response response = null;
        BufferedReader reader = null;

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        // Will contain the raw JSON response as a string.
        String trailerJsonStr = null;

        try {
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
//            final String MOVIE_ID = "id";
            final String APIKEY_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                    .appendPath(params[0].mId)
                    .appendPath("videos")
                    .appendQueryParameter(APIKEY_PARAM, BuildConfig.MOVIES_TMDB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            Log.i("sort", "Query String: " + builtUri.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();
            InputStream inputStream = response.body().byteStream();


            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                trailerJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // new line to make debugging easier
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            trailerJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e("MainActivityFragment", "Error ", e);
            return null;
        } finally {
            if (response.body() != null) {
                response.body().close();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("MainActivityFragment", "Error closing stream", e);
                }
            }
        }
        Log.i("sort", trailerJsonStr);

        try {
            ArrayList<String> trailerKeys = getMovieDataFromJson(trailerJsonStr);
            if (trailerKeys != null) {
                Log.i("sort onPost", trailerJsonStr);

                if (trailerKeys.size() == 1)
                    params[0].SetTrailers(trailerKeys.get(0));
                else if (trailerKeys.size() >= 2)
                    params[0].SetTrailers(trailerKeys.get(0), trailerKeys.get(1));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {

        mFragmentCallback.onTaskDone();
    }
}