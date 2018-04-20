package pedrojoya.iessaladillo.es.pr232.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr232.R;
import pedrojoya.iessaladillo.es.pr232.data.model.Student;
import pedrojoya.iessaladillo.es.pr232.recycleradapter.BaseViewHolder;
import pedrojoya.iessaladillo.es.pr232.recycleradapter.FilterableListAdapter;

public class MainActivityAdapter extends FilterableListAdapter<Student, MainActivityAdapter
        .ViewHolder> {

    private static final DiffUtil.ItemCallback<Student> diffUtilItemCallback = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(Student oldItem, Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Student oldItem, Student newItem) {
            return oldItem.equals(newItem);
        }
    };

    public MainActivityAdapter() {
        super(diffUtilItemCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false), this);
    }

    @Override
    public boolean includeInFilter(Student item, String filterString) {
        return item.getName().toLowerCase().contains(filterString.toLowerCase());
    }

    class ViewHolder extends BaseViewHolder<Student> {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;


        public ViewHolder(View itemView, MainActivityAdapter adapter) {
            super(itemView, adapter);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblNombre);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblDireccion);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }


        public void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            Picasso.with(imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                    R.drawable.ic_user).error(R.drawable.ic_user).into(imgAvatar);
        }
    }


}
