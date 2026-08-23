package com.unsmoke.app.feature.achievements;

import com.unsmoke.app.core.domain.repository.CravingRepository;
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
public final class AchievementsViewModel_Factory implements Factory<AchievementsViewModel> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<CravingRepository> cravingRepoProvider;

  public AchievementsViewModel_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.cravingRepoProvider = cravingRepoProvider;
  }

  @Override
  public AchievementsViewModel get() {
    return newInstance(quitAttemptRepoProvider.get(), cravingRepoProvider.get());
  }

  public static AchievementsViewModel_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<CravingRepository> cravingRepoProvider) {
    return new AchievementsViewModel_Factory(quitAttemptRepoProvider, cravingRepoProvider);
  }

  public static AchievementsViewModel newInstance(QuitAttemptRepository quitAttemptRepo,
      CravingRepository cravingRepo) {
    return new AchievementsViewModel(quitAttemptRepo, cravingRepo);
  }
}
