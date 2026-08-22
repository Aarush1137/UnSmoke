# UnSmoke — Verified Build Backlog

Last reconciled: 2026-08-22. Check an item only after it is implemented and verified. The earlier all-complete checklist was aspirational; this backlog reflects the source tree and current test coverage.

## Phase 1 — Foundation

- [x] Android/Compose/Hilt/Room project builds as a debug APK.
- [x] Add a branded adaptive launcher icon and remove Android's default icon.
- [ ] Add a dedicated AMOLED colour scheme and apply theme preference across every screen.
- [ ] Add a release build/signing configuration and versioning policy.

## Phase 2 — Data and domain

- [x] Persist onboarding, profile, display preferences, quit attempts, and feature data locally.
- [x] Reset clears Room data and preferences, then restarts onboarding.
- [ ] Extend reset to clear widget state and scheduled work together.
- [ ] Add database migration tests and validate exported JSON/CSV against a documented schema.
- [ ] Centralise currency formatting so the selected symbol applies to all calculated values.

## Phase 3 — Onboarding

- [x] Route first launch from splash to onboarding; returning users go to Home.
- [x] Save the onboarding display name to preferences.
- [ ] Validate required baseline fields and explain zero/unknown values before completion.
- [ ] Add edit/review screens for quit date, baseline, reasons, triggers, and NRT setup.
- [ ] Add UI tests for first launch, completion, and returning-user routing.

## Phase 4 — Home dashboard

- [x] Show progress, metrics, milestone information, and entry points to major features.
- [ ] Audit every dashboard metric against raw Room events and add empty/loading/error states.
- [ ] Make the dashboard react immediately after check-in, lapse, and NRT logging.
- [ ] Add accessibility labels and a large-font layout pass.

## Phase 5 — Craving support

- [x] Provide trigger/intensity selection, intervention timer, outcome, and recovery screens.
- [ ] Persist each craving and outcome reliably when the app backgrounds or is killed.
- [ ] Finish and test mini-games/distraction actions, including reduced-motion support.
- [ ] Add direct access to an emergency contact from the craving flow.

## Phase 6 — NRT tracker

- [x] Provide NRT dashboard and logging UI.
- [ ] Complete add/edit product setup, history filters, and reminder scheduling.
- [ ] Add clear, non-diagnostic medical disclaimer in setup and logging flows.
- [ ] Test NRT cost calculations, quantity validation, and daily aggregation.

## Phase 7 — Progress and analytics

- [x] Provide progress, insights, achievements, and profile surfaces.
- [ ] Reconcile charts and insights with production data; cover no-data and sparse-data cases.
- [ ] Implement personal records, a timeline, and quit-attempt history.
- [ ] Test achievement unlock rules and ensure each can be reached from real activity.

## Phase 8 — Check-ins and reviews

- [x] Provide daily check-in UI.
- [ ] Persist all check-in answers and prevent accidental duplicate check-ins for one day.
- [ ] Build the weekly review from real weekly aggregates and make it shareable/exportable.
- [ ] Add reminder scheduling that respects notification permission and the user preference.

## Phase 9 — Journal and mood

- [x] Provide journal and mood entry UI.
- [ ] Add search, edit/delete, tags, and confirmation/undo behavior.
- [ ] Decide whether journal encryption is in scope and document the privacy model.
- [ ] Test data export and reset behaviour for journal content.

## Phase 10 — Coach

- [ ] Implement the rule-based coach response library and input categories.
- [ ] Use stored streak, triggers, reasons, mood, and lapse data without making medical claims.
- [ ] Add escalation copy for urgent support and a clear “not medical advice” boundary.

## Phase 11 — Notifications and widgets

- [x] Declare widget receivers and notification permissions.
- [ ] Verify widget data refreshes after every relevant log and on device reboot.
- [ ] Implement notification channels, WorkManager scheduling, permission education, and cancellation.
- [ ] Add notification-worker and widget integration tests.

## Phase 12 — Profile, settings, and privacy

- [x] Redesigned Profile: identity card, live statistics, motivation editing, emergency anchor, and navigation hub.
- [x] Redesigned Settings: name, theme, currency, coaching tone, notifications, biometric preference, export/reset controls.
- [x] Persist profile/settings values in DataStore and honour biometric lock at app launch.
- [ ] Apply settings globally: theme, currency, notification style, and app lock changes must take effect predictably.
- [x] Clear Room and preferences, then restart onboarding after reset.
- [ ] Clear widget state and scheduled work as part of reset.
- [ ] Complete data export and verify sharing works on current Android versions.
- [ ] Add privacy policy, data-retention explanation, and accessibility review.

## Phase 13 — Quit plan

- [x] Provide a quit-plan screen and profile entry point.
- [ ] Persist personalised strategies, priorities, reward goals, and implementation intentions.
- [ ] Add edit/reorder/delete actions with undo.
- [ ] Link plan actions to relevant cravings and insights.

## Phase 14 — Lapse and relapse recovery

- [x] Provide a supportive recovery destination from the craving timer.
- [ ] Persist smoking events, preserve historical attempts, and start a new attempt without data loss.
- [ ] Test lapse/restart calculations, streak display, and non-shaming copy.

## Phase 15 — Quality, release, and testing

- [x] `:app:assembleDebug` succeeds on the current working tree.
- [x] Publish a clearly labelled debug testing APK to GitHub Releases.
- [ ] Add unit tests for calculations, repositories, view models, and the coach engine.
- [ ] Add Compose/UI tests for onboarding, core navigation, craving completion, and settings/reset.
- [ ] Run lint, instrumented tests, and manual release checklist on an Android 8+ device.
- [ ] Create signed release APK/AAB, publish release notes, and attach the signed artifact to GitHub.
