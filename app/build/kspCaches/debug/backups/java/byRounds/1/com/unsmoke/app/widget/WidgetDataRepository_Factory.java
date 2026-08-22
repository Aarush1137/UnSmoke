package com.unsmoke.app.widget;

import android.content.Context;
import com.unsmoke.app.core.domain.repository.CravingRepository;
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class WidgetDataRepository_Factory implements Factory<WidgetDataRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<CravingRepository> cravingRepoProvider;

  public WidgetDataRepository_Factory(Provider<Context> contextProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider) {
    this.contextProvider = contextProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.cravingRepoProvider = cravingRepoProvider;
  }

  @Override
  public WidgetDataRepository get() {
    return newInstance(contextProvider.get(), quitAttemptRepoProvider.get(), cravingRepoProvider.get());
  }

  public static WidgetDataRepository_Factory create(Provider<Context> contextProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider) {
    return new WidgetDataRepository_Factory(contextProvider, quitAttemptRepoProvider, cravingRepoProvider);
  }

  public static WidgetDataRepository newInstance(Context context,
      QuitAttemptRepository quitAttemptRepo, CravingRepository cravingRepo) {
    return new WidgetDataRepository(context, quitAttemptRepo, cravingRepo);
  }
}
