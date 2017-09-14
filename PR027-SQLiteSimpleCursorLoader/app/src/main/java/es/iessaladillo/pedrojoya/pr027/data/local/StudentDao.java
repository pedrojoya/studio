package es.iessaladillo.pedrojoya.pr027.data.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;

import es.iessaladillo.pedrojoya.pr027.data.model.Student;

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

    public SQLiteDatabase openWritableDatabase() {
        return dbHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        dbHelper.close();
    }

    // CRUD (Create-Read-Update-Delete) de la tabla alumnos

    // Returns student _id or -1 in of error.
    public long createStudent(Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues contentValues = student.toContentValues();
        long id = bd.insert(DbContract.Student.TABLE_NAME, null, contentValues);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(Uri.parse(DbContract.Student.NOTIFICATION_URI), null);
        return id;
    }

    // Return true in case of success or false otherwise.
    public boolean deleteStudent(long id) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        long deleted = bd.delete(DbContract.Student.TABLE_NAME,
                DbContract.Student._ID + " = " + id, null);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(
                Uri.parse(DbContract.Student.NOTIFICATION_URI), null);
        return deleted == 1;
    }

    // Return true in case of success or false otherwise.
    public boolean updateStudent(Student student) {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        ContentValues valores = student.toContentValues();
        long updated = bd.update(DbContract.Student.TABLE_NAME, valores,
                DbContract.Student._ID + " = " + student.getId(), null);
        dbHelper.close();
        // Notify observers.
        contentResolver.notifyChange(Uri.parse(DbContract.Student.NOTIFICATION_URI), null);
        return updated == 1;
    }

    // Return student or null if it doesn't exist.
    public Student getStudent(long id) {
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

    // Return all students query cursor, ordered alphabetically.
    public Cursor queryStudents() {
        SQLiteDatabase bd = dbHelper.getWritableDatabase();
        return bd.query(DbContract.Student.TABLE_NAME, DbContract.Student.ALL_FIELDS, null, null,
                null, null, DbContract.Student.NAME);
        // DON'T CLOSE DATABASE SO WE CAN OPERATE WITH THE CURSOR.
    }

    // Return all students, ordered alphabetically.
    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        Cursor cursor = this.queryStudents();
        if (cursor != null) {
            students = mapStudentsFromCursor(cursor);
            cursor.close();
        }
        dbHelper.close();
        return students;
    }

    public static ArrayList<Student> mapStudentsFromCursor(Cursor cursor) {
        ArrayList<Student> students = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            students.add(mapStudentFromCursor(cursor));
            cursor.moveToNext();
        }
        return students;
    }

    @SuppressWarnings("WeakerAccess")
    public static Student mapStudentFromCursor(Cursor cursor) {
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
