package es.iessaladillo.pedrojoya.pr194.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr194.data.model.Student;

public class StudentsRequest {

    private final List<Student> students;
    private Throwable error;

    private StudentsRequest(List<Student> students, Throwable error) {
        this.students = students;
        this.error = error;
    }

    public static StudentsRequest newErrorInstance(Throwable error) {
        return new StudentsRequest(null, error);
    }

    public static StudentsRequest newListInstance(List<Student> students) {
        return new StudentsRequest(students, null);
    }

    public List<Student> getStudents() {
        return students;
    }

    public Throwable getError() {
        return error;
    }

    public void removeError() {
        this.error = null;
    }

}
