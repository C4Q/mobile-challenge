package nyc.c4q.vice.mobile.presentation.ui.homeView;

import java.util.List;

import nyc.c4q.vice.mobile.data.model.Movie;

public interface HomeController {

    interface View {
        void showToast(String message);

        void setNowPlayingAdapter(List<Movie> movieList);

        void setPopularsAdapter(List<Movie> movieList);
    }

    interface Presenter {
        void getNowPlayingMovies();

        void getMostPopularMovies();

        void disposeDisposable();
    }

}
