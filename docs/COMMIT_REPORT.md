# UnSmoke v3.2.0 — Final Commit & Release Report

**Project:** UnSmoke (The Evidence-Based Quit Smoking Companion)  
**Release Version:** `3.2.0`  
**Build Number:** `versionCode 11`  
**Date:** September 4, 2026  
**Scope:** Full-Codebase Audit, Security Remediation, Concurrency Fixes, Navigation Overhaul, and 41-Bug Comprehensive Resolution.

---

## 📊 Executive Summary

This release elevates UnSmoke to **v3.2.0 (versionCode 11)** following an exhaustive audit across 5 core domains:
1. **Security & Secrets Hygiene** (Zero API keys in version control, sanitized configuration templates, Play Store policy compliance).
2. **AI & Cloud Backend** (Active `gemini-flash-latest` SDK integration, Firestore security rules update, atomic buddy pairing).
3. **Reactive Concurrency & Lifecycle** (Elimination of side-effects in `combine()`, cancellation of stale collectors via `flatMapLatest`, coroutine leak fixes on Wear OS and WorkManager).
4. **Data Integrity & Persistence** (Room DB migration v6 with unique indices, `try-catch` guards on database writes, negative date clamping, division-by-zero guards).
5. **UI & Navigation Robustness** (Scoped ViewModel across craving flows, single-top home navigation, deep link backstack preservation, 60fps text recomposition optimization).

---

## 🛠️ Summary of All 41 Resolved Bugs

### 🔴 CRITICAL (7 / 7 Fixed)
| Bug ID | Component | Summary & Solution Applied |
|---|---|---|
| **BUG-001** | Security | Leaked API keys untracked from Git; added `**/secrets.properties` and `**/google-services.json` to `.gitignore`; provided sanitized `app/google-services.json.example`. |
| **BUG-002** | CI/CD | Configured release signing in `app/build.gradle.kts`; updated GitHub Actions to build and release both release and debug APKs. |
| **BUG-003** | Firestore | Enforced `.limit(1)` in pairing code queries and updated `firestore.rules` to permit authenticated profile reads for buddy discovery. |
| **BUG-004** | Google Play | Removed forbidden `REQUEST_INSTALL_PACKAGES` permission; converted update flow to browser intent (`Intent.ACTION_VIEW`). |
| **BUG-005** | Gemini AI | Migrated retired `gemini-1.5-flash` model to active `gemini-flash-latest` in both single-turn and multi-turn chat sessions. |
| **BUG-006** | Cloud Backup | Resolved pairing code generation race condition with `CloudBackupEngine` by checking `pairingCode.isNullOrBlank()` with `SetOptions.merge()`. |
| **BUG-007** | Buddy System | Wired live snapshot listener in `BuddyRepository` to trigger `processAcceptedRequests()` whenever an incoming request transitions to `ACCEPTED`. |

### 🟠 HIGH (17 / 17 Fixed)
| Bug ID | Component | Summary & Solution Applied |
|---|---|---|
| **BUG-008** | WorkManager | Removed default `WorkManagerInitializer` from `AndroidManifest.xml` to prevent startup crash with custom `HiltWorkerFactory`. |
| **BUG-009** | Build / R8 | Created missing `app/proguard-rules.pro` with explicit rules for Room, Firestore models, Kotlinx Serialization, Gemini SDK, and WorkManager. |
| **BUG-010** | Personalization | Replaced hardcoded empty strings in `PersonalizationEngine.kt` with standard 12-hour AM/PM hour formatting. |
| **BUG-011** | Wear OS | Replaced unmanaged coroutine in `WearMessageListenerService` with `runBlocking(Dispatchers.IO)` to prevent dropped database writes upon service destruction. |
| **BUG-012** | Dependency | Added `androidx.hilt:hilt-compiler:1.2.0` in version catalog and applied `ksp(libs.androidx.hilt.compiler)` in `app/build.gradle.kts`. |
| **BUG-013** | HomeViewModel | Extracted `wearSyncManager.syncQuitStatus()` side-effect out of `combine()` lambda and into terminal `.collect` consumer. |
| **BUG-014** | Settings | Merged `_backupState` and `_backupMessage` into `SettingsUiState`; added "Cloud Backup & Sync" card with Toast feedback in `SettingsScreen.kt`. |
| **BUG-015** | BuddyViewModel | Added cancellation for `buddiesJob` and cleared `buddyProfiles` if user profile transitions to null. |
| **BUG-016** | Buddy Repo | Replaced unmanaged `CoroutineScope(Dispatchers.IO)` with `this@callbackFlow.launch(Dispatchers.IO)` inside `callbackFlow`. |
| **BUG-017** | Wear OS UI | Replaced `GlobalScope.launch` with `rememberCoroutineScope()` in `MainActivity.kt` and `withContext(Dispatchers.IO)` in `WearCravingScreen.kt`. |
| **BUG-018** | InsightsViewModel | Kept `combine()` transformation pure; moved AI insight generation trigger into `.collect`. |
| **BUG-019** | ProgressViewModel | Implemented `flatMapLatest` on active quit attempt to automatically cancel and replace child collectors. |
| **BUG-020** | RecoveryViewModel | Enclosed all Room database writes in `finishRecovery()` within robust `try-catch` blocks. |
| **BUG-021** | CravingViewModel | Wrapped `cravingRepo.logCraving()` in `try-catch` to ensure state transitions safely even on storage exception. |
| **BUG-022** | Buddy Sync | Guarded `buddyRepo.updateMyStats()` in `try-catch` inside periodic sync loop to prevent loop termination on network glitches. |
| **BUG-023** | Navigation / State | Scoped shared `CravingViewModel` to parent `"craving"` route entry in `AppNavGraph.kt` so intensity and triggers persist into the timer. |
| **BUG-024** | NRT Management | Replaced synchronous `.value` read with suspend `nrtRepo.getProducts().firstOrNull()` to prevent duplicate product insertion. |

### 🟡 MEDIUM (11 / 11 Fixed)
| Bug ID | Component | Summary & Solution Applied |
|---|---|---|
| **BUG-025** | Onboarding | Fixed unit cost calculation for vaping pods (`packPriceDouble / perPackInt`). |
| **BUG-026** | CalculationEngine | Added guard `if (cigarettesPerPack <= 0) return 0.0` in `packsAvoided()`; verified with unit test. |
| **BUG-027** | Room Database | Added unique index on `datestamp` in `DailyCheckInEntity`; bumped DB to version 6 with `MIGRATION_5_6`. |
| **BUG-028** | QuoteEngine | Populated evidence-based quote libraries for `FIRST_WEEK`, `MILESTONE`, `MORNING`, `EVENING`, and `GENERAL`. |
| **BUG-029** | Navigation | Changed `inclusive = false` and added `launchSingleTop = true` on Home popUpTo to prevent backstack destruction. |
| **BUG-030** | Deep Linking | Built backstack to Home and implemented `onNewIntent` via `pendingIntentFlow` in `MainActivity.kt`. |
| **BUG-031** | AI Coach | Preserved existing chat history when appending error messages on network failure. |
| **BUG-032** | HomeViewModel | Replaced mutable state assignment with atomic `_uiState.update` to prevent overwriting AI insights and quotes. |
| **BUG-033** | Achievements | Removed `Context` reference from `AchievementsViewModel`; counted both `"DEFEATED"` and `"SURVIVED"` cravings. |
| **BUG-034** | Companion UI | Added missing `navigationIcon` back button to `CompanionScreen.kt` TopAppBar. |
| **BUG-035** | Home Routing | Corrected "Lung Test" card click routing to `Screen.CheckIn.route`. |

### 🟢 LOW (6 / 6 Fixed)
| Bug ID | Component | Summary & Solution Applied |
|---|---|---|
| **BUG-036** | Networking | Wrapped GitHub release stream in `bufferedReader().use { it.readText() }` to prevent socket leak. |
| **BUG-037** | NRT Engine | Clamped `weeksSmokeFree.coerceAtLeast(0)` across all tapering plans to handle future quit dates. |
| **BUG-038** | Gradle | Removed duplicate `mavenLocal()` declaration in `settings.gradle.kts`. |
| **BUG-039** | Design System | Memoized animated breathing text with `derivedStateOf` in `BreathingOrb.kt` to eliminate 60fps recompositions. |
| **BUG-040** | UX / Location | Removed disruptive runtime location dialog during acute craving timer in `CravingTimerScreen.kt`. |
| **BUG-041** | AppWidget | Added view ID and click PendingIntent in `StreakWidgetReceiver.kt` to launch dashboard on widget tap. |

---

## 🧪 Verification & Build Status

- **Automated Unit Tests:** Passed 100% (`./gradlew testDebugUnitTest` completed with `BUILD SUCCESSFUL`).
- **Code Compilation:** Both `:app:compileDebugKotlin`, `:app:compileDebugUnitTestKotlin`, and `:wear:compileDebugKotlin` compiled with 0 errors.
- **Git Security Audit:** Confirmed `secrets.properties` and `app/google-services.json` are untracked and strictly excluded by `.gitignore`.
- **Version Stamp:**
  - Phone App: `versionCode = 11`, `versionName = "3.2.0"`
  - Wear OS: `versionCode = 11`, `versionName = "3.2.0"`
