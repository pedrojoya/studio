package es.iessaladillo.pedrojoya.pr156.editalumno;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import es.iessaladillo.pedrojoya.pr156.R;
import es.iessaladillo.pedrojoya.pr156.model.Alumno;

public class EditAlumnoActivity extends AppCompatActivity implements EditAlumnoContract.View {

    private static final String EXTRA_ALUMNO = "EXTRA_ALUMNO";

    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnAceptar;

    private EditAlumnoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        mPresenter = new EditAlumnoPresenter(this, getAlumnoFromIntent(getIntent()),
                savedInstanceState == null);
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
            mPresenter.doRetornarDatos(new Alumno(txtNombre.getText().toString(), edad));
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
    public void showDatos(@NonNull Alumno alumno) {
        txtNombre.setText(alumno.getNombre());
        txtEdad.setText(String.valueOf(alumno.getEdad()));
    }

    @SuppressWarnings("SameParameterValue")
    public static void startForResult(Activity activity, int requestCode, Alumno alumno) {
        // Se crea el intent explícito.
        Intent intent = new Intent(activity, EditAlumnoActivity.class);
        // Se añaden como extras los datos iniciales.
        intent.putExtra(EXTRA_ALUMNO, alumno);
        // Envía el intent a la actividad en espera de respuesta, con un
        // determinado código de petición.
        activity.startActivityForResult(intent, requestCode);
    }

    public static Alumno getAlumnoFromIntent(Intent intent) {
        return (intent != null && intent.hasExtra(
                EXTRA_ALUMNO)) ? (Alumno) intent.getParcelableExtra(EXTRA_ALUMNO) : null;
    }

    public static Intent createResultIntent(Alumno alumno) {
        // Se crea un nuevo intent sin acción ni destinatario y se le agregan
        // los extras con los datos introducidos.
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ALUMNO, alumno);
        return intent;
    }

}
