package org.pursuit.demo.mobile.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding2.view.RxView;
import com.squareup.picasso.Picasso;
import com.squareup.sqldelight.runtime.rx.RxQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import javax.inject.Inject;
import kotlin.Unit;
import org.pursuit.demo.mobile.BuildConfig;
import org.pursuit.demo.mobile.PursuitDemoApp;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.api.MovieService;
import org.pursuit.demo.mobile.db.FavoritesQueries;
import org.pursuit.demo.mobile.db.MovieDatabase;
import org.pursuit.demo.mobile.model.Movie;
import org.pursuit.demo.mobile.model.Review;

public class DetailsActivity extends AppCompatActivity {
  private static final String MOVIE_BACKDROP_URL_PREFIX = "https://image.tmdb.org/t/p/w1280/";

  @BindView(R.id.image) ImageView imageView;
  @BindView(R.id.title) TextView titleView;
  @BindView(R.id.release_date) TextView releaseDateView;
  @BindView(R.id.rating) TextView ratingView;
  @BindView(R.id.overview) TextView overviewView;
  @BindView(R.id.reviews) ViewGroup reviews;
  @BindView(R.id.fab) FloatingActionButton fab;

  @Inject MovieService movieService;
  @Inject MovieDatabase movieDatabase;
  @Inject Picasso picasso;

  private CompositeDisposable disposables = new CompositeDisposable();
  private Movie thisMovie;
  private FavoritesQueries favoritesQueries;

  @Override protected void onCreate(@Nullable Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.activity_details);
    ButterKnife.bind(this);
    ((PursuitDemoApp) getApplication()).component().inject(this);
    favoritesQueries = movieDatabase.getFavoritesQueries();

    Intent intent = getIntent();
    int movieId = intent.getIntExtra("movie_id", 0);
    String posterPath = intent.getStringExtra("poster_path");
    String title = intent.getStringExtra("title");
    thisMovie = Movie.from(movieId, posterPath, title);
  }

  @Override protected void onResume() {
    super.onResume();

    disposables.add(
        RxQuery
            .mapToOne(
                RxQuery.toObservable(favoritesQueries.is_favorite(thisMovie.id))
            )
            .take(1) // only take the most recent update
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(isFavorite ->
                fab.setImageResource(isFavorite ? R.drawable.ic_done : R.drawable.ic_save)
            )
    );

    // this defers and splits the stream;
    // only one of the next two disposables will emit an item based on
    // whether the currently movie has already been saved as a favorite or not.
    ConnectableObservable<Boolean> sharedIsFavorite = RxView.clicks(fab)
        .flatMapSingle(click -> RxQuery
            .mapToOne(
                RxQuery.toObservable(favoritesQueries.is_favorite(thisMovie.id))
            )
            .firstOrError()
            // why first?  because SqlDelight observables are "hot" and will continue to emit
            // items every time an update to a database is made, and as a result, will never
            // complete unless explicitly disposed.
            //
            // Try moving .firstOrError and change flatMapSingle to flatMap, then run the app and
            // try to add/delete a favorite and watch the FAB freak out :)
            // Unsure why?  Set breakpoints in both 'map' blocks below and try debugging :)
        )
        .publish();

    disposables.add(
        sharedIsFavorite
            .filter(isFavorite -> isFavorite)
            .map(ignored -> {
              favoritesQueries.delete(thisMovie.id);
              return Unit.INSTANCE;
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(ignored -> fab.setImageResource(R.drawable.ic_save))
    );

    disposables.add(
        sharedIsFavorite
            .filter(isFavorite -> !isFavorite)
            .map(ignored -> {
              favoritesQueries.insert(thisMovie.id, thisMovie.poster_path, thisMovie.title);
              return Unit.INSTANCE;
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(ignored -> fab.setImageResource(R.drawable.ic_done))
    );

    // now that the two streams have been set up and subscribed to; allow upstream emissions!
    disposables.add(sharedIsFavorite.connect());

    disposables.add(
        movieService
            .getMovieDetails(thisMovie.id, BuildConfig.MOVIE_DATABASE_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                details -> {
                  String backdropPath = MOVIE_BACKDROP_URL_PREFIX + details.backdrop_path;
                  picasso.load(backdropPath).into(imageView);
                  titleView.setText(details.title);
                  releaseDateView.setText(details.release_date);
                  ratingView.setText(String.valueOf(details.vote_average));
                  overviewView.setText(details.overview);
                },
                t -> Log.e("C4Q", "Error obtaining movie details", t)
            )
    );

    disposables.add(
        movieService
            .getReviews(thisMovie.id, BuildConfig.MOVIE_DATABASE_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                reviewResponse -> {
                  for (Review review : reviewResponse.results) {
                    TextView reviewView = new TextView(this);
                    reviewView.setText(review.content);
                    reviews.addView(reviewView);
                  }
                },
                t -> Log.e("C4Q", "Error obtaining movie reviews", t)
            )
    );
  }

  @Override protected void onPause() {
    super.onPause();
    disposables.dispose();
  }
}
