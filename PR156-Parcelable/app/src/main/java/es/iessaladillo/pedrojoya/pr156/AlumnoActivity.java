package es.iessaladillo.pedrojoya.pr156;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class AlumnoActivity extends AppCompatActivity {

    public static final String EXTRA_ALUMNO = "alumno";

    private EditText txtNombre;
    private EditText txtEdad;
    private Alumno mAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_alumno);
        // Se obtienen e inicializan las vistas.
        initVistas();
        // Se obtienen y escriben los datos iniciales.
        getDatosIniciales();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        txtNombre = (EditText) this.findViewById(R.id.txtNombre);
        txtEdad = (EditText) this.findViewById(R.id.txtEdad);
        findViewById(R.id.btnAceptar)
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Se finaliza la actividad.
                        finish();
                    }
                });
    }

    // Obtiene los datos iniciales desde el intent con el que ha sido llamada
    // la actividad.
    private void getDatosIniciales() {
        Intent intent = this.getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_ALUMNO)) {
                mAlumno = intent.getParcelableExtra(EXTRA_ALUMNO);
            }
            // Se escriben los datos del alumno en las vistas.
            alumnoToViews();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Si se pulsa en el icono de navegación se finaliza la actividad.
        if (item.getItemId() == android.R.id.home) {
            finish();
            // Se indica que se ha procesado la pulsación sobre el ítem.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Cuando se finaliza la actividad.
    @Override
    public void finish() {
        retornarRespuesta();
        super.finish();
    }

    // Empaqueta los datos de retorno y finaliza la actividad.
    private void retornarRespuesta() {
        // Se actualizan los datos del alumno.
        viewsToAlumno();
        // Se crea un nuevo intent sin acción ni destinatario y se le agrega
        // el extra de alumno.
        Intent intentRetorno = new Intent();
        intentRetorno.putExtra(EXTRA_ALUMNO, mAlumno);
        // Se indica que el resultado es satisfactorio.
        this.setResult(RESULT_OK, intentRetorno);
    }

    // Muestra los datos del alumno en las vistas correspondientes.
    private void alumnoToViews() {
        txtNombre.setText(mAlumno.getNombre());
        txtEdad.setText(String.valueOf(mAlumno.getEdad()));
    }

    // Establece los datos del alumno en base al contenido de las vistas.
    private void viewsToAlumno() {
        mAlumno.setNombre(txtNombre.getText().toString());
        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText().toString());
        } catch (NumberFormatException e) {
            edad = Alumno.DEFAULT_EDAD;
        }
        mAlumno.setEdad(edad);
    }

    // Método estático para iniciar la actividad esperando un resultado.
    public static void startForResult(Activity activity, int requestCode, Alumno alumno) {
        // Se crea el intent explícito.
        Intent intent = new Intent(activity, AlumnoActivity.class);
        // Se añade como extra el alumno recibido.
        intent.putExtra(EXTRA_ALUMNO, alumno);
        // Envía el intent a la actividad en espera de respuesta, con un
        // determinado código de petición.
        activity.startActivityForResult(intent, requestCode);
    }

}