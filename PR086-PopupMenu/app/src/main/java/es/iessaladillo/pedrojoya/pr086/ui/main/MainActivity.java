package es.iessaladillo.pedrojoya.pr086.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.pedrojoya.pr086.R;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigateToInitialFragment();
        }
    }

    private void navigateToInitialFragment() {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, MainFragment.newInstance(), MainFragment.class.getSimpleName())
            .commit();
    }

}
