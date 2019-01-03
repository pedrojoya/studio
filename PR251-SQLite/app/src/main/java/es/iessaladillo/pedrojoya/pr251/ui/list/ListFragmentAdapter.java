package es.iessaladillo.pedrojoya.pr251.ui.list;


import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.pr251.R;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr251.ui.student.StudentFragment;

public class ListFragmentAdapter extends ListAdapter<Student, ListFragmentAdapter.ViewHolder> {

    private final TextDrawable.IBuilder drawableBuilder;

    ListFragmentAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) && TextUtils.equals(
                    oldItem.getAddress(), newItem.getAddress()) && TextUtils.equals(
                    oldItem.getGrade(), newItem.getGrade());
            }
        });
        drawableBuilder = TextDrawable.builder()
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
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListFragmentAdapter.ViewHolder viewHolder,
            int position) {
        viewHolder.bind(getItem(position));
    }

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
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
            itemView.setOnClickListener(v -> navigateToEditStudent(getItem(getAdapterPosition())));
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblGrade.setText(student.getGrade());
            lblAddress.setText(student.getAddress());
            imgAvatar.setImageDrawable(drawableBuilder.build(
                    itemView.isActivated() ? "\u2713" : student.getName().substring(0, 1),
                    itemView.isActivated() ? Color.GRAY : ColorGenerator.MATERIAL.getColor(
                            student.getName())));
        }

        private void navigateToEditStudent(Student student) {
            ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.flContent, StudentFragment.newInstance(student.getId()), StudentFragment.class.getSimpleName())
                .addToBackStack(StudentFragment.class.getSimpleName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
        }

    }

}
