<div align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" width="100" />
  <h1>UnSmoke Ã°Å¸Å¡Â­</h1>
  <p><b>The Evidence-Based, Gamified Quit Smoking Companion</b></p>
</div>

---

UnSmoke is an advanced Android application designed to help users quit smoking through a combination of psychological support, **Cognitive Behavioral Therapy (CBT)** techniques, and clinical **Nicotine Replacement Therapy (NRT)** tapering protocols. Built entirely with Jetpack Compose and Kotlin 2.0.

## Ã°Å¸â€œÂ¸ Screenshots

<div align="center">
  <img src="docs/screenshots/home.png" alt="Home Screen" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="docs/screenshots/timer.png" alt="Craving Timer" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="docs/screenshots/progress.jpg" alt="Progress Tracker" width="250"/>
</div>

---

## Ã¢Å“Â¨ In-Depth Features

### Ã°Å¸Å’Å  1. Urge Surfing & The 4 D's Protocol
When a craving hits, users can launch the **Craving Timer**. It features a visual "breathing wave" animation that mimics the physiological curve of a craving (which usually peaks and subsides within 3-5 minutes). 
While the timer runs, the app provides the **4 D's Toolkit**:
- **Delay:** Wait 5 minutes.
- **Deep Breathe:** Follow the animated visualizer.
- **Drink Water:** Distract the oral fixation.
- **Distract:** Engage the mind elsewhere.

### Ã°Å¸â€œâ€° 2. Clinical NRT Tapering Engine
UnSmoke doesn't just track your days; it actively manages your nicotine step-down. The integrated **NRT Tapering Engine**:
- Calculates progressive 12-week step-down instructions based on your baseline.
- Tracks gum/patch usage directly in the NRT Dashboard.
- Deducts NRT expenditure from your total "Money Saved" for an accurate financial picture.

### Ã°Å¸Â«Â 3. Lung Capacity & Health Recovery
Track how your body heals over time:
- **Baseline Capture:** During onboarding, users hold their breath to establish a baseline lung capacity.
- **Weekly Check-ins:** The app prompts you weekly to re-test your breath-hold, visualizing respiratory improvement via a dynamic expanding lung widget.
- **Health Timeline:** Unlock physiological milestonesÃ¢â‚¬â€from 20 minutes (blood pressure normalizing) to 1 year (heart disease risk halving).

### Ã°Å¸Ââ€  4. Gamified Milestone Badges
A comprehensive achievement engine rewards both craving resistance and streak consistency:
- **Milestone Badges:** Unlocked at 24 hours, 3 days, 1 week, 1 month, etc.
- **Craving Crusher Badges:** Earned by successfully letting the Craving Timer run its course without giving in.

---

## Ã°Å¸â€ºÂ  Tech Stack & Architecture

UnSmoke is built strictly following modern Android development guidelines and Clean Architecture principles:

*   **Language**: Kotlin 2.0.21
*   **UI Framework**: Jetpack Compose & Material 3
*   **Architecture**: MVVM (Model-View-ViewModel) with unidirectional data flow (UDF)
*   **Dependency Injection**: Hilt (2.51.1)
*   **Local Storage**: 
    *   **Room Database**: For structured NRT logs, craving events, and quit attempts.
    *   **DataStore Preferences**: For user state, theme, baseline metrics, and onboarding status.
*   **Concurrency**: Kotlin Coroutines & Flow
*   **Build System**: Gradle 8.14 (Kotlin DSL) with Version Catalogs (libs.versions.toml)

---

## Ã°Å¸Ââ€” Project Structure

The project is modularized by feature within the pp module for clear separation of concerns:

`	ext
app/src/main/kotlin/com/unsmoke/app/
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ core/                  # Core infrastructure and shared components
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ data/              # Room DAOs, DataStore, Repositories, Entities
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ domain/            # CalculationEngine, NRTTaperingEngine, Achievements
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ designsystem/      # Theme, Typography, AppColors, Shared Composables
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ feature/               # Feature modules (MVVM structure)
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ home/              # Dashboard, savings overview, daily stats
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ onboarding/        # First-time user setup & baseline capture
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ progress/          # Streak tracking, badges, weekly lung check-ins
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ cravings/          # Urge surfing, craving timer, coping toolkit
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ nrt/               # Clinical tapering dashboard and logging
Ã¢â€â€š   Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ settings/          # User preferences
Ã¢â€Å“Ã¢â€â‚¬Ã¢â€â‚¬ UnSmokeApplication.kt  # Hilt Application Class
Ã¢â€â€Ã¢â€â‚¬Ã¢â€â‚¬ MainActivity.kt        # Entry Point
`

---

## Ã°Å¸â€™Â» How to Setup and Run Locally

### Prerequisites
- **Android Studio**: Koala / Jellyfish (or newer) recommended.
- **Java Development Kit (JDK)**: **Java 21** is strictly required to compile this project (Gradle 8.14 compatibility).

### Installation Steps
1. **Clone the repository**:
   `ash
   git clone https://github.com/Aarush1137/UnSmoke.git
   `
2. **Open the Project**:
   Launch Android Studio and select File > Open, then navigate to the cloned UnSmoke directory.
3. **Configure Gradle JDK**:
   - Go to File > Settings (or Android Studio > Settings on macOS).
   - Navigate to Build, Execution, Deployment > Build Tools > Gradle.
   - Ensure the **Gradle JDK** is set to a **JDK 21** installation (e.g., Embedded JDK or local corretto-21).
4. **Sync and Build**:
   Click the **Sync Project with Gradle Files** button. Once synced, select the pp configuration and click **Run** (Shift + F10) to deploy to an emulator or physical device.

---

## Ã°Å¸Â¤Â Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## Ã°Å¸â€œâ€ž License
[MIT](https://choosealicense.com/licenses/mit/)