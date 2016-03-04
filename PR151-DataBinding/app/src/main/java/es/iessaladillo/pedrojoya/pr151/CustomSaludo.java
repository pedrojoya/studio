package es.iessaladillo.pedrojoya.pr151;

import android.databinding.Observable;
import android.os.Parcel;
import android.os.Parcelable;

import es.iessaladillo.pedrojoya.pr151.tools.BindableBoolean;
import es.iessaladillo.pedrojoya.pr151.tools.BindableString;

public class CustomSaludo implements Parcelable {

    public BindableString nombre = new BindableString();
    public BindableBoolean educado = new BindableBoolean();
    public BindableBoolean dataValid = new BindableBoolean();

    public CustomSaludo(String n, boolean e) {
        nombre.set(n);
        educado.set(e);
        dataValid.set(!nombre.isEmpty());
        nombre.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                dataValid.set(!nombre.isEmpty());
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(nombre, flags);
        dest.writeParcelable(educado, flags);
    }

    protected CustomSaludo(Parcel in) {
        nombre = in.readParcelable(BindableString.class.getClassLoader());
        educado = in.readParcelable(BindableBoolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CustomSaludo> CREATOR = new Parcelable.Creator<CustomSaludo>() {
        public CustomSaludo createFromParcel(Parcel source) {
            return new CustomSaludo(source);
        }

        public CustomSaludo[] newArray(int size) {
            return new CustomSaludo[size];
        }
    };

}
