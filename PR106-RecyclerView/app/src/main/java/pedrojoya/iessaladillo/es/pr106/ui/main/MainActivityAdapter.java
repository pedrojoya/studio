package pedrojoya.iessaladillo.es.pr106.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr106.R;
import pedrojoya.iessaladillo.es.pr106.data.model.Student;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private List<Student> mData;
    private OnItemLongClickListener onItemLongClickListener;
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
        viewHolder.bind(mData.get(position));
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

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;


        public ViewHolder(View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lblName);
            lblAddress = itemView.findViewById(R.id.lblAddress);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }

        public void bind(Student student) {
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

    @SuppressWarnings("UnusedParameters")
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, Student student, int position);
    }

}
