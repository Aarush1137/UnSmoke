package com.unsmoke.app.core.data.repository;

import com.unsmoke.app.core.data.database.dao.NRTDao;
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
public final class NRTRepositoryImpl_Factory implements Factory<NRTRepositoryImpl> {
  private final Provider<NRTDao> daoProvider;

  public NRTRepositoryImpl_Factory(Provider<NRTDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public NRTRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static NRTRepositoryImpl_Factory create(Provider<NRTDao> daoProvider) {
    return new NRTRepositoryImpl_Factory(daoProvider);
  }

  public static NRTRepositoryImpl newInstance(NRTDao dao) {
    return new NRTRepositoryImpl(dao);
  }
}
