package nyc.c4q.vice.mobile.presentation.ui.detailsView;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import nyc.c4q.vice.mobile.BuildConfig;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.data.db.FavoritesDatabaseHelper;
import nyc.c4q.vice.mobile.data.model.Movie;
import nyc.c4q.vice.mobile.data.model.Review;

public class DetailsPresenter implements DetailsController.Presenter {

    private static final String MOVIE_BACKDROP_URL_PREFIX = "https://image.tmdb.org/t/p/w1280/";
    private DetailsController.View view;
    private MovieService movieService;
    private FavoritesDatabaseHelper databaseHelper;
    private CompositeDisposable disposables = new CompositeDisposable();
    private int movieId;
    private String posterPath, title;

    public DetailsPresenter(DetailsActivity detailsActivity, MovieService movieService, FavoritesDatabaseHelper databaseHelper) {
        view = detailsActivity;
        this.movieService = movieService;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void isFavorite(Intent intent) {
        movieId = intent.getIntExtra("movie_id", 0);
        posterPath = intent.getStringExtra("poster_path");
        title = intent.getStringExtra("title");

        boolean isFavorite = databaseHelper.isFavorite(movieId);
        if (isFavorite) {
            view.setImageResource(R.drawable.ic_done);
        } else view.setImageResource(R.drawable.ic_save);

    }

    @Override
    public void fabListener() {
        boolean isFavorite1 = databaseHelper.isFavorite(movieId);
        if (isFavorite1) {
            databaseHelper.deleteFavorite(movieId);
            view.setImageResource(R.drawable.ic_save);
        } else {
            databaseHelper.addFavorite(Movie.from(movieId, posterPath, title));
            view.setImageResource(R.drawable.ic_done);
        }
    }

    @Override
    public void loadDetails() {
        disposables.add(
                movieService.getMovieDetails(movieId, BuildConfig.MOVIE_DATABASE_API_KEY)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                details -> {
                                    String backdropPath = MOVIE_BACKDROP_URL_PREFIX + details.backdrop_path;
                                    view.setData(backdropPath, details.title, details.release_date, details.vote_average, details.overview);
                                },
                                t -> view.showMessage("Error obtaining movie details")
                        )
        );

        disposables.add(
                movieService.getReviews(movieId, BuildConfig.MOVIE_DATABASE_API_KEY)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                reviewResponse -> {
                                    for (Review review : reviewResponse.results) {
                                        view.setReview(review.content);
                                    }
                                },
                                t -> view.showMessage("Error obtaining movie reviews")
                        )
        );
    }

    @Override
    public void disposeDisposable() {
        disposables.dispose();
    }
}
