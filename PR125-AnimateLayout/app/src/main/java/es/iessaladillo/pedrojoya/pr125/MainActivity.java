package es.iessaladillo.pedrojoya.pr125;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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
        // Se crea la transición y se asoica al contenedor.
        LayoutTransition transition = new LayoutTransition();
        llContentedor.setLayoutTransition(transition);
        // Al aparecer un elemento en el contenedor se anima rotando de 90º a 0º respecto al eje Y.
        // La animación dura la duración estándar de una transición de ese tipo de evento.
        Animator appearAnim = ObjectAnimator.ofFloat(null, "rotationY", 90f, 0f)
                .setDuration(transition.getDuration(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.APPEARING, appearAnim);
        // Al desaparecer un elemento del contenedor se anima rotando de 0º a 90º respecto al eje X.
        Animator disappearAnim = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f)
                .setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));
        transition.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim);
        // Cuando desparece un elemento, el resto de elementos del contenedor se animan
        // desplazándose toda su altura hacia arriba y a la vez encogiéndose a la mitad de su
        // ancho y alto para después volver a su tamaño habitual.
        PropertyValuesHolder pvhSlide = PropertyValuesHolder.ofFloat("y", 0, 1);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f, 1f);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f, 1f);
        Animator changingAppearingAnim = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhSlide, pvhScaleY, pvhScaleX)
                .setDuration(transition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changingAppearingAnim);
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
                llContentedor.removeView(v);
            }
        });
        // Se agrega el botón al contenedor (el botón ocupará por completo el ancho del contenedor.
        llContentedor.addView(button, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
}
