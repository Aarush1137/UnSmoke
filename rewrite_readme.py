import os

content = '''<div align="center">
  <h1>UnSmoke 🚭</h1>
  <p><b>The Evidence-Based, Gamified Quit Smoking Companion</b></p>
</div>

---

UnSmoke is an advanced Android application designed to help users quit smoking through a combination of psychological support, **Cognitive Behavioral Therapy (CBT)** techniques, and clinical **Nicotine Replacement Therapy (NRT)** tapering protocols. Built entirely with Jetpack Compose and Kotlin 2.0.

## 📸 Screenshots

<div align="center">
  <img src="docs/screenshots/home.png" alt="Home Screen" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="docs/screenshots/timer.png" alt="Craving Timer" width="250"/>
</div>

---

## 🚀 In-Depth Features

### 🌊 1. Urge Surfing & The 4 D's Protocol
When a craving hits, users can launch the **Craving Timer**. It features a visual "breathing wave" animation that mimics the physiological curve of a craving (which usually peaks and subsides within 3-5 minutes). 
While the timer runs, the app provides the **4 D's Toolkit**:
- **Delay:** Wait 5 minutes.
- **Deep Breathe:** Follow the animated visualizer.
- **Drink Water:** Distract the oral fixation.
- **Do Something Else:** Suggests random, healthy distractions.

### 💊 2. NRT (Nicotine Replacement Therapy) Tracker
Unlike most apps that just track cold-turkey days, UnSmoke acknowledges that clinical cessation often involves NRT (patches, gums, lozenges).
- Users can log usage of NRT products.
- The app automatically calculates the **Net Money Saved** (Gross savings from avoided cigarettes minus the cost of NRT products purchased).
- Over time, the app provides a **tapering schedule** to step down NRT doses safely.

### 📊 3. Interactive Analytics & Health Connect
Track exactly how much you've saved and how many cigarettes you've avoided.
- Integrated with **Health Connect** to show physical recovery (e.g., Resting Heart Rate drops).
- Unlock 50+ milestone badges based on actual physiological recovery (e.g., "Carbon Monoxide Normal", "Cilia Regrown").

### 🧠 4. AI Quit Coach (Powered by Gemini)
- The app aggregates your craving history, triggers, and intense moments.
- Using a local or cloud Gemini LLM, the app provides **personalized 2-sentence actionable advice** on the Home screen to help you predict and avoid your highest-risk relapse windows.

---

## 📂 Project Structure

`	ext
Unsmoke/
├── app/
│   ├── src/main/kotlin/com/unsmoke/app/
│   │   ├── core/           # Room DB, Repositories, DataStore, Design System
│   │   ├── feature/        # Screen ViewModels & Compose UIs (Home, Craving, Analytics)
│   │   └── widget/         # Glance App Widgets (Dashboard, Streak)
├── wear/                   # WearOS Companion Module
└── docs/                   # Screenshots & Assets
`

## 🛠️ Build & Install

1. Clone the repository:
`ash
git clone https://github.com/Aarush1137/UnSmoke.git
`
2. Open the project in **Android Studio Koala** (or newer).
3. Build and Run the :app configuration on your Android device (Android 8.0+ required).

## 🛡️ Privacy First

UnSmoke respects your deeply personal health data. All craving logs, journal entries, and usage statistics are stored **locally** in a Room Database. No accounts required. No cloud syncing of health data without explicit opt-in. Biometric App Lock ensures your journey remains private.

---

<div align="center">
  <p>Built for the Phase 9 Developer Challenge.</p>
</div>'''

with open("E:\\Projects\\Unsmoke\\README.md", "w", encoding="utf-8") as f:
    f.write(content)

print("Done")
