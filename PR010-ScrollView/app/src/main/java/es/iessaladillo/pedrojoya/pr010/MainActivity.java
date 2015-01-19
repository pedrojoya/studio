package es.iessaladillo.pedrojoya.pr010;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    // Vistas.
    private EditText txtMensaje;
    private TextView lblTexto;
    private ImageView btnEnviar;
    private ScrollView scvTexto;

    // Variables a nivel de clase.
    private SimpleDateFormat formateadorFechas;

    // Al crear la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        getVistas();
        formateadorFechas = new SimpleDateFormat("HH:mm:ss",
                Locale.getDefault());
    }

    private void getVistas() {
        lblTexto = (TextView) findViewById(R.id.lblTexto);
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        btnEnviar = (ImageView) findViewById(R.id.btnEnviar);
        scvTexto = (ScrollView) findViewById(R.id.scvTexto);
        txtMensaje.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                // Si se ha pulsado la tecla de Ir es como si se pulsara el
                // botón.
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    enviarMensaje(txtMensaje.getText().toString());
                }
                return false;
            }
        });
        txtMensaje.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Se activa o desactiva btnEnviar.
                checkDatos();
            }

        });
        btnEnviar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Se envía el mensaje.
                enviarMensaje(txtMensaje.getText().toString());
            }
        });
        // Se realizan las comprobaciones iniciales.
        checkDatos();
        hacerScroll(scvTexto, View.FOCUS_DOWN);
    }

    // Agrega un mensaje al texto.
    private void enviarMensaje(String mensaje) {
        if (!TextUtils.isEmpty(mensaje)) {
            // Se obtiene la hora actual.
            String hora = formateadorFechas.format(new Date());
            // Se agrega el mensaje.
            lblTexto.append("[" + hora + "] " + mensaje + "\n\n");
            // Se limpia el cuadro de texto.
            txtMensaje.setText("");
            // Se mueve el scroll al final para ver el último mensaje.
            hacerScroll(scvTexto, View.FOCUS_DOWN);
        }
    }

    // Hace scroll en el ScrollView en la dirección indicada.
    private void hacerScroll(final ScrollView scv, final int focus) {
        // Es necesario que se haga en el futuro para que se calcule
        // correctamente el final.
        scv.post(new Runnable() {
            @Override
            public void run() {
                scv.fullScroll(focus);
                // scv.scrollTo(0, scv.getBottom());
            }
        });
    }

    // Activa o desasctiva btnEnviar dependiendo de si hay datos.
    private void checkDatos() {
        btnEnviar.setEnabled(!TextUtils.isEmpty(txtMensaje.getText()));
    }
}