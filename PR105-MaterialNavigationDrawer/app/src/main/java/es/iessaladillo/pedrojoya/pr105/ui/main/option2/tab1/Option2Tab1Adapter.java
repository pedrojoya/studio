package es.iessaladillo.pedrojoya.pr105.ui.main.option2.tab1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;
import es.iessaladillo.pedrojoya.pr105.utils.PicassoUtils;

public class Option2Tab1Adapter extends RecyclerView.Adapter<Option2Tab1Adapter.ViewHolder> {

    private final List<Student> data;

    Option2Tab1Adapter(@NonNull List<Student> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_option2_tab1_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Option2Tab1Adapter.ViewHolder holder, int position) {
        Student student = data.get(position);
        holder.lblNombre.setText(student.getName());
        holder.lblDireccion.setText(student.getAddress());
        PicassoUtils.loadUrl(holder.imgAvatar, student.getPhotoUrl(), R.drawable.ic_person_black_24dp);
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    }

}
