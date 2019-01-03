package es.iessaladillo.pedrojoya.pr138.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import es.iessaladillo.pedrojoya.pr138.R;


@SuppressWarnings("WeakerAccess")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            navigateToStartFragment();
        }
    }

    private void navigateToStartFragment() {
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.flContent, MainFragment.newInstance(), MainFragment.class.getSimpleName())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit();
    }

}
