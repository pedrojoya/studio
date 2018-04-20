package pedrojoya.iessaladillo.es.pr223.actionmode;

import android.view.View;

public interface OnItemLongClickListener<M> {
    @SuppressWarnings({"SameReturnValue", "unused"})
    boolean onItemLongClick(View view, M item, int position, long id);
}
