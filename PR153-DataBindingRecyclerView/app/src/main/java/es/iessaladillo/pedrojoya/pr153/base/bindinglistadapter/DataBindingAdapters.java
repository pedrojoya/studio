package es.iessaladillo.pedrojoya.pr153.base.bindinglistadapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class DataBindingAdapters {

    private DataBindingAdapters() {
    }

    @BindingAdapter("app:defaultItemAnimator")
    public static void setDefaultItemAnimator(RecyclerView recyclerView, boolean
            defaultItemAnimator) {
        if (recyclerView != null && defaultItemAnimator) {
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @BindingAdapter("app:dividerItemDecoration")
    public static void setDividerItemDecoration(RecyclerView recyclerView,
                                                Boolean dividerItemDecoration) {
        if (recyclerView != null && dividerItemDecoration) {
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                    LinearLayoutManager.VERTICAL));
        }
    }

    @BindingAdapter("app:onItemClick")
    public static void onItemClick(RecyclerView recyclerView,
                                   final OnItemClickListener action) {
        if (recyclerView != null && recyclerView.getAdapter() instanceof BindingListAdapter) {
            BindingListAdapter adapter = (BindingListAdapter) recyclerView.getAdapter();
            adapter.setOnItemClickListener(action);
        }
    }

    @BindingAdapter("app:onItemLongClick")
    public static void onItemLongClick(RecyclerView recyclerView,
                                       final OnItemLongClickListener action) {
        if (recyclerView != null && recyclerView.getAdapter() instanceof BindingListAdapter) {
            BindingListAdapter adapter = (BindingListAdapter) recyclerView.getAdapter();
            adapter.setOnItemLongClickListener(action);
        }
    }

    @BindingAdapter("app:emptyView")
    public static void addEmptyView(RecyclerView recyclerView, View emptyView) {
        if (recyclerView != null && emptyView != null
                && recyclerView.getAdapter() instanceof BindingListAdapter) {
            BindingListAdapter adapter = (BindingListAdapter) recyclerView.getAdapter();
            adapter.setEmptyView(emptyView);
        }
    }

    @BindingAdapter(value = {"app:onSwipedRight", "app:onSwipedLeft"}, requireAll = false)
    public static void setTouchHelper(RecyclerView recyclerView,
                                      OnSwipedListener onSwipedRight, OnSwipedListener onSwipedLeft) {

        int swipeDirs = 0;
        if (onSwipedRight != null) {
            swipeDirs = swipeDirs | ItemTouchHelper.RIGHT;
        }
        if (onSwipedLeft != null) {
            swipeDirs = swipeDirs | ItemTouchHelper.LEFT;
        }
        if (recyclerView != null && recyclerView.getAdapter() instanceof BindingListAdapter &&
                (onSwipedRight != null || onSwipedLeft != null)) {
            BindingListAdapter adapter = (BindingListAdapter) recyclerView.getAdapter();
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                    new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                            swipeDirs) {

                        @Override
                        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                              RecyclerView.ViewHolder target) {
                            return false;
                        }

                        @Override
                        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                            if (onSwipedLeft != null && direction == ItemTouchHelper.LEFT) {
                                onSwipedLeft.onSwiped(viewHolder, direction, adapter.getItem
                                        (viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                            }
                            if (onSwipedRight != null && direction == ItemTouchHelper.RIGHT) {
                                onSwipedRight.onSwiped(viewHolder, direction, adapter.getItem
                                        (viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
                            }
                        }
                    });
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

}
