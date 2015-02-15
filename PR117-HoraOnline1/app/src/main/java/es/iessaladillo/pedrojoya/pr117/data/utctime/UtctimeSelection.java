package es.iessaladillo.pedrojoya.pr117.data.utctime;

import java.util.Date;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import es.iessaladillo.pedrojoya.pr117.data.base.AbstractSelection;

/**
 * Selection for the {@code utctime} table.
 */
public class UtctimeSelection extends AbstractSelection<UtctimeSelection> {
    @Override
    public Uri uri() {
        return UtctimeColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @param sortOrder How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort
     *            order, which may be unordered.
     * @return A {@code UtctimeCursor} object, which is positioned before the first entry, or null.
     */
    public UtctimeCursor query(ContentResolver contentResolver, String[] projection, String sortOrder) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), sortOrder);
        if (cursor == null) return null;
        return new UtctimeCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null}.
     */
    public UtctimeCursor query(ContentResolver contentResolver, String[] projection) {
        return query(contentResolver, projection, null);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, projection, null, null}.
     */
    public UtctimeCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null, null);
    }


    public UtctimeSelection id(long... value) {
        addEquals("utctime." + UtctimeColumns._ID, toObjectArray(value));
        return this;
    }


    public UtctimeSelection tz(String... value) {
        addEquals(UtctimeColumns.TZ, value);
        return this;
    }

    public UtctimeSelection tzNot(String... value) {
        addNotEquals(UtctimeColumns.TZ, value);
        return this;
    }

    public UtctimeSelection tzLike(String... value) {
        addLike(UtctimeColumns.TZ, value);
        return this;
    }

    public UtctimeSelection hour(Integer... value) {
        addEquals(UtctimeColumns.HOUR, value);
        return this;
    }

    public UtctimeSelection hourNot(Integer... value) {
        addNotEquals(UtctimeColumns.HOUR, value);
        return this;
    }

    public UtctimeSelection hourGt(int value) {
        addGreaterThan(UtctimeColumns.HOUR, value);
        return this;
    }

    public UtctimeSelection hourGtEq(int value) {
        addGreaterThanOrEquals(UtctimeColumns.HOUR, value);
        return this;
    }

    public UtctimeSelection hourLt(int value) {
        addLessThan(UtctimeColumns.HOUR, value);
        return this;
    }

    public UtctimeSelection hourLtEq(int value) {
        addLessThanOrEquals(UtctimeColumns.HOUR, value);
        return this;
    }

    public UtctimeSelection datetime(String... value) {
        addEquals(UtctimeColumns.DATETIME, value);
        return this;
    }

    public UtctimeSelection datetimeNot(String... value) {
        addNotEquals(UtctimeColumns.DATETIME, value);
        return this;
    }

    public UtctimeSelection datetimeLike(String... value) {
        addLike(UtctimeColumns.DATETIME, value);
        return this;
    }

    public UtctimeSelection second(Integer... value) {
        addEquals(UtctimeColumns.SECOND, value);
        return this;
    }

    public UtctimeSelection secondNot(Integer... value) {
        addNotEquals(UtctimeColumns.SECOND, value);
        return this;
    }

    public UtctimeSelection secondGt(int value) {
        addGreaterThan(UtctimeColumns.SECOND, value);
        return this;
    }

    public UtctimeSelection secondGtEq(int value) {
        addGreaterThanOrEquals(UtctimeColumns.SECOND, value);
        return this;
    }

    public UtctimeSelection secondLt(int value) {
        addLessThan(UtctimeColumns.SECOND, value);
        return this;
    }

    public UtctimeSelection secondLtEq(int value) {
        addLessThanOrEquals(UtctimeColumns.SECOND, value);
        return this;
    }

    public UtctimeSelection error(Boolean value) {
        addEquals(UtctimeColumns.ERROR, toObjectArray(value));
        return this;
    }

    public UtctimeSelection minute(Integer... value) {
        addEquals(UtctimeColumns.MINUTE, value);
        return this;
    }

    public UtctimeSelection minuteNot(Integer... value) {
        addNotEquals(UtctimeColumns.MINUTE, value);
        return this;
    }

    public UtctimeSelection minuteGt(int value) {
        addGreaterThan(UtctimeColumns.MINUTE, value);
        return this;
    }

    public UtctimeSelection minuteGtEq(int value) {
        addGreaterThanOrEquals(UtctimeColumns.MINUTE, value);
        return this;
    }

    public UtctimeSelection minuteLt(int value) {
        addLessThan(UtctimeColumns.MINUTE, value);
        return this;
    }

    public UtctimeSelection minuteLtEq(int value) {
        addLessThanOrEquals(UtctimeColumns.MINUTE, value);
        return this;
    }
}
