package com.example.al.moviesp1;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import models.MovieList;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Al on 1/27/2016.
 */
public class GetMovieData implements Callback {
 //   public void GetMovieData(){}


    public void GetMovieData()
    {
        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key=768a237ac06abffaaebe82515e4d142a";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        MovieApiEndpointInterface movieService = retrofit.create(MovieApiEndpointInterface.class);

        Call<MovieList> call = movieService.MOVIE_LIST_CALL();
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                int statusCode = response.code();
                MovieList user = response.body();

            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }
}

