package nyc.c4q.vice.mobile.presentation.ui.recyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.data.model.Movie;
import nyc.c4q.vice.mobile.presentation.ui.detailsView.DetailsActivity;

public class MovieViewHolder extends RecyclerView.ViewHolder {
  private static final String MOVIE_IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w342/";

  @BindView(R.id.movie_image) ImageView image;
  @BindView(R.id.movie_title) TextView title;

  @Inject Picasso picasso;

  public MovieViewHolder(View view) {
    super(view);
    ((ViceApp) view.getContext().getApplicationContext()).component().inject(this);
    ButterKnife.bind(this, view);
  }

  public void bind(final Movie movie) {
    String moviePosterUrl = MOVIE_IMAGE_URL_PREFIX + movie.poster_path;
    picasso.load(moviePosterUrl).error(R.drawable.ic_reload).into(image);
    title.setText(movie.title);

    itemView.setOnClickListener(v -> {
      Context context = itemView.getContext();

      Intent intent = new Intent(context, DetailsActivity.class);
      intent.putExtra("movie_id", movie.id);
      intent.putExtra("poster_path", movie.poster_path);
      intent.putExtra("title", movie.title);
      context.startActivity(intent);
    });
  }
}