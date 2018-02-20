package es.iessaladillo.pedrojoya.pr220.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

import java.util.List;

import es.iessaladillo.pedrojoya.pr220.data.model.Student;

@Dao
public abstract class StudentDao {

    @Insert
    public abstract long insertStudent(Student student);

    @Update
    public abstract int updateStudent(Student student);

    @Delete
    public abstract int deleteStudent(Student student);

    @Query("SELECT * FROM Student WHERE id = :studentId")
    public abstract LiveData<Student> getStudent(String studentId);

    @Query("SELECT * FROM Student ORDER BY name")
    public abstract LiveData<List<Student>> getStudents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertStudents(List<Student> students);

    @Query("DELETE FROM Student")
    public abstract void deleteAllStudents();

    @Transaction
    public void resetStudents(List<Student> students) {
        deleteAllStudents();
        insertStudents(students);
    }
}
