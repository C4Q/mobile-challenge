package org.pursuit.demo.mobile.presenters;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.pursuit.demo.mobile.FakeMovieService;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.db.app.TemporaryDatabase;
import org.pursuit.demo.mobile.model.Movie;
import org.pursuit.demo.mobile.viewmodels.DetailsViewEvent;
import org.pursuit.demo.mobile.viewmodels.DetailsViewEvent.FabTapped;
import org.pursuit.demo.mobile.viewmodels.DetailsViewEvent.InitialLoad;
import org.pursuit.demo.mobile.viewmodels.DetailsViewModel;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

public class DetailsPresenterTest {
  private static final Movie SAMPLE_MOVIE = Movie.from(1, "http://whatever.com", "The Avengers");

  @Rule public TemporaryDatabase database = new TemporaryDatabase();

  private DetailsPresenter presenter;

  @Before
  public void setUp() {
    FakeMovieService movieService = new FakeMovieService();
    presenter = new DetailsPresenter(
        movieService,
        database.getMovieDatabase(),
        Schedulers.trampoline());
  }

  @Test
  public void shouldNotAllowInvalidViewEvent() {
    class Invalid extends DetailsViewEvent {
    }

    try {
      presenter.accept(new Invalid());
      fail("should not allow invalid view events");
    } catch (IllegalArgumentException ignored) {
    }
  }

  @Test
  public void setsDetailsViewOnInitialLoad() {
    TestObserver<DetailsViewModel> viewModels = Observable.wrap(presenter).test();

    // no need to emit a view event to the presenter, since it should already have an initial load
    presenter.accept(new InitialLoad(SAMPLE_MOVIE));

    DetailsViewModel model = viewModels.values().get(1);
    assertThat(model.backdropPath).isEqualTo("oops/todo.png");
    assertThat(model.title).isEqualTo("The Avengers");
    assertThat(model.releaseDate).isEqualTo("1/1/1970");
    assertThat(model.voteAverage).isEqualTo(5.0);
    assertThat(model.overView).isEqualTo("They're back!");
    assertThat(model.reviews).hasSize(3);
  }

  // TODO: make this test pass
  @Test
  public void tappingFabWhenNotFavoriteMakesItFavorite() {
    TestObserver<DetailsViewModel> viewModels = Observable.wrap(presenter).test();

    presenter.accept(new FabTapped(SAMPLE_MOVIE));

    // includes initial load
    viewModels.assertValueCount(1);
    DetailsViewModel model = viewModels.values().get(1);
    assertThat(model.fabResourceId).isEqualTo(R.drawable.ic_done);
  }

  // TODO: make this test pass
  @Test
  public void tappingFabWhenFavoriteMakesItNotFavorite() {
    TestObserver<DetailsViewModel> viewModels = Observable.wrap(presenter).test();

    presenter.accept(new FabTapped(SAMPLE_MOVIE));
    presenter.accept(new FabTapped(SAMPLE_MOVIE));

    // includes initial load
    viewModels.assertValueCount(2);
    DetailsViewModel model = viewModels.values().get(1);
    assertThat(model.fabResourceId).isEqualTo(R.drawable.ic_save);
  }
}
