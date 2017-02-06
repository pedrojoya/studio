package es.iessaladillo.pedrojoya.pr027.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {

    private static DbHelper sInstance;

    // Constantes de sentencias SQL.
    private static final String TBL_ALUMNO_CREATE =
            "create table " + DbContract.Alumno.TABLA + "(" + DbContract.Alumno._ID
                    + " integer primary key autoincrement, " + DbContract.Alumno.NOMBRE
                    + " text not null, " + DbContract.Alumno.CURSO + " text not null, "
                    + DbContract.Alumno.TELEFONO + " text not null, " + DbContract.Alumno.DIRECCION
                    + " text);";
    private static final String TBL_ALUMNO_DROP = "drop table if exists " + DbContract.Alumno.TABLA;

    // Constructor. Recibe el contexto.
    private DbHelper(Context ctx) {
        // Se llama al constructor del padre, que es quien realmente crea o
        // actualiza la versión de BD si es necesario.
        super(ctx, DbContract.BD_NOMBRE, null, DbContract.BD_VERSION);
    }

    // Retorna la instancia (única) del helper.
    public static synchronized DbHelper getInstance(Context context) {
        // Al usar el contexto de la aplicación nos aseguramos de que no haya
        // memory leaks si el recibido es el contexto de una actividad.
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Cuando es necesario crear la BD.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecutan las sentencias SQL de creación de las tablas de la BD.
        db.execSQL(TBL_ALUMNO_CREATE);
    }

    // Método de callback para cuando la BD debe ser actualizada de versión.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se eliminan las tablas y se recrean de nuevo (solo en versión de desarrollo).
        db.execSQL(TBL_ALUMNO_DROP);
        db.execSQL(TBL_ALUMNO_CREATE);
    }

}
