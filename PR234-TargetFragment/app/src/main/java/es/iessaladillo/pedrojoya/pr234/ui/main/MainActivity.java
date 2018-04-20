package es.iessaladillo.pedrojoya.pr234.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr234.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT) == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, MainFragment
                    .newInstance(), TAG_MAIN_FRAGMENT).commit();
        }
    }

}