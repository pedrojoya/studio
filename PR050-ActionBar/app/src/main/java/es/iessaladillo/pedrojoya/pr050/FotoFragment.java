package es.iessaladillo.pedrojoya.pr050;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FotoFragment extends Fragment {

    // Interfaz pública de comunicación con la actividad.
    public interface Listener {
        // Cuando se solicita Info sobre la foto.
        public void onInfo(int fotoResId);
    }

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
    private Listener listener;
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

    // Cuando el fragmento se enlaza con la actividad.
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Establece la actividad como objeto listener.
            listener = (Listener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz.
            throw new ClassCastException(
                    activity.toString()
                            + activity
                                    .getString(R.string._debe_implementar_fotofragment_listener));
        }
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
        initVistas();
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
        case R.id.mnuInfo:
            // Se informa a la actividad de que se solicita Info sobre la foto.
            listener.onInfo(mFotoResId);
            break;
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
    private void initVistas() {
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
            imgFoto.setImageBitmap(aEscalaGrises(mFotoBitmapOriginal));
            break;
        case SEPIA:
            imgFoto.setImageBitmap(aSepia(mFotoBitmapOriginal));
            break;
        case AZULADO:
            imgFoto.setImageBitmap(aAzulado(mFotoBitmapOriginal));
            break;
        case VERDOSO:
            imgFoto.setImageBitmap(aVerdoso(mFotoBitmapOriginal));
            break;
        }
        // Se almacena el efecto aplicado.
        mEfecto = efecto;
        // Se invalida el menú para que se ejecute onPrepareOptionsMenu() y así
        // se deshabilite el ítem correspondiente al efecto actual.
        getActivity().invalidateOptionsMenu();
    }

    // Retorna el bitmap recibido en tono verdoso.
    private Bitmap aVerdoso(Bitmap src) {
        return efectoTono(src, 120, 0.1, 0.6, 0.1);
    }

    // Retorna el bitmap recibido en tono azulado.
    private Bitmap aAzulado(Bitmap src) {
        return efectoTono(src, 120, 0.1, 0.1, 0.6);
    }

    // Retorna el bitmap recibido en tono sepia.
    private Bitmap aSepia(Bitmap src) {
        return efectoTono(src, 120, 0.6, 0.4, 0.2);
    }

    // Retorna el bitmap recibido en escala de grises.
    private Bitmap aEscalaGrises(Bitmap src) {
        // Constantes de factores.
        final double FACTOR_ROJO = 0.299;
        final double FACTOR_VERDE = 0.587;
        final double FACTOR_AZUL = 0.114;
        // Se obtiene la anchura y altura de la imagen de origen.
        int anchura = src.getWidth();
        int altura = src.getHeight();
        // Se crea el bitmap de salida a partir del de origen.
        Bitmap bmSalida = Bitmap.createBitmap(anchura, altura, src.getConfig());
        // Se procesa la imagen píxel a píxel.
        int pixel, transparencia, rojo, verde, azul;
        for (int x = 0; x < anchura; ++x) {
            for (int y = 0; y < altura; ++y) {
                // Se obtiene el pixel y su info de transparencia y color.
                pixel = src.getPixel(x, y);
                transparencia = Color.alpha(pixel);
                rojo = Color.red(pixel);
                verde = Color.green(pixel);
                azul = Color.blue(pixel);
                // Se realiza la conversión a un único color proporcional
                // (gris).
                rojo = verde = azul = (int) (FACTOR_ROJO * rojo + FACTOR_VERDE
                        * verde + FACTOR_AZUL * azul);
                // Se escribe el nuevo pixel en el bitmap de salida.
                bmSalida.setPixel(x, y,
                        Color.argb(transparencia, rojo, verde, azul));
            }
        }
        // Se retorna el bitmap de salida.
        return bmSalida;
    }

    // Retorna el bitmap recibo aplicándole el cambio de tono dado por la
    // intensidad recibida y el factor de cada color.
    public static Bitmap efectoTono(Bitmap src, int intensidad,
            double factorIntesidadRojo, double factorIntensidadVerde,
            double factorIntensidadAzul) {
        // Constantes de factores.
        final double FACTOR_ROJO = 0.3;
        final double FACTOR_VERDE = 0.59;
        final double FACTOR_AZUL = 0.11;
        // Se obtiene la anchura y altura de la imagen de origen.
        int anchura = src.getWidth();
        int altura = src.getHeight();
        // Se crea el bitmap de salida a partir del de origen.
        Bitmap bmSalida = Bitmap.createBitmap(anchura, altura, src.getConfig());
        // Se procesa la imagen píxel a píxel.
        int pixel, transparencia, rojo, verde, azul;
        for (int x = 0; x < anchura; ++x) {
            for (int y = 0; y < altura; ++y) {
                // Se obtiene el pixel y su info de transparencia y color.
                pixel = src.getPixel(x, y);
                transparencia = Color.alpha(pixel);
                rojo = Color.red(pixel);
                verde = Color.green(pixel);
                azul = Color.blue(pixel);
                // Se realiza la conversión a un único color proporcional
                // (gris).
                rojo = verde = azul = (int) (FACTOR_ROJO * rojo + FACTOR_VERDE
                        * verde + FACTOR_AZUL * azul);
                // Se aplica el factor de intensidad a cada canal.
                rojo += (intensidad * factorIntesidadRojo);
                if (rojo > 255) {
                    rojo = 255;
                }
                verde += (intensidad * factorIntensidadVerde);
                if (verde > 255) {
                    verde = 255;
                }
                azul += (intensidad * factorIntensidadAzul);
                if (azul > 255) {
                    azul = 255;
                }
                // Se escribe el nuevo pixel en el bitmap de salida.
                bmSalida.setPixel(x, y,
                        Color.argb(transparencia, rojo, verde, azul));
            }
        }
        // Se retorna el bitmap de salida.
        return bmSalida;
    }

}
