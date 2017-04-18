package es.iessaladillo.pedrojoya.pr206;

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

import activitystarter.ActivityStarter;
import activitystarter.Arg;
import activitystarter.MakeActivityStarter;

@MakeActivityStarter(includeStartForResult = true)
public class AlumnoActivity extends AppCompatActivity {

    private static final String EXTRA_ALUMNO = "EXTRA_ALUMNO";

    @Arg
    Alumno mAlumno;

    private EditText txtNombre;
    private EditText txtEdad;
    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_alumno);
        // Se obtienen los extras del intent con el que se ha llamado la actividad.
        ActivityStarter.fill(this);
        initVistas();
    }

    private void initVistas() {
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        txtNombre = (EditText) this.findViewById(R.id.txtNombre);
        txtNombre.setText(mAlumno.getNombre());
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
        txtEdad.setText(String.valueOf(mAlumno.getEdad()));
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
        return !TextUtils.isEmpty(txtNombre.getText().toString()) && !TextUtils.isEmpty(
                txtEdad.getText().toString()) && Integer.parseInt(txtEdad.getText().toString())
                <= Alumno.MAX_EDAD;
    }

    // Empaqueta los datos de retorno y finaliza la actividad.
    private void prepararRespuesta() {
        // Se crea un nuevo intent sin acción ni destinatario y se le agregan
        // los extras con los datos introducidos.
        Intent intentRetorno = new Intent();
        int edad;
        try {
            edad = Integer.parseInt(txtEdad.getText().toString());
        } catch (NumberFormatException e) {
            edad = Alumno.DEFAULT_EDAD;
        }
        intentRetorno.putExtra(EXTRA_ALUMNO, new Alumno(txtNombre.getText().toString(), edad));
        // Se indica que el resultado es satisfactorio.
        this.setResult(RESULT_OK, intentRetorno);
    }

    public static Alumno getAlumnoFromExtra(Intent intent) {
        return intent.getParcelableExtra(EXTRA_ALUMNO);
    }

}
