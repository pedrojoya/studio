package pedrojoya.iessaladillo.es.pr226.recycleradapter;

import android.view.View;

@SuppressWarnings("WeakerAccess")
public interface OnItemLongClickListener<M> {
    @SuppressWarnings({"SameReturnValue", "unused"})
    boolean onItemLongClick(View view, M item, int position, long id);
}
