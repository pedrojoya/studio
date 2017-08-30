package es.iessaladillo.pedrojoya.pr018.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BitmapUtils {

    private BitmapUtils() { }

    public static Bitmap toGreen(Bitmap src) {
        return toneEffect(src, 120, 0.1, 0.6, 0.1);
    }

    public static Bitmap toBlue(Bitmap src) {
        return toneEffect(src, 120, 0.1, 0.1, 0.6);
    }

    public static Bitmap toSepia(Bitmap src) {
        return toneEffect(src, 120, 0.6, 0.4, 0.2);
    }

    public static Bitmap toGrey(Bitmap src) {
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
    @SuppressWarnings("SameParameterValue")
    private static Bitmap toneEffect(Bitmap src, int intensidad,
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
