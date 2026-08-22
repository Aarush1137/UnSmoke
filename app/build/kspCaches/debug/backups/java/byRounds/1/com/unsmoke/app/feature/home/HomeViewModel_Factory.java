package com.unsmoke.app.feature.home;

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

  public HomeViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(quitAttemptRepoProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new HomeViewModel_Factory(quitAttemptRepoProvider);
  }

  public static HomeViewModel newInstance(QuitAttemptRepository quitAttemptRepo) {
    return new HomeViewModel(quitAttemptRepo);
  }
}
