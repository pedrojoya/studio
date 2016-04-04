package es.iessaladillo.pedrojoya.pr172;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private BottomSheetBehavior<RelativeLayout> bsb;
    private ImageView imgDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        imgDetalle = (ImageView) findViewById(R.id.imgDetalle);
        RelativeLayout rlPanel = (RelativeLayout) findViewById(R.id.rlPanel);
        bsb = BottomSheetBehavior.from(rlPanel);
        //bsb.setPeekHeight(getResources().getDimensionPixelSize(R.dimen.bottomSheet_peekHeight));
        bsb.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        imgDetalle.setImageResource(R.drawable.ic_arrow_drop_down);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_HIDDEN:
                        imgDetalle.setImageResource(R.drawable.ic_arrow_drop_up);
                        break;
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
        imgDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bsb.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (bsb.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
    }

}
