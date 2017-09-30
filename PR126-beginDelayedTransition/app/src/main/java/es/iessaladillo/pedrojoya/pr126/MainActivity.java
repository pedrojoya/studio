package es.iessaladillo.pedrojoya.pr126;

import android.os.Bundle;
import android.support.transition.AutoTransition;
import android.support.transition.Explode;
import android.support.transition.Fade;
import android.support.transition.Slide;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private static final int ANIM_EXPLODE = 0;
    private static final int ANIM_SLIDE_LEFT = 2;
    private static final int ANIM_SLIDE_RIGHT = 3;
    private static final int ANIM_SLIDE_TOP = 4;
    private static final int ANIM_SLIDE_BOTTOM = 5;
    private static final long DURATION = 1000;

    private LinearLayout llContainer;
    private Spinner spnAnimations;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnAdd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        llContainer = findViewById(R.id.llContainer);
        btnAdd = findViewById(R.id.btnAdd);
        spnAnimations = findViewById(R.id.spnAnimation);

        btnAdd.setOnClickListener(v -> add());
    }

    private void add() {
        View view = LayoutInflater.from(this).inflate(R.layout.photo, llContainer, false);
        view.setOnClickListener(this::remove);
        TransitionManager.beginDelayedTransition(llContainer, getSelectedTransition());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.activity_main_imgPhoto_width),
                getResources().getDimensionPixelSize(R.dimen.activity_main_imgPhoto_width));
        params.setMargins(0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin),
                0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
        llContainer.addView(view, params);
    }

    private void remove(View v) {
        Transition transition = new AutoTransition();
        transition.setDuration(DURATION);
        transition.setInterpolator(new AccelerateInterpolator());
        TransitionManager.beginDelayedTransition(llContainer, transition);
        llContainer.removeView(v);
    }

    private Transition getSelectedTransition() {
        switch (spnAnimations.getSelectedItemPosition()) {
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
