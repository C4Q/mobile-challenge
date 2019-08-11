package org.pursuit.demo.mobile.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.pursuit.demo.mobile.model.Movie;

public class FavoritesDatabaseHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "favoritesDatabase";
  private static final int DATABASE_VERSION = 1;
  private static final String TABLE_FAVORITES = "favorites";

  public FavoritesDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    String CREATE_FAVORITES_TABLE =
        "CREATE TABLE " + TABLE_FAVORITES + "("
            + "movieId INTEGER PRIMARY KEY,"
            + "posterPath TEXT NOT NULL,"
            + "title TEXT NOT NULL"
            + ")";

    db.execSQL(CREATE_FAVORITES_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
      onCreate(db);
    }
  }

  public void addFavorite(Movie favorite) {
    SQLiteDatabase db = getWritableDatabase();

    try {
      ContentValues values = new ContentValues();
      values.put("movieId", favorite.id);
      values.put("posterPath", favorite.poster_path);
      values.put("title", favorite.title);
      db.insertOrThrow(TABLE_FAVORITES, null, values);
    } catch (Exception e) {
      Log.e("C4Q", "Error while trying to add post to database", e);
    }
  }

  public void deleteFavorite(int movieId) {
    SQLiteDatabase db = getWritableDatabase();

    try {
      db.delete(TABLE_FAVORITES, "movieId = ?", new String[] { String.valueOf(movieId) });
    } catch (Exception e) {
      Log.e("C4Q", "Error while trying to delete all posts and users", e);
    }
  }

  public boolean isFavorite(int movieId) {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor =
        db.rawQuery("SELECT COUNT(1) as count FROM " + TABLE_FAVORITES + " WHERE movieId = ?",
            new String[] { String.valueOf(movieId) });

    int count = 0;
    try {
      if (cursor.moveToFirst()) {
        do {
          count = cursor.getInt(cursor.getColumnIndex("count"));
        } while (cursor.moveToNext());
      }
    } catch (Exception e) {
      Log.e("C4Q", "Error while trying to get posts from database", e);
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }

    return count != 0;
  }

  public List<Movie> getFavorites() {
    List<Movie> favorites = new ArrayList<>();

    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);
    try {
      if (cursor.moveToFirst()) {
        do {
          int movieId = cursor.getInt(cursor.getColumnIndex("movieId"));
          String posterPath = cursor.getString(cursor.getColumnIndex("posterPath"));
          String title = cursor.getString(cursor.getColumnIndex("title"));
          favorites.add(Movie.from(movieId, posterPath, title));
        } while (cursor.moveToNext());
      }
    } catch (Exception e) {
      Log.e("C4Q", "Error while trying to get favorites from database", e);
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return favorites;
  }
}
