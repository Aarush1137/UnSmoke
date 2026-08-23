package com.unsmoke.app.di;

import com.unsmoke.app.core.data.database.UnSmokeDatabase;
import com.unsmoke.app.core.data.database.dao.CravingDao;
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
public final class DatabaseModule_ProvideCravingDaoFactory implements Factory<CravingDao> {
  private final Provider<UnSmokeDatabase> dbProvider;

  public DatabaseModule_ProvideCravingDaoFactory(Provider<UnSmokeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CravingDao get() {
    return provideCravingDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCravingDaoFactory create(
      Provider<UnSmokeDatabase> dbProvider) {
    return new DatabaseModule_ProvideCravingDaoFactory(dbProvider);
  }

  public static CravingDao provideCravingDao(UnSmokeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCravingDao(db));
  }
}
