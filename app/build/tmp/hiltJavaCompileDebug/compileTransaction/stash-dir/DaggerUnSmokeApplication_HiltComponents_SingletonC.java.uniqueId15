package com.unsmoke.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import com.unsmoke.app.core.data.database.UnSmokeDatabase;
import com.unsmoke.app.core.data.database.dao.CheckInDao;
import com.unsmoke.app.core.data.database.dao.CravingDao;
import com.unsmoke.app.core.data.database.dao.NRTDao;
import com.unsmoke.app.core.data.database.dao.QuitAttemptDao;
import com.unsmoke.app.core.data.datastore.UserPreferencesDataStore;
import com.unsmoke.app.core.data.repository.CheckInRepositoryImpl;
import com.unsmoke.app.core.data.repository.CravingRepositoryImpl;
import com.unsmoke.app.core.data.repository.NRTRepositoryImpl;
import com.unsmoke.app.core.data.repository.QuitAttemptRepositoryImpl;
import com.unsmoke.app.di.DataStoreModule_ProvideDataStoreFactory;
import com.unsmoke.app.di.DataStoreModule_ProvideUserPreferencesDataStoreFactory;
import com.unsmoke.app.di.DatabaseModule_ProvideCheckInDaoFactory;
import com.unsmoke.app.di.DatabaseModule_ProvideCravingDaoFactory;
import com.unsmoke.app.di.DatabaseModule_ProvideNRTDaoFactory;
import com.unsmoke.app.di.DatabaseModule_ProvideQuitAttemptDaoFactory;
import com.unsmoke.app.di.DatabaseModule_ProvideUnSmokeDatabaseFactory;
import com.unsmoke.app.feature.achievements.AchievementsViewModel;
import com.unsmoke.app.feature.achievements.AchievementsViewModel_HiltModules;
import com.unsmoke.app.feature.checkin.CheckInViewModel;
import com.unsmoke.app.feature.checkin.CheckInViewModel_HiltModules;
import com.unsmoke.app.feature.craving.CravingViewModel;
import com.unsmoke.app.feature.craving.CravingViewModel_HiltModules;
import com.unsmoke.app.feature.home.HomeViewModel;
import com.unsmoke.app.feature.home.HomeViewModel_HiltModules;
import com.unsmoke.app.feature.insights.InsightsViewModel;
import com.unsmoke.app.feature.insights.InsightsViewModel_HiltModules;
import com.unsmoke.app.feature.journal.JournalViewModel;
import com.unsmoke.app.feature.journal.JournalViewModel_HiltModules;
import com.unsmoke.app.feature.nrt.NRTViewModel;
import com.unsmoke.app.feature.nrt.NRTViewModel_HiltModules;
import com.unsmoke.app.feature.onboarding.OnboardingViewModel;
import com.unsmoke.app.feature.onboarding.OnboardingViewModel_HiltModules;
import com.unsmoke.app.feature.profile.ProfileViewModel;
import com.unsmoke.app.feature.profile.ProfileViewModel_HiltModules;
import com.unsmoke.app.feature.progress.ProgressViewModel;
import com.unsmoke.app.feature.progress.ProgressViewModel_HiltModules;
import com.unsmoke.app.feature.recovery.RecoveryViewModel;
import com.unsmoke.app.feature.recovery.RecoveryViewModel_HiltModules;
import com.unsmoke.app.feature.settings.SettingsViewModel;
import com.unsmoke.app.feature.settings.SettingsViewModel_HiltModules;
import com.unsmoke.app.feature.splash.SplashViewModel;
import com.unsmoke.app.feature.splash.SplashViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerUnSmokeApplication_HiltComponents_SingletonC {
  private DaggerUnSmokeApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public UnSmokeApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements UnSmokeApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public UnSmokeApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements UnSmokeApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public UnSmokeApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements UnSmokeApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public UnSmokeApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements UnSmokeApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public UnSmokeApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements UnSmokeApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public UnSmokeApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements UnSmokeApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public UnSmokeApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements UnSmokeApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public UnSmokeApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends UnSmokeApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends UnSmokeApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends UnSmokeApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends UnSmokeApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(13).put(LazyClassKeyProvider.com_unsmoke_app_feature_achievements_AchievementsViewModel, AchievementsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_checkin_CheckInViewModel, CheckInViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_craving_CravingViewModel, CravingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_insights_InsightsViewModel, InsightsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_journal_JournalViewModel, JournalViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_nrt_NRTViewModel, NRTViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_onboarding_OnboardingViewModel, OnboardingViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_profile_ProfileViewModel, ProfileViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_progress_ProgressViewModel, ProgressViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_recovery_RecoveryViewModel, RecoveryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_unsmoke_app_feature_splash_SplashViewModel, SplashViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectDataStore(instance, singletonCImpl.provideUserPreferencesDataStoreProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_unsmoke_app_feature_progress_ProgressViewModel = "com.unsmoke.app.feature.progress.ProgressViewModel";

      static String com_unsmoke_app_feature_checkin_CheckInViewModel = "com.unsmoke.app.feature.checkin.CheckInViewModel";

      static String com_unsmoke_app_feature_insights_InsightsViewModel = "com.unsmoke.app.feature.insights.InsightsViewModel";

      static String com_unsmoke_app_feature_settings_SettingsViewModel = "com.unsmoke.app.feature.settings.SettingsViewModel";

      static String com_unsmoke_app_feature_splash_SplashViewModel = "com.unsmoke.app.feature.splash.SplashViewModel";

      static String com_unsmoke_app_feature_recovery_RecoveryViewModel = "com.unsmoke.app.feature.recovery.RecoveryViewModel";

      static String com_unsmoke_app_feature_profile_ProfileViewModel = "com.unsmoke.app.feature.profile.ProfileViewModel";

      static String com_unsmoke_app_feature_journal_JournalViewModel = "com.unsmoke.app.feature.journal.JournalViewModel";

      static String com_unsmoke_app_feature_nrt_NRTViewModel = "com.unsmoke.app.feature.nrt.NRTViewModel";

      static String com_unsmoke_app_feature_home_HomeViewModel = "com.unsmoke.app.feature.home.HomeViewModel";

      static String com_unsmoke_app_feature_achievements_AchievementsViewModel = "com.unsmoke.app.feature.achievements.AchievementsViewModel";

      static String com_unsmoke_app_feature_onboarding_OnboardingViewModel = "com.unsmoke.app.feature.onboarding.OnboardingViewModel";

      static String com_unsmoke_app_feature_craving_CravingViewModel = "com.unsmoke.app.feature.craving.CravingViewModel";

      @KeepFieldType
      ProgressViewModel com_unsmoke_app_feature_progress_ProgressViewModel2;

      @KeepFieldType
      CheckInViewModel com_unsmoke_app_feature_checkin_CheckInViewModel2;

      @KeepFieldType
      InsightsViewModel com_unsmoke_app_feature_insights_InsightsViewModel2;

      @KeepFieldType
      SettingsViewModel com_unsmoke_app_feature_settings_SettingsViewModel2;

      @KeepFieldType
      SplashViewModel com_unsmoke_app_feature_splash_SplashViewModel2;

      @KeepFieldType
      RecoveryViewModel com_unsmoke_app_feature_recovery_RecoveryViewModel2;

      @KeepFieldType
      ProfileViewModel com_unsmoke_app_feature_profile_ProfileViewModel2;

      @KeepFieldType
      JournalViewModel com_unsmoke_app_feature_journal_JournalViewModel2;

      @KeepFieldType
      NRTViewModel com_unsmoke_app_feature_nrt_NRTViewModel2;

      @KeepFieldType
      HomeViewModel com_unsmoke_app_feature_home_HomeViewModel2;

      @KeepFieldType
      AchievementsViewModel com_unsmoke_app_feature_achievements_AchievementsViewModel2;

      @KeepFieldType
      OnboardingViewModel com_unsmoke_app_feature_onboarding_OnboardingViewModel2;

      @KeepFieldType
      CravingViewModel com_unsmoke_app_feature_craving_CravingViewModel2;
    }
  }

  private static final class ViewModelCImpl extends UnSmokeApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AchievementsViewModel> achievementsViewModelProvider;

    private Provider<CheckInViewModel> checkInViewModelProvider;

    private Provider<CravingViewModel> cravingViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<InsightsViewModel> insightsViewModelProvider;

    private Provider<JournalViewModel> journalViewModelProvider;

    private Provider<NRTViewModel> nRTViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<ProgressViewModel> progressViewModelProvider;

    private Provider<RecoveryViewModel> recoveryViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.achievementsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.checkInViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.cravingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.insightsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.journalViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.nRTViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.progressViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.recoveryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(13).put(LazyClassKeyProvider.com_unsmoke_app_feature_achievements_AchievementsViewModel, ((Provider) achievementsViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_checkin_CheckInViewModel, ((Provider) checkInViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_craving_CravingViewModel, ((Provider) cravingViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_insights_InsightsViewModel, ((Provider) insightsViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_journal_JournalViewModel, ((Provider) journalViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_nrt_NRTViewModel, ((Provider) nRTViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_onboarding_OnboardingViewModel, ((Provider) onboardingViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_profile_ProfileViewModel, ((Provider) profileViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_progress_ProgressViewModel, ((Provider) progressViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_recovery_RecoveryViewModel, ((Provider) recoveryViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_unsmoke_app_feature_splash_SplashViewModel, ((Provider) splashViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_unsmoke_app_feature_settings_SettingsViewModel = "com.unsmoke.app.feature.settings.SettingsViewModel";

      static String com_unsmoke_app_feature_recovery_RecoveryViewModel = "com.unsmoke.app.feature.recovery.RecoveryViewModel";

      static String com_unsmoke_app_feature_progress_ProgressViewModel = "com.unsmoke.app.feature.progress.ProgressViewModel";

      static String com_unsmoke_app_feature_craving_CravingViewModel = "com.unsmoke.app.feature.craving.CravingViewModel";

      static String com_unsmoke_app_feature_splash_SplashViewModel = "com.unsmoke.app.feature.splash.SplashViewModel";

      static String com_unsmoke_app_feature_journal_JournalViewModel = "com.unsmoke.app.feature.journal.JournalViewModel";

      static String com_unsmoke_app_feature_profile_ProfileViewModel = "com.unsmoke.app.feature.profile.ProfileViewModel";

      static String com_unsmoke_app_feature_insights_InsightsViewModel = "com.unsmoke.app.feature.insights.InsightsViewModel";

      static String com_unsmoke_app_feature_achievements_AchievementsViewModel = "com.unsmoke.app.feature.achievements.AchievementsViewModel";

      static String com_unsmoke_app_feature_home_HomeViewModel = "com.unsmoke.app.feature.home.HomeViewModel";

      static String com_unsmoke_app_feature_checkin_CheckInViewModel = "com.unsmoke.app.feature.checkin.CheckInViewModel";

      static String com_unsmoke_app_feature_onboarding_OnboardingViewModel = "com.unsmoke.app.feature.onboarding.OnboardingViewModel";

      static String com_unsmoke_app_feature_nrt_NRTViewModel = "com.unsmoke.app.feature.nrt.NRTViewModel";

      @KeepFieldType
      SettingsViewModel com_unsmoke_app_feature_settings_SettingsViewModel2;

      @KeepFieldType
      RecoveryViewModel com_unsmoke_app_feature_recovery_RecoveryViewModel2;

      @KeepFieldType
      ProgressViewModel com_unsmoke_app_feature_progress_ProgressViewModel2;

      @KeepFieldType
      CravingViewModel com_unsmoke_app_feature_craving_CravingViewModel2;

      @KeepFieldType
      SplashViewModel com_unsmoke_app_feature_splash_SplashViewModel2;

      @KeepFieldType
      JournalViewModel com_unsmoke_app_feature_journal_JournalViewModel2;

      @KeepFieldType
      ProfileViewModel com_unsmoke_app_feature_profile_ProfileViewModel2;

      @KeepFieldType
      InsightsViewModel com_unsmoke_app_feature_insights_InsightsViewModel2;

      @KeepFieldType
      AchievementsViewModel com_unsmoke_app_feature_achievements_AchievementsViewModel2;

      @KeepFieldType
      HomeViewModel com_unsmoke_app_feature_home_HomeViewModel2;

      @KeepFieldType
      CheckInViewModel com_unsmoke_app_feature_checkin_CheckInViewModel2;

      @KeepFieldType
      OnboardingViewModel com_unsmoke_app_feature_onboarding_OnboardingViewModel2;

      @KeepFieldType
      NRTViewModel com_unsmoke_app_feature_nrt_NRTViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.unsmoke.app.feature.achievements.AchievementsViewModel 
          return (T) new AchievementsViewModel(singletonCImpl.quitAttemptRepositoryImplProvider.get(), singletonCImpl.cravingRepositoryImplProvider.get());

          case 1: // com.unsmoke.app.feature.checkin.CheckInViewModel 
          return (T) new CheckInViewModel(singletonCImpl.checkInRepositoryImplProvider.get(), singletonCImpl.quitAttemptRepositoryImplProvider.get());

          case 2: // com.unsmoke.app.feature.craving.CravingViewModel 
          return (T) new CravingViewModel(singletonCImpl.cravingRepositoryImplProvider.get(), singletonCImpl.quitAttemptRepositoryImplProvider.get());

          case 3: // com.unsmoke.app.feature.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.nRTRepositoryImplProvider.get(), singletonCImpl.quitAttemptRepositoryImplProvider.get(), singletonCImpl.provideUserPreferencesDataStoreProvider.get());

          case 4: // com.unsmoke.app.feature.insights.InsightsViewModel 
          return (T) new InsightsViewModel(singletonCImpl.cravingRepositoryImplProvider.get(), singletonCImpl.quitAttemptRepositoryImplProvider.get());

          case 5: // com.unsmoke.app.feature.journal.JournalViewModel 
          return (T) new JournalViewModel(singletonCImpl.checkInRepositoryImplProvider.get(), singletonCImpl.quitAttemptRepositoryImplProvider.get());

          case 6: // com.unsmoke.app.feature.nrt.NRTViewModel 
          return (T) new NRTViewModel(singletonCImpl.nRTRepositoryImplProvider.get(), singletonCImpl.quitAttemptRepositoryImplProvider.get());

          case 7: // com.unsmoke.app.feature.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.quitAttemptRepositoryImplProvider.get(), singletonCImpl.nRTRepositoryImplProvider.get(), singletonCImpl.provideUserPreferencesDataStoreProvider.get());

          case 8: // com.unsmoke.app.feature.profile.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.quitAttemptRepositoryImplProvider.get(), singletonCImpl.cravingRepositoryImplProvider.get(), singletonCImpl.provideUserPreferencesDataStoreProvider.get());

          case 9: // com.unsmoke.app.feature.progress.ProgressViewModel 
          return (T) new ProgressViewModel(singletonCImpl.quitAttemptRepositoryImplProvider.get(), singletonCImpl.cravingRepositoryImplProvider.get(), singletonCImpl.nRTRepositoryImplProvider.get(), singletonCImpl.provideUserPreferencesDataStoreProvider.get());

          case 10: // com.unsmoke.app.feature.recovery.RecoveryViewModel 
          return (T) new RecoveryViewModel(singletonCImpl.quitAttemptRepositoryImplProvider.get());

          case 11: // com.unsmoke.app.feature.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.provideUserPreferencesDataStoreProvider.get(), singletonCImpl.provideUnSmokeDatabaseProvider.get());

          case 12: // com.unsmoke.app.feature.splash.SplashViewModel 
          return (T) new SplashViewModel(singletonCImpl.provideUserPreferencesDataStoreProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends UnSmokeApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends UnSmokeApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends UnSmokeApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<DataStore<Preferences>> provideDataStoreProvider;

    private Provider<UserPreferencesDataStore> provideUserPreferencesDataStoreProvider;

    private Provider<UnSmokeDatabase> provideUnSmokeDatabaseProvider;

    private Provider<QuitAttemptRepositoryImpl> quitAttemptRepositoryImplProvider;

    private Provider<CravingRepositoryImpl> cravingRepositoryImplProvider;

    private Provider<CheckInRepositoryImpl> checkInRepositoryImplProvider;

    private Provider<NRTRepositoryImpl> nRTRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>emptyMap());
    }

    private QuitAttemptDao quitAttemptDao() {
      return DatabaseModule_ProvideQuitAttemptDaoFactory.provideQuitAttemptDao(provideUnSmokeDatabaseProvider.get());
    }

    private CravingDao cravingDao() {
      return DatabaseModule_ProvideCravingDaoFactory.provideCravingDao(provideUnSmokeDatabaseProvider.get());
    }

    private CheckInDao checkInDao() {
      return DatabaseModule_ProvideCheckInDaoFactory.provideCheckInDao(provideUnSmokeDatabaseProvider.get());
    }

    private NRTDao nRTDao() {
      return DatabaseModule_ProvideNRTDaoFactory.provideNRTDao(provideUnSmokeDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 1));
      this.provideUserPreferencesDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<UserPreferencesDataStore>(singletonCImpl, 0));
      this.provideUnSmokeDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<UnSmokeDatabase>(singletonCImpl, 3));
      this.quitAttemptRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<QuitAttemptRepositoryImpl>(singletonCImpl, 2));
      this.cravingRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CravingRepositoryImpl>(singletonCImpl, 4));
      this.checkInRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CheckInRepositoryImpl>(singletonCImpl, 5));
      this.nRTRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<NRTRepositoryImpl>(singletonCImpl, 6));
    }

    @Override
    public void injectUnSmokeApplication(UnSmokeApplication unSmokeApplication) {
      injectUnSmokeApplication2(unSmokeApplication);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private UnSmokeApplication injectUnSmokeApplication2(UnSmokeApplication instance) {
      UnSmokeApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.unsmoke.app.core.data.datastore.UserPreferencesDataStore 
          return (T) DataStoreModule_ProvideUserPreferencesDataStoreFactory.provideUserPreferencesDataStore(singletonCImpl.provideDataStoreProvider.get());

          case 1: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DataStoreModule_ProvideDataStoreFactory.provideDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.unsmoke.app.core.data.repository.QuitAttemptRepositoryImpl 
          return (T) new QuitAttemptRepositoryImpl(singletonCImpl.quitAttemptDao());

          case 3: // com.unsmoke.app.core.data.database.UnSmokeDatabase 
          return (T) DatabaseModule_ProvideUnSmokeDatabaseFactory.provideUnSmokeDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.unsmoke.app.core.data.repository.CravingRepositoryImpl 
          return (T) new CravingRepositoryImpl(singletonCImpl.cravingDao());

          case 5: // com.unsmoke.app.core.data.repository.CheckInRepositoryImpl 
          return (T) new CheckInRepositoryImpl(singletonCImpl.checkInDao());

          case 6: // com.unsmoke.app.core.data.repository.NRTRepositoryImpl 
          return (T) new NRTRepositoryImpl(singletonCImpl.nRTDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
