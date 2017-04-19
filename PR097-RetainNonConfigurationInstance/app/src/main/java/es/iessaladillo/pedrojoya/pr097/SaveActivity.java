package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class SaveActivity extends AppCompatActivity {

    private static final String STATE_CONTADOR = "STATE_CONTADOR";
    private static final int DEFAULT_CONTADOR = 0;

    private int mContador;

    private TextView lblMarcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        // Se recupera el estado anterior (si lo hay).
        restoreInstance(savedInstanceState);
        initVistas();
        actualizarMarcador();
    }

    private void restoreInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mContador = savedInstanceState.getInt(STATE_CONTADOR, DEFAULT_CONTADOR);
        }
    }

    private void initVistas() {
        lblMarcador = (TextView) findViewById(R.id.lblMarcador);
        Button btnIncrementar = (Button) findViewById(R.id.btnIncrementar);
        btnIncrementar.setOnClickListener(view -> incrementarContador());
    }

    private void incrementarContador() {
        mContador++;
        actualizarMarcador();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CONTADOR, mContador);
    }

    private void actualizarMarcador() {
        lblMarcador.setText(String.valueOf(mContador));
    }

    // Inicia la actividad.
    public static void start(Context context) {
        context.startActivity(new Intent(context, SaveActivity.class));
    }

}
