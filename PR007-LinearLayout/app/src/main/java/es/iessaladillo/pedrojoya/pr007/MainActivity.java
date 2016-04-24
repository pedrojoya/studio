package es.iessaladillo.pedrojoya.pr007;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        OnClickListener {

    // Vistas.
    private EditText txtUsuario;
    private EditText txtClave;
    private Button btnAceptar;
    private TextView lblUsuario;
    private TextView lblClave;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        if (btnAceptar != null) {
            btnAceptar.setOnClickListener(this);
        }
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        if (btnCancelar != null) {
            btnCancelar.setOnClickListener(this);
        }
        lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        lblClave = (TextView) findViewById(R.id.lblClave);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);
        // Se cambia el color del TextView dependiendo de si el EditText
        // correspondiente tiene el foco o no.
        txtUsuario.setOnFocusChangeListener(new OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblUsuario, hasFocus);
            }

        });
        txtClave.setOnFocusChangeListener(new OnFocusChangeListener() {

            // Al cambiar el foco.
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblClave, hasFocus);
            }

        });
        // btnAceptar sólo accesible si hay datos en txtUsuario y txtClave.
        txtUsuario.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            // Después de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                // btnAceptar disponible sólo si hay datos.
                checkDatos();
                // lblUsuario visible sólo si txtUsuario tiene datos.
                checkVisibility(txtUsuario, lblUsuario);
            }

        });
        txtClave.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            // Después de haber cambiado el texto.
            @Override
            public void afterTextChanged(Editable s) {
                // btnAceptar disponible sólo si hay datos.
                checkDatos();
                // lblClave visible sólo si tiene datos.
                checkVisibility(txtClave, lblClave);
            }

        });
        // Comprobaciones iniciales.
        setColorSegunFoco(lblUsuario, true);
        checkDatos();
        checkVisibility(txtClave, lblClave);
        checkVisibility(txtUsuario, lblUsuario);
    }

    // Al hacer click sobre un botón.
    @Override
    public void onClick(View v) {
        // Dependiendo del botón pulsado.
        switch (v.getId()) {
            case R.id.btnAceptar:
                // Se informa de la conexión
                Toast.makeText(
                        this,
                        getString(R.string.conectando_con_el_usuario, txtUsuario.getText().toString()),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancelar:
                // Se resetean las vistas.
                resetVistas();
                break;
        }
    }

    // Resetea las vistas.
    private void resetVistas() {
        txtUsuario.setText("");
        txtClave.setText("");
    }

    // Activa o desactiva el botón de Aceptar dependiendo de si hay datos.
    private void checkDatos() {
        btnAceptar.setEnabled(!TextUtils.isEmpty(txtUsuario.getText()
                .toString())
                && !TextUtils.isEmpty(txtClave.getText().toString()));
    }

    // TextView visible sólo si EditText tiene datos.
    private void checkVisibility(EditText txt, TextView lbl) {
        if (TextUtils.isEmpty(txt.getText().toString())) {
            lbl.setVisibility(View.INVISIBLE);
        } else {
            lbl.setVisibility(View.VISIBLE);
        }
    }

    // Establece el color y estilo del TextView dependiendo de si el
    // EditText correspondiente tiene el foco o no.
    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        if (hasFocus) {
            lbl.setTextColor(ContextCompat.getColor(this, R.color.accent));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTextColor(ContextCompat.getColor(this, R.color.primary_text));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }

}