package es.iessaladillo.pedrojoya.pr151;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.CompoundButton;

public class Saludo extends BaseObservable implements Parcelable {

    private String nombre;
    private boolean educado;

    public Saludo(String nombre, boolean educado) {
        this.nombre = nombre;
        this.educado = educado;
    }

    @Bindable
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.nombre);
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.dataValid);
    }

    @Bindable
    public boolean isEducado() {
        return educado;
    }

    public void setEducado(boolean educado) {
        this.educado = educado;
        notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.educado);
    }

    @Bindable
    public boolean isDataValid() {
        return !(TextUtils.isEmpty(nombre));
    }

    public TextWatcher nombrewatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!TextUtils.equals(nombre, s.toString())) {
                    nombre = s.toString();
                    notifyPropertyChanged(es.iessaladillo.pedrojoya.pr151.BR.dataValid);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    public CompoundButton.OnCheckedChangeListener educadocheckedchangedlistener() {
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

    protected Saludo(Parcel in) {
        this.nombre = in.readString();
        this.educado = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Saludo> CREATOR = new Parcelable.Creator<Saludo>() {
        public Saludo createFromParcel(Parcel source) {
            return new Saludo(source);
        }

        public Saludo[] newArray(int size) {
            return new Saludo[size];
        }
    };
}
