package nyc.c4q.vice.mobile.presentation.ui.homeView;

import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import nyc.c4q.vice.mobile.BuildConfig;
import nyc.c4q.vice.mobile.data.api.MovieService;

public class HomeViewPresenter implements HomeController.Presenter {


    private CompositeDisposable disposables = new CompositeDisposable();
    private MovieService movieService;
    private HomeController.View view;

    public HomeViewPresenter(HomeView homeView, MovieService movieService) {
        view = homeView;
        this.movieService = movieService;
    }

    @Override
    public void getNowPlayingMovies() {
        disposables.add(
                movieService.getNowPlayingMovies(BuildConfig.MOVIE_DATABASE_API_KEY)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                movieResponse -> {
                                    view.setNowPlayingAdapter(movieResponse.results);
                                },
                                t -> {
                                    Log.e("C4Q", "Error obtaining movies", t);
                                    view.showToast("Error obtaining movies");
                                })
        );
    }

    @Override
    public void getMostPopularMovies() {
        disposables.add(
                movieService.getPopularMovies(BuildConfig.MOVIE_DATABASE_API_KEY)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                movieResponse -> {
                                    view.setPopularsAdapter(movieResponse.results);
                                },
                                t -> {
                                    Log.e("C4Q", "Error obtaining movies", t);
                                    view.showToast("Error obtaining movies");
                                })
        );

    }

    @Override
    public void disposeDisposable() {
        disposables.dispose();
    }
}
