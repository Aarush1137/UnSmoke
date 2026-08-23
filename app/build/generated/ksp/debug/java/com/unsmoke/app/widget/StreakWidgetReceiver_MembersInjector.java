package com.unsmoke.app.widget;

import com.unsmoke.app.core.domain.repository.QuitAttemptRepository;
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
public final class StreakWidgetReceiver_MembersInjector implements MembersInjector<StreakWidgetReceiver> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  public StreakWidgetReceiver_MembersInjector(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  public static MembersInjector<StreakWidgetReceiver> create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new StreakWidgetReceiver_MembersInjector(quitAttemptRepoProvider);
  }

  @Override
  public void injectMembers(StreakWidgetReceiver instance) {
    injectQuitAttemptRepo(instance, quitAttemptRepoProvider.get());
  }

  @InjectedFieldSignature("com.unsmoke.app.widget.StreakWidgetReceiver.quitAttemptRepo")
  public static void injectQuitAttemptRepo(StreakWidgetReceiver instance,
      QuitAttemptRepository quitAttemptRepo) {
    instance.quitAttemptRepo = quitAttemptRepo;
  }
}
