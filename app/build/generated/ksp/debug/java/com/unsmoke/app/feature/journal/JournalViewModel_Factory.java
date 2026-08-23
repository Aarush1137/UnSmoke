package com.unsmoke.app.feature.journal;

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
public final class JournalViewModel_Factory implements Factory<JournalViewModel> {
  private final Provider<CheckInRepository> checkInRepoProvider;

  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  public JournalViewModel_Factory(Provider<CheckInRepository> checkInRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.checkInRepoProvider = checkInRepoProvider;
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  @Override
  public JournalViewModel get() {
    return newInstance(checkInRepoProvider.get(), quitAttemptRepoProvider.get());
  }

  public static JournalViewModel_Factory create(Provider<CheckInRepository> checkInRepoProvider,
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new JournalViewModel_Factory(checkInRepoProvider, quitAttemptRepoProvider);
  }

  public static JournalViewModel newInstance(CheckInRepository checkInRepo,
      QuitAttemptRepository quitAttemptRepo) {
    return new JournalViewModel(checkInRepo, quitAttemptRepo);
  }
}
