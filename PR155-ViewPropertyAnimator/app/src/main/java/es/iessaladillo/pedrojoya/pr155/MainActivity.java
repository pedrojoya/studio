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
    private static final long DURATION = 1000;
    private static final float TURN = 360f;
    private static final float NEW_SCALE = 1.5f;
    private static final float TRASLATION = 200f;

    private ImageView imgPhoto;
    @SuppressWarnings("FieldCanBeLocal")
    private Spinner spnAnimacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        spnAnimacion = ActivityCompat.requireViewById(this, R.id.spnAnimation);
        imgPhoto = ActivityCompat.requireViewById(this, R.id.imgPhoto);

        spnAnimacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                        long id) {
                    animate(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
    }

    private void animate(int position) {
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
        imgPhoto.animate().scaleX(NEW_SCALE).scaleY(NEW_SCALE).setDuration(DURATION)
                .setInterpolator(
                new AccelerateDecelerateInterpolator()).withEndAction(
                () -> imgPhoto.animate().scaleX(1.0f).scaleY(1.0f).setDuration(DURATION).setInterpolator(
                        new AccelerateDecelerateInterpolator()));
    }

    private void doTranslate() {
        imgPhoto.animate().translationX(TRASLATION).setDuration(DURATION).setInterpolator(
                new OvershootInterpolator()).withEndAction(
                () -> imgPhoto.animate().translationX(0).setDuration(DURATION).setInterpolator(
                        new OvershootInterpolator()));
    }

    private void doTranslateY() {
        imgPhoto.animate().translationY(TRASLATION).setDuration(DURATION).setInterpolator(
                new OvershootInterpolator()).withEndAction(
                () -> imgPhoto.animate().translationY(0).setDuration(DURATION).setInterpolator(
                        new OvershootInterpolator()));
    }

    private void doRotate() {
        imgPhoto.animate().rotation(TURN).setDuration(DURATION).setInterpolator(
                new AccelerateDecelerateInterpolator()).withEndAction(
                () -> imgPhoto.animate().rotation(0).setDuration(DURATION).setInterpolator(
                        new AccelerateDecelerateInterpolator()));
    }

    private void doRotateX() {
        imgPhoto.animate().rotationX(TURN).setDuration(DURATION).setInterpolator(
                new AccelerateDecelerateInterpolator()).withEndAction(
                () -> imgPhoto.animate().rotationX(0).setDuration(DURATION).setInterpolator(
                        new AccelerateDecelerateInterpolator()));
    }

    private void doRotateY() {
        imgPhoto.animate().rotationY(TURN).setDuration(DURATION).setInterpolator(
                new AccelerateDecelerateInterpolator()).withEndAction(
                () -> imgPhoto.animate().rotationY(0).setDuration(DURATION).setInterpolator(
                        new AccelerateDecelerateInterpolator()));
    }

    private void doAlpha() {
        imgPhoto.animate().alpha(0.0f).setDuration(DURATION).setInterpolator(
                new AccelerateDecelerateInterpolator()).withEndAction(
                () -> imgPhoto.animate().alpha(1.0f).setDuration(DURATION).setInterpolator(
                        new AccelerateDecelerateInterpolator()));
    }

    private void doSet() {
        imgPhoto.animate().scaleX(NEW_SCALE).scaleY(NEW_SCALE).rotation(TURN).translationY(-TRASLATION).setDuration(
                DURATION).setInterpolator(new AccelerateDecelerateInterpolator()).withEndAction(
                () -> imgPhoto.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .rotation(0)
                        .translationY(0)
                        .setDuration(DURATION)
                        .setInterpolator(new AccelerateDecelerateInterpolator()));
    }

}
