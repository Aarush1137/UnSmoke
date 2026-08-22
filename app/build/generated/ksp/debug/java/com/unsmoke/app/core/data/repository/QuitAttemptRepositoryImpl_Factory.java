package com.unsmoke.app.core.data.repository;

import com.unsmoke.app.core.data.database.dao.QuitAttemptDao;
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
public final class QuitAttemptRepositoryImpl_Factory implements Factory<QuitAttemptRepositoryImpl> {
  private final Provider<QuitAttemptDao> daoProvider;

  public QuitAttemptRepositoryImpl_Factory(Provider<QuitAttemptDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public QuitAttemptRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static QuitAttemptRepositoryImpl_Factory create(Provider<QuitAttemptDao> daoProvider) {
    return new QuitAttemptRepositoryImpl_Factory(daoProvider);
  }

  public static QuitAttemptRepositoryImpl newInstance(QuitAttemptDao dao) {
    return new QuitAttemptRepositoryImpl(dao);
  }
}
