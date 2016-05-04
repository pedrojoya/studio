package pedrojoya.iessaladillo.es.pr105.utils;

import android.support.v4.widget.NestedScrollView;
import android.view.ViewTreeObserver;

@SuppressWarnings("unused")
public abstract class HideShowNestedScrollListener implements ViewTreeObserver
        .OnScrollChangedListener {

    private static final int UMBRAL_SCROLL = 20;

    private final NestedScrollView svScrollView;
    private int mScrollAnterior;
    private boolean mVistasOcultas;


    public HideShowNestedScrollListener(NestedScrollView svScrollView) {
        this.svScrollView = svScrollView;
    }

    @Override
    public void onScrollChanged() {
        int diferencia = svScrollView.getScrollY() - mScrollAnterior;
        if (mVistasOcultas && diferencia < -UMBRAL_SCROLL) {
            mVistasOcultas = false;
            onShow();
        } else if (!mVistasOcultas && diferencia > UMBRAL_SCROLL) {
            mVistasOcultas = true;
            onHide();
        }
        mScrollAnterior = svScrollView.getScrollY();
    }

    // Resetea los valores iniciales.
    public void reset() {
        mScrollAnterior = 0;
        mVistasOcultas = false;
    }

    public abstract void onShow();
    public abstract void onHide();
}
