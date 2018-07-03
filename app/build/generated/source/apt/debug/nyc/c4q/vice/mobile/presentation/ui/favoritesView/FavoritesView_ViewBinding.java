// Generated code from Butter Knife. Do not modify!
package nyc.c4q.vice.mobile.presentation.ui.favoritesView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nyc.c4q.vice.mobile.R;

public class FavoritesView_ViewBinding implements Unbinder {
  private FavoritesView target;

  @UiThread
  public FavoritesView_ViewBinding(FavoritesView target) {
    this(target, target);
  }

  @UiThread
  public FavoritesView_ViewBinding(FavoritesView target, View source) {
    this.target = target;

    target.favoritesRecyclerView = Utils.findRequiredViewAsType(source, R.id.favorites, "field 'favoritesRecyclerView'", RecyclerView.class);
    target.emptyView = Utils.findRequiredViewAsType(source, R.id.empty, "field 'emptyView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FavoritesView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.favoritesRecyclerView = null;
    target.emptyView = null;
  }
}
