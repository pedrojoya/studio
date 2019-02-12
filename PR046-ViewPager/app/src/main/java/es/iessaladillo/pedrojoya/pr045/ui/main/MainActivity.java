package es.iessaladillo.pedrojoya.pr045.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import es.iessaladillo.pedrojoya.pr045.R;
import es.iessaladillo.pedrojoya.pr045.ui.main.v2.MainFragmentV2;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loadInitialFragment();
        }
    }

    private void loadInitialFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
            MainFragmentV2.newInstance(), MainFragmentV2.class.getSimpleName()).commit();
    }

}