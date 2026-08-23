package com.unsmoke.app.feature.settings;

import com.unsmoke.app.core.data.database.UnSmokeDatabase;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  private final Provider<CravingRepository> cravingRepoProvider;

  private final Provider<NRTRepository> nrtRepoProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<UnSmokeDatabase> databaseProvider;

  public SettingsViewModel_Factory(Provider<UserPreferencesDataStore> dataStoreProvider,
      Provider<CravingRepository> cravingRepoProvider, Provider<NRTRepository> nrtRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<UnSmokeDatabase> databaseProvider) {
    this.dataStoreProvider = dataStoreProvider;
    this.cravingRepoProvider = cravingRepoProvider;
    this.nrtRepoProvider = nrtRepoProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(dataStoreProvider.get(), cravingRepoProvider.get(), nrtRepoProvider.get(), quitAttemptRepoProvider.get(), databaseProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<UserPreferencesDataStore> dataStoreProvider,
      Provider<CravingRepository> cravingRepoProvider, Provider<NRTRepository> nrtRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<UnSmokeDatabase> databaseProvider) {
    return new SettingsViewModel_Factory(dataStoreProvider, cravingRepoProvider, nrtRepoProvider, quitAttemptRepoProvider, databaseProvider);
  }

  public static SettingsViewModel newInstance(UserPreferencesDataStore dataStore,
      CravingRepository cravingRepo, NRTRepository nrtRepo, QuitAttemptRepository quitAttemptRepo,
      UnSmokeDatabase database) {
    return new SettingsViewModel(dataStore, cravingRepo, nrtRepo, quitAttemptRepo, database);
  }
}
