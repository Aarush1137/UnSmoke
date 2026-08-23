package com.unsmoke.app.feature.home;

import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
import com.unsmoke.app.core.domain.repository.AiInsightsRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<NRTRepository> nrtRepoProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<CravingRepository> cravingRepoProvider;

  private final Provider<AiInsightsRepository> aiRepoProvider;

  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public HomeViewModel_Factory(Provider<NRTRepository> nrtRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider,
      Provider<AiInsightsRepository> aiRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.nrtRepoProvider = nrtRepoProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.cravingRepoProvider = cravingRepoProvider;
    this.aiRepoProvider = aiRepoProvider;
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(nrtRepoProvider.get(), quitAttemptRepoProvider.get(), cravingRepoProvider.get(), aiRepoProvider.get(), dataStoreProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<NRTRepository> nrtRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider,
      Provider<AiInsightsRepository> aiRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new HomeViewModel_Factory(nrtRepoProvider, quitAttemptRepoProvider, cravingRepoProvider, aiRepoProvider, dataStoreProvider);
  }

  public static HomeViewModel newInstance(NRTRepository nrtRepo,
      QuitAttemptRepository quitAttemptRepo, CravingRepository cravingRepo,
      AiInsightsRepository aiRepo, UserPreferencesDataStore dataStore) {
    return new HomeViewModel(nrtRepo, quitAttemptRepo, cravingRepo, aiRepo, dataStore);
  }
}
