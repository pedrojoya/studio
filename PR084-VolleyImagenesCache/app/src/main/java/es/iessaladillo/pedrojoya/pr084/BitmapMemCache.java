package es.iessaladillo.pedrojoya.pr084;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

//Clase que gestiona la caché de imágenes en memoria.
public class BitmapMemCache extends LruCache<String, Bitmap> implements
        ImageCache {

    // Constructores.
    public BitmapMemCache() {
        // Se llama al otro constructor con el tamaño en KB, calculado como el
        // máximo posible para la JVM.
        this((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);
    }

    public BitmapMemCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);
    }

    // Retorna el tamaño en KB de una imagen de la caché.
    // Recibe la key del elemento y la imagen.
    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getByteCount() / 1024;
    }

    // Retorna si existe en la caché una imagen con esa clave.
    public boolean contains(String key) {
        return get(key) != null;
    }

    // Retorna la imagen correspondiente a una clave.
    public Bitmap getBitmap(String key) {
        return get(key);
    }

    // Escribe una imagen en la caché.
    // Utiliza la url como clave.
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }

}