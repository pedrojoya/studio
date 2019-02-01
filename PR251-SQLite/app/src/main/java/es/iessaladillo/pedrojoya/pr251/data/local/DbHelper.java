package es.iessaladillo.pedrojoya.pr251.data.local;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import androidx.annotation.NonNull;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper instance;

    private final AssetManager assetManager;

    private DbHelper(@NonNull Context context) {
        super(context, DbContract.DB_NAME, null, DbContract.DB_VERSION);
        assetManager = context.getAssets();
    }

    public static DbHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    // Use application context to avoid memory leaks.
                    instance = new DbHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    // Called after creating database file and opening database, and before calling onCreate.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // Enable database log.
        setWriteAheadLoggingEnabled(true);
        // Enable foreign keys in SQLite.
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            executeSqlFromAssetsFile(db, DbContract.DB_VERSION, assetManager);
        } catch (IOException e) {
            throw new RuntimeException("Error reading .sql file in Assets");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables (NOT IN PRODUCTION!!!).
        db.execSQL(DbContract.Student.DROP_TABLE_QUERY);
        try {
            executeSqlFromAssetsFile(db, DbContract.DB_VERSION, assetManager);
        } catch (IOException e) {
            throw new RuntimeException("Error reading .sql file in Assets");
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tables (NOT IN PRODUCTION!!!).
        db.execSQL(DbContract.Student.DROP_TABLE_QUERY);
        try {
            executeSqlFromAssetsFile(db, DbContract.DB_VERSION, assetManager);
        } catch (IOException e) {
            throw new RuntimeException("Error reading .sql file in Assets");
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void executeSqlFromAssetsFile(SQLiteDatabase db, int version,
        AssetManager assetManager) throws IOException {
        final String filename = String.format(Locale.getDefault(), "%d.sql", version);
        final StringBuilder statement = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(assetManager.open(filename)))) {
            for (String line; (line = reader.readLine()) != null; ) {
                // Ignore empty lines and SQL comments. Otherwise add to sentence.
                if (!TextUtils.isEmpty(line) && !line.startsWith("--")) {
                    statement.append(line.trim());
                }
                // If valid SQL sentence, execute it and reset sentence.
                if (line.endsWith(";")) {
                    db.execSQL(statement.toString());
                    statement.setLength(0);
                }
            }
        }
    }

}
