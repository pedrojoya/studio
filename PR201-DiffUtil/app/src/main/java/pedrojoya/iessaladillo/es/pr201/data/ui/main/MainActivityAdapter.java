package pedrojoya.iessaladillo.es.pr201.data.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr201.R;
import pedrojoya.iessaladillo.es.pr201.data.model.Student;

public class MainActivityAdapter extends ListAdapter<Student, MainActivityAdapter.ViewHolder> {

    private View emptyView;
    private View.OnClickListener onItemClickListener;
    private View.OnLongClickListener onItemLongClickListener;

    // DiffCallback for get differences between old and new data list.
    private static final DiffCallback<Student> DIFF_CALLBACK = new DiffCallback<Student>() {
        @Override
        public boolean areItemsTheSame(@NonNull Student oldStudent, @NonNull Student newStudent) {
            // Student properties may have changed if reloaded from the DB, but ID is fixed
            return oldStudent.getId() == newStudent.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldStudent,
                @NonNull Student newStudent) {
            // NOTE: if you use equals, your object must properly override Object#equals()
            // Incorrectly returning false here will result in too many animations.
            return oldStudent.equals(newStudent);
        }
    };

    public MainActivityAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(v);
            }
        });
        itemView.setOnLongClickListener(v ->
                onItemLongClickListener != null && onItemLongClickListener.onLongClick(v));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainActivityAdapter.ViewHolder holder, int position) {
        Student student = getItem(position);
        holder.lblName.setText(student.getName());
        holder.lblAddress.setText(student.getAddress());
        Picasso.with(holder.imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                R.drawable.ic_user).error(R.drawable.ic_user).into(holder.imgAvatar);
    }

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public void setList(List<Student> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.setList(list);
    }

    private void checkEmptyViewVisibility(int count) {
        if (emptyView != null) {
            emptyView.setVisibility(count == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setEmptyView(View view) {
        emptyView = view;
    }

    public void setOnItemClickListener(View.OnClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(View.OnLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;


        public ViewHolder(View itemView) {
            super(itemView);
            lblName = itemView.findViewById(R.id.lblNombre);
            lblAddress = itemView.findViewById(R.id.lblDireccion);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
        }

    }


}
