package nyc.c4q.vice.mobile.presentation.ui.detailsView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;

import nyc.c4q.vice.mobile.BuildConfig;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.data.db.FavoritesDatabaseHelper;
import nyc.c4q.vice.mobile.data.model.Movie;
import nyc.c4q.vice.mobile.data.model.Review;

public class DetailsActivity extends AppCompatActivity implements DetailsController.View {


    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.release_date)
    TextView releaseDateView;
    @BindView(R.id.rating)
    TextView ratingView;
    @BindView(R.id.overview)
    TextView overviewView;
    @BindView(R.id.reviews)
    ViewGroup reviews;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    MovieService movieService;
    @Inject
    FavoritesDatabaseHelper databaseHelper;
    @Inject
    Picasso picasso;

    private DetailsPresenter presenter;
    private Intent intent = getIntent();

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        ((ViceApp) getApplication()).component().inject(this);
        presenter = new DetailsPresenter(this, movieService, databaseHelper);
        presenter.isFavorite(intent);
        fab.setOnClickListener(v -> {
            presenter.fabListener();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.isFavorite(intent);
        presenter.loadDetails();

    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.disposeDisposable();
    }

    @Override
    public void setImageResource(int resource) {
        //if is favorite set check drawable, else save drawable
        fab.setImageResource(resource);
    }

    @Override
    public void setData(String backdropPath, String title, String release_date, double vote_average, String overview) {
        picasso.load(backdropPath).into(imageView);
        titleView.setText(title);
        releaseDateView.setText(release_date);
        ratingView.setText(String.valueOf(vote_average));
        overviewView.setText(overview);
    }

    @Override
    public void setReview(String content) {
        TextView reviewView = new TextView(this);
        reviewView.setText(content);
        reviews.addView(reviewView);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


}