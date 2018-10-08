package es.iessaladillo.pedrojoya.pr211.data.local;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import es.iessaladillo.pedrojoya.pr211.base.BaseDao;
import es.iessaladillo.pedrojoya.pr211.data.local.model.Student;

@Dao
public interface StudentDao extends BaseDao<Student> {

    @Query("SELECT * FROM Student WHERE id = :studentId")
    LiveData<Student> queryStudent(long studentId);

    @Query("SELECT * FROM Student ORDER BY name")
    LiveData<List<Student>> queryStudents();

}
