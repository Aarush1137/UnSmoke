package com.unsmoke.app.feature.nrt;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class NRTViewModel_Factory implements Factory<NRTViewModel> {
  @Override
  public NRTViewModel get() {
    return newInstance();
  }

  public static NRTViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static NRTViewModel newInstance() {
    return new NRTViewModel();
  }

  private static final class InstanceHolder {
    private static final NRTViewModel_Factory INSTANCE = new NRTViewModel_Factory();
  }
}
