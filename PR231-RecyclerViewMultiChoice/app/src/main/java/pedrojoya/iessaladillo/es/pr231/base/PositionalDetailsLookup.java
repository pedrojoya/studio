package pedrojoya.iessaladillo.es.pr231.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.selection.ItemDetailsLookup;

// Clase que usa el tracker para preguntar por los detalles de un determinado ítem
// del recyclerview. Esta clase a su vez pregunta a un viewHolder que implemente la interfaz
// DetailsProvider.
public class PositionalDetailsLookup extends ItemDetailsLookup<Long> {

    private final RecyclerView recyclerView;

    public PositionalDetailsLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    // Retorna el detalle del elemento del recyclerview sobre el que nos hemos movido,
    // que lo obtiene desde su viewHolder.
    @Nullable
    @Override
    public ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            // El viewHolder debe implementar DetailsProvider para poder pedirle
            // los detalles.
            if (viewHolder instanceof DetailsProvider) {
                // Le pedimos al viewHolder los detalles.
                return ((DetailsProvider) viewHolder).getItemDetails();
            }
        }
        return null;
    }

    public interface DetailsProvider {

        // El ViewHolder debe tener un método que retorne los detalles del elemento
        // correspondiente del recyclerview.
        ItemDetails<Long> getItemDetails();

    }
}