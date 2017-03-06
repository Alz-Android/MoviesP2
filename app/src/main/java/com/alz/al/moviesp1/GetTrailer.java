package com.alz.al.moviesp1;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import com.alz.al.moviesp1.API.ReviewApi;
import com.alz.al.moviesp1.API.ServiceGenerator;
import com.alz.al.moviesp1.API.TrailerApi;
import com.alz.al.moviesp1.models.ReviewsList;
import com.alz.al.moviesp1.models.TrailerList;
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

    static ArrayList<String> mYoutubeKeys = new ArrayList<>();
    static ArrayList<String> mMovieReviews = new ArrayList<>();

    public void GetTrailer(String movieId) {

        mYoutubeKeys.clear();
        mMovieReviews.clear();

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
                    if (mMovieReviews.size() > 0)
                        ((TextView) DetailActivityFragment.rootView.findViewById(R.id.review_text)).setText(mMovieReviews.get(0));
                    else
                        ((TextView) DetailActivityFragment.rootView.findViewById(R.id.review_text)).setText("No reviews available");

                } else {
                    Log.i("sort1z", "update4z");
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
                    if (mYoutubeKeys.size() > 0)
                        DetailActivityFragment.mTrailerPath = mYoutubeKeys.get(0);

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



