package com.unsmoke.app.core.notification;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.unsmoke.app.core.domain.repository.QuitAttemptRepository;
import dagger.internal.DaggerGenerated;
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
public final class NotificationWorker_Factory {
  private final Provider<QuitAttemptRepository> quitAttemptRepoProvider;

  public NotificationWorker_Factory(Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    this.quitAttemptRepoProvider = quitAttemptRepoProvider;
  }

  public NotificationWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, quitAttemptRepoProvider.get());
  }

  public static NotificationWorker_Factory create(
      Provider<QuitAttemptRepository> quitAttemptRepoProvider) {
    return new NotificationWorker_Factory(quitAttemptRepoProvider);
  }

  public static NotificationWorker newInstance(Context context, WorkerParameters workerParams,
      QuitAttemptRepository quitAttemptRepo) {
    return new NotificationWorker(context, workerParams, quitAttemptRepo);
  }
}
