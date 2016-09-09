package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SaveActivity extends AppCompatActivity {

    private static final String STATE_CONTADOR = "STATE_CONTADOR";
    private static final int DEFAULT_CONTADOR = 0;

    private int mContador = 0;

    private TextView lblMarcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        initVistas();
        // Si hay un estado guardado, se recupera.
        if (savedInstanceState != null) {
            mContador = savedInstanceState.getInt(STATE_CONTADOR, DEFAULT_CONTADOR);
        }
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
        outState.putInt(STATE_CONTADOR, mContador);
    }

    // Actualiza la vista marcador.
    private void actualizarMarcador() {
        // Se muestra el valor del mContador en el marcador.
        lblMarcador.setText(String.valueOf(mContador));
    }

    // Inicia la actividad.
    public static void start(Context context) {
        context.startActivity(new Intent(context, SaveActivity.class));
    }

}
