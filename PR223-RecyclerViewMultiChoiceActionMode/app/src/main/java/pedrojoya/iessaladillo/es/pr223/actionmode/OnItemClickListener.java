package pedrojoya.iessaladillo.es.pr223.actionmode;

import android.view.View;

public interface OnItemClickListener<M> {
    @SuppressWarnings("unused")
    void onItemClick(View view, M item, int position, long id);
}
