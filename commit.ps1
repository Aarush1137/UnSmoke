cd E:\Projects\Unsmoke
git init

git add .gitignore README.md LICENSE build.gradle.kts settings.gradle.kts gradle app/build.gradle.kts gradlew.bat
git commit -m "feat: initialize UnSmoke project with Gradle 9.3.1, Kotlin 2.4.10, and Jetpack Compose BOM 2026.08.00"

git add app/src/main/kotlin/com/unsmoke/app/core/designsystem
git commit -m "feat(designsystem): add Material 3 navy/teal theme, typography, ProgressRing, BreathingOrb, and shared components"

git add app/src/main/kotlin/com/unsmoke/app/core/data/database/entity
git commit -m "feat(database): add Room 3.0 database with 14 entities covering quit attempts, cravings, NRT, journal, and achievements"

git add app/src/main/kotlin/com/unsmoke/app/core/data/database/dao
git commit -m "feat(dao): add complete DAO layer with Flow-based queries for all entities"

git add app/src/main/kotlin/com/unsmoke/app/core/domain/engine
git commit -m "feat(domain): add CalculationEngine with NRT cost math, QuoteEngine, and all use cases"

git add .
git commit -m "feat(di): add Hilt modules for database, DataStore, and repository bindings"

git branch -M main
git remote add origin https://github.com/Aarush1137/UnSmoke.git
git push -u origin main

git tag -a v0.1.0 -m "v0.1.0: Project foundation - Gradle, design system, complete data layer"
git push origin v0.1.0
