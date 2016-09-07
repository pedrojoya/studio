package es.iessaladillo.pedrojoya.pr005;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    // Constantes.
    private static final int RC_ALUMNO = 1;

    // Variables a nivel de clase.
    private String nombre;
    private int edad;

    private TextView lblDatos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // La actividad responderá al pulsar el botón.
        Button btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
        if (btnSolicitar != null) {
            btnSolicitar.setOnClickListener(this);
        }
        lblDatos = (TextView) this.findViewById(R.id.lblDatos);
    }

    // Cuando se hace click.
    public void onClick(View vista) {
        // Dependiendo del botón.
        int id = vista.getId();
        if (id == R.id.btnSolicitar) {
            // Solicito los datos del alumno.
            solicitarDatos();
        }
    }

    // Muestra la actividad AlumnoActivity en espera de respuesta.
    private void solicitarDatos() {
        AlumnoActivity.startForResult(this, RC_ALUMNO, nombre, edad);
    }

    // Cuando llega una respuesta.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado es satisfactorio y el código de petición el adecuado.
        if (resultCode == RESULT_OK && requestCode == RC_ALUMNO) {
            // Se obtienen los datos de retorno.
            getDatosRetorno(data);
        }
    }

    // Obtiene los datos retornados del intent de retorno.
    private void getDatosRetorno(Intent intent) {
        // Se actualiza el alumno en base a los datos recibidos.
        if (intent != null) {
            if (intent.hasExtra(AlumnoActivity.EXTRA_NOMBRE)) {
                nombre = intent.getStringExtra(AlumnoActivity.EXTRA_NOMBRE);
            }
            if (intent.hasExtra(AlumnoActivity.EXTRA_EDAD)) {
                edad = intent.getIntExtra(AlumnoActivity.EXTRA_EDAD,
                        Alumno.DEFAULT_EDAD);
            }
        }
        // Se muestran los datos del alumno.
        lblDatos.setText(getString(R.string.datos, nombre, edad));
    }

}