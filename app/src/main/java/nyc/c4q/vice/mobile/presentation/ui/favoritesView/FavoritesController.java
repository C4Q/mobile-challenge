package nyc.c4q.vice.mobile.presentation.ui.favoritesView;

import java.util.List;

import nyc.c4q.vice.mobile.data.model.Movie;

public interface FavoritesController {

    interface View {
        void emptyList();

        void setFavorist(List<Movie> favoristList);
    }

    interface Presenter {
        void getFavorist();
    }
}
