package pedrojoya.iessaladillo.es.pr243.tracker;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import androidx.recyclerview.selection.ItemKeyProvider;

// M for Model, V for ViewHolder
public class RecyclerListAdapterItemKeyProvider<M extends Parcelable, V extends RecyclerListAdapter
        .ViewHolder>
        extends ItemKeyProvider<M>{

    private final RecyclerListAdapter<M, V> recyclerListAdapter;

    protected RecyclerListAdapterItemKeyProvider(int scope, RecyclerListAdapter<M, V>
            recyclerListAdapter) {
        super(scope);
        this.recyclerListAdapter = recyclerListAdapter;
    }

    @Nullable
    @Override
    public M getKey(int position) {
        return recyclerListAdapter.getItem(position);
    }

    @Override
    public int getPosition(@NonNull M key) {
        return recyclerListAdapter.getPositionForItem(key);
    }

}
