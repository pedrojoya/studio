package es.iessaladillo.pedrojoya.pr161;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_LAUNCHED = "state_launched";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Si no había sido cargado, simulamos el tiempo de carga.
        if (savedInstanceState == null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Cambiar el tema antes de llamar a super.onCreate
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_LAUNCHED, true);
        super.onSaveInstanceState(outState);
    }

}
