package pedrojoya.iessaladillo.es.pr105;

import android.support.v4.widget.NestedScrollView;
import android.view.ViewTreeObserver;

public abstract class HideShowScrollListener implements ViewTreeObserver
        .OnScrollChangedListener {

    private static final int UMBRAL_SCROLL = 20;

    NestedScrollView svScrollView;
    private int mScrollAnterior;
    private boolean mVistasOcultas;


    public HideShowScrollListener(NestedScrollView svScrollView) {
        this.svScrollView = svScrollView;
    }

    @Override
    public void onScrollChanged() {
        int diferencia = svScrollView.getScrollY() - mScrollAnterior;
        if (mVistasOcultas && diferencia < -UMBRAL_SCROLL) {
            mVistasOcultas = false;
            showVistas();
        } else if (!mVistasOcultas && diferencia > UMBRAL_SCROLL) {
            mVistasOcultas = true;
            hideVistas();
        }
        mScrollAnterior = svScrollView.getScrollY();
    }

    public void reset() {
        mScrollAnterior = 0;
        mVistasOcultas = false;
    }

    public abstract void showVistas();
    public abstract void hideVistas();
}
