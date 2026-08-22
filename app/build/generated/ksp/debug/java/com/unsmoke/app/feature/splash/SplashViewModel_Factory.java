package com.unsmoke.app.feature.splash;

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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<UserPreferencesDataStore> preferencesProvider;

  public SplashViewModel_Factory(Provider<UserPreferencesDataStore> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(preferencesProvider.get());
  }

  public static SplashViewModel_Factory create(
      Provider<UserPreferencesDataStore> preferencesProvider) {
    return new SplashViewModel_Factory(preferencesProvider);
  }

  public static SplashViewModel newInstance(UserPreferencesDataStore preferences) {
    return new SplashViewModel(preferences);
  }
}
