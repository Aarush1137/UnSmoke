package com.unsmoke.app.feature.insights;

import com.unsmoke.app.core.domain.repository.CravingRepository;
import com.unsmoke.app.core.domain.repository.NRTRepository;
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
public final class InsightsViewModel_Factory implements Factory<InsightsViewModel> {
  private final Provider<CravingRepository> cravingRepoProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<NRTRepository> nrtRepoProvider;

  public InsightsViewModel_Factory(Provider<CravingRepository> cravingRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<NRTRepository> nrtRepoProvider) {
    this.cravingRepoProvider = cravingRepoProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.nrtRepoProvider = nrtRepoProvider;
  }

  @Override
  public InsightsViewModel get() {
    return newInstance(cravingRepoProvider.get(), quitAttemptRepoProvider.get(), nrtRepoProvider.get());
  }

  public static InsightsViewModel_Factory create(Provider<CravingRepository> cravingRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<NRTRepository> nrtRepoProvider) {
    return new InsightsViewModel_Factory(cravingRepoProvider, quitAttemptRepoProvider, nrtRepoProvider);
  }

  public static InsightsViewModel newInstance(CravingRepository cravingRepo,
      QuitAttemptRepository quitAttemptRepo, NRTRepository nrtRepo) {
    return new InsightsViewModel(cravingRepo, quitAttemptRepo, nrtRepo);
  }
}
