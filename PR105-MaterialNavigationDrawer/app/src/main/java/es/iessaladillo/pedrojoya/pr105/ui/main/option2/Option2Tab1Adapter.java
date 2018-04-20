package es.iessaladillo.pedrojoya.pr105.ui.main.option2;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;
import es.iessaladillo.pedrojoya.pr105.R;
import es.iessaladillo.pedrojoya.pr105.data.local.model.Student;

@SuppressWarnings("unused")
public class Option2Tab1Adapter extends RecyclerView.Adapter<Option2Tab1Adapter.ViewHolder> {

    private final ArrayList<Student> mDatos;
    private OnItemClickListener onItemClickListener;

    public Option2Tab1Adapter(ArrayList<Student> datos) {
        mDatos = datos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_option2_tab1_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v,
                        mDatos.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Option2Tab1Adapter.ViewHolder holder, int position) {
        Student student = mDatos.get(position);
        holder.lblNombre.setText(student.getNombre());
        holder.lblDireccion.setText(student.getDireccion());
        Picasso.with(holder.imgAvatar.getContext())
                .load(student.getUrlFoto())
                .into(holder.imgAvatar);
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    public void removeItem(int position) {
        mDatos.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Student student) {
        mDatos.add(student);
        notifyItemInserted(mDatos.size() - 1);
    }

    public void swapItems(int from, int to) {
        Collections.swap(mDatos, from, to);
        notifyItemMoved(from, to);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNombre;
        private final TextView lblDireccion;
        private final CircleImageView imgAvatar;


        public ViewHolder(View itemView) {
            super(itemView);
            lblNombre = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblDireccion = ViewCompat.requireViewById(itemView, R.id.lblAddress);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
        }

    }

    @SuppressWarnings("UnusedParameters")
    public interface OnItemClickListener {
        void onItemClick(View view, Student student, int position);
    }

}
