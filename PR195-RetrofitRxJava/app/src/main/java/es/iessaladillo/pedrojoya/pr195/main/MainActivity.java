package es.iessaladillo.pedrojoya.pr195.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.pedrojoya.pr195.R;
import es.iessaladillo.pedrojoya.pr195.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Load initial fragment.
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    MainFragment.newInstance(), MainFragment.class.getSimpleName());
        }
    }

}
