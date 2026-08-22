package com.unsmoke.app.feature.progress;

import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
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

  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public ProgressViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider, Provider<NRTRepository> nrtRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.cravingRepoProvider = cravingRepoProvider;
    this.nrtRepoProvider = nrtRepoProvider;
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public ProgressViewModel get() {
    return newInstance(quitAttemptRepoProvider.get(), cravingRepoProvider.get(), nrtRepoProvider.get(), dataStoreProvider.get());
  }

  public static ProgressViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider, Provider<NRTRepository> nrtRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new ProgressViewModel_Factory(quitAttemptRepoProvider, cravingRepoProvider, nrtRepoProvider, dataStoreProvider);
  }

  public static ProgressViewModel newInstance(QuitAttemptRepository quitAttemptRepo,
      CravingRepository cravingRepo, NRTRepository nrtRepo, UserPreferencesDataStore dataStore) {
    return new ProgressViewModel(quitAttemptRepo, cravingRepo, nrtRepo, dataStore);
  }
}
