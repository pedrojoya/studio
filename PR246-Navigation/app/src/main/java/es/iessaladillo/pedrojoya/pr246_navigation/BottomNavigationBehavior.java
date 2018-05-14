package es.iessaladillo.pedrojoya.pr246_navigation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    private boolean isSnackbarShowing = false;
    private Snackbar.SnackbarLayout snackbar;

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        return dependency instanceof AppBarLayout || dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView
            child, @NonNull View directTargetChild, @NonNull View target, int nestedScrollAxes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView
            child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (isSnackbarShowing) {
            if (snackbar != null) {
                updateSnackbarPaddingByBottomNavigationView(child);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            AppBarLayout appbar = (AppBarLayout) dependency;
            float bottom = appbar.getBottom();
            float height = appbar.getHeight();
            float hidingRate = (height - bottom) / height;
            child.setTranslationY(child.getHeight() * hidingRate);
            return true;
        }
        if (dependency instanceof Snackbar.SnackbarLayout) {
            if (isSnackbarShowing) return true;
            isSnackbarShowing = true;
            snackbar = (Snackbar.SnackbarLayout) dependency;
            updateSnackbarPaddingByBottomNavigationView(child);
            return true;
        }
        return false;
    }

    @Override
    public void onDependentViewRemoved(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            isSnackbarShowing = false;
            snackbar = null;
        }
        super.onDependentViewRemoved(parent, child, dependency);
    }

    private void updateSnackbarPaddingByBottomNavigationView(BottomNavigationView view) {
        if (snackbar != null) {
            int bottomTranslate = (int) (view.getHeight() - view.getTranslationY());
            snackbar.setPadding(snackbar.getPaddingLeft(), snackbar.getPaddingTop(), snackbar.getPaddingRight(), bottomTranslate);
            snackbar.requestLayout();
        }
    }

}