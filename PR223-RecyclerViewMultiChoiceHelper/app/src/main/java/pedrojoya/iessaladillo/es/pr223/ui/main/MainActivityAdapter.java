package pedrojoya.iessaladillo.es.pr223.ui.main;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr223.R;
import pedrojoya.iessaladillo.es.pr223.data.model.Student;
import pedrojoya.iessaladillo.es.pr223.utils.MultiChoiceHelper;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private final MultiChoiceHelper choiceHelper;
    private List<Student> mData;
    private OnItemClickListener onItemClickListener;
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

    public MainActivityAdapter(AppCompatActivity activity, ContextualActionsListener contextualActionsListener) {
        choiceHelper = new MultiChoiceHelper(activity, this);
        choiceHelper.setMultiChoiceModeListener(new MultiChoiceHelper.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main_contextual, menu);
                return true;
            }

            private void updateSelectedCountDisplay(ActionMode mode) {
                int count = choiceHelper.getCheckedItemCount();
                mode.setTitle(choiceHelper.getContext().getResources().getQuantityString(R
                        .plurals.selected, count, count));
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                updateSelectedCountDisplay(mode);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnuDelete:
                        contextualActionsListener.onDelete(choiceHelper.getCheckedItemIds());
                        mode.finish();
                        return true;
                }
                return false;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                updateSelectedCountDisplay(mode);
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Parcelable onSaveInstanceState() {
        return choiceHelper.onSaveInstanceState();
    }

    public void onRestoreInstanceState(Parcelable state) {
        choiceHelper.onRestoreInstanceState(state);
    }

    public void onDestroyView() {
        choiceHelper.clearChoices();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
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

    public void setData(List<Student> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @SuppressWarnings("unused")
    public void addItem(Student student) {
        mData.add(student);
        notifyItemInserted(mData.size() - 1);
    }

    @SuppressWarnings("unused")
    void swapItems(int from, int to) {
        Collections.swap(mData, from, to);
        notifyItemMoved(from, to);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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

    class ViewHolder extends MultiChoiceHelper.ViewHolder {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            lblAddress = itemView.findViewById(R.id.lblAddress);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);

            // Normal click listener (not for action mode).
            setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, mData.get(getAdapterPosition()),
                            getAdapterPosition());
                }
            });

        }

        public void bind(Student student, int position) {
            super.bind(choiceHelper, position);
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            Picasso.with(imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                    R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(imgAvatar);
        }


    }

    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Student student, int position);
    }

    public interface ContextualActionsListener {
        void onDelete(long[] checkedItemsPositions);
    }

}
