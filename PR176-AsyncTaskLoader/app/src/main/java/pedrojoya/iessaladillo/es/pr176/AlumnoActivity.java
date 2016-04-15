package pedrojoya.iessaladillo.es.pr176;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlumnoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);
        initVistas();
    }

    private void initVistas() {
        (findViewById(R.id.btnAgregar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.addAlumno(DB.getNextAlumno());
                finish();
            }
        });
    }

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, AlumnoActivity.class));
    }

}
