package com.unsmoke.app.core.data.database.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.unsmoke.app.core.data.database.entity.NRTProductEntity;
import com.unsmoke.app.core.data.database.entity.NRTUsageEntity;
import java.lang.Class;
import java.lang.Double;
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
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NRTDao_Impl implements NRTDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NRTUsageEntity> __insertionAdapterOfNRTUsageEntity;

  private final EntityInsertionAdapter<NRTProductEntity> __insertionAdapterOfNRTProductEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUsageById;

  public NRTDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNRTUsageEntity = new EntityInsertionAdapter<NRTUsageEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `nrt_usage` (`id`,`productId`,`quitAttemptId`,`timestamp`,`quantity`,`cravingBefore`,`cravingAfter`,`trigger`,`notes`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NRTUsageEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductId());
        statement.bindLong(3, entity.getQuitAttemptId());
        statement.bindLong(4, entity.getTimestamp());
        statement.bindLong(5, entity.getQuantity());
        if (entity.getCravingBefore() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getCravingBefore());
        }
        if (entity.getCravingAfter() == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, entity.getCravingAfter());
        }
        if (entity.getTrigger() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTrigger());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNotes());
        }
      }
    };
    this.__insertionAdapterOfNRTProductEntity = new EntityInsertionAdapter<NRTProductEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `nrt_product` (`id`,`type`,`name`,`nicotineStrengthMg`,`packPrice`,`unitsPerPack`,`pricePerUnit`,`isActive`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final NRTProductEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getType());
        statement.bindString(3, entity.getName());
        if (entity.getNicotineStrengthMg() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getNicotineStrengthMg());
        }
        statement.bindDouble(5, entity.getPackPrice());
        statement.bindLong(6, entity.getUnitsPerPack());
        statement.bindDouble(7, entity.getPricePerUnit());
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__preparedStmtOfDeleteUsageById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM nrt_usage WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertUsage(final NRTUsageEntity usage,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfNRTUsageEntity.insertAndReturnId(usage);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertProduct(final NRTProductEntity product,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfNRTProductEntity.insertAndReturnId(product);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUsageById(final long logId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUsageById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, logId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteUsageById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<NRTProductEntity>> getAllProducts() {
    final String _sql = "SELECT * FROM nrt_product";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"nrt_product"}, new Callable<List<NRTProductEntity>>() {
      @Override
      @NonNull
      public List<NRTProductEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfNicotineStrengthMg = CursorUtil.getColumnIndexOrThrow(_cursor, "nicotineStrengthMg");
          final int _cursorIndexOfPackPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "packPrice");
          final int _cursorIndexOfUnitsPerPack = CursorUtil.getColumnIndexOrThrow(_cursor, "unitsPerPack");
          final int _cursorIndexOfPricePerUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "pricePerUnit");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final List<NRTProductEntity> _result = new ArrayList<NRTProductEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NRTProductEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final Double _tmpNicotineStrengthMg;
            if (_cursor.isNull(_cursorIndexOfNicotineStrengthMg)) {
              _tmpNicotineStrengthMg = null;
            } else {
              _tmpNicotineStrengthMg = _cursor.getDouble(_cursorIndexOfNicotineStrengthMg);
            }
            final double _tmpPackPrice;
            _tmpPackPrice = _cursor.getDouble(_cursorIndexOfPackPrice);
            final int _tmpUnitsPerPack;
            _tmpUnitsPerPack = _cursor.getInt(_cursorIndexOfUnitsPerPack);
            final double _tmpPricePerUnit;
            _tmpPricePerUnit = _cursor.getDouble(_cursorIndexOfPricePerUnit);
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            _item = new NRTProductEntity(_tmpId,_tmpType,_tmpName,_tmpNicotineStrengthMg,_tmpPackPrice,_tmpUnitsPerPack,_tmpPricePerUnit,_tmpIsActive);
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
  public Flow<List<NRTUsageEntity>> getUsagesForQuit(final long quitId) {
    final String _sql = "SELECT * FROM nrt_usage WHERE quitAttemptId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, quitId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"nrt_usage"}, new Callable<List<NRTUsageEntity>>() {
      @Override
      @NonNull
      public List<NRTUsageEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "productId");
          final int _cursorIndexOfQuitAttemptId = CursorUtil.getColumnIndexOrThrow(_cursor, "quitAttemptId");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfCravingBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "cravingBefore");
          final int _cursorIndexOfCravingAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "cravingAfter");
          final int _cursorIndexOfTrigger = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<NRTUsageEntity> _result = new ArrayList<NRTUsageEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final NRTUsageEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final long _tmpQuitAttemptId;
            _tmpQuitAttemptId = _cursor.getLong(_cursorIndexOfQuitAttemptId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final Integer _tmpCravingBefore;
            if (_cursor.isNull(_cursorIndexOfCravingBefore)) {
              _tmpCravingBefore = null;
            } else {
              _tmpCravingBefore = _cursor.getInt(_cursorIndexOfCravingBefore);
            }
            final Integer _tmpCravingAfter;
            if (_cursor.isNull(_cursorIndexOfCravingAfter)) {
              _tmpCravingAfter = null;
            } else {
              _tmpCravingAfter = _cursor.getInt(_cursorIndexOfCravingAfter);
            }
            final String _tmpTrigger;
            if (_cursor.isNull(_cursorIndexOfTrigger)) {
              _tmpTrigger = null;
            } else {
              _tmpTrigger = _cursor.getString(_cursorIndexOfTrigger);
            }
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new NRTUsageEntity(_tmpId,_tmpProductId,_tmpQuitAttemptId,_tmpTimestamp,_tmpQuantity,_tmpCravingBefore,_tmpCravingAfter,_tmpTrigger,_tmpNotes);
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
