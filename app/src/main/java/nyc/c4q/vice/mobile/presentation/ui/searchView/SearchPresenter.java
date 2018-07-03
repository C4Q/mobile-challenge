package nyc.c4q.vice.mobile.presentation.ui.searchView;

import android.util.Log;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import nyc.c4q.vice.mobile.BuildConfig;
import nyc.c4q.vice.mobile.data.api.MovieService;
import nyc.c4q.vice.mobile.data.model.SearchResponse;

class SearchPresenter implements SearchController.Presenter{
    private SearchController.View view;
    private MovieService searchService;
    private PublishSubject<String> publishSubject = PublishSubject.create();

    public SearchPresenter(SearchView searchView, MovieService movieService) {
        view= searchView;
        searchService=movieService;
    }

    public DisposableObserver<SearchResponse> getObserver(){
        return new DisposableObserver<SearchResponse>() {

            @Override
            public void onNext(@NonNull SearchResponse SearchResponse) {
                view.setRecyclerViewData(SearchResponse.getResults());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d("==","Completed");
            }
        };
    }

    public Observable<String> getObservableQuery(String text){
        publishSubject.onNext(text);
        return publishSubject;
    }

    @Override
    public void getSearchList(String text) {
        getObservableQuery(text)
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
                        return searchService.searchMovies(BuildConfig.MOVIE_DATABASE_API_KEY,s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }
}
