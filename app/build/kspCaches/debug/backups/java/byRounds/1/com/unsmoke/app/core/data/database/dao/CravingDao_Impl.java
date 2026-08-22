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
import com.unsmoke.app.core.data.database.entity.CravingEventEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class CravingDao_Impl implements CravingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CravingEventEntity> __insertionAdapterOfCravingEventEntity;

  public CravingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCravingEventEntity = new EntityInsertionAdapter<CravingEventEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `craving_event` (`id`,`quitAttemptId`,`timestamp`,`intensity`,`trigger`,`location`,`intervention`,`outcome`,`durationSeconds`,`nrtUsedBefore`,`mood`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CravingEventEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getQuitAttemptId());
        statement.bindLong(3, entity.getTimestamp());
        statement.bindLong(4, entity.getIntensity());
        if (entity.getTrigger() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getTrigger());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getLocation());
        }
        if (entity.getIntervention() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getIntervention());
        }
        statement.bindString(8, entity.getOutcome());
        if (entity.getDurationSeconds() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDurationSeconds());
        }
        final int _tmp = entity.getNrtUsedBefore() ? 1 : 0;
        statement.bindLong(10, _tmp);
        if (entity.getMood() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getMood());
        }
      }
    };
  }

  @Override
  public Object insert(final CravingEventEntity craving,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCravingEventEntity.insertAndReturnId(craving);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<CravingEventEntity>> getCravingsForQuit(final long quitId) {
    final String _sql = "SELECT * FROM craving_event WHERE quitAttemptId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, quitId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"craving_event"}, new Callable<List<CravingEventEntity>>() {
      @Override
      @NonNull
      public List<CravingEventEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQuitAttemptId = CursorUtil.getColumnIndexOrThrow(_cursor, "quitAttemptId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIntensity = CursorUtil.getColumnIndexOrThrow(_cursor, "intensity");
          final int _cursorIndexOfTrigger = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfIntervention = CursorUtil.getColumnIndexOrThrow(_cursor, "intervention");
          final int _cursorIndexOfOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "outcome");
          final int _cursorIndexOfDurationSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "durationSeconds");
          final int _cursorIndexOfNrtUsedBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "nrtUsedBefore");
          final int _cursorIndexOfMood = CursorUtil.getColumnIndexOrThrow(_cursor, "mood");
          final List<CravingEventEntity> _result = new ArrayList<CravingEventEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CravingEventEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpQuitAttemptId;
            _tmpQuitAttemptId = _cursor.getLong(_cursorIndexOfQuitAttemptId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final int _tmpIntensity;
            _tmpIntensity = _cursor.getInt(_cursorIndexOfIntensity);
            final String _tmpTrigger;
            if (_cursor.isNull(_cursorIndexOfTrigger)) {
              _tmpTrigger = null;
            } else {
              _tmpTrigger = _cursor.getString(_cursorIndexOfTrigger);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpIntervention;
            if (_cursor.isNull(_cursorIndexOfIntervention)) {
              _tmpIntervention = null;
            } else {
              _tmpIntervention = _cursor.getString(_cursorIndexOfIntervention);
            }
            final String _tmpOutcome;
            _tmpOutcome = _cursor.getString(_cursorIndexOfOutcome);
            final Long _tmpDurationSeconds;
            if (_cursor.isNull(_cursorIndexOfDurationSeconds)) {
              _tmpDurationSeconds = null;
            } else {
              _tmpDurationSeconds = _cursor.getLong(_cursorIndexOfDurationSeconds);
            }
            final boolean _tmpNrtUsedBefore;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfNrtUsedBefore);
            _tmpNrtUsedBefore = _tmp != 0;
            final Integer _tmpMood;
            if (_cursor.isNull(_cursorIndexOfMood)) {
              _tmpMood = null;
            } else {
              _tmpMood = _cursor.getInt(_cursorIndexOfMood);
            }
            _item = new CravingEventEntity(_tmpId,_tmpQuitAttemptId,_tmpTimestamp,_tmpIntensity,_tmpTrigger,_tmpLocation,_tmpIntervention,_tmpOutcome,_tmpDurationSeconds,_tmpNrtUsedBefore,_tmpMood);
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
