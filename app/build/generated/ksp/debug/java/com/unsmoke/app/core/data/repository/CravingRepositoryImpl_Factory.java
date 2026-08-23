package com.unsmoke.app.core.data.repository;

import com.unsmoke.app.core.data.database.dao.CravingDao;
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
public final class CravingRepositoryImpl_Factory implements Factory<CravingRepositoryImpl> {
  private final Provider<CravingDao> daoProvider;

  public CravingRepositoryImpl_Factory(Provider<CravingDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public CravingRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static CravingRepositoryImpl_Factory create(Provider<CravingDao> daoProvider) {
    return new CravingRepositoryImpl_Factory(daoProvider);
  }

  public static CravingRepositoryImpl newInstance(CravingDao dao) {
    return new CravingRepositoryImpl(dao);
  }
}
