# UnSmoke

UnSmoke is an offline-first Android companion for quitting smoking. It helps people get through cravings, understand patterns, track nicotine-replacement therapy (NRT), and keep a compassionate record of progress.

> UnSmoke supports a quit journey; it does not provide medical advice or personalised NRT dosing. Consult a qualified healthcare professional for clinical guidance.

## What it includes

- Guided craving support with intensity and trigger logging, breathing exercises, a ten-minute timer, and a no-shame lapse-recovery flow.
- A smoke-free dashboard for elapsed time, cigarettes avoided, estimated savings, cravings, and milestones.
- NRT product and usage tracking, including expenditure against cigarette savings.
- Daily check-ins, a private journal, personal insights, a quit plan, achievements, and a profile with a support contact.
- Home-screen widgets: a streak counter, full dashboard, and one-tap craving launcher.
- On-device privacy: Room and DataStore storage, no account or cloud sync, optional Android biometric app lock, and local data export.

## Technology

| Area | Implementation |
| --- | --- |
| Platform | Native Android, min SDK 26, target/compile SDK 36 |
| Language and UI | Kotlin, Jetpack Compose, Material 3 |
| App architecture | MVVM, Hilt, Navigation Compose, Coroutines and Flow |
| Local data | Room and DataStore Preferences |
| Background/UI extras | WorkManager, Glance widgets, Vico charts, Lottie, BiometricPrompt |

## Run locally

1. Open the project in Android Studio with Android SDK Platform 36 installed.
2. Use a JDK compatible with the Gradle/Android Studio setup (the project compiles Java source compatibility level 17).
3. Connect an emulator or Android device, then run:

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:installDebug
```

The APK is written to `app\build\outputs\apk\debug\app-debug.apk`.

## Verification

The current working tree builds successfully with:

```powershell
.\gradlew.bat :app:assembleDebug
```

Before a release, also run unit and instrumented tests once they are added, and manually check onboarding, the full craving flow, NRT logging, biometric unlock, widgets, and the reset/export paths.

## Project layout

```text
app/src/main/kotlin/com/unsmoke/app/
├── core/          # Room, DataStore, domain calculations, design system
├── feature/       # Onboarding, home, craving, NRT, journal, settings, etc.
├── navigation/    # Compose navigation graph and screen routes
└── widget/        # Glance home-screen widgets
```

Design reference images live under [`assets/`](assets/). The product implementation plan and build checklist are in [`ai_artifacts/`](ai_artifacts/).

## Current limitations

- The `AMOLED` preference uses the shared dark scheme for now rather than a distinct pure-black palette.
- The app has no committed automated test suite yet.

## License

MIT License — Aarush Jain, 2026.
