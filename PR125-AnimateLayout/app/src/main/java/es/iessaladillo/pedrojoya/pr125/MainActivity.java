package es.iessaladillo.pedrojoya.pr125;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llContainer;
    @SuppressWarnings("FieldCanBeLocal")
    private SwitchCompat swCustom;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnAdd;

    private LayoutTransition layoutTransition;
    private LayoutTransition defaultLayoutTransition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        llContainer = findViewById(R.id.llContainer);
        btnAdd = findViewById(R.id.btnAdd);
        swCustom = findViewById(R.id.swCustom);

        btnAdd.setOnClickListener(v -> add());
        setupTransitions();
        if (swCustom != null) {
            swCustom.setOnCheckedChangeListener(
                    (buttonView, isChecked) -> llContainer.setLayoutTransition(
                            isChecked ? layoutTransition : defaultLayoutTransition));
        }
    }

    private void setupTransitions() {
        defaultLayoutTransition = llContainer.getLayoutTransition();
        layoutTransition = new LayoutTransition();
        setupAddTransition(layoutTransition);
        setupRemoveTransition(layoutTransition);
        setupOtherTransition(layoutTransition);
    }

    private void setupAddTransition(LayoutTransition layoutTransition) {
        Animator addAnimator = ObjectAnimator.ofFloat(null, "scaleY", 0, 1).setDuration(
                layoutTransition.getDuration(LayoutTransition.APPEARING));
        layoutTransition.setAnimator(LayoutTransition.APPEARING, addAnimator);
    }

    private void setupRemoveTransition(LayoutTransition layoutTransition) {
        Animator removeAnimator = ObjectAnimator.ofFloat(null, "rotationX", 0f, 90f).setDuration(
                layoutTransition.getDuration(LayoutTransition.DISAPPEARING));
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, removeAnimator);
    }

    private void setupOtherTransition(LayoutTransition layoutTransition) {
        PropertyValuesHolder pvhSlide = PropertyValuesHolder.ofFloat(View.Y, 0, 1);
        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 0.5f, 1f);
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 0.5f, 1f);
        Animator changingAppearingAnim = ObjectAnimator.ofPropertyValuesHolder(this, pvhSlide,
                pvhScaleY, pvhScaleX).setDuration(
                layoutTransition.getDuration(LayoutTransition.CHANGE_DISAPPEARING));
        layoutTransition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changingAppearingAnim);
    }

    private void add() {
        View view = LayoutInflater.from(this).inflate(R.layout.photo, llContainer, false);
        view.setOnClickListener(v -> llContainer.removeView(v));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.activity_main_imgPhoto_width),
                getResources().getDimensionPixelSize(R.dimen.activity_main_imgPhoto_width));
        params.setMargins(0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin),
                0, getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin));
        llContainer.addView(view, params);
    }

}
