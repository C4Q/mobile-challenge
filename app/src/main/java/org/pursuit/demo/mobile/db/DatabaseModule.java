package org.pursuit.demo.mobile.db;

import android.content.Context;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import com.squareup.sqldelight.android.AndroidSqliteDriver;
import com.squareup.sqldelight.db.SqlDriver;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Singleton;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import org.pursuit.demo.mobile.db.app.MovieDatabaseImplKt;
import org.pursuit.demo.mobile.ui.App;

@Module
public class DatabaseModule {
  public static final String DATABASE_NAME = "pursuit_movies.db";

  // A little heavy since SqlDelight favors Kotlin over Java...
  @Provides @Singleton SupportSQLiteOpenHelper provideSQLiteHelper(@App Context context) {
    KClass<MovieDatabase> movieDatabaseKClass =
        JvmClassMappingKt.getKotlinClass(MovieDatabase.class);
    SqlDriver.Schema schema = MovieDatabaseImplKt.getSchema(movieDatabaseKClass);
    SupportSQLiteOpenHelper.Configuration config =
        SupportSQLiteOpenHelper.Configuration.builder(context)
            .name(DATABASE_NAME)
            .callback(new MovieDatabaseCallback(schema.getVersion()))
            .build();
    return new FrameworkSQLiteOpenHelperFactory().create(config);
  }

  @Provides @Singleton MovieDatabase provideMovieDatabase(SupportSQLiteOpenHelper helper) {
    return MovieDatabaseImplKt.newInstance(JvmClassMappingKt.getKotlinClass(MovieDatabase.class),
        new AndroidSqliteDriver(helper));
  }

  @Provides @Singleton Scheduler provideIoScheduler() {
    return Schedulers.io();
  }
}
