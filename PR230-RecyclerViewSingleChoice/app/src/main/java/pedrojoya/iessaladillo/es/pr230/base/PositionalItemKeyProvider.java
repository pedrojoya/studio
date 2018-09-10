package pedrojoya.iessaladillo.es.pr230.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import androidx.recyclerview.selection.ItemKeyProvider;

// Clase que indica la clave de cada posición del recyclerview y viceversa.
// Usaremos la propia posición como key.
public class PositionalItemKeyProvider extends ItemKeyProvider<Long> {

    public PositionalItemKeyProvider() {
        super(ItemKeyProvider.SCOPE_MAPPED);
    }

    // Retorna la clave que representa al elemento situado en una determianda posición.
    @Nullable
    @Override
    public Long getKey(int position) {
        return (long) position;
    }

    // Retorna la posición correspondiente a una determinada clave.
    @Override
    public int getPosition(@NonNull Long key) {
        return  (int) (long) key;
    }

}
