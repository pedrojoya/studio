package es.iessaladillo.pedrojoya.pr187;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import es.iessaladillo.pedrojoya.pr187.events.PostExecuteEvent;
import es.iessaladillo.pedrojoya.pr187.events.PreExecuteEvent;
import es.iessaladillo.pedrojoya.pr187.events.ProgressEvent;

public class TareaService extends IntentService {

    // Constantes.
    private static final String SERVICE_NAME = "tarea";

    private static final String EXTRA_NUM_TRABAJOS = "extra_num_trabajos";
    private static final int DEFAULT_NUM_TRABAJOS = 10;
    private static final String EXTRA_CANCELAR = "extra_cancelar";

    private boolean isCancelled;

    // Constructor.
    public TareaService() {
        // El constructor del padre requiere que se le pase un nombre al
        // servicio.
        super(SERVICE_NAME);
    }

    public static void start(Context context, int numTrabajos) {
        Intent intent = new Intent(context, TareaService.class);
        intent.putExtra(EXTRA_NUM_TRABAJOS, numTrabajos);
        context.startService(intent);
    }

    public static void cancel(Context context) {
        Intent intent = new Intent(context, TareaService.class);
        intent.putExtra(EXTRA_CANCELAR, true);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Se comprueba si se quiere cancelar.
        if (intent.hasExtra(EXTRA_CANCELAR)) {
            isCancelled = true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // Cuando se procesa cada llamada.
    @Override
    protected void onHandleIntent(Intent intent) {
        // Se envía un evento PreExecute.
        publishPreExecute();
        // Se obtiene del Intent el número de trabajos
        int numTrabajos = intent.getIntExtra(EXTRA_NUM_TRABAJOS, DEFAULT_NUM_TRABAJOS);
        // Se realizan los trabajos.
        for (int i = 0; i < numTrabajos && !isCancelled; i++) {
            // Se pone a trabajar.
            trabajar();
            // Se envía un evento de progreso.
            publishProgress(i + 1);
        }
        // Se envia un evento PostExecute.
        publishPostExecute();
    }

    // Simula un trabajo de 1 segundo.
    private void trabajar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void publishPreExecute() {
        EventBus.getDefault().post(new PreExecuteEvent());
    }

    private void publishProgress(int numTrabajo) {
        EventBus.getDefault().post(new ProgressEvent(numTrabajo));
    }

    private void publishPostExecute() {
        EventBus.getDefault().post(new PostExecuteEvent());
    }

}
