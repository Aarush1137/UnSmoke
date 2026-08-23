package com.unsmoke.app.feature.analytics;

import com.unsmoke.app.core.data.repository.HealthConnectRepository;
import com.unsmoke.app.core.domain.repository.CravingRepository;
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AnalyticsViewModel_Factory implements Factory<AnalyticsViewModel> {
  private final Provider<CravingRepository> cravingRepoProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<HealthConnectRepository> healthRepoProvider;

  public AnalyticsViewModel_Factory(Provider<CravingRepository> cravingRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<HealthConnectRepository> healthRepoProvider) {
    this.cravingRepoProvider = cravingRepoProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.healthRepoProvider = healthRepoProvider;
  }

  @Override
  public AnalyticsViewModel get() {
    return newInstance(cravingRepoProvider.get(), quitAttemptRepoProvider.get(), healthRepoProvider.get());
  }

  public static AnalyticsViewModel_Factory create(Provider<CravingRepository> cravingRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<HealthConnectRepository> healthRepoProvider) {
    return new AnalyticsViewModel_Factory(cravingRepoProvider, quitAttemptRepoProvider, healthRepoProvider);
  }

  public static AnalyticsViewModel newInstance(CravingRepository cravingRepo,
      QuitAttemptRepository quitAttemptRepo, HealthConnectRepository healthRepo) {
    return new AnalyticsViewModel(cravingRepo, quitAttemptRepo, healthRepo);
  }
}
