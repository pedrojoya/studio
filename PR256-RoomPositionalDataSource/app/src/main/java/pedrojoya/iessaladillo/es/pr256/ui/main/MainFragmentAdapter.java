package pedrojoya.iessaladillo.es.pr256.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr256.R;
import pedrojoya.iessaladillo.es.pr256.data.local.model.Student;

class MainFragmentAdapter extends PagedListAdapter<Student, MainFragmentAdapter
        .ViewHolder> {


    MainFragmentAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.equals(newItem);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Nullable
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
            itemView.setOnClickListener(v -> showStudent(getItem(getAdapterPosition())));
        }

        void bind(Student student) {
            if (student != null) {
                lblName.setText(student.getName());
                lblAddress.setText(student.getAddress());
                Picasso.with(imgAvatar.getContext()).load(student.getPhoto()).placeholder(
                        R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(
                        imgAvatar);
            }
        }

        private void showStudent(@Nullable Student student) {
            if (student != null) {
                Toast.makeText(itemView.getContext(), student.getName(), Toast.LENGTH_SHORT).show();
            }
        }

    }

}
