<div align="center">
  <h1>UnSmoke</h1>
  <p><b>The Evidence-Based, Gamified Quit Smoking Companion</b></p>
  <p>
    <img src="https://img.shields.io/badge/version-3.2.1-blue.svg" alt="Version 3.2.1" />
    <img src="https://img.shields.io/badge/build-versionCode%2012-success.svg" alt="Build 12" />
    <img src="https://img.shields.io/badge/platform-Android%20%7C%20Wear%20OS-brightgreen.svg" alt="Platforms" />
    <img src="https://img.shields.io/badge/tests-100%25%20passing-brightgreen.svg" alt="Unit Tests Passing" />
  </p>
</div>

---

UnSmoke is an advanced, clinical-grade Android and Wear OS application designed to empower individuals on their journey to quit smoking and vaping. Combining **Cognitive Behavioral Therapy (CBT)**, **Nicotine Replacement Therapy (NRT)** tapering protocols, **Google Gemini Generative AI**, and a gamified **Virtual Companion**, UnSmoke turns evidence-based addiction science into daily habit transformation.

## 📸 Screenshots

<div align="center">
  <img src="docs/screenshots/home.png" alt="Home Screen" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="docs/screenshots/timer.png" alt="Craving Timer" width="250"/>
</div>

---

## 🚀 What's New in v3.2.1 (Calculations & Cloud Resilience Update)

Version 3.2.1 resolves financial and metrics calculation anomalies, delivers automated active-attempt database self-healing, provides full diagnostic transparency for AI Coach, and adds interactive connection retry for Quit Buddy networking.

| Feature / Fix | Severity | Status | Key Highlights |
|---|:---:|:---:|---|
| **Onboarding Unit Price** | 🔴 **CRITICAL** | ✅ Resolved | Onboarding explicitly prompts for unit piece cost (e.g. ₹25/cig or pod); fixed erroneous 20-fold division so `pricePerCigarette` correctly stores true piece price and `packPrice` computes as `unitPrice * perPack`. |
| **Cigs Avoided Metric Display** | 🔴 **CRITICAL** | ✅ Resolved | Added `cigarettesAvoided` to `HomeUiState`; `HomeScreen.kt` now displays actual avoided cigarettes count rather than days smoke-free. |
| **Active Attempt DB Self-Healing** | 🟠 **HIGH** | ✅ Resolved | Automatically detects and repairs existing Room DB attempts where `pricePerCigarette < 5.0 && packPrice >= 10.0`, immediately restoring accurate money saved (~₹989) and avoided counts (~40) without re-onboarding. |
| **AI Coach Diagnostics & Upgrade** | 🟡 **MEDIUM** | ✅ Resolved | Standardized model identifier to `gemini-1.5-flash`; surfaces actual API/connection error messages directly in chat bubbles for instant troubleshooting. |
| **Buddy Network Diagnostics & Retry** | 🟡 **MEDIUM** | ✅ Resolved | Unmasked underlying Firebase auth/Firestore exception reasons in mock card; added an interactive **Retry Connection** button. |

> Detailed technical documentation and line-by-line verification diffs are available in [ai_artifacts/BUG_FIX_ANALYSIS.md](ai_artifacts/BUG_FIX_ANALYSIS.md) and [ai_artifacts/BUG_FIX_PROGRESS.md](ai_artifacts/BUG_FIX_PROGRESS.md).

---

## 🌟 Core Features

### ⏱️ 1. Live Ticking Quit Timer
- Real-time second-by-second countdown tracking smoke-free duration, money saved, and cigarettes avoided.
- Relapse/lapse reset logic that preserves your historical quit attempts and insights.

### ⌚ 2. Native Wear OS Companion App
- **Wearable Data Layer Sync:** Active quit timestamps sync automatically from phone to smartwatch.
- **Wrist Dashboard:** Live smoke-free timer right on your watch.
- **Haptic Breathing Exercise:** Guided 4-7-8 breathing circle with synchronized tactile haptic vibrations.
- **Emergency SOS Alert:** One-tap emergency broadcast sent from wrist to your paired buddy.

### 🤖 3. AI Coach & Personalized Analytics
- **Gemini AI Integration:** Powered by Google's latest `gemini-flash-latest` model.
- **CBT Therapy Chat:** Conversational agent specialized in urge surfing, grounding, and cognitive reframing.
- **High-Risk Window Prediction:** Analyzes historical craving times and triggers to predict vulnerability hours.

### 🏄 4. Craving Timer & The 4 D's Protocol
- Visual animated breathing orb matching the physiological 3-5 minute craving curve.
- **The 4 D's Protocol:** Delay (timer), Deep Breathe (visual orb), Drink Water (oral substitute), Do Something Else (distraction suggestions).
- Shared ViewModel state between craving setup, acute timer, and outcome screens.

### 💊 5. Clinical Nicotine Replacement Therapy (NRT) Tracker
- Logs gums, patches, lozenges, and inhalers.
- Evidence-based tapering schedules stepping down dosages safely over time.
- **Net Savings Calculation:** Total cigarette money saved minus NRT expenditure.

### 👥 6. Anonymous Peer Buddy System
- 6-digit pairing code generation backed by Firebase Firestore.
- Real-time progress updates, shared milestones, and mutual emergency craving SOS alerts.
- Built-in privacy controls with 30-day TTL cloud account cleanup.

### 🐾 7. Gamified Virtual Companion
- Tamagotchi-inspired interactive companion that mirrors your lung health and streak.
- Feeds on resisted cravings and daily check-ins; reflects sadness or health drops on lapses.

### 🔒 8. Security & Data Management
- **Biometric App Lock:** Optional fingerprint and face unlock via AndroidX Biometric.
- **Encrypted Cloud Backup:** One-tap manual or background encrypted sync to Firestore.
- **Clinician Export:** Generates standardized CSV/JSON summaries for healthcare providers.

### 📱 9. Home Screen Streak Widget
- Glance-based and RemoteViews desktop widget displaying current smoke-free streak and money saved.
- Interactive tap-to-launch navigation directly to the dashboard.

---

## 🛠️ Architecture & Tech Stack

- **Language & Platform:** Kotlin 2.0, JDK 21, Android SDK (Min 26, Target 36)
- **UI Toolkit:** Jetpack Compose with Material 3 Design System
- **Architecture:** Clean Architecture + MVVM + Unidirectional Data Flow (UDF)
- **Dependency Injection:** Hilt (Dagger) with KSP
- **Local Persistence:** Room Database v6 (MIGRATION_1_2 through MIGRATION_5_6) + DataStore Preferences
- **Cloud Infrastructure:** Firebase Firestore, Firebase Authentication (Anonymous)
- **AI Engine:** Google Generative AI SDK (`gemini-flash-latest`)
- **Wearable Integration:** Wear OS Compose + Google Play Services Wearable Data Layer
- **Background Processing:** AndroidX WorkManager with Hilt worker injection
- **CI/CD Pipeline:** GitHub Actions automated release pipeline producing both release and debug APKs

---

## 📦 Building from Source

### Prerequisites
- **JDK 21** installed and configured in your environment.
- **Android SDK** with Platform 36 and Build-Tools installed.

### 1. Clone & Configure
```bash
git clone https://github.com/Aarush1137/UnSmoke.git
cd UnSmoke
```

### 2. Configure Local Properties & Google Services
Create a `local.properties` file in the project root:
```properties
MAPS_API_KEY=your_google_maps_key_here
GEMINI_API_KEY=your_gemini_api_key_here
```

For Firebase services, copy the sanitized template:
```bash
cp app/google-services.json.example app/google-services.json
# Edit app/google-services.json with your Firebase project credentials
```

### 3. Build & Run Tests
```bash
# Run all unit tests
./gradlew testDebugUnitTest

# Build Android phone APKs (Release & Debug)
./gradlew :app:assembleRelease :app:assembleDebug

# Build Wear OS smartwatch APK
./gradlew :wear:assembleRelease :wear:assembleDebug
```

---

## 📄 License

This project is developed for educational and healthcare companion purposes. See the [LICENSE](LICENSE) file for details.
