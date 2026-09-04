# Changelog

## [3.2.0] - 2026-09-04

### 🚀 Major Audit & Comprehensive Remediation (41 Bugs Resolved)
- **Security & Secret Hygiene:**
  - Untracked production API keys and credentials from Git index; updated `.gitignore` with strict exclusions for `**/secrets.properties` and `**/google-services.json`.
  - Added sanitized `app/google-services.json.example` template.
  - Removed dangerous `REQUEST_INSTALL_PACKAGES` permission from AndroidManifest.xml; migrated in-app updater to standard browser intent (`Intent.ACTION_VIEW`).
- **AI & Cloud Infrastructure:**
  - Migrated Google Generative AI SDK models from retired `gemini-1.5-flash` to active `gemini-flash-latest`.
  - Updated Firestore security rules to allow authenticated profile reads and paired buddy queries.
  - Added `.limit(1)` to Firestore buddy pairing code queries to comply with security rules.
  - Resolved pairing code generation race condition with `CloudBackupEngine` via `SetOptions.merge()`.
  - Wired live snapshot listener in `BuddyRepository` to trigger `processAcceptedRequests` on `ACCEPTED` status.
- **CI/CD & Release Pipeline:**
  - Configured release build type signing in `app/build.gradle.kts`.
  - Updated GitHub Actions workflow (`build-apk.yml`) to assemble and publish both release and debug APK artifacts.
- **Stability, Coroutines & ProGuard:**
  - Disabled default `WorkManagerInitializer` in `AndroidManifest.xml` to prevent race condition crashes with custom `HiltWorkerFactory`.
  - Created `app/proguard-rules.pro` with keep rules for Room DAOs/Entities, Firestore models, Kotlinx Serialization, Gemini AI SDK, WorkManager, and DataStore.
  - Replaced `GlobalScope.launch` with lifecycle-aware coroutine scopes in Wear OS (`rememberCoroutineScope`, `LaunchedEffect`, `withContext(Dispatchers.IO)`).
  - Switched `WearMessageListenerService` to `runBlocking(Dispatchers.IO)` to guarantee Room writes complete before service destruction.
  - Added `androidx.hilt:hilt-compiler:1.2.0` via KSP for WorkManager injection.
  - Isolated reactive flows: moved side-effects (`wearSyncManager`, `generateAiInsight`) out of `combine` lambdas into terminal `.collect` blocks.
  - Protected database writes across `RecoveryViewModel`, `CravingViewModel`, and `BuddyViewModel` with robust `try-catch` blocks.
- **Navigation & State Management:**
  - Scoped shared `CravingViewModel` to parent `"craving"` backstack entry in `AppNavGraph.kt` to prevent trigger/intensity loss between screens.
  - Fixed navigation stack reset on recovery/craving by setting `inclusive = false` and `launchSingleTop = true` on Home popUpTo.
  - Built backstack hierarchy to Home for deep links and Wear OS SOS alerts in `MainActivity.kt`, with clean `onNewIntent` handling via StateFlow.
  - Added missing `navigationIcon` back button to `CompanionScreen.kt`.
  - Routed Home screen "Lung Test" card to `Screen.CheckIn.route` instead of Journal.
  - Preserved AI Coach chat history when displaying connection error messages.
  - Converted `HomeViewModel` state updates to atomic `_uiState.update`.
  - Removed `Context` leak from `AchievementsViewModel` and moved share intent creation directly to `AchievementsScreen.kt`.
  - Included both `"DEFEATED"` and `"SURVIVED"` craving outcomes in achievement calculations.
- **Room Database & Migration:**
  - Added unique index on `datestamp` in `DailyCheckInEntity` to prevent duplicate check-in rows.
  - Bumped database version to 6; added `MIGRATION_5_6` and registered in `DatabaseModule.kt` with `.fallbackToDestructiveMigration()`.
- **Domain Engines & Math Guards:**
  - Added guard `if (cigarettesPerPack <= 0) return 0.0` in `CalculationEngine.packsAvoided` and added unit test.
  - Populated comprehensive evidence-based quote libraries for `FIRST_WEEK`, `MILESTONE`, `MORNING`, `EVENING`, and `GENERAL` in `QuoteEngine.kt`.
  - Clamped `weeksSmokeFree.coerceAtLeast(0)` across all tapering plans in `NRTTaperingEngine.kt`.
  - Implemented 12-hour AM/PM formatting in `PersonalizationEngine.formatHour()`.
  - Fixed vaping unit price calculation in `OnboardingViewModel.kt` (`packPriceDouble / perPackInt`).
  - Replaced cold state read with suspend query in `NRTViewModel` to prevent duplicate product insertion.
- **UI Performance & Widget Polish:**
  - Memoized text instructions with `derivedStateOf` in `BreathingOrb.kt` to eliminate 60fps recompositions.
  - Removed disruptive runtime permission dialog in `CravingTimerScreen.kt` during acute cravings.
  - Added view ID and click PendingIntent to `StreakWidgetReceiver.kt` to launch dashboard on widget tap.
  - Removed redundant `mavenLocal()` declaration in `settings.gradle.kts`.
  - Closed `InputStream` with `.use { ... }` in `UpdateChecker.kt`.


## [0.7.0] - 2026-08-22
### Added
- **Final Features**: Completed the full suite of UI screens:
  - **Daily Check-in**: Emoji-based mood tracking and craving slider.
  - **Insights**: Top trigger, high-risk time, and best coping tool analysis.
  - **Journal**: Track feelings and write daily entries.
  - **My Plan**: Reorderable list of personalized coping strategies.
  - **Achievements & Profile**: Badge grid and detailed lifetime stats.

## [0.6.0] - 2026-08-22
### Added
- **NRT Tracker**: Dashboard showing daily logs, total expenditure vs savings, and a plan completion donut ring.
- **Progress Screen**: Comprehensive metrics view with time-range filtering (7 Days, 30 Days, 3 Months, 1 Year) for tracking streak, money, and craving data.

## [0.5.0] - 2026-08-22
### Added
- **Onboarding Flow**: 3-step animated conversational setup for capturing baseline smoking habits and user profile.
- **Craving Support**: Full 5-step immersive support system with Intensity Slider, Trigger selection, and a 10-minute dark-themed breathing timer (with glowing Canvas animations).
- **Outcome & Lapse UI**: Supportive, non-shaming screens for when users successfully ride out a craving or experience a slip.

## [0.4.0] - 2026-08-22
### Added
- **Widgets System**: Introduced Jetpack Glance to power three new home screen widgets:
  - **Streak Widget (2x2)**: Smoke-free days counter, quit date, and I HAVE A CRAVING button.
  - **Dashboard Widget (4x2)**: Full metrics � days, cigarettes avoided, money saved (amber), craving stats, and a craving button.
  - **Craving Widget (1x1)**: Emergency one-tap craving support launcher.
- **Widget Background Sync**: Integrated Hilt Worker for periodic WidgetDataRepository refresh.
- **Core Architecture Scaffolded**: Home, Onboarding, Craving systems started.

## [0.2.0] - 2026-08-22
### Added
- **UI System**: Mint/Teal primary color palette with Amber achievement accents.
- **Components**: ProgressRing, BreathingOrb added to the design system.
