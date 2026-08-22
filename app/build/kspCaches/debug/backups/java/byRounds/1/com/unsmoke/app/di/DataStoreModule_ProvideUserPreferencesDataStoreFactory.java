package com.unsmoke.app.di;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DataStoreModule_ProvideUserPreferencesDataStoreFactory implements Factory<UserPreferencesDataStore> {
  private final Provider<DataStore<Preferences>> dataStoreProvider;

  public DataStoreModule_ProvideUserPreferencesDataStoreFactory(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public UserPreferencesDataStore get() {
    return provideUserPreferencesDataStore(dataStoreProvider.get());
  }

  public static DataStoreModule_ProvideUserPreferencesDataStoreFactory create(
      Provider<DataStore<Preferences>> dataStoreProvider) {
    return new DataStoreModule_ProvideUserPreferencesDataStoreFactory(dataStoreProvider);
  }

  public static UserPreferencesDataStore provideUserPreferencesDataStore(
      DataStore<Preferences> dataStore) {
    return Preconditions.checkNotNullFromProvides(DataStoreModule.INSTANCE.provideUserPreferencesDataStore(dataStore));
  }
}
