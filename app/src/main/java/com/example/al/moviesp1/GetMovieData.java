package com.example.al.moviesp1;

import android.util.Log;
import java.util.ArrayList;
import API.MovieApi;
import API.ServiceGenerator;
import models.MovieList;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Al on 1/27/2016.
 */
public class GetMovieData {

    ArrayList<MovieInfo> movieInfoList = new ArrayList<>();

    public void updateMovies(String sortOrder)
    {
        Log.i("sort1", "update1");
        movieInfoList.clear();

        MovieApi movieService = ServiceGenerator.createService(MovieApi.class);
        String apiKey = BuildConfig.MOVIES_TMDB_API_KEY;
        Log.i("sort1", sortOrder + " "+ apiKey);
        Call<MovieList> call = movieService.MOVIE_LIST_CALL(sortOrder, apiKey);
        Log.i("sort1", "update11");


        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccess()) {
                    Log.i("sort1", "update2");
                    for(int i = 0; i < response.body().results.size(); i++) {
                        Log.i("sort1", "update3");
                        models.MovieJSON movie = response.body().results.get(i);
                        Log.i("sort1", "update4");
                        movieInfoList.add(new MovieInfo(
                                        movie.id.toString(),
                                        movie.posterPath,
                                        movie.title,
                                        movie.overview,
                                        movie.voteAverage.toString(),
                                        movie.popularity.toString(),
                                        movie.releaseDate.toString()
                                )
                        );
                    }
                    Log.i("sort1", response.headers().toString());

                    MainActivityFragment.setMovieAdapter(movieInfoList);
                } else {
                    Log.i("sort1", "update4");
                    // error response, no access to resource?
                }
            }
            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("sort1", t.getMessage());
            }
        });
    }
}
