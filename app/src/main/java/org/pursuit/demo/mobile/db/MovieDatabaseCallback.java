package org.pursuit.demo.mobile.db;

import android.database.sqlite.SQLiteDiskIOException;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.squareup.sqldelight.android.AndroidSqliteDriver;
import com.squareup.sqldelight.db.SqlDriver;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import org.pursuit.demo.mobile.db.app.MovieDatabaseImplKt;

class MovieDatabaseCallback extends SupportSQLiteOpenHelper.Callback {
  public MovieDatabaseCallback(int version) {
    super(version);
  }

  @Override public void onOpen(SupportSQLiteDatabase db) {
    db.execSQL("PRAGMA foreign_keys = ON;");
    try {
      db.enableWriteAheadLogging();
    } catch (SQLiteDiskIOException ignored) {
      // WAL mode isn't required to run the app, its just faster. No worries if it doesn't work.
    }
  }

  @Override public void onCreate(SupportSQLiteDatabase db) {
    SqlDriver database = new AndroidSqliteDriver(db);
    KClass<MovieDatabase> movieDatabaseKClass =
        JvmClassMappingKt.getKotlinClass(MovieDatabase.class);
    MovieDatabaseImplKt.getSchema(movieDatabaseKClass).create(database);
  }

  @Override public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
    SqlDriver database = new AndroidSqliteDriver(db);
    KClass<MovieDatabase> movieDatabaseKClass =
        JvmClassMappingKt.getKotlinClass(MovieDatabase.class);
    MovieDatabaseImplKt.getSchema(movieDatabaseKClass).migrate(database, oldVersion, newVersion);
  }
}
