// Generated code from Butter Knife. Do not modify!
package nyc.c4q.vice.mobile.presentation.ui.homeView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nyc.c4q.vice.mobile.R;

public class HomeView_ViewBinding implements Unbinder {
  private HomeView target;

  @UiThread
  public HomeView_ViewBinding(HomeView target) {
    this(target, target);
  }

  @UiThread
  public HomeView_ViewBinding(HomeView target, View source) {
    this.target = target;

    target.nowPlayingRecyclerView = Utils.findRequiredViewAsType(source, R.id.now_playing, "field 'nowPlayingRecyclerView'", RecyclerView.class);
    target.mostPopularRecyclerView = Utils.findRequiredViewAsType(source, R.id.most_popular, "field 'mostPopularRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeView target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.nowPlayingRecyclerView = null;
    target.mostPopularRecyclerView = null;
  }
}
