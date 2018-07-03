package nyc.c4q.vice.mobile.presentation.ui.searchView;

import java.util.List;
import nyc.c4q.vice.mobile.data.model.SearchResponse;

public interface SearchController {

    interface View{
        void setRecyclerViewData(List<SearchResponse.Results> resultsList);
    }

    interface Presenter{
        void getSearchList(String text);

    }

}
