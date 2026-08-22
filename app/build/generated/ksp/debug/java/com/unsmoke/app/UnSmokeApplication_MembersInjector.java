package com.unsmoke.app;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class UnSmokeApplication_MembersInjector implements MembersInjector<UnSmokeApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public UnSmokeApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<UnSmokeApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new UnSmokeApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(UnSmokeApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.unsmoke.app.UnSmokeApplication.workerFactory")
  public static void injectWorkerFactory(UnSmokeApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
