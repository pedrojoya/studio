package es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab1;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr105.utils.PicassoUtils;

public class Option2Tab1Adapter extends ListAdapter<Student, Option2Tab1Adapter.ViewHolder> {


    Option2Tab1Adapter() {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                TextUtils.equals(oldItem.getAddress(), newItem.getAddress()) &&
                TextUtils.equals(oldItem.getPhotoUrl(), newItem.getPhotoUrl());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_option2_tab1_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Option2Tab1Adapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNombre;
        private final TextView lblDireccion;
        private final CircleImageView imgAvatar;


        ViewHolder(View itemView) {
            super(itemView);
            lblNombre = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblDireccion = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

        void bind(Student student) {
            lblNombre.setText(student.getName());
            lblDireccion.setText(student.getAddress());
            PicassoUtils.loadUrl(imgAvatar, student.getPhotoUrl(), R.drawable.ic_person_black_24dp);
        }

    }

}
