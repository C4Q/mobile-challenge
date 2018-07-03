package nyc.c4q.vice.mobile.data.db;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import nyc.c4q.vice.mobile.presentation.root.App;

@Module
public class DatabaseModule {
  @Provides @Singleton FavoritesDatabaseHelper provideDatabaseHelper(@App Context context) {
    return new FavoritesDatabaseHelper(context);
  }
}