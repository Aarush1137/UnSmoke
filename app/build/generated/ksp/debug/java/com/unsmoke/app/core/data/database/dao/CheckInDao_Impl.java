package com.unsmoke.app.core.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.unsmoke.app.core.data.database.entity.DailyCheckInEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CheckInDao_Impl implements CheckInDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyCheckInEntity> __insertionAdapterOfDailyCheckInEntity;

  public CheckInDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyCheckInEntity = new EntityInsertionAdapter<DailyCheckInEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_checkin` (`id`,`datestamp`,`dayRating`,`smoked`,`cravingLevel`,`topHelper`,`tomorrowFocus`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyCheckInEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getDatestamp());
        statement.bindString(3, entity.getDayRating());
        final int _tmp = entity.getSmoked() ? 1 : 0;
        statement.bindLong(4, _tmp);
        statement.bindLong(5, entity.getCravingLevel());
        if (entity.getTopHelper() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTopHelper());
        }
        if (entity.getTomorrowFocus() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getTomorrowFocus());
        }
        statement.bindLong(8, entity.getTimestamp());
      }
    };
  }

  @Override
  public Object insert(final DailyCheckInEntity checkIn,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyCheckInEntity.insert(checkIn);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<DailyCheckInEntity>> getAllCheckIns() {
    final String _sql = "SELECT * FROM daily_checkin ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"daily_checkin"}, new Callable<List<DailyCheckInEntity>>() {
      @Override
      @NonNull
      public List<DailyCheckInEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDatestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "datestamp");
          final int _cursorIndexOfDayRating = CursorUtil.getColumnIndexOrThrow(_cursor, "dayRating");
          final int _cursorIndexOfSmoked = CursorUtil.getColumnIndexOrThrow(_cursor, "smoked");
          final int _cursorIndexOfCravingLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "cravingLevel");
          final int _cursorIndexOfTopHelper = CursorUtil.getColumnIndexOrThrow(_cursor, "topHelper");
          final int _cursorIndexOfTomorrowFocus = CursorUtil.getColumnIndexOrThrow(_cursor, "tomorrowFocus");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final List<DailyCheckInEntity> _result = new ArrayList<DailyCheckInEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyCheckInEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpDatestamp;
            _tmpDatestamp = _cursor.getString(_cursorIndexOfDatestamp);
            final String _tmpDayRating;
            _tmpDayRating = _cursor.getString(_cursorIndexOfDayRating);
            final boolean _tmpSmoked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfSmoked);
            _tmpSmoked = _tmp != 0;
            final int _tmpCravingLevel;
            _tmpCravingLevel = _cursor.getInt(_cursorIndexOfCravingLevel);
            final String _tmpTopHelper;
            if (_cursor.isNull(_cursorIndexOfTopHelper)) {
              _tmpTopHelper = null;
            } else {
              _tmpTopHelper = _cursor.getString(_cursorIndexOfTopHelper);
            }
            final String _tmpTomorrowFocus;
            if (_cursor.isNull(_cursorIndexOfTomorrowFocus)) {
              _tmpTomorrowFocus = null;
            } else {
              _tmpTomorrowFocus = _cursor.getString(_cursorIndexOfTomorrowFocus);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _item = new DailyCheckInEntity(_tmpId,_tmpDatestamp,_tmpDayRating,_tmpSmoked,_tmpCravingLevel,_tmpTopHelper,_tmpTomorrowFocus,_tmpTimestamp);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
