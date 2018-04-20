package es.iessaladillo.pedrojoya.pr027.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;

// CursorLoader sin content provider. Autor original: Christophe Beyls.
@SuppressWarnings("WeakerAccess")
public abstract class SimpleCursorLoader extends AsyncTaskLoader<Cursor> {

    // Observador de los cambios en el cursor.
    private final ForceLoadContentObserver mObserver;
    // Cursor gestionado por el Loader.
    private Cursor mCursor;

    // Se ejecuta en un hilo secundario. Retorna el cursor a entregar.
    @Override
    public Cursor loadInBackground() {
        // Se obtiene el cursor.
        Cursor cursor = getCursor();
        if (cursor != null) {
            // Se llena la ventana del cursor.
            cursor.getCount();
            // Se registra el observador para que
            cursor.registerContentObserver(mObserver);
            cursor.setNotificationUri(getContext().getContentResolver(), getUri());
        }
        return cursor;
    }

    // Entrega el cursor en el hilo de la UI.
    @Override
    public void deliverResult(Cursor cursor) {
        // Si han llegado los datos cuando ya se había parado el Loader,
        // se cierra el cursor y no se entrega nada.
        if (isReset()) {
            if (cursor != null) {
                cursor.close();
            }
            return;
        }
        // Se actualiza la copia local del cursor.
        Cursor oldCursor = mCursor;
        mCursor = cursor;
        // Si el Loader está iniciado se entrega el cursor.
        if (isStarted()) {
            super.deliverResult(cursor);
        }
        // Si el nuevo cursor es distinto que el anterior y estaba abierto
        // se cierra el cursor viejo.
        if (oldCursor != null && oldCursor != cursor && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    // Constructor. Recibe el contexto porque lo necesita la clase padre.
    public SimpleCursorLoader(Context context) {
        super(context);
        // Se crea un observador.
        mObserver = new ForceLoadContentObserver();
    }

    // Cuando se solicitan los datos.
    @Override
    protected void onStartLoading() {
        // Si ya tenemos el cursor, se entrega.
        if (mCursor != null) {
            deliverResult(mCursor);
        }
        // Si ha habido cambios o no tenemos el cursor, se fuerza la carga.
        if (takeContentChanged() || mCursor == null) {
            forceLoad();
        }
    }

    // Cuando se para el Loader.
    @Override
    protected void onStopLoading() {
        // Se marca para que se cancele la carga.
        cancelLoad();
    }

    // Cuando se cancela la carga.
    @Override
    public void onCanceled(Cursor cursor) {
        // Si tenemos el cursor abierto, se cierra.
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        // Se indica que han cambiado los datos, para que la próxima vez que se
        // soliciten se obtengan de nuevo en vez de entregar la copia local.
        onContentChanged();
    }

    // Cuando se reseta el Loader.
    @Override
    protected void onReset() {
        super.onReset();
        // Nos aseguramos de parar el Loader.
        onStopLoading();
        // Si tenemos el cursor y está abierto, se cierra.
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        // Se libera la memoria asociada al cursor.
        mCursor = null;
    }

    // Método abstracto que debe implementarse con el código necesario
    // para obtener el cursor (consulta), que será ejecutado en un hilo
    // secundario.
    protected abstract Cursor getCursor();

    // Método abstracto que debe implementarse con la Uri que se va a observar.
    protected abstract Uri getUri();

}
