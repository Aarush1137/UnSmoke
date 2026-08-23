

## Phase 6: Polish, Notifications, and NRT Refinement (Upcoming)

### 1. Home Screen Notifications (Bell Icon)
- **Goal:** Make the Bell icon on the Home screen functional.
- **Implementation:** 
  - Create a NotificationsScreen or a ModalBottomSheet triggered by the bell icon.
  - Wire it to AchievementEngine to show recently unlocked badges, weekly check-in reminders, or pending clinical tapering recommendations.

### 2. Default Currency & Theme Accents
- **Currency:** Update UserPreferencesDataStore and SettingsViewModel to default to ‚¹ (Rupee) instead of $/?.
- **Theme Accents:** 
  - Ensure the Settings page allows selecting accents (Mint, Pink, Blue, Orange).
  - Update AppTheme in Theme.kt to dynamically read the accent from UserPreferencesDataStore and apply the primary color across all screens.

### 3. NRT Logging UX Improvements
- **Goal:** Make logging NRT faster and visually rewarding.
- **Implementation:**
  - Replace the basic "Log NRT" bottom sheet with an interactive sheet.
  - Use visual sliders for dosage and quantity.
  - Add a success confetti/checkmark animation upon logging.

### 4. "My Quit Plan" Improvements
- **Goal:** Overhaul the Quit Plan section to look more premium.
- **Implementation:**
  - Convert text-heavy plan details into visual cards.
  - Add a timeline view showing the user's progress through their personalized tapering schedule.
  - Add animated progress indicators similar to the Insights page.
## Phase 7: Advanced Analytics, CBT Recovery, and Export

### 1. Craving Heatmap & Trigger Analytics
- **Goal:** Visualize craving patterns to help users anticipate triggers.
- **Implementation:**
  - Create `AnalyticsViewModel.kt` to query `NrtRepository` / `CravingRepository` and aggregate cravings by hour of day and by trigger.
  - Build `AnalyticsScreen.kt` using a simple Canvas-based bar chart or heatmap grid.
  - Add a new tab or floating action button to access Analytics from the Insights or Progress screen.

### 2. CBT "Relapse Autopsy" (Recovery Flow)
- **Goal:** Provide a shame-free, educational flow when the user relapses.
- **Implementation:**
  - Create `RelapseAutopsyScreen.kt` with a 3-step questionnaire: (1) Emotion/Trigger, (2) NRT usage check, (3) Plan adjustment.
  - Update `HomeViewModel.kt` so clicking "I Smoked" launches this flow instead of instantly deleting the quit attempt.
  - Persist the learnings in `QuitAttemptRepository`.

### 3. Daily "Quit Coach" Micro-Lessons
- **Goal:** Boost 30-day retention with daily CBT education.
- **Implementation:**
  - Create `QuitCoachData.kt` containing a list of 30 short strings/lessons (e.g., "Day 3: The Dopamine Drop").
  - Update `HomeViewModel.kt` to expose `todaysLesson` based on `smokeFreeDays`.
  - Display it dynamically inside the newly added Daily Quit Coach card in `HomeScreen.kt`.

### 4. Clinician / Doctor Export (CSV)
- **Goal:** Allow users to export their clinical NRT tapering and craving data.
- **Implementation:**
  - Create `ExportEngine.kt` to query the last 30 days of NRT logs and write them to a `.csv` file in the cache directory.
  - Use `FileProvider` to create a Share Intent.
  - Add an "Export Report" button to `ProfileScreen.kt` or `SettingsScreen.kt`.

### 5. Social Share Cards for Milestones
- **Goal:** Allow users to share beautifully branded milestone badges.
- **Implementation:**
  - Add a "Share" icon to the badges in `InsightsScreen.kt`.
  - When clicked, generate a bitmap using Android Canvas (or a Compose capture utility) drawing the badge on a nice gradient background.
  - Pass the bitmap URI to an `Intent.ACTION_SEND` share sheet.
