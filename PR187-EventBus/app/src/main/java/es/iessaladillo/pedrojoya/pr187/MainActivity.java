package es.iessaladillo.pedrojoya.pr187;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CommitTransaction")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentById(R.id.flContent) == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.flContent, new MainFragment()).commitNow();
            Log.d("Mia", "Fragmento cargado");
        }
    }

}
