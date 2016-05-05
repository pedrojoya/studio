package es.iessaladillo.pedrojoya.pr170.old;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class BindableBoolean extends BaseObservable implements Parcelable {

    boolean mValue;

    public boolean get() {
        return mValue;
    }

    public void set(boolean value) {
        if (mValue != value) {
            this.mValue = value;
            notifyChange();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(mValue ? (byte) 1 : (byte) 0);
    }

    public BindableBoolean() {
    }

    protected BindableBoolean(Parcel in) {
        this.mValue = in.readByte() != 0;
    }

    public static final Creator<BindableBoolean> CREATOR = new Creator<BindableBoolean>() {
        public BindableBoolean createFromParcel(Parcel source) {
            return new BindableBoolean(source);
        }

        public BindableBoolean[] newArray(int size) {
            return new BindableBoolean[size];
        }
    };
}