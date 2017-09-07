package es.iessaladillo.pedrojoya.pr165.data.utils;

import android.graphics.Paint;
import android.widget.TextView;

@SuppressWarnings({"unused", "WeakerAccess"})
public class TextViewUtils {

    private TextViewUtils() {
    }

    public static void setStrikethrough(TextView textView, boolean strikethrough) {
        if (strikethrough) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

}
