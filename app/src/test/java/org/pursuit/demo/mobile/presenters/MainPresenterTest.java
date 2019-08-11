package org.pursuit.demo.mobile.presenters;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import org.pursuit.demo.mobile.FakeResourceManager;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.viewmodels.MainViewEvent;
import org.pursuit.demo.mobile.viewmodels.MainViewModel;
import org.pursuit.demo.mobile.viewmodels.TabSelected;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class MainPresenterTest {
  private static final String HOMEEEEEE = "Homeeeeee";
  private static final String FAVOORRRITEEES = "Favoorrriteees";

  private MainPresenter presenter;
  private FakeResourceManager resourceManager;

  @Before
  public void setUp() {
    resourceManager = new FakeResourceManager();
    resourceManager.putString(R.string.home_title, HOMEEEEEE);
    resourceManager.putString(R.string.favorites_title, FAVOORRRITEEES);

    presenter = new MainPresenter(resourceManager);
  }

  @Test
  public void shouldNotAllowInvalidViewEvent() {
    class Invalid extends MainViewEvent {}

    try {
      presenter.accept(new Invalid());
      fail("should not allow invalid view events");
    }
    catch (IllegalArgumentException ignored) { }
  }

  @Test
  public void setsHomeViewOnInitialLoad() {
    TestObserver<MainViewModel> viewModels = Observable.wrap(presenter).test();

    // no need to emit a view event to the presenter, since it should already have an initial load

    viewModels.assertValueCount(1);
    viewModels.values().get(0).toolbarTitle.equals(HOMEEEEEE);
  }

  @Test
  public void shouldEmitHomeViewModelOnHomeTabSelection() {
    TestObserver<MainViewModel> viewModels = Observable.wrap(presenter).test();

    presenter.accept(new TabSelected(R.id.action_home));

    // includes initial load
    viewModels.assertValueCount(2);
    viewModels.values().get(1).toolbarTitle.equals(HOMEEEEEE);
  }

  @Test
  public void shouldEmitFavoritesViewModelOnFavoritesTabSelection() {
    TestObserver<MainViewModel> viewModels = Observable.wrap(presenter).test();

    presenter.accept(new TabSelected(R.id.action_favorites));

    // includes initial load
    viewModels.assertValueCount(2);
    viewModels.values().get(1).toolbarTitle.equals(FAVOORRRITEEES);
  }
}
