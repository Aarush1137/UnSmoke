package com.unsmoke.app.feature.progress;

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
public final class ProgressViewModel_Factory implements Factory<ProgressViewModel> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  public ProgressViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  @Override
  public ProgressViewModel get() {
    return newInstance(quitAttemptRepoProvider.get());
  }

  public static ProgressViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new ProgressViewModel_Factory(quitAttemptRepoProvider);
  }

  public static ProgressViewModel newInstance(QuitAttemptRepository quitAttemptRepo) {
    return new ProgressViewModel(quitAttemptRepo);
  }
}
