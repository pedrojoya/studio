package es.iessaladillo.pedrojoya.pr028.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper extends SQLiteOpenHelper {

	// Constantes de sentencias SQL.
	private static final String TBL_ALUMNO_CREATE = "create table "
			+ BD.Alumno.TABLA + "(" + BD.Alumno._ID
			+ " integer primary key autoincrement, " + BD.Alumno.NOMBRE
			+ " text not null, " + BD.Alumno.CURSO + " text not null, "
			+ BD.Alumno.TELEFONO + " text not null, "
			+ BD.Alumno.DIRECCION + " text);";
	private static final String TBL_ALUMNO_DROP = "drop table if exists "
			+ BD.Alumno.TABLA;

	// Constructor. Recibe el contexto.
	public Helper(Context ctx) {
		// Se llama al constructor del padre, que es quien realmente crea o
		// actualiza la versión de BD si es necesario.
		super(ctx, BD.BD_NOMBRE, null, BD.BD_VERSION);
	}

	// Cuando es necesario crear la BD.
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Ejecuto las sentencias SQL de creación de las tablas de la BD.
		db.execSQL(TBL_ALUMNO_CREATE);
	}

	// Método de callback para cuando la BD debe se actualizada de versión
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Se eliminan las tablas y se recrean de nuevo.
		db.execSQL(TBL_ALUMNO_DROP);
		db.execSQL(TBL_ALUMNO_CREATE);
	}
}
