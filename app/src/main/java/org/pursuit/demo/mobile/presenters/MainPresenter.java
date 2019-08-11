package org.pursuit.demo.mobile.presenters;

import com.jakewharton.rxrelay2.BehaviorRelay;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import javax.inject.Inject;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.ResourceManager;
import org.pursuit.demo.mobile.viewmodels.MainViewEvent;
import org.pursuit.demo.mobile.viewmodels.MainViewModel;
import org.pursuit.demo.mobile.viewmodels.TabSelected;

public class MainPresenter implements Consumer<MainViewEvent>, ObservableSource<MainViewModel> {
  private final ResourceManager resourceManager;
  private final BehaviorRelay<MainViewModel> viewModels;

  @Inject
  public MainPresenter(ResourceManager resourceManager) {
    this.resourceManager = resourceManager;
    viewModels = BehaviorRelay.createDefault(
        new MainViewModel(resourceManager.getString(R.string.home_title), R.id.action_home)
    );
  }

  // any observer (i.e., a view) that subscribes to this presenter is actually
  // subscribing to the viewmodel stream
  @Override public void subscribe(Observer<? super MainViewModel> observer) {
    viewModels.subscribe(observer);
  }

  // any emission to this presenter is actually emitting to the viewevent stream
  @Override public void accept(MainViewEvent mainViewEvent) {
    if (!(mainViewEvent instanceof TabSelected)) {
      throw new IllegalArgumentException("Did you forget to add a new view event?");
    }

    TabSelected tabSelectedEvent = (TabSelected) mainViewEvent;

    MainViewModel nextViewModel;
    switch (tabSelectedEvent.menuItemId) {
      case R.id.action_home:
        nextViewModel =
            new MainViewModel(resourceManager.getString(R.string.home_title), R.id.action_home);
        break;
      case R.id.action_favorites:
        nextViewModel =
            new MainViewModel(resourceManager.getString(R.string.favorites_title),
                R.id.action_favorites);
        break;
      default:
        throw new IllegalStateException("Unknown menu item id");
    }

    viewModels.accept(nextViewModel);
  }
}
