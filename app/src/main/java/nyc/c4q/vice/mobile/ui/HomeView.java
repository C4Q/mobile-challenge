package nyc.c4q.vice.mobile.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import nyc.c4q.vice.mobile.BuildConfig;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.ViceApp;
import nyc.c4q.vice.mobile.api.MovieService;

public class HomeView extends LinearLayout {
  @BindView(R.id.now_playing) RecyclerView nowPlayingRecyclerView;
  @BindView(R.id.most_popular) RecyclerView mostPopularRecyclerView;

  @Inject MovieService movieService;

  private MovieAdapter nowPlayingAdapter;
  private MovieAdapter mostPopularAdapter;
  private CompositeDisposable disposables = new CompositeDisposable();

  public HomeView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    ((ViceApp) context.getApplicationContext()).component().inject(this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    nowPlayingRecyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), HORIZONTAL, false));
    nowPlayingAdapter = new MovieAdapter(R.layout.movie_list_item);
    nowPlayingRecyclerView.setAdapter(nowPlayingAdapter);

    mostPopularRecyclerView.setLayoutManager(
        new LinearLayoutManager(getContext(), HORIZONTAL, false));
    mostPopularAdapter = new MovieAdapter(R.layout.movie_list_item);
    mostPopularRecyclerView.setAdapter(mostPopularAdapter);
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();

    disposables.add(
        movieService.getNowPlayingMovies(BuildConfig.MOVIE_DATABASE_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                movieResponse -> {
                  nowPlayingAdapter.setData(movieResponse.results);
                },
                t -> {
                  Log.e("C4Q", "Error obtaining movies", t);
                  Toast.makeText(getContext(), "Error obtaining movies", Toast.LENGTH_SHORT).show();
                })
    );

    disposables.add(
        movieService.getPopularMovies(BuildConfig.MOVIE_DATABASE_API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                movieResponse -> {
                  mostPopularAdapter.setData(movieResponse.results);
                },
                t -> {
                  Log.e("C4Q", "Error obtaining movies", t);
                  Toast.makeText(getContext(), "Error obtaining movies", Toast.LENGTH_SHORT).show();
                })
    );
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    disposables.dispose();
  }
}
