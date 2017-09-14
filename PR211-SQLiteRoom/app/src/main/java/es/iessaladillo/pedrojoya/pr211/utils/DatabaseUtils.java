package es.iessaladillo.pedrojoya.pr211.utils;

import android.annotation.SuppressLint;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@SuppressWarnings({"WeakerAccess", "unused"})
public class DatabaseUtils {

    private DatabaseUtils() {
    }

    // Execute SQL sentences from version assets file into database.
    // Version assets file must be name version.sql, for example 1.sql
    @SuppressWarnings("SameParameterValue")
    public static void executeSqlFromAssetsFile(SupportSQLiteDatabase db, int version,
            AssetManager assetManager) {
        BufferedReader reader = null;
        try {
            @SuppressLint("DefaultLocale")
            String filename = String.format("%d.sql", version);
            final InputStream inputStream = assetManager.open(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuilder statement = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                // Ignore empty lines and SQL comments.
                if (!TextUtils.isEmpty(line) && !line.startsWith("--")) {
                    statement.append(line.trim());
                }
                // If valid SQL sentence.
                if (line.endsWith(";")) {
                    db.execSQL(statement.toString());
                    statement.setLength(0);
                }
            }
        } catch (IOException e) {
            Log.e("DatabaseUtils", "Error reading SQL file", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.w("DatabaseUtils", "Error closing file reader", e);
                }
            }
        }
    }

}
