package pedrojoya.iessaladillo.es.pr248.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import pedrojoya.iessaladillo.es.pr248.R;
import pedrojoya.iessaladillo.es.pr248.utils.FragmentUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.flContent,
                    MainFragment.newInstance(), MainFragment.class.getSimpleName());
        }
    }

}
