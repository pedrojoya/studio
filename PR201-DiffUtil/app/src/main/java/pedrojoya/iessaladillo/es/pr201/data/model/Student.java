package pedrojoya.iessaladillo.es.pr201.data.model;

public class Student {

    private final int id;
    private String name;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        //noinspection SimplifiableIfStatement
        if (address != null ? !address.equals(student.address) : student.address != null)
            return false;
        return photoUrl != null ? photoUrl.equals(student.photoUrl) : student.photoUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        return result;
    }

    public Student reverseName() {
        name = new StringBuilder(name).reverse().toString();
        return this;
    }
}
