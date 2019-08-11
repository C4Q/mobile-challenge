package org.pursuit.demo.mobile;

import dagger.Component;
import javax.inject.Singleton;
import org.pursuit.demo.mobile.api.ApiModule;
import org.pursuit.demo.mobile.db.DatabaseModule;
import org.pursuit.demo.mobile.ui.DetailsView;
import org.pursuit.demo.mobile.ui.FavoritesView;
import org.pursuit.demo.mobile.ui.HomeView;
import org.pursuit.demo.mobile.ui.MainView;
import org.pursuit.demo.mobile.ui.MovieViewHolder;
import org.pursuit.demo.mobile.ui.PicassoModule;

@Singleton
@Component(modules = {
    AndroidModule.class,
    ApiModule.class,
    DatabaseModule.class,
    PicassoModule.class
})
public interface PursuitDemoAppComponent {
  void inject(MainView view);

  void inject(HomeView view);

  void inject(FavoritesView view);

  void inject(DetailsView view);

  void inject(MovieViewHolder viewHolder);
}
