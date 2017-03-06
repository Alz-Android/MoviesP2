package com.alz.al.moviesp1;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import com.alz.al.moviesp1.API.MovieApi;
import com.alz.al.moviesp1.API.ServiceGenerator;

import com.alz.al.moviesp1.models.DBMovieTable;
import com.alz.al.moviesp1.models.MoviesTable;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

import com.alz.al.moviesp1.models.MovieJSON;
import com.alz.al.moviesp1.models.MovieList;

/**
 * Created by Al on 1/27/2016.
 */
public class GetMovieData {

    private Context mContext;
    ArrayList<MovieInfo> movieInfoList = new ArrayList<>();

    public GetMovieData(Context context) {
        mContext = context;
    }

    public void updateMovies() {
        Log.i("sort1", "update1");
        movieInfoList.clear();

        // default 'false' flag for movies being favorites
        String[] args = new String[]{"false"};
        mContext.getContentResolver().delete(MoviesTable.CONTENT_URI, "favorite=?", args);

        MovieApi movieService = ServiceGenerator.createService(MovieApi.class);
        String apiKey = BuildConfig.MOVIES_TMDB_API_KEY;

        String[] sortOrders = {"popularity.desc","vote_average.desc"};

        for(int j = 0 ; j<=1 ; j++){
            Call<MovieList> call = movieService.MOVIE_LIST_CALL(String.valueOf(sortOrders[j]), apiKey);
            Log.i("sort1", "update11");

            Log.i("sort1", call.request().url().toString());

            final String isPopular = (j == 0 ? "true" : "false");

            call.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    Log.i("sort1", "update112");
                    if (response.isSuccess()) {
                        Log.i("sort1", "update2");
                        for (int i = 0; i < response.body().results.size(); i++) {
                            Log.i("sort1", "update3xx");
                            MovieJSON movie = (MovieJSON) response.body().results.get(i);
                            Log.i("sortid", "update4");

                            Log.i("sortid", movie.id.toString());

                            DBMovieTable movieRow = new DBMovieTable(
                                    "false",
                                    movie.id.toString(),
                                    movie.posterPath,
                                    movie.title,
                                    movie.overview,
                                    movie.voteAverage.toString(),
                                    movie.popularity.toString(),
                                    movie.releaseDate.toString(),
                                    isPopular
                            );

                            mContext.getContentResolver().insert(MoviesTable.CONTENT_URI, MoviesTable.getContentValues(movieRow, false));
                        }
                        Log.i("sort1", response.headers().toString());
                    } else {
                        Log.i("sort1", "update Error");
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
}