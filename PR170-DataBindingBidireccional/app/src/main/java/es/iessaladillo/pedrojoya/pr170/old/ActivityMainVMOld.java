package es.iessaladillo.pedrojoya.pr170.old;

import android.os.Parcel;
import android.os.Parcelable;

@SuppressWarnings("WeakerAccess")
public class ActivityMainVMOld implements Parcelable {

    public BindableString nombre = new BindableString();
    public BindableBoolean educado = new BindableBoolean();
    public BindableString tratamiento = new BindableString();

    @SuppressWarnings("unused")
    public ActivityMainVMOld() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(nombre, flags);
        dest.writeParcelable(educado, flags);
        dest.writeParcelable(tratamiento, flags);
    }

    protected ActivityMainVMOld(Parcel in) {
        nombre = in.readParcelable(BindableString.class.getClassLoader());
        educado = in.readParcelable(BindableBoolean.class.getClassLoader());
        tratamiento = in.readParcelable(BindableString.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityMainVMOld> CREATOR = new Parcelable.Creator<ActivityMainVMOld>() {
        public ActivityMainVMOld createFromParcel(Parcel source) {
            return new ActivityMainVMOld(source);
        }

        public ActivityMainVMOld[] newArray(int size) {
            return new ActivityMainVMOld[size];
        }
    };
}
