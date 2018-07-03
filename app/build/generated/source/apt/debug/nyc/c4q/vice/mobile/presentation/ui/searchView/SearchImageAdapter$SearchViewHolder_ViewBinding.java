// Generated code from Butter Knife. Do not modify!
package nyc.c4q.vice.mobile.presentation.ui.searchView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nyc.c4q.vice.mobile.R;

public class SearchImageAdapter$SearchViewHolder_ViewBinding implements Unbinder {
  private SearchImageAdapter.SearchViewHolder target;

  @UiThread
  public SearchImageAdapter$SearchViewHolder_ViewBinding(SearchImageAdapter.SearchViewHolder target,
      View source) {
    this.target = target;

    target.imageMovie = Utils.findRequiredViewAsType(source, R.id.image_movie, "field 'imageMovie'", ImageView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.releaseDate = Utils.findRequiredViewAsType(source, R.id.release_date, "field 'releaseDate'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchImageAdapter.SearchViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageMovie = null;
    target.title = null;
    target.releaseDate = null;
  }
}
