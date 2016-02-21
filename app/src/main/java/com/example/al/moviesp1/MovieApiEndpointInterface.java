package com.example.al.moviesp1;

import models.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Al on 1/28/2016.
 */
public interface MovieApiEndpointInterface {
    @GET("http://api.themoviedb.org/3/discover/movie?sort_by=vote_count.desc&api_key=768a237ac06abffaaebe82515e4d142a")

    Call<MovieList> MOVIE_LIST_CALL();



//    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
//    Call<MovieList> loadQuestions(@Query("tagged") String tags);

}
