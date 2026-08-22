package com.unsmoke.app.di;

import com.unsmoke.app.core.data.database.UnSmokeDatabase;
import com.unsmoke.app.core.data.database.dao.NRTDao;
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
public final class DatabaseModule_ProvideNRTDaoFactory implements Factory<NRTDao> {
  private final Provider<UnSmokeDatabase> dbProvider;

  public DatabaseModule_ProvideNRTDaoFactory(Provider<UnSmokeDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public NRTDao get() {
    return provideNRTDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideNRTDaoFactory create(Provider<UnSmokeDatabase> dbProvider) {
    return new DatabaseModule_ProvideNRTDaoFactory(dbProvider);
  }

  public static NRTDao provideNRTDao(UnSmokeDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideNRTDao(db));
  }
}
