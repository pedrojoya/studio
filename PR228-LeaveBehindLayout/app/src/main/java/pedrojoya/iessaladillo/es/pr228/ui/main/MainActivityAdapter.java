package pedrojoya.iessaladillo.es.pr228.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr228.R;
import pedrojoya.iessaladillo.es.pr228.data.model.Student;
import pedrojoya.iessaladillo.es.pr228.recycleradapter.BaseListAdapter;
import pedrojoya.iessaladillo.es.pr228.recycleradapter.BaseViewHolder;

public class MainActivityAdapter extends BaseListAdapter<Student, MainActivityAdapter.ViewHolder> {

    public MainActivityAdapter(List<Student> data) {
        super(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false), this);
    }

    class ViewHolder extends BaseViewHolder<Student> {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;
        public final View foreground_view;
        public final View rightLeaveBehind;
        public final View leftLeaveBehind;

        ViewHolder(View itemView, MainActivityAdapter adapter) {
            super(itemView, adapter);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            foreground_view = ViewCompat.requireViewById(itemView, R.id.view_foreground);
            rightLeaveBehind = ViewCompat.requireViewById(itemView, R.id.rightLeaveBehind);
            leftLeaveBehind = ViewCompat.requireViewById(itemView, R.id.leftLeaveBehind);
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
