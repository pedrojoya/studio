package es.iessaladillo.pedrojoya.pr005;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class AlumnoActivity extends Activity {

    // Constantes.
    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_EDAD = "edad";
    public static final int DEFAULT_EDAD = 18;

    // Vistas.
    private EditText txtNombre;
    private EditText txtEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_alumno);
        // Se obtienen e inicializan las vistas.
        getVistas();
        // Se obtienen y escriben los datos iniciales.
        getDatosIniciales();
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        findViewById(R.id.btnAceptar)
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // Se retorna la respuesta al componente llamador.
                        retornarRespuesta();
                    }
                });
        txtNombre = (EditText) this.findViewById(R.id.txtNombre);
        txtEdad = (EditText) this.findViewById(R.id.txtEdad);
    }

    // Obtiene los datos iniciales desde el intent con el que ha sido llamada
    // la actividad.
    private void getDatosIniciales() {
        Intent intent = this.getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_NOMBRE)) {
                txtNombre.setText(intent.getStringExtra(EXTRA_NOMBRE));
            }
            txtEdad.setText(intent.getIntExtra(EXTRA_EDAD, DEFAULT_EDAD) + "");
        }
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
            edad = DEFAULT_EDAD;
        }
        intentRetorno.putExtra(EXTRA_EDAD, edad);
        // Se indica que el resultado es satisfactorio.
        this.setResult(RESULT_OK, intentRetorno);
        // Se finaliza la actividad.
        this.finish();
    }

}
