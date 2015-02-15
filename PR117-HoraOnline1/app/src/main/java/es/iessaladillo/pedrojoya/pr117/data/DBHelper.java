package es.iessaladillo.pedrojoya.pr117.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import es.iessaladillo.pedrojoya.pr117.BuildConfig;
import es.iessaladillo.pedrojoya.pr117.data.utctime.UtctimeColumns;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "utctime.db";
    private static final int DATABASE_VERSION = 1;
    private static DBHelper sInstance;
    private final Context mContext;
    private final DBHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_UTCTIME = "CREATE TABLE IF NOT EXISTS "
            + UtctimeColumns.TABLE_NAME + " ( "
            + UtctimeColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UtctimeColumns.TZ + " TEXT, "
            + UtctimeColumns.HOUR + " INTEGER, "
            + UtctimeColumns.DATETIME + " TEXT, "
            + UtctimeColumns.SECOND + " INTEGER, "
            + UtctimeColumns.ERROR + " INTEGER, "
            + UtctimeColumns.MINUTE + " INTEGER "
            + " );";

    // @formatter:on

    public static DBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static DBHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static DBHelper newInstancePreHoneycomb(Context context) {
        return new DBHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mOpenHelperCallbacks = new DBHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static DBHelper newInstancePostHoneycomb(Context context) {
        return new DBHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private DBHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new DBHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_UTCTIME);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
