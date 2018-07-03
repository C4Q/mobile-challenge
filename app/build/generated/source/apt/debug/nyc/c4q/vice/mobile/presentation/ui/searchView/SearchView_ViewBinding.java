// Generated code from Butter Knife. Do not modify!
package nyc.c4q.vice.mobile.presentation.ui.searchView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nyc.c4q.vice.mobile.R;

public class SearchView_ViewBinding implements Unbinder {
  private SearchView target;

  @UiThread
  public SearchView_ViewBinding(SearchView target) {
    this(target, target);
  }

  @UiThread
  public SearchView_ViewBinding(SearchView target, View source) {
    this.target = target;

    target.inputSearch = Utils.findRequiredViewAsType(source, R.id.input_search, "field 'inputSearch'", EditText.class);
    target.searchRecyclerView = Utils.findRequiredViewAsType(source, R.id.recycler_view, "field 'searchRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SearchView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.inputSearch = null;
    target.searchRecyclerView = null;
  }
}
