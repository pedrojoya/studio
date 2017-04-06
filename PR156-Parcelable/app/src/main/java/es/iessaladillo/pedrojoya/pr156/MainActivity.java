package es.iessaladillo.pedrojoya.pr156;

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
    private Alumno mAlumno;

    private TextView lblDatos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se crea el alumno.
        mAlumno = new Alumno("", Alumno.DEFAULT_EDAD);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        Button btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
        if (btnSolicitar != null) {
            // La actividad responderá al pulsar el botón.
            btnSolicitar.setOnClickListener(this);
        }
        lblDatos = (TextView) this.findViewById(R.id.lblDatos);
    }

    // Cuando se hace click.
    public void onClick(View vista) {
        if (vista.getId() == R.id.btnSolicitar) {
            solicitarDatos();
        }
    }

    // Muestra la actividad AlumnoActivity en espera de respuesta.
    private void solicitarDatos() {
        AlumnoActivity.startForResult(this, RC_ALUMNO, mAlumno);
    }

    // Cuando llega una respuesta.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado es satisfactorio.
        if (resultCode == RESULT_OK && requestCode == RC_ALUMNO) {
            getDatosRetorno(data);
        }
    }

    // Obtiene los datos retornados del intent de retorno.
    private void getDatosRetorno(Intent intentRetorno) {
        // Se actualiza el alumno en base a los datos recibidos.
        if (intentRetorno != null) {
            if (intentRetorno.hasExtra(AlumnoActivity.EXTRA_ALUMNO)) {
                mAlumno = intentRetorno.getParcelableExtra(AlumnoActivity.EXTRA_ALUMNO);
            }
        }
        // Se muestran los datos del alumno.
        alumnoToViews();
    }

    // Muestra los datos del alumno en las vistas.
    private void alumnoToViews() {
        lblDatos.setText(getString(R.string.datos, mAlumno.getNombre(), mAlumno.getEdad()));
    }

}
