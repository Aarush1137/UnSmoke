# Security and Privacy Policy

## 1. Local-First Architecture
UnSmoke prioritizes user privacy. By default, **all health data, cravings, journal entries, and smoking history** are stored strictly **locally** on your device using a Room SQLite database.
- We do not sell your data.
- We do not run third-party analytics trackers.
- Your quit journey is private.

## 2. Firebase & Buddy System (Opt-In)
If you choose to use the **Quit Buddy** feature, you opt-in to anonymous pairing via Firebase Authentication and Firestore.
- **Anonymous Auth:** No email, phone number, or social logins are required.
- **Firestore Data:** The only data sent to Firebase is an anonymous User ID (UID), a random 6-digit pairing code, and a boolean flag indicating if you requested an SOS (
eedsHelp).
- **Security Rules:** Firestore is locked down via strict security rules (irestore.rules). A user can only read or write their own pairing document, or read the document of the single buddy they are successfully paired with.

## 3. App Lock (Biometrics)
To protect your journal and health data from physical snooping, UnSmoke supports Biometric App Lock (Fingerprint/Face ID). When enabled, this uses the Android ndroidx.biometric library securely anchored to your device's trusted execution environment (TEE).

## 4. Google Play Store Compliance
- **Data Safety:** We declare that health data is NOT collected or shared off-device (unless the user explicitly pairs a Buddy, which only shares anonymous SOS states).
- **Encryption:** All data in transit to Firebase is encrypted via HTTPS/TLS. Local database encryption (SQLCipher) is not currently implemented but is planned for a future release.