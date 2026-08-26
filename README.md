<div align="center">
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

## 🌟 In-Depth Features

### ⏱️ 1. Live Ticking Quit Timer (New in v1.3.1)
The Home screen features a dynamic, real-time ticking timer that tracks the exact days, hours, minutes, and seconds since your quit date. The timer automatically resets if a relapse is logged, keeping you accountable down to the exact second.

### ⌚ 2. Wear OS Companion App & Data Sync (New in v1.3.1)
UnSmoke includes a native Wear OS application built with Compose for Wear OS. 
- Log cravings directly from your wrist.
- Real-time DataClient syncing to your phone so cravings logged on the watch appear immediately in your Heatmap.
- Beautiful, glanceable UI with the same clinical CBT timer protocols.

### 📱 3. Gemini AI Recovery Coach
An embedded, on-device AI coach powered by Google's Gemini Flash. 
- Ask for immediate psychological support during severe cravings.
- Dynamic prompts ("I feel like giving up", "I'm stressed") adjust based on your current quit phase.

### 👥 4. Multi-Buddy Accountability System (New in v2.1.0)
You are no longer alone. You can now pair with a network of quit-buddies using real-time Firebase syncing.
- **Connect with Multiple Friends**: Share your code to link with multiple people in your support network.
- **Live Stats**: See exactly how many days your buddies have been smoke-free and how much NRT they've used.
- **SOS Broadcasting**: Hit the SOS button to instantly alert all of your connected buddies if you are about to relapse.

### 🧠 5. Advanced Craving Heatmaps & Location Tracking
Uses Fused Location Provider and Google Maps SDK.
- The app automatically logs the latitude/longitude of every craving you experience.
- The **Trigger Map** visually renders a heatmap of your city so you can see geographically where you are most vulnerable (e.g., specific bars, work, stressful commutes).

### 🧬 6. NRT Tapering Algorithms
Unlike other apps that just track days, UnSmoke mathematically calculates your NRT absorption.
- Log Patches, Gum, or Lozenges.
- Calculates your live Nicotine plasma levels and guides you through clinical step-down protocols.

### 🛡️ 7. Bio-Metric Lock
Lock your recovery journal and private stats behind Android's Biometric Prompt (Fingerprint/Face Unlock).

### 🏆 8. Interactive Achievements Grid
Gamified milestones that unlock beautiful Badges as you hit 24 hours, 7 days, 1 month, etc. 

## 🛠️ Tech Stack & Architecture
- **Language**: Kotlin 2.0.0
- **UI**: Jetpack Compose (Material 3) + Compose for Wear OS
- **Architecture**: MVI / MVVM Clean Architecture
- **Dependency Injection**: Hilt / Dagger
- **Local Database**: Room DB (Offline First)
- **Live Sync**: Firebase Firestore (Anonymous Auth)
- **Location & Mapping**: Google Maps SDK, FusedLocationProviderClient
- **AI Engine**: Generative AI SDK (Gemini)

## 📥 Getting Started
1. Clone the repository: git clone https://github.com/Aarush1137/UnSmoke.git
2. Add your **Gemini API Key** and **Google Maps API Key** to local.properties:
   `properties
   GEMINI_API_KEY="your_ai_studio_key"
   MAPS_API_KEY="your_google_cloud_maps_key"
   `
3. Sync Gradle and run on a physical device or emulator.