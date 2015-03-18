package es.iessaladillo.pedrojoya.pr018;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

    // Constantes.
    private static final String ESTADO_EFECTO = "mEfecto";
    private static final String ESTADO_FOTO = "mFotoBitmap";

    // Variables
    private Bitmap mFotoBitmapOriginal;

    // Vistas.
    private ImageView imgFoto;
    private int mEfecto = R.id.mnuOriginal;

    // Cuando se crea la actividad.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Se obtienen e inicializan las vistas.
        initVistas();
        // Se obtiene el bitmap de la foto.
        mFotoBitmapOriginal = BitmapFactory.decodeResource(getResources(),
                R.drawable.bench);
        // Se recupera el estado anterior (efecto y foto resultante).
        if (savedInstanceState != null) {
            mEfecto = savedInstanceState.getInt(ESTADO_EFECTO);
            imgFoto.setImageBitmap((Bitmap) savedInstanceState
                    .getParcelable(ESTADO_FOTO));
        }
    }

    // Obtiene e inicializa las vistas.
    private void initVistas() {
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Se almacena en el bundle el efecto aplicado y la foto resultante (la
        // clase Bitmap implementa la interfaz Parcelable).
        outState.putInt(ESTADO_EFECTO, mEfecto);
        outState.putParcelable(ESTADO_FOTO,
                ((BitmapDrawable) imgFoto.getDrawable()).getBitmap());
        super.onSaveInstanceState(outState);
    }

    // Cuando se crea el menú de opciones.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Se inflo el menú a partir de la especificación XML.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        // Se da la posibilidad al sistema de agregar ítems al menú.
        return super.onCreateOptionsMenu(menu);
    }

    // Antes de mostrar el menú.
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Se habilitan todos los ítems del menú.
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setEnabled(true);
        }
        // Se deshabilita el ítem correspondiente al efecto actual.
        if (mEfecto != 0) {
            menu.findItem(mEfecto).setEnabled(false);
        }
        // Se retorna lo que retorne la llamada al padre.
        return super.onPrepareOptionsMenu(menu);
    }

    // Cuando se pulsa un elemento del menú.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Dependiendo del item pulsado realizo la acción deseada.
        switch (item.getItemId()) {
        case R.id.mnuOriginal:
            imgFoto.setImageBitmap(mFotoBitmapOriginal);
            break;
        case R.id.mnuGrises:
            imgFoto.setImageBitmap(aEscalaGrises(mFotoBitmapOriginal));
            break;
        case R.id.mnuSepia:
            imgFoto.setImageBitmap(aSepia(mFotoBitmapOriginal));
            break;
        case R.id.mnuAzulado:
            imgFoto.setImageBitmap(aAzulado(mFotoBitmapOriginal));
            break;
        case R.id.mnuVerdoso:
            imgFoto.setImageBitmap(aVerdoso(mFotoBitmapOriginal));
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        // Se almacena el id del menú del efecto actual.
        mEfecto = item.getItemId();
        // Se retorna que ya se ha gestionado el evento.
        return true;
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
    private static Bitmap efectoTono(Bitmap src, int intensidad,
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
