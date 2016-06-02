package es.iessaladillo.pedrojoya.pr170;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.iessaladillo.pedrojoya.pr170.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_SALUDO = "saludo";

    private MainViewModel mViewModel;
    // private Random mAleatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (savedInstanceState == null) {
            mViewModel = new MainViewModel();
        } else {
            mViewModel = savedInstanceState.getParcelable(STATE_SALUDO);
        }
        mBinding.setViewModel(mViewModel);
        mBinding.setPresenter(new MainPresenter());
        // Se establecen los listeners.
        // setListeners();
        // mAleatorio = new Random();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_SALUDO, mViewModel);
        super.onSaveInstanceState(outState);
    }

/*
    // Establece los listeners de las vistas.
    private void setListeners() {

        mBinding.imgImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });
        mBinding.txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBinding.btnSaludar.setEnabled(!TextUtils.isEmpty(s.toString()));
            }
        });

        cargarCursos();

    }


    private void cargarCursos() {
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.tratamientos, android.R.layout.simple_list_item_1);
        mBinding.txtTratamiento.setAdapter(adaptador);
        mBinding.txtTratamiento.setOnItemSelectedListener(new ClickToSelectEditText.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelectedListener(String item, int selectedIndex) {
                mBinding.txtTratamiento.setText(item);
            }
        });
    }

    private void loadImage() {
        Picasso.with(this).load(getString(R.string.lorempixel) + (mAleatorio.nextInt(9) + 1)).into(mBinding.imgImagen);
    }


    // Muestra el saludo.
    private void showSaludo() {
        String mensaje = getString(R.string.buenos_dias);
        if (mViewModel.educado.get()) {
            mensaje = mensaje + " " + getString(R.string.tenga_usted);
            if (!mViewModel.tratamiento.isEmpty()) {
                mensaje = mensaje + " " + mViewModel.tratamiento.get();
            }
        }
        mensaje += " " + mViewModel.nombre.get();
        // Se muestra el mensaje
        Snackbar.make(mBinding.imgImagen, mensaje, Snackbar.LENGTH_LONG).show();
    }
*/

}
