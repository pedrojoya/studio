package es.iessaladillo.pedrojoya.pr040.data.remote;

import java.util.List;

import es.iessaladillo.pedrojoya.pr040.data.model.Student;

public class StudentsRequest {

    private final List<Student> students;
    private Exception error;
    private final boolean loading;

    private StudentsRequest(List<Student> students, Exception error, boolean loading) {
        this.students = students;
        this.error = error;
        this.loading = loading;
    }

    public static StudentsRequest newLoadingInstance(List<Student> students) {
        return new StudentsRequest(students, null, true);
    }

    public static StudentsRequest newErrorInstance(Exception error) {
        return new StudentsRequest(null, error, false);
    }

    public static StudentsRequest newListInstance(List<Student> students) {
        return new StudentsRequest(students, null, false);
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

    public boolean isLoading() {
        return loading;
    }

}
