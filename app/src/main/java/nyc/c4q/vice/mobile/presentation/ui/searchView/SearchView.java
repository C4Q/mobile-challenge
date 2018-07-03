package nyc.c4q.vice.mobile.presentation.ui.searchView;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import android.widget.EditText;
import android.widget.LinearLayout;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.data.model.SearchResponse;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.presentation.ui.ListenerDetails;
import nyc.c4q.vice.mobile.presentation.ui.detailsView.DetailsActivity;

public class SearchView extends LinearLayout implements SearchController.View, ListenerDetails {

    @BindView(R.id.input_search) EditText inputSearch;
    @BindView(R.id.recycler_view) RecyclerView searchRecyclerView;

    @Inject
    MovieService movieService;

    private CompositeDisposable disposables = new CompositeDisposable();
    private SearchImageAdapter adapter;
    private SearchPresenter presenter;
    private Context context;


    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ((ViceApp) context.getApplicationContext()).component().inject(this);
        this.context=context;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        initializeRecycler();
    }

    private void initializeRecycler() {
        presenter = new SearchPresenter(this, movieService);
        adapter= new SearchImageAdapter(this);
        searchRecyclerView.setAdapter(adapter);
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void setRecyclerViewData(List<SearchResponse.Results> resultsList) {
        adapter.searchResponse(resultsList);
    }

    private void getTextChange() {

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String searchTerm= s.toString();
                presenter.getSearchList(searchTerm);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getTextChange();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposables.dispose();
    }


    @Override
    public void passId(int id, String posterPath, String title) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("movie_id", id);
        intent.putExtra("poster_path", posterPath);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }
}
