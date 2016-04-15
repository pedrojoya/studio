package pedrojoya.iessaladillo.es.pr176;

import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;

public class AlumnosLoader extends AsyncTaskLoader<ArrayList<Alumno>> {

    private ArrayList<Alumno> mDatos;

    public AlumnosLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        // Si no tengo datos o está activa la bandera de recarga, se recarga.
        if (takeContentChanged() || mDatos == null) {
            Log.d(getContext().getString(R.string.app_name), "forceLoad");
            forceLoad();
        }
        else {
            // Como tengo datos y no se ha activado recarga, los entrego
            deliverResult(mDatos);
        }
    }

    @Override
    public ArrayList<Alumno> loadInBackground() {
        return DB.getAlumnos();
    }

    @Override
    public void deliverResult(ArrayList<Alumno> data) {
        mDatos = data;
        super.deliverResult(data);
    }

}
