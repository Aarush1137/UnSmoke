package com.unsmoke.app.core.data.repository;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class HealthConnectRepository_Factory implements Factory<HealthConnectRepository> {
  private final Provider<Context> contextProvider;

  public HealthConnectRepository_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HealthConnectRepository get() {
    return newInstance(contextProvider.get());
  }

  public static HealthConnectRepository_Factory create(Provider<Context> contextProvider) {
    return new HealthConnectRepository_Factory(contextProvider);
  }

  public static HealthConnectRepository newInstance(Context context) {
    return new HealthConnectRepository(context);
  }
}
