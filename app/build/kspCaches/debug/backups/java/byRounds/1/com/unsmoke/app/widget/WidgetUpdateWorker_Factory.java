package com.unsmoke.app.widget;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class WidgetUpdateWorker_Factory {
  private final Provider<WidgetDataRepository> widgetDataRepoProvider;

  public WidgetUpdateWorker_Factory(Provider<WidgetDataRepository> widgetDataRepoProvider) {
    this.widgetDataRepoProvider = widgetDataRepoProvider;
  }

  public WidgetUpdateWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, widgetDataRepoProvider.get());
  }

  public static WidgetUpdateWorker_Factory create(
      Provider<WidgetDataRepository> widgetDataRepoProvider) {
    return new WidgetUpdateWorker_Factory(widgetDataRepoProvider);
  }

  public static WidgetUpdateWorker newInstance(Context context, WorkerParameters workerParams,
      WidgetDataRepository widgetDataRepo) {
    return new WidgetUpdateWorker(context, workerParams, widgetDataRepo);
  }
}
