package es.iessaladillo.pedrojoya.pr151;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.CompoundButton;

public class ActivityMainVM extends BaseObservable implements Parcelable {

    private String nombre;
    private boolean educado;

    public ActivityMainVM() {
    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.nombre);
        //notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.formularioValido);
    }

    @Bindable
    public boolean isEducado() {
        return educado;
    }

    public void setEducado(boolean educado) {
        this.educado = educado;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.educado);
    }

    // Depende de la propiedad nombre (es decir se debe recalcular cada vez que cambie el valor
    // de la propiedad nombre.
    @Bindable({"nombre"})
    public boolean isFormularioValido() {
        return !(TextUtils.isEmpty(nombre));
    }

    // Para vinculación bidireccional del EditText con la prop nombre.
    public TextWatcher txtNombreTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!TextUtils.equals(nombre, s.toString())) {
                    nombre = s.toString();
                    notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.formularioValido);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    // Para vinculación bidireccional del Checkbox con la prop educado.
    public CompoundButton.OnCheckedChangeListener chkEducadoCheckedChangedListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (educado != isChecked) {
                    educado = isChecked;
                }
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeByte(educado ? (byte) 1 : (byte) 0);
    }

    protected ActivityMainVM(Parcel in) {
        this.nombre = in.readString();
        this.educado = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ActivityMainVM> CREATOR = new Parcelable
            .Creator<ActivityMainVM>() {
        public ActivityMainVM createFromParcel(Parcel source) {
            return new ActivityMainVM(source);
        }

        public ActivityMainVM[] newArray(int size) {
            return new ActivityMainVM[size];
        }
    };
}
