package es.iessaladillo.pedrojoya.pr154;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.AnimatorRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_XML_ANIMATIONS = 16;

    private ImageView imgPhoto;
    @SuppressWarnings("FieldCanBeLocal")
    private Spinner spnAnimation;

    private final @AnimatorRes int[] animationsResIds = {R.animator.scale, R.animator.translate, R.animator
            .translate_y, R.animator.rotate, R.animator.rotate_x, R.animator.rotate_y, R.animator.alpha, R.animator.set, R.animator.secuencia, R.animator.rotate_repeat, R.animator.rotate_repeat_reverse, R.animator.rotate_anticipate, R.animator.rotate_overshoot, R.animator.rotate_anticipate_overshoot, R.animator.rotate_cycle, R.animator.rotate_bounce};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        spnAnimation = ActivityCompat.requireViewById(this, R.id.spnAnimation);
        imgPhoto = ActivityCompat.requireViewById(this, R.id.imgPhoto);

        spnAnimation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        if (position < NUM_XML_ANIMATIONS) {
            startAnimation(animationsResIds[position]);
        } else {
            startObjectAnimator();
        }
    }

    private void startAnimation(int animationsResId) {
        Animator anim = AnimatorInflater.loadAnimator(this, animationsResId);
        anim.setTarget(imgPhoto);
        anim.start();
    }

    private void startObjectAnimator() {
        imgPhoto.setImageDrawable(null);
        ObjectAnimator animator = ObjectAnimator.ofObject(imgPhoto, "backgroundColor",
                new ArgbEvaluator(), Color.RED, Color.GREEN);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                imgPhoto.setImageResource(R.drawable.photo);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

}
