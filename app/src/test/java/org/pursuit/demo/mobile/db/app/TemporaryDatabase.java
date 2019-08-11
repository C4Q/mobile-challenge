package org.pursuit.demo.mobile.db.app;

import com.squareup.sqldelight.db.SqlDriver;
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver;
import java.io.IOException;
import java.util.Properties;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import org.junit.rules.ExternalResource;
import org.pursuit.demo.mobile.db.MovieDatabase;

import static com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver.IN_MEMORY;

public class TemporaryDatabase extends ExternalResource {

  private SqlDriver driver;
  private MovieDatabase database;

  public TemporaryDatabase() {
    driver = new JdbcSqliteDriver(IN_MEMORY, new Properties());

    KClass<MovieDatabase> movieDatabaseKClass =
        JvmClassMappingKt.getKotlinClass(MovieDatabase.class);
    SqlDriver.Schema schema = MovieDatabaseImplKt.getSchema(movieDatabaseKClass);
    schema.create(driver);

    database = MovieDatabaseImplKt.newInstance(
        JvmClassMappingKt.getKotlinClass(MovieDatabase.class),
        driver
    );
  }

  public MovieDatabase getMovieDatabase() {
    return database;
  }

  @Override protected void after() {
    if (driver != null) {
      try {
        driver.close();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        driver = null;
      }
    }
  }
}
