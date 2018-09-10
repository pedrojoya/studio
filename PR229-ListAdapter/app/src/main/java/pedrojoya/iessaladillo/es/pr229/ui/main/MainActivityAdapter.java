package pedrojoya.iessaladillo.es.pr229.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr229.R;
import pedrojoya.iessaladillo.es.pr229.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr229.base.BaseListAdapter;
import pedrojoya.iessaladillo.es.pr229.base.BaseViewHolder;

public class MainActivityAdapter extends BaseListAdapter<Student, MainActivityAdapter.ViewHolder> {

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

    MainActivityAdapter() {
        super(diffUtilItemCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends BaseViewHolder {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;


        ViewHolder(View itemView, MainActivityAdapter adapter) {
            super(itemView, adapter);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            Picasso.with(imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                    R.drawable.ic_user).error(R.drawable.ic_user).into(imgAvatar);
        }

    }

}
