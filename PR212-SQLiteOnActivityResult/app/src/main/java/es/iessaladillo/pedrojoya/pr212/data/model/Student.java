package es.iessaladillo.pedrojoya.pr212.data.model;

import android.content.ContentValues;

import es.iessaladillo.pedrojoya.pr212.data.local.DbContract;

public class Student {

    private long id;
    private String name;
    private String phone;
    private String grade;
    private String address;

    public Student() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
