package es.iessaladillo.pedrojoya.pr211.ui.main;


import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.recyclerview.extensions.ListAdapter;
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
    public static final DiffCallback<Student> DIFF_CALLBACK = new DiffCallback<Student>() {
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(MainFragmentListAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(getItem(position), position);
    }

    @Override
    public void setList(List<Student> list) {
        checkEmptyViewVisibility(list == null ? 0 : list.size());
        super.setList(list);
    }

    public Student getItemAtPosition(int position) {
        Student student = null;
        try {
            student = getItem(position);
        } finally {
            return student;
        }
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

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            lblName = itemView.findViewById(R.id.lblName);
            lblGrade = itemView.findViewById(R.id.lblGrade);
            lblAddress = itemView.findViewById(R.id.lblAddress);
        }

        public void bind(Student student, int position) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAddress.setText(student.getAddress());
            imgAvatar.setImageDrawable(mDrawableBuilder.build(student.getName().substring(0, 1),
                    ColorGenerator.MATERIAL.getColor(student.getName())));
        }

    }

}
