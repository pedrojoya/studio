package es.iessaladillo.pedrojoya.pr084;

// Clase contrato con las claves de Instagram.
public class Instagram {

    // Constantes.
    public static final String CLIENT_ID = "c432d0e158dd46f7873950a19a582102";
    public static final String BASE_URL = "https://api.instagram.com/v1";
    public static final String ARRAY_DATOS_KEY = "data";
    public static final String TIPO_ELEMENTO_KEY = "type";
    public static final String TIPO_ELEMENTO_IMAGEN = "image";
    public static final String USUARIO_KEY = "user";
    public static final String NOMBRE_USUARIO_KEY = "username";
    public static final String IMAGEN_KEY = "images";
    public static final String RESOLUCION_MINIATURA_KEY = "thumbnail";
    public static final String RESOLUCION_ESTANDAR_KEY = "standard_resolution";
    public static final String URL_KEY = "url";
    public static final String PAGINACION_KEY = "pagination";
    public static final String SIGUIENTE_PETICION_KEY = "next_url";

    // Retorna la url de acceso a las imágenes más recientes para un tag.
    public static String getRecentMediaURL(String tag) {
        return BASE_URL + "/tags/" + tag + "/media/recent?client_id="
                + CLIENT_ID;
    }

    // Constructor privado para que NO pueda instanciarse.
    private Instagram() {
    }

}
