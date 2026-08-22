package com.unsmoke.app.core.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.unsmoke.app.core.data.database.entity.QuitAttemptEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QuitAttemptDao_Impl implements QuitAttemptDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuitAttemptEntity> __insertionAdapterOfQuitAttemptEntity;

  public QuitAttemptDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuitAttemptEntity = new EntityInsertionAdapter<QuitAttemptEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quit_attempt` (`id`,`startEpochMillis`,`endEpochMillis`,`status`,`cigarettesPerDay`,`cigarettesPerPack`,`packPrice`,`pricePerCigarette`,`timezone`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuitAttemptEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getStartEpochMillis());
        if (entity.getEndEpochMillis() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getEndEpochMillis());
        }
        statement.bindString(4, entity.getStatus());
        statement.bindDouble(5, entity.getCigarettesPerDay());
        statement.bindLong(6, entity.getCigarettesPerPack());
        statement.bindDouble(7, entity.getPackPrice());
        statement.bindDouble(8, entity.getPricePerCigarette());
        statement.bindString(9, entity.getTimezone());
        statement.bindLong(10, entity.getCreatedAt());
      }
    };
  }

  @Override
  public Object insert(final QuitAttemptEntity attempt,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuitAttemptEntity.insertAndReturnId(attempt);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<QuitAttemptEntity>> getAllAttempts() {
    final String _sql = "SELECT * FROM quit_attempt ORDER BY startEpochMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quit_attempt"}, new Callable<List<QuitAttemptEntity>>() {
      @Override
      @NonNull
      public List<QuitAttemptEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "startEpochMillis");
          final int _cursorIndexOfEndEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "endEpochMillis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCigarettesPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "cigarettesPerDay");
          final int _cursorIndexOfCigarettesPerPack = CursorUtil.getColumnIndexOrThrow(_cursor, "cigarettesPerPack");
          final int _cursorIndexOfPackPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "packPrice");
          final int _cursorIndexOfPricePerCigarette = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerCigarette");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<QuitAttemptEntity> _result = new ArrayList<QuitAttemptEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuitAttemptEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartEpochMillis;
            _tmpStartEpochMillis = _cursor.getLong(_cursorIndexOfStartEpochMillis);
            final Long _tmpEndEpochMillis;
            if (_cursor.isNull(_cursorIndexOfEndEpochMillis)) {
              _tmpEndEpochMillis = null;
            } else {
              _tmpEndEpochMillis = _cursor.getLong(_cursorIndexOfEndEpochMillis);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final double _tmpCigarettesPerDay;
            _tmpCigarettesPerDay = _cursor.getDouble(_cursorIndexOfCigarettesPerDay);
            final int _tmpCigarettesPerPack;
            _tmpCigarettesPerPack = _cursor.getInt(_cursorIndexOfCigarettesPerPack);
            final double _tmpPackPrice;
            _tmpPackPrice = _cursor.getDouble(_cursorIndexOfPackPrice);
            final double _tmpPricePerCigarette;
            _tmpPricePerCigarette = _cursor.getDouble(_cursorIndexOfPricePerCigarette);
            final String _tmpTimezone;
            _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new QuitAttemptEntity(_tmpId,_tmpStartEpochMillis,_tmpEndEpochMillis,_tmpStatus,_tmpCigarettesPerDay,_tmpCigarettesPerPack,_tmpPackPrice,_tmpPricePerCigarette,_tmpTimezone,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<QuitAttemptEntity> getActiveAttempt() {
    final String _sql = "SELECT * FROM quit_attempt WHERE status = 'ACTIVE' LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quit_attempt"}, new Callable<QuitAttemptEntity>() {
      @Override
      @Nullable
      public QuitAttemptEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStartEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "startEpochMillis");
          final int _cursorIndexOfEndEpochMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "endEpochMillis");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCigarettesPerDay = CursorUtil.getColumnIndexOrThrow(_cursor, "cigarettesPerDay");
          final int _cursorIndexOfCigarettesPerPack = CursorUtil.getColumnIndexOrThrow(_cursor, "cigarettesPerPack");
          final int _cursorIndexOfPackPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "packPrice");
          final int _cursorIndexOfPricePerCigarette = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerCigarette");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final QuitAttemptEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpStartEpochMillis;
            _tmpStartEpochMillis = _cursor.getLong(_cursorIndexOfStartEpochMillis);
            final Long _tmpEndEpochMillis;
            if (_cursor.isNull(_cursorIndexOfEndEpochMillis)) {
              _tmpEndEpochMillis = null;
            } else {
              _tmpEndEpochMillis = _cursor.getLong(_cursorIndexOfEndEpochMillis);
            }
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final double _tmpCigarettesPerDay;
            _tmpCigarettesPerDay = _cursor.getDouble(_cursorIndexOfCigarettesPerDay);
            final int _tmpCigarettesPerPack;
            _tmpCigarettesPerPack = _cursor.getInt(_cursorIndexOfCigarettesPerPack);
            final double _tmpPackPrice;
            _tmpPackPrice = _cursor.getDouble(_cursorIndexOfPackPrice);
            final double _tmpPricePerCigarette;
            _tmpPricePerCigarette = _cursor.getDouble(_cursorIndexOfPricePerCigarette);
            final String _tmpTimezone;
            _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new QuitAttemptEntity(_tmpId,_tmpStartEpochMillis,_tmpEndEpochMillis,_tmpStatus,_tmpCigarettesPerDay,_tmpCigarettesPerPack,_tmpPackPrice,_tmpPricePerCigarette,_tmpTimezone,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
