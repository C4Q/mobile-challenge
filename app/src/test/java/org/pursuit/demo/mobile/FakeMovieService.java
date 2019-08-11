package org.pursuit.demo.mobile;

import io.reactivex.Observable;
import java.util.Arrays;
import org.pursuit.demo.mobile.api.MovieService;
import org.pursuit.demo.mobile.model.MovieDetails;
import org.pursuit.demo.mobile.model.MovieResponse;
import org.pursuit.demo.mobile.model.Review;
import org.pursuit.demo.mobile.model.ReviewResponse;

public class FakeMovieService implements MovieService {
  @Override public Observable<MovieResponse> getNowPlayingMovies(String key) {
    throw new UnsupportedOperationException("TODO");
  }

  @Override public Observable<MovieResponse> getPopularMovies(String key) {
    throw new UnsupportedOperationException("TODO");
  }

  @Override public Observable<MovieDetails> getMovieDetails(int movieId, String key) {
    return Observable.just(
        MovieDetails.from(
            "oops/todo.png",
            "The Avengers",
            "1/1/1970",
            5.0,
            "They're back!"
        )
    );
  }

  @Override public Observable<ReviewResponse> getReviews(int movieId, String key) {
    return Observable.just(
        new ReviewResponse(
            Arrays.asList(
                Review.from("1", "JRod", "This movie is meh!", "http://rottentomatoes.com"),
                Review.from("2", "Evelyn", "This movie is great!", "http://rottentomatoes.com"),
                Review.from("3", "Geo", "This movie rocks!", "http://rottentomatoes.com")
            )
        )
    );
  }
}
