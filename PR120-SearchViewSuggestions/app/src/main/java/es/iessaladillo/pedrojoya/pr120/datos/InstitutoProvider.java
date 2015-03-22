package es.iessaladillo.pedrojoya.pr120.datos;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class InstitutoProvider extends ContentProvider {

    // Constantes generales.
    // Autoridad. Debe ser similar al valor de android:authority de la etiqueta
    // <provider> en el manifiesto.
    private static final String AUTHORITY = "es.iessaladillo.pedrojoya.pr120.provider";
    private static final Uri CONTENT_URI_BASE = Uri.parse("content://"
            + AUTHORITY); // Uri base de acceso al proveedor.

    // Constantes para la entidad Alumnos.
    private static final String BASE_PATH_ALUMNOS = "alumnos"; // Segmento path.
    public static final Uri CONTENT_URI_ALUMNOS = Uri.withAppendedPath(
            CONTENT_URI_BASE, BASE_PATH_ALUMNOS); // Uri pública de acceso a
    // alumnos.
    private static final String MIME_TYPE_ALUMNOS = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/vnd.es.iessaladillo.instituto.alumnos"; // Tipo MIME alumnos.
    private static final String MIME_ITEM_TYPE_ALUMNOS = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/vnd.es.iessaladillo.instituto.alumno"; // Tipo MIME alumno.

    // Constantes para las sugerencias.


    // Constantes para tipos de Uris (deben tener todos un valor diferente).
    private static final int URI_TYPE_ALUMNOS_LIST = 10; // Tipo para alumnos.
    private static final int URI_TYPE_ALUMNOS_ID = 20; // Tipo para alumno.
    private static final int URI_TYPE_SUGERENCIAS = 30; // Tipo para alumno.


    // Se crea el validador de Uris, al que se le añaden todas los tipos de uris
    // considerados válidos.
    private static final UriMatcher validadorURIs = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        validadorURIs.addURI(AUTHORITY, BASE_PATH_ALUMNOS,
                URI_TYPE_ALUMNOS_LIST);
        validadorURIs.addURI(AUTHORITY, BASE_PATH_ALUMNOS + "/#",
                URI_TYPE_ALUMNOS_ID);
        // Para las sugerencias.
        validadorURIs.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY, URI_TYPE_SUGERENCIAS);
        validadorURIs.addURI(AUTHORITY, SearchManager.SUGGEST_URI_PATH_QUERY + "/*", URI_TYPE_SUGERENCIAS);
    }

    // Retorna el tipo de uri recibida.
    @Override
    public String getType(Uri uri) {
        // Dependiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
            case URI_TYPE_ALUMNOS_LIST:
                return MIME_TYPE_ALUMNOS;
            case URI_TYPE_ALUMNOS_ID:
                return MIME_ITEM_TYPE_ALUMNOS;
            // Para las sugerencias.
            case URI_TYPE_SUGERENCIAS:
                return SearchManager.SUGGEST_MIME_TYPE;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
    }

    // Variables miembro.
    // private SQLiteDatabase bd;
    private InstitutoHelper helper;

    // Retorna si todo ha ido bien.
    @Override
    public boolean onCreate() {
        // Se crea el helper para el acceso a la bd.
        helper = new InstitutoHelper(getContext());
        return true;
    }

    // Retorna el cursor con el resultado de la consulta. Recibe los parámetros
    // indicados en el método query del ContentResolver.
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Se abre la base de datos.
        SQLiteDatabase bd = helper.getReadableDatabase();
        // Se crea un constructor de consultas.
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        // Dependiendo de la uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        Cursor cursor;
        switch (tipoURI) {
            case URI_TYPE_ALUMNOS_LIST:
                // Se compueba si el llamador ha solicitado una columna que no
                // existe.
                checkColumns(InstitutoContract.Alumno.TODOS, projection);
                // Se establece la tabla para la consulta.
                builder.setTables(InstitutoContract.Alumno.TABLA);
                // Se realiza la consulta.
                cursor = builder.query(bd, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;
            case URI_TYPE_ALUMNOS_ID:
                // Se compueba si el llamador ha solicitado una columna que no
                // existe.
                checkColumns(InstitutoContract.Alumno.TODOS, projection);
                // Se establece la tabla para la consulta.
                builder.setTables(InstitutoContract.Alumno.TABLA);
                // Se agrega al where la selección de ese alumno.
                builder.appendWhere(InstitutoContract.Alumno._ID + " = "
                        + uri.getLastPathSegment());
                // Se realiza la consulta.
                cursor = builder.query(bd, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;
            // Para las sugerencias.
            case URI_TYPE_SUGERENCIAS:
                if (selectionArgs == null) {
                    throw new IllegalArgumentException(
                            "selectionArgs must be provided for the Uri: " + uri);
                } else if (selectionArgs.length == 0 || selectionArgs[0].length() == 0) {
                    return null;
                }
                // Se establece la tabla para la consulta.
                builder.setTables(InstitutoContract.Alumno.TABLA);
                // Se crea un mapa de renombrado (AS) de los campos del SELECT, ya que debemos
                // proporcionar los nombres que espera el SearchView.
                String[] campos = new String[]{"ROWID", InstitutoContract.Alumno._ID, InstitutoContract.Alumno.NOMBRE, InstitutoContract.Alumno.CURSO, InstitutoContract.Alumno.FOTO};
                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("ROWID", BaseColumns._ID);
                mapa.put(InstitutoContract.Alumno.NOMBRE,
                        InstitutoContract.Alumno.NOMBRE + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
                mapa.put(InstitutoContract.Alumno.CURSO,
                        InstitutoContract.Alumno.CURSO + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_2);
                mapa.put(InstitutoContract.Alumno.FOTO,
                        InstitutoContract.Alumno.FOTO + " AS " + SearchManager.SUGGEST_COLUMN_ICON_1);
                mapa.put(InstitutoContract.Alumno._ID,
                        InstitutoContract.Alumno._ID + " AS " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
                builder.setProjectionMap(mapa);
                String termino = selectionArgs[0];
                String criterio = InstitutoContract.Alumno.NOMBRE + " LIKE '%" + termino + "%'";
                // Se realiza la consulta.
                cursor = builder.query(bd, campos, criterio, null,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica a los escuchadores del content provider.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    // Retorna el número de registros eliminados. Recibe los parámetros recibido
    // por el método delete del ContentResolver.
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int filasBorradas;
        // Se inicializa la selección.
        String where = selection;
        // Se obtiene la base de datos.
        SQLiteDatabase bd = helper.getWritableDatabase();
        // Dependiendo de la uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
            case URI_TYPE_ALUMNOS_LIST:
                // Se realiza el borrado.
                filasBorradas = bd.delete(InstitutoContract.Alumno.TABLA, where, selectionArgs);
                break;
            case URI_TYPE_ALUMNOS_ID:
                // Se agrega al where la selección de ese alumno.
                where = InstitutoContract.Alumno._ID + "=" + uri.getLastPathSegment()
                        + (TextUtils.isEmpty(selection) ? "" : " and " + where);
                // Se realiza el borrado.
                filasBorradas = bd.delete(InstitutoContract.Alumno.TABLA, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica de los cambios a todos los listener.
        if (filasBorradas > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return filasBorradas;
    }

    // Retorna la uri del nuevo registro. Recibe los parámetros recibido por el
    // método insert del ContentResolver.
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id;
        // Se obtiene la base de datos.
        SQLiteDatabase bd = helper.getWritableDatabase();
        // Dependiendo de la uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
            case URI_TYPE_ALUMNOS_LIST:
                id = bd.insert(InstitutoContract.Alumno.TABLA, null, values);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica a los escuchadores del content provider.
        getContext().getContentResolver().notifyChange(uri, null);
        // Se retorna la URI del registro insertado.
        return ContentUris.withAppendedId(uri, id);
    }

    // Retorna el número de registros actualizados. Recibe los parámetros
    // recibidos por el método update del ContentResolver.
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int filasActualizadas;
        // Se inicializa la parte del where.
        String where = selection;
        // Se obtiene la base de datos.
        SQLiteDatabase bd = helper.getWritableDatabase();
        // Depndiendo del tipo de uri solicitada.
        int tipoURI = validadorURIs.match(uri);
        switch (tipoURI) {
            case URI_TYPE_ALUMNOS_LIST:
                filasActualizadas = bd.update(InstitutoContract.Alumno.TABLA, values, where,
                        selectionArgs);
                break;
            case URI_TYPE_ALUMNOS_ID:
                // Se agrega al where la selección de ese alumno.
                where = InstitutoContract.Alumno._ID + "=" + uri.getLastPathSegment()
                        + (TextUtils.isEmpty(selection) ? "" : " and " + where);
                filasActualizadas = bd.update(InstitutoContract.Alumno.TABLA, values, where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        // Se notifica a los listeners.
        if (filasActualizadas > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Se retorna el número de registros actualizados.
        return filasActualizadas;
    }

    // Comprueba si todas las columnas están entre las disponibles.
    private void checkColumns(String[] disponibles, String[] columnas) {
        if (columnas != null) {
            HashSet<String> columnasSolicitadas = new HashSet<>(
                    Arrays.asList(columnas));
            HashSet<String> columnasDisponibles = new HashSet<>(
                    Arrays.asList(disponibles));
            // Si hay alguna solicitada no disponible se lanza excepción.
            if (!columnasDisponibles.containsAll(columnasSolicitadas)) {
                throw new IllegalArgumentException(
                        "Se ha solicitado un campo desconocido");
            }
        }
    }
}
