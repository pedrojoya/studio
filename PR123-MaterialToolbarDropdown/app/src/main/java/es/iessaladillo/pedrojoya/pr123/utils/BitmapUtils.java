package es.iessaladillo.pedrojoya.pr123.utils;

import android.graphics.ColorMatrix;

public class BitmapUtils {

    private BitmapUtils() {
    }

    public static ColorMatrix getGreyColorMatrix() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        return colorMatrix;
    }

    public static ColorMatrix getSepiaColorMatrix() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrix colorScale = new ColorMatrix();
        colorScale.setScale(1, 1, 0.8f, 1);
        // Convert to grayscale, then apply brown color
        colorMatrix.postConcat(colorScale);
        return colorMatrix;
    }

    public static ColorMatrix getBinaryColorMatrix() {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        float m = 255f;
        float t = -255*128f;
        ColorMatrix threshold = new ColorMatrix(new float[] {
                m, 0, 0, 1, t,
                0, m, 0, 1, t,
                0, 0, m, 1, t,
                0, 0, 0, 1, 0
        });
        // Convert to grayscale, then scale and clamp
        colorMatrix.postConcat(threshold);
        return colorMatrix;
    }

    public static ColorMatrix getInvertedColorMatrix() {
        return new ColorMatrix(new float[] {
                -1,  0,  0,  0, 255,
                0, -1,  0,  0, 255,
                0,  0, -1,  0, 255,
                0,  0,  0,  1,   0
        });
    }

}
