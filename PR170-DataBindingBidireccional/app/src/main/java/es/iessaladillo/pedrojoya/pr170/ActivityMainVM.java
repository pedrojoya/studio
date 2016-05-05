package es.iessaladillo.pedrojoya.pr170;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Random;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ActivityMainVM extends BaseObservable implements Parcelable {

    private final Random mAleatorio = new Random();
    private String nombre;
    private boolean educado;
    private String tratamiento;

    public ActivityMainVM() {
    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr170.BR.nombre);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr170.BR.valido);
    }

    @Bindable
    public boolean isEducado() {
        return educado;
    }

    public void setEducado(boolean educado) {
        this.educado = educado;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr170.BR.educado);
    }

    @Bindable
    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr170.BR.tratamiento);
    }

    @Bindable
    public boolean isValido() {
        return !TextUtils.isEmpty(nombre);
    }

    @SuppressWarnings("SameReturnValue")
    public boolean saludar(View v) {
        String mensaje = v.getContext().getString(R.string.buenos_dias);
        if (educado) {
            mensaje = mensaje + " " + v.getContext().getString(R.string.tenga_usted);
            if (!tratamiento.isEmpty()) {
                mensaje = mensaje + " " + tratamiento;
            }
        }
        mensaje += " " + nombre;
        // Se muestra el mensaje
        Snackbar.make(v, mensaje, Snackbar.LENGTH_LONG).show();
        return true;
    }

    public void loadImage(View v) {
        Picasso.with(v.getContext()).load(v.getContext().getString(R.string.lorempixel) + (mAleatorio.nextInt(9) + 1)).into((ImageView) v);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeByte(educado ? (byte) 1 : (byte) 0);
        dest.writeString(this.tratamiento);
    }

    protected ActivityMainVM(Parcel in) {
        this.nombre = in.readString();
        this.educado = in.readByte() != 0;
        this.tratamiento = in.readString();
    }

    public static final Parcelable.Creator<ActivityMainVM> CREATOR = new Parcelable.Creator<ActivityMainVM>() {
        @Override
        public ActivityMainVM createFromParcel(Parcel source) {
            return new ActivityMainVM(source);
        }

        @Override
        public ActivityMainVM[] newArray(int size) {
            return new ActivityMainVM[size];
        }
    };


}
