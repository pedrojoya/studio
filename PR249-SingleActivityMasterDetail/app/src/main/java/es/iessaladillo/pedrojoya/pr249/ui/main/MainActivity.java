package es.iessaladillo.pedrojoya.pr249.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.pedrojoya.pr249.R;
import es.iessaladillo.pedrojoya.pr249.ui.list.ListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigateToInitialFragment();
        }
    }

    private void navigateToInitialFragment() {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.flContent, ListFragment.newInstance(), ListFragment.class.getSimpleName())
            .commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
