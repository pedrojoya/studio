package pedrojoya.iessaladillo.es.pr227.base;

import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;

@SuppressWarnings({"WeakerAccess", "unused"})
public class IconicDrawable {

    @ColorInt
    private final int backgroundColor;
    private final Drawable icon;

    public IconicDrawable(int backgroundColor, Drawable icon) {
        this.backgroundColor = backgroundColor;
        this.icon = icon;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public Drawable getIcon() {
        return icon;
    }

}
