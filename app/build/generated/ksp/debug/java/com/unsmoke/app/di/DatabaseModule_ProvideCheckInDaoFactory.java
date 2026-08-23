package com.unsmoke.app.di;

import com.unsmoke.app.core.data.database.UnSmokeDatabase;
import com.unsmoke.app.core.data.database.dao.CheckInDao;
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
public final class DatabaseModule_ProvideCheckInDaoFactory implements Factory<CheckInDao> {
  private final Provider<UnSmokeDatabase> dbProvider;

  public DatabaseModule_ProvideCheckInDaoFactory(Provider<UnSmokeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CheckInDao get() {
    return provideCheckInDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCheckInDaoFactory create(
      Provider<UnSmokeDatabase> dbProvider) {
    return new DatabaseModule_ProvideCheckInDaoFactory(dbProvider);
  }

  public static CheckInDao provideCheckInDao(UnSmokeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCheckInDao(db));
  }
}
