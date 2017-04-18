package es.iessaladillo.pedrojoya.pr206;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int RC_ALUMNO = 1;

    Alumno mAlumno;

    private TextView lblDatos;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlumno = new Alumno("", Alumno.DEFAULT_EDAD);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        // La actividad responderá al pulsar el botón.
        Button btnSolicitar = (Button) this.findViewById(R.id.btnSolicitar);
        btnSolicitar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarDatos();
            }
        });
        lblDatos = (TextView) this.findViewById(R.id.lblDatos);
    }

    // Muestra la actividad AlumnoActivity en espera de respuesta.
    private void solicitarDatos() {
        AlumnoActivityStarter.startForResult(this, mAlumno, RC_ALUMNO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Si el resultado es satisfactorio y el código de petición el adecuado.
        if (resultCode == RESULT_OK && requestCode == RC_ALUMNO) {
            // Se obtienen los extros del intent retornado y se inyectan en los campos del alumno.
            mAlumno = AlumnoActivity.getAlumnoFromExtra(data);
            lblDatos.setText(getString(R.string.datos, mAlumno.getNombre(), mAlumno.getEdad()));
        }
    }

}
