package com.unsmoke.app;

import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public MainActivity_MembersInjector(Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new MainActivity_MembersInjector(dataStoreProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectDataStore(instance, dataStoreProvider.get());
  }

  @InjectedFieldSignature("com.unsmoke.app.MainActivity.dataStore")
  public static void injectDataStore(MainActivity instance, UserPreferencesDataStore dataStore) {
    instance.dataStore = dataStore;
  }
}
