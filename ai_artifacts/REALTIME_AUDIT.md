# UnSmoke — Live Audit, Bug, and Fix Log

**Started:** 2026-09-06
**Scope:** Full Android phone app, Wear module, build/release configuration, persistence, navigation, Profile/Settings, and user-facing safety.
**Rule:** An item is marked fixed only after the relevant build or test has passed. Existing claims in earlier reports are re-validated rather than assumed correct.

## Live status

| Area | Status | Evidence / next action |
| --- | --- | --- |
| Repository baseline | In progress | Current `main` HEAD is `84ec2522` (`v3.2.1`). Earlier reports claim 45 fixes; independent validation is under way. |
| Build integrity | In progress | A normal assemble can reuse stale outputs. A forced source compilation is being established for a trustworthy result. |
| Unit tests | Pending | Run the current test suite after concurrent Gradle work completes. |
| Onboarding/NRT handoff | In progress | Recent work extends onboarding and NRT persistence; verify it compiles and does not regress routes/data. |
| Profile/Settings | In progress | Audit persistence, reset, theme, accessibility, export, and cloud-backup behaviours. |
| Safety/privacy | In progress | Audit secrets, medical boundaries, Firebase, permissions, and release signing. |

## Findings

### AUD-001 — Stale build output can mask source errors

- **Severity:** High (verification integrity)
- **Status:** Investigating
- **Evidence:** `:app:assembleDebug` reported up-to-date after source state changed. The expected `:app:compileDebugKotlin` task is not present in this Gradle configuration; the available aggregate task is `:app:compileDebugSources`.
- **Next action:** force the correct aggregate compilation and record the exact outcome.

## Fix log

No fixes have been applied during this live audit yet.

## Next update

Collect build, test, data-flow, and navigation findings; prioritise reproducible high-impact defects before implementation.
