package es.iessaladillo.pedrojoya.pr178.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private final long id;
    private final String name;
    private final String address;
    private final String photoUrl;

    public Student(long id, String name, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public long getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.photoUrl);
    }

    @SuppressWarnings("WeakerAccess")
    protected Student(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.address = in.readString();
        this.photoUrl = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
