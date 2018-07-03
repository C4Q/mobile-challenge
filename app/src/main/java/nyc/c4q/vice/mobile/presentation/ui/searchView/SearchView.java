package nyc.c4q.vice.mobile.presentation.ui.searchView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.net.NetworkInterface;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import nyc.c4q.vice.mobile.BuildConfig;
import nyc.c4q.vice.mobile.R;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.data.model.SearchResponse;
import nyc.c4q.vice.mobile.presentation.root.ViceApp;
import nyc.c4q.vice.mobile.presentation.ui.recyclerView.MovieAdapter;

public class SearchView extends LinearLayout {

    @BindView(R.id.input_search) EditText inputSearch;
    @BindView(R.id.recycler_view) RecyclerView searchRecyclerView;

    @Inject
    MovieService movieService;

    private CompositeDisposable disposables = new CompositeDisposable();
    private PublishSubject<String> publishSubject = PublishSubject.create();
    private SearchImageAdapter adapter;


    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ((ViceApp) context.getApplicationContext()).component().inject(this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        adapter= new SearchImageAdapter();
        searchRecyclerView.setAdapter(adapter);
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        String searchmovie = inputSearch.getText().toString();

        getObservableQuery(inputSearch)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        if(s.equals("")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                })
                .debounce(2, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<SearchResponse>>() {
                    @Override
                    public Observable<SearchResponse> apply(@NonNull String s) throws Exception {
                        return movieService.searchMovies(BuildConfig.MOVIE_DATABASE_API_KEY,s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());


    }


    private Observable<String> getObservableQuery(EditText query){

        final PublishSubject<String> publishSubject = PublishSubject.create();

        query.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                String text= s.toString();
//                Log.d("==", "beforeTextChanged: "+text);
//                publishSubject.onNext(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text= s.toString();
                Log.d("==", "beforeTextChanged: "+text);
                publishSubject.onNext(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return publishSubject;
    }


    public DisposableObserver<SearchResponse> getObserver(){
        return new DisposableObserver<SearchResponse>() {

            @Override
            public void onNext(@NonNull SearchResponse SearchResponse) {
                Log.d("==","OnNext"+SearchResponse.getResults().size());
                Log.d("==","OnNext"+SearchResponse.getResults().get(0).getBackdrop_path());
                adapter.searchResponse(SearchResponse.getResults());

                //searchviewInterface.displayResult(MovieResponse);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("==","Error"+e);
                e.printStackTrace();
                //searchviewInterface.displayError("Error fetching Movie Data");
            }

            @Override
            public void onComplete() {
                Log.d("==","Completed");
            }
        };
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        disposables.dispose();
    }
}
