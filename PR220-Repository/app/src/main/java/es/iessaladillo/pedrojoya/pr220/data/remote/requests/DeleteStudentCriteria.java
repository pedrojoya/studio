package es.iessaladillo.pedrojoya.pr220.data.remote.requests;

import es.iessaladillo.pedrojoya.pr220.data.model.Student;

@SuppressWarnings("unused")
public class DeleteStudentCriteria {

    private String id;

    public DeleteStudentCriteria(Student student) {
        this.id = student.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
