package es.iessaladillo.pedrojoya.pr195.data.model;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class Student {

    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_GRADE = "grade";
    public static final String KEY_REPEATER = "repeater";
    public static final String KEY_AGE = "age";
    public static final String KEY_PHOTO = "photo";

    private String photo;
    private String name;
    private int age;
    private String grade;
    private String address;
    private String phone;
    private boolean repeater;

    public Student(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString(Student.KEY_NAME);
        address = jsonObject.getString(Student.KEY_ADDRESS);
        phone = jsonObject.getString(Student.KEY_PHONE);
        grade = jsonObject.getString(Student.KEY_GRADE);
        repeater = jsonObject.getBoolean(Student.KEY_REPEATER);
        age = jsonObject.getInt(Student.KEY_AGE);
        photo = jsonObject.getString(Student.KEY_PHOTO);
    }

    public Student() { }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRepeater() {
        return repeater;
    }

    public void setRepeater(boolean repeater) {
        this.repeater = repeater;
    }

}
