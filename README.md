<div align="center">
  <h1>UnSmoke ÃƒÂ°Ã…Â¸Ã…Â¡Ã‚Â­</h1>
  <p><b>The Evidence-Based, Gamified Quit Smoking Companion</b></p>
</div>

---

UnSmoke is an advanced Android application designed to help users quit smoking through a combination of psychological support, **Cognitive Behavioral Therapy (CBT)** techniques, and clinical **Nicotine Replacement Therapy (NRT)** tapering protocols. Built entirely with Jetpack Compose and Kotlin 2.0.

## ÃƒÂ°Ã…Â¸Ã¢â‚¬Å“Ã‚Â± Screenshots

<div align="center">
  <img src="docs/screenshots/home.png" alt="Home Screen" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="docs/screenshots/timer.png" alt="Craving Timer" width="250"/>
</div>

---

## ÃƒÂ¢Ã…â€œÃ‚Â¨ In-Depth Features

### ÃƒÂ¢Ã‚ÂÃ‚Â±ÃƒÂ¯Ã‚Â¸Ã‚Â 1. Live Ticking Quit Timer (New in v1.3.1)
The Home screen features a dynamic, real-time ticking timer that tracks the exact days, hours, minutes, and seconds since your quit date. The timer automatically resets if a relapse is logged, keeping you accountable down to the exact second.

### ÃƒÂ¢Ã…â€™Ã…Â¡ 2. Wear OS Companion App & Data Sync (New in v1.3.1)
UnSmoke now comes with a fully native smartwatch app! 
- **Live Sync:** The Data Layer automatically syncs your active quit attempt from your phone to your wrist.
- **Wrist Dashboard:** View your live ticking timer directly on your smartwatch.
- **Haptic Breathing:** Trigger the "Breathe" function on your watch to launch a visually expanding orb synchronized with physical haptic vibrations to guide you through a 4-7-8 breathing exercise without ever touching your phone.
- **Emergency SOS:** Send immediate alerts from your wrist when a craving peaks.

### ÃƒÂ°Ã…Â¸Ã‚Â§Ã‚Â  3. Urge Surfing & The 4 D's Protocol
When a craving hits, users can launch the **Craving Timer**. It features a visual "breathing wave" animation that mimics the physiological curve of a craving (which usually peaks and subsides within 3-5 minutes). 
While the timer runs, the app provides the **4 D's Toolkit**:
- **Delay:** Wait 5 minutes.
- **Deep Breathe:** Follow the animated visualizer.
- **Drink Water:** Distract the oral fixation.
- **Do Something Else:** Suggests random, healthy distractions.

### ÃƒÂ°Ã…Â¸Ã¢â‚¬â„¢Ã…Â  4. NRT (Nicotine Replacement Therapy) Tracker
Unlike most apps that just track cold-turkey days, UnSmoke acknowledges that clinical cessation often involves NRT (patches, gums, lozenges).
- Users can log usage of NRT products.
- The app calculates the **Net Money Saved** (Gross savings from avoided cigarettes minus the cost of NRT products purchased).
- Over time, the app provides a **tapering schedule** to step down NRT doses safely.

### ÃƒÂ°Ã…Â¸Ã¢â‚¬â„¢Ã‚Â° 5. Localized Cost Tracking
Designed for global users, the onboarding flow asks for the exact average cost of *one* cigarette rather than assuming standard 20-pack sizes. This allows users who buy loose cigarettes or smaller packs (highly common in India and other regions) to get perfectly accurate daily and yearly savings calculations.

### ÃƒÂ°Ã…Â¸Ã¢â‚¬Å“Ã…Â  6. Interactive Analytics & Health Connect
Track exactly how much you've saved and how many cigarettes you've avoided.
- Integrated with **Health Connect** to show physical recovery (e.g., Resting Heart Rate drops).
- Unlock 50+ milestone badges based on actual physiological recovery (e.g., "Carbon Monoxide Normal", "Cilia Regrown").

### ÃƒÂ°Ã…Â¸Ã‚Â¤Ã¢â‚¬â€œ 7. AI Quit Coach (Powered by Gemini)
- The app aggregates your craving history, triggers, and intense moments.
- Using a local or cloud Gemini LLM, the app provides **personalized 2-sentence actionable advice** on the Home screen to help you predict and avoid your highest-risk relapse windows.

---

## ÃƒÂ°Ã…Â¸Ã¢â‚¬Å“Ã‚Â Project Structure

```text
Unsmoke/
ÃƒÂ¢Ã¢â‚¬ÂÃ…â€œÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ app/
ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬Å¡   ÃƒÂ¢Ã¢â‚¬ÂÃ…â€œÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ src/main/kotlin/com/unsmoke/app/
ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬Å¡   ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬Å¡   ÃƒÂ¢Ã¢â‚¬ÂÃ…â€œÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ core/           # Room DB, Repositories, DataStore, Design System
ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬Å¡   ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬Å¡   ÃƒÂ¢Ã¢â‚¬ÂÃ…â€œÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ feature/        # Screen ViewModels & Compose UIs (Home, Craving, Analytics)
ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬Å¡   ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬Å¡   ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬ÂÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ widget/         # Glance App Widgets (Dashboard, Streak)
ÃƒÂ¢Ã¢â‚¬ÂÃ…â€œÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ wear/                   # WearOS Companion Module
ÃƒÂ¢Ã¢â‚¬ÂÃ¢â‚¬ÂÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ÃƒÂ¢Ã¢â‚¬ÂÃ¢â€šÂ¬ docs/                   # Screenshots & Assets
```

## ÃƒÂ°Ã…Â¸Ã¢â‚¬ÂºÃ‚Â ÃƒÂ¯Ã‚Â¸Ã‚Â Build & Install

1. Clone the repository:
```bash
git clone https://github.com/Aarush1137/UnSmoke.git
```
2. Open the project in **Android Studio Koala** (or newer).
3. Build and Run the `:app` configuration on your Android device (Android 8.0+ required).

## ÃƒÂ°Ã…Â¸Ã¢â‚¬ÂÃ¢â‚¬â„¢ Privacy First

UnSmoke respects your deeply personal health data. All craving logs, journal entries, and usage statistics are stored **locally** in a Room Database. No accounts required. No cloud syncing of health data without explicit opt-in. Biometric App Lock ensures your journey remains private.

---

<div align="center">
  <p>Built for the Phase 9 Developer Challenge.</p>
</div>

## 🤖 AI Development Artifacts
This project was developed with the assistance of Advanced Agentic Coding. You can review the AI's planning, task breakdown, and technical walkthroughs in the following generated artifacts:
- [Implementation Plan](docs/ai_artifacts/implementation_plan.md): Architectural decisions and database schemas.
- [Task Tracker](docs/ai_artifacts/task.md): Component-level checklists and sprint progress.
- [Walkthrough & Notes](docs/ai_artifacts/walkthrough.md): Technical validation and feature summaries.