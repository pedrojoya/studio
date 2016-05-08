package es.iessaladillo.pedrojoya.pr154;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_XML_ANIMATIONS = 16;
    
    private ImageView imgImagen;
    private final int[] resIdsAnimaciones = {R.animator.scale, R.animator.translate,
            R.animator.translate_y, R.animator.rotate, R.animator.rotate_x,
            R.animator.rotate_y, R.animator.alpha, R.animator.set,
            R.animator.secuencia, R.animator.rotate_repeat,
            R.animator.rotate_repeat_reverse, R.animator.rotate_anticipate,
            R.animator.rotate_overshoot, R.animator.rotate_anticipate_overshoot,
            R.animator.rotate_cycle, R.animator.rotate_bounce};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        Spinner spnAnimacion = (Spinner) findViewById(R.id.spnAnimacion);
        if (spnAnimacion != null) {
            spnAnimacion.setOnItemSelectedListener(
                    new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view,
                                                   int position, long id) {
                            // Se realiza la animación seleccionada.
                            animar(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
        }
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
    }

    // Carga y realizar la animación correspondiente a dicha posición.
    private void animar(int position) {
        if (position < NUM_XML_ANIMATIONS) {
            Animator anim = AnimatorInflater
                .loadAnimator(this, resIdsAnimaciones[position]);
            anim.setTarget(imgImagen);
            anim.start();
        }
        else {
            imgImagen.setImageDrawable(null);
            ObjectAnimator animador = ObjectAnimator.ofObject(imgImagen,
                    "backgroundColor", new ArgbEvaluator(), Color.RED, Color.GREEN);
            animador.setDuration(4000);
            animador.setInterpolator(new LinearInterpolator());
            animador.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    imgImagen.setImageResource(R.drawable.imagen);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            animador.start();
        }
    }

}