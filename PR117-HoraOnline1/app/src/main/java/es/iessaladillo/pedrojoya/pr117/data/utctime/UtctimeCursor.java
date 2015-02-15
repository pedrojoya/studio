package es.iessaladillo.pedrojoya.pr117.data.utctime;

import java.util.Date;

import android.database.Cursor;

import es.iessaladillo.pedrojoya.pr117.data.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code utctime} table.
 */
public class UtctimeCursor extends AbstractCursor {
    public UtctimeCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Get the {@code tz} value.
     * Can be {@code null}.
     */
    public String getTz() {
        Integer index = getCachedColumnIndexOrThrow(UtctimeColumns.TZ);
        return getString(index);
    }

    /**
     * Get the {@code hour} value.
     * Can be {@code null}.
     */
    public Integer getHour() {
        return getIntegerOrNull(UtctimeColumns.HOUR);
    }

    /**
     * Get the {@code datetime} value.
     * Can be {@code null}.
     */
    public String getDatetime() {
        Integer index = getCachedColumnIndexOrThrow(UtctimeColumns.DATETIME);
        return getString(index);
    }

    /**
     * Get the {@code second} value.
     * Can be {@code null}.
     */
    public Integer getSecond() {
        return getIntegerOrNull(UtctimeColumns.SECOND);
    }

    /**
     * Get the {@code error} value.
     * Can be {@code null}.
     */
    public Boolean getError() {
        return getBoolean(UtctimeColumns.ERROR);
    }

    /**
     * Get the {@code minute} value.
     * Can be {@code null}.
     */
    public Integer getMinute() {
        return getIntegerOrNull(UtctimeColumns.MINUTE);
    }
}
