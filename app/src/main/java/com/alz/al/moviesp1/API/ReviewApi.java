package com.alz.al.moviesp1.API;

import com.alz.al.moviesp1.models.ReviewsList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Al on 2/25/2016.
 */
public interface ReviewApi {
    @GET("/3/movie/{id}/reviews")
    Call<ReviewsList> REVIEW_CALL(
            @Path("id") String id,
            @Query("api_key") String api_key
    );
}
