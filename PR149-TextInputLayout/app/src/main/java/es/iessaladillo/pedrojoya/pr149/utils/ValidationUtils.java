package es.iessaladillo.pedrojoya.pr149.utils;

import android.support.annotation.NonNull;
import android.util.Patterns;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class ValidationUtils {

    private ValidationUtils() {
    }

    @SuppressWarnings("RedundantIfStatement")
    public static boolean isValidSpanishPhoneNumber(@NonNull String phone) {
        if (phone.length() > 0 && phone.length() < 9) {
            return false;
        }
        if (!phone.startsWith("6") && !phone.startsWith("7") && !phone.startsWith("8")
                && !phone.startsWith("9")) {
            return false;
        }
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isValidEmail(@NonNull String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}
