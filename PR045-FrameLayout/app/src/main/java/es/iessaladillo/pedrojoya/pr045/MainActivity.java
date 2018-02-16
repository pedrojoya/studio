package es.iessaladillo.pedrojoya.pr045;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_DETAIL_VISIBLE = "STATE_DETAIL_VISIBLE";

    private boolean mDetailVisible;

    private ImageView imgDetail;
    private TextView lblDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        restoreInstance(savedInstanceState);
        initViews();
    }

    private void restoreInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDetailVisible = savedInstanceState.getBoolean(STATE_DETAIL_VISIBLE, false);
        }
    }

    private void initViews() {
        imgDetail = findViewById(R.id.imgDetail);
        imgDetail.setOnClickListener(v -> toggleDetailVisibility());
        lblDetail = findViewById(R.id.lblDetail);
        setupPanelState(mDetailVisible);
    }

    private void setupPanelState(boolean isVisible) {
        if (isVisible) {
            lblDetail.setVisibility(View.VISIBLE);
            imgDetail.setImageResource(R.drawable.ic_action_navigation_expand);
        } else {
            lblDetail.setVisibility(View.GONE);
            imgDetail.setImageResource(R.drawable.ic_action_navigation_collapse);
        }
    }

    private void toggleDetailVisibility() {
        mDetailVisible = !mDetailVisible;
        setupPanelState(mDetailVisible);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_DETAIL_VISIBLE, mDetailVisible);
    }

}
