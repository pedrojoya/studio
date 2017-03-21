package pedrojoya.iessaladillo.es.pr202;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se carga el fragmento.
        if (getSupportFragmentManager().findFragmentById(R.id.flContent) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, MainFragment.newInstance()).commit();
        }
    }

}
