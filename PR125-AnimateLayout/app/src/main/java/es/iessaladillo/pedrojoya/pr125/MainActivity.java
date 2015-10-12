package es.iessaladillo.pedrojoya.pr125;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;


public class MainActivity extends AppCompatActivity {

    private LinearLayout llContentedor;
    private LayoutTransition mTransicion;
    private LayoutTransition mPorDefecto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llContentedor = (LinearLayout) findViewById(R.id.llContenedor);
        (findViewById(R.id.btnAgregar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarElemento();
            }
        });
        // Se configuran las transiciones del contenedor.
        configTransicionesContenedor();
        // Si se cambia la opción.
        ((Switch) findViewById(R.id.swPersonalizado))
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llContentedor.setLayoutTransition(mTransicion);
                }
                else {
                    llContentedor.setLayoutTransition(mPorDefecto);
                }
            }
        });
    }

    // Configura las transiciones del contenedor.
    private void configTransicionesContenedor() {
        // Se almacena la transición por defecto.
        mPorDefecto = llContentedor.getLayoutTransition();
        // Se crea la transición.
        mTransicion = new LayoutTransition();
        // Se configura.
        configTransicionAdicion();
        configTransicionEliminacion();
        configTransicionResto();
    }

    // Configura la animación cuando se añade un elemento al contenedor..
    private void configTransicionAdicion() {
        // Al aparecer un elemento en el contenedor se anima escalándose desde
        // 0 a su altura normal.
        Animator adicionAnimator = ObjectAnimator.ofFloat(null, "scaleY", 0, 1)
                .setDuration(mTransicion.getDuration(LayoutTransition.APPEARING));
        mTransicion.setAnimator(LayoutTransition.APPEARING, adicionAnimator);
    }

    // Configura la animación cuando se elimina un elemento del contenedor.
    private void configTransicionEliminacion() {
        // Al desaparecer un elemento del contenedor se anima rotando de 0º a 90º respecto al eje X.
        Animator disappearAnim = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f)
                .setDuration(mTransicion.getDuration(LayoutTransition.DISAPPEARING));
        mTransicion.setAnimator(LayoutTransition.DISAPPEARING, disappearAnim);
    }

    // Configura la animación del resto de elementos cuando se elimina un
    // elemento del contenedor.
    private void configTransicionResto() {
        // Cuando desparece un elemento, el resto de elementos del contenedor se animan
        // desplazándose toda su altura hacia arriba y a la vez encogiéndose a la mitad de su
        // ancho y alto para después volver a su tamaño habitual.
        PropertyValuesHolder pvhSlide = PropertyValuesHolder.ofFloat("y", 0, 1);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.5f, 1f);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.5f, 1f);
        Animator changingAppearingAnim = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhSlide, pvhScaleY, pvhScaleX)
                .setDuration(mTransicion.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        mTransicion.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changingAppearingAnim);
    }

    // Agrega un botón al contenedor.
    private void agregarElemento() {
        // Se infla el elemento a partir de su layout.
        View v = LayoutInflater.from(this).inflate(R.layout.imagen, llContentedor, false);
        // Al hacer click sobre el elemento, se eliminará del contenedor.
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llContentedor.removeView(v);
            }
        });
        // Se añade el elemento al contenedor.
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.imagen_width),
                getResources().getDimensionPixelSize(R.dimen.imagen_width));
        params.setMargins(0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin),
                0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
        llContentedor.addView(v, params);
    }

}
