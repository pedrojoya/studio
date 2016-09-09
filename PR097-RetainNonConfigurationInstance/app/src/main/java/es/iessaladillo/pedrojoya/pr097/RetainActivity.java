package es.iessaladillo.pedrojoya.pr097;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class RetainActivity extends AppCompatActivity {

    private State mEstado;

    private TextView lblMarcador;

    // Al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);
        initVistas();
        // Se obtiene el objeto de estado existente y si no lo hay se crea.
        mEstado = (State) getLastCustomNonConfigurationInstance();
        if (mEstado == null) {
            mEstado = new State();
        }
        // Se actualiza el marcador.
        actualizarMarcador();
    }

    // Obtiene e inicializa las vistas.
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
        mEstado.incrementContador();
        // Se actualiza el marcador.
        actualizarMarcador();
    }

    // Inicia la actividad.
    public static void start(Context context) {
        context.startActivity(new Intent(context, RetainActivity.class));
    }

    // Retorna el objeto a preservar en el cambio de configuración.
    @Override
        public State onRetainCustomNonConfigurationInstance() {
        return mEstado;
    }

    private void actualizarMarcador() {
        // Se muestra el valor del contador en el marcador.
        lblMarcador.setText(String.valueOf(mEstado.getContador()));
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
