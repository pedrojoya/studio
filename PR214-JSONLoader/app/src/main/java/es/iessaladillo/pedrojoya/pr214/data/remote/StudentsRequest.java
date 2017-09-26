package es.iessaladillo.pedrojoya.pr214.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr214.data.model.Student;

public class StudentsRequest {

    private final List<Student> students;
    private Exception error;

    private StudentsRequest(List<Student> students, Exception error) {
        this.students = students;
        this.error = error;
    }

    public static StudentsRequest newErrorInstance(Exception error) {
        return new StudentsRequest(null, error);
    }

    public static StudentsRequest newListInstance(List<Student> students) {
        return new StudentsRequest(students, null);
    }

    public List<Student> getStudents() {
        return students;
    }

    public Exception getError() {
        return error;
    }

    public void removeError() {
        this.error = null;
    }

}
