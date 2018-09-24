package pedrojoya.iessaladillo.es.pr228.base;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/*
    Usa elementos del layout como leave-behinds derecho e izquierdo, situados en el fondo. Se
    basa en sólo desplazar la vista foreground, cuyo fondo no puede ser transparente.
 */
public abstract class LayoutLeaveBehindCallback extends ItemTouchHelper.SimpleCallback {

    @SuppressWarnings("SameParameterValue")
    public LayoutLeaveBehindCallback(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            // Para que sólo afecte la vista foreground.
            getDefaultUIUtil().onSelected(getForegroundView(viewHolder));
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
            RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
            boolean isCurrentlyActive) {
        // Para que sólo afecte la vista foreground.
        getDefaultUIUtil().onDrawOver(c, recyclerView, getForegroundView(viewHolder), dX, dY, actionState,
                isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // Para que sólo afecte la vista foreground.
        getDefaultUIUtil().clearView(getForegroundView(viewHolder));
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        // Para que sólo afecte la vista foreground.
        getDefaultUIUtil().onDraw(c, recyclerView, getForegroundView(viewHolder), dX, dY, actionState,
                isCurrentlyActive);
        View rightLeaveBehindView = getRightLeaveBehindView(viewHolder);
        View leftLeaveBehindView = getLeftLeaveBehindView(viewHolder);
        if (dX < 0) {
            if (rightLeaveBehindView != null) rightLeaveBehindView.setVisibility(View.VISIBLE);
            if (leftLeaveBehindView != null) leftLeaveBehindView.setVisibility(View.INVISIBLE);
        } else if (dX > 0) {
            if (rightLeaveBehindView != null) rightLeaveBehindView.setVisibility(View.INVISIBLE);
            if (leftLeaveBehindView != null) leftLeaveBehindView.setVisibility(View.VISIBLE);
        } else {
            if (rightLeaveBehindView != null) rightLeaveBehindView.setVisibility(View.INVISIBLE);
            if (leftLeaveBehindView != null) leftLeaveBehindView.setVisibility(View.INVISIBLE);
        }
    }

    protected abstract View getForegroundView(RecyclerView.ViewHolder viewHolder);
    protected abstract View getRightLeaveBehindView(RecyclerView.ViewHolder viewHolder);
    protected abstract View getLeftLeaveBehindView(RecyclerView.ViewHolder viewHolder);

}
