package es.iessaladillo.pedrojoya.pr203.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr203.R;
import es.iessaladillo.pedrojoya.pr203.data.model.Student;

@SuppressWarnings("unused")
public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private List<Student> students;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;
    private View emptyView;
    private final RecyclerView.AdapterDataObserver observer = new RecyclerView
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


    public MainActivityAdapter(List<Student> students) {
        this.students = students;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, students.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(v -> {
            if (onItemLongClickListener != null) {
                onItemLongClickListener.onItemLongClick(v,
                        students.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
                return true;
            } else {
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivityAdapter.ViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students == null ? 0 : students.size();
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    public void removeItem(int position) {
        students.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Student student) {
        students.add(student);
        notifyItemInserted(students.size() - 1);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    public void setData(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public void setEmptyView(@NonNull View emptyView) {
        if (this.emptyView != null) {
            unregisterAdapterDataObserver(observer);
        }
        this.emptyView = emptyView;
        registerAdapterDataObserver(observer);
    }

    private void checkEmptyViewVisibility() {
        if (emptyView != null) {
            emptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void onDestroy() {
        if (emptyView != null) {
            unregisterAdapterDataObserver(observer);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblName)
        public TextView lblName;
        @BindView(R.id.lblAddress)
        public TextView lblAddress;
        @BindView(R.id.imgPhoto)
        public CircleImageView imgPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            String url = student.getPhotoUrl();
            Picasso.with(imgPhoto.getContext()).load(url).placeholder(
                    R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(
                    imgPhoto);
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
