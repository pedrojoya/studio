package es.iessaladillo.pedrojoya.pr129;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        OnClickListener {

    private Button btnIniciar;
    private Thread hiloSecundario;
    private TextView lblTiempo;
    private final SimpleDateFormat formateador = new SimpleDateFormat
            ("HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVistas();
    }

    private void getVistas() {
        lblTiempo = (TextView) this.findViewById(R.id.lblTiempo);
        lblTiempo.setText(formateador.format(new Date()));
        btnIniciar = (Button) this.findViewById(R.id.btnIniciar);
        if (btnIniciar != null) {
            btnIniciar.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        // Dependiendo del botón pulsado.
        switch (v.getId()) {
        case R.id.btnIniciar:
            if (btnIniciar.getText().toString()
                    .equals(getString(R.string.iniciar))) {
                iniciar();
            } else {
                parar();
            }
            break;
        }
    }

    private void iniciar() {
        // Se crea el hilo pasándole el tiempo guardado al objeto Crono.
        hiloSecundario = new Thread(new Reloj(), "Secundario");
        // Se inicia el hilo.
        hiloSecundario.start();
        // Se cambia el texto del botón.
        btnIniciar.setText(R.string.parar);
    }

    private void parar() {
        // Se interrumple el hilo.
        hiloSecundario.interrupt();
        // Se cambia el texto del botón.
        btnIniciar.setText(R.string.iniciar);
    }

    private class Reloj implements Runnable {

        // Variables a nivel de clase.
        final SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        @Override
        public void run() {
            while (true) {
                // Se obtiene la representación en cadena de la hora actual.
                // La variable debe ser final para que se pueda enviar en el
                // Runnable que se envía al hilo principal.
                final String cadena = formateador.format(new Date());
                // Se envía la actualización al hilo principal.
                lblTiempo.post(new Runnable() {

                    @Override
                    public void run() {
                        lblTiempo.setText(cadena);
                    }

                });
                // Espera un segundo.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Se finaliza el hilo si se produce la interrupción
                    // mientras se duerme.
                    return;
                }
            }
        }

    }

}
