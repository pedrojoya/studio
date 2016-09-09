package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import icepick.Icepick;
import icepick.State;

public class IcepickActivity extends AppCompatActivity {

    @SuppressWarnings("WeakerAccess")
    @State
    int mContador = 0;

    private TextView lblMarcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        initVistas();
        // Se restaura el estado usando Icepick.
        Icepick.restoreInstanceState(this, savedInstanceState);
        actualizarMarcador();
    }

    private void initVistas() {
        lblMarcador = (TextView) findViewById(R.id.lblMarcador);
        Button btnIncrementar = (Button) findViewById(R.id.btnIncrementar);
        if (btnIncrementar != null) {
            btnIncrementar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    incrementarContador();
                }
            });
        }
    }

    private void incrementarContador() {
        mContador++;
        // Se actualiza el marcador.
        actualizarMarcador();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Se guarda el estado usando Icepick.
        Icepick.saveInstanceState(this, outState);
    }

    // Actualiza la vista marcador.
    private void actualizarMarcador() {
        // Se muestra el valor del mContador en el marcador.
        lblMarcador.setText(String.valueOf(mContador));
    }

    // Inicia la actividad.
    public static void start(Context context) {
        context.startActivity(new Intent(context, IcepickActivity.class));
    }

}
