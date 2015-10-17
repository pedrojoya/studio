package es.iessaladillo.pedrojoya.pr005;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class AlumnoActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_EDAD = "edad";

    private EditText txtNombre;
    private EditText txtEdad;

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
            if (intent.hasExtra(EXTRA_NOMBRE)) {
                txtNombre.setText(intent.getStringExtra(EXTRA_NOMBRE));
            }
            txtEdad.setText(String.valueOf(intent.getIntExtra(EXTRA_EDAD, Alumno.DEFAULT_EDAD)));
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
        // Se crea un nuevo intent sin acción ni destinatario y se le agregan
        // los extras con los datos introducidos.
        Intent intentRetorno = new Intent();
        intentRetorno.putExtra(EXTRA_NOMBRE, txtNombre.getText().toString());
        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText().toString());
        } catch (NumberFormatException e) {
            edad = Alumno.DEFAULT_EDAD;
        }
        intentRetorno.putExtra(EXTRA_EDAD, edad);
        // Se indica que el resultado es satisfactorio.
        this.setResult(RESULT_OK, intentRetorno);
    }

    // Método estático para iniciar la actividad esperando un resultado.
    public static void startForResult(Activity activity, int requestCode, String nombre, int edad) {
        // Se crea el intent explícito.
        Intent intent = new Intent(activity, AlumnoActivity.class);
        // Se añaden como extras los datos iniciales.
        intent.putExtra(EXTRA_NOMBRE, nombre);
        intent.putExtra(EXTRA_EDAD, edad);
        // Envía el intent a la actividad en espera de respuesta, con un
        // determinado código de petición.
        activity.startActivityForResult(intent, requestCode);
    }

}