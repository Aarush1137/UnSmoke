package com.unsmoke.app.feature.plan;

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
public final class PlanViewModel_Factory implements Factory<PlanViewModel> {
  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public PlanViewModel_Factory(Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public PlanViewModel get() {
    return newInstance(dataStoreProvider.get());
  }

  public static PlanViewModel_Factory create(Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new PlanViewModel_Factory(dataStoreProvider);
  }

  public static PlanViewModel newInstance(UserPreferencesDataStore dataStore) {
    return new PlanViewModel(dataStore);
  }
}
