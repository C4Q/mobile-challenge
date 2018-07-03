package nyc.c4q.vice.mobile.presentation.ui.detailsView;

import android.content.Intent;

public interface DetailsController {

    interface View {
        void setImageResource(int resource);

        void setData(String backdropPath, String title, String release_date, double vote_average, String overview);

        void setReview(String content);

        void showMessage(String message);
    }

    interface Presenter {
        void isFavorite(Intent intent);

        void fabListener();

        void loadDetails();

        void disposeDisposable();
    }
}
