package es.iessaladillo.pedrojoya.pr005;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    // Constantes.
    private static final int RC_ALUMNO = 1;

    // Variables a nivel de clase.
    private String mNombre = "";
    private int mEdad = AlumnoActivity.DEFAULT_EDAD;

    // Vistas.
    private Button btnSolicitar;
    private TextView lblDatos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
        // La actividad responderá al pulsar el botón.
        btnSolicitar.setOnClickListener(this);
        lblDatos = (TextView) this.findViewById(R.id.lblDatos);
    }

    // Cuando se hace click.
    public void onClick(View vista) {
        // Dependiendo del botón.
        switch (vista.getId()) {
        case R.id.btnSolicitar:
            // Solicito los datos del alumno.
            solicitarDatos();
            break;
        }
    }

    // Muestra la actividad AlumnoActivity en espera de respuesta.
    private void solicitarDatos() {
        // Se crea el intent explícito.
        Intent intent = new Intent(this, AlumnoActivity.class);
        // Se añaden como extras los datos iniciales.
        intent.putExtra(AlumnoActivity.EXTRA_NOMBRE, mNombre);
        intent.putExtra("edad", mEdad);
        // Envía el intent a la actividad en espera de respuesta, con un
        // determinado código de petición.
        this.startActivityForResult(intent, RC_ALUMNO);
    }

    // Cuando llega una respuesta.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado es satisfactorio.
        if (resultCode == RESULT_OK) {
            // Depeniendo del código de petición (Request Code)
            switch (requestCode) {
            case RC_ALUMNO:
                // Se obtienen los datos de retorno.
                getDatosRetorno(data);
            }
        }
    }

    // Obtiene los datos retornados del intent de retorno.
    private void getDatosRetorno(Intent intentRetorno) {
        // Se escriben los datos recibidos en las variables globales.
        if (intentRetorno != null) {
            if (intentRetorno.hasExtra(AlumnoActivity.EXTRA_NOMBRE)) {
                mNombre = intentRetorno
                        .getStringExtra(AlumnoActivity.EXTRA_NOMBRE);
            }
            mEdad = intentRetorno.getIntExtra(AlumnoActivity.EXTRA_EDAD,
                    AlumnoActivity.DEFAULT_EDAD);
        }
        // Se muestran los datos recibidos.
        mostrarDatos();
    }

    // Muestra los datos en el TextView de datos.
    private void mostrarDatos() {
        String texto = this.getString(R.string.nombre) + ": " + mNombre + "\n"
                + this.getString(R.string.edad) + ": " + mEdad;
        lblDatos.setText(texto);
    }

}