package es.iessaladillo.pedrojoya.pr151;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import es.iessaladillo.pedrojoya.pr151.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_SALUDO = "saludo";

    private Saludo mSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            mSaludo = new Saludo("", true);
        } else {
            mSaludo = savedInstanceState.getParcelable(STATE_SALUDO);
        }
        binding.setSaludo(mSaludo);
        // Se establecen los listeners.
        setListeners(binding);
    }

    // Establece los listeners de las vistas.
    private void setListeners(ActivityMainBinding binding) {
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
        if (mSaludo.isEducado()) {
            mensaje = mensaje + " " + getString(R.string.tenga_usted);
        }
        mensaje += " " + mSaludo.getNombre();
        // Se muestra el mensaje en un Toast.
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuCustom) {
            startActivity(new Intent(this, CustomActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_SALUDO, mSaludo);
        super.onSaveInstanceState(outState);
    }

}
