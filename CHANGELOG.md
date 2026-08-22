# Changelog

## [0.6.0] - 2026-08-22
### Added
- **NRT Tracker**: Dashboard showing daily logs, total expenditure vs savings, and a plan completion donut ring.
- **Progress Screen**: Comprehensive metrics view with time-range filtering (7 Days, 30 Days, 3 Months, 1 Year) for tracking streak, money, and craving data.

## [0.5.0] - 2026-08-22
### Added
- **Onboarding Flow**: 3-step animated conversational setup for capturing baseline smoking habits and user profile.
- **Craving Support**: Full 5-step immersive support system with Intensity Slider, Trigger selection, and a 10-minute dark-themed breathing timer (with glowing Canvas animations).
- **Outcome & Lapse UI**: Supportive, non-shaming screens for when users successfully ride out a craving or experience a slip.

## [0.4.0] - 2026-08-22
### Added
- **Widgets System**: Introduced Jetpack Glance to power three new home screen widgets:
  - **Streak Widget (2x2)**: Smoke-free days counter, quit date, and I HAVE A CRAVING button.
  - **Dashboard Widget (4x2)**: Full metrics — days, cigarettes avoided, money saved (amber), craving stats, and a craving button.
  - **Craving Widget (1x1)**: Emergency one-tap craving support launcher.
- **Widget Background Sync**: Integrated Hilt Worker for periodic WidgetDataRepository refresh.
- **Core Architecture Scaffolded**: Home, Onboarding, Craving systems started.

## [0.2.0] - 2026-08-22
### Added
- **UI System**: Mint/Teal primary color palette with Amber achievement accents.
- **Components**: ProgressRing, BreathingOrb added to the design system.
