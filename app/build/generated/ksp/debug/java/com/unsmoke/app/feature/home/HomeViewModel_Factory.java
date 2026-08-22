package com.unsmoke.app.feature.home;

import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
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
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public HomeViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(quitAttemptRepoProvider.get(), dataStoreProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new HomeViewModel_Factory(quitAttemptRepoProvider, dataStoreProvider);
  }

  public static HomeViewModel newInstance(QuitAttemptRepository quitAttemptRepo,
      UserPreferencesDataStore dataStore) {
    return new HomeViewModel(quitAttemptRepo, dataStore);
  }
}
