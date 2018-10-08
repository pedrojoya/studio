package es.iessaladillo.pedrojoya.pr251.data.local;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import es.iessaladillo.pedrojoya.pr251.data.local.model.Student;

@SuppressWarnings("unused")
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

    // Returns student _id or -1 in of error.
    public long insertStudent(@NonNull Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues contentValues = student.toContentValues();
        long id = bd.insert(DbContract.Student.TABLE_NAME, null, contentValues);
        dbHelper.close();
        // Notify observers.
        (new QueryStudentsTask()).execute();
        return id;
    }

    // Return number of students deleted.
    public long deleteStudent(@NonNull Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        long deleted = bd.delete(DbContract.Student.TABLE_NAME,
                DbContract.Student._ID + " = " + student.getId(), null);
        dbHelper.close();
        // Notify observers.
        (new QueryStudentsTask()).execute();
        return deleted;
    }

    // Return number of students updated.
    public long updateStudent(Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues valores = student.toContentValues();
        long updated = bd.update(DbContract.Student.TABLE_NAME, valores,
                DbContract.Student._ID + " = " + student.getId(), null);
        dbHelper.close();
        // Notify observers.
        (new QueryStudentsTask()).execute();
        return updated;
    }

    // Return a live data with student data.
    public LiveData<Student> queryStudent(long id) {
        MutableLiveData<Student> studentLiveData = new MutableLiveData<>();
        (new QueryStudentTask(id, studentLiveData)).execute();
        return studentLiveData;
    }

    // Return a live data with all students, ordered alphabetically.
    public LiveData<List<Student>> queryStudents() {
        (new QueryStudentsTask()).execute();
        return studentsLiveData;
    }

    private ArrayList<Student> mapStudentsFromCursor(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            students.add(mapStudentFromCursor(cursor));
            cursor.moveToNext();
        }
        return students;
    }

    private Student mapStudentFromCursor(Cursor cursor) {
        Student student = new Student();
        student.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.Student._ID)));
        student.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.NAME)));
        student.setGrade(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.GRADE)));
        student.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.PHONE)));
        student.setAddress(
                cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Student.ADDRESS)));
        return student;
    }

    @SuppressLint("StaticFieldLeak")
    private class QueryStudentsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            requeryStudents();
            return null;
        }

        // Query all students, ordered alphabetically, and updates studentsLiveData.
        private void requeryStudents() {
            ArrayList<Student> students = new ArrayList<>();
            SQLiteDatabase bd = dbHelper.getWritableDatabase();
            Log.i(StudentDao.class.getSimpleName(), "Querying students");
            Cursor cursor = bd.query(DbContract.Student.TABLE_NAME, DbContract.Student.ALL_FIELDS, null,
                    null, null, null, DbContract.Student.NAME);
            if (cursor != null) {
                students = mapStudentsFromCursor(cursor);
                cursor.close();
            }
            dbHelper.close();
            studentsLiveData.postValue(students);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class QueryStudentTask extends AsyncTask<Void, Void, Void> {

        private final long id;
        private final MutableLiveData<Student> studentMutableLiveData;

        private QueryStudentTask(long id, MutableLiveData<Student> studentMutableLiveData) {
            this.id = id;
            this.studentMutableLiveData = studentMutableLiveData;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            queryStudent(id);
            return null;
        }

        // Return student or null if it doesn't exist.
        private void queryStudent(long id) {
            SQLiteDatabase bd = dbHelper.getWritableDatabase();
            Cursor cursor = bd.query(true, DbContract.Student.TABLE_NAME, DbContract.Student.ALL_FIELDS,
                    DbContract.Student._ID + " = " + id, null, null, null, null, null);
            Student student = null;
            if (cursor != null) {
                cursor.moveToFirst();
                student = mapStudentFromCursor(cursor);
            }
            dbHelper.close();
            studentMutableLiveData.postValue(student);
        }

    }

}
