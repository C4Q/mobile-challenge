package org.pursuit.demo.mobile.model;

import androidx.annotation.NonNull;
import java.util.List;

public class ReviewResponse {
  public List<Review> results;

  public ReviewResponse(@NonNull List<Review> results) {
    this.results = results;
  }
}
