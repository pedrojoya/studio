package pedrojoya.iessaladillo.es.pr107;// /*

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class WeatherProvider extends ContentProvider {

     // The URI Matcher used by this content provider.
     private static final UriMatcher sUriMatcher = buildUriMatcher();
     private DBHelper mOpenHelper;

     private static final int METEO = 100;
     private static final int METEO_CON_LOCALIDAD = 101;
     private static final int METEO_CON_LOCALIDAD_Y_FECHA = 102;
     private static final int LOCALIDAD = 300;
     private static final int LOCALIDAD_ID = 301;

     private static UriMatcher buildUriMatcher() {
         // I know what you're thinking.  Why create a UriMatcher when you can use regular
         // expressions instead?  Because you're not crazy, that's why.

         // All paths added to the UriMatcher have a corresponding code to return when a match is
         // found.  The code passed into the constructor represents the code to return for the root
         // URI.  It's common to use NO_MATCH as the code for this case.
         final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
         final String authority = DBContract.CONTENT_AUTHORITY;

         // For each type of URI you want to add, create a corresponding code.
         matcher.addURI(authority, DBContract.PATH_METEO, METEO);
         matcher.addURI(authority, DBContract.PATH_METEO + "/*", METEO_CON_LOCALIDAD);
         matcher.addURI(authority, DBContract.PATH_METEO + "/*/*", METEO_CON_LOCALIDAD_Y_FECHA);

         matcher.addURI(authority, DBContract.PATH_LOCALIDAD, LOCALIDAD);
         matcher.addURI(authority, DBContract.PATH_LOCALIDAD + "/#", LOCALIDAD_ID);

         return matcher;
     }

     private static final SQLiteQueryBuilder sWeatherByLocationSettingQueryBuilder;

     static{
         sWeatherByLocationSettingQueryBuilder = new SQLiteQueryBuilder();
         sWeatherByLocationSettingQueryBuilder.setTables(
                 DBContract.Meteo.NOMBRE_TABLA + " INNER JOIN " +
                         DBContract.Localidad.NOMBRE_TABLA +
                         " ON " + DBContract.Meteo.NOMBRE_TABLA +
                         "." + DBContract.Meteo.COLUMN_LOC_KEY +
                         " = " + DBContract.Localidad.NOMBRE_TABLA +
                         "." + DBContract.Localidad._ID);
     }

     private static final String sLocationSettingSelection =
             DBContract.Localidad.NOMBRE_TABLA+
                     "." + DBContract.Localidad.COLUMN_LOCATION_SETTING + " = ? ";
     private static final String sLocationSettingWithStartDateSelection =
             DBContract.Localidad.NOMBRE_TABLA+
                     "." + DBContract.Localidad.COLUMN_LOCATION_SETTING + " = ? AND " +
                     DBContract.Meteo.FECHA + " >= ? ";

     private static final String sLocationSettingAndDaySelection =
             DBContract.Localidad.NOMBRE_TABLA +
                     "." + DBContract.Localidad.COLUMN_LOCATION_SETTING + " = ? AND " +
                     DBContract.Meteo.FECHA + " = ? ";

     private Cursor getWeatherByLocationSetting(Uri uri, String[] projection, String sortOrder) {
         String locationSetting = DBContract.Meteo.getLocationSettingFromUri(uri);
         String startDate = DBContract.Meteo.getStartDateFromUri(uri);

         String[] selectionArgs;
         String selection;

         if (startDate == null) {
             selection = sLocationSettingSelection;
             selectionArgs = new String[]{locationSetting};
         } else {
             selectionArgs = new String[]{locationSetting, startDate};
             selection = sLocationSettingWithStartDateSelection;
         }

         return sWeatherByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                 projection,
                 selection,
                 selectionArgs,
                 null,
                 null,
                 sortOrder
         );
     }

     private Cursor getWeatherByLocationSettingAndDate(
             Uri uri, String[] projection, String sortOrder) {
         String locationSetting = DBContract.Meteo.getLocationSettingFromUri(uri);
         String date = DBContract.Meteo.getDateFromUri(uri);

         return sWeatherByLocationSettingQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                 projection,
                 sLocationSettingAndDaySelection,
                 new String[]{locationSetting, date},
                 null,
                 null,
                 sortOrder
         );
     }

     @Override
     public boolean onCreate() {
         mOpenHelper = new DBHelper(getContext());
         return true;
     }

     @Override
     public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                         String sortOrder) {
         // Here's the switch statement that, given a URI, will determine what kind of request it is,
         // and query the database accordingly.
         Cursor retCursor;
         switch (sUriMatcher.match(uri)) {
             // "weather/*/*"
             case METEO_CON_LOCALIDAD_Y_FECHA:
             {
                 retCursor = getWeatherByLocationSettingAndDate(uri, projection, sortOrder);
                 break;
             }
             // "weather/*"
             case METEO_CON_LOCALIDAD: {
                 retCursor = getWeatherByLocationSetting(uri, projection, sortOrder);
                 break;
             }
             // "weather"
             case METEO: {
                 retCursor = mOpenHelper.getReadableDatabase().query(
                         DBContract.Meteo.NOMBRE_TABLA,
                         projection,
                         selection,
                         selectionArgs,
                         null,
                         null,
                         sortOrder
                 );
                 break;
             }

             /**
              * TODO YOUR CODE BELOW HERE FOR QUIZ
              * QUIZ - 4b - Implement Location_ID queries
              * https://www.udacity.com/course/viewer#!/c-ud853/l-1576308909/e-1675098551/m-1675098552
              **/

             default:
                 throw new UnsupportedOperationException("Unknown uri: " + uri);
         }
         retCursor.setNotificationUri(getContext().getContentResolver(), uri);
         return retCursor;
     }

     @Override
     public String getType(Uri uri) {

         // Use the Uri Matcher to determine what kind of URI this is.
         final int match = sUriMatcher.match(uri);

         switch (match) {
             case METEO_CON_LOCALIDAD_Y_FECHA:
                 return DBContract.Meteo.CONTENT_ITEM_TYPE;
             case METEO_CON_LOCALIDAD:
                 return DBContract.Meteo.CONTENT_TYPE;
             case METEO:
                 return DBContract.Meteo.CONTENT_TYPE;

             /**
              * TODO YOUR CODE BELOW HERE FOR QUIZ
              * QUIZ - 4b - Coding the Content Provider : getType
              * https://www.udacity.com/course/viewer#!/c-ud853/l-1576308909/e-1675098546/m-1675098547
              **/

             default:
                 throw new UnsupportedOperationException("Unknown uri: " + uri);
         }
     }

     @Override
     public Uri insert(Uri uri, ContentValues values) {
         final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
         final int match = sUriMatcher.match(uri);
         Uri returnUri;

         switch (match) {
             case METEO: {
                 long _id = db.insert(DBContract.Meteo.NOMBRE_TABLA, null, values);
                 if ( _id > 0 )
                     returnUri = DBContract.Meteo.buildWeatherUri(_id);
                 else
                     throw new SQLException("Failed to insert row into " + uri);
                 break;
             }
             case LOCALIDAD: {
                 long _id = db.insert(DBContract.Localidad.NOMBRE_TABLA, null, values);
                 if ( _id > 0 )
                     returnUri = DBContract.Localidad.buildLocationUri(_id);
                 else
                     throw new SQLException("Failed to insert row into " + uri);
                 break;
             }
             default:
                 throw new UnsupportedOperationException("Unknown uri: " + uri);
         }
         getContext().getContentResolver().notifyChange(uri, null);
         return returnUri;
     }

     @Override
     public int delete(Uri uri, String selection, String[] selectionArgs) {
         final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
         final int match = sUriMatcher.match(uri);
         int rowsDeleted;
         switch (match) {
             case METEO:
                 rowsDeleted = db.delete(
                         DBContract.Meteo.NOMBRE_TABLA, selection, selectionArgs);
                 break;
             case LOCALIDAD:
                 rowsDeleted = db.delete(
                         DBContract.Localidad.NOMBRE_TABLA, selection, selectionArgs);
                 break;
             default:
                 throw new UnsupportedOperationException("Unknown uri: " + uri);
         }
         // Because a null deletes all rows
         if (selection == null || rowsDeleted != 0) {
             getContext().getContentResolver().notifyChange(uri, null);
         }
         return rowsDeleted;
     }

     @Override
     public int update(
             Uri uri, ContentValues values, String selection, String[] selectionArgs) {
         /**
          * TODO YOUR CODE BELOW HERE FOR QUIZ
          * QUIZ - 4b - Updating and Deleting
          * https://www.udacity.com/course/viewer#!/c-ud853/l-1576308909/e-1675098563/m-1675098564
          **/
         return 0;
     }

     @Override
     public int bulkInsert(Uri uri, ContentValues[] values) {
         final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
         final int match = sUriMatcher.match(uri);
         switch (match) {
             case METEO:
                 db.beginTransaction();
                 int returnCount = 0;
                 try {
                     for (ContentValues value : values) {
                         long _id = db.insert(DBContract.Meteo.NOMBRE_TABLA, null, value);
                         if (_id != -1) {
                             returnCount++;
                         }
                     }
                     db.setTransactionSuccessful();
                 } finally {
                     db.endTransaction();
                 }
                 getContext().getContentResolver().notifyChange(uri, null);
                 return returnCount;
             default:
                 return super.bulkInsert(uri, values);
         }
     }
}
