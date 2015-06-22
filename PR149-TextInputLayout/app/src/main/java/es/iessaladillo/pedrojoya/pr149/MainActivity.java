package es.iessaladillo.pedrojoya.pr149;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        setupValidacionTelefono();
        setupValidacionEmail();
    }

    // Configura la validación del teléfono.
    private void setupValidacionTelefono() {
        final TextInputLayout tilTelefono = (TextInputLayout) findViewById(R.id.tilTelefono);
        final EditText txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtTelefono.getText().toString())) {
                    if (!isValidPhoneNumber(txtTelefono.getText().toString())) {
                        tilTelefono.setError("No es un número de teléfono válido");
                    } else {
                        tilTelefono.setErrorEnabled(false);
                    }
                } else {
                    tilTelefono.setErrorEnabled(false);
                }
            }
        });
    }

    // Configura la validación del email.
    private void setupValidacionEmail() {
        final TextInputLayout tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(txtEmail.getText().toString())) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
                        tilEmail.setError("Debe tener el formato usuario@dominio.tipo");
                    } else {
                        tilEmail.setErrorEnabled(false);
                    }
                } else {
                    tilEmail.setErrorEnabled(false);
                }
            }
        });
    }

    // Retorna si la cadena recibida es un número de teléfono válido.
    private boolean isValidPhoneNumber(String cadena) {
        if (cadena.length() > 0 && cadena.length() < 9) return false;
        if (!cadena.startsWith("6") && !cadena.startsWith("7") &&
                !cadena.startsWith("8") && !cadena.startsWith("9"))
            return false;
        return true;
    }

}
