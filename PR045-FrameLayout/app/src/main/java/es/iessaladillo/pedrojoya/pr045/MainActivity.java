package es.iessaladillo.pedrojoya.pr045;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_DETALLE_VISIBLE = "STATE_DETALLE_VISIBLE";

    private boolean mDetalleVisible;

    private ImageView imgDetalle;
    private TextView lblDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        restoreInstance(savedInstanceState);
        initVistas();
    }

    private void restoreInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDetalleVisible = savedInstanceState.getBoolean(STATE_DETALLE_VISIBLE, false);
        }
    }

    private void initVistas() {
        imgDetalle = (ImageView) findViewById(R.id.imgDetalle);
        imgDetalle.setOnClickListener(v -> toggleDetalle());
        // Se establece el tipo de letra de los TextView.
        TextView lblTitulo = (TextView) findViewById(R.id.lblTitulo);
        lblTitulo.setTypeface(
                Typeface.createFromAsset(getAssets(), "fonts/alegreya-boldItalic.ttf"));
        TextView lblSubtitulo = (TextView) findViewById(R.id.lblSubtitulo);
        lblSubtitulo.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/alegreya-bold.ttf"));
        lblDetalle = (TextView) findViewById(R.id.lblDetalle);
        lblDetalle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/alegreya-regular.ttf"));
        setupPanelState(mDetalleVisible);
    }

    private void setupPanelState(boolean isVisible) {
        if (isVisible) {
            lblDetalle.setVisibility(View.VISIBLE);
            imgDetalle.setImageResource(R.drawable.ic_action_navigation_expand);
        } else {
            lblDetalle.setVisibility(View.GONE);
            imgDetalle.setImageResource(R.drawable.ic_action_navigation_collapse);
        }
    }

    // Cambia la visibilidad del detalle.
    private void toggleDetalle() {
        mDetalleVisible = !mDetalleVisible;
        setupPanelState(mDetalleVisible);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_DETALLE_VISIBLE, mDetalleVisible);
    }

}
