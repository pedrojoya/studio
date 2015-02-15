package es.iessaladillo.pedrojoya.pr117.data.utctime;

import android.content.ContentResolver;
import android.net.Uri;

import es.iessaladillo.pedrojoya.pr117.data.base.AbstractContentValues;
import es.iessaladillo.pedrojoya.pr117.model.UTCTime;

/**
 * Content values wrapper for the {@code utctime} table.
 */
public class UtctimeContentValues extends AbstractContentValues {

    // Construye el content values a partir del objeto modelo.
    public UtctimeContentValues(UTCTime utcTime) {
        putTz(utcTime.getTz());
        putDatetime(utcTime.getDatetime());
        putError(utcTime.getError());
        putHour(utcTime.getHour());
        putMinute(utcTime.getHour());
        putSecond(utcTime.getSecond());
    }

    public UtctimeContentValues() {
    }


    @Override
    public Uri uri() {
        return UtctimeColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, UtctimeSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public UtctimeContentValues putTz(String value) {
        mContentValues.put(UtctimeColumns.TZ, value);
        return this;
    }

    public UtctimeContentValues putTzNull() {
        mContentValues.putNull(UtctimeColumns.TZ);
        return this;
    }


    public UtctimeContentValues putHour(Integer value) {
        mContentValues.put(UtctimeColumns.HOUR, value);
        return this;
    }

    public UtctimeContentValues putHourNull() {
        mContentValues.putNull(UtctimeColumns.HOUR);
        return this;
    }


    public UtctimeContentValues putDatetime(String value) {
        mContentValues.put(UtctimeColumns.DATETIME, value);
        return this;
    }

    public UtctimeContentValues putDatetimeNull() {
        mContentValues.putNull(UtctimeColumns.DATETIME);
        return this;
    }


    public UtctimeContentValues putSecond(Integer value) {
        mContentValues.put(UtctimeColumns.SECOND, value);
        return this;
    }

    public UtctimeContentValues putSecondNull() {
        mContentValues.putNull(UtctimeColumns.SECOND);
        return this;
    }


    public UtctimeContentValues putError(Boolean value) {
        mContentValues.put(UtctimeColumns.ERROR, value);
        return this;
    }

    public UtctimeContentValues putErrorNull() {
        mContentValues.putNull(UtctimeColumns.ERROR);
        return this;
    }


    public UtctimeContentValues putMinute(Integer value) {
        mContentValues.put(UtctimeColumns.MINUTE, value);
        return this;
    }

    public UtctimeContentValues putMinuteNull() {
        mContentValues.putNull(UtctimeColumns.MINUTE);
        return this;
    }

}
