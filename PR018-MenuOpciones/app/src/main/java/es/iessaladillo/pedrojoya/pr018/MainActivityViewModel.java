package es.iessaladillo.pedrojoya.pr018;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import es.iessaladillo.pedrojoya.pr018.utils.BitmapUtils;

@SuppressWarnings("WeakerAccess")
public class MainActivityViewModel extends AndroidViewModel {

    private final Bitmap originalBitmap;
    private Bitmap greyBitmap;
    private Bitmap sepiaBitmap;
    private Bitmap blueBitmap;
    private Bitmap greenBitmap;

    public MainActivityViewModel(Application application) {
        super(application);
        originalBitmap = BitmapFactory.decodeResource(application.getResources(), R.drawable.bench);
    }

    public Bitmap getOriginalBitmap() {
        return originalBitmap;
    }

    public Bitmap getGreyBitmap() {
        if (greyBitmap == null) {
            greyBitmap = BitmapUtils.toGrey(originalBitmap);
        }
        return greyBitmap;
    }

    public Bitmap getSepiaBitmap() {
        if (sepiaBitmap == null) {
            sepiaBitmap = BitmapUtils.toSepia(originalBitmap);
        }
        return sepiaBitmap;
    }

    public Bitmap getBlueBitmap() {
        if (blueBitmap == null) {
            blueBitmap = BitmapUtils.toBlue(originalBitmap);
        }
        return blueBitmap;
    }

    public Bitmap getGreenBitmap() {
        if (greenBitmap == null) {
            greenBitmap = BitmapUtils.toGreen(originalBitmap);
        }
        return greenBitmap;
    }

}
