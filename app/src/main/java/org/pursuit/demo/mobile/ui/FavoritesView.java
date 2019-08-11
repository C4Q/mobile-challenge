package org.pursuit.demo.mobile.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;
import javax.inject.Inject;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.PursuitDemoApp;
import org.pursuit.demo.mobile.db.FavoritesDatabaseHelper;
import org.pursuit.demo.mobile.model.Movie;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class FavoritesView extends FrameLayout {
  @BindView(R.id.favorites) RecyclerView favoritesRecyclerView;
  @BindView(R.id.empty) TextView emptyView;

  @Inject FavoritesDatabaseHelper databaseHelper;

  private MovieAdapter favoritesAdapter;

  public FavoritesView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    ((PursuitDemoApp)context.getApplicationContext()).component().inject(this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    favoritesRecyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), HORIZONTAL, false));
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
