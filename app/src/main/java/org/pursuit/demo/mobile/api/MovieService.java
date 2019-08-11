package org.pursuit.demo.mobile.api;

import io.reactivex.Observable;
import org.pursuit.demo.mobile.model.MovieDetails;
import org.pursuit.demo.mobile.model.MovieResponse;
import org.pursuit.demo.mobile.model.ReviewResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {
  @GET("movie/now_playing")
  Observable<MovieResponse> getNowPlayingMovies(@Query("api_key") String key);

  @GET("movie/popular")
  Observable<MovieResponse> getPopularMovies(@Query("api_key") String key);

  @GET("movie/{movie_id}")
  Observable<MovieDetails> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String key);

  @GET("movie/{movie_id}/reviews")
  Observable<ReviewResponse> getReviews(@Path("movie_id") int movieId, @Query("api_key") String key);
}
