package es.iessaladillo.pedrojoya.pr186;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Si queremos que este modo se aplique en todas las actividades de la
    // aplicación, se recomienda situarlo en la clase que extienda de
    // Application.
    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imgImagen = (ImageView) findViewById(R.id.imgImagen);
        imgImagen.setOnClickListener(view -> cambiarModo());
    }

    private void cambiarModo() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                getDelegate().setLocalNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
            case Configuration.UI_MODE_NIGHT_YES:
                getDelegate().setLocalNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
        recreate();
    }

}
