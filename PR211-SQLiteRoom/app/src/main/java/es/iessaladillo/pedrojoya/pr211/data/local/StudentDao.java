package es.iessaladillo.pedrojoya.pr211.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import es.iessaladillo.pedrojoya.pr211.data.model.Student;

@Dao
public interface StudentDao {

    @Insert
    long insertStudent(Student student);

    @Update
    int updateStudent(Student student);

    @Delete
    int deleteStudent(Student student);

    @Query("SELECT * FROM Student WHERE id = :studentId")
    LiveData<Student> getStudent(long studentId);

    @Query("SELECT * FROM Student ORDER BY name")
    LiveData<List<Student>> getStudents();

}
