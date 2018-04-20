package pedrojoya.iessaladillo.es.pr243.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    private final int id;
    private final String name;
    private final String address;
    private final String photoUrl;

    public Student(int id, String name, String address, String photoUrl) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public Student(Student student) {
        this(student.id, student.name, student.address, student.photoUrl);
    }

    public int getId() {
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

    // If we want to use DiffCallback in List<Student> we need to override default Object equals.
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id == student.id && (name != null ? name.equals(student.name) : student.name == null)
                && (address != null ? address.equals(student.address) : student.address == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.photoUrl);
    }

    protected Student(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.address = in.readString();
        this.photoUrl = in.readString();
    }

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
