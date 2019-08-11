package org.pursuit.demo.mobile.model;

import androidx.annotation.NonNull;

public class Review {
  String id;
  String author;
  public String content;
  String url;

  public static Review from(@NonNull String id, @NonNull String author, @NonNull String content,
      @NonNull String url) {
    Review review = new Review();
    review.id = id;
    review.author = author;
    review.content = content;
    review.url = url;
    return review;
  }
}
