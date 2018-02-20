package es.iessaladillo.pedrojoya.pr220.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import es.iessaladillo.pedrojoya.pr220.data.model.Student;
import es.iessaladillo.pedrojoya.pr220.data.remote.responses.StudentResponse;
import io.reactivex.Single;

public interface Repository {

    LiveData<List<Student>> getStudents();
    LiveData<Student> getStudent(String studentId);
    Single<StudentResponse> addStudent(Student student);
    Single<List<StudentResponse>> updateStudent(Student student);
    Single<List<StudentResponse>> deleteStudent(Student student);

    Single<List<Student>> loadStudentsFromApi();

}
