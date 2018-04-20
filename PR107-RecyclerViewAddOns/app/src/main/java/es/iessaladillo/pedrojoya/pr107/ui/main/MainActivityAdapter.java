package es.iessaladillo.pedrojoya.pr107.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr107.R;
import es.iessaladillo.pedrojoya.pr107.data.model.Student;

@SuppressWarnings("unused")
public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    @SuppressWarnings("unused")
    public interface OnItemClickListener {
        void onItemClick(View view, Student student, int position);
    }

    @SuppressWarnings("unused")
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Student student, int position);
    }

    private List<Student> mData;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;
    private final SparseBooleanArray mSelectedItems = new SparseBooleanArray();
    private View mEmptyView;
    private final RecyclerView.AdapterDataObserver mObserver = new RecyclerView
            .AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            checkEmptyViewVisibility();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            checkEmptyViewVisibility();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            checkEmptyViewVisibility();
        }
    };


    public MainActivityAdapter() {
        // Needed for drag & drop.
        setHasStableIds(true);
    }

    public void setData(List<Student> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, mData.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v,
                        mData.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
                return true;
            } else {
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivityAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public Student getItem(int position) {
        return mData.get(position);
    }


    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void removeSelectedItems() {
        // Removed in inverse order. Change selection state.
        List<Integer> selectedPositions = getSelectedItemsPositions();
        Collections.sort(selectedPositions, Collections.reverseOrder());
        for (int i = 0; i < selectedPositions.size(); i++) {
            int pos = selectedPositions.get(i);
            toggleSelection(pos);
            removeItem(pos);
        }
    }

    public void addItem(Student student, int position) {
        mData.add(position, student);
        notifyItemInserted(position);
    }

    void swapItems(int from, int to) {
        Collections.swap(mData, from, to);
        // Swap selection state of the items from y to.
        boolean fromSelected = mSelectedItems.get(from, false);
        boolean toSelected = mSelectedItems.get(to, false);
        if (fromSelected) {
            mSelectedItems.delete(from);
        }
        if (toSelected) {
            mSelectedItems.delete(to);
        }
        if (fromSelected) {
            mSelectedItems.put(to, true);
        }
        if (toSelected) {
            mSelectedItems.put(from, true);
        }
        notifyItemMoved(from, to);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    public void toggleSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        } else {
            mSelectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void setSelected(int position) {
        mSelectedItems.put(position, true);
        notifyItemChanged(position);

    }

    public void clearSelection(int position) {
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
        }
        notifyItemChanged(position);
    }

    public void clearSelections() {
        if (mSelectedItems.size() > 0) {
            mSelectedItems.clear();
            notifyDataSetChanged();
        }
    }

    public int getSelectedItemCount() {
        return mSelectedItems.size();
    }

    @SuppressWarnings("WeakerAccess")
    public List<Integer> getSelectedItemsPositions() {
        List<Integer> items = new ArrayList<>(mSelectedItems.size());
        for (int i = 0; i < mSelectedItems.size(); i++) {
            items.add(mSelectedItems.keyAt(i));
        }
        return items;
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mData.size()) {
            return -1;
        }
        return mData.get(position).getId();
    }

    public int getPositionForId(long id) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public void moveItem(int start, int end) {
        int max = Math.max(start, end);
        int min = Math.min(start, end);
        if (min >= 0 && max < mData.size()) {
            Student item = mData.remove(min);
            mData.add(max, item);
            notifyItemMoved(min, max);
        }
    }

    public void setEmptyView(@NonNull View emptyView) {
        if (mEmptyView != null) {
            unregisterAdapterDataObserver(mObserver);
        }
        mEmptyView = emptyView;
        registerAdapterDataObserver(mObserver);
    }


    private void checkEmptyViewVisibility() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void onDestroy() {
        if (mEmptyView != null) {
            unregisterAdapterDataObserver(mObserver);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

        public void bind(Student student, int position) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            Picasso.with(imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                    R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(imgAvatar);
            // Activate item.
            itemView.setActivated(mSelectedItems.get(position, false));
        }
    }

}
