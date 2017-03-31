package pedrojoya.iessaladillo.es.pr204.main.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr204.R;
import pedrojoya.iessaladillo.es.pr204.base.ImageLoader;
import pedrojoya.iessaladillo.es.pr204.component.PicassoImageLoader;
import pedrojoya.iessaladillo.es.pr204.model.entity.Alumno;

@SuppressWarnings("unused")
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Alumno> mDatos;
    private final ImageLoader mImageLoader;
    private OnItemClickListener onItemClickListener;

    public MainAdapter() {
        mDatos = new ArrayList<>();
        mImageLoader = new PicassoImageLoader();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_main_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, mDatos.get(viewHolder.getAdapterPosition()),
                        viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.bind(mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    public void removeItem(int position) {
        mDatos.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Alumno alumno) {
        mDatos.add(alumno);
        notifyItemInserted(mDatos.size() - 1);
    }

    public void changeData(List<Alumno> data) {
        mDatos = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblNombre)
        TextView lblNombre;
        @BindView(R.id.lblDireccion)
        TextView lblDireccion;
        @BindView(R.id.imgAvatar)
        CircleImageView imgAvatar;

        // El constructor recibe la vista-fila.
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(Alumno alumno) {
            lblNombre.setText(alumno.getNombre());
            lblDireccion.setText(alumno.getDireccion());
            mImageLoader.loadImageIntoImageView(alumno.getUrlFoto(), imgAvatar, R.drawable.ic_user,
                    R.drawable.ic_user);
        }

    }

    public interface OnItemClickListener {
        @SuppressWarnings("UnusedParameters")
        void onItemClick(View view, Alumno alumno, int position);
    }

}
