package com.unsmoke.app.widget;

import com.unsmoke.app.core.domain.repository.NRTRepository;
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
public final class DashboardWidgetReceiver_MembersInjector implements MembersInjector<DashboardWidgetReceiver> {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  private final Provider<NRTRepository> nrtRepoProvider;

  public DashboardWidgetReceiver_MembersInjector(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<NRTRepository> nrtRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
    this.nrtRepoProvider = nrtRepoProvider;
  }

  public static MembersInjector<DashboardWidgetReceiver> create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider,
      Provider<NRTRepository> nrtRepoProvider) {
    return new DashboardWidgetReceiver_MembersInjector(quitAttemptRepoProvider, nrtRepoProvider);
  }

  @Override
  public void injectMembers(DashboardWidgetReceiver instance) {
    injectQuitAttemptRepo(instance, quitAttemptRepoProvider.get());
    injectNrtRepo(instance, nrtRepoProvider.get());
  }

  @InjectedFieldSignature("com.unsmoke.app.widget.DashboardWidgetReceiver.quitAttemptRepo")
  public static void injectQuitAttemptRepo(DashboardWidgetReceiver instance,
      QuitAttemptRepository quitAttemptRepo) {
    instance.quitAttemptRepo = quitAttemptRepo;
  }

  @InjectedFieldSignature("com.unsmoke.app.widget.DashboardWidgetReceiver.nrtRepo")
  public static void injectNrtRepo(DashboardWidgetReceiver instance, NRTRepository nrtRepo) {
    instance.nrtRepo = nrtRepo;
  }
}
