package com.example.al.moviesp1;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import API.ReviewApi;
import API.ServiceGenerator;
import API.TrailerApi;
import models.ReviewsList;
import models.TrailerList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Al on 2/25/2016.
 */
public class GetTrailer {

    TrailerApi trailerService = ServiceGenerator.createService(TrailerApi.class);
    ReviewApi reviewService = ServiceGenerator.createService(ReviewApi.class);

    String apiKey = BuildConfig.MOVIES_TMDB_API_KEY;
//
//    public static ArrayList<String> getmYoutubeKeys() {
//        return mYoutubeKeys;
//    }

    public static ArrayList<String> getmMovieReviews() {
        return mMovieReviews;
    }

    static ArrayList<String> mYoutubeKeys = new ArrayList<>();
    static ArrayList<String> mMovieReviews = new ArrayList<String>();

    private DetailActivity.PlaceholderFragment.FragmentCallback mFragmentCallback;

    public void GetTrailer(String movieId) {

        Call<TrailerList> callTrailer = trailerService.TRAILER_CALL(
                movieId, apiKey);

        Call<ReviewsList> callReviews = reviewService.REVIEW_CALL(
                movieId, apiKey);

        callReviews.enqueue(new Callback<ReviewsList>() {
            @Override
            public void onResponse(Call<ReviewsList> callReviews, Response<ReviewsList> response) {
                if (response.isSuccess()) {
                    Log.i("sort1z", "update2z");
                    for (int j = 0; j < response.body().results.size(); j++) {

                        mMovieReviews.add(response.body().results.get(j).content);
                        Log.i("sort1z", response.body().results.get(j).content);
                    }

                    if (mMovieReviews != null)
                        ((TextView) DetailActivity.rootView.findViewById(R.id.review_text)).setText(mMovieReviews.get(0));

                } else {
                    Log.i("sort1z", "update4z");
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<ReviewsList> callReviews, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("sort1", t.getMessage());
            }
        });

        callTrailer.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> callTrailer, Response<TrailerList> response) {
                if (response.isSuccess()) {
                    Log.i("sort1", "update2a");
                    for (int j = 0; j < response.body().results.size(); j++) {

                        mYoutubeKeys.add(response.body().results.get(j).key);
                        Log.i("sort1z", response.body().results.get(j).key);
                    }
                    if (mYoutubeKeys != null)
                        DetailActivity.mTrailerPath = mYoutubeKeys.get(0);

                } else {
                    Log.i("sort1", "update4");
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<TrailerList> callTrailer, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("sort1", t.getMessage());
            }
        });
    }
}



