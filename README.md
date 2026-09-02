<div align="center">
  <h1>🚭 UnSmoke</h1>
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

## 🌟 In-Depth Features

### ⏱️ 1. Live Ticking Quit Timer
The Home screen features a dynamic, real-time ticking timer that tracks the exact days, hours, minutes, and seconds since your quit date. The timer automatically resets if a relapse is logged, keeping you accountable down to the exact second.

### ⌚ 2. Wear OS Companion App & Data Sync
UnSmoke comes with a fully native smartwatch app! 
- **Live Sync:** The Data Layer automatically syncs your active quit attempt from your phone to your wrist.
- **Wrist Dashboard:** View your live ticking timer directly on your smartwatch.
- **Haptic Breathing:** Trigger the "Breathe" function on your watch to launch a visually expanding orb synchronized with physical haptic vibrations to guide you through a 4-7-8 breathing exercise without ever touching your phone.
- **Emergency SOS:** Send immediate alerts from your wrist when a craving peaks.

### ☁️ 3. Cloud Backup & Firestore Sync
Never lose your progress! UnSmoke automatically links your anonymous identity to Google Cloud Firestore.
- **Auto-Sync:** Cravings, NRT usages, and Quit Attempts are safely backed up to the cloud.
- **30-Day Auto Delete:** To protect your privacy and reduce server costs, inactive accounts (no app launches for 30 days) are automatically purged via Firestore TTL (Time-To-Live) policies.

### 🤖 4. AI Coach & Insight Analytics
Powered by Google's latest `gemini-flash-latest` model.
- **CBT Chat:** Chat with an empathetic AI therapist specializing in urge surfing and grounding techniques.
- **Relapse Predictions:** The AI analyzes your craving logs (intensity, time of day, triggers) to predict high-risk periods and provide actionable interventions.

### 🏄 5. Urge Surfing & The 4 D's Protocol
When a craving hits, launch the **Craving Timer**. It features a visual "breathing wave" animation that mimics the physiological curve of a craving (which usually peaks and subsides within 3-5 minutes). 
While the timer runs, the app provides the **4 D's Toolkit**:
- **Delay:** Wait 5 minutes.
- **Deep Breathe:** Follow the animated visualizer.
- **Drink Water:** Distract the oral fixation.
- **Do Something Else:** Suggests random, healthy distractions.

### 💊 6. NRT (Nicotine Replacement Therapy) Tracker
Unlike most apps that just track cold-turkey days, UnSmoke acknowledges that clinical cessation often involves NRT (patches, gums, lozenges).
- Users can log usage of NRT products.
- The app calculates the **Net Money Saved** (Gross savings from avoided cigarettes minus the cost of NRT products purchased).
- Over time, the app provides a **tapering schedule** to step down NRT doses safely.

### 💸 7. Localized Cost Tracking
Designed for global users, the onboarding flow asks for the exact average cost of *one* cigarette rather than assuming standard 20-pack sizes. This allows users who buy loose cigarettes or smaller packs (highly common in India and other regions) to get perfectly accurate daily and yearly savings calculations.

---

## 🛠️ Architecture & Tech Stack

UnSmoke follows **Modern Android Development (MAD)** guidelines:

- **UI:** Jetpack Compose (Material 3)
- **Architecture:** Clean Architecture + MVVM (Model-View-ViewModel)
- **Dependency Injection:** Hilt (Dagger)
- **Local Database:** Room Database
- **Remote Database:** Firebase Firestore & Firebase Auth (Anonymous)
- **AI Integration:** Google Generative AI SDK (`gemini-flash-latest`)
- **Wearables:** Wear OS Data Layer API
- **Concurrency:** Kotlin Coroutines & Flows
- **CI/CD:** GitHub Actions (Automated APK builds with Secure Secret Injection)

## 📦 Building from Source

To build UnSmoke locally:

1. Clone the repository.
2. Ensure you are using **JDK 21**.
3. Set up your local properties:
   Create a `local.properties` file in the root directory and add:
   ```properties
   MAPS_API_KEY=your_google_maps_key_here
   GEMINI_API_KEY=your_gemini_api_key_here
   ```
4. Build the project using Gradle:
   ```bash
   ./gradlew :app:assembleDebug
   ./gradlew :wear:assembleDebug
   ```

*(Note: The GitHub Actions CI/CD pipeline will automatically inject the Gemini API key from GitHub Secrets for release builds!)*
