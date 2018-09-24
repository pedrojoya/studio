package pedrojoya.iessaladillo.es.pr228.ui.main;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr228.R;
import pedrojoya.iessaladillo.es.pr228.base.BaseListAdapter;
import pedrojoya.iessaladillo.es.pr228.base.BaseViewHolder;
import pedrojoya.iessaladillo.es.pr228.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr228.utils.PicassoUtils;

public class MainActivityAdapter extends BaseListAdapter<Student, MainActivityAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<Student> diffUtilItemCallback = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(Student oldItem, Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Student oldItem, Student newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getAddress().equals(newItem.getAddress());
        }
    };

    MainActivityAdapter() {
        super(diffUtilItemCallback);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    class ViewHolder extends BaseViewHolder {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;
        public final View foreground_view;
        public final View rightLeaveBehind;
        public final View leftLeaveBehind;

        ViewHolder(View itemView) {
            super(itemView, getOnItemClickListener(), getOnItemLongClickListener());
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            foreground_view = ViewCompat.requireViewById(itemView, R.id.view_foreground);
            rightLeaveBehind = ViewCompat.requireViewById(itemView, R.id.rightLeaveBehind);
            leftLeaveBehind = ViewCompat.requireViewById(itemView, R.id.leftLeaveBehind);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            PicassoUtils.loadUrl(imgAvatar, student.getPhotoUrl(), R.drawable.ic_person_black_24dp);
        }

    }

}
