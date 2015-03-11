package es.iessaladillo.pedrojoya.pr126;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.transitions.everywhere.AutoTransition;
import android.transitions.everywhere.Slide;
import android.transitions.everywhere.Transition;
import android.transitions.everywhere.TransitionManager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity {

    private LinearLayout llContentedor;
    private Button btnAgregar;

    private int mContador = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llContentedor = (LinearLayout) findViewById(R.id.llContenedor);
        ((Button) findViewById(R.id.btnAgregar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarElemento();
            }
        });
    }

    // Agrega un botón al contenedor.
    public void agregarElemento() {
        // Se crea el botón.
        Button button = new Button(this);
        button.setText(getString(R.string.pulsa_para_eliminar) + " " + ++mContador);
        // Al pulsar el botón se eliminará del contenedor.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se inicia una transición diferida.
                Transition transition = new AutoTransition();
                transition.setDuration(500);
                transition.setInterpolator(new AccelerateInterpolator());
                TransitionManager.beginDelayedTransition(llContentedor, transition);
                // Se elimina el botón del contenedor.
                llContentedor.removeView(v);
            }
        });
        // Se inicia una transición diferida.
        TransitionManager.beginDelayedTransition(llContentedor, new Slide());
        // Se agrega el botón al contenedor (el botón ocupará por completo el ancho del contenedor.
        llContentedor.addView(button, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}