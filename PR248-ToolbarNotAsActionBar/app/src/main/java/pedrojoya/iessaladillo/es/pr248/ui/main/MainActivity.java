package pedrojoya.iessaladillo.es.pr248.ui.main;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import pedrojoya.iessaladillo.es.pr248.R;
import pedrojoya.iessaladillo.es.pr248.ui.secondary.SecondaryActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = ActivityCompat.requireViewById(this, R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.inflateMenu(R.menu.activity_main);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.mnuNext) {
                SecondaryActivity.start(this);
                return true;
            } else {
                return false;
            }
        });
    }

}
