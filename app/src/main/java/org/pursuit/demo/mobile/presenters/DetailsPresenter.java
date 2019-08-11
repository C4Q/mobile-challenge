package org.pursuit.demo.mobile.presenters;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.squareup.sqldelight.runtime.rx.RxQuery;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;
import javax.inject.Inject;
import kotlin.Pair;
import org.pursuit.demo.mobile.BuildConfig;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.api.MovieService;
import org.pursuit.demo.mobile.db.FavoritesQueries;
import org.pursuit.demo.mobile.db.MovieDatabase;
import org.pursuit.demo.mobile.model.Movie;
import org.pursuit.demo.mobile.model.MovieDetails;
import org.pursuit.demo.mobile.model.ReviewResponse;
import org.pursuit.demo.mobile.viewmodels.DetailsViewEvent;
import org.pursuit.demo.mobile.viewmodels.DetailsViewEvent.FabTapped;
import org.pursuit.demo.mobile.viewmodels.DetailsViewEvent.InitialLoad;
import org.pursuit.demo.mobile.viewmodels.DetailsViewModel;

public class DetailsPresenter
    implements Consumer<DetailsViewEvent>, ObservableSource<DetailsViewModel> {
  private final BehaviorRelay<DetailsViewModel> viewModels = BehaviorRelay.create();
  private final MovieService movieService;
  private final FavoritesQueries favoritesQueries;
  private final Scheduler ioScheduler;

  private CompositeDisposable disposables = new CompositeDisposable();

  @Inject
  public DetailsPresenter(MovieService movieService, MovieDatabase movieDatabase,
      Scheduler ioScheduler) {
    this.movieService = movieService;
    this.favoritesQueries = movieDatabase.getFavoritesQueries();
    this.ioScheduler = ioScheduler;
  }

  @Override public void subscribe(Observer<? super DetailsViewModel> observer) {
    viewModels.subscribe(observer);
  }

  @Override public void accept(DetailsViewEvent detailsViewEvent) {
    if (detailsViewEvent instanceof InitialLoad) {
      InitialLoad initialLoad = (InitialLoad) detailsViewEvent;
      Movie thisMovie = initialLoad.thisMovie;

      disposables.add(
          RxQuery
              .mapToOne(
                  RxQuery.toObservable(favoritesQueries.is_favorite(thisMovie.id), ioScheduler)
              )
              .take(1) // only take the most recent update
              .subscribe(isFavorite -> {
                    DetailsViewModel viewModel = new DetailsViewModel();
                    viewModel.fabResourceId = isFavorite ? R.drawable.ic_done : R.drawable.ic_save;
                    viewModels.accept(viewModel);
                  }
              )
      );

      disposables.add(
          movieService
              .getMovieDetails(thisMovie.id, BuildConfig.MOVIE_DATABASE_API_KEY)
              .flatMap(
                  movieDetails ->
                      movieService.getReviews(thisMovie.id, BuildConfig.MOVIE_DATABASE_API_KEY),
                  Pair::new
              )
              .subscribe(
                  pair -> {
                    MovieDetails details = pair.getFirst();
                    ReviewResponse reviewResponse = pair.getSecond();

                    DetailsViewModel viewModel = new DetailsViewModel();
                    viewModel.backdropPath = details.backdrop_path;
                    viewModel.title = details.title;
                    viewModel.releaseDate = details.release_date;
                    viewModel.voteAverage = details.vote_average;
                    viewModel.overView = details.overview;
                    viewModel.reviews = reviewResponse.results; // nice...
                    viewModels.accept(viewModel);
                  }
              )
      );
    } else if (detailsViewEvent instanceof FabTapped) {
      FabTapped fabTapped = (FabTapped) detailsViewEvent;

      // this defers and splits the stream;
      // only one of the next two disposables will emit an item based on
      // whether the currently movie has already been saved as a favorite or not.
      ConnectableObservable<Pair<Boolean, Movie>> sharedIsFavorite = RxQuery
          .mapToOne(
              RxQuery.toObservable(
                  favoritesQueries.is_favorite(fabTapped.thisMovie.id),
                  ioScheduler)
          )
          .map(isFavorite -> new Pair<>(isFavorite, fabTapped.thisMovie))
          .publish();

      disposables.add(
          sharedIsFavorite
              .filter(pair -> pair.getFirst())
              .subscribe(pair -> {
                Movie movie = pair.getSecond();
                favoritesQueries.delete(movie.id);

                DetailsViewModel viewModel = new DetailsViewModel();
                viewModel.fabResourceId = R.drawable.ic_save;
                viewModels.accept(viewModel);
              })
      );

      disposables.add(
          sharedIsFavorite
              .filter(pair -> !pair.getFirst())
              .subscribe(pair -> {
                Movie movie = pair.getSecond();
                favoritesQueries.insert(movie.id, movie.poster_path, movie.title);

                DetailsViewModel viewModel = new DetailsViewModel();
                viewModel.fabResourceId = R.drawable.ic_done;
                viewModels.accept(viewModel);
              })
      );

      // now that the two streams have been set up and subscribed to; allow upstream emissions!
      disposables.add(sharedIsFavorite.connect());
    } else {
      throw new IllegalArgumentException("Did you forget to add a new view event?");
    }
  }
}
