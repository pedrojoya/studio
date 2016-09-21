package es.iessaladillo.pedrojoya.pr179;

import android.os.Parcel;
import android.os.Parcelable;

class Concepto implements Parcelable {

    // Propiedades.
    private final int fotoResId;
    private final String english;
    private final String spanish;

    // Constructores.
    public Concepto(int fotoResId, String english, String spanish) {
        this.fotoResId = fotoResId;
        this.english = english;
        this.spanish = spanish;
    }

    // Getters y Setters.
    public int getFotoResId() {
        return fotoResId;
    }

    public String getEnglish() {
        return english;
    }

    public String getSpanish() {
        return spanish;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.fotoResId);
        dest.writeString(this.english);
        dest.writeString(this.spanish);
    }

    @SuppressWarnings("WeakerAccess")
    protected Concepto(Parcel in) {
        this.fotoResId = in.readInt();
        this.english = in.readString();
        this.spanish = in.readString();
    }

    public static final Parcelable.Creator<Concepto> CREATOR = new Parcelable.Creator<Concepto>() {
        public Concepto createFromParcel(Parcel source) {
            return new Concepto(source);
        }

        public Concepto[] newArray(int size) {
            return new Concepto[size];
        }
    };
}
