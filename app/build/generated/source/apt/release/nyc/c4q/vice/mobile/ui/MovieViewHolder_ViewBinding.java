// Generated code from Butter Knife. Do not modify!
package nyc.c4q.vice.mobile.presentation.ui;

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

public class MovieViewHolder_ViewBinding implements Unbinder {
  private MovieViewHolder target;

  @UiThread
  public MovieViewHolder_ViewBinding(MovieViewHolder target, View source) {
    this.target = target;

    target.image = Utils.findRequiredViewAsType(source, R.id.movie_image, "field 'image'", ImageView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.movie_title, "field 'title'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MovieViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.image = null;
    target.title = null;
  }
}
