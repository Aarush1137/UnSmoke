# Virtual Companion (Sprint 5)

This sprint introduces a Tamagotchi-style gamified "Virtual Companion" that evolves and heals alongside the user's smoke-free journey. Taking care of the companion reinforces positive habits.

## Proposed Changes

### 1. Database & State
#### [NEW] CompanionEntity (or integrate into UserProfileEntity)
Since there is only one companion per user, we can either create a new CompanionEntity or add fields to QuitAttemptEntity / UserProfileEntity.
- **Fields Needed**: companionName, healthPoints (0-100), evolutionStage (0-3), lastFedTimestamp.

### 2. Companion Mechanics
- **Healing**: Health points naturally regenerate for every smoke-free day.
- **Taking Damage**: If the user relapses (logs a SmokingEvent), the companion takes massive damage and loses an evolution stage.
- **Feeding/Care**: Users can "Care" for the companion by logging completed cravings, doing breathing exercises, or drinking water.

### 3. User Interface
#### [MODIFY] BuddyScreen.kt -> CompanionScreen.kt
- Repurpose the current "Human Buddy" screen into the "Virtual Companion" hub.
- Render the companion visually using Canvas or a series of animated Vectors based on its evolutionStage (e.g., Seedling -> Sprout -> Plant -> Tree).
- Add interaction buttons: "Water / Care", "Check Stats".

#### [MODIFY] HomeScreen.kt
- Add a mini-widget on the dashboard showing the companion's face/health bar so the user always sees it.

## Open Questions

> [!IMPORTANT]
> **Companion Theme:** What kind of companion do you want? 
> **Option A:** A cute, expressive "Lung" character that gets healthier and happier.
> **Option B:** A "Tree of Life" (Plant) that starts as a seed and grows into a massive tree.
> **Option C:** A literal virtual pet (like a tamagotchi monster/animal).

Let me know which theme you prefer and I'll scaffold the database and UI!