# UnSmoke Project Walkthrough
**Status: Redesigned Profile & Settings + Successful Device Installation**

Over this extensive session, we successfully transitioned **UnSmoke** from a 64-point specification and a folder of 14 static visual artifacts into a fully architected, offline-first Android application.

Here is a summary of the core systems and logic built:

## 1. Core Data & Architecture (Offline-First)
*   **Room Database**: Implemented a robust local database (`QuitAttemptEntity`, `CravingEventEntity`, `DailyCheckInEntity`, `NRTUsageEntity`) with a strict "No Shame" data retention policy.
*   **Calculation Engine**: Handled edge cases for backdated quit dates, timezone shifts, and precise calculations for 'cigarettes avoided' and 'money saved'.
*   **Personalization Engine**: Dynamically analyzes the user's `CravingEventEntity` timestamps to identify high-risk times (e.g., 7 PM - 9 PM) and ranks successful coping strategies.

## 2. Redesigned Profile & Settings Hub
*   **Redesigned Profile (`ProfileScreen.kt` & `ProfileViewModel.kt`)**:
    *   Avatar with glowing mint border & dynamic smoke-free status.
    *   Live stat cards: Days Free, Money Saved, Cigarettes Avoided.
    *   Editable **Core Motivation** ("My Why") with quick dialog updates.
    *   **Emergency Anchor Contact**: Direct-call launcher for a support person during acute cravings.
    *   Hub navigation to Plan, Achievements, and Settings.
*   **Redesigned Settings (`SettingsScreen.kt` & `SettingsViewModel.kt`)**:
    *   Display name & Multi-currency support (ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡Ãƒâ€šÃ‚Â¹, $, ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â€šÂ¬Ã…Â¡Ãƒâ€šÃ‚Â¬, ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â£, ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥, C$).
    *   Theme Mode selector (Dark Midnight Teal, AMOLED Black, Light, System Default).
    *   Push notification master toggle & 4 Coaching Voice Tones (Gentle, Direct, Tough Love, Minimal).
    *   Live **Biometric App Lock** via `BiometricPrompt`.
    *   In-app GitHub Release update checker.
    *   Data export (JSON) and Wipe Data / Factory Reset.

## 3. UI Refinements & "10-Minute" Rule
*   **Craving Flow (`CravingScreen.kt` & `CravingTimerScreen.kt`)**: Background-safe 10-minute timer with dynamic color shifts (Green $\to$ Amber $\to$ Red) and quick trigger selection.
*   **Check-in & Journal (`CheckInScreen.kt` & `JournalScreen.kt`)**: 5-point mood selector, sleep and stress sliders, and reflection log.
*   **Dynamic Empty States**: Reusable `EmptyStateCard` components ensure screens never look empty or broken.
*   **Social Share Cards & Celebrations**: `UnSmokeShareCard` and `MilestoneCelebrationOverlay` with native `FileProvider` and Android `Intent.ACTION_SEND` integration.

## 4. Build Environment & Live Device Installation
*   **Java 25 Incompatibility Resolved**: Bundled a local OpenJDK 21 toolchain to allow clean Gradle builds without modifying global system environments.
*   **Deployed to Device**: The APK was compiled (`assembleDebug`) and successfully installed & launched on the connected device (`adb-8487f76c-NassIM`).

---

### Priority Tasks for Next Session
1. Add an adaptive app launcher icon in `res/mipmap`.
2. Fix Onboarding launch bypass so new installs always walk through setup.
3. Fix backstack navigation on sub-screens.
4. Prepare repository interfaces for Artifact 15 (AI Coach).

## Phase 7: Advanced Analytics, CBT Recovery, and Export (Completed)
We have successfully rolled out the complete suite of advanced clinical and analytical features:
*   **Craving Analytics Dashboard**: Added an interactive heatmap that visualizes the intensity of cravings mapped against the time of day and user triggers. 
*   **CBT Relapse Autopsy Flow**: Intercepted the "I Smoked" action, replacing immediate streak-reset punishment with a 3-step, shame-free CBT questionnaire (`RecoveryScreen.kt`) to reflect on triggers and dosage.
*   **Daily Quit Coach**: Built a 30-day curriculum of micro-lessons delivered to the `HomeScreen` daily based on the user's current smoke-free milestone.
*   **Clinician Export (CSV)**: Built `ExportEngine.kt` and wired it into the Settings page, allowing users to securely generate and export their NRT dosage history and craving logs to share with their doctors via a native Android share sheet.
*   **Social Share Cards**: Built `ShareEngine.kt` using standard Android Canvas graphics to render a personalized, aesthetic dark-teal milestone graphic (with days free and money saved) for one-tap sharing to Instagram and WhatsApp.

## Phase 8 (Finalized) & Hotfixes

- **Achievements Grid**: 7 unlocked badges mapping back to days and craving streak.
- **Auto-Update Fix**: Dynamically pulls version using packageManager, added "application/vnd.android.package-archive" to DownloadManager.Request, and injected REQUEST_INSTALL_PACKAGES into Manifest.
- **Version Bump**: Bumped to v1.2.0 in uild.gradle.kts.

## Phase 9 - Sprint 1 (In Progress)

- **WearOS Scaffold**: Bootstrapped the :wear module.
- Configured wear/build.gradle.kts for Compose for Wear OS targeting Android SDK 33.
- Scaffolded MainActivity.kt and WearHomeScreen.kt with a basic UI layout featuring SOS & Log NRT buttons.
- Copied app assets to the wear module and successfully compiled.

## Phase 9 - Sprint 2 (Completed)

- **Health Connect Integration**: Hooked up ndroidx.health.connect:connect-client.
- Added HealthConnectRepository.kt to pull 7-day Resting Heart Rate.
- Updated AnalyticsViewModel.kt to process RHR alongside craving severity.
- UI dynamically displays a **Physiological Recovery** card overlay on AnalyticsScreen.kt.