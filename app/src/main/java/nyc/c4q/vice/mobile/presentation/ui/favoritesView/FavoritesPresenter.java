package nyc.c4q.vice.mobile.presentation.ui.favoritesView;

import java.util.List;

import nyc.c4q.vice.mobile.data.db.FavoritesDatabaseHelper;
import nyc.c4q.vice.mobile.data.model.Movie;

public class FavoritesPresenter implements FavoritesController.Presenter {
    private FavoritesController.View view;
    private FavoritesDatabaseHelper databaseHelper;

    public FavoritesPresenter(FavoritesView favoritesView, FavoritesDatabaseHelper databaseHelper) {
        view = favoritesView;
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void getFavorist() {
        List<Movie> favorites = databaseHelper.getFavorites();
        if (favorites.isEmpty()) {
            view.emptyList();

        } else {
            view.setFavorist(favorites);
        }
    }
}
