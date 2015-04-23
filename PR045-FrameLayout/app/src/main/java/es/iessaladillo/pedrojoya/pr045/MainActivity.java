package es.iessaladillo.pedrojoya.pr045;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        OnClickListener {

    // Vistas.
    private ImageView imgDetalle;
    private TextView lblDetalle;
    private RelativeLayout rlPanel;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        rlPanel = (RelativeLayout) findViewById(R.id.rlPanel);
        findViewById(R.id.imgFoto).setOnClickListener(this);
        imgDetalle = (ImageView) findViewById(R.id.imgDetalle);
        imgDetalle.setOnClickListener(this);
        lblDetalle = (TextView) findViewById(R.id.lblDetalle);
        lblDetalle.setVisibility(View.GONE);
        // Se establece el tipo de letra de los TextView.
        ((TextView) findViewById(R.id.lblTitulo)).setTypeface(Typeface
                .createFromAsset(getAssets(), "fonts/alegreya-boldItalic.ttf"));
        ((TextView) findViewById(R.id.lblSubtitulo)).setTypeface(Typeface
                .createFromAsset(getAssets(), "fonts/alegreya-bold.ttf"));
        lblDetalle.setTypeface(Typeface.createFromAsset(getAssets(),
                "fonts/alegreya-regular.ttf"));

    }

    // Al pulsar sobre una vista cuyo click es gestionada por la actividad.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgFoto:
                // Se cambia la visibilidad del panel.
                togglePanel();
                break;
            case R.id.imgDetalle:
                // Se cambia la visibilidad del detalle.
                toggleDetalle();
        }
    }

    // Cambia la visibilidad del detalle.
    private void togglePanel() {
        // Si no está visible se hace visible o viceversa.
        if (rlPanel.getVisibility() == View.GONE) {
            rlPanel.setVisibility(View.VISIBLE);
        } else {
            rlPanel.setVisibility(View.GONE);
        }
    }

    // Cambia la visibilidad del detalle.
    private void toggleDetalle() {
        // Si no está visible se hace visible o viceversa y se cambia el icono
        // de expansión o contracción.
        if (lblDetalle.getVisibility() == View.GONE) {
            lblDetalle.setVisibility(View.VISIBLE);
            imgDetalle.setImageResource(R.drawable.ic_action_navigation_expand);
        } else {
            lblDetalle.setVisibility(View.GONE);
            imgDetalle
                    .setImageResource(R.drawable.ic_action_navigation_collapse);
        }

    }
}