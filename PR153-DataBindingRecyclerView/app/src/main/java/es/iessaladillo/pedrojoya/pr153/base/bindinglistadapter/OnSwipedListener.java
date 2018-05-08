package es.iessaladillo.pedrojoya.pr153.base.bindinglistadapter;

import android.support.v7.widget.RecyclerView;

@SuppressWarnings("WeakerAccess")
public interface OnSwipedListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, Object item, int position);
}
