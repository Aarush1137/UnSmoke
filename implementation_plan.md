

## Phase 6: Polish, Notifications, and NRT Refinement (Upcoming)

### 1. Home Screen Notifications (Bell Icon)
- **Goal:** Make the Bell icon on the Home screen functional.
- **Implementation:** 
  - Create a NotificationsScreen or a ModalBottomSheet triggered by the bell icon.
  - Wire it to AchievementEngine to show recently unlocked badges, weekly check-in reminders, or pending clinical tapering recommendations.

### 2. Default Currency & Theme Accents
- **Currency:** Update UserPreferencesDataStore and SettingsViewModel to default to ₹ (Rupee) instead of $/?.
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