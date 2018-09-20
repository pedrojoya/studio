package pedrojoya.iessaladillo.es.pr230.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.selection.ItemDetailsLookup;
import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr230.R;
import pedrojoya.iessaladillo.es.pr230.base.BaseListAdapter;
import pedrojoya.iessaladillo.es.pr230.base.BaseViewHolder;
import pedrojoya.iessaladillo.es.pr230.base.PositionalDetailsLookup;
import pedrojoya.iessaladillo.es.pr230.base.PositionalItemDetails;
import pedrojoya.iessaladillo.es.pr230.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr230.utils.PicassoUtils;

public class MainActivityListAdapter extends BaseListAdapter<Student, MainActivityListAdapter
        .ViewHolder, Long> {

    private static final DiffUtil.ItemCallback<Student> diffUtilItemCallback = new DiffUtil.ItemCallback<Student>() {
        @Override
        public boolean areItemsTheSame(Student oldItem, Student newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
            return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                    TextUtils.equals(oldItem.getAddress(), newItem.getAddress());
        }
    };

    MainActivityListAdapter() {
        super(diffUtilItemCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        // Es muy importante que la posición sea convertida a long, o de lo contrario
        // isSelected() devolverá false.
        viewHolder.bind(getItem(position),
                selectionTracker != null && selectionTracker.isSelected((long) position));
    }

    class ViewHolder extends BaseViewHolder implements PositionalDetailsLookup.DetailsProvider {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;

        ViewHolder(View itemView) {
            super(itemView, onItemClickListener);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

        @Override
        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            return new PositionalItemDetails(getAdapterPosition());
        }

        void bind(Student student, boolean selected) {
            itemView.setActivated(selected);
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            PicassoUtils.loadUrl(imgAvatar, student.getPhotoUrl(), R.drawable.ic_person_black_24dp);
        }

    }

}
