package es.iessaladillo.pedrojoya.pr164.data.model;

import android.view.View;

import es.iessaladillo.pedrojoya.pr164.R;
import es.iessaladillo.pedrojoya.pr164.multityperecycleradapter.Item;
import es.iessaladillo.pedrojoya.pr164.ui.main.StudentViewHolder;

public class Student extends Item<StudentViewHolder> {

    private int id;
    private String name;
    private int age;

    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (age != student.age) return false;
        return name != null ? name.equals(student.name) : student.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }

    @Override
    public int getLayoutResIdForType() {
        return R.layout.activity_main_student;
    }

    @Override
    public StudentViewHolder createViewHolder(View itemView) {
        return new StudentViewHolder(itemView);
    }

    @Override
    public void bind(StudentViewHolder holder, int position) {
        holder.lblNombre.setText(getName());
        holder.lblCurso.setText(String.valueOf(getAge()));
    }

    @Override
    public boolean areItemsTheSame(Item newItem) {
        return getId() == ((Student) newItem).getId();
    }

    @Override
    public boolean areContentsTheSame(Item newItem) {
        return equals(newItem);
    }

}
