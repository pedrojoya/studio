package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class RetainActivity extends AppCompatActivity {

    private State mEstado;

    private TextView lblMarcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        // Se obtiene el objeto de estado existente y si no lo hay se crea.
        restoreIntance();
        initVistas();
        actualizarMarcador();
    }

    private void restoreIntance() {
        mEstado = (State) getLastCustomNonConfigurationInstance();
        if (mEstado == null) {
            mEstado = new State();
        }
    }

    private void initVistas() {
        lblMarcador = (TextView) findViewById(R.id.lblMarcador);
        Button btnIncrementar = (Button) findViewById(R.id.btnIncrementar);
        btnIncrementar.setOnClickListener(view -> incrementarContador());
    }

    private void incrementarContador() {
        mEstado.incrementContador();
        actualizarMarcador();
    }

    // Retorna el objeto a preservar en el cambio de configuración.
    @Override
    public State onRetainCustomNonConfigurationInstance() {
        return mEstado;
    }

    private void actualizarMarcador() {
        lblMarcador.setText(String.valueOf(mEstado.getContador()));
    }

    // Inicia la actividad.
    public static void start(Context context) {
        context.startActivity(new Intent(context, RetainActivity.class));
    }

    // Clase interna para guardar el estado de la actividad.
    private static class State {
        private int contador = 0;

        public int getContador() {
            return contador;
        }

        public void incrementContador() {
            contador++;
        }
    }

}
