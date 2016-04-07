package es.iessaladillo.pedrojoya.pr173;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private BottomSheetBehavior<RelativeLayout> bsb;
    private ImageView imgDetalle;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);
        initVistas();
    }

    private void initVistas() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getString(R.string.foto_del_dia));
                startActivity(intent);
            }
        });
    }

}
