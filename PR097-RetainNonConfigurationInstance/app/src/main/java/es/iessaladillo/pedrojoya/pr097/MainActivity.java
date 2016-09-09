package es.iessaladillo.pedrojoya.pr097;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVistas();
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        Button btnSave = (Button) findViewById(R.id.btnSave);
        if (btnSave != null) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SaveActivity.start(MainActivity.this);
                }
            });
        }
        Button btnRetain = (Button) findViewById(R.id.btnRetain);
        if (btnRetain != null) {
            btnRetain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RetainActivity.start(MainActivity.this);
                }
            });
        }
        Button btnIcepick = (Button) findViewById(R.id.btnIcepick);
        if (btnIcepick != null) {
            btnIcepick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IcepickActivity.start(MainActivity.this);
                }
            });
        }
    }

}
