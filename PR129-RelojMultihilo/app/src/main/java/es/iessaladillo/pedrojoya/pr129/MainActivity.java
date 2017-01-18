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

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private Button btnIniciar;
    private Thread hiloSecundario;
    private TextView lblTiempo;
    private final SimpleDateFormat mFormateador = new SimpleDateFormat("HH:mm:ss",
            Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        lblTiempo = (TextView) this.findViewById(R.id.lblTiempo);
        lblTiempo.setText(mFormateador.format(new Date()));
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
                if (btnIniciar.getText().toString().equals(getString(R.string.iniciar))) {
                    iniciar();
                } else {
                    parar();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Al destruirse la actividad debemos interrumpir el hilo secundario.
        // Si no lo paráramos, se produciría un memory leak.
        parar();
    }

    private void iniciar() {
        // Se crea el hilo pasándole el tiempo guardado al objeto Crono.
        hiloSecundario = new Thread(new Reloj());
        // Se inicia el hilo.
        hiloSecundario.start();
        // Se cambia el texto del botón.
        btnIniciar.setText(R.string.parar);
    }

    private void actualizarHora(String cadena) {
        lblTiempo.setText(cadena);
    }

    private void parar() {
        // Se interrumple el hilo.
        hiloSecundario.interrupt();
        // Se cambia el texto del botón.
        btnIniciar.setText(R.string.iniciar);
    }

    private class Reloj implements Runnable {

        final SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                final String cadena = formateador.format(new Date());
                // Se crea la tarea de actualización.
                Runnable tarea = new Runnable() {

                    @Override
                    public void run() {
                        actualizarHora(cadena);
                    }

                };
                // Se ejecuta en el hilo principal.
                runOnUiThread(tarea);
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
