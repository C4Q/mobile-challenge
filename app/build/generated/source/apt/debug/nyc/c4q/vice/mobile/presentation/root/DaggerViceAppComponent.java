// Generated by Dagger (https://google.github.io/dagger).
package nyc.c4q.vice.mobile.presentation.root;

import com.squareup.picasso.Picasso;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import nyc.c4q.vice.mobile.data.api.ApiModule;
import nyc.c4q.vice.mobile.data.api.ApiModule_ProvideHttpClientFactory;
import nyc.c4q.vice.mobile.data.api.ApiModule_ProvideMovieServiceFactory;
import nyc.c4q.vice.mobile.data.api.ApiModule_ProvideRetrofitFactory;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.data.db.DatabaseModule;
import nyc.c4q.vice.mobile.data.db.DatabaseModule_ProvideDatabaseHelperFactory;
import nyc.c4q.vice.mobile.data.db.FavoritesDatabaseHelper;
import nyc.c4q.vice.mobile.presentation.ui.detailsView.DetailsActivity;
import nyc.c4q.vice.mobile.presentation.ui.detailsView.DetailsActivity_MembersInjector;
import nyc.c4q.vice.mobile.presentation.ui.favoritesView.FavoritesView;
import nyc.c4q.vice.mobile.presentation.ui.favoritesView.FavoritesView_MembersInjector;
import nyc.c4q.vice.mobile.presentation.ui.homeView.HomeView;
import nyc.c4q.vice.mobile.presentation.ui.homeView.HomeView_MembersInjector;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.MovieViewHolder;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.MovieViewHolder_MembersInjector;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.PicassoModule;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.PicassoModule_ProvidePicassoFactory;
import nyc.c4q.vice.mobile.presentation.ui.searchView.SearchImageAdapter;
import nyc.c4q.vice.mobile.presentation.ui.searchView.SearchImageAdapter_SearchViewHolder_MembersInjector;
import nyc.c4q.vice.mobile.presentation.ui.searchView.SearchView;
import nyc.c4q.vice.mobile.presentation.ui.searchView.SearchView_MembersInjector;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public final class DaggerViceAppComponent implements ViceAppComponent {
  private Provider<OkHttpClient> provideHttpClientProvider;

  private Provider<Retrofit> provideRetrofitProvider;

  private Provider<MovieService> provideMovieServiceProvider;

  private AndroidModule_ProvideAppContextFactory provideAppContextProvider;

  private Provider<FavoritesDatabaseHelper> provideDatabaseHelperProvider;

  private Provider<Picasso> providePicassoProvider;

  private DaggerViceAppComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.provideHttpClientProvider =
        DoubleCheck.provider(ApiModule_ProvideHttpClientFactory.create(builder.apiModule));
    this.provideRetrofitProvider =
        DoubleCheck.provider(
            ApiModule_ProvideRetrofitFactory.create(builder.apiModule, provideHttpClientProvider));
    this.provideMovieServiceProvider =
        DoubleCheck.provider(
            ApiModule_ProvideMovieServiceFactory.create(
                builder.apiModule, provideRetrofitProvider));
    this.provideAppContextProvider =
        AndroidModule_ProvideAppContextFactory.create(builder.androidModule);
    this.provideDatabaseHelperProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideDatabaseHelperFactory.create(
                builder.databaseModule, provideAppContextProvider));
    this.providePicassoProvider =
        DoubleCheck.provider(
            PicassoModule_ProvidePicassoFactory.create(
                builder.picassoModule, provideAppContextProvider, provideHttpClientProvider));
  }

  @Override
  public void inject(HomeView view) {
    injectHomeView(view);
  }

  @Override
  public void inject(FavoritesView view) {
    injectFavoritesView(view);
  }

  @Override
  public void inject(DetailsActivity activity) {
    injectDetailsActivity(activity);
  }

  @Override
  public void inject(MovieViewHolder viewHolder) {
    injectMovieViewHolder(viewHolder);
  }

  @Override
  public void inject(SearchView view) {
    injectSearchView(view);
  }

  @Override
  public void inject(SearchImageAdapter.SearchViewHolder view) {
    injectSearchViewHolder(view);
  }

  private HomeView injectHomeView(HomeView instance) {
    HomeView_MembersInjector.injectMovieService(instance, provideMovieServiceProvider.get());
    return instance;
  }

  private FavoritesView injectFavoritesView(FavoritesView instance) {
    FavoritesView_MembersInjector.injectDatabaseHelper(
        instance, provideDatabaseHelperProvider.get());
    return instance;
  }

  private DetailsActivity injectDetailsActivity(DetailsActivity instance) {
    DetailsActivity_MembersInjector.injectMovieService(instance, provideMovieServiceProvider.get());
    DetailsActivity_MembersInjector.injectDatabaseHelper(
        instance, provideDatabaseHelperProvider.get());
    DetailsActivity_MembersInjector.injectPicasso(instance, providePicassoProvider.get());
    return instance;
  }

  private MovieViewHolder injectMovieViewHolder(MovieViewHolder instance) {
    MovieViewHolder_MembersInjector.injectPicasso(instance, providePicassoProvider.get());
    return instance;
  }

  private SearchView injectSearchView(SearchView instance) {
    SearchView_MembersInjector.injectMovieService(instance, provideMovieServiceProvider.get());
    return instance;
  }

  private SearchImageAdapter.SearchViewHolder injectSearchViewHolder(
      SearchImageAdapter.SearchViewHolder instance) {
    SearchImageAdapter_SearchViewHolder_MembersInjector.injectPicasso(
        instance, providePicassoProvider.get());
    return instance;
  }

  public static final class Builder {
    private ApiModule apiModule;

    private AndroidModule androidModule;

    private DatabaseModule databaseModule;

    private PicassoModule picassoModule;

    private Builder() {}

    public ViceAppComponent build() {
      if (apiModule == null) {
        this.apiModule = new ApiModule();
      }
      if (androidModule == null) {
        throw new IllegalStateException(AndroidModule.class.getCanonicalName() + " must be set");
      }
      if (databaseModule == null) {
        this.databaseModule = new DatabaseModule();
      }
      if (picassoModule == null) {
        this.picassoModule = new PicassoModule();
      }
      return new DaggerViceAppComponent(this);
    }

    public Builder androidModule(AndroidModule androidModule) {
      this.androidModule = Preconditions.checkNotNull(androidModule);
      return this;
    }

    public Builder apiModule(ApiModule apiModule) {
      this.apiModule = Preconditions.checkNotNull(apiModule);
      return this;
    }

    public Builder databaseModule(DatabaseModule databaseModule) {
      this.databaseModule = Preconditions.checkNotNull(databaseModule);
      return this;
    }

    public Builder picassoModule(PicassoModule picassoModule) {
      this.picassoModule = Preconditions.checkNotNull(picassoModule);
      return this;
    }
  }
}