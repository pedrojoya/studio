package es.iessaladillo.pedrojoya.pr236.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

    private static final int BUFFER_SIZE = 1000;

    private FileUtils() {
    }

    public static void copyFile(InputStream inputStream, File outputFile) throws IOException {
        BufferedInputStream reader;
        reader = new BufferedInputStream(inputStream);
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        BufferedOutputStream writer = new BufferedOutputStream(outputStream);
        byte[] array = new byte[BUFFER_SIZE];
        int read = reader.read(array);
        while (read > 0) {
            writer.write(array, 0, read);
            read = reader.read(array);
        }
        writer.close();
        reader.close();
    }

}
