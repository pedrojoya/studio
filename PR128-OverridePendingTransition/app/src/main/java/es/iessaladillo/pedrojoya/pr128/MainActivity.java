package es.iessaladillo.pedrojoya.pr128;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    private static final int ANIM_PULL_RIGHT_INDEX = 1;

    private Spinner spnSalida;
    private Spinner spnEntrada;

    private final int[] mResIdsAnimacionesSalida =
            {R.anim.push_right, R.anim.push_left, R.anim.push_up,
                    R.anim.push_down, R.anim.scale_down_disappear};
    private final int[] mResIdsAnimacionesEntrada =
            {R.anim.pull_left, R.anim.pull_right, R.anim.pull_down,
                    R.anim.pull_up, R.anim.scale_up_appear};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnSalida = (Spinner) findViewById(R.id.spnSalida);
        spnEntrada = (Spinner) findViewById(R.id.spnEntrada);
        if (spnEntrada != null) {
            // Se selecciona inicialmente la animación de entrada que concuerda
            // con la animación de salida seleccionada por defecto.
            spnEntrada.setSelection(ANIM_PULL_RIGHT_INDEX);
        }
        Button btnIr = (Button) findViewById(R.id.btnIr);
        if (btnIr != null) {
            btnIr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Se lanza la otra actividad (se le envían las animaciones que
                    // deben llevarse a cabo en el retorno.
                    OtraActivity.start(MainActivity.this,
                            mResIdsAnimacionesSalida[spnEntrada.getSelectedItemPosition()],
                            mResIdsAnimacionesEntrada[spnSalida.getSelectedItemPosition()]);
                    // Se realiza la animación (entrada, salida).
                    overridePendingTransition(
                            mResIdsAnimacionesEntrada[spnEntrada.getSelectedItemPosition()],
                            mResIdsAnimacionesSalida[spnSalida.getSelectedItemPosition()]);
                }
            });
        }
    }

}
