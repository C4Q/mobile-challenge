package org.pursuit.demo.mobile.viewmodels;

import org.pursuit.demo.mobile.model.Movie;

public class DetailsViewEvent {
  public static class InitialLoad extends DetailsViewEvent {
    public final Movie thisMovie;

    public InitialLoad(Movie thisMovie) {
      this.thisMovie = thisMovie;
    }
  }

  public static class FabTapped extends DetailsViewEvent {
    public final Movie thisMovie;

    public FabTapped(Movie thisMovie) {
      this.thisMovie = thisMovie;
    }
  }
}
