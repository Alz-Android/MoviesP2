package com.example.al.moviesp1;

import android.content.Context;
import android.util.Log;
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

    private Context mContext;
    private MainActivityFragment mainActivityFragment = new MainActivityFragment();

    public GetTrailer(Context context) {

        Log.i("TrailerContext", context.toString());
        mContext = context;
    }

    TrailerApi trailerService = ServiceGenerator.createService(TrailerApi.class);
    ReviewApi reviewService = ServiceGenerator.createService(ReviewApi.class);

    String apiKey = BuildConfig.MOVIES_TMDB_API_KEY;

    ArrayList<String> youtubeKeys = new ArrayList<>();
    private DetailActivity.PlaceholderFragment.FragmentCallback mFragmentCallback;

    ArrayList<String> movieIds = new ArrayList<String>();

    public void GetTrailer() {
        for (int i = 0; i < mainActivityFragment.getMovieAdapter().getCount(); i++) {
            //           movieIds.add(MainActivityFragment.getMovieAdapter().getItem(i).mId);

            Call<TrailerList> callTrailer = trailerService.TRAILER_CALL(
                    mainActivityFragment.getMovieAdapter().getItem(i).mId, apiKey);

            Call<ReviewsList> callReviews = reviewService.REVIEW_CALL(
                    mainActivityFragment.getMovieAdapter().getItem(i).mId, apiKey);

            // needs to be declared final because it's being used in an inner class
            final int movieIndex = i;
            callReviews.enqueue(new Callback<ReviewsList>() {
                @Override
                public void onResponse(Call<ReviewsList> callReviews, Response<ReviewsList> response) {
                    if (response.isSuccess()) {
                        Log.i("sort1z", "update2z");
                        for (int j = 0; j < response.body().results.size(); j++) {
                            Log.i("sort1z", "update3z");
                            mainActivityFragment.getMovieAdapter().getItem(movieIndex).SetReviews(
                                    response.body().results.get(j).content);



//                            Log.i("sort1z", MainActivityFragment.getMovieAdapter().getItem(movieIndex).mReviews);
                        }
//                        Log.i("sort1z", response.headers().toString());
                    } else {
                        Log.i("sort1z", "update4z");
                        // error response, no access to resource?
                    }
                }
                @Override
                public void onFailure(Call<ReviewsList> callReviews, Throwable t) {
                    // something went completely south (like no internet connection)
                    Log.d("sort1z", t.getMessage());
                }
            });


            callTrailer.enqueue(new Callback<TrailerList>() {
                @Override
                public void onResponse(Call<TrailerList> callTrailer, Response<TrailerList> response) {
                    if (response.isSuccess()) {
                        Log.i("sort1z", "update2a");
                        for (int j = 0; j < response.body().results.size(); j++) {
                            Log.i("sort1z", "update3a");
                            mainActivityFragment.getMovieAdapter().getItem(movieIndex).SetTrailers(
                                    response.body().results.get(j).key
                            );
        //                    Log.i("sort1", MainActivityFragment.getMovieAdapter().getItem(movieIndex).mTrailerPath0);
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
        }
    }
}




