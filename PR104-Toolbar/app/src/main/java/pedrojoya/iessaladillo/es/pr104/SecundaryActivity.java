package pedrojoya.iessaladillo.es.pr104;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class SecundaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundary);
        // La toolbar actuará como ActionBar.
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        // La ActionBar mostrará el icono de navegación.
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
