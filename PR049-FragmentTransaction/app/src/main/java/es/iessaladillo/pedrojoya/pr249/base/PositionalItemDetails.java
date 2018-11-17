package es.iessaladillo.pedrojoya.pr249.base;

import androidx.annotation.Nullable;

import androidx.recyclerview.selection.ItemDetailsLookup;

// Clase que contiene los detalles de selección de un determinado item.
// Cada viewHolder crea un objeto de esta clase en su método getItemDetails().
// En este caso se usa la posición en el adaptador como clave.
public class PositionalItemDetails extends ItemDetailsLookup.ItemDetails<Long> {

    private final int adapterPosition;

    public PositionalItemDetails(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Long getSelectionKey() {
        return (long) adapterPosition;
    }

}