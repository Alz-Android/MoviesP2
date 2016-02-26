package com.example.al.moviesp1;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import API.ServiceGenerator;
import API.TrailerApi;
import models.MovieList;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Al on 2/25/2016.
 */
public class GetTrailer {

    TrailerApi movieService = ServiceGenerator.createService(TrailerApi.class);
    String apiKey = BuildConfig.MOVIES_TMDB_API_KEY;

    ArrayList<String> youtubeKeys = new ArrayList<>();
    private DetailActivity.PlaceholderFragment.FragmentCallback mFragmentCallback;

    ArrayList<String> movieIds = new  ArrayList<String>();

    public void GetTrailer(){
        for(int i = 0 ; i < MainActivityFragment.getMovieAdapter().getCount() ; i++){

            movieIds.add(MainActivityFragment.getMovieAdapter().getItem(i).mId);


        }
    }





}
