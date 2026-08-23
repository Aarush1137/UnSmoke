package com.unsmoke.app.feature.nrt;

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
public final class NRTViewModel_Factory implements Factory<NRTViewModel> {
  private final Provider<NRTRepository> nrtRepoProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  public NRTViewModel_Factory(Provider<NRTRepository> nrtRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.nrtRepoProvider = nrtRepoProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  @Override
  public NRTViewModel get() {
    return newInstance(nrtRepoProvider.get(), quitAttemptRepoProvider.get());
  }

  public static NRTViewModel_Factory create(Provider<NRTRepository> nrtRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new NRTViewModel_Factory(nrtRepoProvider, quitAttemptRepoProvider);
  }

  public static NRTViewModel newInstance(NRTRepository nrtRepo,
      QuitAttemptRepository quitAttemptRepo) {
    return new NRTViewModel(nrtRepo, quitAttemptRepo);
  }
}
