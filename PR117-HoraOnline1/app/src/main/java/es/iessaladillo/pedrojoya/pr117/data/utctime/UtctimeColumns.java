package es.iessaladillo.pedrojoya.pr117.data.utctime;

import android.net.Uri;
import android.provider.BaseColumns;

import es.iessaladillo.pedrojoya.pr117.data.UTCTimeProvider;

/**
 * Columns for the {@code utctime} table.
 */
public class UtctimeColumns implements BaseColumns {
    public static final String TABLE_NAME = "utctime";
    public static final Uri CONTENT_URI = Uri.parse(UTCTimeProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = new String(BaseColumns._ID);

    public static final String TZ = "tz";

    public static final String HOUR = "hour";

    public static final String DATETIME = "datetime";

    public static final String SECOND = "second";

    public static final String ERROR = "error";

    public static final String MINUTE = "minute";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TZ,
            HOUR,
            DATETIME,
            SECOND,
            ERROR,
            MINUTE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c == TZ || c.contains("." + TZ)) return true;
            if (c == HOUR || c.contains("." + HOUR)) return true;
            if (c == DATETIME || c.contains("." + DATETIME)) return true;
            if (c == SECOND || c.contains("." + SECOND)) return true;
            if (c == ERROR || c.contains("." + ERROR)) return true;
            if (c == MINUTE || c.contains("." + MINUTE)) return true;
        }
        return false;
    }

}
