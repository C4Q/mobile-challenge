package nyc.c4q.vice.mobile.presentation.ui.recyclerView;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.Collections;
import java.util.List;
import nyc.c4q.vice.mobile.data.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
  private final int resourceId;

  private List<Movie> movieList = Collections.emptyList();

  public MovieAdapter(@LayoutRes int resourceId) {
    this.resourceId = resourceId;
  }

  public void setData(List<Movie> nowPlaying) {
    this.movieList = nowPlaying;
    notifyDataSetChanged();
  }

  @NonNull @Override
  public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new MovieViewHolder(inflater.inflate(resourceId, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
    holder.bind(movieList.get(position));
  }

  @Override public int getItemCount() {
    return movieList.size();
  }
}