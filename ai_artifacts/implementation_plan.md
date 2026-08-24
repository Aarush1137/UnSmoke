# UnSmoke: Phase 10 Implementation Plan

## Goal Description
Phase 9 successfully established our external ecosystem (Wear OS, Health Connect) and social accountability (Firebase Buddy). For **Phase 10**, the goal is to deepen user engagement through advanced gamification (Financial Goals), predictive analytics (Location Heatmapping), broader addiction support (Vaping), and deeper Wear OS integration.

Below are 5 proposed Sprints for Phase 10. 

## User Review Required
> [!IMPORTANT]
> Please review the proposed sprints below. Let me know which of these features you want to prioritize, modify, or drop! Once approved, we will begin with Sprint 1.

---

### Sprint 1: Financial Wishlist (The "Reward" Engine)
We currently calculate "Net Money Saved." We should gamify this by allowing users to set tangible rewards.
- **Wishlist UI:** A new tab where users can add items they want to buy (e.g., "PlayStation 5", "Trip to Bali") with a target price.
- **Progress Bars:** Visually fill up the progress bar toward the item using the live `Net Money Saved` calculation.
- **Confetti/Reward:** A celebratory animation when a goal is funded by their quit journey.

### Sprint 2: Location-Based Trigger Heatmaps (Predictive AI)
Cravings are often tied to physical locations (the bar, a specific street corner).
- **Location Tagging:** When logging a craving via the phone or Wear OS, optionally capture the GPS coordinate.
- **Trigger Map:** A new map UI (Google Maps SDK) showing a heatmap of their high-risk areas.
- **Geofence Alerts (Optional):** A background service that gently nudges the user with a CBT reminder if they linger in a high-risk location.

### Sprint 3: Wear OS Watch Face Complication
We have a Wear OS app, but users shouldn't have to open it to see their streak.
- **ComplicationProviderService:** Build a native Wear OS complication (a small widget for the watch face).
- **Data:** Display the live Days/Hours quit streak directly on their favorite watch face.

### Sprint 4: Vaping & E-Cigarette Cessation Mode
Currently, the app focuses heavily on combustible cigarettes and NRTs. Vaping is a massive demographic.
- **Vape Onboarding:** Add a toggle during onboarding: "I smoke cigarettes" vs. "I vape".
- **Titration Engine:** Track vape juice (ml) or pod consumption, and calculate tapering schedules based on nicotine mg/ml reduction.

### Sprint 5: Virtual Companion (Tamagotchi-style Gamification)
To increase daily retention, give the user something to take care of that reflects their own health.
- **Visual Mascot:** A healthy lung, a plant, or a tiny character that "heals" and grows stronger every day the user remains smoke-free.
- **Relapse Impact:** If a relapse is logged, the companion loses some health/XP, adding a powerful psychological deterrent.

---

## Open Questions
1. Do you want to implement all 5 of these sprints, or are there specific ones that stand out to you as the highest priority?
2. For the **Virtual Companion** (Sprint 5), what kind of visual would you prefer? A growing plant, a character, or a literal lung?
3. For **Vaping** (Sprint 4), do you want to calculate money saved per pod/bottle instead of per pack?

## Proposed Changes (For Sprint 1: Financial Wishlist)
If we start with Sprint 1, the following components will be modified:

### Domain / Database
#### [NEW] `RewardGoalEntity.kt` (Room Database Entity)
#### [NEW] `RewardDao.kt` & `RewardRepository.kt`

### Feature / UI
#### [NEW] `RewardsScreen.kt` & `RewardsViewModel.kt`
#### [MODIFY] `AppNavGraph.kt` (To add the Rewards route)
#### [MODIFY] `HomeScreen.kt` (To show a mini-preview of their top financial goal)

## Verification Plan
- Deploy the app and add a test goal of $500.
- Fast-forward the quit date to simulate $300 saved and verify the progress bar visually hits 60%.
- Ensure the Room database successfully persists the user's wishlist across app reboots.