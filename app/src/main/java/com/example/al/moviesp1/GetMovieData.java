package com.example.al.moviesp1;

import android.util.Log;

import java.io.IOException;

import API.MovieApiEndpointInterface;
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
    public void GetMovieData(){}


    @Override
    public void onResponse(Call call, Response response) {
        Log.i("GetMovieData", "3");
    }

    @Override
    public void onFailure(Call call, Throwable t) {

    }

    public void GetWithRetro() {
        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key=768a237ac06abffaaebe82515e4d142a";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("GetMovieData", "1");
        // prepare call in Retrofit 2.0
        MovieApiEndpointInterface movieService = retrofit.create(MovieApiEndpointInterface.class);

        Log.i("GetMovieData", "2");

        Call<MovieList> call = movieService.MOVIE_LIST_CALL();
        try {
            Response response = call.execute();
        }catch(IOException e){
            Log.e("GetMovieData", "Error ", e);
        }
    }
}

//        call.enqueue(new Callback<MovieList>() {
//            @Override
//            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
//
//                int statusCode = response.code();
//                MovieList user = response.body();
//
//            }
//            @Override
//            public void onFailure(Call<MovieList> call, Throwable t) {
//                // Log error here since request failed
//            }
//        });



