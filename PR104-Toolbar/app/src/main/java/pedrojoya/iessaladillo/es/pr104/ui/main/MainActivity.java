package pedrojoya.iessaladillo.es.pr104.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import pedrojoya.iessaladillo.es.pr104.R;
import pedrojoya.iessaladillo.es.pr104.ui.secondary.SecondaryActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnuNext) {
            SecondaryActivity.start(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
