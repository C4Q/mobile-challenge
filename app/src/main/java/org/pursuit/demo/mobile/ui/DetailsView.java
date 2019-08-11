package org.pursuit.demo.mobile.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.jakewharton.rxrelay2.PublishRelay;
import com.squareup.picasso.Picasso;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;
import org.pursuit.demo.mobile.PursuitDemoApp;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.model.Movie;
import org.pursuit.demo.mobile.model.Review;
import org.pursuit.demo.mobile.presenters.DetailsPresenter;
import org.pursuit.demo.mobile.viewmodels.DetailsViewEvent;
import org.pursuit.demo.mobile.viewmodels.DetailsViewModel;

public class DetailsView extends CoordinatorLayout implements Consumer<DetailsViewModel>,
    ObservableSource<DetailsViewEvent> {
  private static final String MOVIE_BACKDROP_URL_PREFIX = "https://image.tmdb.org/t/p/w1280/";

  @BindView(R.id.image) ImageView imageView;
  @BindView(R.id.title) TextView titleView;
  @BindView(R.id.release_date) TextView releaseDateView;
  @BindView(R.id.rating) TextView ratingView;
  @BindView(R.id.overview) TextView overviewView;
  @BindView(R.id.reviews) ViewGroup reviews;
  @BindView(R.id.fab) FloatingActionButton fab;

  @Inject DetailsPresenter presenter;
  @Inject Picasso picasso;

  private BehaviorRelay<DetailsViewModel> viewModels = BehaviorRelay.create();
  private PublishRelay<DetailsViewEvent> viewEvents = PublishRelay.create();
  private CompositeDisposable disposables = new CompositeDisposable();

  private Movie thisMovie;

  public DetailsView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
    Activity myHostActivity = (Activity) getContext();
    ((PursuitDemoApp) myHostActivity.getApplicationContext()).component().inject(this);

    Intent intent = myHostActivity.getIntent();
    int movieId = intent.getIntExtra("movie_id", 0);
    String posterPath = intent.getStringExtra("poster_path");
    String title = intent.getStringExtra("title");
    thisMovie = Movie.from(movieId, posterPath, title);
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();

    disposables.add(Observable.wrap(this).subscribe(presenter));
    disposables.add(Observable.wrap(presenter).subscribe(this));

    // this stream populates the view given the latest viewmodel from the presenter
    disposables.add(
        viewModels
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(viewModel -> {
              String backdropPath = MOVIE_BACKDROP_URL_PREFIX + viewModel.backdropPath;
              picasso.load(backdropPath).into(imageView);
              titleView.setText(viewModel.title);
              releaseDateView.setText(viewModel.releaseDate);
              ratingView.setText(String.valueOf(viewModel.voteAverage));
              overviewView.setText(viewModel.overView);

              reviews.removeAllViews();
              for (Review review : viewModel.reviews) {
                TextView reviewView = new TextView(getContext());
                reviewView.setText(review.content);
                reviews.addView(reviewView);
              }
            }
        )
    );

    // this stream emits a new viewevent to the presenter upon fab click
    disposables.add(
        RxView
            .clicks(fab)
            .subscribe(click -> viewEvents.accept(new DetailsViewEvent.FabTapped(thisMovie)))
    );
  }

  @Override public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    disposables.dispose();
  }

  @Override public void subscribe(Observer<? super DetailsViewEvent> observer) {
    viewEvents.subscribe(observer);
  }

  @Override public void accept(DetailsViewModel viewModel) {
    viewModels.accept(viewModel);
  }
}
