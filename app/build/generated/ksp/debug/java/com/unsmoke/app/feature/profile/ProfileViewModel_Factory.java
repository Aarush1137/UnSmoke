package com.unsmoke.app.feature.profile;

import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<CravingRepository> cravingRepoProvider;

  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public ProfileViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.cravingRepoProvider = cravingRepoProvider;
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(quitAttemptRepoProvider.get(), cravingRepoProvider.get(), dataStoreProvider.get());
  }

  public static ProfileViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new ProfileViewModel_Factory(quitAttemptRepoProvider, cravingRepoProvider, dataStoreProvider);
  }

  public static ProfileViewModel newInstance(QuitAttemptRepository quitAttemptRepo,
      CravingRepository cravingRepo, UserPreferencesDataStore dataStore) {
    return new ProfileViewModel(quitAttemptRepo, cravingRepo, dataStore);
  }
}
