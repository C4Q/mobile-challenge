package nyc.c4q.vice.mobile.data.api;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {
  private static final String MOVIE_DATABASE_BASE_URL = "https://api.themoviedb.org/3/";

  @Provides @Singleton OkHttpClient provideHttpClient() {
    return new OkHttpClient();
  }

  @Provides @Singleton Retrofit provideRetrofit(OkHttpClient client) {
    return new Retrofit.Builder()
        .baseUrl(MOVIE_DATABASE_BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build();
  }

  @Provides @Singleton MovieService provideMovieService(Retrofit retrofit) {
    return retrofit.create(MovieService.class);
  }
}