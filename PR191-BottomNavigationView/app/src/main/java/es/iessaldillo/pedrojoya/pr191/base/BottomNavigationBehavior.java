package es.iessaldillo.pedrojoya.pr191.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    private boolean isSnackbarShowing = false;
    private Snackbar.SnackbarLayout snackbar;

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent,
            @NonNull BottomNavigationView child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout || dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
            @NonNull BottomNavigationView child, @NonNull View directTargetChild,
            @NonNull View target, int nestedScrollAxes, int type) {
        return true;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout,
            @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy,
            @NonNull int[] consumed, int type) {
        if (isSnackbarShowing) {
            if (snackbar != null) {
                updateSnackbarPaddingByBottomNavigationView(child);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull BottomNavigationView child,
            @NonNull View dependency) {
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
    public void onDependentViewRemoved(@NonNull CoordinatorLayout parent, @NonNull BottomNavigationView child,
            @NonNull View dependency) {
        if (dependency instanceof Snackbar.SnackbarLayout) {
            isSnackbarShowing = false;
            snackbar = null;
        }
        super.onDependentViewRemoved(parent, child, dependency);
    }

    private void updateSnackbarPaddingByBottomNavigationView(BottomNavigationView view) {
        if (snackbar != null) {
            int bottomTranslate = (int) (view.getHeight() - view.getTranslationY());
            snackbar.setPadding(snackbar.getPaddingLeft(), snackbar.getPaddingTop(),
                    snackbar.getPaddingRight(), bottomTranslate);
            snackbar.requestLayout();
        }
    }

}