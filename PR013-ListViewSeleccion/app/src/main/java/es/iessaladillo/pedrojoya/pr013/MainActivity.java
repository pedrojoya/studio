package es.iessaladillo.pedrojoya.pr013;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnClickListener,
        OnItemClickListener {

    // Vistas.
    private ListView lstRespuestas;
    private TextView lblContador;
    private Button btnComprobar;
    private View vContador;
    private TextView lblPuntuacion;

    // Variables.
    private ObjectAnimator mAnimador;
    private ArrayAdapter<String> mAdaptador;
    private int mPuntuacion = 100;

    // Constantes.
    private static final long CONTADOR_INICIAL = 5000; // milisegundos

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
        vContador = findViewById(R.id.vContador);
        lblPuntuacion = (TextView) findViewById(R.id.lblPuntuacion);
        btnComprobar = (Button) findViewById(R.id.btnComprobar);
        // La actividad actuará como listener cuando se pulse el botón.
        if (btnComprobar != null) {
            btnComprobar.setOnClickListener(this);
        }
        lblContador = (TextView) findViewById(R.id.lblContador);
        lstRespuestas = (ListView) this.findViewById(R.id.lstRespuestas);
        // Se crea y asigna el adaptador a la lista.
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("Marrón");
        respuestas.add("Verde");
        respuestas.add("Blanco");
        respuestas.add("Negro");
        mAdaptador = new ArrayAdapter<>(this,
                R.layout.activity_main_respuesta, R.id.lblRespuesta, respuestas);
        lstRespuestas.setAdapter(mAdaptador);
        // Se trata de una lista de selección simple.
        lstRespuestas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // La actividad actuará como listener cuando se pulse sobre un
        // elemento de la lista.
        lstRespuestas.setOnItemClickListener(this);
    }

    // Al mostrar la actividad.
    @Override
    protected void onResume() {
        // Se anima el fondo del contador.
        animarFondoContador();
        // Se inicia la cuenta atrás.
        long CONTADOR_INTERVALO = 1000;
        new CountDownTimer(CONTADOR_INICIAL, CONTADOR_INTERVALO) {

            // Cuando pasa un intervalo.
            public void onTick(long millisUntilFinished) {
                // Se actualiza el TextView con el valor del contador.
                lblContador.setText((String.valueOf(millisUntilFinished / 1000)));
            }

            // Cuando la cuenta atrás llega a su fin.
            public void onFinish() {
                // Se finaliza la animación del fono del contador.
                mAnimador.end();
                // Se hace visible el botón de comprobación y se oculta el contador.
                btnComprobar.setVisibility(View.VISIBLE);
                lblContador.setVisibility(View.GONE);
                vContador.setVisibility(View.GONE);
            }

        }.start();
        super.onResume();
    }

    // Inicia la animación de rotación del fondo del contador.
    private void animarFondoContador() {
        mAnimador = ObjectAnimator.ofFloat(vContador, View.ROTATION, 0.0f,
                (CONTADOR_INICIAL / 1000) * 360.0f);
        mAnimador.setDuration(CONTADOR_INICIAL);
        mAnimador.setRepeatMode(ObjectAnimator.RESTART);
        mAnimador.setRepeatCount(ObjectAnimator.INFINITE);
        mAnimador.setInterpolator(new LinearInterpolator());
        mAnimador.start();
    }

    // Cuando se hace click en un elemento de la lista.
    @Override
    public void onItemClick(AdapterView<?> lst, View v, int position, long id) {
        // Se activa el botón de comprobación.
        btnComprobar.setEnabled(true);
    }

    // Cuando se pulsa el botón.
    @Override
    public void onClick(View v) {
        // Se obtiene el elemento seleccionado.
        int posSeleccionado = lstRespuestas.getCheckedItemPosition();
        String elemSeleccionado = (String) lstRespuestas
                .getItemAtPosition(posSeleccionado);
        // Se comprueba si la respuesta es correcta.
        boolean correcta = TextUtils.equals(elemSeleccionado, "Blanco");
        if (correcta) {
            // Se muestra la puntuación.
            lblPuntuacion.setText(getString(R.string.puntuacion, "+", mPuntuacion));
            animarPuntuacion(true);
        } else {
            // Se disminuye la puntuación.
            int disminucion = mPuntuacion == 100 ? 50 : 25;
            mPuntuacion -= disminucion;
            // Se muestra la puntuación.
            lblPuntuacion.setText(getString(R.string.puntuacion, "-", disminucion));
            animarPuntuacion(false);
            // Se elimina la respuesta seleccionada del adaptador y se fuerza
            // el repintado de la lista.
            lstRespuestas.setItemChecked(posSeleccionado, false);
            mAdaptador.remove(elemSeleccionado);
            mAdaptador.notifyDataSetChanged();
            // Se desactiva el botón de comprobación.
            btnComprobar.setEnabled(false);
        }
    }

    // Realiza una animación para mostrar la puntuación. Recibe si la respuesta
    // ha sido correcta o no.
    private void animarPuntuacion(final boolean correcta) {
        // Se establece el fondo dependiendo de si es correcta o no.
        lblPuntuacion
                .setBackgroundResource(correcta ? R.drawable.puntuacion_fondo_correcto
                        : R.drawable.puntuacion_fondo_incorrecto);
        // Se realiza la animación.
        lblPuntuacion.setVisibility(View.VISIBLE);
        lblPuntuacion.animate().scaleX(1.2f).scaleY(1.2f).translationY(30)
                .setDuration(3000).setInterpolator(new BounceInterpolator())
                .setListener(new AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }

                    // Cuando termina la animación.
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Si era la respuesta correcta se finaliza la activdad.
                        if (correcta) {
                            finish();
                        } else {
                            // Si la respuesta estaba equivocada, se recoloca
                            // la puntuación para que funciona una futura
                            // animación.
                            recolocarPuntuacion();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                });
    }

    // Se recoloca la puntuación en su posición original.
    private void recolocarPuntuacion() {
        lblPuntuacion.setVisibility(View.INVISIBLE);
        lblPuntuacion.setScaleX(1);
        lblPuntuacion.setScaleY(1);
        lblPuntuacion.setTranslationX(0);
        lblPuntuacion.setTranslationY(0);
    }

}