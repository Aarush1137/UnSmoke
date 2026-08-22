# UnSmoke — Implementation Plan

A premium native Android quit-smoking + NRT companion app.

---

## Background

UnSmoke helps smokers quit cigarettes and manage nicotine-replacement therapy (NRT).  
The central experience: **"Help me get through the next craving without smoking."**  
The app must feel like a premium 2026 consumer wellness product — calm, personal, supportive, never clinical.

---

## User Review Required

> [!IMPORTANT]
> **AI Coach**: Based on your answer ("no cost"), the AI Coach will be built as a **rule-based / scripted coach** — no LLM API calls, no backend required, completely free. The coach responds intelligently using the user's own stored data (streak, triggers, reasons, mood) and a carefully written response library. This can be swapped for a real LLM later if desired.

> [!IMPORTANT]
> **Firebase / Cloud Sync**: Because you selected "no cost", there will be **no Firebase or cloud backend**. All data is stored locally on-device using **Room** (SQLite). The user can export data as JSON/CSV. No user accounts, no server costs.

> [!IMPORTANT]
> **Currency**: Indian Rupee (₹ / INR) is the default. Currency symbol will be configurable in Settings for international users.

---

## Open Questions

> [!NOTE]
> **App package name**: Will use `com.unsmoke.app` unless you specify otherwise.

> [!NOTE]
> **Minimum Android version**: Plan is to target **Android 8.0 (API 26+)** which gives access to `java.time` for timezone-safe date math without a compatibility library.

> [!NOTE]
> **App icon / brand colors**: The breathing orb motif suggests a **deep navy / teal / soft white** palette with optional AMOLED dark mode. Will generate a suggested color system — you can review before finalizing.

> [!NOTE]
> **Default cigarette price**: **₹25 per cigarette** (no pre-fill; user must enter their own).

> [!NOTE]
> **NRT Cost Tracking (Nicotex default)**: Default NRT example = **Nicotex chewing gum, ₹80 for a pack of 9**. During NRT setup, the app will ask:
> - Which NRT product are they using?
> - How many units are in a pack?
> - What is the price of one pack?
> - Cost per unit is calculated automatically: `packPrice ÷ unitsPerPack`
> 
> Savings screen shows three values:
> - **Gross savings** = cigarettes avoided × price per cigarette
> - **NRT expenditure** = NRT units logged × cost per unit
> - **Net savings** = Gross − NRT expenditure
> 
> This gives an honest, accurate picture of real money saved. Supports any NRT product.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Platform | Android (API 26+) |
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | Clean Architecture + MVVM |
| DI | Hilt |
| Database | Room (SQLite, local-only) |
| Preferences | DataStore Preferences |
| Async | Kotlin Coroutines + Flow |
| Navigation | Navigation Compose |
| Charts | Vico (Compose-native) |
| Animations | Compose Animation API + Lottie |
| Background | WorkManager |
| Notifications | Android Notification APIs |
| Date/Time | `java.time` (API 26+, no desugaring needed) |
| Serialization | Kotlin Serialization (JSON export) |
| Testing | JUnit4 + MockK + Turbine + Compose UI Tests |
| Build | Gradle Kotlin DSL + Version Catalogs |

---

## Project Module Structure

```
E:\Projects\Unsmoke\
├── app/                          # Application shell, DI graph, navigation host
├── core/
│   ├── data/                     # Room DB, DAOs, DataStore, Repositories
│   ├── domain/                   # Use cases, models, business logic
│   ├── designsystem/             # Theme, colors, typography, shared UI components
│   ├── notifications/            # WorkManager workers, notification channels
│   └── common/                   # Extensions, utils, date helpers, formatters
└── feature/
    ├── onboarding/               # Conversational onboarding flow
    ├── home/                     # Dashboard, progress ring, metrics
    ├── craving/                  # Craving button, intervention engine, timer
    ├── nrt/                      # NRT tracker, NRT journal, NRT dashboard
    ├── progress/                 # Analytics, charts, achievements, timeline
    ├── checkin/                  # Daily check-in, weekly review
    ├── journal/                  # Private journal, mood tracking
    ├── coach/                    # Rule-based AI coach
    ├── settings/                 # All settings, data export, privacy
    └── quitplan/                 # Quit plan, implementation intentions, quit reasons
```

---

## Proposed Changes

### Phase 1 — Foundation

#### [NEW] Gradle project setup
- `settings.gradle.kts` — multi-module project definition
- `build.gradle.kts` (root) — plugin declarations
- `gradle/libs.versions.toml` — version catalog for all dependencies
- `app/build.gradle.kts` — app module build config
- `app/src/main/AndroidManifest.xml` — permissions, activities, services

#### [NEW] `core/designsystem`
- `Theme.kt` — Material 3 `MaterialTheme` with light/dark variants
- `Color.kt` — UnSmoke brand color palette (navy, teal, soft white, accent)
- `Typography.kt` — large-number display fonts, body text
- `Shape.kt` — rounded corner system
- `Components.kt` — `PrimaryButton`, `SecondaryButton`, `MetricCard`, `ProgressRing`, `CravingSlider`, `TriggerChip`, `MoodSelector`, `MilestoneCard`, `QuoteCard`, `SavingsGoalCard`
- `BreathingOrb.kt` — animated breathing orb composable (shared across craving + home)

---

### Phase 2 — Data Layer

#### [NEW] `core/data/database`
- `UnSmokeDatabase.kt` — Room database class

**Tables (Room entities):**

| Entity | Key Fields |
|---|---|
| `UserProfile` | name, notificationStyle, currency, createdAt |
| `QuitAttempt` | id, startEpochMillis, endEpochMillis, status (ACTIVE/ENDED), cigarettesPerDay, cigarettesPerPack, packPrice, packCurrency, timezone |
| `SmokingBaseline` | quitAttemptId, yearsSmoked, weekendCigsPerDay, firstCigAfterWaking, strongestPeriod |
| `SmokingEvent` | id, quitAttemptId, timestamp, cigaretteCount, trigger, mood, notes |
| `CravingEvent` | id, quitAttemptId, timestamp, intensity, trigger, location, intervention, outcome (DEFEATED/SMOKED/ABANDONED), durationSeconds, nrtUsedBefore, mood |
| `NRTProduct` | id, type (GUM/LOZENGE/PATCH/SPRAY/INHALER/OTHER), name, nicotineStrengthMg, unit |
| `NRTUsage` | id, productId, timestamp, quantity, cravingBefore, cravingAfter, trigger, notes |
| `NRTReminder` | id, productId, hour, minute, enabled, days |
| `MoodEntry` | id, date, mood (1-5), emotions (JSON array), notes |
| `DailyCheckIn` | id, date, dayRating, smoked, cravingLevel, topHelper, tomorrowFocus |
| `JournalEntry` | id, timestamp, content (encrypted opt), mood, tags (JSON) |
| `Milestone` | id, type, achievedAt, value |
| `Achievement` | id, type, unlockedAt, displayedToUser |
| `QuitReason` | id, category, customText, quitAttemptId |
| `TriggerPattern` | id, trigger, count, lastSeen, hourOfDay, quitAttemptId |
| `RewardGoal` | id, name, targetAmount, currency, achieved |
| `NotificationPreference` | id, type, enabled, scheduledHour, scheduledMinute |

> [!NOTE]
> **Never store derived values** (streak, cigarettesAvoided, moneySaved) in the database. Always calculate from raw timestamps + baseline.

#### [NEW] `core/domain`
- `CalculationEngine.kt` — all dynamic calculations:
  - `smokeFreeDuration(quitAttempt): Duration`
  - `cigarettesAvoided(quitAttempt): Double`
  - `moneySaved(quitAttempt): Double`
  - `currentStreak(attempts): Int` (smoke-free days)
  - `longestStreak(attempts): Int`
  - `totalSmokeFreeDays(attempts): Int`
  - `packsAvoided(cigarettesAvoided, perPack): Double`
- Use cases: `GetActiveQuitAttemptUseCase`, `LogCravingUseCase`, `LogNRTUseCase`, `LogSmokingEventUseCase`, `CompleteCheckInUseCase`, `GetInsightsUseCase`, `GetAchievementsUseCase`, etc.

---

### Phase 3 — Onboarding

#### [NEW] `feature/onboarding`
- `OnboardingGraph.kt` — navigation graph for 12–15 onboarding screens
- Screen 1: Welcome ("Let's get you free.")
- Screen 2: Quit status (Today / Already started / Planning)
- Screen 3: Date picker (if "already started") with immediate calculation display
- Screen 4: Smoking baseline (cigs/day, pack size, pack price)
- Screen 5: Years smoking
- Screen 6: Quit reasons (multi-select chips)
- Screen 7: Common triggers (multi-select chips)
- Screen 8: NRT setup (type, strength, schedule)
- Screen 9: Notification style preference (Gentle/Direct/Tough-love/Minimal)
- Screen 10: Savings goal (optional)
- Screen 11: Name (optional)
- Screen 12: Completion ("You're already 3 days in. Let's keep it going.")
- Animated progress indicator at top
- One question per screen, large typography
- Back navigation support
- All data persisted to Room on completion

---

### Phase 4 — Home Dashboard

#### [NEW] `feature/home`
- `HomeScreen.kt` — main dashboard
- Top section: personalized greeting + smoke-free counter (days, large display)
- Animated progress ring (circular arc, glowing)
- Metric cards: cigarettes avoided, money saved, cravings defeated, NRT logged
- "CRAVING" FAB — prominent, always accessible
- "WHY I QUIT" quick button
- This week's progress bar (days smoke-free / 7)
- Next milestone countdown
- Rotating context-aware quote card
- State-adaptive: normal / craving-active / post-difficult-day / milestone / lapse-recovery
- `HomeViewModel.kt` — collects all live data via Flow

---

### Phase 5 — Craving System

#### [NEW] `feature/craving`
- `CravingScreen.kt` — main craving entry (intensity slider)
- `TriggerSelectionScreen.kt` — trigger chip grid
- `NeedSelectionScreen.kt` — what do you need right now?
- `CravingInterventionScreen.kt` — intervention engine
  - Breathing orb animation (30–60 sec guided)
  - 10-minute delay timer with calming visuals
  - Distraction quick actions
  - Cognitive reframe cards
- `CravingTimerScreen.kt` — countdown with animation
- `CravingOutcomeScreen.kt` — "Did you smoke?" → victory or recovery flow
- `EmergencyModeScreen.kt` — full-screen ultra-simple mode (1-tap from home)
- `CravingHistoryScreen.kt` — list + charts
- `CravingViewModel.kt` + `CravingInterventionEngine.kt`

**Mini-games** (distraction feature):
- `BubbleTapGame.kt` — tap bubbles before they disappear
- `BreathingOrbGame.kt` — follow the orb expansion/contraction
- `MemorySequenceGame.kt` — pattern recall
- `FocusDotsGame.kt` — concentration exercise

---

### Phase 6 — NRT Tracker

#### [NEW] `feature/nrt`
- `NRTDashboardScreen.kt` — today's count, last used, craving reduction chart
- `NRTLogScreen.kt` — log NRT use (product, quantity, craving before/after, trigger)
- `NRTProductSetupScreen.kt` — add/edit NRT products
- `NRTReminderScreen.kt` — schedule reminders
- `NRTHistoryScreen.kt` — usage history + trends
- `NRTViewModel.kt`
- Medical disclaimer displayed clearly
- No personalized dosing advice generated

---

### Phase 7 — Progress & Analytics

#### [NEW] `feature/progress`
- `ProgressScreen.kt` — main analytics dashboard
- `AchievementsScreen.kt` — unlocked + locked achievements grid
- `TimelineScreen.kt` — vertical journey timeline
- `PersonalRecordsScreen.kt` — personal bests
- `InsightsScreen.kt` — generated insights cards
- `QuitAttemptsScreen.kt` — all attempts history (attempt 1, 2, current)

Charts (Vico):
- Smoke-free days per week
- Cravings by hour of day
- Cravings by trigger (bar/pie)
- Money saved over time (line)
- NRT usage by day
- Mood trend

**Achievements** (30+ defined):
- First Day, First Week, Double Digits, Two Weeks, Month One
- 100/500/1000 Cigarettes Avoided
- ₹5,000/₹10,000/₹25,000 Saved
- 10/50/100 Cravings Defeated
- Trigger Master, Comeback, Check-in Streak, NRT Consistent
- etc.

---

### Phase 8 — Daily Check-in & Weekly Review

#### [NEW] `feature/checkin`
- `DailyCheckInScreen.kt` — 5-question daily check-in (< 60 seconds)
- `WeeklyReviewScreen.kt` — auto-generated weekly summary
- `WeeklyReviewViewModel.kt` — aggregates week data into narrative insights

---

### Phase 9 — Journal & Mood

#### [NEW] `feature/journal`
- `JournalScreen.kt` — list of entries with search
- `JournalEntryScreen.kt` — write entry with mood + tags
- `MoodTrackingScreen.kt` — standalone mood selector
- Prompted with optional writing prompts
- Optional: tag entries with cravings, NRT, triggers

---

### Phase 10 — Rule-Based Coach

#### [NEW] `feature/coach`
- `CoachScreen.kt` — chat-style interface
- `CoachEngine.kt` — rule-based response engine:
  - Evaluates: streak, craving history, triggers, reasons, mood, lapse history
  - Selects from 200+ written responses across categories
  - Responds to: "I want a cigarette", "I had a hard day", "I slipped", etc.
  - Launches craving mode from coach context
- Clearly labeled "Coach" (not "AI Doctor")
- Includes prompt to seek professional support when appropriate

---

### Phase 11 — Notifications

#### [NEW] `core/notifications`
- `NotificationChannels.kt` — define all channels
- `MorningWorker.kt` — daily morning message
- `EveningWorker.kt` — evening reflection prompt
- `NRTReminderWorker.kt` — personalized NRT reminders
- `MilestoneWorker.kt` — achievement/milestone alerts
- `HighRiskWindowWorker.kt` — proactive high-risk period warnings
- All notifications privacy-safe (no sensitive data in notification body)

---

### Phase 12 — Settings & Privacy

#### [NEW] `feature/settings`
- `SettingsScreen.kt` — main settings
- `QuitDateEditScreen.kt` — change quit date/time
- `SmokingBaselineEditScreen.kt` — update smoking baseline
- `NotificationSettingsScreen.kt` — all notification controls
- `PrivacyScreen.kt` — data export, delete data, delete journal
- `MedicalDisclaimerScreen.kt` — clear, non-intrusive
- `DataExportScreen.kt` — JSON/CSV export
- `AppLockScreen.kt` — BiometricPrompt integration (optional)
- `ThemeScreen.kt` — light/dark/system

---

### Phase 13 — Quit Plan & Implementation Intentions

#### [NEW] `feature/quitplan`
- `QuitPlanScreen.kt` — personalized quit plan summary
- `QuitReasonsScreen.kt` — view/edit quit reasons (the "Why I Quit" screen)
- `ImplementationIntentionsScreen.kt` — "When X, I will Y" builder
- `SocialSituationModeScreen.kt` — "I'm around smokers" feature
- `RewardShopScreen.kt` — personal reward goals

---

### Phase 14 — Lapse / Relapse Flow

Implemented within `feature/craving`:
- `LapseScreen.kt` — non-shaming slip flow
- `LapseRecoveryScreen.kt` — understanding what happened + next steps
- `NewAttemptScreen.kt` — start a new quit attempt (preserves history)
- Historical stats always preserved across attempts

---

### Phase 15 — Polish

- Smooth shared element transitions between screens
- Counter animations (number roll-up)
- Progress ring animated on first load
- Milestone fullscreen celebration sheet
- Haptic feedback for craving defeated, achievements
- Empty state illustrations for every list screen
- Error handling: graceful fallbacks for all calculations
- Undo snackbar for all logging actions
- Reduced motion mode support

---

## Verification Plan

### Build Verification
```bash
./gradlew assembleDebug
./gradlew test
./gradlew connectedAndroidTest
```

### Key Scenarios to Manually Verify
1. Backdate quit date to Aug 20 → home shows "3 days smoke-free", correct cigs avoided + money saved
2. Log craving → complete intervention → mark defeated → reflected in analytics
3. Log smoking event → lapse flow → historical stats preserved → new attempt
4. NRT log → craving before/after → dashboard updates
5. Daily check-in saves → appears in weekly review
6. Change quit date → all calculations update immediately
7. Offline mode: no internet → all features work
8. Delete data → all user data cleared

### Automated Tests
- `CalculationEngineTest.kt` — unit tests for all dynamic calculations
- `QuitAttemptDaoTest.kt` — Room DAO tests
- `OnboardingViewModelTest.kt` — state machine tests
- `CravingViewModelTest.kt` — craving flow tests
- `HomeScreenTest.kt` — Compose UI tests

---

> [!TIP]
> The build order follows the spec: Design System → Navigation → Onboarding → Quit Date Engine → Baseline → Home → Calculations → NRT → Craving → Emergency Mode → Check-in → Lapse → Progress → Insights → Notifications → Reasons → Journal → Achievements → Coach → Privacy/Settings → Offline → Tests → Polish

