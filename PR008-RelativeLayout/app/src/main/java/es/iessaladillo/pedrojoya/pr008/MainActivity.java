package es.iessaladillo.pedrojoya.pr008;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsuario;
    private EditText txtClave;
    private Button btnAceptar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(v -> conectar());
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(v -> resetVistas());
        TextView lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        TextView lblClave = (TextView) findViewById(R.id.lblClave);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);
        setCambiarColorFocusListener(lblUsuario, txtUsuario);
        setCambiarColorFocusListener(lblClave, txtClave);
        setCambiarVisibilidadTextWatcher(lblUsuario, txtUsuario);
        setCambiarVisibilidadTextWatcher(lblClave, txtClave);
        // Comprobaciones iniciales.
        setColorSegunFoco(lblUsuario, true);
        checkFormularioValido();
        setTextViewVisibility(txtClave, lblClave);
        setTextViewVisibility(txtUsuario, lblUsuario);
    }

    // Realiza la conexión.
    private void conectar() {
        Toast.makeText(this,
                getString(R.string.conectando_con_el_usuario, txtUsuario.getText().toString()),
                Toast.LENGTH_SHORT).show();
    }

    // Resetea las vistas.
    private void resetVistas() {
        txtUsuario.setText("");
        txtClave.setText("");
    }

    private void setCambiarVisibilidadTextWatcher(TextView lbl, EditText txt) {
        txt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setTextViewVisibility(txt, lbl);
                checkFormularioValido();
            }

        });
    }

    private void setCambiarColorFocusListener(TextView lbl, EditText txt) {
        txt.setOnFocusChangeListener((v, hasFocus) -> setColorSegunFoco(lbl, hasFocus));
    }

    private void checkFormularioValido() {
        btnAceptar.setEnabled(isFormValid());
    }

    private boolean isFormValid() {
        return !TextUtils.isEmpty(txtUsuario.getText().toString()) && !TextUtils.isEmpty(
                txtClave.getText().toString());
    }

    private void setTextViewVisibility(EditText txt, TextView lbl) {
        lbl.setVisibility(
                TextUtils.isEmpty(txt.getText().toString()) ? View.INVISIBLE : View.VISIBLE);
    }

    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        lbl.setTextColor(
                hasFocus ? ContextCompat.getColor(this, R.color.accent) : ContextCompat.getColor(
                        this, R.color.primary));
        lbl.setTypeface(hasFocus ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
    }

}
