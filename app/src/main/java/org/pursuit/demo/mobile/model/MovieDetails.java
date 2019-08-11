package org.pursuit.demo.mobile.model;

import androidx.annotation.NonNull;

public class MovieDetails {
  public String backdrop_path;
  public String title;
  public String release_date;
  public double vote_average;
  public String overview;

  public static MovieDetails from(@NonNull String backdrop_path, @NonNull String title,
      @NonNull String release_date,
      double vote_average, @NonNull String overview) {
    MovieDetails details = new MovieDetails();
    details.backdrop_path = backdrop_path;
    details.title = title;
    details.release_date = release_date;
    details.vote_average = vote_average;
    details.overview = overview;
    return details;
  }
}
