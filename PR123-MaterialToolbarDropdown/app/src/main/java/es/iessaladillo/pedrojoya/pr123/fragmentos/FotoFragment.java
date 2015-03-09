package es.iessaladillo.pedrojoya.pr123.fragmentos;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import es.iessaladillo.pedrojoya.pr123.R;
import es.iessaladillo.pedrojoya.pr123.utils.ColorBitmap;

public class FotoFragment extends Fragment {

    // Constantes.
    private static final String EXTRA_FOTO_RESID = "fotoResId";

    private enum Efecto {
        ORIGINAL, GRISES, SEPIA, AZULADO, VERDOSO
    };

    // Vistas.
    private ImageView imgFoto;

    // Variables
    private int mFotoResId;
    private Efecto mEfecto;
    private Bitmap mFotoBitmapOriginal;

    // Retorna un objeto fragmento bien construido. Recibe el resId de la foto.
    public static FotoFragment newInstance(int fotoResId) {
        // Se crea el objeto fragmento y se le añaden sus argumentos.
        FotoFragment frg = new FotoFragment();
        Bundle argumentos = new Bundle();
        argumentos.putInt(EXTRA_FOTO_RESID, fotoResId);
        frg.setArguments(argumentos);
        return frg;
    }

    // Al crear el fragmento.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // El fragmento se mantendrá en memoria entre cambios de configuración.
        setRetainInstance(true);
        // Se indica que el fragmento proporciona ítems al menú de opciones.
        setHasOptionsMenu(true);
        mEfecto = Efecto.ORIGINAL;
        super.onCreate(savedInstanceState);
    }

    // Retorna la vista que debe mostrar el fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_foto, container, false);
    }

    // Al terminar de crearse la actividad con todos sus fragmentos.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Se obtienen el argumento correspondiente al redId de la foto.
        Bundle argumentos = getArguments();
        if (argumentos != null && argumentos.containsKey(EXTRA_FOTO_RESID)) {
            mFotoResId = argumentos.getInt(EXTRA_FOTO_RESID);
        } else {
            // Si no se proporciona el argumento se establece una foto por
            // defecto.
            mFotoResId = R.drawable.bench;
        }
        // Se obtiene el bitmap de la foto.
        mFotoBitmapOriginal = BitmapFactory.decodeResource(getResources(),
                mFotoResId);
        // Se obtienen e inicializan las vistas.
        getVistas();
        super.onActivityCreated(savedInstanceState);
    }

    // Cuando se va a crear el menú de opciones.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se agregan al menú los ítems correspondientes del fragmento.
        inflater.inflate(R.menu.fragment_foto, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // Cuando se va a pintar el menú de opciones.
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // Se habilitan todos los ítems del menú.
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setEnabled(true);
        }
        // Se deshabilita el ítem correspondiente al efecto actual.
        switch (mEfecto) {
        case ORIGINAL:
            menu.findItem(R.id.mnuOriginal).setEnabled(false);
            break;
        case GRISES:
            menu.findItem(R.id.mnuGrises).setEnabled(false);
            break;
        case SEPIA:
            menu.findItem(R.id.mnuSepia).setEnabled(false);
            break;
        case AZULADO:
            menu.findItem(R.id.mnuAzulado).setEnabled(false);
            break;
        case VERDOSO:
            menu.findItem(R.id.mnuVerdoso).setEnabled(false);
            break;
        }
        super.onPrepareOptionsMenu(menu);
    }

    // Cuando se pulsa un ítem del menú de opciones.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado realizo la acción deseada.
        switch (item.getItemId()) {
        case R.id.mnuOriginal:
            aplicarEfecto(Efecto.ORIGINAL);
            break;
        case R.id.mnuGrises:
            aplicarEfecto(Efecto.GRISES);
            break;
        case R.id.mnuSepia:
            aplicarEfecto(Efecto.SEPIA);
            break;
        case R.id.mnuAzulado:
            aplicarEfecto(Efecto.AZULADO);
            break;
        case R.id.mnuVerdoso:
            aplicarEfecto(Efecto.VERDOSO);
            break;
        default:
            // Se propaga el evento porque no ha podido ser resuelto.
            return super.onOptionsItemSelected(item);
        }
        // Si se llega aquí es que se ha procesado el evento.
        return true;
    }

    // Obtiene e inicializa las vistas.
    private void getVistas() {
        imgFoto = (ImageView) (getView().findViewById(R.id.imgFoto));
        // Se aplica el efecto actual.
        aplicarEfecto(mEfecto);
    }

    // Aplica el efecto indicado a la foto.
    private void aplicarEfecto(Efecto efecto) {
        // Se establece la imagen.
        switch (efecto) {
        case ORIGINAL:
            imgFoto.setImageBitmap(mFotoBitmapOriginal);
            break;
        case GRISES:
            imgFoto.setImageBitmap(ColorBitmap
                    .aEscalaGrises(mFotoBitmapOriginal));
            break;
        case SEPIA:
            imgFoto.setImageBitmap(ColorBitmap.aSepia(mFotoBitmapOriginal));
            break;
        case AZULADO:
            imgFoto.setImageBitmap(ColorBitmap.aAzulado(mFotoBitmapOriginal));
            break;
        case VERDOSO:
            imgFoto.setImageBitmap(ColorBitmap.aVerdoso(mFotoBitmapOriginal));
            break;
        }
        // Se almacena el efecto aplicado.
        mEfecto = efecto;
        // Se invalida el menú para que se ejecute onPrepareOptionsMenu() y así
        // se deshabilite el ítem correspondiente al efecto actual.
        getActivity().invalidateOptionsMenu();
    }

}
