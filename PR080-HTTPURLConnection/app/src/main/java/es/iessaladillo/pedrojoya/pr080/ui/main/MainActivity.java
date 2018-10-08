package es.iessaladillo.pedrojoya.pr080.ui.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr080.R;
import es.iessaladillo.pedrojoya.pr080.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    MainFragment.newInstance(), TAG_MAIN_FRAGMENT);
        }
    }

}
