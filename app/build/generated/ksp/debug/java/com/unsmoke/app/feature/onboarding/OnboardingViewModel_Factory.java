package com.unsmoke.app.feature.onboarding;

import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<NRTRepository> nrtRepoProvider;

  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public OnboardingViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<NRTRepository> nrtRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.nrtRepoProvider = nrtRepoProvider;
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(quitAttemptRepoProvider.get(), nrtRepoProvider.get(), dataStoreProvider.get());
  }

  public static OnboardingViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<NRTRepository> nrtRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new OnboardingViewModel_Factory(quitAttemptRepoProvider, nrtRepoProvider, dataStoreProvider);
  }

  public static OnboardingViewModel newInstance(QuitAttemptRepository quitAttemptRepo,
      NRTRepository nrtRepo, UserPreferencesDataStore dataStore) {
    return new OnboardingViewModel(quitAttemptRepo, nrtRepo, dataStore);
  }
}
