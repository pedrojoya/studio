package es.iessaladillo.pedrojoya.pr170;

import android.os.Parcel;
import android.os.Parcelable;

import es.iessaladillo.pedrojoya.pr170.tools.BindableBoolean;
import es.iessaladillo.pedrojoya.pr170.tools.BindableString;

public class ActivityMainVM implements Parcelable {

    public BindableString nombre = new BindableString();
    public BindableBoolean educado = new BindableBoolean();
    public BindableString tratamiento = new BindableString();

    public ActivityMainVM() {
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

    protected ActivityMainVM(Parcel in) {
        nombre = in.readParcelable(BindableString.class.getClassLoader());
        educado = in.readParcelable(BindableBoolean.class.getClassLoader());
        tratamiento = in.readParcelable(BindableString.class.getClassLoader());
    }

    public static final Parcelable.Creator<ActivityMainVM> CREATOR = new Parcelable.Creator<ActivityMainVM>() {
        public ActivityMainVM createFromParcel(Parcel source) {
            return new ActivityMainVM(source);
        }

        public ActivityMainVM[] newArray(int size) {
            return new ActivityMainVM[size];
        }
    };
}
