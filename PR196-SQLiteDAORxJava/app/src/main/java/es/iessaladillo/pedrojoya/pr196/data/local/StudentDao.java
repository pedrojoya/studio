package es.iessaladillo.pedrojoya.pr196.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.iessaladillo.pedrojoya.pr196.data.model.Student;
import io.reactivex.Single;

@SuppressWarnings("unused")
public class StudentDao {

    private static StudentDao sInstance;

    private final DbHelper dbHelper;
    private final ContentResolver contentResolver;

    private StudentDao(Context context, DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        contentResolver = context.getContentResolver();
    }

    public static synchronized StudentDao getInstance(Context context, DbHelper dbHelper) {
        // Application context to avoid memory leaks.
        if (sInstance == null) {
            sInstance = new StudentDao(context.getApplicationContext(), dbHelper);
        }
        return sInstance;
    }

    private SQLiteDatabase openWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    private void closeDatabase() {
        dbHelper.close();
    }

    // CRUD (Create-Read-Update-Delete) de la tabla alumnos

    // Returns student _id or -1 in of error.
    private long createStudent(Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues contentValues = student.toContentValues();
        long id = bd.insert(DbContract.Student.TABLE_NAME, null, contentValues);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(Uri.parse(DbContract.Student.NOTIFICATION_URI), null);
        return id;
    }

    public Single<Long> createStudentRx(final Student student) {
        return Single.fromCallable(() -> createStudent(student));
    }

    private int deleteStudent(long id) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        int deleted = bd.delete(DbContract.Student.TABLE_NAME,
                DbContract.Student._ID + " = " + id, null);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(
                Uri.parse(DbContract.Student.NOTIFICATION_URI), null);
        return deleted;
    }

    public Single<Integer> deleteStudentRx(final Student student) {
        return Single.fromCallable(() -> deleteStudent(student.getId()));
    }

    private int updateStudent(Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues valores = student.toContentValues();
        int updated = bd.update(DbContract.Student.TABLE_NAME, valores,
                DbContract.Student._ID + " = " + student.getId(), null);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(Uri.parse(DbContract.Student.NOTIFICATION_URI), null);
        return updated;
    }

    public Single<Integer> updateAlumnoRx(final Student student) {
        return Single.fromCallable(() -> updateStudent(student));
    }

    // Return student or null if it doesn't exist.
    private Student getStudent(long id) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        Cursor cursor = bd.query(true, DbContract.Student.TABLE_NAME, DbContract.Student.ALL_FIELDS,
                DbContract.Student._ID + " = " + id, null, null, null, null, null);
        Student student = null;
        if (cursor != null) {
            cursor.moveToFirst();
            student = mapStudentFromCursor(cursor);
        }
        dbHelper.close();
        return student;
    }

    public Single<Student> getStudentRx(final long id) {
        return Single.fromCallable(() -> getStudent(id));
    }

    // Return all students query cursor, ordered alphabetically.
    private Cursor queryStudents() {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        return bd.query(DbContract.Student.TABLE_NAME, DbContract.Student.ALL_FIELDS, null, null,
                null, null, DbContract.Student.NAME);
        // DON'T CLOSE DATABASE SO WE CAN OPERATE WITH THE CURSOR.
    }

    // Return all students, ordered alphabetically.
    private ArrayList<Student> getStudents() {
        Log.d(StudentDao.class.getSimpleName(), "Querying students");
        ArrayList<Student> students = new ArrayList<>();
        Cursor cursor = this.queryStudents();
        if (cursor != null) {
            students = mapStudentsFromCursor(cursor);
            cursor.close();
        }
        dbHelper.close();
        return students;
    }

    public Single<List<Student>> getStudentsRx() {
        return Single.fromCallable(this::getStudents);
    }

    private static ArrayList<Student> mapStudentsFromCursor(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            students.add(mapStudentFromCursor(cursor));
            cursor.moveToNext();
        }
        return students;
    }

    @SuppressWarnings("WeakerAccess")
    private static Student mapStudentFromCursor(Cursor cursor) {
        Student student = new Student();
        student.setId(
                cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.Student._ID)));
        student.setName(cursor.getString(
                cursor.getColumnIndexOrThrow(DbContract.Student.NAME)));
        student.setGrade(cursor.getString(
                cursor.getColumnIndexOrThrow(DbContract.Student.GRADE)));
        student.setPhone(cursor.getString(
                cursor.getColumnIndexOrThrow(DbContract.Student.PHONE)));
        student.setAddress(cursor.getString(
                cursor.getColumnIndexOrThrow(DbContract.Student.ADDRESS)));
        return student;
    }

}
