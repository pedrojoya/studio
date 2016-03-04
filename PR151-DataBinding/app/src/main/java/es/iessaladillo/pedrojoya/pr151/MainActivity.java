package es.iessaladillo.pedrojoya.pr151;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr151.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_SALUDO = "saludo";

    private ActivityMainVM mViewModel;
    private ActivityMainBinding mBinding;
    private Random mAleatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            mViewModel = new ActivityMainVM();
        } else {
            mViewModel = savedInstanceState.getParcelable(STATE_SALUDO);
        }
        mBinding.setViewModel(mViewModel);
        // Se establecen los listeners.
        setListeners();
        mAleatorio = new Random();
    }

    // Establece los listeners de las vistas.
    private void setListeners() {
        mBinding.btnSaludar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSaludo();
            }
        });
        mBinding.imgImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });
    }

    private void loadImage() {
        Picasso.with(this).load(getString(R.string.lorempixel) + (mAleatorio.nextInt(9) + 1)).into(mBinding.imgImagen);
    }

    // Muestra el saludo.
    private void showSaludo() {
        String mensaje = getString(R.string.buenos_dias);
        if (mViewModel.isEducado()) {
            mensaje = mensaje + " " + getString(R.string.tenga_usted);
        }
        mensaje += " " + mViewModel.getNombre();
        // Se muestra el mensaje
        Snackbar.make(mBinding.imgImagen, mensaje, Snackbar.LENGTH_LONG).show();
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
        outState.putParcelable(STATE_SALUDO, mViewModel);
        super.onSaveInstanceState(outState);
    }

}
