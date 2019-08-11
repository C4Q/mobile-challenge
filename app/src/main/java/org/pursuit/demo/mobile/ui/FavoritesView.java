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
import com.squareup.sqldelight.runtime.rx.RxQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.pursuit.demo.mobile.PursuitDemoApp;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.db.Favorites;
import org.pursuit.demo.mobile.db.FavoritesQueries;
import org.pursuit.demo.mobile.db.MovieDatabase;
import org.pursuit.demo.mobile.model.Movie;

import static androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL;

public class FavoritesView extends FrameLayout {
  @BindView(R.id.favorites) RecyclerView favoritesRecyclerView;
  @BindView(R.id.empty) TextView emptyView;

  @Inject MovieDatabase movieDatabase;

  private MovieAdapter favoritesAdapter;
  private FavoritesQueries favoritesQueries;
  // only one disposable; no need for CompositeDisposable
  private Disposable disposable;

  public FavoritesView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    ((PursuitDemoApp)context.getApplicationContext()).component().inject(this);
    favoritesQueries = movieDatabase.getFavoritesQueries();
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    favoritesRecyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), HORIZONTAL, false)
    );
    favoritesAdapter = new MovieAdapter(R.layout.full_movie_list_item);
    favoritesRecyclerView.setAdapter(favoritesAdapter);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    disposable = RxQuery
        .mapToList(
            RxQuery
                .toObservable(favoritesQueries.select())
        )
        .map(favorites -> {
              List<Movie> movies = new ArrayList<>();
              for (Favorites favorite : favorites) {
                movies.add(
                    Movie.from(
                        favorite.getMovie_id(),
                        favorite.getPoster_path(),
                        favorite.getTitle()
                    )
                );
              }
              return movies;
            }
        )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movies -> {
          if (movies.isEmpty()) {
            favoritesRecyclerView.setVisibility(GONE);
            emptyView.setVisibility(VISIBLE);
          } else {
            favoritesAdapter.setData(movies);
          }
        });
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    disposable.dispose();
  }
}
