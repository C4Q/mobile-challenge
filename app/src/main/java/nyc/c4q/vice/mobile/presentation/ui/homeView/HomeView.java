package nyc.c4q.vice.mobile.presentation.ui.homeView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;
import nyc.c4q.vice.mobile.BuildConfig;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.MovieAdapter;

public class HomeView extends LinearLayout {
  @BindView(R.id.now_playing) RecyclerView nowPlayingRecyclerView;
  @BindView(R.id.most_popular) RecyclerView mostPopularRecyclerView;

  @Inject MovieService movieService;

  private MovieAdapter nowPlayingAdapter;
  private MovieAdapter mostPopularAdapter;
  //holds subscriptions, you then can unsubscribe from all at once
  private CompositeDisposable disposables = new CompositeDisposable();

  public HomeView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    //Dagger
    ((ViceApp) context.getApplicationContext()).component().inject(this);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);

    //Pass item view layout, can be different when using same adapter different recycler views
    nowPlayingAdapter = new MovieAdapter(R.layout.movie_list_item);
    nowPlayingRecyclerView.setAdapter(nowPlayingAdapter);

    //Pass item view layout, can be different when using same adapter different recycler views
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