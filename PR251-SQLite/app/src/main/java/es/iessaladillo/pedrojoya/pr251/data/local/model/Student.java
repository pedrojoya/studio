package es.iessaladillo.pedrojoya.pr251.data.local.model;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import es.iessaladillo.pedrojoya.pr251.data.local.DbContract;

public class Student {

    private final long id;
    @NonNull
    private final String name;
    @NonNull
    private final String phone;
    @NonNull
    private final String grade;
    private final String address;

    public Student(long id, @NonNull String name, @NonNull String phone, @NonNull String grade,
        String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPhone() {
        return phone;
    }

    @NonNull
    public String getGrade() {
        return grade;
    }

    public String getAddress() {
        return address;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.Student.NAME, name);
        contentValues.put(DbContract.Student.GRADE, grade);
        contentValues.put(DbContract.Student.PHONE, phone);
        contentValues.put(DbContract.Student.ADDRESS, address);
        return contentValues;
    }

}
