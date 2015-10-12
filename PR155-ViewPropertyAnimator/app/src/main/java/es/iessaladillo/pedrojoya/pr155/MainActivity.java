package es.iessaladillo.pedrojoya.pr155;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final int SCALE = 0;
    private static final int TRANSLATE = 1;
    private static final int TRANSLATE_Y = 2;
    private static final int ROTATE = 3;
    private static final int ROTATE_X = 4;
    private static final int ROTATE_Y = 5;
    private static final int ALPHA = 6;
    private static final int SET = 7;

    private ImageView imgImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        ((Spinner) findViewById(R.id.spnAnimacion)).setOnItemSelectedListener(
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
        imgImagen = (ImageView) findViewById(R.id.imgImagen);
    }

    // Carga y realizar la animación correspondiente a dicha posición.
    private void animar(int position) {
        switch (position) {
            case SCALE:
                doScale();
                break;
            case TRANSLATE:
                doTranslate();
                break;
            case TRANSLATE_Y:
                doTranslateY();
                break;
            case ROTATE:
                doRotate();
                break;
            case ROTATE_X:
                doRotateX();
                break;
            case ROTATE_Y:
                doRotateY();
                break;
            case ALPHA:
                doAlpha();
                break;
            case SET:
                doSet();
                break;
        }
   }

    private void doScale() {
        imgImagen.animate()
                .scaleX(1.5f).scaleY(1.5f).setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().scaleX(1.0f).scaleY(1.0f)
                                .setDuration(1000)
                                .setInterpolator(
                                        new AccelerateDecelerateInterpolator());

                    }
                });
    }

    private void doTranslate() {
        imgImagen.animate()
                .translationX(200).setDuration(1000)
                .setInterpolator(new OvershootInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().translationX(0).setDuration(1000)
                                .setInterpolator(new OvershootInterpolator());
                    }
                });
    }

    private void doTranslateY() {
        imgImagen.animate()
                .translationY(200).setDuration(1000)
                .setInterpolator(new OvershootInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().translationY(0).setDuration(1000)
                                .setInterpolator(new OvershootInterpolator());
                    }
                });
    }

    private void doRotate() {
        imgImagen.animate()
                .rotation(360).setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().rotation(0).setDuration(1000)
                                .setInterpolator(new AccelerateDecelerateInterpolator());
                    }
                });
    }

    private void doRotateX() {
        imgImagen.animate()
                .rotationX(360).setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().rotationX(0).setDuration(1000)
                                .setInterpolator(new AccelerateDecelerateInterpolator());
                    }
                });
    }

    private void doRotateY() {
        imgImagen.animate()
                .rotationY(360).setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().rotationY(0).setDuration(1000)
                                .setInterpolator(new AccelerateDecelerateInterpolator());
                    }
                });
    }

    private void doAlpha() {
        imgImagen.animate()
                .alpha(0.0f).setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().alpha(1.0f).setDuration(1000)
                                .setInterpolator(new AccelerateDecelerateInterpolator());
                    }
                });
    }

    private void doSet() {
        imgImagen.animate()
                .scaleX(1.5f).scaleY(1.5f).rotation(360).translationY(-200)
                .setDuration(1000)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imgImagen.animate().scaleX(1.0f).scaleY(1.0f)
                                .rotation(0).translationY(0)
                                .setDuration(1000)
                                .setInterpolator(
                                        new AccelerateDecelerateInterpolator());

                    }
                });
    }

}