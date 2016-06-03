package com.example.al.moviesp1;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import java.util.ArrayList;

import API.ReviewApi;
import API.ServiceGenerator;
import API.TrailerApi;
import models.DBMovieTable;
import models.DBReviewTable;
import models.DBTrailerTable;
import models.MovieJSON;
import models.MoviesTable;
import models.ReviewTable;
import models.ReviewsList;
import models.TrailerList;
import models.TrailerTable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Al on 2/25/2016.
 */
public class GetTrailer {

    private Context mContext;
//    private MainActivityFragment mainActivityFragment = new MainActivityFragment();

    public GetTrailer(Context context) {

        Log.i("GetTrailer", context.toString());
        mContext = context;
    }

    TrailerApi trailerService = ServiceGenerator.createService(TrailerApi.class);
    ReviewApi reviewService = ServiceGenerator.createService(ReviewApi.class);

    String apiKey = BuildConfig.MOVIES_TMDB_API_KEY;

    ArrayList<String> youtubeKeys = new ArrayList<>();
    private DetailActivity.PlaceholderFragment.FragmentCallback mFragmentCallback;

    ArrayList<String> movieIds = new ArrayList<String>();


    // This outer loop goes through all the movies
    // 1st inner loop uses retrofit to attach relevant Reviews for the current movie
    // 2nd inner loop uses retrofit to attach relevant Traiers for the current movie

    public void GetTrailer() {

        mContext.getContentResolver().delete(ReviewTable.CONTENT_URI,null,null);
        mContext.getContentResolver().delete(TrailerTable.CONTENT_URI,null,null);

        final String[] MOVIE_COLUMNS = {
                MoviesTable.FIELD__ID,
                MoviesTable.FIELD_ID,
                MoviesTable.FIELD_POSTER_PATH,
                MoviesTable.FIELD_TITLE,
                MoviesTable.FIELD_OVERVIEW,
                MoviesTable.FIELD_VOTEAVERAGE,
                MoviesTable.FIELD_POPULARITY,
                MoviesTable.FIELD_RELEASEDATE,
                MoviesTable.FIELD_ISPOPULAR
        };

        final Cursor cursor = mContext.getContentResolver().query(
                MoviesTable.CONTENT_URI,
                null, // new String[]{MoviesTable.FIELD_ID},
                null, //MoviesTable.FIELD_FAVORITE + " = ?",
                null, // new String[] {"false"},
                null);

        Log.i("GetTrailer", "works");

        if (cursor.moveToFirst()){
            do{
                String data = cursor.getString(cursor.getColumnIndex("id"));
                Log.i("GetTrailer", "works2");
                Log.i("GetTrailer", data);

            }while(cursor.moveToNext());
        }
        cursor.close();




        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            //           movieIds.add(MainActivityFragment.getMovieAdapter().getItem(i).mId);

            Call<TrailerList> callTrailer = trailerService.TRAILER_CALL(
                    cursor.getString(0), apiKey);

            Call<ReviewsList> callReviews = reviewService.REVIEW_CALL(
                    cursor.getString(0), apiKey);

            // needs to be declared final because it's being used in an inner class
 //           final int movieIndex = i;

            Log.i("GetTrailer", cursor.getString(cursor.getColumnIndex("id")));
            callReviews.enqueue(new Callback<ReviewsList>() {
                @Override
                public void onResponse(Call<ReviewsList> callReviews, Response<ReviewsList> response) {
                    if (response.isSuccess()) {
                        Log.i("GetTrailer", cursor.getString(0));
                        for (int j = 0; j < response.body().results.size(); j++) {
                            Log.i("GetTrailer", "update3z");

        // step one, get movie from adapter, and attach review
        // step 2: get movie from db and attach review

                            String movieID = cursor.getString(0);
                            String review = response.body().results.get(j).content;

                            Log.i("GetTrailer", " update4z");
                            DBReviewTable reviewRow = new DBReviewTable(movieID, review);

                            mContext.getContentResolver().insert(ReviewTable.CONTENT_URI, ReviewTable.getContentValues(reviewRow,false));
                            Log.i("GetTrailer", " update5z");
                        }
//                        Log.i("sort1z", response.headers().toString());
                    } else {
                        Log.i("GetTrailer", "update6z");
                        // error response, no access to resource?
                    }
                }
                @Override
                public void onFailure(Call<ReviewsList> callReviews, Throwable t) {
                    // something went completely south (like no internet connection)
                    Log.d("GetTrailer", t.getMessage());
                }
            });


            callTrailer.enqueue(new Callback<TrailerList>() {
                @Override
                public void onResponse(Call<TrailerList> callTrailer, Response<TrailerList> response) {
                    if (response.isSuccess()) {
                        Log.i("sort1z", "update2a");
                        for (int j = 0; j < response.body().results.size(); j++) {
                            Log.i("sort1z", "update3a");
//                            mainActivityFragment.getMovieAdapter().getItem(movieIndex).SetTrailers(
//                                    response.body().results.get(j).key );

                            String movieID = cursor.getString(0);
                            String trialer = response.body().results.get(j).key;

                            Log.i("GetTrailer", " update4a");
                            DBTrailerTable trailerRow = new DBTrailerTable(movieID, trialer);

                            mContext.getContentResolver().insert(TrailerTable.CONTENT_URI, TrailerTable.getContentValues(trailerRow,false));
                            Log.i("GetTrailer", " update5a");
                        }
        //                Log.i("sort1", response.headers().toString());
                    } else {
                        Log.i("sort1z", "update Error");
                        // error response, no access to resource?
                    }
                }
                @Override
                public void onFailure(Call<TrailerList> callTrailer, Throwable t) {
                    // something went completely south (like no internet connection)
                    Log.d("sort1", t.getMessage());
                }
            });
            cursor.moveToNext();
        }
    }
}




