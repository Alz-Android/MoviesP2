package com.example.al.moviesp1.API;
import com.example.al.moviesp1.models.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Al on 1/28/2016.
 */
public interface MovieApi {

    @GET("/3/discover/movie")

    Call<MovieList> MOVIE_LIST_CALL(
            @Query("sort_by") String sort_by,
            @Query("api_key") String api_key
    );
}
