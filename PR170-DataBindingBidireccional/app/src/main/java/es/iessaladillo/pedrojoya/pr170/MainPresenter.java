package es.iessaladillo.pedrojoya.pr170;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Random;

public class MainPresenter {

    private final Random mAleatorio = new Random();

    @SuppressWarnings("SameReturnValue")
    public boolean saludar(View v, MainViewModel viewModel) {
        // Las líneas siguientes no son necesarias usando lambda expresions
        // en el layout.
        //ActivityMainBinding binding = DataBindingUtil.findBinding(v);
        //MainViewModel vm = binding.getViewModel();
        String mensaje = v.getContext().getString(R.string.buenos_dias);
        if (viewModel.isEducado()) {
            mensaje = mensaje + " " + v.getContext().getString(R.string.tenga_usted);
            if (!viewModel.getTratamiento().isEmpty()) {
                mensaje = mensaje + " " + viewModel.getTratamiento();
            }
        }
        mensaje += " " + viewModel.getNombre();
        // Se muestra el mensaje
        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).show();
        return true;
    }

    public void loadImage(View v) {
        Picasso.with(v.getContext()).load(v.getContext().getString(R.string.lorempixel) + (mAleatorio.nextInt(9) + 1)).into((ImageView) v);
    }


}
