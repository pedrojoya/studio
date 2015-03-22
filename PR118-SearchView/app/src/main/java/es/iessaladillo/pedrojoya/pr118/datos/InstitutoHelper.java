package es.iessaladillo.pedrojoya.pr118.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONArray;

import java.io.InputStream;

import es.iessaladillo.pedrojoya.pr118.R;
import es.iessaladillo.pedrojoya.pr118.models.Alumno;

class InstitutoHelper extends SQLiteOpenHelper {

	private static final String TBL_ALUMNO_CREATE = "create table "
			+ InstitutoContract.Alumno.TABLA + "(" + InstitutoContract.Alumno._ID
			+ " integer primary key autoincrement, " + InstitutoContract.Alumno.NOMBRE
			+ " text not null, " + InstitutoContract.Alumno.CURSO + " text not null, "
			+ InstitutoContract.Alumno.TELEFONO + " text not null, "
			+ InstitutoContract.Alumno.DIRECCION + " text);";
	private static final String TBL_ALUMNO_DROP = "drop table if exists "
			+ InstitutoContract.Alumno.TABLA;
    private final Context mContexto;

    public InstitutoHelper(Context ctx) {
		super(ctx, InstitutoContract.BD_NOMBRE, null, InstitutoContract.BD_VERSION);
        mContexto = ctx;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TBL_ALUMNO_CREATE);
        insertarDatosIniciales(db);
	}

    // Inserta los registros iniciales a partir de un fichero json.
    private void insertarDatosIniciales(SQLiteDatabase db) {
        try {
            String sAlumnosJson = jsonFileFromRawFolderToString(R.raw.datos, mContexto);
            JSONArray array = new JSONArray(sAlumnosJson);
            for (int i = 0; i < array.length(); i++) {
                db.insert(InstitutoContract.Alumno.TABLA, null, Alumno.toContentValues(new Alumno(array.getJSONObject(i))));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método de callback para cuando la BD debe se actualizada de versión
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Se eliminan las tablas y se recrean de nuevo.
		db.execSQL(TBL_ALUMNO_DROP);
		db.execSQL(TBL_ALUMNO_CREATE);
	}

    private static String jsonFileFromRawFolderToString(int resId, Context context) {
        try {
            InputStream entrada = context.getResources().openRawResource(resId);
            byte[] data = new byte[entrada.available()];
            entrada.read(data);
            entrada.close();
            return new String(data);
        } catch (Exception e) {
            return "";
        }
    }

}
