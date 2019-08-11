package org.pursuit.demo.mobile.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import org.pursuit.demo.mobile.R;
import org.pursuit.demo.mobile.PursuitDemoApp;
import org.pursuit.demo.mobile.model.Movie;

public class MovieViewHolder extends RecyclerView.ViewHolder {
  private static final String MOVIE_IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w342/";

  @BindView(R.id.movie_image) ImageView image;
  @BindView(R.id.movie_title) TextView title;

  @Inject Picasso picasso;

  public MovieViewHolder(View view) {
    super(view);
    ((PursuitDemoApp) view.getContext().getApplicationContext()).component().inject(this);
    ButterKnife.bind(this, view);
  }

  public void bind(final Movie movie) {
    String moviePosterUrl = MOVIE_IMAGE_URL_PREFIX + movie.poster_path;
    picasso.load(moviePosterUrl).into(image);
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
