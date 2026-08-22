package com.unsmoke.app.core.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.unsmoke.app.core.data.database.dao.CheckInDao;
import com.unsmoke.app.core.data.database.dao.CheckInDao_Impl;
import com.unsmoke.app.core.data.database.dao.CravingDao;
import com.unsmoke.app.core.data.database.dao.CravingDao_Impl;
import com.unsmoke.app.core.data.database.dao.NRTDao;
import com.unsmoke.app.core.data.database.dao.NRTDao_Impl;
import com.unsmoke.app.core.data.database.dao.QuitAttemptDao;
import com.unsmoke.app.core.data.database.dao.QuitAttemptDao_Impl;
import com.unsmoke.app.core.data.database.dao.UserProfileDao;
import com.unsmoke.app.core.data.database.dao.UserProfileDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UnSmokeDatabase_Impl extends UnSmokeDatabase {
  private volatile QuitAttemptDao _quitAttemptDao;

  private volatile CravingDao _cravingDao;

  private volatile NRTDao _nRTDao;

  private volatile CheckInDao _checkInDao;

  private volatile UserProfileDao _userProfileDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profile` (`id` INTEGER NOT NULL, `name` TEXT, `notificationStyle` TEXT NOT NULL, `currency` TEXT NOT NULL, `currencySymbol` TEXT NOT NULL, `appLockEnabled` INTEGER NOT NULL, `theme` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quit_attempt` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `startEpochMillis` INTEGER NOT NULL, `endEpochMillis` INTEGER, `status` TEXT NOT NULL, `cigarettesPerDay` REAL NOT NULL, `cigarettesPerPack` INTEGER NOT NULL, `packPrice` REAL NOT NULL, `pricePerCigarette` REAL NOT NULL, `timezone` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `smoking_baseline` (`quitAttemptId` INTEGER NOT NULL, `yearsSmoked` REAL, `weekendCigsPerDay` REAL, `firstCigAfterWakingMinutes` INTEGER, `strongestPeriod` TEXT, PRIMARY KEY(`quitAttemptId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `smoking_event` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quitAttemptId` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `cigaretteCount` INTEGER NOT NULL, `trigger` TEXT, `mood` INTEGER, `notes` TEXT, `eventType` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `craving_event` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quitAttemptId` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `intensity` INTEGER NOT NULL, `trigger` TEXT, `location` TEXT, `intervention` TEXT, `outcome` TEXT NOT NULL, `durationSeconds` INTEGER, `nrtUsedBefore` INTEGER NOT NULL, `mood` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `nrt_product` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT NOT NULL, `name` TEXT NOT NULL, `nicotineStrengthMg` REAL, `packPrice` REAL NOT NULL, `unitsPerPack` INTEGER NOT NULL, `pricePerUnit` REAL NOT NULL, `isActive` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `nrt_usage` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `quitAttemptId` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `quantity` INTEGER NOT NULL, `cravingBefore` INTEGER, `cravingAfter` INTEGER, `trigger` TEXT, `notes` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `nrt_reminder` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productId` INTEGER NOT NULL, `hour` INTEGER NOT NULL, `minute` INTEGER NOT NULL, `enabled` INTEGER NOT NULL, `label` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `mood_entry` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `datestamp` TEXT NOT NULL, `mood` INTEGER NOT NULL, `emotions` TEXT NOT NULL, `notes` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_checkin` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `datestamp` TEXT NOT NULL, `dayRating` TEXT NOT NULL, `smoked` INTEGER NOT NULL, `cravingLevel` INTEGER NOT NULL, `topHelper` TEXT, `tomorrowFocus` TEXT, `timestamp` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `journal_entry` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `content` TEXT NOT NULL, `mood` INTEGER, `tags` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `achievement` (`type` TEXT NOT NULL, `unlockedAt` INTEGER, `displayedToUser` INTEGER NOT NULL, PRIMARY KEY(`type`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quit_reason` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `quitAttemptId` INTEGER NOT NULL, `category` TEXT NOT NULL, `customText` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reward_goal` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `targetAmount` REAL NOT NULL, `achieved` INTEGER NOT NULL, `achievedAt` INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notification_preference` (`type` TEXT NOT NULL, `enabled` INTEGER NOT NULL, `scheduledHour` INTEGER, `scheduledMinute` INTEGER, PRIMARY KEY(`type`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `implementation_intention` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `trigger` TEXT NOT NULL, `action` TEXT NOT NULL, `quitAttemptId` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trigger_log` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `trigger` TEXT NOT NULL, `count` INTEGER NOT NULL, `lastSeen` INTEGER NOT NULL, `hourOfDay` INTEGER NOT NULL, `quitAttemptId` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4eb04768caf5741b56a6591bc2d933fe')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `user_profile`");
        db.execSQL("DROP TABLE IF EXISTS `quit_attempt`");
        db.execSQL("DROP TABLE IF EXISTS `smoking_baseline`");
        db.execSQL("DROP TABLE IF EXISTS `smoking_event`");
        db.execSQL("DROP TABLE IF EXISTS `craving_event`");
        db.execSQL("DROP TABLE IF EXISTS `nrt_product`");
        db.execSQL("DROP TABLE IF EXISTS `nrt_usage`");
        db.execSQL("DROP TABLE IF EXISTS `nrt_reminder`");
        db.execSQL("DROP TABLE IF EXISTS `mood_entry`");
        db.execSQL("DROP TABLE IF EXISTS `daily_checkin`");
        db.execSQL("DROP TABLE IF EXISTS `journal_entry`");
        db.execSQL("DROP TABLE IF EXISTS `achievement`");
        db.execSQL("DROP TABLE IF EXISTS `quit_reason`");
        db.execSQL("DROP TABLE IF EXISTS `reward_goal`");
        db.execSQL("DROP TABLE IF EXISTS `notification_preference`");
        db.execSQL("DROP TABLE IF EXISTS `implementation_intention`");
        db.execSQL("DROP TABLE IF EXISTS `trigger_log`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUserProfile = new HashMap<String, TableInfo.Column>(8);
        _columnsUserProfile.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("notificationStyle", new TableInfo.Column("notificationStyle", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("currency", new TableInfo.Column("currency", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("currencySymbol", new TableInfo.Column("currencySymbol", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("appLockEnabled", new TableInfo.Column("appLockEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("theme", new TableInfo.Column("theme", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfile.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfile = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfile = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfile = new TableInfo("user_profile", _columnsUserProfile, _foreignKeysUserProfile, _indicesUserProfile);
        final TableInfo _existingUserProfile = TableInfo.read(db, "user_profile");
        if (!_infoUserProfile.equals(_existingUserProfile)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profile(com.unsmoke.app.core.data.database.entity.UserProfileEntity).\n"
                  + " Expected:\n" + _infoUserProfile + "\n"
                  + " Found:\n" + _existingUserProfile);
        }
        final HashMap<String, TableInfo.Column> _columnsQuitAttempt = new HashMap<String, TableInfo.Column>(10);
        _columnsQuitAttempt.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("startEpochMillis", new TableInfo.Column("startEpochMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("endEpochMillis", new TableInfo.Column("endEpochMillis", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("cigarettesPerDay", new TableInfo.Column("cigarettesPerDay", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("cigarettesPerPack", new TableInfo.Column("cigarettesPerPack", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("packPrice", new TableInfo.Column("packPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("pricePerCigarette", new TableInfo.Column("pricePerCigarette", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("timezone", new TableInfo.Column("timezone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitAttempt.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuitAttempt = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuitAttempt = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuitAttempt = new TableInfo("quit_attempt", _columnsQuitAttempt, _foreignKeysQuitAttempt, _indicesQuitAttempt);
        final TableInfo _existingQuitAttempt = TableInfo.read(db, "quit_attempt");
        if (!_infoQuitAttempt.equals(_existingQuitAttempt)) {
          return new RoomOpenHelper.ValidationResult(false, "quit_attempt(com.unsmoke.app.core.data.database.entity.QuitAttemptEntity).\n"
                  + " Expected:\n" + _infoQuitAttempt + "\n"
                  + " Found:\n" + _existingQuitAttempt);
        }
        final HashMap<String, TableInfo.Column> _columnsSmokingBaseline = new HashMap<String, TableInfo.Column>(5);
        _columnsSmokingBaseline.put("quitAttemptId", new TableInfo.Column("quitAttemptId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingBaseline.put("yearsSmoked", new TableInfo.Column("yearsSmoked", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingBaseline.put("weekendCigsPerDay", new TableInfo.Column("weekendCigsPerDay", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingBaseline.put("firstCigAfterWakingMinutes", new TableInfo.Column("firstCigAfterWakingMinutes", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingBaseline.put("strongestPeriod", new TableInfo.Column("strongestPeriod", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSmokingBaseline = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSmokingBaseline = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSmokingBaseline = new TableInfo("smoking_baseline", _columnsSmokingBaseline, _foreignKeysSmokingBaseline, _indicesSmokingBaseline);
        final TableInfo _existingSmokingBaseline = TableInfo.read(db, "smoking_baseline");
        if (!_infoSmokingBaseline.equals(_existingSmokingBaseline)) {
          return new RoomOpenHelper.ValidationResult(false, "smoking_baseline(com.unsmoke.app.core.data.database.entity.SmokingBaselineEntity).\n"
                  + " Expected:\n" + _infoSmokingBaseline + "\n"
                  + " Found:\n" + _existingSmokingBaseline);
        }
        final HashMap<String, TableInfo.Column> _columnsSmokingEvent = new HashMap<String, TableInfo.Column>(8);
        _columnsSmokingEvent.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingEvent.put("quitAttemptId", new TableInfo.Column("quitAttemptId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingEvent.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingEvent.put("cigaretteCount", new TableInfo.Column("cigaretteCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingEvent.put("trigger", new TableInfo.Column("trigger", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingEvent.put("mood", new TableInfo.Column("mood", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingEvent.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSmokingEvent.put("eventType", new TableInfo.Column("eventType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSmokingEvent = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSmokingEvent = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSmokingEvent = new TableInfo("smoking_event", _columnsSmokingEvent, _foreignKeysSmokingEvent, _indicesSmokingEvent);
        final TableInfo _existingSmokingEvent = TableInfo.read(db, "smoking_event");
        if (!_infoSmokingEvent.equals(_existingSmokingEvent)) {
          return new RoomOpenHelper.ValidationResult(false, "smoking_event(com.unsmoke.app.core.data.database.entity.SmokingEventEntity).\n"
                  + " Expected:\n" + _infoSmokingEvent + "\n"
                  + " Found:\n" + _existingSmokingEvent);
        }
        final HashMap<String, TableInfo.Column> _columnsCravingEvent = new HashMap<String, TableInfo.Column>(11);
        _columnsCravingEvent.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("quitAttemptId", new TableInfo.Column("quitAttemptId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("intensity", new TableInfo.Column("intensity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("trigger", new TableInfo.Column("trigger", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("intervention", new TableInfo.Column("intervention", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("outcome", new TableInfo.Column("outcome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("durationSeconds", new TableInfo.Column("durationSeconds", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("nrtUsedBefore", new TableInfo.Column("nrtUsedBefore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCravingEvent.put("mood", new TableInfo.Column("mood", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCravingEvent = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCravingEvent = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCravingEvent = new TableInfo("craving_event", _columnsCravingEvent, _foreignKeysCravingEvent, _indicesCravingEvent);
        final TableInfo _existingCravingEvent = TableInfo.read(db, "craving_event");
        if (!_infoCravingEvent.equals(_existingCravingEvent)) {
          return new RoomOpenHelper.ValidationResult(false, "craving_event(com.unsmoke.app.core.data.database.entity.CravingEventEntity).\n"
                  + " Expected:\n" + _infoCravingEvent + "\n"
                  + " Found:\n" + _existingCravingEvent);
        }
        final HashMap<String, TableInfo.Column> _columnsNrtProduct = new HashMap<String, TableInfo.Column>(8);
        _columnsNrtProduct.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtProduct.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtProduct.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtProduct.put("nicotineStrengthMg", new TableInfo.Column("nicotineStrengthMg", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtProduct.put("packPrice", new TableInfo.Column("packPrice", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtProduct.put("unitsPerPack", new TableInfo.Column("unitsPerPack", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtProduct.put("pricePerUnit", new TableInfo.Column("pricePerUnit", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtProduct.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNrtProduct = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNrtProduct = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNrtProduct = new TableInfo("nrt_product", _columnsNrtProduct, _foreignKeysNrtProduct, _indicesNrtProduct);
        final TableInfo _existingNrtProduct = TableInfo.read(db, "nrt_product");
        if (!_infoNrtProduct.equals(_existingNrtProduct)) {
          return new RoomOpenHelper.ValidationResult(false, "nrt_product(com.unsmoke.app.core.data.database.entity.NRTProductEntity).\n"
                  + " Expected:\n" + _infoNrtProduct + "\n"
                  + " Found:\n" + _existingNrtProduct);
        }
        final HashMap<String, TableInfo.Column> _columnsNrtUsage = new HashMap<String, TableInfo.Column>(9);
        _columnsNrtUsage.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("quitAttemptId", new TableInfo.Column("quitAttemptId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("cravingBefore", new TableInfo.Column("cravingBefore", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("cravingAfter", new TableInfo.Column("cravingAfter", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("trigger", new TableInfo.Column("trigger", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtUsage.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNrtUsage = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNrtUsage = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNrtUsage = new TableInfo("nrt_usage", _columnsNrtUsage, _foreignKeysNrtUsage, _indicesNrtUsage);
        final TableInfo _existingNrtUsage = TableInfo.read(db, "nrt_usage");
        if (!_infoNrtUsage.equals(_existingNrtUsage)) {
          return new RoomOpenHelper.ValidationResult(false, "nrt_usage(com.unsmoke.app.core.data.database.entity.NRTUsageEntity).\n"
                  + " Expected:\n" + _infoNrtUsage + "\n"
                  + " Found:\n" + _existingNrtUsage);
        }
        final HashMap<String, TableInfo.Column> _columnsNrtReminder = new HashMap<String, TableInfo.Column>(6);
        _columnsNrtReminder.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtReminder.put("productId", new TableInfo.Column("productId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtReminder.put("hour", new TableInfo.Column("hour", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtReminder.put("minute", new TableInfo.Column("minute", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtReminder.put("enabled", new TableInfo.Column("enabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNrtReminder.put("label", new TableInfo.Column("label", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNrtReminder = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNrtReminder = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNrtReminder = new TableInfo("nrt_reminder", _columnsNrtReminder, _foreignKeysNrtReminder, _indicesNrtReminder);
        final TableInfo _existingNrtReminder = TableInfo.read(db, "nrt_reminder");
        if (!_infoNrtReminder.equals(_existingNrtReminder)) {
          return new RoomOpenHelper.ValidationResult(false, "nrt_reminder(com.unsmoke.app.core.data.database.entity.NRTReminderEntity).\n"
                  + " Expected:\n" + _infoNrtReminder + "\n"
                  + " Found:\n" + _existingNrtReminder);
        }
        final HashMap<String, TableInfo.Column> _columnsMoodEntry = new HashMap<String, TableInfo.Column>(5);
        _columnsMoodEntry.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoodEntry.put("datestamp", new TableInfo.Column("datestamp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoodEntry.put("mood", new TableInfo.Column("mood", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoodEntry.put("emotions", new TableInfo.Column("emotions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMoodEntry.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMoodEntry = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMoodEntry = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMoodEntry = new TableInfo("mood_entry", _columnsMoodEntry, _foreignKeysMoodEntry, _indicesMoodEntry);
        final TableInfo _existingMoodEntry = TableInfo.read(db, "mood_entry");
        if (!_infoMoodEntry.equals(_existingMoodEntry)) {
          return new RoomOpenHelper.ValidationResult(false, "mood_entry(com.unsmoke.app.core.data.database.entity.MoodEntryEntity).\n"
                  + " Expected:\n" + _infoMoodEntry + "\n"
                  + " Found:\n" + _existingMoodEntry);
        }
        final HashMap<String, TableInfo.Column> _columnsDailyCheckin = new HashMap<String, TableInfo.Column>(8);
        _columnsDailyCheckin.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyCheckin.put("datestamp", new TableInfo.Column("datestamp", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyCheckin.put("dayRating", new TableInfo.Column("dayRating", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyCheckin.put("smoked", new TableInfo.Column("smoked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyCheckin.put("cravingLevel", new TableInfo.Column("cravingLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyCheckin.put("topHelper", new TableInfo.Column("topHelper", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyCheckin.put("tomorrowFocus", new TableInfo.Column("tomorrowFocus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyCheckin.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyCheckin = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyCheckin = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyCheckin = new TableInfo("daily_checkin", _columnsDailyCheckin, _foreignKeysDailyCheckin, _indicesDailyCheckin);
        final TableInfo _existingDailyCheckin = TableInfo.read(db, "daily_checkin");
        if (!_infoDailyCheckin.equals(_existingDailyCheckin)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_checkin(com.unsmoke.app.core.data.database.entity.DailyCheckInEntity).\n"
                  + " Expected:\n" + _infoDailyCheckin + "\n"
                  + " Found:\n" + _existingDailyCheckin);
        }
        final HashMap<String, TableInfo.Column> _columnsJournalEntry = new HashMap<String, TableInfo.Column>(5);
        _columnsJournalEntry.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournalEntry.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournalEntry.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournalEntry.put("mood", new TableInfo.Column("mood", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsJournalEntry.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysJournalEntry = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesJournalEntry = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoJournalEntry = new TableInfo("journal_entry", _columnsJournalEntry, _foreignKeysJournalEntry, _indicesJournalEntry);
        final TableInfo _existingJournalEntry = TableInfo.read(db, "journal_entry");
        if (!_infoJournalEntry.equals(_existingJournalEntry)) {
          return new RoomOpenHelper.ValidationResult(false, "journal_entry(com.unsmoke.app.core.data.database.entity.JournalEntryEntity).\n"
                  + " Expected:\n" + _infoJournalEntry + "\n"
                  + " Found:\n" + _existingJournalEntry);
        }
        final HashMap<String, TableInfo.Column> _columnsAchievement = new HashMap<String, TableInfo.Column>(3);
        _columnsAchievement.put("type", new TableInfo.Column("type", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievement.put("unlockedAt", new TableInfo.Column("unlockedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAchievement.put("displayedToUser", new TableInfo.Column("displayedToUser", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAchievement = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAchievement = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAchievement = new TableInfo("achievement", _columnsAchievement, _foreignKeysAchievement, _indicesAchievement);
        final TableInfo _existingAchievement = TableInfo.read(db, "achievement");
        if (!_infoAchievement.equals(_existingAchievement)) {
          return new RoomOpenHelper.ValidationResult(false, "achievement(com.unsmoke.app.core.data.database.entity.AchievementEntity).\n"
                  + " Expected:\n" + _infoAchievement + "\n"
                  + " Found:\n" + _existingAchievement);
        }
        final HashMap<String, TableInfo.Column> _columnsQuitReason = new HashMap<String, TableInfo.Column>(4);
        _columnsQuitReason.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitReason.put("quitAttemptId", new TableInfo.Column("quitAttemptId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitReason.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuitReason.put("customText", new TableInfo.Column("customText", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuitReason = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuitReason = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuitReason = new TableInfo("quit_reason", _columnsQuitReason, _foreignKeysQuitReason, _indicesQuitReason);
        final TableInfo _existingQuitReason = TableInfo.read(db, "quit_reason");
        if (!_infoQuitReason.equals(_existingQuitReason)) {
          return new RoomOpenHelper.ValidationResult(false, "quit_reason(com.unsmoke.app.core.data.database.entity.QuitReasonEntity).\n"
                  + " Expected:\n" + _infoQuitReason + "\n"
                  + " Found:\n" + _existingQuitReason);
        }
        final HashMap<String, TableInfo.Column> _columnsRewardGoal = new HashMap<String, TableInfo.Column>(5);
        _columnsRewardGoal.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRewardGoal.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRewardGoal.put("targetAmount", new TableInfo.Column("targetAmount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRewardGoal.put("achieved", new TableInfo.Column("achieved", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRewardGoal.put("achievedAt", new TableInfo.Column("achievedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRewardGoal = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRewardGoal = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRewardGoal = new TableInfo("reward_goal", _columnsRewardGoal, _foreignKeysRewardGoal, _indicesRewardGoal);
        final TableInfo _existingRewardGoal = TableInfo.read(db, "reward_goal");
        if (!_infoRewardGoal.equals(_existingRewardGoal)) {
          return new RoomOpenHelper.ValidationResult(false, "reward_goal(com.unsmoke.app.core.data.database.entity.RewardGoalEntity).\n"
                  + " Expected:\n" + _infoRewardGoal + "\n"
                  + " Found:\n" + _existingRewardGoal);
        }
        final HashMap<String, TableInfo.Column> _columnsNotificationPreference = new HashMap<String, TableInfo.Column>(4);
        _columnsNotificationPreference.put("type", new TableInfo.Column("type", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationPreference.put("enabled", new TableInfo.Column("enabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationPreference.put("scheduledHour", new TableInfo.Column("scheduledHour", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotificationPreference.put("scheduledMinute", new TableInfo.Column("scheduledMinute", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotificationPreference = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotificationPreference = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotificationPreference = new TableInfo("notification_preference", _columnsNotificationPreference, _foreignKeysNotificationPreference, _indicesNotificationPreference);
        final TableInfo _existingNotificationPreference = TableInfo.read(db, "notification_preference");
        if (!_infoNotificationPreference.equals(_existingNotificationPreference)) {
          return new RoomOpenHelper.ValidationResult(false, "notification_preference(com.unsmoke.app.core.data.database.entity.NotificationPreferenceEntity).\n"
                  + " Expected:\n" + _infoNotificationPreference + "\n"
                  + " Found:\n" + _existingNotificationPreference);
        }
        final HashMap<String, TableInfo.Column> _columnsImplementationIntention = new HashMap<String, TableInfo.Column>(4);
        _columnsImplementationIntention.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImplementationIntention.put("trigger", new TableInfo.Column("trigger", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImplementationIntention.put("action", new TableInfo.Column("action", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsImplementationIntention.put("quitAttemptId", new TableInfo.Column("quitAttemptId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysImplementationIntention = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesImplementationIntention = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoImplementationIntention = new TableInfo("implementation_intention", _columnsImplementationIntention, _foreignKeysImplementationIntention, _indicesImplementationIntention);
        final TableInfo _existingImplementationIntention = TableInfo.read(db, "implementation_intention");
        if (!_infoImplementationIntention.equals(_existingImplementationIntention)) {
          return new RoomOpenHelper.ValidationResult(false, "implementation_intention(com.unsmoke.app.core.data.database.entity.ImplementationIntentionEntity).\n"
                  + " Expected:\n" + _infoImplementationIntention + "\n"
                  + " Found:\n" + _existingImplementationIntention);
        }
        final HashMap<String, TableInfo.Column> _columnsTriggerLog = new HashMap<String, TableInfo.Column>(6);
        _columnsTriggerLog.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerLog.put("trigger", new TableInfo.Column("trigger", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerLog.put("count", new TableInfo.Column("count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerLog.put("lastSeen", new TableInfo.Column("lastSeen", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerLog.put("hourOfDay", new TableInfo.Column("hourOfDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerLog.put("quitAttemptId", new TableInfo.Column("quitAttemptId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTriggerLog = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTriggerLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTriggerLog = new TableInfo("trigger_log", _columnsTriggerLog, _foreignKeysTriggerLog, _indicesTriggerLog);
        final TableInfo _existingTriggerLog = TableInfo.read(db, "trigger_log");
        if (!_infoTriggerLog.equals(_existingTriggerLog)) {
          return new RoomOpenHelper.ValidationResult(false, "trigger_log(com.unsmoke.app.core.data.database.entity.TriggerLogEntity).\n"
                  + " Expected:\n" + _infoTriggerLog + "\n"
                  + " Found:\n" + _existingTriggerLog);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "4eb04768caf5741b56a6591bc2d933fe", "a48af945df4073666b8ef7f024a26b15");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "user_profile","quit_attempt","smoking_baseline","smoking_event","craving_event","nrt_product","nrt_usage","nrt_reminder","mood_entry","daily_checkin","journal_entry","achievement","quit_reason","reward_goal","notification_preference","implementation_intention","trigger_log");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `user_profile`");
      _db.execSQL("DELETE FROM `quit_attempt`");
      _db.execSQL("DELETE FROM `smoking_baseline`");
      _db.execSQL("DELETE FROM `smoking_event`");
      _db.execSQL("DELETE FROM `craving_event`");
      _db.execSQL("DELETE FROM `nrt_product`");
      _db.execSQL("DELETE FROM `nrt_usage`");
      _db.execSQL("DELETE FROM `nrt_reminder`");
      _db.execSQL("DELETE FROM `mood_entry`");
      _db.execSQL("DELETE FROM `daily_checkin`");
      _db.execSQL("DELETE FROM `journal_entry`");
      _db.execSQL("DELETE FROM `achievement`");
      _db.execSQL("DELETE FROM `quit_reason`");
      _db.execSQL("DELETE FROM `reward_goal`");
      _db.execSQL("DELETE FROM `notification_preference`");
      _db.execSQL("DELETE FROM `implementation_intention`");
      _db.execSQL("DELETE FROM `trigger_log`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(QuitAttemptDao.class, QuitAttemptDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CravingDao.class, CravingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NRTDao.class, NRTDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CheckInDao.class, CheckInDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProfileDao.class, UserProfileDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public QuitAttemptDao quitAttemptDao() {
    if (_quitAttemptDao != null) {
      return _quitAttemptDao;
    } else {
      synchronized(this) {
        if(_quitAttemptDao == null) {
          _quitAttemptDao = new QuitAttemptDao_Impl(this);
        }
        return _quitAttemptDao;
      }
    }
  }

  @Override
  public CravingDao cravingDao() {
    if (_cravingDao != null) {
      return _cravingDao;
    } else {
      synchronized(this) {
        if(_cravingDao == null) {
          _cravingDao = new CravingDao_Impl(this);
        }
        return _cravingDao;
      }
    }
  }

  @Override
  public NRTDao nrtDao() {
    if (_nRTDao != null) {
      return _nRTDao;
    } else {
      synchronized(this) {
        if(_nRTDao == null) {
          _nRTDao = new NRTDao_Impl(this);
        }
        return _nRTDao;
      }
    }
  }

  @Override
  public CheckInDao checkInDao() {
    if (_checkInDao != null) {
      return _checkInDao;
    } else {
      synchronized(this) {
        if(_checkInDao == null) {
          _checkInDao = new CheckInDao_Impl(this);
        }
        return _checkInDao;
      }
    }
  }

  @Override
  public UserProfileDao userProfileDao() {
    if (_userProfileDao != null) {
      return _userProfileDao;
    } else {
      synchronized(this) {
        if(_userProfileDao == null) {
          _userProfileDao = new UserProfileDao_Impl(this);
        }
        return _userProfileDao;
      }
    }
  }
}
