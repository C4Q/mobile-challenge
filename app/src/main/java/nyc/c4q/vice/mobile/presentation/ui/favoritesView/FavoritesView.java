package nyc.c4q.vice.mobile.presentation.ui.favoritesView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import javax.inject.Inject;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.data.db.FavoritesDatabaseHelper;
import nyc.c4q.vice.mobile.data.model.Movie;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.MovieAdapter;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class FavoritesView extends FrameLayout {
  @BindView(R.id.favorites) RecyclerView favoritesRecyclerView;
  @BindView(R.id.empty) TextView emptyView;

  @Inject FavoritesDatabaseHelper databaseHelper;

  private MovieAdapter favoritesAdapter;

  public FavoritesView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    ((ViceApp)context.getApplicationContext()).component().inject(this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    favoritesAdapter = new MovieAdapter(R.layout.full_movie_list_item);
    favoritesRecyclerView.setAdapter(favoritesAdapter);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    List<Movie> favorites = databaseHelper.getFavorites();
    if (favorites.isEmpty()) {
      favoritesRecyclerView.setVisibility(GONE);
      emptyView.setVisibility(VISIBLE);
    } else {
      favoritesAdapter.setData(favorites);
    }
  }
}
