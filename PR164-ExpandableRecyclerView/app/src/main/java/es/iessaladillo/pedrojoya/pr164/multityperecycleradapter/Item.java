package es.iessaladillo.pedrojoya.pr164.multityperecycleradapter;

import android.support.annotation.LayoutRes;
import android.view.View;

public abstract class Item<V extends MultiTypeViewHolder> {

    @LayoutRes
    public abstract int getLayoutResIdForType();

    public abstract V createViewHolder(View itemView);

    public abstract void bind(V holder, int position);

    public abstract boolean areItemsTheSame(Item newItem);

    public abstract boolean areContentsTheSame(Item newItem);

}
