package es.iessaladillo.pedrojoya.pr005.editalumno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import es.iessaladillo.pedrojoya.pr005.R;
import es.iessaladillo.pedrojoya.pr005.model.Alumno;

public class EditAlumnoActivity extends AppCompatActivity implements EditAlumnoContract.View {

    private static final String EXTRA_NOMBRE = "EXTRA_NOMBRE";
    private static final String EXTRA_EDAD = "EXTRA_EDAD";

    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnAceptar;

    private EditAlumnoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        mPresenter = new EditAlumnoPresenter(this, getNombreFromIntent(getIntent()),
                getEdadFromIntent(getIntent()), savedInstanceState == null);
        initVistas();
    }

    private void initVistas() {
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        txtNombre = (EditText) this.findViewById(R.id.txtNombre);
        txtEdad = (EditText) this.findViewById(R.id.txtEdad);
        btnAceptar.setOnClickListener(v -> {
            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText().toString());
            } catch (NumberFormatException e) {
                edad = Alumno.DEFAULT_EDAD;
            }
            mPresenter.doRetornarDatos(txtNombre.getText().toString(), edad);
        });
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onViewAttach(this);
    }

    // Retorna si el formulario tiene datos correctos.
    private boolean isFormularioCorrecto() {
        return !TextUtils.isEmpty(txtNombre.getText().toString()) && !TextUtils.isEmpty(
                txtEdad.getText().toString()) && Integer.parseInt(txtEdad.getText().toString())
                <= Alumno.MAX_EDAD;
    }

    @Override
    public void returnIntent(Intent resultIntent) {
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void showDatos(String nombre, int edad) {
        txtNombre.setText(nombre);
        txtEdad.setText(String.valueOf(edad));
    }

    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity activity, int requestCode, String nombre, int edad) {
        // Se crea el intent explícito.
        Intent intent = new Intent(activity, EditAlumnoActivity.class);
        // Se añaden como extras los datos iniciales.
        intent.putExtra(EXTRA_NOMBRE, nombre);
        intent.putExtra(EXTRA_EDAD, edad);
        // Envía el intent a la actividad en espera de respuesta, con un
        // determinado código de petición.
        activity.startActivityForResult(intent, requestCode);
    }

    public static String getNombreFromIntent(Intent intent) {
        return (intent != null && intent.hasExtra(EXTRA_NOMBRE)) ? intent.getStringExtra(
                EXTRA_NOMBRE) : "";
    }

    public static int getEdadFromIntent(Intent intent) {
        return (intent != null && intent.hasExtra(EXTRA_EDAD)) ? intent.getIntExtra(EXTRA_EDAD,
                Alumno.DEFAULT_EDAD) : Alumno.DEFAULT_EDAD;
    }

    public static Intent createResultIntent(String nombre, int edad) {
        // Se crea un nuevo intent sin acción ni destinatario y se le agregan
        // los extras con los datos introducidos.
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NOMBRE, nombre);
        intent.putExtra(EXTRA_EDAD, edad);
        return intent;
    }

}
