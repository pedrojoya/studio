package es.iessaladillo.pedrojoya.pr086.utils;

import android.util.Log;
import android.widget.PopupMenu;

import java.lang.reflect.Field;

public class PopupMenuUtils {

    private PopupMenuUtils() {
    }

    public static void enableIcons(PopupMenu popupMenu) {
        Object menuHelper;
        Class[] argTypes;
        try {
            @SuppressWarnings("JavaReflectionMemberAccess")
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            Log.e("PopupMenu", "Error enabling icons in popup menu");
        }
    }

}
