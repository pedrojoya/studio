package pedrojoya.iessaladillo.es.pr181.main.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import pedrojoya.iessaladillo.es.pr181.R;
import pedrojoya.iessaladillo.es.pr181.main.model.entity.Student;

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.lblNombre)
    TextView lblNombre;
    @BindView(R.id.lblDireccion)
    TextView lblDireccion;
    @BindView(R.id.imgAvatar)
    CircleImageView imgAvatar;

    public MainViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Student alumno) {
        lblNombre.setText(alumno.getNombre());
        lblDireccion.setText(alumno.getDireccion());
        Picasso.with(imgAvatar.getContext())
                .load(alumno.getUrlFoto())
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(imgAvatar);
    }

}