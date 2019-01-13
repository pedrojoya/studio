package es.iessaladillo.pedrojoya.pr083.data.remote.dto;

import org.json.JSONException;
import org.json.JSONObject;

// Remote Student model
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class StudentDto {

    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_GRADE = "grade";
    private static final String KEY_REPEATER = "repeater";
    private static final String KEY_AGE = "age";
    private static final String KEY_PHOTO = "photo";

    private String photo;
    private String name;
    private int age;
    private String grade;
    private String address;
    private String phone;
    private boolean repeater;

    public StudentDto(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString(KEY_NAME);
        address = jsonObject.getString(KEY_ADDRESS);
        phone = jsonObject.getString(KEY_PHONE);
        grade = jsonObject.getString(KEY_GRADE);
        repeater = jsonObject.getBoolean(KEY_REPEATER);
        age = jsonObject.getInt(KEY_AGE);
        photo = jsonObject.getString(KEY_PHOTO);
    }

    public StudentDto() { }

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
