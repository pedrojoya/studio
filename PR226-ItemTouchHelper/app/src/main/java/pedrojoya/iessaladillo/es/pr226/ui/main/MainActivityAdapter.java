package pedrojoya.iessaladillo.es.pr226.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr226.R;
import pedrojoya.iessaladillo.es.pr226.base.BaseListAdapter;
import pedrojoya.iessaladillo.es.pr226.base.BaseViewHolder;
import pedrojoya.iessaladillo.es.pr226.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr226.utils.PicassoUtils;

// We don't extend ListAdapter because we want to swap items.
public class MainActivityAdapter extends BaseListAdapter<Student, MainActivityAdapter
        .ViewHolder> {

    private boolean sortEnabled;

    MainActivityAdapter() {
        super();
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

    void setSortEnabled(boolean enabled) {
        sortEnabled = enabled;
        notifyDataSetChanged();
    }

    void swapItems(int from, int to) {
        long fromOrder = getItem(from).getOrder();
        getItem(from).setOrder(getItem(to).getOrder());
        getItem(to).setOrder(fromOrder);
        Collections.swap(data, from, to);
        notifyItemMoved(from, to);
    }

    class ViewHolder extends BaseViewHolder {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;
        private final ImageView imgDrag;
        private final TextView lblOrder;

        ViewHolder(@NonNull View itemView) {
            super(itemView, getOnItemClickListener());
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            imgDrag = ViewCompat.requireViewById(itemView, R.id.imgDrag);
            lblOrder = ViewCompat.requireViewById(itemView, R.id.lblOrder);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            PicassoUtils.loadUrl(imgAvatar, student.getPhotoUrl(), R.drawable.ic_person_black_24dp);
            lblOrder.setText(String.valueOf(student.getOrder()));
            imgDrag.setVisibility(sortEnabled ? View.VISIBLE : View.INVISIBLE);
            lblOrder.setVisibility(!sortEnabled ? View.VISIBLE : View.INVISIBLE);
        }

    }

}