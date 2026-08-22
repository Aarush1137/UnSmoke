package com.unsmoke.app.feature.craving;

import com.unsmoke.app.core.domain.repository.CravingRepository;
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
public final class CravingViewModel_Factory implements Factory<CravingViewModel> {
  private final Provider<CravingRepository> cravingRepoProvider;

  public CravingViewModel_Factory(Provider<CravingRepository> cravingRepoProvider) {
    this.cravingRepoProvider = cravingRepoProvider;
  }

  @Override
  public CravingViewModel get() {
    return newInstance(cravingRepoProvider.get());
  }

  public static CravingViewModel_Factory create(Provider<CravingRepository> cravingRepoProvider) {
    return new CravingViewModel_Factory(cravingRepoProvider);
  }

  public static CravingViewModel newInstance(CravingRepository cravingRepo) {
    return new CravingViewModel(cravingRepo);
  }
}
