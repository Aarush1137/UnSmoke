# Codex Handoff — UnSmoke

## Purpose

This is the working record for Codex sessions. Update it whenever a stable checkpoint is built and committed. Read this file together with `implementation_plan.md` and `task_todo.md` before resuming work.

## 2026-08-22 checkpoint

### Repository assessment

- Branch: `main`; remote: `https://github.com/Aarush1137/UnSmoke.git`.
- The repository is a single Android app module, not the multi-module layout proposed in the original plan.
- The previous README was stale: it claimed several features were pending even though related UI existed, linked to image files that do not exist, and did not include a practical local build guide.
- The previous task list marked every phase complete, but there are no committed unit or instrumented test files and several listed features have only UI/scaffold coverage.

### Stable work completed in this checkpoint

1. **First-launch routing** — splash now reads the persisted onboarding flag. New users are sent to onboarding; returning users are sent to Home.
2. **Onboarding identity** — completing onboarding now saves the user’s display name used by Profile and Settings.
3. **Biometric app lock** — MainActivity now reads the persisted lock preference rather than using a hard-coded disabled value.
4. **Theme preference** — MainActivity applies light, system, and dark/AMOLED selections to the app theme. AMOLED still uses the dark palette until a true AMOLED palette is designed.
5. **Profile and Settings continuation** — preserved and validated the existing uncommitted redesign: editable motivation and support contact, profile statistics, persisted identity/preferences, setting dialogs, and reset/export controls.
6. **Safer reset** — reset now clears both Room and DataStore, then starts onboarding again. Widget cache and scheduled-work cleanup remain tracked as follow-up work.
7. **README** — replaced the stale readme with an accurate product overview, technology table, local build steps, current limitations, and links to project assets/artifacts.
8. **Roadmap integrity** — replaced the all-complete task list with a verified backlog, including a detailed Profile/Settings feature plan and remaining work in every phase.

### Verification performed

```powershell
.\gradlew.bat :app:assembleDebug
```

Result: **BUILD SUCCESSFUL** on 2026-08-22. The debug APK is generated at `app\build\outputs\apk\debug\app-debug.apk`.

## Profile and Settings feature plan

### Profile

- Identity header: display name and active quit-attempt status.
- Live metrics: days smoke-free, money saved, and cigarettes avoided.
- Motivation: edit and display the personal reason for quitting.
- Emergency anchor: store a voluntary support contact and launch the dialer; never initiate a call automatically.
- Navigation hub: Quit Plan, Achievements, and Settings.

### Settings

- Preferences: display name, theme, currency, coaching tone, notifications, and optional biometric lock.
- Privacy: export and reset are explicit actions; reset must be upgraded to clear every local store before it is described as comprehensive.
- Behaviour contract: every persisted preference needs a corresponding app-wide consumer and an automated test.

## Next recommended checkpoint

1. Make reset fully comprehensive: clear widget cache and scheduled workers in addition to Room/DataStore, which are already cleared, then verify a clean first-run state.
2. Audit app-wide currency and theme consumers, especially hard-coded colours and currency symbols.
3. Add automated tests for onboarding routing, biometric preference, settings persistence, and reset.
4. Create branded adaptive icons and a release signing/release-asset process; publish only a signed release APK/AAB, not the debug APK.

## Release-note rule

Each stable commit should include: the exact Gradle verification command/result, a concise changelog entry if user-visible behaviour changed, and an update to this file plus `task_todo.md` when roadmap status changes.
