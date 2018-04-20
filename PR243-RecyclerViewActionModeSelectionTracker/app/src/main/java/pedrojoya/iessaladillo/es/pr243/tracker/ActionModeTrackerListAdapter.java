package pedrojoya.iessaladillo.es.pr243.tracker;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.OnItemActivatedListener;
import androidx.recyclerview.selection.Selection;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import pedrojoya.iessaladillo.es.pr243.actionmode.OnItemClickListener;
import pedrojoya.iessaladillo.es.pr243.actionmode.OnItemLongClickListener;

@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class ActionModeTrackerListAdapter<M extends Parcelable, V extends ActionModeTrackerViewHolder<M>> extends ListAdapter<M, V> implements ActionMode.Callback {

    private AppCompatActivity activity;
    private ItemKeyProvider<M> itemKeyProvider;
    private List<M> list;

    @SuppressWarnings("unused")
    public interface MultiChoiceModeListener extends ActionMode.Callback {
        void onSelectionChanged(ActionMode mode, int selected);

    }

    private SelectionTracker selectionTracker;
    private MultiChoiceModeListener multichoiceModeListener;
    private ActionMode actionMode;
    private OnItemClickListener<M> onItemClickListener;
    private OnItemLongClickListener<M> onItemLongClickListener;
    private View emptyView;

    public ActionModeTrackerListAdapter(
            DiffUtil.ItemCallback<M> diffUtilItemCallback) {
        super(diffUtilItemCallback);
    }

    public void buildSelectionTracker(AppCompatActivity activity,
            ActionModeTrackerListAdapter.MultiChoiceModeListener multiChoiceModeListener,
            String selectionId, RecyclerView recyclerView, Class<M> modelClass) {
        this.activity = activity;
        this.multichoiceModeListener = multiChoiceModeListener;
        itemKeyProvider = new ItemKeyProvider<M>(ItemKeyProvider.SCOPE_CACHED) {
            @Nullable
            @Override
            public M getKey(int position) {
                return getItem(position);
            }

            @Override
            public int getPosition(@NonNull M key) {
                return getPositionForItem(key);
            }
        };

        selectionTracker = new SelectionTracker.Builder<M>(selectionId, recyclerView,
                itemKeyProvider, new ItemDetailsLookup<M>() {
            @Nullable
            @Override
            public ItemDetails getItemDetails(@NonNull MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (view != null) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                    if (viewHolder instanceof RecyclerListAdapter.ViewHolder) {
                        return ((RecyclerListAdapter.ViewHolder) viewHolder).getItemDetails();
                    }
                }
                return null;
            }
        }, StorageStrategy.createParcelableStorage(modelClass)).withOnItemActivatedListener(
                new OnItemActivatedListener<M>() {
                    @Override
                    public boolean onItemActivated(
                            @NonNull ItemDetailsLookup.ItemDetails<M> itemDetails,
                            @NonNull MotionEvent e) {
                        if (actionMode == null && onItemClickListener != null) {
                            onItemClickListener.onItemClick(null,
                                    getItem(itemDetails.getPosition()), itemDetails.getPosition(),
                                    itemDetails.getPosition());
                            return true;
                        }
                        return false;
                    }
                }).build();

        selectionTracker.addObserver(new SelectionTracker.SelectionObserver() {
            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if (selectionTracker.hasSelection() && actionMode != null) {
                    actionMode.finish();
                    actionMode = null;
                } else {
                    if (actionMode == null) {
                        actionMode = activity.startSupportActionMode(
                                ActionModeTrackerListAdapter.this);
                    }
                    multichoiceModeListener.onSelectionChanged(actionMode,
                            selectionTracker.getSelection().size());
                }
            }
        });
    }

    public int getPositionForItem(M item) {
        return list.indexOf(item);
    }

    public ActionMode getActionMode() {
        return actionMode;
    }

    public Selection getSelection() {
        return selectionTracker.getSelection();
    }

    public void deselect(Object key) {
        selectionTracker.deselect(key);
    }

    //    public void toggleChecked(int position) {
    //        onChecked(position, !isChecked(position));
    //    }

    //    public void onChecked(int position, boolean isChecked) {
    //        selectionTracker.setItemsSelected().setChecked(position, isChecked);
    //        if (selectionTracker.hasSelection() && actionMode != null) {
    //            actionMode.finish();
    //        } else {
    //            if (actionMode == null) {
    //                actionMode = activity.startSupportActionMode(this);
    //            }
    //            multichoiceModeListener.onSelectionChanged(actionMode, position,
    //                    getItemId(position), isChecked);
    //        }
    //    }

    //    public int getCheckedCount() {
    //        return (selectionTracker.getSelection().size());
    //    }
    //
    //
    //
    //    public boolean isChecked(int position) {
    //        return (selectionTracker.isSelected().isChecked(position));
    //    }
    //
    //    public void visitChecks(ManyChoiceMode.Visitor v) {
    //        selectionTracker.getSelection()..visitChecks(v);
    //    }

    @Override
    public M getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        actionMode = mode;
        return multichoiceModeListener.onCreateActionMode(mode, menu);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return multichoiceModeListener.onPrepareActionMode(mode, menu);
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return multichoiceModeListener.onActionItemClicked(mode, item);
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        if (actionMode != null) {
            selectionTracker.clearSelection();
            actionMode = null;
        }
        multichoiceModeListener.onDestroyActionMode(mode);
    }

    @Override
    public void submitList(List<M> list) {
        this.list = list;
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        if (actionMode != null) {
            actionMode.finish();
        }
        super.submitList(list);
    }

    public void setOnItemClickListener(OnItemClickListener<M> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<M> listener) {
        this.onItemLongClickListener = listener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        checkEmptyViewVisibility(getItemCount());
    }

    private void checkEmptyViewVisibility(int size) {
        if (emptyView != null) {
            emptyView.setVisibility(size == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void onSaveInstanceState(Bundle state) {
        selectionTracker.onSaveInstanceState(state);
    }

    public void onRestoreInstanceState(Bundle state) {
        selectionTracker.onRestoreInstanceState(state);
        if (selectionTracker.hasSelection()) {
            actionMode = activity.startSupportActionMode(this);
        }
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        if (selectionTracker != null) {
            holder.setChecked(selectionTracker.isSelected(itemKeyProvider.getKey(position)));
        }
        holder.bind(getItem(position));
    }

    public OnItemClickListener<M> getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener<M> getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

}
