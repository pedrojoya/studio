package pedrojoya.iessaladillo.es.pr227.base;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public abstract class LeaveBehindCallback extends ItemTouchHelper.SimpleCallback {

    private IconicDrawable leftIconicDrawable;
    private IconicDrawable rightIconicDrawable;

    @SuppressWarnings("SameParameterValue")
    public LeaveBehindCallback(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    @SuppressWarnings("UnusedReturnValue")
    public LeaveBehindCallback withLeftIconicDrawable(IconicDrawable iconicDrawable) {
        this.leftIconicDrawable = iconicDrawable;
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public LeaveBehindCallback withRightIconicDrawable(IconicDrawable iconicDrawable) {
        this.rightIconicDrawable = iconicDrawable;
        return this;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (dX < 0 && rightIconicDrawable != null) {
            showRightLeaveBehind(c, viewHolder, dX);
        } else if (dX > 0 && leftIconicDrawable != null) {
            showLeftLeaveBehind(c, viewHolder, dX);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void showRightLeaveBehind(Canvas c, RecyclerView.ViewHolder viewHolder, float dX) {
        // Draw background.
        ColorDrawable background = new ColorDrawable();
        background.setColor(rightIconicDrawable.getBackgroundColor());
        background.setBounds(viewHolder.itemView.getRight() + (int) dX,
                viewHolder.itemView.getTop(), viewHolder.itemView.getRight(),
                viewHolder.itemView.getBottom());
        background.draw(c);
        // Draw icon.
        int itemHeight = viewHolder.itemView.getBottom() - viewHolder.itemView.getTop();
        Drawable icon = rightIconicDrawable.getIcon();
        int iconIntrinsicWidth = icon.getIntrinsicWidth();
        int iconIntrinsicHeight = icon.getIntrinsicHeight();
        int iconTop = viewHolder.itemView.getTop() + (itemHeight - iconIntrinsicHeight) / 2;
        int iconMargin = (itemHeight - iconIntrinsicHeight) / 2;
        int iconLeft = viewHolder.itemView.getRight() - iconMargin - iconIntrinsicWidth;
        int iconRight = viewHolder.itemView.getRight() - iconMargin;
        int iconBottom = iconTop + iconIntrinsicHeight;
        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        icon.draw(c);
    }

    private void showLeftLeaveBehind(Canvas c, RecyclerView.ViewHolder viewHolder, float dX) {
        // Draw background.
        ColorDrawable background = new ColorDrawable();
        background.setColor(leftIconicDrawable.getBackgroundColor());
        background.setBounds(viewHolder.itemView.getLeft(), viewHolder.itemView.getTop(),
                viewHolder.itemView.getLeft() + (int) dX, viewHolder.itemView.getBottom());
        background.draw(c);
        // Draw icon.
        int itemHeight = viewHolder.itemView.getBottom() - viewHolder.itemView.getTop();
        Drawable icon = leftIconicDrawable.getIcon();
        int iconIntrinsicWidth = icon.getIntrinsicWidth();
        int iconIntrinsicHeight = icon.getIntrinsicHeight();
        int iconTop = viewHolder.itemView.getTop() + (itemHeight - iconIntrinsicHeight) / 2;
        int iconMargin = (itemHeight - iconIntrinsicHeight) / 2;
        int iconLeft = viewHolder.itemView.getLeft() + iconMargin;
        int iconRight = viewHolder.itemView.getLeft() + iconMargin + iconIntrinsicWidth;
        int iconBottom = iconTop + iconIntrinsicHeight;
        icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
        icon.draw(c);
    }

}
