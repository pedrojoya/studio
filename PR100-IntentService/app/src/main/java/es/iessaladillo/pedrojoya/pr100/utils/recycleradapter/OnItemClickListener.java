package es.iessaladillo.pedrojoya.pr100.utils.recycleradapter;

import android.view.View;

public interface OnItemClickListener<M> {
    @SuppressWarnings("unused")
    void onItemClick(View view, M item, int position, long id);
}
