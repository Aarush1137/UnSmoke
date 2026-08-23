package com.unsmoke.app.feature.checkin;

import com.unsmoke.app.core.domain.repository.CheckInRepository;
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
public final class CheckInViewModel_Factory implements Factory<CheckInViewModel> {
  private final Provider<CheckInRepository> checkInRepoProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  public CheckInViewModel_Factory(Provider<CheckInRepository> checkInRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.checkInRepoProvider = checkInRepoProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  @Override
  public CheckInViewModel get() {
    return newInstance(checkInRepoProvider.get(), quitAttemptRepoProvider.get());
  }

  public static CheckInViewModel_Factory create(Provider<CheckInRepository> checkInRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new CheckInViewModel_Factory(checkInRepoProvider, quitAttemptRepoProvider);
  }

  public static CheckInViewModel newInstance(CheckInRepository checkInRepo,
      QuitAttemptRepository quitAttemptRepo) {
    return new CheckInViewModel(checkInRepo, quitAttemptRepo);
  }
}
