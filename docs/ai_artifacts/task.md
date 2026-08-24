# Phase 10: Gamification & Deep Personalization

## Pre-Sprint Polish & Security
- [x] Create SECURITY.md (Play Store compliance).
- [x] Create irestore.rules and irebase.json for Buddy System security.
- [x] Fix UpdateChecker.kt bug (showing v1.2.2 instead of latest v1.3.1).

## Sprint 1: Financial Wishlist (The Reward Engine)
- [ ] **Database & DI**
  - [x] Verify RewardGoalEntity structure.
  - [x] Create RewardDao with CRUD operations (Flow list, insert, delete, mark as achieved).
  - [x] Update AppDatabase to include the DAO.
  - [x] Update DatabaseModule to provide RewardDao.
  - [x] Create RewardRepository.
- [ ] **Domain & ViewModel**
  - [x] Create RewardsViewModel to expose a combined Flow of (rewardGoals, netMoneySaved).
- [ ] **UI Implementation**
  - [x] Create RewardsScreen.kt:
    - [x] LazyColumn of user goals.
    - [x] Visual progress bars matching 
etMoneySaved / targetAmount.
    - [x] Confetti or visual distinction for chieved goals.
    - [x] "Add Goal" ModalBottomSheet or Dialog.
  - [x] Update AppNavGraph.kt to include the ewards route.
  - [x] Update HomeScreen.kt to link to the Rewards screen (e.g., a "Rewards" button next to Insights, or a mini-widget).

## Future Sprints (Backlog)
- [x] Sprint 2: Location-Based Trigger Heatmaps (Predictive AI)
  - [x] Room Migration to support lat/lng
  - [x] Location permissions & FusedLocationProviderClient integration
  - [x] Google Maps Compose integration
  - [x] TriggerMapScreen UI & ViewModel
- [x] Sprint 3: Wear OS Watch Face Complication
  - [x] Integrate watchface-complications-data-source-ktx dependency
  - [x] Build UnSmokeComplicationService.kt to expose current Quit Streak
  - [x] Wire up AndroidManifest.xml for Wear OS complication discovery
- [ ] Sprint 4: Vaping & E-Cigarette Cessation Mode
- [ ] Sprint 5: Virtual Companion (Tamagotchi-style Gamification)
## Bug Fixes & Refinements
- [x] **Bug 1: Accent Colors & Themes** - Settings accent colors and light/dark theme are selected but don't apply to the UI.
- [x] **Bug 2: Unimplemented Settings** - Certain settings (like Notifications) are placeholders and don't work. Need to hide or implement.
- [x] **Bug 3: Dashboard Timer** - When selecting "Today" in onboarding, the timer starts from 12:00 AM instead of 0 (current time).
- [x] **Bug 4: Settings Version** - Settings screen still hardcoded to show v1.2.2.
- [x] **Bug 5: Quit Buddy API Key** - "API key not valid error" in Quit Buddy screen.