package com.unsmoke.app.core.data.repository;

import com.unsmoke.app.core.data.database.dao.CheckInDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class CheckInRepositoryImpl_Factory implements Factory<CheckInRepositoryImpl> {
  private final Provider<CheckInDao> daoProvider;

  public CheckInRepositoryImpl_Factory(Provider<CheckInDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public CheckInRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static CheckInRepositoryImpl_Factory create(Provider<CheckInDao> daoProvider) {
    return new CheckInRepositoryImpl_Factory(daoProvider);
  }

  public static CheckInRepositoryImpl newInstance(CheckInDao dao) {
    return new CheckInRepositoryImpl(dao);
  }
}
