package es.iessaladillo.pedrojoya.pr251.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class StudentDao {

    private static StudentDao instance;

    private final DbHelper dbHelper;
    private final MutableLiveData<List<Student>> studentsLiveData = new MutableLiveData<>();

    private StudentDao(@NonNull DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public static StudentDao getInstance(DbHelper dbHelper) {
        if (instance == null) {
            synchronized (StudentDao.class) {
                if (instance == null) {
                    instance = new StudentDao(dbHelper);
                }
            }
        }
        return instance;
    }

    // CRUD (Create-Read-Update-Delete) of student table

    // Returns student _id or -1 if error inserting.
    public long insertStudent(@NonNull Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insertOrThrow(DbContract.Student.TABLE_NAME, null, student.toContentValues());
        updateStudentsLiveData();
        return id;
    }

    // Return number of students deleted.
    public int deleteStudent(@NonNull Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleted = db.delete(DbContract.Student.TABLE_NAME,
            DbContract.Student._ID + " = " + student.getId(), null);
        updateStudentsLiveData();
        return deleted;
    }

    // Return number of students updated.
    public int updateStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updated = db.update(DbContract.Student.TABLE_NAME, student.toContentValues(),
            DbContract.Student._ID + " = " + student.getId(), null);
        updateStudentsLiveData();
        return updated;
    }

    // Updates students LiveData in order to notify observers.
    private void updateStudentsLiveData() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            SQLiteDatabase bd = dbHelper.getReadableDatabase();
            List<Student> students;
            try (Cursor cursor = bd.query(DbContract.Student.TABLE_NAME,
                DbContract.Student.ALL_FIELDS, null, null, null, null, DbContract.Student.NAME)) {
                students = mapStudentsFromCursor(cursor);
            }
            studentsLiveData.postValue(students);
        });
    }

    // Return a live data with student data.
    public LiveData<Student> queryStudent(long id) {
        MutableLiveData<Student> studentLiveData = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Student student = null;
            try (Cursor cursor = db.query(true, DbContract.Student.TABLE_NAME,
                DbContract.Student.ALL_FIELDS, DbContract.Student._ID + " = " + id, null, null,
                null, null, null)) {
                if (cursor.moveToFirst()) {
                    student = mapStudentFromCursor(cursor);
                }
            }
            studentLiveData.postValue(student);
        });
        return studentLiveData;
    }

    // Return a live data with all students, ordered alphabetically.
    public LiveData<List<Student>> queryStudents() {
        updateStudentsLiveData();
        return studentsLiveData;
    }

    private List<Student> mapStudentsFromCursor(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>();
        while (cursor.moveToNext()) {
            students.add(mapStudentFromCursor(cursor));
        }
        return students;
    }

    private Student mapStudentFromCursor(Cursor cursor) {
        return new Student(cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.Student._ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.PHONE)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.GRADE)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.ADDRESS)));
    }

}
