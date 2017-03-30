package pedrojoya.iessaladillo.es.pr204.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import pedrojoya.iessaladillo.es.pr204.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cargarFragmento();
    }

    private void cargarFragmento() {
        FragmentManager gestorFragmentos = getSupportFragmentManager();
        Fragment fragment = gestorFragmentos.findFragmentById(R.id.main_content);
        if (fragment == null) {
            gestorFragmentos.beginTransaction().replace(R.id.main_content, MainFragment
                    .newInstance()).commit();
        }
    }

}
