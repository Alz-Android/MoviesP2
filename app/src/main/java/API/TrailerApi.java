package API;

import models.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Al on 2/25/2016.
 */
public interface TrailerApi {
    @GET("/3/movie/{id}")
    Call<MovieList> TRAILER_CALL(
            @Path("id") String id,
            @Query("api_key") String api_key
    );
}
