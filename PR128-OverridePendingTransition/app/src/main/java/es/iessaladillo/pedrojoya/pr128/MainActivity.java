package es.iessaladillo.pedrojoya.pr128;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    private static final int ANIM_PULL_RIGHT_INDEX = 1;

    private Spinner spnExit;
    private Spinner spnEnter;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btnNavigate;

    private final int[] mExitAnimationsResIds = {R.anim.push_right, R.anim.push_left, R.anim.push_up, R.anim.push_down, R.anim.scale_down_disappear};
    private final int[] mEnterAnimationsResIds = {R.anim.pull_left, R.anim.pull_right, R.anim.pull_down, R.anim.pull_up, R.anim.scale_up_appear};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        spnExit = findViewById(R.id.spnExit);
        spnEnter = findViewById(R.id.spnEnter);
        btnNavigate = findViewById(R.id.btnNavigate);

        spnEnter.setSelection(ANIM_PULL_RIGHT_INDEX);
        btnNavigate.setOnClickListener(v -> navigate());
    }

    private void navigate() {
        // Inform about return animations.
        SecundaryActivity.start(MainActivity.this,
                mExitAnimationsResIds[spnEnter.getSelectedItemPosition()],
                mEnterAnimationsResIds[spnExit.getSelectedItemPosition()]);
        // Inform about enter and exit animation.
        overridePendingTransition(mEnterAnimationsResIds[spnEnter.getSelectedItemPosition()],
                mExitAnimationsResIds[spnExit.getSelectedItemPosition()]);
    }

}
