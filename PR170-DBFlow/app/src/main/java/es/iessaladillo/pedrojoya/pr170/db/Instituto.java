package es.iessaladillo.pedrojoya.pr170.db;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = Instituto.BD_NOMBRE, version = Instituto.BD_VERSION, foreignKeysSupported=true)
public class Instituto {

    public static final String BD_NOMBRE = "instituto";
    public static final int BD_VERSION = 1;

}