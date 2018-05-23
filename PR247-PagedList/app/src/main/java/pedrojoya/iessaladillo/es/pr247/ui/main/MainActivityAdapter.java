package pedrojoya.iessaladillo.es.pr247.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr247.R;
import pedrojoya.iessaladillo.es.pr247.data.model.Student;
import pedrojoya.iessaladillo.es.pr247.recycleradapter.BasePagedListAdapter;
import pedrojoya.iessaladillo.es.pr247.recycleradapter.BaseViewHolder;

// El adaptador que visulice la PagedList debe ser un PagedListAdapter
public class MainActivityAdapter extends BasePagedListAdapter<Student, MainActivityAdapter
        .ViewHolder> {


    public MainActivityAdapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(Student oldItem, Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(Student oldItem, Student newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false), this);
    }

    static class ViewHolder extends BaseViewHolder<Student> {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;

        ViewHolder(View itemView, BasePagedListAdapter<Student, MainActivityAdapter.ViewHolder>
                adapter) {
            super(itemView, adapter);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

        @Override
        public void bind(Student student) {
            if (student != null) {
                lblName.setText(student.getName());
                lblAddress.setText(student.getAddress());
                Picasso.with(imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                        R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(
                        imgAvatar);
            }
        }

    }

}
