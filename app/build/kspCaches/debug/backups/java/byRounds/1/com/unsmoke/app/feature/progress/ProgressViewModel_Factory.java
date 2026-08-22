package com.unsmoke.app.feature.progress;

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
public final class ProgressViewModel_Factory implements Factory<ProgressViewModel> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<CravingRepository> cravingRepoProvider;

  private final Provider<NRTRepository> nrtRepoProvider;

  public ProgressViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider, Provider<NRTRepository> nrtRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.cravingRepoProvider = cravingRepoProvider;
    this.nrtRepoProvider = nrtRepoProvider;
  }

  @Override
  public ProgressViewModel get() {
    return newInstance(quitAttemptRepoProvider.get(), cravingRepoProvider.get(), nrtRepoProvider.get());
  }

  public static ProgressViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider, Provider<NRTRepository> nrtRepoProvider) {
    return new ProgressViewModel_Factory(quitAttemptRepoProvider, cravingRepoProvider, nrtRepoProvider);
  }

  public static ProgressViewModel newInstance(QuitAttemptRepository quitAttemptRepo,
      CravingRepository cravingRepo, NRTRepository nrtRepo) {
    return new ProgressViewModel(quitAttemptRepo, cravingRepo, nrtRepo);
  }
}
