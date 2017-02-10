package es.iessaladillo.pedrojoya.pr194;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

// Cargador que entrega una lista de alumnos, obteniéndolas
// en un hilo secundario a partir de petición HTTP con respuesta JSON.
class AlumnosLoader extends AsyncTaskLoader<ArrayList<Alumno>> {

    private ArrayList<Alumno> mDatos;

    public AlumnosLoader(Context context) {
        super(context);
    }

    // Cuando el cargador pasa al estado de iniciado.
    @Override
    protected void onStartLoading() {
        // Si se tienen datos o está activa la bandera de recarga, se recarga.
        if (mDatos == null || takeContentChanged()) {
            forceLoad();
        } else {
            // Como se tienen datos y no se ha activado recarga, se entregan
            deliverResult(mDatos);
        }
    }

    // Tarea de carga de datos en hilo secundario.
    @Override
    public ArrayList<Alumno> loadInBackground() {
        // Se define la lista de cadenas que retornará la tarea.
        ArrayList<Alumno> datos = null;
        // Se obtienen los datos.
        Api.ApiInterface servicio = Api.getApiInterface(getContext());
        Call<List<Alumno>> peticion = servicio.getAlumnos();
        try {
            Response<List<Alumno>> response = peticion.execute();
            if (response.body() != null && response.isSuccessful()) {
                datos = new ArrayList<>(response.body());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Se retorna la lista de cadenas.
        return datos;
    }

    // Cuando se deben entregar los datos. Recibe los datos a entregar.
    @Override
    public void deliverResult(ArrayList<Alumno> datos) {
        // Cacheamos los datos por si nos los vuelven a pedir.
        mDatos = datos;
        // Se entregan los datos.
        super.deliverResult(datos);
    }

}
