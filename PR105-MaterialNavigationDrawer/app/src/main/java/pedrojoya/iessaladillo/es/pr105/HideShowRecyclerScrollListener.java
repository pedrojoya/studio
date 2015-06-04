package pedrojoya.iessaladillo.es.pr105;

import android.support.v7.widget.RecyclerView;

public abstract class HideShowRecyclerScrollListener extends RecyclerView
        .OnScrollListener {

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // Si se ha desplazado hacia abajo lo suficiente y los controles están
        // visibles.
        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            // Se ocultan.
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;
            // Si se ha desplazado lo suficiente hacia arriba y los controles no
            // están visibles.
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            // Se muestran.
            onShow();
            controlsVisible = true;
            // Se reinicia el acumulador de distancia desplazada.
            scrolledDistance = 0;
        }
        // Acumulamos la distancia recorrida (sólo si tiene sentido).
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
            scrolledDistance += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();

}