package es.iessaladillo.pedrojoya.pr164;

import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// Elemento para el RecyclerView. Debe implementar Parcelable para que sea
// posible guardar la lista de datos en el cambio de orientación.
public abstract class ListItem implements Parcelable {

    // Constantes posibles para los tipos.
    @IntDef({TYPE_HEADER, TYPE_CHILD})
    // Se indica al compilador que no almacene los datos de la anotación en
    // el fichero .class
    @Retention(RetentionPolicy.SOURCE)
    // Declaramos el nombre de la anotación.
    public @interface ListItemType { }

    static final int TYPE_HEADER = 0;
    static final int TYPE_CHILD = 1;

    // Cualquier subclase deberá implementar este método para retornar
    // el tipo de elemento del que se trata.
    // Se usa la anotación para indicar que el tipo de retorno tiene que ser
    // una de las constantes.
    @ListItemType
    public abstract int getType();

}
