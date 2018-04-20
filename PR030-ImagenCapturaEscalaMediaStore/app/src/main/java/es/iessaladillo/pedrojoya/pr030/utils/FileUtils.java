package es.iessaladillo.pedrojoya.pr030.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import es.iessaladillo.pedrojoya.pr030.R;

public class FileUtils {

    private FileUtils() {
    }

    public static File createPictureFile(Context context, String filename, boolean external, boolean
            publicAccess) {
        // Get and create folder if needed.
        File folder;
        if (external && Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (publicAccess) {
                // OJO, USANDO ALMACENAMIENTO EXTERNO PÚBLICO HABITUAL NO FUNCIONA PORQUE EL
                // FILEPROVIDER NO ES CAPAZ DE ACCEDER CUANDO HAY VARIOS ALMACENAMIENTOS
                // SECUNDARIOS.
                // folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                // Public external storage.
                folder = context.getExternalMediaDirs()[0];
            } else {
                // Specific external storage.
                folder = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            }
        } else {
            // Internal storage.
            folder = context.getFilesDir();
        }
        if (folder != null && !folder.exists()) {
            if (!folder.mkdirs()) {
                Log.d(context.getString(R.string.app_name), "Error creating directory");
                return null;
            }
        }
        // Create and return file.
        File file = null;
        if (folder != null) {
            file = new File(folder.getPath(), filename);
            Log.d(context.getString(R.string.app_name), file.getAbsolutePath());
        }
        return file;
    }

}
