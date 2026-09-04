# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in getDefaultProguardFile('proguard-android-optimize.txt').

# Room Database
-keepclassmembers class * extends androidx.room.RoomDatabase {
    <methods>;
}
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }

# Firebase & Firestore Models
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod

-keep class com.unsmoke.app.core.domain.repository.BuddyProfile { *; }
-keepclassmembers class com.unsmoke.app.core.domain.repository.BuddyProfile {
    <fields>;
    <init>(...);
}

# Kotlinx Serialization
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}
-keepattributes *Annotation*,InnerClasses
-dontnote kotlinx.serialization.SerializationKt

# Google Generative AI SDK (Gemini)
-keep class com.google.ai.client.generativeai.** { *; }

# WorkManager & Hilt
-keep class androidx.work.** { *; }
-keep class androidx.hilt.work.** { *; }

# Protobuf / DataStore
-keep class androidx.datastore.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
