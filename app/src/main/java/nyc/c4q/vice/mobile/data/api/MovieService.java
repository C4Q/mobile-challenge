package nyc.c4q.vice.mobile.data.api;

import io.reactivex.Observable;
import nyc.c4q.vice.mobile.data.model.MovieDetails;
import nyc.c4q.vice.mobile.data.model.MovieResponse;
import nyc.c4q.vice.mobile.data.model.ReviewResponse;
import nyc.c4q.vice.mobile.data.model.SearchResponse;
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

  @GET("search/movie")
  Observable<SearchResponse> searchMovies(@Query("api_key") String key, @Query("query") String query);

}