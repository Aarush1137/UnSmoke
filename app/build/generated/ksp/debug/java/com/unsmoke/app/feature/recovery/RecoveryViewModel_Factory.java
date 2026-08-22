package com.unsmoke.app.feature.recovery;

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
public final class RecoveryViewModel_Factory implements Factory<RecoveryViewModel> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  public RecoveryViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  @Override
  public RecoveryViewModel get() {
    return newInstance(quitAttemptRepoProvider.get());
  }

  public static RecoveryViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new RecoveryViewModel_Factory(quitAttemptRepoProvider);
  }

  public static RecoveryViewModel newInstance(QuitAttemptRepository quitAttemptRepo) {
    return new RecoveryViewModel(quitAttemptRepo);
  }
}
