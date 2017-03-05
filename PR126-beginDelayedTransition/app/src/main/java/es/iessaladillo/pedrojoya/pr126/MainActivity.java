package es.iessaladillo.pedrojoya.pr126;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.AutoTransition;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

@SuppressWarnings("unused")
public class MainActivity extends AppCompatActivity {

    private static final int ANIM_EXPLODE = 0;
    private static final int ANIM_FADE_IN = 1;
    private static final int ANIM_SLIDE_LEFT = 2;
    private static final int ANIM_SLIDE_RIGHT = 3;
    private static final int ANIM_SLIDE_TOP = 4;
    private static final int ANIM_SLIDE_BOTTOM = 5;

    private LinearLayout llContentedor;
    private Spinner spnAnimaciones;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        llContentedor = (LinearLayout) findViewById(R.id.llContenedor);
        Button btnAgregar = (Button) findViewById(R.id.btnAgregar);
        if (btnAgregar != null) {
            btnAgregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    agregarElemento();
                }
            });
        }
        spnAnimaciones = (Spinner) findViewById(R.id.spnAnimacion);
    }

    // Agrega una vista al contenedor.
    private void agregarElemento() {
        // Se infla el elemento a partir de su layout.
        View v = LayoutInflater.from(this).inflate(R.layout.imagen, llContentedor, false);
        // Al hacer click sobre el elemento, se eliminará del contenedor.
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se inicia una transición diferida.
                Transition transition = new AutoTransition();
                transition.setDuration(1000);
                transition.setInterpolator(new AccelerateInterpolator());
                TransitionManager.beginDelayedTransition(llContentedor, transition);
                // Se realizan cambios en el contenedor, lo que provocará que
                // se ejecute la transición.
                llContentedor.removeView(v);
            }
        });
        // Se inicia una transición diferida.
        TransitionManager.beginDelayedTransition(llContentedor, getTipoTransicion());
        // Se añade el elemento al contenedor (provoca la ejecución de la
        // transición.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.imagen_width),
                getResources().getDimensionPixelSize(R.dimen.imagen_width));
        params.setMargins(0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin),
                0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
        llContentedor.addView(v, params);
    }

    // Retorna el objeto Transition a emplear dependiendo de la opción
    // seleccionada en el Spinner.
    private Transition getTipoTransicion() {
        switch (spnAnimaciones.getSelectedItemPosition()) {
            case ANIM_EXPLODE:
                return new Explode();
            case ANIM_SLIDE_LEFT:
                return new Slide(Gravity.START);
            case ANIM_SLIDE_RIGHT:
                return new Slide(Gravity.END);
            case ANIM_SLIDE_TOP:
                return new Slide(Gravity.TOP);
            case ANIM_SLIDE_BOTTOM:
                return new Slide(Gravity.BOTTOM);
            default:
                return new Fade();
        }
    }

}
