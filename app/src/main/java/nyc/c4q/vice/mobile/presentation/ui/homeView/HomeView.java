package nyc.c4q.vice.mobile.presentation.ui.homeView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import javax.inject.Inject;

import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.data.model.Movie;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.MovieAdapter;

public class HomeView extends LinearLayout implements HomeController.View {
    @BindView(R.id.now_playing)
    RecyclerView nowPlayingRecyclerView;
    @BindView(R.id.most_popular)
    RecyclerView mostPopularRecyclerView;

    @Inject
    MovieService movieService;

    private MovieAdapter nowPlayingAdapter;
    private MovieAdapter mostPopularAdapter;
    //holds subscriptions, you then can unsubscribe from all at once


    private HomeViewPresenter presenter;

    public HomeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //Dagger
        ((ViceApp) context.getApplicationContext()).component().inject(this);
        presenter = new HomeViewPresenter(this, movieService);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        //Pass item view layout, can be different when using same adapter different recycler views
        nowPlayingAdapter = new MovieAdapter(R.layout.movie_list_item);
        nowPlayingRecyclerView.setAdapter(nowPlayingAdapter);

        //Pass item view layout, can be different when using same adapter different recycler views
        mostPopularAdapter = new MovieAdapter(R.layout.movie_list_item);
        mostPopularRecyclerView.setAdapter(mostPopularAdapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.getNowPlayingMovies();
        presenter.getMostPopularMovies();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.disposeDisposable();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNowPlayingAdapter(List<Movie> movieList) {
        nowPlayingAdapter.setData(movieList);
    }

    @Override
    public void setPopularsAdapter(List<Movie> movieList) {
        mostPopularAdapter.setData(movieList);
    }
}