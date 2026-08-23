package com.unsmoke.app.feature.settings;

import com.unsmoke.app.core.data.database.UnSmokeDatabase;
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
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

  private final Provider<UnSmokeDatabase> databaseProvider;

  public SettingsViewModel_Factory(Provider<UserPreferencesDataStore> dataStoreProvider,
      Provider<UnSmokeDatabase> databaseProvider) {
    this.dataStoreProvider = dataStoreProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(dataStoreProvider.get(), databaseProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<UserPreferencesDataStore> dataStoreProvider,
      Provider<UnSmokeDatabase> databaseProvider) {
    return new SettingsViewModel_Factory(dataStoreProvider, databaseProvider);
  }

  public static SettingsViewModel newInstance(UserPreferencesDataStore dataStore,
      UnSmokeDatabase database) {
    return new SettingsViewModel(dataStore, database);
  }
}
