package es.iessaladillo.pedrojoya.pr139;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

@SuppressWarnings({"SameParameterValue", "UnusedParameters", "WeakerAccess", "FieldCanBeLocal"})
public abstract class EndlessRecyclerOnScrollListener extends
        RecyclerView.OnScrollListener {

    // Número total de ítems antes de la última carga.
    private int previousTotal = 0;
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    // Umbral de disparo de carga.
    private final int visibleThreshold = 5;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private int current_page = 1;

    private final LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++;

            onLoadMore(current_page);

            loading = true;
        }
    }

    public void reset(int previousTotal, boolean loading) {
        this.previousTotal = previousTotal;
        this.loading = loading;
    }

    public abstract void onLoadMore(int current_page);

}