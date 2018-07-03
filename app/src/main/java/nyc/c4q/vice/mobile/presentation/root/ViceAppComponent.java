package nyc.c4q.vice.mobile.presentation.root;

import dagger.Component;
import javax.inject.Singleton;
import nyc.c4q.vice.mobile.data.api.ApiModule;
import nyc.c4q.vice.mobile.data.db.DatabaseModule;
import nyc.c4q.vice.mobile.presentation.ui.detailsView.DetailsActivity;
import nyc.c4q.vice.mobile.presentation.ui.favoritesView.FavoritesView;
import nyc.c4q.vice.mobile.presentation.ui.homeView.HomeView;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.MovieViewHolder;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.PicassoModule;
import nyc.c4q.vice.mobile.presentation.ui.searchView.SearchImageAdapter;
import nyc.c4q.vice.mobile.presentation.ui.searchView.SearchView;

@Singleton
@Component(modules = {
    AndroidModule.class,
    ApiModule.class,
    DatabaseModule.class,
    PicassoModule.class
})
public interface ViceAppComponent {
  void inject(HomeView view);

  void inject(FavoritesView view);

  void inject(DetailsActivity activity);

  void inject(MovieViewHolder viewHolder);

  void inject(SearchView view);

  void inject(SearchImageAdapter.SearchViewHolder view);
}