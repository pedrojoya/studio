package es.iessaladillo.pedrojoya.pr130;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    // Constantes (se definen con esos nombres para introducir las AsyncTasks).
    private static final int onPreExecute = 0;
    private static final int onProgressUpdate = 1;
    private static final int onPostExecute = 2;
    private static final int NUM_PASOS = 10;

    private ProgressBar prbBarra;
    private TextView lblMensaje;
    private ProgressBar prbCirculo;
    private Button btnIniciar;

    private Manejador mManejador;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se crea el manejador, que estará asociado al hilo desde el que se crea
        // (hilo IU). Se le pasa la actividad, para que al manejar los mensajes ejecute métodos
        // de la misma.
        mManejador = new Manejador(this);
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
        // Se crea la tarea secundaria, un hilo para ella y se inicia.
        (new Thread(new TareaSecundaria(mManejador))).start();
    }

    // Hace visibles las vistas relacionadas con el progreso.
    private void mostrarBarras() {
        prbBarra.setVisibility(View.VISIBLE);
        lblMensaje.setText(getString(R.string.trabajando, 0, NUM_PASOS));
        lblMensaje.setVisibility(View.VISIBLE);
        prbCirculo.setVisibility(View.VISIBLE);
    }

    // Actualiza el valor de las barras de progreso.
    private void actualizarBarras(int progreso) {
        lblMensaje.setText(getString(R.string.trabajando, progreso, NUM_PASOS));
        prbBarra.setProgress(progreso);
    }

    // Muestra el total de tareas realizadas.
    private void mostrarRealizadas(int tareas) {
        lblMensaje.setText(getResources().getQuantityString(R.plurals.realizadas, tareas, tareas));
    }

    // Resetea las vistas relacionadas con el progreso.
    private void resetearVistas() {
        prbBarra.setVisibility(View.INVISIBLE);
        prbBarra.setProgress(0);
        prbCirculo.setVisibility(View.INVISIBLE);
        prbCirculo.setProgress(0);
        btnIniciar.setEnabled(true);
    }

    // Clase interna para la tarea secundaria. Debe ser static para evitar memory leaks.
    private static class TareaSecundaria implements Runnable {

        // El manejador no genera memory leaks porque posee una referencia fuerte a la actividad,
        // sino tan solo una referencia débil.
        private final Manejador manejador;

        public TareaSecundaria(Manejador manejador) {
            this.manejador = manejador;
        }

        @Override
        public void run() {
            // Crea y envía el mensaje de inicio de ejecución al manejador.
            enviarMensajeInicio();
            for (int i = 0; i < NUM_PASOS; i++) {
                // Se pone a trabajar.
                trabajar();
                // Crea y envía un mensaje de actualización al manejador.
                enviarMensajeProgreso(i);
            }
            // Crea y envía el mensaje de fin de ejecución al manejador.
            enviarMensajeFin();
        }

        private void enviarMensajeInicio() {
            Message msgInicio = new Message();
            msgInicio.what = onPreExecute;
            manejador.sendMessage(msgInicio);
        }

        private void enviarMensajeProgreso(int i) {
            Message msgProgreso = new Message();
            msgProgreso.what = onProgressUpdate;
            msgProgreso.arg1 = i + 1;
            manejador.sendMessage(msgProgreso);
        }

        private void enviarMensajeFin() {
            Message msgFin = new Message();
            msgFin.what = onPostExecute;
            msgFin.arg1 = NUM_PASOS;
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

    // Clase interna para el Manejador. Debe ser static para no generar memory leaks.
    static class Manejador extends Handler {

        private final WeakReference<MainActivity>
                mActivityWeakReference;

        Manejador(MainActivity activity) {
            // Se llama implícitamente al constructor por defecto de la clase {{Handler}}, que es
            // Handler(), que asocia el handler al looper del hilo desde el que se está creando (en
            // este caso el hilo de la interfaz de usuario).

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
