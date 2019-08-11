package org.pursuit.demo.mobile;

import android.app.Application;

public class PursuitDemoApp extends Application {

  private PursuitDemoAppComponent appComponent;

  @Override public void onCreate() {
    super.onCreate();

    appComponent = DaggerPursuitDemoAppComponent.builder()
        .androidModule(new AndroidModule(this))
        .build();
  }

  public PursuitDemoAppComponent component() {
    return appComponent;
  }
}
