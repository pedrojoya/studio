package es.iessaladillo.pedrojoya.pr151.tools;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class BindableString extends BaseObservable implements Parcelable {

    // Valor de la cadena.
    private String mValue;

    // Retorna el valor de la cadena (cadena vacía si es null).
    public String get() {
        return mValue != null ? mValue : "";
    }

    // Establece el valor de la cadena.
    public void set(String value) {
        // Si es un nuevo valor se establece y se notifica.
        if (!TextUtils.equals(this.mValue, value)) {
            mValue = value;
            notifyChange();
        }
    }

    // Retorna si es una cadena vacía.
    public boolean isEmpty() {
        return mValue == null || mValue.isEmpty();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mValue);
    }

    public BindableString() {
    }

    protected BindableString(Parcel in) {
        this.mValue = in.readString();
    }

    public static final Parcelable.Creator<BindableString> CREATOR = new Parcelable.Creator<BindableString>() {
        public BindableString createFromParcel(Parcel source) {
            return new BindableString(source);
        }

        public BindableString[] newArray(int size) {
            return new BindableString[size];
        }
    };
}