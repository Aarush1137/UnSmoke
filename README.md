# UnSmoke - Android Quit-Smoking Companion

UnSmoke is a premium, production-quality native Android application designed to help users stop smoking, stay smoke-free, manage cravings, track NRT (Nicotine Replacement Therapy) usage, understand triggers, build replacement behaviors, and maintain long-term progress.

## 📱 Screenshots & Visuals
*(Images sourced from ssets/master_visuals/)*

| Home Dashboard | Cravings & Triggers | Achievements & Progress |
| :---: | :---: | :---: |
| <img src="assets/master_visuals/unsmoke_home_exact.jpg" width="250"/> | <img src="assets/master_visuals/craving_intervention.jpg" width="250"/> | <img src="assets/master_visuals/milestones_badges.jpg" width="250"/> |

| Empty States | Personal Insights | Shareable Milestones |
| :---: | :---: | :---: |
| <img src="assets/master_visuals/empty_states.jpg" width="250"/> | <img src="assets/master_visuals/insights_dashboard.jpg" width="250"/> | <img src="assets/master_visuals/share_cards.jpg" width="250"/> |

## 🚀 Features
*   **10-Minute Craving Intervention:** Background-safe 10-minute timer to help you beat acute cravings.
*   **"No Shame" Recovery Flow:** If you lapse, the app doesn't reset you to zero and shame you. It compassionately asks what happened, updates your triggers, and helps you restart.
*   **NRT Tracking:** Log gums, patches, or lozenges to visualize your weaning process.
*   **Smart Insights:** Automatically analyzes your logs to find your highest-risk hours of the day and most effective coping strategies.
*   **Dynamic Achievements:** Earn beautifully crafted badges for consistency and milestones.
*   **Biometric Privacy:** Lock your sensitive health data behind Android's native BiometricPrompt.
*   **Offline-First:** All data is stored locally in Room. No cloud syncing, no accounts required.

## 🛠 Tech Stack
*   **Language:** Kotlin (2.1.10)
*   **UI Toolkit:** Jetpack Compose (BOM 2024.06.00)
*   **Architecture:** Clean Architecture + MVVM
*   **Database:** Room (3.0.1)
*   **Dependency Injection:** Hilt (2.51.1)
*   **Background Work:** WorkManager
*   **Local Storage:** DataStore Preferences
*   **Toolchain:** Java 21

## 🚧 Current Status & Known Issues
The foundational logic, database, and UI are built. The following fixes are pending for the next development session:
*   Enforcing the Onboarding flow on first launch.
*   Fixing navigation back-stack issues on sub-screens.
*   Enforcing strict Dark Mode matching the design system.
*   Missing launcher app icon (currently showing default Android logo).

## 📄 License
MIT License - Aarush Jain 2026

