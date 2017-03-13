package es.iessaladillo.pedrojoya.pr170;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MainActivityViewModel extends BaseObservable implements Parcelable {

    private String nombre;
    private boolean educado;
    private String tratamiento;

    public MainActivityViewModel() {
    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr170.BR.nombre);
        // No necesario desde Android 2.3 gracias a las propiedades dependientes.
        //notifyPropertyChanged(es.iessaladillo.pedrojoya.pr170.BR.valido);
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

    // Propiedad dependiente de la propiedad nombre.
    @Bindable({"nombre"})
    public boolean isValido() {
        return !TextUtils.isEmpty(nombre);
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

    protected MainActivityViewModel(Parcel in) {
        this.nombre = in.readString();
        this.educado = in.readByte() != 0;
        this.tratamiento = in.readString();
    }

    public static final Parcelable.Creator<MainActivityViewModel> CREATOR = new Parcelable
            .Creator<MainActivityViewModel>() {
        @Override
        public MainActivityViewModel createFromParcel(Parcel source) {
            return new MainActivityViewModel(source);
        }

        @Override
        public MainActivityViewModel[] newArray(int size) {
            return new MainActivityViewModel[size];
        }
    };


}
