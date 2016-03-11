package com.example.al.moviesp1;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.util.ArrayList;
import API.MovieApi;
import API.ServiceGenerator;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.internal.Context;
import io.realm.internal.Table;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

import RealmDbObjects.MovieList;
import RealmDbObjects.MovieJSON;

//import models.MovieJSON;
//import models.MovieList;

/**
 * Created by Al on 1/27/2016.
 */
public class GetMovieData extends AppCompatActivity {

    ArrayList<MovieInfo> movieInfoList = new ArrayList<>();

    public void updateMovies(String sortOrder)
    {
        Log.i("sort1", "update1");
        movieInfoList.clear();
        Log.i("sort1", "update1a");

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
                    for(int i = 0; i < response.body().getResults().size(); i++) {
                        Log.i("sort1", "update3");
   //                     RealmDbObjects.MovieJSON movie = (RealmDbObjects.MovieJSON)response.body().results.get(i);

                        MovieJSON movie = (MovieJSON) response.body().getResults().get(i);

                        Log.i("sort1", "update4");

//                        MainActivityFragment.getRealm().beginTransaction();
//                        MainActivityFragment.getRealm().copyToRealm(movie);
//                        MainActivityFragment.getRealm().commitTransaction();

//                        movieInfoList.add(new MovieInfo(
//                                        movie.id.toString(),
//                                        movie.posterPath,
//                                        movie.title,
//                                        movie.overview,
//                                        movie.voteAverage.toString(),
//                                        movie.popularity.toString(),
//                                        movie.releaseDate.toString()
//                                )
//                        );

                        movieInfoList.add(new MovieInfo(
                                        movie.getId().toString(),
                                        movie.getPosterPath(),
                                        movie.getTitle(),
                                        movie.getOverview(),
                                        movie.getVoteAverage().toString(),
                                        movie.getPopularity().toString(),
                                        movie.getReleaseDate().toString()
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