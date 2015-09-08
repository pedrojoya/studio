package es.iessaladillo.pedrojoya.pr151;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr151.databinding.ActivityCustomBinding;

public class CustomActivity extends AppCompatActivity {

    private static final String STATE_SALUDO = "saludo";

    private CustomSaludo mSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCustomBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_custom);
        if (savedInstanceState == null) {
            mSaludo = new CustomSaludo("Baldomero", true);
        } else {
            mSaludo = savedInstanceState.getParcelable(STATE_SALUDO);
        }
        binding.setCustom(mSaludo);
        // Se establecen los listeners.
        setListeners(binding);
    }

    // Establece los listeners de las vistas.
    private void setListeners(ActivityCustomBinding binding) {
        binding.btnSaludar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSaludo();
            }
        });
    }

    // Muestra el saludo.
    private void showSaludo() {
        String mensaje = getString(R.string.buenos_dias);
        if (mSaludo.educado.get()) {
            mensaje = mensaje + " " + getString(R.string.tenga_usted);
        }
        mensaje += " " + mSaludo.nombre.get();
        // Se muestra el mensaje en un Toast.
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_SALUDO, mSaludo);
        super.onSaveInstanceState(outState);
    }

}
