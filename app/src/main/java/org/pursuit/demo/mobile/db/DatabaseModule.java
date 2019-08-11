package org.pursuit.demo.mobile.db;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.pursuit.demo.mobile.ui.App;

@Module
public class DatabaseModule {
  @Provides @Singleton FavoritesDatabaseHelper provideDatabaseHelper(@App Context context) {
    return new FavoritesDatabaseHelper(context);
  }
}
