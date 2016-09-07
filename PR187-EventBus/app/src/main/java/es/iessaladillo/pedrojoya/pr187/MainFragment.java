package es.iessaladillo.pedrojoya.pr187;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import es.iessaladillo.pedrojoya.pr187.events.PostExecuteEvent;
import es.iessaladillo.pedrojoya.pr187.events.PreExecuteEvent;
import es.iessaladillo.pedrojoya.pr187.events.ProgressEvent;


public class MainFragment extends Fragment {

    private static final String STATE_IS_TAREA_STARTED = "state_isWorking";

    private ProgressBar prbBarra;
    private TextView lblMensaje;
    private ProgressBar prbCirculo;
    private Button btnIniciar;
    private Button btnCancelar;

    private boolean mIsTareaStarted;

    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initVistas(v);
        return v;
    }

    // Obtiene e inicializa las vistas.
    private void initVistas(View v) {
        prbBarra = (ProgressBar) v.findViewById(R.id.prbBarra);
        lblMensaje = (TextView) v.findViewById(R.id.lblMensaje);
        prbCirculo = (ProgressBar) v.findViewById(R.id.prbCirculo);
        btnIniciar = (Button) v.findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar();
            }
        });
        btnCancelar = (Button) v.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar();
            }
        });
        actualizarVistas(mIsTareaStarted);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_IS_TAREA_STARTED, mIsTareaStarted);
        super.onSaveInstanceState(outState);
    }

    private void iniciar() {
        // Se inicia el servicio pasándole el número de pasos.
        TareaService.start(getContext(), getResources().getInteger(R.integer.numPasos));
    }

    // Cuando se hace click en btnCancelar.
    private void cancelar() {
        // Se envía un nuevo intent al servicio para que se cancele.
        TareaService.cancel(getContext());
    }

    // Actualiza el estado de las vistas. Recibe si la tarea está iniciada o no.
    private void actualizarVistas(boolean iniciada) {
        if (!iniciada) {
            prbBarra.setProgress(0);
            lblMensaje.setText("");
        }
        btnIniciar.setEnabled(!iniciada);
        btnCancelar.setEnabled(iniciada);
        prbBarra.setVisibility(iniciada ? View.VISIBLE : View.INVISIBLE);
        lblMensaje.setVisibility(iniciada ? View.VISIBLE : View.INVISIBLE);
        prbCirculo.setVisibility(iniciada ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Se registra la actividad en el bus.
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        // Se anula el registro de la actividad en el bus.
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @SuppressWarnings({"UnusedParameters", "unused"})
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPreExecuteEvent(PreExecuteEvent event) {
        mIsTareaStarted = true;
        // Se hacen visibles las vistas para el progreso.
        actualizarVistas(true);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProgressEvent(ProgressEvent event) {
        // Se actualiza la barra.
        lblMensaje.setText(getString(R.string.trabajando, event.getNumTrabajo(),
                getResources().getInteger(R.integer.numPasos)));
        prbBarra.setProgress(event.getNumTrabajo());
    }

    @SuppressWarnings({"UnusedParameters", "unused"})
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPostExecuteEvent(PostExecuteEvent event) {
        mIsTareaStarted = false;
        // Se resetean las vistas.
        actualizarVistas(false);
    }

}
