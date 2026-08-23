package com.unsmoke.app.core.domain.repository;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AiInsightsRepository_Factory implements Factory<AiInsightsRepository> {
  @Override
  public AiInsightsRepository get() {
    return newInstance();
  }

  public static AiInsightsRepository_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AiInsightsRepository newInstance() {
    return new AiInsightsRepository();
  }

  private static final class InstanceHolder {
    private static final AiInsightsRepository_Factory INSTANCE = new AiInsightsRepository_Factory();
  }
}
