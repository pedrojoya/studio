package es.iessaladillo.pedrojoya.pr220.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import es.iessaladillo.pedrojoya.pr220.BR;
import es.iessaladillo.pedrojoya.pr220.R;
import es.iessaladillo.pedrojoya.pr220.data.remote.responses.StudentResponse;
import es.iessaladillo.pedrojoya.pr220.ui.views.RecyclerBindingAdapter;
import es.iessaladillo.pedrojoya.pr220.utils.ValidationUtils;

@SuppressWarnings("WeakerAccess")
@Entity
public class Student extends BaseObservable implements RecyclerBindingAdapter.ViewModel {

    @SuppressWarnings("NullableProblems")
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String phone;
    private String grade;
    private String address;

    public Student() {
    }

    @Ignore
    public Student(StudentResponse studentResponse) {
        id = studentResponse.getId();
        name = studentResponse.getName();
        phone = studentResponse.getPhone();
        grade = studentResponse.getGrade();
        address = studentResponse.getAddress();
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
        notifyPropertyChanged(BR.grade);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable({"name"})
    public boolean isValidName() {
        return !TextUtils.isEmpty(name);
    }

    @Bindable({"grade"})
    public boolean isValidGrade() {
        return !TextUtils.isEmpty(grade);
    }

    @Bindable({"phone"})
    public boolean isValidPhone() {
        return ValidationUtils.isValidPhone(phone);
    }


    @Bindable({"name", "grade", "phone"})
    public boolean isValid() {
        return isValidName() && isValidGrade() && isValidPhone();
    }

    @Ignore
    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_item;
    }

    @SuppressWarnings({"SimplifiableIfStatement", "ConstantConditions"})
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != null ? !id.equals(student.id) : student.id != null) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        if (phone != null ? !phone.equals(student.phone) : student.phone != null) return false;
        if (grade != null ? !grade.equals(student.grade) : student.grade != null) return false;
        return address != null ? address.equals(student.address) : student.address == null;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
