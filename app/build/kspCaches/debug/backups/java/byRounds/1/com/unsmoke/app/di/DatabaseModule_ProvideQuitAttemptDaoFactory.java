package com.unsmoke.app.di;

import com.unsmoke.app.core.data.database.UnSmokeDatabase;
import com.unsmoke.app.core.data.database.dao.QuitAttemptDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideQuitAttemptDaoFactory implements Factory<QuitAttemptDao> {
  private final Provider<UnSmokeDatabase> dbProvider;

  public DatabaseModule_ProvideQuitAttemptDaoFactory(Provider<UnSmokeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public QuitAttemptDao get() {
    return provideQuitAttemptDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideQuitAttemptDaoFactory create(
      Provider<UnSmokeDatabase> dbProvider) {
    return new DatabaseModule_ProvideQuitAttemptDaoFactory(dbProvider);
  }

  public static QuitAttemptDao provideQuitAttemptDao(UnSmokeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideQuitAttemptDao(db));
  }
}
