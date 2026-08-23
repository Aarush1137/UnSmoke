<div align="center">
  <h1>UnSmoke ðŸš­</h1>
  <p><b>The Evidence-Based, Gamified Quit Smoking Companion</b></p>
</div>

---

UnSmoke is an advanced Android application designed to help users quit smoking through a combination of psychological support, **Cognitive Behavioral Therapy (CBT)** techniques, and clinical **Nicotine Replacement Therapy (NRT)** tapering protocols. Built entirely with Jetpack Compose and Kotlin 2.0.

## ðŸ“¸ Screenshots

<div align="center">
  <img src="docs/screenshots/home.png" alt="Home Screen" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="docs/screenshots/timer.png" alt="Craving Timer" width="250"/>
</div>

---

## ðŸš€ In-Depth Features

### ðŸŒŠ 1. Urge Surfing & The 4 D's Protocol
When a craving hits, users can launch the **Craving Timer**. It features a visual "breathing wave" animation that mimics the physiological curve of a craving (which usually peaks and subsides within 3-5 minutes). 
While the timer runs, the app provides the **4 D's Toolkit**:
- **Delay:** Wait 5 minutes.
- **Deep Breathe:** Follow the animated visualizer.
- **Drink Water:** Distract the oral fixation.
- **Do Something Else:** Suggests random, healthy distractions.

### ðŸ’Š 2. NRT (Nicotine Replacement Therapy) Tracker
Unlike most apps that just track cold-turkey days, UnSmoke acknowledges that clinical cessation often involves NRT (patches, gums, lozenges).
- Users can log usage of NRT products.
- The app automatically calculates the **Net Money Saved** (Gross savings from avoided cigarettes minus the cost of NRT products purchased).
- Over time, the app provides a **tapering schedule** to step down NRT doses safely.

### ðŸ“Š 3. Interactive Analytics & Health Connect
Track exactly how much you've saved and how many cigarettes you've avoided.
- Integrated with **Health Connect** to show physical recovery (e.g., Resting Heart Rate drops).
- Unlock 50+ milestone badges based on actual physiological recovery (e.g., "Carbon Monoxide Normal", "Cilia Regrown").

### ðŸ§  4. AI Quit Coach (Powered by Gemini)
- The app aggregates your craving history, triggers, and intense moments.
- Using a local or cloud Gemini LLM, the app provides **personalized 2-sentence actionable advice** on the Home screen to help you predict and avoid your highest-risk relapse windows.

---

## ðŸ“‚ Project Structure

```text
Unsmoke/
â”œâ”€â”€ app/
â”‚   â”œâ”€â”€ src/main/kotlin/com/unsmoke/app/
â”‚   â”‚   â”œâ”€â”€ core/           # Room DB, Repositories, DataStore, Design System
â”‚   â”‚   â”œâ”€â”€ feature/        # Screen ViewModels & Compose UIs (Home, Craving, Analytics)
â”‚   â”‚   â””â”€â”€ widget/         # Glance App Widgets (Dashboard, Streak)
â”œâ”€â”€ wear/                   # WearOS Companion Module
â””â”€â”€ docs/                   # Screenshots & Assets
```

## ðŸ› ï¸ Build & Install

1. Clone the repository:
```bash
git clone https://github.com/Aarush1137/UnSmoke.git
```
2. Open the project in **Android Studio Koala** (or newer).
3. Build and Run the `:app` configuration on your Android device (Android 8.0+ required).

## ðŸ›¡ï¸ Privacy First

UnSmoke respects your deeply personal health data. All craving logs, journal entries, and usage statistics are stored **locally** in a Room Database. No accounts required. No cloud syncing of health data without explicit opt-in. Biometric App Lock ensures your journey remains private.

---

<div align="center">
  <p>Built for the Phase 9 Developer Challenge.</p>
</div>
