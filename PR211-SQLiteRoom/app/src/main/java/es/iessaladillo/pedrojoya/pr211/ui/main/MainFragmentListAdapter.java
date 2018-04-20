package es.iessaladillo.pedrojoya.pr211.ui.main;


import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

import es.iessaladillo.pedrojoya.pr211.R;
import es.iessaladillo.pedrojoya.pr211.data.model.Student;

public class MainFragmentListAdapter extends ListAdapter<Student, MainFragmentListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, Student student, int position);
    }

    // DiffCallback for get differences between old and new data list.
    private static final DiffUtil.ItemCallback<Student> DIFF_CALLBACK = new DiffUtil.ItemCallback<Student>() {
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

    private OnItemClickListener onItemClickListener;
    private final TextDrawable.IBuilder mDrawableBuilder;
    private View mEmptyView;

    public MainFragmentListAdapter() {
        super(DIFF_CALLBACK);
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .toUpperCase()
                .endConfig()
                .round();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getItem(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainFragmentListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(getItem(position));
    }

    @Override
    public void submitList(List<Student> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.submitList(list);
    }

    public Student getItemAtPosition(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setEmptyView(@NonNull View emptyView) {
        mEmptyView = emptyView;
    }

    private void checkEmptyViewVisibility(int count) {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(count == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblName;
        private final TextView lblAddress;
        private final ImageView imgAvatar;

        private final TextView lblGrade;

        ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblGrade = ViewCompat.requireViewById(itemView, R.id.lblGrade);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAddress.setText(student.getAddress());
            imgAvatar.setImageDrawable(mDrawableBuilder.build(student.getName().substring(0, 1),
                    ColorGenerator.MATERIAL.getColor(student.getName())));
        }

    }

}
