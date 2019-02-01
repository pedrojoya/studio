package es.iessaladillo.pedrojoya.pr211.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.iessaladillo.pedrojoya.pr211.base.Resource;
import es.iessaladillo.pedrojoya.pr211.data.local.model.Student;

public interface Repository {

    LiveData<List<Student>> queryStudents();
    LiveData<Student> queryStudent(long studentId);
    LiveData<Resource<Long>> insertStudent(Student student);
    LiveData<Resource<Integer>> updateStudent(Student student);
    LiveData<Resource<Integer>> deleteStudent(Student student);

}
