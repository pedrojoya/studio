package es.iessaladillo.pedrojoya.pr178;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {

    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                                       @NonNull final FloatingActionButton child,
                                       @NonNull final View directTargetChild, @NonNull final View target,
            int axes, int type) {
        // Se reacciona ante el scroll vertical.
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(
                coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                               @NonNull final FloatingActionButton child, @NonNull final View target,
                               final int dxConsumed, final int dyConsumed, final int dxUnconsumed,
                               final int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed, type);
        if (dyConsumed > 0 && child.getTranslationY() == 0) {
            // Cuando se hace scroll hacia abajo se oculta el FAB.
            //child.hide();
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child
                    .getLayoutParams();
            ViewCompat.animate(child).translationY(child.getHeight() + lp.bottomMargin);
        } else if (dyConsumed < 0 && child.getTranslationY() > 0) {
            // Cuando se hace scroll hacia arriba se muestra el FAB.
            //child.show();
            ViewCompat.animate(child).translationY(0);
        }
    }

}
