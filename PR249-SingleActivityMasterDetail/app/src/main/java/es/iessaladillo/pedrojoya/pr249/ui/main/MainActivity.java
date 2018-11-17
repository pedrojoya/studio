package es.iessaladillo.pedrojoya.pr249.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr249.R;
import es.iessaladillo.pedrojoya.pr249.ui.detail.DetailFragment;
import es.iessaladillo.pedrojoya.pr249.ui.list.ListFragment;
import es.iessaladillo.pedrojoya.pr249.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity implements ListFragment.onItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // We load initial fragment
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    ListFragment.newInstance(), ListFragment.class.getSimpleName());
        }
    }

    @Override
    public void onItemSelected(@NonNull String item) {
        FragmentUtils.replaceFragmentAddToBackstack(getSupportFragmentManager(),
                R.id.flContent, DetailFragment.newInstance(item),
                DetailFragment.class.getSimpleName(), DetailFragment.class.getSimpleName(),
                FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
