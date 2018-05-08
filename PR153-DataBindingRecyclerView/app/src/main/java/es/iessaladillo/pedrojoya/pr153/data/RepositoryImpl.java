package es.iessaladillo.pedrojoya.pr153.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import es.iessaladillo.pedrojoya.pr153.data.local.AppDatabase;
import es.iessaladillo.pedrojoya.pr153.data.local.model.Student;

public class RepositoryImpl implements Repository {

    private static RepositoryImpl instance;

    private final AppDatabase db;

    private RepositoryImpl(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "instituto").addCallback(
                new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        DatabaseUtils.executeSqlFromAssetsFile(db, db.getVersion(), context
//                                .getAssets());
                    }
                }).allowMainThreadQueries().build();
    }

    public static synchronized RepositoryImpl getInstance(Context context) {
        if (instance == null) {
            instance = new RepositoryImpl(context);
        }
        return instance;
    }

    @Override
    public LiveData<List<Student>> getStudents() {
        return db.studentDao().getStudents();
    }

    @Override
    public LiveData<Student> getStudent(long studentId) {
        return db.studentDao().getStudent(studentId);
    }

    @Override
    public long insertStudent(Student student) {
        return db.studentDao().insertStudent(student);
    }

    @Override
    public int updateStudent(Student student) {
        return db.studentDao().updateStudent(student);
    }

    @Override
    public int deleteStudent(Student student) {
        return db.studentDao().deleteStudent(student);
    }

    @Override
    public Student newFakeStudent() { return db.newFakeStudent(); }

}
