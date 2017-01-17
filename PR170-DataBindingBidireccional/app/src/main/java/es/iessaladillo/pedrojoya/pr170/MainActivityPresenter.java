package es.iessaladillo.pedrojoya.pr170;

import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Random;

import es.iessaladillo.pedrojoya.pr170.databinding.ActivityMainBinding;

public class MainActivityPresenter {

    private final Random mAleatorio = new Random();

    public void btnSaludarOnClick(View v) {
        // Se obtiene el binding y el viewModel.
        ActivityMainBinding binding = DataBindingUtil.findBinding(v);
        MainActivityViewModel viewModel = binding.getViewModel();
        StringBuffer sb = new StringBuffer(v.getContext().getString(R.string.buenos_dias));
        if (viewModel.isEducado()) {
            sb.append(" ");
            sb.append(v.getContext().getString(R.string.tenga_usted));
            if (!viewModel.getTratamiento().isEmpty()) {
                sb.append(" ");
                sb.append(viewModel.getTratamiento());
            }
        }
        sb.append(" ");
        sb.append(viewModel.getNombre());
        // Se muestra el mensaje
        Snackbar.make(v, sb.toString(), Snackbar.LENGTH_LONG).show();
    }

    // Recibe directamente el viewModel para no tener que obtenerlo (sólo con vinculación de
    // listener).
    public boolean btnSaludarOnLongClick(View v, MainActivityViewModel viewModel) {
        StringBuffer sb = new StringBuffer(v.getContext().getString(R.string.buenos_dias));
        sb.append(" ");
        sb.append(v.getContext().getString(R.string.tenga_usted));
        if (!viewModel.getTratamiento().isEmpty()) {
            sb.append(" ");
            sb.append(viewModel.getTratamiento());
        }
        sb.append(" ");
        sb.append(viewModel.getNombre());
        // Se muestra el mensaje
        Snackbar.make(v, sb.toString(), Snackbar.LENGTH_LONG).show();
        return true;
    }

    public void imgImagenOnClick(View v) {
        Picasso.with(v.getContext()).load(
                v.getContext().getString(R.string.lorempixel) + (mAleatorio.nextInt(9) + 1)).into(
                (ImageView) v);
    }


}
