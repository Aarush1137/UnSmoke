package com.unsmoke.app.di;

import android.content.Context;
import com.unsmoke.app.core.data.database.UnSmokeDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideUnSmokeDatabaseFactory implements Factory<UnSmokeDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideUnSmokeDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public UnSmokeDatabase get() {
    return provideUnSmokeDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideUnSmokeDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideUnSmokeDatabaseFactory(contextProvider);
  }

  public static UnSmokeDatabase provideUnSmokeDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideUnSmokeDatabase(context));
  }
}
