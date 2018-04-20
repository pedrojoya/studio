package pedrojoya.iessaladillo.es.pr243.tracker;

import android.support.annotation.Nullable;

import androidx.recyclerview.selection.ItemDetailsLookup;

public class RecyclerListAdapterItemDetails<M> extends ItemDetailsLookup.ItemDetails<M> {
    @Override
    public int getPosition() {
        return 0;
    }

    @Nullable
    @Override
    public M getSelectionKey() {
        return null;
    }
}
