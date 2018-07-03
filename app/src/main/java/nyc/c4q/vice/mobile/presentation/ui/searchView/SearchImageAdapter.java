package nyc.c4q.vice.mobile.presentation.ui.searchView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.data.model.SearchResponse;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.presentation.ui.ListenerDetails;

public class SearchImageAdapter extends RecyclerView.Adapter<SearchImageAdapter.SearchViewHolder> {
    private static final String MOVIE_IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w342/";
    private List<SearchResponse.Results> searcResponseList = new ArrayList<>();
    private ListenerDetails listenerDetails;

    public SearchImageAdapter(ListenerDetails listenerDetails) {
        this.listenerDetails = listenerDetails;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new SearchViewHolder(inflater.inflate(R.layout.search_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        SearchResponse.Results response= searcResponseList.get(position);
        holder.onBind(response);
    }

    @Override
    public int getItemCount() {
        return searcResponseList.size();
    }

    public void searchResponse(List<SearchResponse.Results> response) {
        searcResponseList.clear();
        searcResponseList.addAll(response);
        notifyDataSetChanged();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.image_movie) ImageView imageMovie;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.release_date) TextView releaseDate;

        @Inject
        Picasso picasso;

        private SearchResponse.Results response;

        public SearchViewHolder(View itemView) {
            super(itemView);
            ((ViceApp) itemView.getContext().getApplicationContext()).component().inject(this);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void onBind(SearchResponse.Results response) {
            this.response=response;
            if(response.getPoster_path()!=null){
            }
                String url=MOVIE_IMAGE_URL_PREFIX+response.getPoster_path();
                picasso.load(url).error(R.drawable.ic_reload).into(imageMovie);
            title.setText(response.getTitle());
            releaseDate.setText(response.getRelease_date());

        }

        @Override
        public void onClick(View v) {
            listenerDetails.passId(response.getId(), response.getPoster_path(), response.getTitle());
        }
    }


}
