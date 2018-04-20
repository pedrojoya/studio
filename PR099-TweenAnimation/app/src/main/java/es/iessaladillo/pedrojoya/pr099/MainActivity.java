package es.iessaladillo.pedrojoya.pr099;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private ImageView imgImagen;
    private final int[] resIdsAnimaciones = {R.anim.scale, R.anim.translate,
            R.anim.translate_y, R.anim.rotate, R.anim.alpha, R.anim.set,
            R.anim.secuencia, R.anim.rotate_repeat,
            R.anim.rotate_repeat_reverse, R.anim.rotate_anticipate,
            R.anim.rotate_overshoot, R.anim.rotate_anticipate_overshoot,
            R.anim.rotate_cycle, R.anim.rotate_bounce};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        (ActivityCompat.requireViewById(this, R.id.spnAnimacion)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Se realiza la animación seleccionada.
                animar(resIdsAnimaciones[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        imgImagen = ActivityCompat.requireViewById(this, R.id.imgImagen);
    }

    // Carga y realizar la animación con dicho resId.
    private void animar(int resIdAnimacion) {
        Animation animacion = AnimationUtils.loadAnimation(this,
                resIdAnimacion);
        imgImagen.startAnimation(animacion);
    }

}