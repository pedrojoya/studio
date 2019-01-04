package es.iessaladillo.pedrojoya.pr178.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr178.R;
import es.iessaladillo.pedrojoya.pr178.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr178.utils.PicassoUtils;

public class MainFragmentAdapter extends ListAdapter<Student, MainFragmentAdapter.ViewHolder> {

    MainFragmentAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) && TextUtils.equals(
                    oldItem.getAddress(), newItem.getAddress()) && TextUtils.equals(
                    oldItem.getPhotoUrl(), newItem.getPhotoUrl());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainFragmentAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(getItem(position));
    }

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            itemView.setOnClickListener(v -> {
                if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                    showBottomSheetDialogFragment(getItem(getAdapterPosition()));
                }
            });
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            PicassoUtils.loadUrl(imgAvatar, student.getPhotoUrl(), R.drawable.ic_person_black_24dp);
        }

        private void showBottomSheetDialogFragment(Student student) {
            MenuBottomSheetDialogFragment.newInstance(student).show(
                ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(),
                MenuBottomSheetDialogFragment.class.getSimpleName());
        }

    }

}
