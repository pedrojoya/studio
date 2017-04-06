package es.iessaladillo.pedrojoya.pr005;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AlumnoActivity extends AppCompatActivity {

    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_EDAD = "edad";

    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_alumno);
        // Se obtienen e inicializan las vistas.
        initVistas();
        // Se obtienen y escriben los datos iniciales.
        getDatosRecibidos();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        txtNombre = (EditText) this.findViewById(R.id.txtNombre);
        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnAceptar.setEnabled(isFormularioCorrecto());
            }
        });
        txtEdad = (EditText) this.findViewById(R.id.txtEdad);
        txtEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnAceptar.setEnabled(isFormularioCorrecto());
            }
        });
        if (btnAceptar != null) {
            btnAceptar.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Se prepara la respuesta.
                    prepararRespuesta();
                    // Se finaliza la actividad.
                    finish();
                }
            });
        }
    }

    // Retorna si el formulario tiene datos correctos.
    private boolean isFormularioCorrecto() {
        return !TextUtils.isEmpty(txtNombre.getText().toString())
                && !TextUtils.isEmpty(txtEdad.getText().toString())
                && Integer.parseInt(txtEdad.getText().toString()) <= Alumno.MAX_EDAD;
    }

    // Obtiene los datos iniciales desde el intent con el que ha sido llamada
    // la actividad.
    private void getDatosRecibidos() {
        Intent intent = this.getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_NOMBRE)) {
                txtNombre.setText(intent.getStringExtra(EXTRA_NOMBRE));
            }
            if (intent.hasExtra(EXTRA_EDAD)) {
                txtEdad.setText(String.valueOf(intent.getIntExtra(EXTRA_EDAD, Alumno.DEFAULT_EDAD)));
            }
        }
    }

    // Empaqueta los datos de retorno y finaliza la actividad.
    private void prepararRespuesta() {
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
    @SuppressWarnings("SameParameterValue")
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