package nyc.c4q.vice.mobile.presentation.root;

import android.app.Application;



public class ViceApp extends Application {

  private ViceAppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();

    appComponent = DaggerViceAppComponent.builder()
        .androidModule(new AndroidModule(this))
        .build();
  }

  public ViceAppComponent component() {
    return appComponent;
  }
}