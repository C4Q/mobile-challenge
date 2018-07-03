// Generated code from Butter Knife. Do not modify!
package nyc.c4q.vice.mobile.presentation.ui.detailsView;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import nyc.c4q.vice.mobile.R;

public class DetailsActivity_ViewBinding implements Unbinder {
  private DetailsActivity target;

  @UiThread
  public DetailsActivity_ViewBinding(DetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailsActivity_ViewBinding(DetailsActivity target, View source) {
    this.target = target;

    target.imageView = Utils.findRequiredViewAsType(source, R.id.image, "field 'imageView'", ImageView.class);
    target.titleView = Utils.findRequiredViewAsType(source, R.id.title, "field 'titleView'", TextView.class);
    target.releaseDateView = Utils.findRequiredViewAsType(source, R.id.release_date, "field 'releaseDateView'", TextView.class);
    target.ratingView = Utils.findRequiredViewAsType(source, R.id.rating, "field 'ratingView'", TextView.class);
    target.overviewView = Utils.findRequiredViewAsType(source, R.id.overview, "field 'overviewView'", TextView.class);
    target.reviews = Utils.findRequiredViewAsType(source, R.id.reviews, "field 'reviews'", ViewGroup.class);
    target.fab = Utils.findRequiredViewAsType(source, R.id.fab, "field 'fab'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.titleView = null;
    target.releaseDateView = null;
    target.ratingView = null;
    target.overviewView = null;
    target.reviews = null;
    target.fab = null;
  }
}
