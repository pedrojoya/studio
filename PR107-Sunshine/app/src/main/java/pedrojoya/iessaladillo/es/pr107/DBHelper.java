/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pedrojoya.iessaladillo.es.pr107;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "sunshine.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * TODO YOUR CODE BELOW HERE FOR QUIZ
         * QUIZ - 4a - LocationEntry
         * https://www.udacity.com/course/viewer#!/c-ud853/l-1639338560/e-1633698599/m-1633698600
         **/

        final String SQL_CREATE_TABLE_LOCALIDAD = "";

        final String SQL_CREATE_TABLE_METEO = "CREATE TABLE " + DBContract.Meteo.NOMBRE_TABLA + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                DBContract.Meteo._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                DBContract.Meteo.LOCALIDAD_ID + " INTEGER NOT NULL, " +
                DBContract.Meteo.FECHA + " TEXT NOT NULL, " +
                DBContract.Meteo.DESCRIPCION_CORTA + " TEXT NOT NULL, " +
                DBContract.Meteo.METEO_ID + " INTEGER NOT NULL," +

                DBContract.Meteo.TEMPERATURA_MINIMA + " REAL NOT NULL, " +
                DBContract.Meteo.TEMPERATURA_MAXIMA + " REAL NOT NULL, " +

                DBContract.Meteo.HUMEDAD + " REAL NOT NULL, " +
                DBContract.Meteo.PRESION + " REAL NOT NULL, " +
                DBContract.Meteo.VELOCIDAD_VIENTO + " REAL NOT NULL, " +
                DBContract.Meteo.GRADOS + " REAL NOT NULL, " +

                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + DBContract.Meteo.LOCALIDAD_ID + ") REFERENCES " +
                DBContract.Localidad.NOMBRE_TABLA + " (" + DBContract.Localidad._ID + "), " +

                // To assure the application have just one weather entry per day
                // per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + DBContract.Meteo.FECHA + ", " +
                DBContract.Meteo.LOCALIDAD_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_LOCALIDAD);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_METEO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Localidad.NOMBRE_TABLA);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBContract.Meteo.NOMBRE_TABLA);
        onCreate(sqLiteDatabase);
    }
}
