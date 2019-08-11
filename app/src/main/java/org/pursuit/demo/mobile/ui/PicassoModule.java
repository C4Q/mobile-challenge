package org.pursuit.demo.mobile.ui;

import android.content.Context;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module
public class PicassoModule {
  @Provides @Singleton Picasso providePicasso(@App Context context, OkHttpClient client) {
    return new Picasso.Builder(context)
        .downloader(new OkHttp3Downloader(client))
        .build();
  }
}
