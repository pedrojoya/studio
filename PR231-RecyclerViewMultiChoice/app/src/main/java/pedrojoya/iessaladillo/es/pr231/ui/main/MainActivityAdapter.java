package pedrojoya.iessaladillo.es.pr231.ui.main;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr231.data.model.Student;
import pedrojoya.iessaladillo.es.pr231.multichoice.MultiChoiceModeListAdapter;
import pedrojoya.iessaladillo.es.pr231.multichoice.MultiChoiceModeViewHolder;
import pedrojoya.iessaladillo.es.pr331.R;

public class MainActivityAdapter extends MultiChoiceModeListAdapter<Student, MainActivityAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Student> diffUtilItemCallback = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(Student oldItem, Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Student oldItem, Student newItem) {
            return false;
        }
    };

    public MainActivityAdapter() {
        super(diffUtilItemCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false), this);
    }

    static class ViewHolder extends MultiChoiceModeViewHolder<Student> {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;

        public ViewHolder(View itemView, MainActivityAdapter adapter) {
            super(itemView, adapter);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

        @Override
        protected void setChecked(boolean isChecked) {
            itemView.setActivated(isChecked);
        }

        @Override
        public void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            Picasso.with(imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                    R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(
                    imgAvatar);
        }

    }

}
