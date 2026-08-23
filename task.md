

## Phase 7: Advanced Analytics, CBT Recovery, and Export
- [x] **Feature 1: Craving Analytics Dashboard**
  - Create database queries for aggregating cravings by time and trigger.
  - Build `AnalyticsScreen.kt` with a visual heatmap/bar chart.
- [x] **Feature 2: CBT Relapse Autopsy Flow**
  - Create `RelapseAutopsyScreen.kt` with the 3-step recovery questionnaire.
  - Intercept the "I Smoked" button to route here before resetting the streak.
- [ ] **Feature 3: Daily Quit Coach**
  - Populate 30 days of CBT micro-lessons.
  - Bind the correct lesson to the Home Screen coach card based on `smokeFreeDays`.
- [ ] **Feature 4: Clinician Export (CSV)**
  - Implement `ExportEngine.kt` to generate a CSV of NRT logs and Cravings.
  - Add Export button and Share Intent via `FileProvider`.
- [ ] **Feature 5: Social Share Cards**
  - Implement bitmap generation for milestone badges.
  - Wire up a native Android share sheet for Instagram/WhatsApp sharing.
