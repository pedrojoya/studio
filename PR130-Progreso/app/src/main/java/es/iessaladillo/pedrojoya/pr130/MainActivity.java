package es.iessaladillo.pedrojoya.pr130;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends ActionBarActivity {

    // Constantes (se definen con esos nombres para introducir las AsyncTasks).
    private static final int onPreExecute = 0;
    private static final int onProgressUpdate = 1;
    private static final int onPostExecute = 2;

    private ProgressBar prbBarra;
    private TextView lblMensaje;
    private ProgressBar prbCirculo;
    private Button btnIniciar;

    private Manejador manejador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        prbBarra = (ProgressBar) findViewById(R.id.prbBarra);
        lblMensaje = (TextView) findViewById(R.id.lblMensaje);
        prbCirculo = (ProgressBar) findViewById(R.id.prbCirculo);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar();
            }
        });
    }

    // Cuando se hace click en btnIniciar.
    private void iniciar() {
        btnIniciar.setEnabled(false);
        // Se crea el manejador.
        manejador = new Manejador(this);
        // Se crea la tarea secundaria.
        Runnable tarea = new TareaSecundaria();
        // Se crea el hilo y se inicia.
        Thread hiloSecundario = new Thread(tarea);
        hiloSecundario.start();
    }

    // Hace visibles las vistas relacionadas con el progreso.
    private void mostrarBarras() {
        prbBarra.setVisibility(View.VISIBLE);
        lblMensaje.setText(R.string.trabajando);
        lblMensaje.setVisibility(View.VISIBLE);
        prbCirculo.setVisibility(View.VISIBLE);
    }

    // Actualiza el valor de las barras de progreso.
    private void actualizarBarras(int progreso) {
        lblMensaje.setText(getString(R.string.trabajando) + " " + progreso
                + " de 10");
        prbBarra.setProgress(progreso);
    }

    // Muestra el total de tareas realizadas.
    private void mostrarRealizadas(int tareas) {
        lblMensaje.setText(getString(R.string.realizadas) + " "
                + tareas + " " + getString(R.string.tareas));
    }

    // Resetea las vistas relacionadas con el progreso.
    private void resetearVistas() {
        prbBarra.setVisibility(View.INVISIBLE);
        prbBarra.setProgress(0);
        prbCirculo.setVisibility(View.INVISIBLE);
        prbCirculo.setProgress(0);
        btnIniciar.setEnabled(true);
    }

    // Clase interna para la Tarea Secundaria.
    private class TareaSecundaria implements Runnable {

        @Override
        public void run() {
            // Crea y envía el mensaje de inicio de ejecución al manejador.
            Message msgInicio = new Message();
            msgInicio.what = onPreExecute;
            manejador.sendMessage(msgInicio);
            // Se realizan diez pasos.
            for (int i = 0; i < 10; i++) {
                // Se pone a trabajar.
                trabajar();
                // Crea y envía un mensaje de actualización al manejador.
                Message msgProgreso = new Message();
                msgProgreso.what = onProgressUpdate;
                msgProgreso.arg1 = i + 1;
                manejador.sendMessage(msgProgreso);
            }
            // Crea y envía el mensaje de fin de ejecución al manejador.
            Message msgFin = new Message();
            msgFin.what = onPostExecute;
            msgFin.arg1 = 10;
            manejador.sendMessage(msgFin);
        }

        // Simula un trabajo de 1 segundo.
        private void trabajar() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    // Clase interna para el Manejador.
    static class Manejador extends Handler {

        private final WeakReference<MainActivity> mActivityWeakReference;

        Manejador(MainActivity activity) {
            // Se almacena una referencia débil de la actividad.
            mActivityWeakReference = new WeakReference<>(activity);
        }

        // Debemos sobrescribir este método para recibir mensajes.
        @Override
        public void handleMessage(Message mensaje) {
            // Se obtiene la actividad (si aún existe).
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null) {
                // Dependiendo del mensaje recibido.
                switch (mensaje.what) {
                    // Mensaje de inicio del hilo secundario.
                    case onPreExecute:
                        // Se hacen visibles las vistas para el progreso.
                        activity.mostrarBarras();
                        break;
                    // Mensaje de progreso del hilo secundario.
                    case onProgressUpdate:
                        // Se actualizan las barras.
                        int progreso = mensaje.arg1;
                        activity.actualizarBarras(progreso);
                        break;
                    // Mensaje de fin del hilo secundario.
                    case onPostExecute:
                        // Se informa al usuario y se resetean las vistas.
                        int tareas = mensaje.arg1;
                        activity.mostrarRealizadas(tareas);
                        activity.resetearVistas();
                        break;
                }
            }
        }

    }

}
