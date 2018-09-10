package pedrojoya.iessaladillo.es.pr201.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr201.R;
import pedrojoya.iessaladillo.es.pr201.data.local.model.Student;
import pedrojoya.iessaladillo.es.pr201.base.BaseListAdapter;
import pedrojoya.iessaladillo.es.pr201.base.BaseViewHolder;

public class MainActivityAdapter extends BaseListAdapter<Student, MainActivityAdapter.ViewHolder> {

    class DiffStudentsCallback extends DiffUtil.Callback {

        private final List<Student> oldStudents;
        private final List<Student> newStudents;

        DiffStudentsCallback(List<Student> oldStudents, List<Student> newStudents) {
            this.oldStudents = oldStudents;
            this.newStudents = newStudents;
        }

        @Override
        public int getOldListSize() {
            return oldStudents == null ? 0 : oldStudents.size();
        }

        @Override
        public int getNewListSize() {
            return newStudents == null ? 0 : newStudents.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldStudents.get(oldItemPosition).getId() == newStudents.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldStudents.get(oldItemPosition).equals(newStudents.get(newItemPosition));
        }

    }

    public MainActivityAdapter(List<Student> data) {
        super(data);
    }

    @Override
    public void submitList(@NonNull List<Student> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                new DiffStudentsCallback(getList(), newList), true);
        getList().clear();
        getList().addAll(newList);
        diffResult.dispatchUpdatesTo(this);
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

    class ViewHolder extends BaseViewHolder<Student> {

        private final TextView lblName;
        private final TextView lblAddress;
        private final CircleImageView imgAvatar;


        ViewHolder(View itemView, MainActivityAdapter adapter) {
            super(itemView, adapter);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblNombre);
            lblAddress = ViewCompat.requireViewById(itemView, R.id.lblDireccion);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

        void bind(Student student) {
            lblName.setText(student.getName());
            lblAddress.setText(student.getAddress());
            Picasso.with(imgAvatar.getContext()).load(student.getPhotoUrl()).placeholder(
                    R.drawable.ic_person_black_24dp).error(R.drawable.ic_person_black_24dp).into(imgAvatar);
        }

    }


}
