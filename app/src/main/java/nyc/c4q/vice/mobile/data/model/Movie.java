package nyc.c4q.vice.mobile.data.model;

public class Movie {
  public int id;
  public String poster_path;
  public String title;

  public static Movie from(int id, String posterPath, String title) {
    Movie movie = new Movie();
    movie.id = id;
    movie.poster_path = posterPath;
    movie.title = title;
    return movie;
  }

  public int getId() {
    return id;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public String getTitle() {
    return title;
  }
}