package pedrojoya.iessaladillo.es.pr176;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

class AlumnosLoader extends AsyncTaskLoader<ArrayList<Alumno>> {

    private ArrayList<Alumno> mDatos;

    public AlumnosLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        // Si no se dispone de datos o está activa la bandera de recarga, se recarga.
        if (takeContentChanged() || mDatos == null) {
            Log.d(getContext().getString(R.string.app_name), "forceLoad");
            forceLoad();
        } else {
            // Se entregan los datos disponibles.
            deliverResult(mDatos);
        }
    }

    @Override
    public ArrayList<Alumno> loadInBackground() {
        // Se obtienen los datos y se retornan.
        return DB.getAlumnos();
    }

    @Override
    public void deliverResult(ArrayList<Alumno> data) {
        // Se hace la copia local de los datos.
        mDatos = data;
        // Se entregan los datos a los clientes.
        super.deliverResult(data);
    }

}
